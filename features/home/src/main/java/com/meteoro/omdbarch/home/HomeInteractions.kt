package com.meteoro.omdbarch.home

import com.meteoro.omdbarch.architecture.StateTransition
import com.meteoro.omdbarch.architecture.UserInteraction

data class SearchQuery(val query: String) : UserInteraction, StateTransition.Parameters