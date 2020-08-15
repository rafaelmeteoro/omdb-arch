package com.meteoro.omdbarch.home

import com.meteoro.omdbarch.architecture.StateMachineContainer
import com.meteoro.omdbarch.architecture.StateTransition
import com.meteoro.omdbarch.architecture.UnsupportedUserInteraction
import com.meteoro.omdbarch.architecture.UserInteraction
import com.meteoro.omdbarch.domain.repository.CorSearchRepository
import com.meteoro.omdbarch.domain.repository.ManagerRepository

class HomeContainerViewModel(
    private val searchRepository: CorSearchRepository,
    private val managerRepository: ManagerRepository,
    private val machine: StateMachineContainer<HomePresentation>
) {
    fun bind() = machine.states()

    fun handle(interaction: UserInteraction) =
        interpret(interaction)
            .let { transition ->
                machine.consume(transition)
            }

    private fun interpret(interaction: UserInteraction) =
        when (interaction) {
            is SearchQuery -> StateTransition(::searchMovies, interaction)
            else -> throw UnsupportedUserInteraction
        }

    private suspend fun searchMovies(parameters: StateTransition.Parameters): HomePresentation {
        val interaction = parameters as SearchQuery
        saveSearch(interaction.query)
        val result = searchRepository.searchMovies(interaction.query)
        return HomePresentationMapper().fromDomain(result)
    }

    private fun saveSearch(query: String) {
        if (query.isNotEmpty()) managerRepository.save(query)
    }
}
