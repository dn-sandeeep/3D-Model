package com.sandeep.a3dmodel.data

class BundledModelLibraryRepository : ModelLibraryRepository {
    override fun loadLibrary(): List<ModelLibraryEntry> = listOf(
        ModelLibraryEntry("cube", "Blue Cube", "models/cube_blue.glb"),
        ModelLibraryEntry("pyramid", "Amber Pyramid", "models/pyramid_amber.glb"),
        ModelLibraryEntry("tetra", "Mint Tetra", "models/tetra_mint.glb"),
        ModelLibraryEntry("octa", "Sky Octa", "models/octa_sky.glb"),
        ModelLibraryEntry("prism", "Coral Prism", "models/prism_coral.glb")
    )
}
