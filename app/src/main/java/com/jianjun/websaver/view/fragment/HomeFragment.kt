package com.jianjun.websaver.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.jianjun.websaver.R
import com.jianjun.websaver.base.BaseFragment
import com.jianjun.websaver.base.mvp.BaseMvpFragment
import com.jianjun.websaver.contact.HomeContact
import com.jianjun.websaver.module.PagerTag
import com.jianjun.websaver.module.TAG_ALL
import com.jianjun.websaver.module.TAG_READ
import com.jianjun.websaver.module.TAG_UNREAD
import com.jianjun.websaver.presenter.HomePresenter
import com.jianjun.websaver.view.adapter.MultiFragmentAdapter

/**
 * Created by jianjunhuang on 10/22/19.
 */
class HomeFragment : BaseMvpFragment<HomePresenter>(), HomeContact.IHomeView {

    private var viewpager: ViewPager? = null
    private var tablayout: TabLayout? = null
    private var pagerAdapter: MultiFragmentAdapter<String, PagerListFragment>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_home, null)
        viewpager = view.findViewById(R.id.vp_pager)
        tablayout = view.findViewById(R.id.tab_layout)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pagerAdapter = MultiFragmentAdapter(childFragmentManager,
            object : MultiFragmentAdapter.FragmentCreator<String, PagerListFragment> {
                override fun create(data: String?, pos: Int): PagerListFragment {
                    return PagerListFragment.create(data ?: TAG_ALL)
                }

                override fun getTitle(data: String?): String {
                    return data ?: TAG_ALL
                }

            })
        viewpager?.adapter = pagerAdapter
        tablayout?.setupWithViewPager(viewpager)
        getPresenter()?.queryTags()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun createPresenter(): HomePresenter {
        return HomePresenter()
    }

    override fun onTags(tags: List<String>) {
        pagerAdapter?.setDataList(tags)
    }
}