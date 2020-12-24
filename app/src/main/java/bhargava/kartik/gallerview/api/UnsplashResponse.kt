package bhargava.kartik.gallerview.api

import bhargava.kartik.gallerview.dataclasses.UnsplashPhoto

data class UnsplashResponse(
    val results: List<UnsplashPhoto>
)