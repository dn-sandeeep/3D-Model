package com.sandeep.a3dmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sandeep.a3dmodel.ui.theme.A3DModelTheme
import com.sandeep.a3dmodel.workspace.ModelWorkspaceScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            A3DModelTheme {
                ModelWorkspaceScreen()
            }
        }
    }
}
