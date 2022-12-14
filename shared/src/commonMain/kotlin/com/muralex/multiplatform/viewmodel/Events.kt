package com.muralex.multiplatform.viewmodel

class Events (val stateManager : StateManager) {

    val dataRepository
        get() = stateManager.dataRepository

    // we run each event function on a Dispatchers.Main coroutine
    fun screenCoroutine (block: suspend () -> Unit) {
        stateManager.runInScreenScope { block() }
    }

}