package bhargava.kartik.gallerview.ui.gallerviewpackage.paging

import androidx.paging.PagingSource
import bhargava.kartik.gallerview.api.ApiService
import bhargava.kartik.gallerview.dataclasses.PhotoItem
import retrofit2.HttpException
import java.io.IOException

private const val UNSPLASH_STARTING_PAGE_INDEX = 1

class UnsplashPagingSource(
    private val unsplashApi: ApiService,
) : PagingSource<Int, PhotoItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoItem> {
        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX

        return try {
            val response = unsplashApi.searchPhotos(position, params.loadSize)
            val photos = response

            LoadResult.Page(
                data = photos,
                prevKey = if (position == UNSPLASH_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (photos.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}