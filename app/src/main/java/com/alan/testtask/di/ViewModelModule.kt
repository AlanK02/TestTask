package com.alan.testtask.di

import androidx.lifecycle.ViewModel
import com.alan.testtask.presentation.auth.AuthViewModel
import com.alan.testtask.presentation.profile.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    @Binds
    fun bindAuthViewModel(viewModel: AuthViewModel): ViewModel

    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    @Binds
    fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel
}
