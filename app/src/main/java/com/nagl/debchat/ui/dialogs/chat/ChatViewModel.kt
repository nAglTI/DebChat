package com.nagl.debchat.ui.dialogs.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nagl.debchat.data.model.Dialog
import com.nagl.debchat.data.model.Message
import com.nagl.debchat.data.model.net.NetMessage
import com.nagl.debchat.data.model.UserToken
import com.nagl.debchat.data.source.repository.DataStoreRepository
import com.nagl.debchat.data.source.repository.IDataRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChatViewModel @Inject constructor(
    private val dataRepository: IDataRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private lateinit var dialog: Dialog

    private val _userToken = MutableLiveData<UserToken?>()
    var userToken: LiveData<UserToken?> = _userToken

    private val _messages = MutableLiveData<List<Message>?>()
    var messages: LiveData<List<Message>?> = _messages

    private val _isLoading = MutableLiveData(false)
    var isLoading: LiveData<Boolean> = _isLoading

    fun setDialog(argsDialog: Dialog) {
        dialog = argsDialog
    }

    fun getCacheMessages(chatId: Long) {
        _isLoading.value = true
        viewModelScope.launch {
            when (val result = dataRepository.getCacheMessages(chatId)) {
                is com.nagl.debchat.utils.Result.Success -> {
                    if (result.data != null) {
                        _messages.value = result.data
                    } else {
                        getNetMessages(chatId, 0)
                    }
                }
                else -> {}
            }
        }
    }

    fun getNetMessages(chatId: Long, startMessageId: Long) {
        _isLoading.value = true
        viewModelScope.launch {
            when (val result = dataRepository.getNetMessages(userToken.value!!.token, chatId, startMessageId)) {
                is com.nagl.debchat.utils.Result.Success -> {
                    if (result.data != null) {
                        _messages.value = result.data
                    } else {
                        _messages.value = null
                    }
                }
                is com.nagl.debchat.utils.Result.Error -> {

                }
                is com.nagl.debchat.utils.Result.Loading -> _isLoading.postValue(true)
            }
        }
    }
// TODO: delete messages above db limit
}