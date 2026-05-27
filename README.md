# 3D Model Viewer

Single-activity Android app for loading multiple `.glb` models onto one canvas, moving them, resizing their containers, and switching each model into an interaction mode.

## Architecture

The code is split into three simple layers:
- **Data**: bundled model library and asset metadata
- **Domain**: workspace rules, mode constants, placement math, and size limits
- **Presentation**: the workspace presenter/state holder and the Compose screen

That split keeps the UI thin, keeps workspace rules testable, and makes the 3D rendering setup easier to reason about.

## 3D Library

The app uses **SceneView 2.3.1**, which runs on top of **Filament**.

Why this stack:
- Filament is a high-performance rendering engine built for real-time Android graphics.
- SceneView gives a Compose-friendly API for loading glTF/GLB assets without building a large custom rendering layer.
- The app keeps one shared engine and shared loaders at screen scope, which reduces setup work and avoids recreating rendering infrastructure per model.

## Performance Choices

- **Shared engine/loaders**: one Filament engine, one model loader, one material loader, one environment loader.
- **Small bundled assets**: five tiny offline `.glb` files are shipped with the app, so there is no network fetch or large asset download.
- **Unlit transparent models**: the sample models use simple materials instead of expensive lighting and texture work.
- **Single-screen UI**: no fragments, no navigation, and no extra screens.
- **Per-card interaction**: each model card handles its own gestures so only the affected item updates.
- **Explicit resize handle**: container resizing is done with a visible handle instead of pinch, which avoids conflict with the 3D view.

## Trade-offs

- The sample models are intentionally simple, so they are readable and cheap to render, but they are not visually rich.
- The app favors stable performance over advanced graphics features like shadows, PBR materials, or heavy post-processing.
- SceneView makes integration much faster, but it adds an abstraction layer instead of using raw Filament directly.
- The UI is optimized for clarity and gesture separation, not for a highly polished editor-style experience.

## What I Would Improve With More Time

- Add stronger profiling and device-specific tuning for very low-end phones.
- Reuse more per-model scene resources to reduce churn when many items are added and removed.
- Add a clearer resize affordance or optional resize overlay for smaller screens.
- Improve asset variety with more sample models and better visual differentiation.
- Add instrumentation/UI tests for drag, resize, interact, and close flows.

## Known Bugs / Limitations

- Gesture behavior can still feel device-dependent because the app embeds a 3D surface inside a Compose card.
- The resize handle is hidden in `Interact` mode, so resizing is only available in normal mode.
- The bundled models are deliberately simple and translucent, so they are easier to run but less realistic.
- I verified debug builds and unit tests locally, but I did not run a full performance profile on target low-end hardware in this workspace.

## Verification

```bash
./gradlew.bat :app:assembleDebug
./gradlew.bat testDebugUnitTest
```
