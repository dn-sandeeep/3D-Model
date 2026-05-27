package com.sandeep.a3dmodel.domain

import org.junit.Assert.assertEquals
import org.junit.Test

class WorkspaceRulesTest {

    @Test
    fun initialCardPositionIsClampedToViewport() {
        val (x, y) = WorkspaceRules.initialCardPosition(
            index = 12,
            viewportWidth = 400f,
            viewportHeight = 300f,
            cardWidth = 550f,
            cardHeight = 550f
        )

        assertEquals(0f, x, 0.001f)
        assertEquals(0f, y, 0.001f)
    }
}
