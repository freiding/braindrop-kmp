# Project Architecture Guide

## Overview

This is a **Kotlin Multiplatform (KMP)** project targeting Android and iOS.
Each feature is an isolated Gradle module. Architecture: **MVVM via UseCases**. DI: **Koin**.

---

## Module Structure

```
root/
├── app/                        # Android entry point
├── iosApp/                     # iOS entry point (Xcode)
├── core/
│   ├── core-ui/                # Shared UI components, theme, design system
│   ├── core-network/           # HTTP client (Ktor), interceptors, base models
│   ├── core-database/          # SQLDelight setup, base DAOs
│   ├── core-common/            # Extensions, utils, Result wrapper, dispatchers
│   └── core-navigation/        # Navigation contracts, Route definitions
├── feature/
│   ├── feature-auth/
│   ├── feature-home/
│   ├── feature-profile/
│   └── feature-<name>/         # Each feature follows the same internal structure
└── build-logic/                # Convention plugins (shared Gradle config)
```

### Feature module internal structure

```
feature-<name>/
├── data/
│   ├── repository/             # RepositoryImpl
│   ├── datasource/             # Remote + Local data sources
│   └── dto/                    # API response models
├── domain/
│   ├── model/                  # Domain entities (pure Kotlin, no Android/platform deps)
│   ├── repository/             # Repository interface
│   └── usecase/                # One file per use case
├── presentation/
│   ├── viewmodel/              # ViewModel(s) for this feature
│   ├── screen/                 # Composable screens
│   └── component/              # Local UI components specific to this feature
└── di/
    └── <Name>Module.kt         # Koin module for this feature
```

---

## Architecture Rules

### MVVM + UseCase

- **ViewModel** does NOT talk to Repository directly — only through UseCases.
- **UseCase** contains a single `operator fun invoke(...)` method.
- **ViewModel** exposes:
  - `StateFlow<UiState>` for screen state
  - `SharedFlow<UiEffect>` for one-time side effects (navigation, toasts)
  - Accepts `UiEvent` from the UI (user actions)

```kotlin
// UiState — immutable data class
data class HomeUiState(
    val isLoading: Boolean = false,
    val items: List<Item> = emptyList(),
    val error: String? = null
)

// UiEffect — sealed class for one-time events
sealed class HomeUiEffect {
    data class NavigateTo(val route: String) : HomeUiEffect()
    data class ShowToast(val message: String) : HomeUiEffect()
}

// UiEvent — sealed class for user actions
sealed class HomeUiEvent {
    data object Refresh : HomeUiEvent()
    data class ItemClicked(val id: String) : HomeUiEvent()
}
```

### UseCase pattern

```kotlin
class GetItemsUseCase(
    private val repository: ItemRepository
) {
    suspend operator fun invoke(): Result<List<Item>> {
        return repository.getItems()
    }
}
```

- One class = one responsibility.
- UseCases live in `domain/usecase/`.
- UseCases depend only on Repository **interfaces**, never on implementations.
- UseCases are **not** Android-aware — no Context, no ViewModel dependency.

### Repository pattern

- Interface in `domain/repository/` — pure Kotlin.
- Implementation in `data/repository/` — can use Ktor, SQLDelight, etc.
- Repository maps DTOs → Domain models.

```kotlin
// domain/repository/ItemRepository.kt
interface ItemRepository {
    suspend fun getItems(): Result<List<Item>>
}

// data/repository/ItemRepositoryImpl.kt
class ItemRepositoryImpl(
    private val remoteDataSource: ItemRemoteDataSource,
    private val localDataSource: ItemLocalDataSource
) : ItemRepository {
    override suspend fun getItems(): Result<List<Item>> = runCatching {
        remoteDataSource.fetchItems().map { it.toDomain() }
    }
}
```

---

## Dependency Injection (Koin)

Each feature module has its own Koin module in `di/<Name>Module.kt`.

```kotlin
// feature-home/di/HomeModule.kt
val homeModule = module {
    factory { GetItemsUseCase(get()) }
    factory { ItemRepositoryImpl(get(), get()) } bind ItemRepository::class
    viewModel { HomeViewModel(get()) }
}
```

Root DI wiring:

```kotlin
// In app module or shared startKoin block
startKoin {
    modules(
        coreNetworkModule,
        coreDatabaseModule,
        authModule,
        homeModule,
        profileModule
        // add new feature modules here
    )
}
```

Rules:
- **Never** inject concrete implementations — always bind to interfaces.
- Use `factory` for UseCases and Repositories (stateless).
- Use `viewModel { }` for ViewModels.
- Use `single { }` only for truly singleton objects (HTTP client, DB driver).
- Do not reference other feature modules' DI modules directly.

---

## Shared Code Guidelines

- **Domain layer** must be 100% pure Kotlin — zero platform code.
- Avoid `expect/actual` unless necessary; prefer interface + DI injection for platform differences.
- Use `kotlinx.coroutines` for async; `kotlinx.serialization` for JSON.
- HTTP client: **Ktor** (configured in `core-network`).
- Local storage: **SQLDelight** (configured in `core-database`).
- Date/time: **kotlinx-datetime**.

