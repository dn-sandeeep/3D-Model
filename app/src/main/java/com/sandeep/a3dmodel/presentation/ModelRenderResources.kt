package com.sandeep.a3dmodel.presentation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberEnvironmentLoader
import io.github.sceneview.rememberMaterialLoader
import io.github.sceneview.rememberModelLoader

class ModelRenderResources(
    val engine: com.google.android.filament.Engine,
    val modelLoader: io.github.sceneview.loaders.ModelLoader,
    val materialLoader: io.github.sceneview.loaders.MaterialLoader,
    val environmentLoader: io.github.sceneview.loaders.EnvironmentLoader
)

@Composable
fun rememberModelRenderResources(context: Context): ModelRenderResources {
    val engine = rememberEngine()
    val modelLoader = rememberModelLoader(engine, context)
    val materialLoader = rememberMaterialLoader(engine, context)
    val environmentLoader = rememberEnvironmentLoader(engine, context)
    return remember(engine, modelLoader, materialLoader, environmentLoader) {
        ModelRenderResources(
            engine = engine,
            modelLoader = modelLoader,
            materialLoader = materialLoader,
            environmentLoader = environmentLoader
        )
    }
}
