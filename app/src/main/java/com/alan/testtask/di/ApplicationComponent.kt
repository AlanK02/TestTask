package com.alan.testtask.di

import android.content.Context
import com.alan.testtask.presentation.ViewModelFactory
import dagger.BindsInstance
import dagger.Component

@Component(modules = [DataModule::class, ViewModelModule::class])
@ApplicationScope
interface ApplicationComponent {
    fun getViewModelFactory(): ViewModelFactory

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }
}
