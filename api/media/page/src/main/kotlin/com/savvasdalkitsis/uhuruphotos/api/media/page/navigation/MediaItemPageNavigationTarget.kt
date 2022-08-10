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
package com.savvasdalkitsis.uhuruphotos.api.media.page.navigation

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.navigation.NavBackStackEntry
import com.savvasdalkitsis.uhuruphotos.api.media.page.domain.model.MediaId
import com.savvasdalkitsis.uhuruphotos.api.media.page.domain.model.MediaSequenceDataSource
import com.savvasdalkitsis.uhuruphotos.api.media.page.domain.model.MediaSequenceDataSource.Single

object MediaItemPageNavigationTarget {

    const val registrationName = "mediaItemPage/{type}/{id}/{centerX}/{centerY}/{scale}/{dataSource}/{mediaSource}"

    fun name(
        id: MediaId<*>,
        offset: Offset,
        scale: Float,
        isVideo: Boolean,
        mediaSequenceDataSource: MediaSequenceDataSource = Single,
    ) = registrationName
        .replace("{id}", id.value.toString())
        .replace("{centerX}", offset.x.toString())
        .replace("{centerY}", offset.y.toString())
        .replace("{scale}", scale.toString())
        .replace(
            "{type}", when {
                isVideo -> "video"
                else -> "photo"
            }
        )
        .replace("{dataSource}", mediaSequenceDataSource.toArgument)

    val NavBackStackEntry.datasource: MediaSequenceDataSource
        get() =
        MediaSequenceDataSource.from(get("dataSource").orEmpty())

    val NavBackStackEntry.mediaItemId: MediaId<*> get() = MediaId(get("id")!!)
    val NavBackStackEntry.isVideo: Boolean get() = get("type") == "video"
    val NavBackStackEntry.center: Offset?
        get() {
            val x = get("centerX")?.toFloat()
            val y = get("centerY")?.toFloat()
            return if (x != null && y != null) Offset(x, y) else null
        }
    val NavBackStackEntry.scale: Float get() = get("scale")?.toFloat() ?: 0.3f

    private fun NavBackStackEntry.get(arg: String) = arguments!!.getString(arg)

    fun Offset?.offsetFrom(size: IntSize) = when {
        this != null -> IntOffset((x - size.width / 2).toInt(), (y - size.height / 2).toInt())
        else -> IntOffset.Zero
    }
}