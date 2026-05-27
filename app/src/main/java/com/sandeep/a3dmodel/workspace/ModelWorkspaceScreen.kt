package com.sandeep.a3dmodel.workspace

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.filament.Engine
import com.google.android.filament.Renderer
import com.google.android.filament.Scene
import com.google.android.filament.View
import dev.romainguy.kotlin.math.Float3
import io.github.sceneview.SceneView
import io.github.sceneview.loaders.EnvironmentLoader
import io.github.sceneview.loaders.MaterialLoader
import io.github.sceneview.loaders.ModelLoader
import io.github.sceneview.math.Position
import io.github.sceneview.node.ModelNode
import io.github.sceneview.rememberCameraNode
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberEnvironmentLoader
import io.github.sceneview.rememberMaterialLoader
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberRenderer
import io.github.sceneview.rememberScene
import io.github.sceneview.rememberView

@Composable
fun ModelWorkspaceScreen() {
    val context = LocalContext.current
    val library = remember { defaultModelLibrary() }
    val engine = rememberEngine()
    val modelLoader = rememberModelLoader(engine, context)
    val materialLoader = rememberMaterialLoader(engine, context)
    val environmentLoader = rememberEnvironmentLoader(engine, context)
    val items = remember { mutableStateListOf<WorkspaceModelState>() }
    var nextId by remember { mutableLongStateOf(1L) }
    var addMenuExpanded by remember { mutableStateOf(false) }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF08111E),
                        Color(0xFF122235),
                        Color(0xFF09121F)
                    )
                )
            )
    ) {
        val density = LocalDensity.current
        val viewportWidth = with(density) { maxWidth.toPx() }
        val viewportHeight = with(density) { maxHeight.toPx() }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Surface(
                color = Color(0xAA0F1825),
                contentColor = MaterialTheme.colorScheme.onSurface,
                shape = MaterialTheme.shapes.extraLarge,
                tonalElevation = 2.dp,
                shadowElevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "3D Model Workspace",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "${items.size} loaded | drag, resize, interact, close",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                        )
                    }

                    Box {
                        FilledTonalButton(onClick = { addMenuExpanded = true }) {
                            Text("Add model")
                        }
                        DropdownMenu(
                            expanded = addMenuExpanded,
                            onDismissRequest = { addMenuExpanded = false },
                            modifier = Modifier.background(Color(0xFF132235))
                        ) {
                            library.forEach { entry ->
                                DropdownMenuItem(
                                    text = { Text(entry.label) },
                                    onClick = {
                                        addMenuExpanded = false
                                        val card = createWorkspaceItem(
                                            itemId = nextId++,
                                            asset = entry,
                                            index = items.size
                                        )
                                        val maxX = (viewportWidth - card.width).coerceAtLeast(0f)
                                        val maxY = (viewportHeight - card.height).coerceAtLeast(0f)
                                        card.x = (viewportWidth * 0.08f + items.size * 26f)
                                            .coerceIn(0f, maxX)
                                        card.y = (viewportHeight * 0.12f + items.size * 22f)
                                            .coerceIn(0f, maxY)
                                        items.add(card)
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.extraLarge)
                    .border(
                        width = 1.dp,
                        color = Color.White.copy(alpha = 0.08f),
                        shape = MaterialTheme.shapes.extraLarge
                    )
                    .background(Color(0xFF070D14))
            ) {
                items.forEach { item ->
                    ModelWorkspaceCard(
                        state = item,
                        engine = engine,
                        modelLoader = modelLoader,
                        materialLoader = materialLoader,
                        environmentLoader = environmentLoader,
                        onClose = { items.remove(item) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ModelWorkspaceCard(
    state: WorkspaceModelState,
    engine: Engine,
    modelLoader: ModelLoader,
    materialLoader: MaterialLoader,
    environmentLoader: EnvironmentLoader,
    onClose: () -> Unit
) {
    val density = LocalDensity.current
    val widthDp = with(density) { state.width.toDp() }
    val heightDp = with(density) { state.height.toDp() }
    val accent = accentForItem(state.itemId)
    val accentGlow = accent.copy(alpha = 0.24f)
    val cardTint = if (accent.luminance() > .5f) {
        Color(0xFF10141B).copy(alpha = 0.95f)
    } else {
        Color(0xFF121B29).copy(alpha = 0.96f)
    }
    val cameraNode = rememberCameraNode(engine) {
        position = Position(z = 3.0f)
    }
    val scene = rememberScene(engine)
    val view = rememberView(engine)
    val renderer = rememberRenderer(engine)
    val modelInstance = remember(state.asset.assetPath) {
        runCatching { modelLoader.createModelInstance(state.asset.assetPath) }.getOrNull()
    }
    val modelNode = remember(modelInstance) {
        modelInstance?.let {
            ModelNode(
                modelInstance = it,
                autoAnimate = false,
                scaleToUnits = 1.0f,
                centerOrigin = Float3(0f, 0f, 0f)
            )
        }
    }

    Box(
        modifier = Modifier
            .offset { IntOffset(state.x.toInt(), state.y.toInt()) }
            .size(widthDp, heightDp)
            .graphicsLayer { shadowElevation = 6f }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = MaterialTheme.shapes.large,
            color = cardTint,
            tonalElevation = 4.dp,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = 1.5.dp,
                        color = accent.copy(alpha = 0.75f),
                        shape = MaterialTheme.shapes.large
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(MaterialTheme.shapes.small)
                            .background(accent)
                    )
                    Text(
                        text = state.asset.label,
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White.copy(alpha = 0.92f)
                    )
                    Text(
                        text = if (state.mode == WorkspaceMode.Interact) "Interact" else "Move",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.72f)
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 10.dp)
                        .clip(MaterialTheme.shapes.large)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    accentGlow,
                                    Color(0xFF05080C)
                                )
                            )
                        )
                ) {
                    AndroidView(
                        modifier = Modifier.fillMaxSize(),
                        factory = { context ->
                            SceneView(
                                context,
                                null,
                                0,
                                0,
                                engine,
                                modelLoader,
                                materialLoader,
                                environmentLoader,
                                scene,
                                view,
                                renderer,
                                cameraNode
                            )
                        },
                        update = { sceneView ->
                            sceneView.cameraManipulator = null
                            sceneView.onTouchEvent = { _, _ -> false }
                            sceneView.clearChildNodes()
                            if (modelNode != null) {
                                sceneView.addChildNode(modelNode)
                            }
                        }
                    )

                    if (modelNode != null) {
                        LaunchedEffect(state.rotationX, state.rotationY, state.contentScale) {
                            modelNode.rotation = Float3(state.rotationX, state.rotationY, 0f)
                            modelNode.scale = Float3(
                                state.contentScale,
                                state.contentScale,
                                state.contentScale
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp)
                            .then(
                                if (state.mode == WorkspaceMode.MoveAndResize) {
                                    Modifier.pointerInput(state.mode) {
                                        detectDragGestures { change, dragAmount ->
                                            state.moveBy(dragAmount.x, dragAmount.y)
                                        }
                                    }
                                } else {
                                    Modifier.pointerInput(state.mode) {
                                        detectTransformGestures { _, pan, zoom, _ ->
                                            state.applyInteractionGesture(pan.x, pan.y, zoom)
                                        }
                                    }
                                }
                            )
                    )

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 8.dp, bottom = 8.dp)
                            .size(28.dp)
                            .clip(MaterialTheme.shapes.small)
                            .background(
                                if (state.mode == WorkspaceMode.MoveAndResize) {
                                    accent.copy(alpha = 0.95f)
                                } else {
                                    Color.White.copy(alpha = 0.28f)
                                }
                            )
                            .border(
                                width = 1.dp,
                                color = Color.White.copy(alpha = 0.5f),
                                shape = MaterialTheme.shapes.small
                            )
                            .then(
                                if (state.mode == WorkspaceMode.MoveAndResize) {
                                    Modifier.pointerInput(state.mode) {
                                        detectDragGestures { change, dragAmount ->
                                            val delta = (dragAmount.x + dragAmount.y) / 2f
                                            state.resizeByDelta(delta, delta)
                                        }
                                    }
                                } else {
                                    Modifier
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "↘",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.95f),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = state::toggleMode,
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(
                            horizontal = 14.dp,
                            vertical = 8.dp
                        ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (state.mode == WorkspaceMode.Interact) {
                                Color(0xFF2C6E49)
                            } else {
                                Color(0xFF31587D)
                            }
                        )
                    ) {
                        Text(
                            text = if (state.mode == WorkspaceMode.Interact) "Exit interact" else "Interact",
                            fontSize = 12.sp,
                            maxLines = 1,
                            softWrap = false
                        )
                    }
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = onClose,
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(
                            horizontal = 14.dp,
                            vertical = 8.dp
                        ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF9B3040)
                        )
                    ) {
                        Text("Close", fontSize = 12.sp, maxLines = 1, softWrap = false)
                    }
                }
            }
        }
    }
}

private fun accentForItem(itemId: Long): Color {
    val palette = listOf(
        Color(0xFF75B7FF),
        Color(0xFFE8B458),
        Color(0xFF7CD6B3),
        Color(0xFFF08A74),
        Color(0xFFB38BFF)
    )
    return palette[(itemId.toInt() - 1).floorMod(palette.size)]
}

private fun Int.floorMod(modulus: Int): Int {
    val result = this % modulus
    return if (result < 0) result + modulus else result
}
