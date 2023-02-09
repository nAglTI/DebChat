package com.nagl.debchat.di.module

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nagl.debchat.ViewModelFactory
import com.nagl.debchat.di.key.ViewModelKey
import com.nagl.debchat.ui.authorization.AuthorizationViewModel
import com.nagl.debchat.ui.dialogs.DialogsViewModel
import com.nagl.debchat.ui.dialogs.chat.ChatViewModel
import com.nagl.debchat.ui.settings.SettingsViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@InstallIn(SingletonComponent::class)
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @IntoMap
    @Binds
    @ViewModelKey(AuthorizationViewModel::class)
    abstract fun bindAuthorizationViewModel(viewModel: AuthorizationViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(DialogsViewModel::class)
    abstract fun bindDialogsViewModel(viewModel: DialogsViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(ChatViewModel::class)
    abstract fun bindChatViewModel(viewModel: ChatViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSettingsViewModel(viewModel: SettingsViewModel): ViewModel
}