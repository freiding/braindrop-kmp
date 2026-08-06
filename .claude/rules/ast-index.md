# ast-index — BrainDrop-specific notes

The general rules (ast-index before Grep, no Grep "for completeness", when Grep is
still right) and the cross-language command reference live in the global rule and are
already loaded — this file carries only what's specific to this repository.

## Kotlin / Android Notes

| Task | Command |
|------|---------|
| Find `@Composable` functions | `ast-index composables "Screen"` |
| Find `@Preview` functions | `ast-index previews` |
| Find suspend functions | `ast-index suspend "Name"` |
| Find Flow/StateFlow/SharedFlow | `ast-index flows` |
| Find extension functions | `ast-index extensions "Type"` |
| Find deeplinks | `ast-index deeplinks` |
| Resource usages | `ast-index resource-usages "name"` |
| Public API of a module | `ast-index api "frame-sdk"` |
| Unused module deps | `ast-index unused-deps "features:auth"` |

## Swift / iOS Notes

| Task | Command |
|------|---------|
| Find SwiftUI views and state | `ast-index swiftui "ContentView"` |
| Find async functions | `ast-index async-funcs` |
| Storyboard/xib usages | `ast-index storyboard-usages "Name"` |
| Asset usages (xcassets) | `ast-index asset-usages "name"` |

Swift sources live in `iosApp/` — an Xcode wrapper project consuming the
`:FrameIOApp` framework, not a Gradle module.

## Kotlin Multiplatform Notes

- Treat `commonMain`, `commonTest`, and platform source sets (`androidMain`,
  `iosMain`, `desktopMain`, `jvmMain`) as first-class code, not support files.
- When explaining behavior, consider both Kotlin `expect`/`actual` edges and
  Swift/ObjC interop.
- Do not default to Android-only guidance in a KMP repo — a symbol found in
  `commonMain` is shared by Android, Desktop, and iOS.
- `ast-index search` returns hits across all source sets; check which source
  set a hit lives in before concluding a change is platform-specific.