package com.sandeep.a3dmodel.workspace

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ModelWorkspaceStateTest {

    private val asset = ModelLibraryEntry("cube", "Blue Cube", "models/cube_blue.glb")

    @Test
    fun moveModeMovesButDoesNotRotateOrResizeModel() {
        val state = WorkspaceModelState(
            itemId = 1L,
            asset = asset,
            initialX = 10f,
            initialY = 20f,
            initialWidth = 200f,
            initialHeight = 220f
        )

        state.moveBy(dx = 12f, dy = -6f)

        assertEquals(22f, state.x, 0.001f)
        assertEquals(14f, state.y, 0.001f)
        assertEquals(200f, state.width, 0.001f)
        assertEquals(220f, state.height, 0.001f)
        assertEquals(0f, state.rotationX, 0.001f)
        assertEquals(0f, state.rotationY, 0.001f)
        assertEquals(1.4f, state.contentScale, 0.001f)
    }

    @Test
    fun resizeHandleChangesContainerSizeButDoesNotMoveCard() {
        val state = WorkspaceModelState(
            itemId = 1L,
            asset = asset,
            initialX = 10f,
            initialY = 20f,
            initialWidth = 200f,
            initialHeight = 220f
        )

        state.resizeByDelta(deltaWidth = 30f, deltaHeight = 40f)

        assertEquals(10f, state.x, 0.001f)
        assertEquals(20f, state.y, 0.001f)
        assertEquals(230f, state.width, 0.001f)
        assertEquals(260f, state.height, 0.001f)
        assertEquals(0f, state.rotationX, 0.001f)
        assertEquals(0f, state.rotationY, 0.001f)
        assertEquals(1.4f, state.contentScale, 0.001f)
    }

    @Test
    fun interactionModeRotatesAndScalesButDoesNotMoveCard() {
        val state = WorkspaceModelState(
            itemId = 1L,
            asset = asset,
            initialX = 10f,
            initialY = 20f,
            initialWidth = 200f,
            initialHeight = 220f
        )
        state.toggleMode()

        state.applyInteractionGesture(panX = 16f, panY = -8f, zoom = 1.5f)

        assertEquals(10f, state.x, 0.001f)
        assertEquals(20f, state.y, 0.001f)
        assertEquals(200f, state.width, 0.001f)
        assertEquals(220f, state.height, 0.001f)
        assertEquals(5.6f, state.rotationY, 0.001f)
        assertEquals(2.8f, state.rotationX, 0.001f)
        assertEquals(2.1f, state.contentScale, 0.001f)
    }

    @Test
    fun modeToggleIsStable() {
        val state = WorkspaceModelState(
            itemId = 1L,
            asset = asset,
            initialX = 10f,
            initialY = 20f,
            initialWidth = 200f,
            initialHeight = 220f
        )

        assertTrue(state.mode == WorkspaceMode.MoveAndResize)
        state.toggleMode()
        assertTrue(state.mode == WorkspaceMode.Interact)
        state.toggleMode()
        assertFalse(state.mode == WorkspaceMode.Interact)
    }
}