---

## Error Handling

Use a unified `Result<T>` wrapper (stdlib `kotlin.Result` or a custom sealed class):

```kotlin
// Recommended custom wrapper for richer error typing
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: AppException) : Result<Nothing>()
}
```

- Catch exceptions at the **data layer** boundary (RepositoryImpl or DataSource).
- Domain and presentation layers work with `Result<T>`, never throw.
- Map network/DB exceptions to `AppException` subclasses in `core-common`.

---

## Naming Conventions

| Layer | Suffix | Example |
|---|---|---|
| Domain model | (none) | `User`, `Item` |
| DTO | `Dto` | `UserDto` |
| Remote DataSource | `RemoteDataSource` | `ItemRemoteDataSource` |
| Local DataSource | `LocalDataSource` | `ItemLocalDataSource` |
| Repository interface | `Repository` | `ItemRepository` |
| Repository impl | `RepositoryImpl` | `ItemRepositoryImpl` |
| UseCase | `UseCase` | `GetItemsUseCase` |
| ViewModel | `ViewModel` | `HomeViewModel` |
| Koin module val | `<name>Module` | `homeModule` |
| UI State | `UiState` | `HomeUiState` |
| UI Effect | `UiEffect` | `HomeUiEffect` |
| UI Event | `UiEvent` | `HomeUiEvent` |

---

## Navigation

- Navigation contracts (routes/destinations) are defined in `core-navigation`.
- Feature modules **emit** navigation effects via `UiEffect` — they do not navigate directly.
- The app/host layer subscribes to effects and performs actual navigation.
- Never import one `feature-X` from another `feature-Y`.

---

## Testing

- Each layer is tested independently.
- **Domain / UseCase tests**: pure unit tests, no mocks of Android classes.
- **ViewModel tests**: use `kotlinx-coroutines-test` + `Turbine` for Flow testing.
- **Repository tests**: mock DataSources; test mapping logic.
- **UI tests** (optional): Compose UI tests or screenshot tests.

```kotlin
// Example ViewModel test
@Test
fun `when use case returns success, state has items`() = runTest {
    val useCase = mockk<GetItemsUseCase>()
    coEvery { useCase() } returns Result.Success(listOf(fakeItem))
    val vm = HomeViewModel(useCase)

    vm.state.test {
        val state = awaitItem()
        assertFalse(state.isLoading)
        assertEquals(listOf(fakeItem), state.items)
    }
}
```

Test file location mirrors source: `src/commonTest/` or `src/androidTest/`.

---

## Gradle / Build

- Shared Gradle configuration lives in `build-logic/` as **convention plugins**.
- Do not duplicate Gradle config across feature modules — extend the convention plugin.
- Version catalog (`libs.versions.toml`) is the single source of truth for all dependency versions.
- Feature modules expose only what's needed: prefer `implementation` over `api`.

Convention plugin naming:
- `kmp.library` — for shared KMP library modules
- `kmp.feature` — for feature modules (includes Compose, ViewModel, Koin)
- `kmp.application` — for the Android app module

---

## Code Style

- Follow [Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html).
- Max line length: **120 characters**.
- No wildcard imports.
- All public API in domain layer must have KDoc.
- Prefer `data class` for models; use `copy()` for state updates.
- Coroutine scope in ViewModel: use `viewModelScope`; in other classes inject `CoroutineScope` via Koin.

---

## Adding a New Feature — Checklist

When asked to create a new feature module, follow these steps:

1. Create `feature/feature-<name>/` directory with the standard internal structure above.
2. Add `build.gradle.kts` extending the `kmp.feature` convention plugin.
3. Define domain model(s) in `domain/model/`.
4. Define repository interface in `domain/repository/`.
5. Implement DTO(s) in `data/dto/` and mapping extensions.
6. Implement remote/local data sources in `data/datasource/`.
7. Implement repository in `data/repository/`.
8. Create UseCase(s) in `domain/usecase/`.
9. Create `UiState`, `UiEffect`, `UiEvent` in `presentation/viewmodel/`.
10. Implement ViewModel.
11. Create Composable screen(s) in `presentation/screen/`.
12. Wire Koin module in `di/<Name>Module.kt`.
13. Register the Koin module in the root `startKoin` block.
14. Add navigation route to `core-navigation` if needed.
15. Write unit tests for UseCases and ViewModel.

---

## What Claude Should NOT Do

- Do not add business logic inside a ViewModel — delegate to UseCase.
- Do not let a UseCase depend on another UseCase — compose at ViewModel level.
- Do not import `feature-X` from `feature-Y`.
- Do not use `GlobalScope` — always use injected or `viewModelScope`.
- Do not put `@Composable` functions in ViewModel or domain layers.
- Do not hardcode strings, colors, or dimensions — use the design system from `core-ui`.
- Do not skip the `Result` wrapper — never let exceptions propagate to ViewModel uncaught.
