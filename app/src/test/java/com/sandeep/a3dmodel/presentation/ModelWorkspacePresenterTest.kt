package com.sandeep.a3dmodel.presentation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Test
import com.sandeep.a3dmodel.data.BundledModelLibraryRepository

class ModelWorkspacePresenterTest {

    private val library = BundledModelLibraryRepository().loadLibrary()

    @Test
    fun addingSameModelTwiceBringsExistingItemToFrontWithoutDuplicating() {
        val presenter = ModelWorkspacePresenter(BundledModelLibraryRepository())
        val first = library[0]
        val second = library[1]

        presenter.addModel(first, viewportWidth = 1000f, viewportHeight = 800f)
        presenter.addModel(second, viewportWidth = 1000f, viewportHeight = 800f)
        presenter.addModel(first, viewportWidth = 1000f, viewportHeight = 800f)

        assertEquals(2, presenter.items.size)
        assertEquals(first.id, presenter.items[0].asset.id)
        assertEquals(second.id, presenter.items[1].asset.id)
        assertTrue(presenter.items[0].zIndex > presenter.items[1].zIndex)
        assertEquals(presenter.items[0].itemId, presenter.activeItemId)
    }

    @Test
    fun activatingExistingItemBringsItToFrontWithoutDuplicating() {
        val presenter = ModelWorkspacePresenter(BundledModelLibraryRepository())
        val first = library[0]
        val second = library[1]

        presenter.addModel(first, viewportWidth = 1000f, viewportHeight = 800f)
        presenter.addModel(second, viewportWidth = 1000f, viewportHeight = 800f)

        presenter.activateItem(presenter.items[0])

        assertEquals(2, presenter.items.size)
        assertEquals(first.id, presenter.items[0].asset.id)
        assertEquals(second.id, presenter.items[1].asset.id)
        assertTrue(presenter.items[0].zIndex > presenter.items[1].zIndex)
        assertEquals(presenter.items[0].itemId, presenter.activeItemId)
    }

    @Test
    fun closingModelAllowsItToBeAddedAgainAsANewItem() {
        val presenter = ModelWorkspacePresenter(BundledModelLibraryRepository())
        val asset = library[0]

        presenter.addModel(asset, viewportWidth = 1000f, viewportHeight = 800f)
        val originalItem = presenter.items.single()

        presenter.closeModel(originalItem)
        presenter.addModel(asset, viewportWidth = 1000f, viewportHeight = 800f)

        assertEquals(1, presenter.items.size)
        assertEquals(asset.id, presenter.items.single().asset.id)
        assertSame(asset, presenter.items.single().asset)
        assertEquals(presenter.items.single().itemId, presenter.activeItemId)
    }
}
