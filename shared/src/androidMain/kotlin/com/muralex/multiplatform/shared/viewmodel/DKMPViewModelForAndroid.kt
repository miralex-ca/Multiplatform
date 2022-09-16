package com.muralex.multiplatform.shared.viewmodel

import android.content.Context
import com.muralex.multiplatform.datalayer.Repository
import com.muralex.multiplatform.viewmodel.DKMPViewModel




fun DKMPViewModel.Factory.getAndroidInstance(context : Context) : DKMPViewModel {

    val repository = Repository()
    return DKMPViewModel(repository)
}