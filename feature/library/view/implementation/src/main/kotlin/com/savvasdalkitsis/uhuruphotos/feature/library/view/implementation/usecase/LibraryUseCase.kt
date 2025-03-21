/*
Copyright 2023 Savvas Dalkitsis

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
package com.savvasdalkitsis.uhuruphotos.feature.library.view.implementation.usecase

import com.savvasdalkitsis.uhuruphotos.feature.library.view.implementation.ui.state.LibraryItem
import com.savvasdalkitsis.uhuruphotos.foundation.preferences.api.Preferences
import com.savvasdalkitsis.uhuruphotos.foundation.preferences.api.observe
import com.savvasdalkitsis.uhuruphotos.foundation.preferences.api.set
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LibraryUseCase @Inject constructor(
    private val preferences: Preferences,
) {
    private val key = "libraryItemsOrder"

    fun getLibraryItems(): Flow<List<LibraryItem>> = preferences.observe<String>(
        key,
        defaultValue = LibraryItem.entries.serialize
    ).map { it.deserialize }

    fun setLibraryItems(items: List<LibraryItem>) {
        preferences.set(key, items.serialize)
    }

    private val List<LibraryItem>.serialize get() = joinToString(":")

    private val String.deserialize get() = split(":").map(LibraryItem::valueOf)
}
