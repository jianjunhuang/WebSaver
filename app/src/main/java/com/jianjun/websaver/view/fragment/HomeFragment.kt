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

/**
 * Created by jianjunhuang on 10/22/19.
 */
class HomeFragment : BaseMvpFragment<HomePresenter>(), HomeContact.IHomeView {

    private var viewpager: ViewPager? = null
    private var tablayout: TabLayout? = null
    private val pagerListFragments: List<String>? = null

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
        viewpager?.adapter =
            fragmentManager?.let {
                TabAdapter(
                    it,
                    FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                    pagerListFragments
                )
            }
        tablayout?.setupWithViewPager(viewpager)
    }

    class TabAdapter(
        fm: FragmentManager,
        behavior: Int,
        private val fragments: Array<PagerListFragment>
    ) :
        FragmentPagerAdapter(fm, behavior) {

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return fragments[position].getTitle()
        }

    }

    override fun onResume() {
        super.onResume()
    }

    override fun createPresenter(): HomePresenter {
        return HomePresenter()
    }

    override fun onTags(tags: List<String>) {

    }
}