# 3D Model Viewer

Single-activity Android app for loading, placing, resizing, and interacting with multiple `.glb` models on one screen.

## What It Uses

- Kotlin
- Minimum SDK 24
- Jetpack Compose for the shell UI
- SceneView 2.3.1 on top of Filament for 3D rendering

## Why SceneView / Filament

- Filament is a GPU-focused renderer built for real-time performance on Android.
- SceneView gives a Compose-friendly wrapper around Filament and glTF loading without adding a large engine/runtime layer.
- The app keeps one shared Filament engine for the screen and reuses loaders instead of recreating them per model.

## App Behavior

- One activity only.
- Add models from the top bar menu.
- Each model stays on screen as its own draggable, resizable container.
- Every container has:
  - `Interact` toggle
  - `Close` button
- Normal mode:
  - one-finger drag moves the container
  - pinch resizes the container
- Interaction mode:
  - one-finger drag rotates the model
  - pinch scales the 3D content
- The two modes are isolated by design.

## Bundled Assets

The app ships with 5 small procedural `.glb` assets under `app/src/main/assets/models/`.

They are intentionally:

- low-poly
- unlit
- texture-free
- tiny on disk

That keeps startup fast and avoids lighting/texturing cost on weak devices.

## Performance Decisions

- Shared engine and shared loaders:
  - avoids repeated Filament setup
  - reduces memory churn
- Small offline models:
  - no network fetches
  - no large geometry or texture uploads
- Unlit materials:
  - avoids expensive lighting work
  - keeps the models visible without HDR environment setup
- No extra screens, fragments, or navigation:
  - less lifecycle overhead
- Per-card gesture logic:
  - only the touched model updates
  - avoids global scene recomposition for every movement
- Container-resize uses viewport scaling:
  - the 3D content naturally scales with the card size

