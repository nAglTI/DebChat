package com.nagl.debchat.ui.dialogs.chat

import androidx.lifecycle.*
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
    private lateinit var userToken: UserToken
    //var userToken: LiveData<UserToken?> = _userToken

    private val _newMessages = MutableLiveData<List<Message>?>()
    var newMessages: LiveData<List<Message>?> = _newMessages
    val messagesList = mutableListOf<Message>()

    private val _isLoading = MutableLiveData(false)
    var isLoading: LiveData<Boolean> = _isLoading

    fun setDialog(argsDialog: Dialog) {
        dialog = argsDialog
    }

    fun getUserToken() {
        viewModelScope.launch {
            dataStoreRepository.token.collect {
                userToken = UserToken(it)
            }
        }
    }

    fun getCacheMessages(chatId: Long) {
        _isLoading.value = true
        viewModelScope.launch {
            when (val result = dataRepository.getCacheMessages(chatId)) {
                is com.nagl.debchat.utils.Result.Success -> {
                    if (result.data != null) {
                        _newMessages.value = result.data
                        getNetMessages(chatId, result.data.last().id + 1)
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
            when (val result =
                dataRepository.getNetMessages(userToken.token, chatId, startMessageId)) {
                is com.nagl.debchat.utils.Result.Success -> {
                    if (result.data != null) {
                        _newMessages.value = result.data
                        addCachedMessages(result.data)
                    } else {
                        _newMessages.value = null
                    }
                }
                is com.nagl.debchat.utils.Result.Error -> {

                }
                is com.nagl.debchat.utils.Result.Loading -> _isLoading.postValue(true)
            }
        }
    }

    fun sendMessage(text: String) {
        _isLoading.value = true
        viewModelScope.launch {
            when (val result =
                dataRepository.sendMessage(userToken.token, Message(text = text), dialog.chatId)) {
                is com.nagl.debchat.utils.Result.Success -> {
                    if (result.data != null) {
                        _newMessages.value = listOf(result.data)
                        addCachedMessages(listOf(result.data))
                    } else {
                        _newMessages.value = null
                    }
                }
                is com.nagl.debchat.utils.Result.Error -> {

                }
                is com.nagl.debchat.utils.Result.Loading -> _isLoading.postValue(true)
            }
        }
    }

    private fun addCachedMessages(list: List<Message>) {
        viewModelScope.launch {
            dataRepository.insertMessagesToCache(dialog.chatId, list)
        }
    }
// TODO: delete messages above db limit
}