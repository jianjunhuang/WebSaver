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

/**
 * Created by jianjunhuang on 10/22/19.
 */
class HomeFragment : BaseFragment() {

    private var viewpager: ViewPager? = null
    private var tablayout: TabLayout? = null
    private val pagerListFragments: Array<Fragment> = arrayOf(
        PagerListFragment()
    )

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

    class TabAdapter(fm: FragmentManager, behavior: Int, private val fragments: Array<Fragment>) :
        FragmentPagerAdapter(fm, behavior) {

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return "All"
        }

    }
}