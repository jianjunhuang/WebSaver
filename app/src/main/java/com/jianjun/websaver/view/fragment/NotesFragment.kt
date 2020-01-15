package com.jianjun.websaver.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jianjun.websaver.R
import com.jianjun.base.base.BaseFragment

/**
 * Created by jianjunhuang on 10/22/19.
 */
class NotesFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_note, null)

        return view
    }
}