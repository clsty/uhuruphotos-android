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
package com.savvasdalkitsis.uhuruphotos.albums.module

import com.savvasdalkitsis.uhuruphotos.albums.service.AlbumsService
import com.savvasdalkitsis.uhuruphotos.db.albums.AlbumsQueries
import com.savvasdalkitsis.uhuruphotos.db.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
class AlbumsModule {

    @Provides
    fun albumsService(
        retrofit: Retrofit,
    ): AlbumsService = retrofit.create(AlbumsService::class.java)
}