package com.meteoro.omdbarch.architecture

import com.meteoro.omdbarch.coroutines.testutils.CoroutinesTestHelper
import com.meteoro.omdbarch.coroutines.testutils.FlowTest.Companion.flowTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal class StateMachineContainerTest {

    private lateinit var machine: StateMachineContainer<String>

    @get:Rule
    val helper = CoroutinesTestHelper()

    @Before
    fun `before each test`() {
        machine = StateMachineContainer(
            container = StateContainer.Unbounded(helper.scope),
            executor = TaskExecutor.Synchronous(helper.scope)
        )
    }

    @Test
    fun `should generate states, successful execution`() {
        // Given
        flowTest(machine.states()) {
            // When
            triggerEmissions {
                machine.consume(
                    StateTransition(::successfulExecution)
                )
            }

            // Then
            afterCollect { emissions ->
                val expectedStates = listOf(
                    ViewState.FirstLaunch,
                    ViewState.Loading.FromEmpty,
                    ViewState.Success(MESSAGE)
                )

                assertThat(emissions).isEqualTo(expectedStates)
            }
        }
    }

    @Test
    fun `should generate states, error execution`() {
        // Given
        flowTest(machine.states()) {
            // When
            triggerEmissions {
                machine.consume(
                    StateTransition(::successfulExecution)
                )
            }

            // Then
            afterCollect { emissions ->
                val expectedStates = listOf(
                    ViewState.FirstLaunch,
                    ViewState.Loading.FromEmpty,
                    ViewState.Failed(ERROR)
                )

                assertThat(emissions).isEqualTo(expectedStates)
            }
        }
    }

    @Test
    fun `should generate states, with previous execution`() {
        // Given
        flowTest(machine.states()) {
            // When
            triggerEmissions {
                machine.consume(
                    StateTransition(::successfulExecution)
                )
            }

            // and
            triggerEmissions {
                machine.consume(
                    StateTransition(::successfulExecution, Interaction)
                )
            }

            // Then
            afterCollect { emissions ->
                val expectedStates = listOf(
                    ViewState.FirstLaunch,
                    ViewState.Loading.FromEmpty,
                    ViewState.Success(MESSAGE),
                    ViewState.Loading.FromPrevious(MESSAGE),
                    ViewState.Success(Interaction.DATA)
                )

                assertThat(emissions).isEqualTo(expectedStates)
            }
        }
    }

    @Test
    fun `should generate states, ignoring previous broken execution`() {
        // Given
        flowTest(machine.states()) {
            // When
            triggerEmissions {
                machine.consume(
                    StateTransition(::brokenExecution)
                )
            }

            // And
            triggerEmissions {
                machine.consume(
                    StateTransition(::successfulExecution)
                )
            }

            // Then
            afterCollect { emissions ->
                val expectedStates = listOf(
                    ViewState.FirstLaunch,
                    ViewState.Loading.FromEmpty,
                    ViewState.Failed(ERROR),
                    ViewState.Loading.FromEmpty,
                    ViewState.Success(MESSAGE)
                )

                assertThat(emissions).isEqualTo(expectedStates)
            }
        }
    }

    private suspend fun successfulExecution(): String {
        return suspendCoroutine { continuation ->
            continuation.resume(MESSAGE)
        }
    }

    private suspend fun successfulExecution(parameters: StateTransition.Parameters): String {
        return suspendCoroutine { continuation ->
            val interaction = parameters as Interaction
            continuation.resume(interaction.DATA)
        }
    }

    private suspend fun brokenExecution(): String {
        return suspendCoroutine { continuation ->
            continuation.resumeWithException(ERROR)
        }
    }

    private companion object {
        const val MESSAGE = "Kotlin is awesome"
        val ERROR = IllegalStateException("Ouch")
    }

    object Interaction : StateTransition.Parameters {
        const val DATA = "Hello"
    }
}
