package com.jianjun.websaver.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jianjun.websaver.R
import com.jianjun.websaver.base.ItemClickListener
import com.jianjun.websaver.base.mvp.BaseMvpFragment
import com.jianjun.websaver.contact.PagerListContact
import com.jianjun.websaver.module.TAG_ALL
import com.jianjun.websaver.module.db.entity.Pager
import com.jianjun.websaver.presenter.PagerListPresenter
import com.jianjun.websaver.view.activity.PagerViewerActivity
import com.jianjun.websaver.view.adapter.PagerListAdapter
import com.jianjun.websaver.view.widgets.MultiSelectedLayout

/**
 * Created by jianjunhuang on 10/25/19.
 */
class PagerListFragment : BaseMvpFragment<PagerListPresenter>(), PagerListContact.IPagersView,
    ItemClickListener<Pager> {

    companion object {
        const val KEY_TAG = "key_tag"
        fun create(tag: String): PagerListFragment {
            val fragment = PagerListFragment()
            val bundle = Bundle()
            bundle.putString(KEY_TAG, tag)
            fragment.arguments = bundle
            return fragment
        }

    }

    private var pagerTag: String = TAG_ALL


    override fun onItemClick(data: Pager, position: Int, adapter: RecyclerView.Adapter<*>) {
        val intent = Intent()
        context?.let {
            intent.setClass(
                it, PagerViewerActivity::class.java
            )
            intent.putExtra(Intent.EXTRA_TEXT, data.url)
            startActivity(intent)
        }
    }

    override fun onPagers(pagers: List<Pager>?) {
        pagerAdapter.refresh(pagers!!)
    }

    override fun createPresenter(): PagerListPresenter {
        return PagerListPresenter()
    }

    private var pagerList: RecyclerView? = null
    private lateinit var pagerAdapter: PagerListAdapter
    private var multiSelectedLayout: MultiSelectedLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pagerTag = arguments?.getString(KEY_TAG).toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_pager_list, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pagerList = view.findViewById(R.id.rv_pager)
        pagerList?.layoutManager = LinearLayoutManager(context)
        pagerAdapter = PagerListAdapter()
        pagerAdapter.setOnItemCLickListener(this)
        pagerList?.adapter = pagerAdapter
        multiSelectedLayout = view.findViewById(R.id.multi_selected_layout)
        multiSelectedLayout?.setSelectAdapter(pagerAdapter)
        multiSelectedLayout?.selectedListener = object : MultiSelectedLayout.MultiSelectedListener {
            override fun onClose() {
            }

            override fun onDeleted() {
            }

            override fun onSelectedAll(selectedAll: Boolean) {
            }
        }
        getPresenter()?.queryPagers(pagerTag)
    }

}