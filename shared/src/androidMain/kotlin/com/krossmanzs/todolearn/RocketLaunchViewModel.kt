package com.krossmanzs.todolearn

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krossmanzs.todolearn.entity.RocketLaunch
import kotlinx.coroutines.launch

// A RocketLaunchScreenState instance will store data received from
// the SDK and the current state of the request.
class RocketLaunchViewModel(private val sdk: SpaceSDK): ViewModel() {
    private val _state = mutableStateOf(RocketLaunchScreenState())
    val state: State<RocketLaunchScreenState> = _state

    fun loadLaunches() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, launches = emptyList())

            try {
                val launches = sdk.getLaunches(forceReload = true)
                _state.value = _state.value.copy(isLoading = false, launches = launches)
            } catch (_: Exception) {
                _state.value = _state.value.copy(isLoading = true, launches = emptyList())
            }
        }
    }

    init {
        loadLaunches()
    }
}

data class RocketLaunchScreenState(
    val isLoading: Boolean = false,
    val launches: List<RocketLaunch> = emptyList()
)