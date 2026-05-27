package com.sandeep.a3dmodel.domain

object WorkspaceRules {
    const val ROTATION_SENSITIVITY = 0.35f
    const val MIN_CONTAINER_SIZE_PX = 180f
    const val MAX_CONTAINER_SIZE_PX = 980f
    const val MIN_CONTENT_SCALE = 0.35f
    const val MAX_CONTENT_SCALE = 3.5f
    const val INITIAL_CONTAINER_SIZE_PX = 550f

    fun initialCardPosition(
        index: Int,
        viewportWidth: Float,
        viewportHeight: Float,
        cardWidth: Float = INITIAL_CONTAINER_SIZE_PX,
        cardHeight: Float = INITIAL_CONTAINER_SIZE_PX
    ): Pair<Float, Float> {
        val baseX = 28f + (index % 3) * 54f
        val baseY = 148f + (index / 3) * 56f
        val maxX = (viewportWidth - cardWidth).coerceAtLeast(0f)
        val maxY = (viewportHeight - cardHeight).coerceAtLeast(0f)
        return Pair(
            baseX.coerceIn(0f, maxX),
            baseY.coerceIn(0f, maxY)
        )
    }
}
