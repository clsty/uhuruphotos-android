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
package com.savvasdalkitsis.uhuruphotos.feature.uploads.view.implementation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.work.WorkInfo
import androidx.work.WorkInfo.State.BLOCKED
import androidx.work.WorkInfo.State.CANCELLED
import androidx.work.WorkInfo.State.ENQUEUED
import androidx.work.WorkInfo.State.FAILED
import androidx.work.WorkInfo.State.RUNNING
import androidx.work.WorkInfo.State.SUCCEEDED
import coil.ImageLoader
import com.savvasdalkitsis.uhuruphotos.feature.upload.domain.api.model.UploadJob
import com.savvasdalkitsis.uhuruphotos.feature.upload.domain.api.model.UploadJobState
import com.savvasdalkitsis.uhuruphotos.feature.upload.domain.api.model.UploadJobType
import com.savvasdalkitsis.uhuruphotos.feature.upload.domain.api.model.UploadJobType.Completing
import com.savvasdalkitsis.uhuruphotos.feature.upload.domain.api.model.UploadJobType.Initializing
import com.savvasdalkitsis.uhuruphotos.feature.upload.domain.api.model.UploadJobType.Synchronising
import com.savvasdalkitsis.uhuruphotos.feature.upload.domain.api.model.UploadJobType.Uploading
import com.savvasdalkitsis.uhuruphotos.feature.uploads.view.implementation.R
import com.savvasdalkitsis.uhuruphotos.feature.uploads.view.implementation.seam.actions.ClearFinished
import com.savvasdalkitsis.uhuruphotos.feature.uploads.view.implementation.seam.actions.UploadsAction
import com.savvasdalkitsis.uhuruphotos.feature.uploads.view.implementation.ui.state.UploadsState
import com.savvasdalkitsis.uhuruphotos.foundation.icons.api.R.drawable
import com.savvasdalkitsis.uhuruphotos.foundation.image.api.model.LocalThumbnailImageLoader
import com.savvasdalkitsis.uhuruphotos.foundation.image.api.ui.Thumbnail
import com.savvasdalkitsis.uhuruphotos.foundation.strings.api.R.string
import com.savvasdalkitsis.uhuruphotos.foundation.theme.api.CustomColors
import com.savvasdalkitsis.uhuruphotos.foundation.theme.api.PreviewAppTheme
import com.savvasdalkitsis.uhuruphotos.foundation.ui.api.ui.ActionIcon
import com.savvasdalkitsis.uhuruphotos.foundation.ui.api.ui.CommonScaffold
import com.savvasdalkitsis.uhuruphotos.foundation.ui.api.ui.DynamicIcon
import com.savvasdalkitsis.uhuruphotos.foundation.ui.api.ui.FullLoading
import com.savvasdalkitsis.uhuruphotos.foundation.ui.api.ui.UpNavButton
import kotlinx.collections.immutable.persistentListOf


@Composable
internal fun Uploads(
    state: UploadsState,
    action: (UploadsAction) -> Unit,
) {
    CommonScaffold(
        title = { Text(text = stringResource(string.uploads)) },
        navigationIcon = { UpNavButton() },
        actionBarContent = {
            AnimatedVisibility(visible = !state.isLoading) {
                ActionIcon(
                    icon = drawable.ic_clear_all,
                    onClick = { action(ClearFinished) },
                )
            }
        }
    ) { contentPadding ->
        when {
            state.isLoading -> FullLoading()
            state.jobs.isEmpty() -> FullLoading {
                DynamicIcon(icon = R.raw.animation_empty)
            }
            else -> LazyColumn(
                modifier = Modifier.padding(contentPadding),
            ) {
                for (job in state.jobs) {
                    item(job.localItemId) {
                        UploadJobRow(job)
                    }
                }
            }
        }
    }
}

