package com.example.moviesinfo.data.api

import com.example.moviesinfo.data.models.MoviesResponse
import com.example.moviesinfo.data.models.ReviewsResponse
import com.example.moviesinfo.data.models.TrailersResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

private const val TOKEN = "QF8JSTP-X1M4PRE-PXZAKBA-ZYCMN77"
private const val TOKEN2 = "B4SYYRK-VMHMMS2-G67AVP8-1JD6P9Z"

interface MoviesApiService {

    @Headers("X-API-KEY:$TOKEN")
    @GET("movie")
    suspend fun loadMovies(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 100,
        @Query("rating.kp") rating: String = "6-10",
        @Query("year") year: String = "1990-2024",
        @Query("isSeries") isSeries: Boolean = false,
        @Query("sortField") sortField: String = "votes.kp",
        @Query("sortType") sortType: Int = -1
    ): MoviesResponse

    @Headers("X-API-KEY:$TOKEN")
    @GET("movie/{id}")
    suspend fun loadTrailers(
        @Path("id") movieId: Int
    ): TrailersResponse

    @Headers("X-API-KEY:$TOKEN")
    @GET("review")
    suspend fun loadReviews(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query("sortField") sortField: String = "createdAt",
        @Query("sortType") sortType: Int = 1,
        @Query("movieId") movieId: Int
    ): ReviewsResponse
}