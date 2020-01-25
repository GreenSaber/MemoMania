package com.saber.green.memomania.ui.gamefields

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.saber.green.memomania.R

class EasyGameFieldFragment : BaseGameFieldFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_game_easy_field, container, false)
        return view
    }
}
