// Copyright 2018 Google LLC
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
package com.google.wear.whereami.complication

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Icon
import androidx.wear.watchface.complications.data.*
import androidx.wear.watchface.complications.datasource.ComplicationDataSourceUpdateRequester
import androidx.wear.watchface.complications.datasource.ComplicationRequest
import com.google.wear.whereami.R
import com.google.wear.whereami.WhereAmIActivity.Companion.tapAction
import com.google.wear.whereami.data.MyDateModel
import com.google.wear.whereami.data.withPersianDigits
import com.google.wear.whereami.kt.CoroutinesComplicationDataSourceService
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat
import java.util.*


class WhereAmIComplicationProviderService : CoroutinesComplicationDataSourceService() {


    override fun onCreate() {
        super.onCreate()
    }


    override suspend fun onComplicationUpdate(complicationRequest: ComplicationRequest): ComplicationData? {
        val pdate = PersianDate()
        val pdformater2 = PersianDateFormat("y F j")
        pdformater2.format(pdate);
        val date = Date();
        val dateModel = MyDateModel(pdate.dayOfWeek(date),pdate.getMonthName(),pdate.shDay)
        return toComplicationData(
            complicationRequest.complicationType,
            dateModel
        )
    }

    override fun getPreviewData(type: ComplicationType): ComplicationData {
        val pdate = PersianDate()
        val pdformater2 = PersianDateFormat("y F j")
        pdformater2.format(pdate);
        val date = Date();
        val dateModel = MyDateModel(pdate.dayOfWeek(date),pdate.getMonthName(),pdate.shDay)

        return toComplicationData(type, dateModel)
    }

    fun toComplicationData(
        type: ComplicationType,
        dateModel: MyDateModel
    ): ComplicationData {
        return when (type) {
            ComplicationType.SHORT_TEXT -> ShortTextComplicationData.Builder(
                toComplication("${dateModel.day.withPersianDigits} ${dateModel.monthName}"),
                toComplication("${dateModel.weekDayName()}")
            )
                .setTitle(toComplication("${dateModel.weekDayName()}"))
                .setTapAction(tapAction())
                .build()
            ComplicationType.LONG_TEXT -> LongTextComplicationData.Builder(
                toComplication("${dateModel.day.withPersianDigits} ${dateModel.monthName}"),
                toComplication("${dateModel.weekDayName()}")
            )
                .setTitle(toComplication("${dateModel.weekDayName()}"))

                .setTapAction(tapAction())
                .build()
            else -> throw IllegalArgumentException("Unexpected complication type $type")
        }
    }


    fun toComplication(s : String): ComplicationText {
        return PlainComplicationText.Builder(s).build()
    }

    companion object {
        fun Context.forceComplicationUpdate() {
            if (applicationContext.checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                val request =
                    ComplicationDataSourceUpdateRequester.create(
                        applicationContext, ComponentName(
                            applicationContext, WhereAmIComplicationProviderService::class.java
                        )
                    )
                request.requestUpdateAll()
            }
        }
    }
}


