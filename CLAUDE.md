# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this repo actually is right now

**Read this before touching `README.md` or `docs/ARCHITECTURE.md`.** Those two files describe Milelog's *target* architecture (Compose, `ViewModel` + `StateFlow`, a `TripRepository`, Navigation 3, an XML→Compose migration story). None of that exists in code yet. The current source tree is the unmodified output of Android Studio's "Empty Views Activity" template:

- `MainActivity.kt` just calls `enableEdgeToEdge()` and inflates `activity_main.xml`.
- `app/build.gradle.kts` has no Compose, no ViewModel/lifecycle, no coroutines, no Room, no Navigation dependencies — only `appcompat`, `material`, `constraintlayout`, `core-ktx`, `activity-ktx`.
- There is no `Trip` model, no repository, no `ui`/`data` packages.

When asked to build a feature, treat the README/ARCHITECTURE.md as the design doc to implement *toward*, not a description of what's already there — check `app/build.gradle.kts` and `gradle/libs.versions.toml` for what's actually on the classpath before assuming a dependency (e.g. Compose BOM, Navigation 3) is available.

There's also a local, gitignored planning doc at `docs/personal/milelog-one-week-kickoff.md` (see `.gitignore`) that lays out a day-by-day build order: Day 0 blank Views app (current state) → Day 1 XML trip list/add/detail wired to a `ViewModel` exposing `StateFlow` → Day 2 derive a weekly-total `StateFlow` → Day 3 introduce `ComposeView` for one screen → Day 4 migrate the list to `LazyColumn` → Day 5 migrate detail/add-trip + navigation → Day 6 theming/state cleanup → Day 7 docs. If the user's request matches one of these steps, follow that sequencing rather than jumping ahead (e.g. don't reach for Room, a routing API, or accounts — those are explicitly deferred until after the Compose migration is done).

## Commands

Build and run from the project root using the Gradle wrapper.

```bash
./gradlew assembleDebug          # build the debug APK
./gradlew installDebug           # build and install on a connected device/emulator
./gradlew testDebugUnitTest      # run JVM unit tests (app/src/test)
./gradlew connectedDebugAndroidTest  # run instrumented tests (app/src/androidTest), needs a device/emulator
./gradlew lint                   # Android Lint
```

Run a single JVM test class or method with `--tests`:

```bash
./gradlew testDebugUnitTest --tests "com.example.milelog.ExampleUnitTest"
./gradlew testDebugUnitTest --tests "com.example.milelog.ExampleUnitTest.addition_isCorrect"
```

There is no `gradle.properties`-configured CI/lint-check task beyond the standard `lint`/`test`/`check` set; `./gradlew check` runs lint + unit tests together.

## Project structure

- Single-module app: `settings.gradle.kts` includes only `:app`.
- `namespace`/`applicationId`: `com.example.milelog`.
- `compileSdk = 37`, `minSdk = 24`, `targetSdk = 37`; Java/Kotlin compiled at source/target compatibility 11.
- Version catalog for dependencies lives in `gradle/libs.versions.toml` — add new dependencies there, not as inline coordinates in `app/build.gradle.kts`.
- Release build has `optimization { enable = false }` in `app/build.gradle.kts` (R8/minify off).
