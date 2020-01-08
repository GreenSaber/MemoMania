package com.saber.green.memomania.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.saber.green.memomania.model.Game

class MenuViewModel(application: Application) : AndroidViewModel(application) {

    fun refreshData() {
        Game.refreshData()
    }
}