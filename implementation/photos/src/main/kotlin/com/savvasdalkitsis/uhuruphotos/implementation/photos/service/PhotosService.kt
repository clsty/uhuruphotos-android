/*
Copyright 2022 Savvas Dalkitsis

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.savvasdalkitsis.uhuruphotos.implementation.photos.service

import com.savvasdalkitsis.uhuruphotos.api.photos.model.PhotoResult
import com.savvasdalkitsis.uhuruphotos.implementation.photos.service.model.PhotoFavouriteRequest
import com.savvasdalkitsis.uhuruphotos.implementation.photos.service.model.PhotoOperationResponse
import com.savvasdalkitsis.uhuruphotos.implementation.photos.service.model.PhotoResults
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PhotosService {

    @GET("/api/photos/{id}/")
    suspend fun getPhoto(@Path("id") id: String): PhotoResult

    @POST("/api/photosedit/favorite/")
    suspend fun setPhotoFavourite(@Body favouriteRequest: PhotoFavouriteRequest): PhotoOperationResponse

    @DELETE("/api/photos/{id}/")
    suspend fun deletePhoto(@Path("id") id: String): Response<Unit>

    @GET("/api/photos/favorites/")
    suspend fun getFavouritePhotos(): PhotoResults

    @GET("/api/photos/hidden/")
    suspend fun getHiddenPhotos(): PhotoResults
}