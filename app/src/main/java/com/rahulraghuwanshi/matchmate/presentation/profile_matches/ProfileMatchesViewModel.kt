package com.rahulraghuwanshi.matchmate.presentation.profile_matches

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahulraghuwanshi.matchmate.data.local.MatchMateDao
import com.rahulraghuwanshi.matchmate.presentation.explore.model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileMatchesViewModel @Inject constructor(
    private val mateDao: MatchMateDao
) : ViewModel() {

    private val _userMatchDetailsFlow =
        MutableStateFlow<List<UserData>>(emptyList())
    val userMatchDetailsFlow: StateFlow<List<UserData>>
        get() = _userMatchDetailsFlow.asStateFlow()


    fun fetchMatchedUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            mateDao.getAllMatchedUsers()?.let {
                Log.d("MAJAMA", "fetchMatchedUsers() called >> $it")
                _userMatchDetailsFlow.emit(it)
            }
        }
    }
}