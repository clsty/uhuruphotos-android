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
package com.savvasdalkitsis.uhuruphotos.implementation.people.viewmodel

import androidx.lifecycle.ViewModel
import com.savvasdalkitsis.uhuruphotos.api.seam.Seam
import com.savvasdalkitsis.uhuruphotos.api.seam.SeamViaHandler.Companion.handler
import com.savvasdalkitsis.uhuruphotos.implementation.people.seam.PeopleAction
import com.savvasdalkitsis.uhuruphotos.implementation.people.seam.PeopleEffect
import com.savvasdalkitsis.uhuruphotos.implementation.people.seam.PeopleActionHandler
import com.savvasdalkitsis.uhuruphotos.implementation.people.seam.PeopleMutation
import com.savvasdalkitsis.uhuruphotos.implementation.people.view.state.PeopleState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    peopleActionHandler: PeopleActionHandler,
) : ViewModel(),
    Seam<PeopleState, PeopleEffect, PeopleAction, PeopleMutation> by handler(
        peopleActionHandler,
        PeopleState()
    )