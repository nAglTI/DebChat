package com.nagl.debchat.ui.authorization

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nagl.debchat.data.model.net.UserRequest
import com.nagl.debchat.data.source.repository.DataStoreRepository
import com.nagl.debchat.data.source.repository.IDataRepository
import com.nagl.debchat.utils.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthorizationViewModel @Inject constructor(
    private val dataRepository: IDataRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isAuthPassed = MutableLiveData<Boolean>()
    val isAuthPassed: LiveData<Boolean> = _isAuthPassed

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _isAuth = MutableLiveData<Boolean>()
    val isAuth: LiveData<Boolean> = _isAuth

//    private val _userLogin = MutableLiveData<String>()
//    private val _userPassword = MutableLiveData<String>()

    fun isUserAuth() {
        _isLoading.value = true
        viewModelScope.launch {
            dataStoreRepository.isUserAuth.collect {
                _isAuth.postValue(it)
                if (!it) {
                    _isLoading.postValue(false)
                }
            }
        }
    }

    fun getUserToken(requestData: UserRequest) {
        _isLoading.value = true
        _isError.value = false
        viewModelScope.launch {
            when (val result = dataRepository.getUserToken(requestData)) {
                is Result.Success -> {
                    if (result.data != null) {
                        dataStoreRepository.apply {
                            saveUserToken(result.data.token)
                            saveUserAuth(true)
                        }
                        _isAuthPassed.value = true
                    } else {
                        _isAuthPassed.value = false
                        _isLoading.value = false
                    }
                }
                is Result.Error -> {
                    _isLoading.value = false
                    _isAuthPassed.value = false
                    _isError.value = true
                }
                is Result.Loading ->
                    _isLoading.postValue(true)
            }
        }
    }
}