package com.jianjun.websaver.view.fragment

import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.jianjun.base.adapter.BaseAdapter
import com.jianjun.websaver.R
import com.jianjun.websaver.databinding.FragmentPagerBottomBinding
import com.jianjun.websaver.module.bean.PagerBottomBean
import com.jianjun.websaver.view.adapter.PagerBottomAdapter

/**
 * Created by jianjunhuang on 1/27/20.
 */
class PagerViewerBottomFragment : BottomSheetDialogFragment(),
    BaseAdapter.OnItemClickListener<PagerBottomAdapter.ViewHolder, PagerBottomBean> {

    private lateinit var databinding: FragmentPagerBottomBinding
    private lateinit var adapter: PagerBottomAdapter
    private var url: String? = null

    companion object {
        const val SHARE = 1
        const val BROWSER = 2
        const val KEY_URL = "key_url"
        const val TAG = "PagerViewerBottomFragment"
        fun newInstance(url: String): PagerViewerBottomFragment {
            val fragment = PagerViewerBottomFragment()
            val bundle = Bundle()
            bundle.putString(KEY_URL, url)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding =
            FragmentPagerBottomBinding.inflate(inflater, null, false)
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        url = arguments?.getString(KEY_URL)
        val layoutManager = GridLayoutManager(context, 2)
        databinding.rvBottom.layoutManager = layoutManager
        databinding.rvBottom.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                val pos = parent.getChildAdapterPosition(view)
                if (pos % 2 == 0) {
                    outRect.left = 16
                    outRect.right = 8
                } else {
                    outRect.left = 8
                    outRect.right = 16
                }
                outRect.top = 16
                outRect.bottom = 16
            }
        })
        adapter = PagerBottomAdapter()
        adapter.onItemClickListener = this
        databinding.rvBottom.adapter = adapter
        //init item
        adapter.refresh(
            arrayListOf(
                PagerBottomBean(SHARE, R.drawable.ic_share, "Share"),
                PagerBottomBean(BROWSER, R.drawable.ic_public, "Open with others")
            )
        )
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val background: View? = it.window?.findViewById(R.id.design_bottom_sheet)
            background?.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    override fun onClick(holder: PagerBottomAdapter.ViewHolder, pos: Int, data: PagerBottomBean) {
        try {
            val intent = Intent()
            url?.let {
                intent.data = Uri.parse(it)
            }
            when (data.id) {
                SHARE -> {
                    intent.action = Intent.ACTION_SEND
                    intent.type = "text/plain"
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Share")
                    intent.putExtra(Intent.EXTRA_TEXT, url)
                }
                BROWSER -> {
                    intent.action = Intent.ACTION_VIEW
                }
            }
            startActivity(intent)
        } catch (e: Exception) {
            view?.let {
                Snackbar.make(it, "not intent application", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}