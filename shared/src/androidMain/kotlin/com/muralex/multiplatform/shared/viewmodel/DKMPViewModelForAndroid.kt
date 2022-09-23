package com.muralex.multiplatform.shared.viewmodel

import android.content.Context
import com.muralex.multiplatform.NewsDb
import com.muralex.multiplatform.datalayer.Repository
import com.muralex.multiplatform.viewmodel.DKMPViewModel
import com.squareup.sqldelight.android.AndroidSqliteDriver

fun DKMPViewModel.Factory.getAndroidInstance(context : Context) : DKMPViewModel {
    val sqlDriver = AndroidSqliteDriver(NewsDb.Schema, context, "News.db")
    val repository = Repository(sqlDriver)
    return DKMPViewModel(repository)
}