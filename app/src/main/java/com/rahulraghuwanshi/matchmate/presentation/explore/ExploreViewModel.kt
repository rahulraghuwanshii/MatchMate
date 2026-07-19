package com.rahulraghuwanshi.matchmate.presentation.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rahulraghuwanshi.matchmate.data.local.MatchMateDatabase
import com.rahulraghuwanshi.matchmate.data.network.model.UserDetails
import com.rahulraghuwanshi.matchmate.domain.paging_source.UserDetailsRemoteMediator
import com.rahulraghuwanshi.matchmate.domain.use_case.FetchUserProfileUseCase
import com.rahulraghuwanshi.matchmate.presentation.explore.model.Matched
import com.rahulraghuwanshi.matchmate.presentation.explore.model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val matchMateDatabase: MatchMateDatabase,
    private val fetchUserProfileUseCase: FetchUserProfileUseCase
) : ViewModel() {

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }

    private val _userDetailFlow =
        MutableStateFlow<PagingData<UserDetails>?>(null)
    val userDetailFlow: StateFlow<PagingData<UserDetails>?>
        get() = _userDetailFlow.asStateFlow()

    // Ids the user has already accepted/declined this session - the
    // PagingSource reads this on every load() to exclude them.
    private val swipedIds = mutableSetOf<Int>()

    /**
     * A PagingSource still loads the data; but when the paged data is exhausted, the Paging library triggers the RemoteMediator to load new data from the network source.
     * The RemoteMediator stores the new data in the local database, so an in-memory cache in the ViewModel is unnecessary.
     * Finally, the PagingSource invalidates itself, and the Pager creates a new instance to load the fresh data from the database.
     */
    @OptIn(ExperimentalPagingApi::class)
    fun fetchUserDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            Pager(
                config = PagingConfig(
                    pageSize = NETWORK_PAGE_SIZE,
                    initialLoadSize = NETWORK_PAGE_SIZE,
                    prefetchDistance = 5,
                ),
                pagingSourceFactory = { matchMateDatabase.matchMateDao().getAllUsersData() },
                remoteMediator = UserDetailsRemoteMediator(
                    matchMateDatabase = matchMateDatabase,
                    fetchUserProfileUseCase = fetchUserProfileUseCase
                )
            ).flow.cachedIn(viewModelScope).collectLatest {
                _userDetailFlow.emit(it)
            }
        }
    }

    /**
     * Call this right after your fly-off animation completes. It records the
     * swipe and returns whether the caller should trigger adapter.refresh()
     * (it always should here, but this keeps the call site explicit/readable).
     */
    fun markSwiped(userData: UserData, accepted: Boolean): Boolean {
        viewModelScope.launch {
            val matcher = if (accepted) Matched.LIKE else Matched.DISLIKE
            matchMateDatabase.matchMateDao().insertMatchedUsers(userData.copy(matched = matcher))
        }
        swipedIds.add(userData.id)
        return true
    }
}