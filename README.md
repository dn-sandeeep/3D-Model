# 3D Model Viewer

Single-activity Android app for loading, placing, resizing, and interacting with multiple `.glb` models on one screen.

## 3D Library

- `SceneView 2.3.1` on top of `Filament`
- Jetpack Compose for the shell UI

## Why This Stack

- Filament is a GPU-focused renderer built for real-time 3D on Android.
- SceneView gives a Compose-friendly wrapper around Filament and glTF loading.
- The app keeps one shared Filament engine for the screen and reuses loaders instead of creating them per model.
- That keeps the codebase small enough to reason about without building a full custom engine layer.

## Performance Optimizations

- Shared engine and shared loaders:
  - avoids repeated Filament setup
  - reduces memory churn
- Small bundled `.glb` assets:
  - no network fetches
  - no large texture uploads
- Low-poly, unlit, texture-free models:
  - less rendering cost
  - no HDR lighting setup required
- One activity, no fragments, no navigation stack:
  - less lifecycle overhead
- Per-card gesture handling:
  - only the touched model updates
  - avoids global scene recomposition for every move or resize
- Preview bitmap reuse for inactive cards:
  - keeps inactive cards light while preserving a visible thumbnail

## Trade-Offs

- Simpler architecture over a full 3D engine:
  - faster to build
  - easier to maintain
- Performance over realism:
  - the bundled models are intentionally simple, not photorealistic
- One live interactive model at a time:
  - easier state management
  - lower rendering overhead
  - requires switching active cards when you want to interact with another model
- Compose shell UI plus `AndroidView` for rendering:
  - practical integration
  - but it mixes Compose and Android view lifecycles

## With More Time

- Add richer lighting and material support.
- Improve asset loading with persistence and better model metadata.
- Add save/restore for workspace layout and model state.
- Add clearer active-card highlighting and interaction feedback.
- Add optional import support for user-provided `.glb` files.

## Known Limitations

- Only one model is actively interactive at a time.
- Inactive cards are shown as previews, not live 3D surfaces.
- The preview capture path depends on `PixelCopy`, so it only works on API 26+.
- The bundled models are intentionally simple and are not meant to demonstrate high-end visual fidelity.

## Development Tools

- Codex for implementation help, refactoring, and code review support.
- Gradle for building and testing.
- Android Studio for editing, running, and debugging.
