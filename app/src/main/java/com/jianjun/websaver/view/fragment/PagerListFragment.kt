package com.jianjun.websaver.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jianjun.websaver.R
import com.jianjun.websaver.base.BaseFragment
import com.jianjun.websaver.base.ItemClickListener
import com.jianjun.websaver.base.mvp.BaseMvpFragment
import com.jianjun.websaver.contact.PagerListContact
import com.jianjun.websaver.module.db.entity.Pager
import com.jianjun.websaver.presenter.PagerListPresenter
import com.jianjun.websaver.view.activity.PagerViewerActivity
import com.jianjun.websaver.view.adapter.PagerListAdapter
import java.util.*

/**
 * Created by jianjunhuang on 10/25/19.
 */
class PagerListFragment : BaseMvpFragment<PagerListPresenter>(), PagerListContact.IPagersView,
    ItemClickListener<Pager> {
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
        pagerAdapter?.refresh(pagers!!)
    }

    override fun createPresenter(): PagerListPresenter {
        return PagerListPresenter()
    }

    private var pagerList: RecyclerView? = null
    private var pagerAdapter: PagerListAdapter? = null

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
        pagerAdapter = PagerListAdapter(null)
        pagerAdapter?.setOnItemCLickListener(this)
        pagerList?.adapter = pagerAdapter
    }

    override fun onResume() {
        super.onResume()
        getPresenter()?.queryPagers()
    }
}