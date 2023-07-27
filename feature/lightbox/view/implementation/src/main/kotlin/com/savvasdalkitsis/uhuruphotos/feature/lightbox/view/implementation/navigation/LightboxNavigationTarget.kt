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
package com.savvasdalkitsis.uhuruphotos.feature.lightbox.view.implementation.navigation

import androidx.compose.runtime.Composable
import com.savvasdalkitsis.uhuruphotos.feature.lightbox.view.api.navigation.LightboxNavigationRoute
import com.savvasdalkitsis.uhuruphotos.feature.lightbox.view.implementation.ui.Lightbox
import com.savvasdalkitsis.uhuruphotos.feature.lightbox.view.implementation.viewmodel.LightboxViewModel
import com.savvasdalkitsis.uhuruphotos.foundation.navigation.api.NavigationTarget
import com.savvasdalkitsis.uhuruphotos.foundation.navigation.api.NavigationTargetBuilder
import com.savvasdalkitsis.uhuruphotos.foundation.navigation.api.NavigationTargetRegistry
import com.savvasdalkitsis.uhuruphotos.foundation.ui.api.theme.ThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import se.ansman.dagger.auto.AutoInitialize
import javax.inject.Inject
import javax.inject.Singleton

@AutoInitialize
@Singleton
class LightboxNavigationTarget @Inject constructor(
    registry: NavigationTargetRegistry,
    private val navigationTargetBuilder: NavigationTargetBuilder,
) : NavigationTarget<LightboxNavigationRoute>(LightboxNavigationRoute::class, registry) {

    @Composable
    override fun View(route: LightboxNavigationRoute) = with(navigationTargetBuilder) {
        ViewModelView(
            themeMode = MutableStateFlow(ThemeMode.DARK_MODE),
            route = route,
            viewModel = LightboxViewModel::class,
        ) { state, actions ->
            Lightbox(state, actions)
        }
    }
}
