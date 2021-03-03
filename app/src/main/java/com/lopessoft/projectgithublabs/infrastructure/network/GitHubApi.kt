package com.lopessoft.projectgithublabs.infrastructure

import com.lopessoft.projectgithublabs.domain.PullRequestItem
import com.lopessoft.projectgithublabs.domain.Repository
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {

    @GET("search/repositories")
    fun getRepositories(
        @Query("q") language: String,
        @Query("sort") sort: String,
        @Query("page") page: Int
    ): Observable<Repository>

    @GET("repos/{creator}/{repositoryName}/pulls")
    fun getPullRequests(
        @Path("creator") creator: String,
        @Path("repositoryName") repositoryName: String
    ): Observable<List<PullRequestItem>>

}