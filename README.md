# Milelog

A personal distance tracker for Android — built as a deliberate, incremental **migration from Views/XML to Jetpack Compose**.

Log a trip by entering a start and end place; Milelog records the distance and totals it by week. The app is intentionally small; the point of the repository is to demonstrate modern Android practice (Compose, coroutines/Flow, unidirectional data flow) applied *on top of* a traditional View-based implementation — including the interop seams that real-world migrations actually hit.

> **Status:** active work-in-progress. The foundation is being migrated screen-by-screen from XML to Compose; see the roadmap below for what's done and what's next.

<!-- TODO: add a screenshot or short screen recording once the list + detail screens are running. A GIF of a trip being added is ideal. -->
<!-- ![Milelog list and detail screens](docs/images/milelog-demo.png) -->

## Why this project

Most Compose sample apps are greenfield. This one isn't — it starts as a Views/XML app and is migrated to Compose incrementally, the way an existing production codebase actually gets modernized. That means it exercises the parts a greenfield app never touches:

- Hosting Compose inside an existing View hierarchy with `ComposeView`, and Views inside Compose with `AndroidView`.
- Keeping the `ViewModel`, `StateFlow`, and data layers UI-agnostic so they survive the migration untouched — only the UI layer is rewritten.
- Handling the seams: theme bridging, lifecycle ownership at the boundary, and reactive state collection.

The full reasoning lives in [`docs/ARCHITECTURE.md`](docs/ARCHITECTURE.md).

## Features

- [x] Log a trip (start place, end place, date, distance)
- [x] Trip list
- [x] Trip detail screen
- [ ] Weekly distance total (computed, reactive)
- [ ] Local persistence (Room)
- [ ] Automatic road distance from place names (geocoding + routing API)
- [ ] User accounts with cloud sync

<!-- Update the checkboxes as you complete each item. Keep them honest — an accurate in-progress list reads better than an overclaimed one. -->

## Tech stack

- **Language:** Kotlin
- **UI:** Jetpack Compose (BOM `2026.08.00`) + Material 3, migrated from Android Views/XML
- **Async / state:** Coroutines, Flow, `StateFlow`, `collectAsStateWithLifecycle`
- **Architecture:** MVVM with unidirectional data flow; `ViewModel` + immutable UI state; repository boundary
- **Navigation:** Navigation 3
- **Interop:** `ComposeView` and `AndroidView` for incremental migration

*Planned as the app grows:* Room (persistence), Retrofit (routing/geocoding API), Firebase Auth + Firestore (accounts and sync).

## Getting started

**Requirements**
- Latest stable Android Studio
- JDK 17+ (or simply use the JDK bundled with Android Studio)
- `compileSdk 37`, `minSdk 24`

**Build & run**
```bash
git clone https://github.com/[your-username]/milelog.git
cd milelog
```
Open the project in Android Studio, let Gradle sync, and run on an emulator or device.

## Architecture at a glance

Milelog follows a layered, unidirectional design: composables render UI state and send events up to a `ViewModel`, which exposes state as a `StateFlow` and talks only to a `TripRepository`. The repository hides where data comes from — an in-memory store today, Room and a remote API later — so the UI stays stable as the app grows.

See [`docs/ARCHITECTURE.md`](docs/ARCHITECTURE.md) for the full write-up, including the migration strategy and key trade-offs.

## Roadmap

1. **Foundation (current)** — trip logging, list, detail, weekly total; Views migrated to Compose.
2. **Persistence** — Room as the local source of truth (offline-first).
3. **Real road distance** — geocode both endpoints and call a routing API, replacing manually entered distance.
4. **Accounts + sync** — authentication and cloud sync layered on the offline-first foundation.

## About

<!-- TODO: a couple of honest, professional lines. Example below — edit freely. -->
Built by **[Your Name]** as a portfolio project demonstrating a Views-to-Compose migration on Android. Background: several years of Android SDK development. [LinkedIn / portfolio link]

## License

<!-- TODO: pick one. MIT is a fine default for a portfolio repo. -->
Released under the MIT License. See [`LICENSE`](LICENSE).
