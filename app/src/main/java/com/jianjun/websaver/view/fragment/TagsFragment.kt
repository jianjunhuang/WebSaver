package com.jianjun.websaver.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.jianjun.base.base.BaseFragment
import com.jianjun.websaver.databinding.FragmentTagBinding
import com.jianjun.websaver.presenter.TagsListViewModel
import com.jianjun.websaver.view.adapter.TagsListAdapter

/**
 * Created by jianjunhuang on 10/22/19.
 */
class TagsFragment : BaseFragment() {

    private lateinit var binding: FragmentTagBinding
    private lateinit var adapter: TagsListAdapter
    private val viewModel: TagsListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTagBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = TagsListAdapter()
        val flexManager = FlexboxLayoutManager(requireContext()).apply {
            flexDirection = FlexDirection.COLUMN
            justifyContent = JustifyContent.FLEX_START
        }
        binding.rvTags.apply {
            layoutManager = flexManager
            adapter = this@TagsFragment.adapter
        }
    }

    override fun onResume() {
        super.onResume()
    }
}