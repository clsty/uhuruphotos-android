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
package com.savvasdalkitsis.uhuruphotos.feed.view.state


import androidx.annotation.DrawableRes
import com.savvasdalkitsis.uhuruphotos.icons.R
import kotlin.math.max
import kotlin.math.min

enum class FeedDisplays(
    override val compactColumnsPortrait: Int,
    override val compactColumnsLandscape: Int,
    override val wideColumnsPortrait: Int,
    override val wideColumnsLandscape: Int,
    override val shouldAddEmptyPhotosInRows: Boolean,
    @DrawableRes override val iconResource: Int,
    override val maintainAspectRatio: Boolean,
    override val friendlyName: String,
) : FeedDisplay {
    TINY(
        compactColumnsPortrait = 5,
        compactColumnsLandscape = 8,
        wideColumnsPortrait = 8,
        wideColumnsLandscape = 9,
        shouldAddEmptyPhotosInRows = true,
        iconResource = R.drawable.ic_feed_tiny,
        maintainAspectRatio = false,
        friendlyName = "Tiny",
    ),
    COMPACT(
        compactColumnsPortrait = 4,
        compactColumnsLandscape = 7,
        wideColumnsPortrait = 7,
        wideColumnsLandscape = 8,
        shouldAddEmptyPhotosInRows = true,
        iconResource = R.drawable.ic_feed_compact,
        maintainAspectRatio = true,
        friendlyName = "Compact",
    ),
    COMFORTABLE(
        compactColumnsPortrait = 3,
        compactColumnsLandscape = 4,
        wideColumnsPortrait = 4,
        wideColumnsLandscape = 6,
        shouldAddEmptyPhotosInRows = true,
        iconResource = R.drawable.ic_feed_comfortable,
        maintainAspectRatio = true,
        friendlyName = "Comfortable",
    ),
    BIG(
        compactColumnsPortrait = 2,
        compactColumnsLandscape = 3,
        wideColumnsPortrait = 3,
        wideColumnsLandscape = 5,
        shouldAddEmptyPhotosInRows = false,
        iconResource = R.drawable.ic_feed_big,
        maintainAspectRatio = true,
        friendlyName = "Big",
    ),
    FULL(
        compactColumnsPortrait = 1,
        compactColumnsLandscape = 2,
        wideColumnsPortrait = 2,
        wideColumnsLandscape = 4,
        shouldAddEmptyPhotosInRows = false,
        iconResource = R.drawable.ic_feed_full,
        maintainAspectRatio = true,
        friendlyName = "Full",
    );

    override val zoomIn: FeedDisplay get() = values()[min(ordinal + 1, values().size - 1)]
    override val zoomOut: FeedDisplay get() = values()[max(0, ordinal -1)]

    companion object {
        val default = COMFORTABLE
    }
}