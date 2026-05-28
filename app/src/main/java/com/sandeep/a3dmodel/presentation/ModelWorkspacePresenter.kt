package com.sandeep.a3dmodel.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.sandeep.a3dmodel.data.BundledModelLibraryRepository
import com.sandeep.a3dmodel.data.ModelLibraryEntry
import com.sandeep.a3dmodel.data.ModelLibraryRepository

class ModelWorkspacePresenter(
    private val libraryRepository: ModelLibraryRepository = BundledModelLibraryRepository()
) {
    val library: List<ModelLibraryEntry> = libraryRepository.loadLibrary()
    val items = mutableStateListOf<WorkspaceModelState>()
    var activeItemId by mutableStateOf<Long?>(null)

    private var nextId = 1L
    private var nextZIndex = 0f

    fun addModel(asset: ModelLibraryEntry, viewportWidth: Float, viewportHeight: Float) {
        val existingIndex = items.indexOfFirst { it.asset.id == asset.id }
        if (existingIndex >= 0) {
            val item = items[existingIndex]
            item.zIndex = nextZIndex++
            activeItemId = item.itemId
            return
        }

        val card = createWorkspaceItem(
            itemId = nextId++,
            asset = asset,
            index = items.size,
            zIndex = nextZIndex++,
            viewportWidth = viewportWidth,
            viewportHeight = viewportHeight
        )
        items.add(card)
        activeItemId = card.itemId
    }

    fun closeModel(item: WorkspaceModelState) {
        items.remove(item)
        if (activeItemId == item.itemId) {
            activeItemId = items.maxByOrNull { it.zIndex }?.itemId
        }
    }
}
