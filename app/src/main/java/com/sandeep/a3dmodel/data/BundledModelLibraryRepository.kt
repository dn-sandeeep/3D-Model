package com.sandeep.a3dmodel.data

class BundledModelLibraryRepository : ModelLibraryRepository {
    override fun loadLibrary(): List<ModelLibraryEntry> = listOf(
        ModelLibraryEntry("cube", "Cube", "models/cube_blue.glb"),
        ModelLibraryEntry("pyramid", "Pyramid", "models/pyramid_amber.glb"),
        ModelLibraryEntry("tetra", "Tetra", "models/tetra_mint.glb"),
        ModelLibraryEntry("octa", "Octa", "models/octa_sky.glb"),
        ModelLibraryEntry("prism", "Prism", "models/prism_coral.glb")
    )
}
