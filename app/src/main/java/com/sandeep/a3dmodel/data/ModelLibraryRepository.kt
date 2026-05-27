package com.sandeep.a3dmodel.data

interface ModelLibraryRepository {
    fun loadLibrary(): List<ModelLibraryEntry>
}
