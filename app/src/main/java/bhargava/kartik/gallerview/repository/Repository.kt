package bhargava.kartik.gallerview.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import bhargava.kartik.gallerview.api.ApiService
import bhargava.kartik.gallerview.ui.gallerviewpackage.paging.UnsplashPagingSource
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiService: ApiService
) {

     fun getAllPhotos() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UnsplashPagingSource(apiService) }
        ).liveData
}