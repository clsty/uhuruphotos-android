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
package com.savvasdalkitsis.uhuruphotos.implementation.auth.weblogin.weblogin.viewmodel

import androidx.lifecycle.ViewModel
import com.savvasdalkitsis.uhuruphotos.api.seam.Seam
import com.savvasdalkitsis.uhuruphotos.api.seam.SeamViaHandler.Companion.handler
import com.savvasdalkitsis.uhuruphotos.implementation.auth.weblogin.weblogin.seam.WebLoginAction
import com.savvasdalkitsis.uhuruphotos.implementation.auth.weblogin.weblogin.seam.WebLoginEffect
import com.savvasdalkitsis.uhuruphotos.implementation.auth.weblogin.weblogin.seam.WebLoginActionHandler
import com.savvasdalkitsis.uhuruphotos.implementation.auth.weblogin.weblogin.seam.WebLoginMutation
import com.savvasdalkitsis.uhuruphotos.implementation.auth.weblogin.weblogin.view.WebLoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WebLoginViewModel @Inject constructor(
    handler: WebLoginActionHandler,
) : ViewModel(),
    Seam<WebLoginState, WebLoginEffect, WebLoginAction, WebLoginMutation> by handler(
        handler,
        WebLoginState(""),
    )