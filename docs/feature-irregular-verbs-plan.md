# Feature Plan: Irregular Verbs

## Scope

~200 most common irregular verbs. Each verb has:
- base form, past simple, past participle
- Russian translation
- 2 example sentences (English + Russian)

Users can mark verbs as learned manually. Quiz shows only unlearned verbs.

---

## Data Model

```kotlin
// domain/model/IrregularVerb.kt
data class IrregularVerb(
    val id: String,               // == baseForm, e.g. "go"
    val baseForm: String,
    val pastSimple: String,
    val pastParticiple: String,
    val translation: String,      // Russian, e.g. "идти, ехать"
    val examples: List<VerbExample>
)

data class VerbExample(
    val english: String,
    val russian: String
)

// domain/model/VerbProgress.kt
data class VerbProgress(
    val verbId: String,
    val isLearned: Boolean,
    val timesCorrect: Int,
    val timesIncorrect: Int
)

// domain/model/QuizQuestion.kt
data class QuizQuestion(
    val verb: IrregularVerb,
    val type: QuizType,
    val correctAnswer: String,
    val options: List<String>     // 4 options including correct
)

enum class QuizType {
    EN_TO_RU,          // given base form → choose Russian translation
    RU_TO_EN,          // given Russian translation → choose base form
    VERB_FORMS         // given base form → choose past simple or past participle
}
```

---

## Database

New SQLDelight table in `core-database`:

```sql
-- IrregularVerbProgress.sq
CREATE TABLE IrregularVerbProgress (
    verb_id TEXT NOT NULL PRIMARY KEY,
    is_learned INTEGER NOT NULL DEFAULT 0,
    times_correct INTEGER NOT NULL DEFAULT 0,
    times_incorrect INTEGER NOT NULL DEFAULT 0,
    last_studied_at INTEGER
);
```

Queries needed: `getAll`, `getByVerbId`, `upsertProgress`, `markLearned`, `countLearned`.

---

## Module Structure

```
feature/feature-irregular-verbs/
├── build.gradle.kts                      # kmp.feature convention plugin
├── data/
│   ├── datasource/
│   │   ├── LocalIrregularVerbDataSource.kt   # static verb list (200 verbs)
│   │   └── LocalVerbProgressDataSource.kt    # wraps IrregularVerbProgressQueries
│   └── repository/
│       └── IrregularVerbRepositoryImpl.kt
├── domain/
│   ├── model/
│   │   ├── IrregularVerb.kt
│   │   ├── VerbExample.kt
│   │   ├── VerbProgress.kt
│   │   └── QuizQuestion.kt + QuizType.kt
│   ├── repository/
│   │   └── IrregularVerbRepository.kt
│   └── usecase/
│       ├── GetVerbsUseCase.kt             # returns List<Pair<IrregularVerb, VerbProgress>>
│       ├── GetVerbDetailUseCase.kt        # returns Pair<IrregularVerb, VerbProgress>
│       ├── ToggleVerbLearnedUseCase.kt    # flips isLearned
│       ├── GenerateQuizUseCase.kt         # picks unlearned verbs, builds QuizQuestion list
│       └── SubmitQuizAnswerUseCase.kt     # records correct/incorrect, returns isCorrect
├── presentation/
│   ├── list/
│   │   ├── VerbListScreen.kt
│   │   ├── VerbListContracts.kt           # UiState / UiEvent / UiEffect
│   │   └── VerbListViewModel.kt
│   ├── detail/
│   │   ├── VerbDetailScreen.kt
│   │   ├── VerbDetailContracts.kt
│   │   └── VerbDetailViewModel.kt
│   └── quiz/
│       ├── QuizScreen.kt
│       ├── QuizContracts.kt
│       └── QuizViewModel.kt
└── di/
    └── IrregularVerbsModule.kt
```

---

## Screens

### VerbListScreen
- Toolbar with title "Irregular Verbs" and total/learned counter (e.g. "47/200")
- Tab or chip filter: "All" / "To learn" (default)
- Each row: base form, past simple, past participle, Russian translation, ✓ badge if learned
- FAB or top button "Start Quiz" → navigates to QuizScreen
- Tap row → VerbDetailScreen

### VerbDetailScreen
- Shows all 3 forms + translation
- 2 example sentences with Russian translations
- "Mark as learned / Unmark" toggle button

### QuizScreen
- Entry: mode selector (EN→RU / RU→EN / Verb Forms) shown as BottomSheet or inline chips
- Question card: question text at top, 4 answer buttons below
- After tap: highlights correct (green) and wrong (red), short delay, then next question
- Progress bar at top (questions answered / total)
- End screen: score summary, "Try again" / "Back to list"

---

## Navigation Routes

```kotlin
// core-navigation/Routes.kt additions
@Serializable
data object IrregularVerbsList : Routes

@Serializable
data class IrregularVerbDetail(val verbId: String) : Routes

@Serializable
data class IrregularVerbsQuiz(val mode: String = "EN_TO_RU") : Routes
```

---

## Wiring

1. `core-database`: add `IrregularVerbProgress.sq`, expose `IrregularVerbProgressQueries` from `AppDatabase`.
2. `core-navigation`: add 3 new routes.
3. `feature-irregular-verbs`: full implementation per module structure above.
4. `shared/App.kt`: add 3 composable destinations.
5. `app/BrainDropApplication.kt`: add `irregularVerbsModule` to `startKoin`.
6. `feature-home`: add "Irregular Verbs" category card that emits navigation effect to `IrregularVerbsList`.

---

## Implementation Order

1. [x] `IrregularVerbProgress.sq` + expose queries in `core-database`
2. [x] Routes in `core-navigation`
3. [x] `feature-irregular-verbs` module scaffold + `build.gradle.kts`
4. [x] Domain models + repository interface
5. [x] Static verb data (179 verbs) in `LocalIrregularVerbDataSource`
6. [x] `LocalVerbProgressDataSource` + `IrregularVerbRepositoryImpl`
7. [x] All 5 use cases
8. [x] `VerbListViewModel` + `VerbListScreen`
9. [x] `VerbDetailViewModel` + `VerbDetailScreen`
10. [x] `QuizViewModel` + `QuizScreen`
11. [x] `IrregularVerbsModule` (Koin)
12. [x] Wire into `App.kt`, `BrainDropApplication`, home category
13. [ ] Unit tests: use cases + ViewModels (optional, can add later)

---

## Open Questions / Decisions Made

| Question | Answer |
|---|---|
| Verb count | ~200 most common |
| Example translations | Yes, Russian included |
| Learned criterion | Manual button only |
| Quiz scope | Unlearned verbs only |
