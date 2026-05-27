package com.sandeep.a3dmodel.workspace

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class ModelLibraryEntry(
    val id: String,
    val label: String,
    val assetPath: String
)

enum class WorkspaceMode {
    MoveAndResize,
    Interact
}

class WorkspaceModelState(
    val itemId: Long,
    val asset: ModelLibraryEntry,
    initialX: Float,
    initialY: Float,
    initialWidth: Float,
    initialHeight: Float
) {
    var x by mutableFloatStateOf(initialX)
    var y by mutableFloatStateOf(initialY)
    var width by mutableFloatStateOf(initialWidth)
    var height by mutableFloatStateOf(initialHeight)
    var mode by mutableStateOf(WorkspaceMode.MoveAndResize)
    var rotationX by mutableFloatStateOf(0f)
    var rotationY by mutableFloatStateOf(0f)
    var contentScale by mutableFloatStateOf(1.4f)

    fun toggleMode() {
        mode = if (mode == WorkspaceMode.MoveAndResize) {
            WorkspaceMode.Interact
        } else {
            WorkspaceMode.MoveAndResize
        }
    }

    fun moveBy(dx: Float, dy: Float) {
        x += dx
        y += dy
    }

    fun resizeByDelta(deltaWidth: Float, deltaHeight: Float) {
        width = (width + deltaWidth).coerceIn(MIN_CONTAINER_SIZE_PX, MAX_CONTAINER_SIZE_PX)
        height = (height + deltaHeight).coerceIn(MIN_CONTAINER_SIZE_PX, MAX_CONTAINER_SIZE_PX)
    }

    fun rotateAndZoomBy(panX: Float, panY: Float, zoom: Float) {
        rotationY += panX * ROTATION_SENSITIVITY
        rotationX = (rotationX - panY * ROTATION_SENSITIVITY).coerceIn(-85f, 85f)
        contentScale = (contentScale * zoom).coerceIn(MIN_CONTENT_SCALE, MAX_CONTENT_SCALE)
    }

    fun applyInteractionGesture(panX: Float, panY: Float, zoom: Float) {
        if (mode != WorkspaceMode.Interact) return
        rotateAndZoomBy(panX, panY, zoom)
    }

    companion object {
        const val ROTATION_SENSITIVITY = 0.35f
        const val MIN_CONTAINER_SIZE_PX = 180f
        const val MAX_CONTAINER_SIZE_PX = 980f
        const val MIN_CONTENT_SCALE = 0.35f
        const val MAX_CONTENT_SCALE = 3.5f
    }
}

fun defaultModelLibrary(): List<ModelLibraryEntry> = listOf(
    ModelLibraryEntry("cube", "Blue Cube", "models/cube_blue.glb"),
    ModelLibraryEntry("pyramid", "Amber Pyramid", "models/pyramid_amber.glb"),
    ModelLibraryEntry("tetra", "Mint Tetra", "models/tetra_mint.glb"),
    ModelLibraryEntry("octa", "Sky Octa", "models/octa_sky.glb"),
    ModelLibraryEntry("prism", "Coral Prism", "models/prism_coral.glb")
)

fun createWorkspaceItem(
    itemId: Long,
    asset: ModelLibraryEntry,
    index: Int
): WorkspaceModelState {
    val baseX = 28f + (index % 3) * 54f
    val baseY = 148f + (index / 3) * 56f
    return WorkspaceModelState(
        itemId = itemId,
        asset = asset,
        initialX = baseX,
        initialY = baseY,
        initialWidth = 550f,
        initialHeight = 550f
    )
}
