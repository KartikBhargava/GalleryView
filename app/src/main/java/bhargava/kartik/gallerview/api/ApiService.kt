package bhargava.kartik.gallerview.api

import bhargava.kartik.gallerview.dataclasses.PhotoItem
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET

interface ApiService {

    @GET("/photos")
    suspend fun getAllPhotos(@Body requestBody: RequestBody) : List<PhotoItem>

}