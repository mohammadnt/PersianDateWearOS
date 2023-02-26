// Copyright 2021 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.ntmohammad.persiandatewearos.tile

import androidx.wear.tiles.LayoutElementBuilders.HORIZONTAL_ALIGN_CENTER
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.ResourceBuilders
import androidx.wear.tiles.TileBuilders.Tile
import com.google.android.horologist.tiles.CoroutinesTileService
import com.ntmohammad.persiandatewearos.PersianDateActivity
import com.ntmohammad.persiandatewearos.kt.activityClickable
import com.ntmohammad.persiandatewearos.kt.column
import com.ntmohammad.persiandatewearos.kt.fontStyle
import com.ntmohammad.persiandatewearos.kt.layout
import com.ntmohammad.persiandatewearos.kt.modifiers
import com.ntmohammad.persiandatewearos.kt.text
import com.ntmohammad.persiandatewearos.kt.tile
import com.ntmohammad.persiandatewearos.kt.timeline
import com.ntmohammad.persiandatewearos.kt.timelineEntry
import com.ntmohammad.persiandatewearos.kt.toContentDescription
import com.ntmohammad.persiandatewearos.kt.toSpProp

class PersianDateTileProviderService : CoroutinesTileService() {

    override fun onCreate() {
        super.onCreate()

    }

    override suspend fun tileRequest(requestParams: RequestBuilders.TileRequest): Tile {

        return tile {
            setResourcesVersion(STABLE_RESOURCES_VERSION)

            timeline {
                timelineEntry {
                    layout {
                        column {
                            setHorizontalAlignment(HORIZONTAL_ALIGN_CENTER)

                            setModifiers(
                                modifiers {
                                    setSemantics("desc".toContentDescription())
                                    setClickable(
                                        activityClickable(
                                            this@PersianDateTileProviderService.packageName,
                                            PersianDateActivity::class.java.name
                                        )
                                    )
                                }
                            )
                            addContent(
                                text {
                                    setMaxLines(2)
                                    setFontStyle(fontStyle {
                                        setSize(16f.toSpProp())
                                    })
                                    setText("desc")
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    override suspend fun resourcesRequest(requestParams: RequestBuilders.ResourcesRequest): ResourceBuilders.Resources {
        return ResourceBuilders.Resources.Builder()
            .setVersion(STABLE_RESOURCES_VERSION)
            .build()
    }

    companion object {
        const val STABLE_RESOURCES_VERSION = "1"
    }
}
