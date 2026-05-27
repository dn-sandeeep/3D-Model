package com.sandeep.a3dmodel.presentation

import androidx.compose.runtime.mutableStateListOf
import com.sandeep.a3dmodel.data.BundledModelLibraryRepository
import com.sandeep.a3dmodel.data.ModelLibraryEntry
import com.sandeep.a3dmodel.data.ModelLibraryRepository

class ModelWorkspacePresenter(
    private val libraryRepository: ModelLibraryRepository = BundledModelLibraryRepository()
) {
    val library: List<ModelLibraryEntry> = libraryRepository.loadLibrary()
    val items = mutableStateListOf<WorkspaceModelState>()

    private var nextId = 1L

    fun addModel(asset: ModelLibraryEntry, viewportWidth: Float, viewportHeight: Float) {
        val card = createWorkspaceItem(
            itemId = nextId++,
            asset = asset,
            index = items.size,
            viewportWidth = viewportWidth,
            viewportHeight = viewportHeight
        )
        items.add(card)
    }

    fun closeModel(item: WorkspaceModelState) {
        items.remove(item)
    }
}
