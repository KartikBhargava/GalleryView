package bhargava.kartik.gallerview.api

import android.provider.ContactsContract
import bhargava.kartik.gallerview.BuildConfig
import bhargava.kartik.gallerview.dataclasses.PhotoItem
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @Headers("Accept-Version: v1", "Authorization: Client-ID ${BuildConfig.UNSPLASH_ACCESS_KEY}")
    @GET("/photos")
    suspend fun searchPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<PhotoItem>

    @Headers("Accept-Version: v1", "Authorization: Client-ID ${BuildConfig.UNSPLASH_ACCESS_KEY}")
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): UnsplashResponse
}