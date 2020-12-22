package bhargava.kartik.gallerview.ui.gallerviewpackage.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import bhargava.kartik.gallerview.api.Resource
import bhargava.kartik.gallerview.dataclasses.PhotoItem
import bhargava.kartik.gallerview.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class GalleryViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    fun getAllPhotos() = repository.getAllPhotos().cachedIn(viewModelScope)
}