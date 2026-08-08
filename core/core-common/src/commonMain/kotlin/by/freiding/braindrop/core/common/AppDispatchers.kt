package by.freiding.braindrop.core.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Injected via Koin so data-layer classes (repositories, data sources) don't hardcode
 * Dispatchers.Default — that keeps DB/file work off the ViewModel's Main-confined
 * viewModelScope and gives tests a seam to swap in a deterministic test dispatcher.
 */
class AppDispatchers(
    val io: CoroutineDispatcher = Dispatchers.Default,
)
