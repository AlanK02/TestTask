package com.alan.testtask.presentation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.alan.testtask.di.ApplicationComponent
import com.alan.testtask.di.DaggerApplicationComponent

class TestTaskApplication : Application() {
    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}

@Composable
fun getApplicationComponent(): ApplicationComponent {
    return (LocalContext.current.applicationContext as TestTaskApplication).component
}