@Composable
fun UploadJobRow(job: UploadJob) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(64.dp),
        horizontalArrangement = spacedBy(4.dp),
    ) {
        val smallSegment = 0.2f
        Thumbnail(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f),
            url = job.thumbnailUrl,
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = spacedBy(4.dp),
        ) {
            Text(
                text = job.displayName ?: "",
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.body2,
            )
            Row(
                modifier = Modifier
                    .height(4.dp),
                horizontalArrangement = spacedBy(2.dp),
            ) {
                Segment(weight = smallSegment, displayingJobType = Initializing, job.latestJobState)
                Segment(weight = 1f, displayingJobType = Uploading, job.latestJobState)
                Segment(weight = smallSegment, displayingJobType = Completing, job.latestJobState)
                Segment(weight = smallSegment, displayingJobType = Synchronising, job.latestJobState)
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(job.latestJobState.state.displayName),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.body2,
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = stringResource(job.latestJobState.jobType.displayName),
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}

@Composable
private fun RowScope.Segment(
    weight: Float,
    displayingJobType: UploadJobType,
    jobState: UploadJobState,
) {
    when {
        jobState.jobType != displayingJobType -> Box(modifier = Modifier
            .fillMaxHeight()
            .weight(weight)
            .background(
                if (displayingJobType.precedes(jobState.jobType))
                    CustomColors.syncSuccess
                else
                    CustomColors.emptyItem,
                RoundedCornerShape(2.dp)
            )
        )
        jobState.state.isFinished -> Box(modifier = Modifier
            .fillMaxHeight()
            .weight(weight)
            .background(
                if (jobState.state == SUCCEEDED)
                    CustomColors.syncSuccess
                else
                    CustomColors.syncError,
                RoundedCornerShape(2.dp)
            )
        )
        jobState.progressPercent != null -> LinearProgressIndicator(
            modifier = Modifier
                .fillMaxHeight()
                .weight(weight),
            progress = jobState.progressPercent!!,
            strokeCap = StrokeCap.Round,
        )
        else -> LinearProgressIndicator(
            modifier = Modifier
                .fillMaxHeight()
                .weight(weight),
            strokeCap = StrokeCap.Round,
            color = if (jobState.state == ENQUEUED)
                CustomColors.syncQueued
            else
                MaterialTheme.colors.primary,
        )
    }
}

@Preview
@Composable
private fun UploadsPreview() {
    PreviewAppTheme {
        CompositionLocalProvider(
            LocalThumbnailImageLoader provides ImageLoader(LocalContext.current)
        ) {
            Uploads(
                UploadsState(
                    isLoading = false,
                    jobs = persistentListOf(
                        UploadJob(
                            localItemId = 1,
                            displayName = "PXL_20230801_103507882.jpg",
                            thumbnailUrl = "",
                            latestJobState = UploadJobState(
                                state = ENQUEUED,
                                jobType = Initializing,
                                progressPercent = null,
                            )
                        ),
                        UploadJob(
                            localItemId = 2,
                            displayName = "PXL_20230801_103507850.jpg",
                            thumbnailUrl = "",
                            latestJobState = UploadJobState(
                                state = RUNNING,
                                jobType = Initializing,
                                progressPercent = null,
                            )
                        ),
                        UploadJob(
                            localItemId = 3,
                            displayName = "PXL_20230801_103507810.mp4",
                            thumbnailUrl = "",
                            latestJobState = UploadJobState(
                                state = RUNNING,
                                jobType = Uploading,
                                progressPercent = null,
                            )
                        ),
                        UploadJob(
                            localItemId = 30,
                            displayName = "PXL_20230801_103507810.mp4",
                            thumbnailUrl = "",
                            latestJobState = UploadJobState(
                                state = RUNNING,
                                jobType = Uploading,
                                progressPercent = 0.1f,
                            )
                        ),
                        UploadJob(
                            localItemId = 4,
                            displayName = "PXL_20230801_103507810.mp4",
                            thumbnailUrl = "",
                            latestJobState = UploadJobState(
                                state = FAILED,
                                jobType = Uploading,
                                progressPercent = null,
                            )
                        ),
                        UploadJob(
                            localItemId = 5,
                            displayName = "PXL_20230801_103507800.jpg",
                            thumbnailUrl = "",
                            latestJobState = UploadJobState(
                                state = RUNNING,
                                jobType = Completing,
                                progressPercent = null,
                            )
                        ),
                        UploadJob(
                            localItemId = 6,
                            displayName = "PXL_20230801_103507100.jpg",
                            thumbnailUrl = "",
                            latestJobState = UploadJobState(
                                state = RUNNING,
                                jobType = Synchronising,
                                progressPercent = null,
                            )
                        ),
                        UploadJob(
                            localItemId = 7,
                            displayName = "PXL_20230801_103507100.jpg",
                            thumbnailUrl = "",
                            latestJobState = UploadJobState(
                                state = SUCCEEDED,
                                jobType = Synchronising,
                                progressPercent = null,
                            )
                        ),
                    ),
                )
            ) {}
        }
    }
}

private val WorkInfo.State.displayName: Int
    @Stable
    get() = when (this) {
        ENQUEUED -> string.queued
        RUNNING -> string.running
        SUCCEEDED -> string.succeeded
        FAILED -> string.failed
        BLOCKED -> string.blocked
        CANCELLED -> string.canceled
    }