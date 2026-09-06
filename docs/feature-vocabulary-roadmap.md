# Feature Roadmap: Vocabulary

The Vocabulary section (chunks + Leitner SRS + card session + CLOZE/TYPING quiz)
shipped on `feat/words`. This file tracks the pieces that were deliberately left
out of v1.

Design reference: `design/vocabulary/Vocabulary Chunks.dc.html` (artboards 01–08),
`design/vocabulary/github.md` (screen map).

---

## Deferred: mini-story from learned chunks (artboard 08)

**Status:** not started — cut from v1 by scope decision. Artboards 01–07 were
built; the "Собрать историю из N чанков" button on the quiz result screen was
dropped rather than wired.

### What it is

A short, coherent paragraph (3–5 sentences) that stitches together several
chunks the user has already learned in one theme, so the collocations are seen
working together in a connected text rather than in isolated example sentences.

From artboard 08:

- Header: `История · <тема>` + caption `N ВЫУЧЕННЫХ ЧАНКОВ В ОДНОМ ТЕКСТЕ`.
- Body: the English story, with every embedded chunk highlighted
  (`E8EFFE` bg / `1B3F97` text — the same chunk-highlight treatment as
  `ChunkHighlight`). Example from the design:

  > On Monday the team had to **make a decision** about the new office. Nobody
  > wanted to **take responsibility for** it, so we agreed to **meet a deadline**
  > on Friday instead. Anna had to **catch a flight** that evening, and when the
  > contract was finally signed she was **over the moon**.

- `ПЕРЕВОД` — full Russian translation of the story in a muted card
  (toggleable; design has a `showStoryTranslation` prop, default on).
- `ЧАНКИ В ТЕКСТЕ` — the list of chunks used, each a chip/row. Chunks that are
  **due for review** are tinted yellow, with the note:
  *«Жёлтым — те, что вернутся на повторение: история засчитывается как одна
  успешная встреча.»*
- Buttons: `Другая история` (regenerate) · `Готово` (back to list).

### Behavioural notes

- **Counts as one successful review.** Reading a story marks each due chunk it
  contains as reviewed with a `KNOW`-equivalent grade — i.e. run
  `ChunkSrs.schedule(..., RecallGrade.KNOW, ...)` once per due chunk, the same
  way `SubmitVocabularyQuizAnswerUseCase` grades a correct answer. New (never
  seen) chunks should probably be excluded from story selection so the story is
  genuinely a *review* surface.
- **Single theme per story**, chosen from themes where the user has ≥ N learned
  chunks (design shows N = 6; make it a constant, allow 4–8).
- **Entry points:** (1) re-add the button on the quiz result screen
  (`Собрать историю из N чанков`, shown only when a theme qualifies);
  (2) optionally a button on the list bottom bar next to `Карточная сессия`.

### Open question — how the story text is produced

The Vocabulary section is **fully offline** (no remote data source, no DTO
layer). The story generator has to respect that. Options, roughly in order of
effort:

1. **Hand-authored templates per theme.** A small set of story skeletons per
   theme with ordered chunk slots (`{chunk:make_a_decision}` …). Pick a template
   whose slots are all covered by the user's learned chunks; fill slots with the
   chunk text and splice `moreExamples`-style connective prose around them.
   Deterministic, fully offline, but authoring cost scales with themes and the
   text can feel canned. **Most likely v1 choice.**
2. **On-device assembly** from per-chunk "story fragment" fields added to the
   `Chunk` model (one clause per chunk that already reads as story prose). Order
   fragments, add fixed connectives (`On Monday…`, `so…`, `and when…`). Cheaper
   to author than #1, less coherent.
3. **LLM call.** Best text, but needs network + a backend/key and breaks the
   offline guarantee. Would be a separate, later track if the app ever gains a
   networked tier.

Decision needed before implementation: which of the above, and (for #1/#2) who
authors the connective text.

### Rough shape if picked up (follows `CLAUDE.md` MVVM + UseCase + Koin)

| Piece | Location |
|---|---|
| `ChunkStory` model (`theme`, `english`, `russian`, `usedChunkIds`, `dueChunkIds`) | `domain/model/` |
| `GenerateChunkStoryUseCase` (theme → `ChunkStory?`), `SubmitChunkStoryReviewUseCase` (grade the due chunks) | `domain/usecase/` |
| story templates / fragments seed | `data/datasource/seed/` |
| `presentation/story/` — `VocabularyStoryContracts` / `ViewModel` / `Screen` | mirrors the other trios |
| `Routes.VocabularyStory(theme: String)` | `core-navigation/Routes.kt` |
| `composable<Routes.VocabularyStory>` + nav from quiz result | `shared/App.kt`, feature-home `HomeScreen` unaffected |
| re-add the result-screen button (currently absent — see `VocabularyQuizScreen`) | `presentation/quiz/` |
| reuse `ChunkHighlight`, `brainDropCard`, `BrainDropButton` | — |
| tests: `GenerateChunkStoryUseCaseTest` (theme qualification, chunk selection, no-eligible-theme → null), story-review grading | `commonTest/` |
