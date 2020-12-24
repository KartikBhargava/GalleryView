package bhargava.kartik.gallerview.ui.gallerviewpackage.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import bhargava.kartik.gallerview.api.Resource
import bhargava.kartik.gallerview.dataclasses.PhotoItem
import bhargava.kartik.gallerview.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class ImageSearchViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    val photos = currentQuery.switchMap { queryString ->
        repository.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    fun searchPhotos(query: String) {
        currentQuery.value = query
    }

    companion object {
        private const val DEFAULT_QUERY = "cats"
    }
}