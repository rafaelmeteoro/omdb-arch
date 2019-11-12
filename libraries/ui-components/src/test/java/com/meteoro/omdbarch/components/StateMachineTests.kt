package com.meteoro.omdbarch.components

import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test

class StateMachineTests {

    data class User(val name: String)

    lateinit var machine: StateMachine<User>

    @Before
    fun `before each test`() {
        machine = StateMachine()
    }

    @Test
    fun `verify composite with an empty upstream`() {
        val noResults = Flowable.empty<User>().compose(machine)
        val events = listOf(ViewState.Launched, ViewState.Done)

        assertMachineExecution(
            incoming = noResults,
            expected = events
        )
    }

    @Test
    fun `verify composite with broken upstream`() {
        val failure = IllegalStateException("You failed")
        val errorHappened = Flowable.error<User>(failure).compose(machine)
        val events = listOf(ViewState.Launched, ViewState.Failed(failure), ViewState.Done)

        assertMachineExecution(
            incoming = errorHappened,
            expected = events
        )
    }

    @Test
    fun `verify composite with an successful upstream`() {
        val user = User(name = "Iron Man")
        val execution = Flowable.just(user).compose(machine)
        val events = listOf(ViewState.Launched, ViewState.Success(user), ViewState.Done)

        assertMachineExecution(
            incoming = execution,
            expected = events
        )
    }

    private fun assertMachineExecution(
        incoming: Flowable<ViewState<User>>,
        expected: List<ViewState<User>>
    ) {
        incoming.test()
            .assertTerminated()
            .assertComplete()
            .assertNoErrors()
            .assertValueSequence(expected)
    }
}