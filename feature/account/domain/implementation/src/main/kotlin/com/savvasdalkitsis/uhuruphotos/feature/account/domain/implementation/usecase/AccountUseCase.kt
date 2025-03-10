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
package com.savvasdalkitsis.uhuruphotos.feature.account.domain.implementation.usecase

import androidx.media3.datasource.cache.CacheDataSource
import com.savvasdalkitsis.uhuruphotos.feature.account.domain.api.usecase.AccountUseCase
import com.savvasdalkitsis.uhuruphotos.feature.db.domain.api.Database
import com.savvasdalkitsis.uhuruphotos.feature.db.domain.api.extensions.async
import com.savvasdalkitsis.uhuruphotos.foundation.image.api.cache.ImageCacheUseCase
import com.savvasdalkitsis.uhuruphotos.foundation.video.api.evictAll
import com.savvasdalkitsis.uhuruphotos.foundation.worker.api.usecase.WorkScheduleUseCase
import se.ansman.dagger.auto.AutoBind
import javax.inject.Inject

@AutoBind
class AccountUseCase @Inject constructor(
    private val db: Database,
    private val imageCacheUseCase: ImageCacheUseCase,
    private val videoCache: CacheDataSource.Factory,
    private val workScheduleUseCase: WorkScheduleUseCase,
) : AccountUseCase {

    override suspend fun logOut() {
        async {
            workScheduleUseCase.cancelAllScheduledWork()
            with(db) {
                remoteMediaCollectionsQueries.clearAll()
                autoAlbumQueries.clearAll()
                autoAlbumPeopleQueries.clearAll()
                autoAlbumPhotosQueries.clearAll()
                autoAlbumsQueries.clearAll()
                peopleQueries.clearAll()
                personQueries.clearAll()
                remoteMediaItemDetailsQueries.clearAll()
                remoteMediaItemSummaryQueries.clearAll()
                remoteMediaTrashQueries.clear()
                searchQueries.clearAll()
                tokenQueries.clearAll()
                userQueries.clearAll()
                userAlbumQueries.clearAll()
                userAlbumPhotosQueries.clearAll()
                userAlbumsQueries.clearAll()
            }
            imageCacheUseCase.clearAll()
            videoCache.evictAll()
        }
    }
}