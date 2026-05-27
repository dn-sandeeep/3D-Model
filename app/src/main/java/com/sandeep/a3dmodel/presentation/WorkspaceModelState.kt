package com.sandeep.a3dmodel.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sandeep.a3dmodel.data.ModelLibraryEntry
import com.sandeep.a3dmodel.domain.WorkspaceMode
import com.sandeep.a3dmodel.domain.WorkspaceRules

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
        width = (width + deltaWidth).coerceIn(
            WorkspaceRules.MIN_CONTAINER_SIZE_PX,
            WorkspaceRules.MAX_CONTAINER_SIZE_PX
        )
        height = (height + deltaHeight).coerceIn(
            WorkspaceRules.MIN_CONTAINER_SIZE_PX,
            WorkspaceRules.MAX_CONTAINER_SIZE_PX
        )
    }

    fun rotateAndZoomBy(panX: Float, panY: Float, zoom: Float) {
        rotationY += panX * WorkspaceRules.ROTATION_SENSITIVITY
        rotationX = (rotationX - panY * WorkspaceRules.ROTATION_SENSITIVITY).coerceIn(-85f, 85f)
        contentScale = (contentScale * zoom).coerceIn(
            WorkspaceRules.MIN_CONTENT_SCALE,
            WorkspaceRules.MAX_CONTENT_SCALE
        )
    }

    fun applyInteractionGesture(panX: Float, panY: Float, zoom: Float) {
        if (mode != WorkspaceMode.Interact) return
        rotateAndZoomBy(panX, panY, zoom)
    }
}

fun createWorkspaceItem(
    itemId: Long,
    asset: ModelLibraryEntry,
    index: Int,
    viewportWidth: Float,
    viewportHeight: Float
): WorkspaceModelState {
    val (x, y) = WorkspaceRules.initialCardPosition(
        index = index,
        viewportWidth = viewportWidth,
        viewportHeight = viewportHeight
    )
    return WorkspaceModelState(
        itemId = itemId,
        asset = asset,
        initialX = x,
        initialY = y,
        initialWidth = WorkspaceRules.INITIAL_CONTAINER_SIZE_PX,
        initialHeight = WorkspaceRules.INITIAL_CONTAINER_SIZE_PX
    )
}
