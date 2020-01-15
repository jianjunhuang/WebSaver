package com.jianjun.base.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MultiFragmentAdapter<E, F : Fragment>(
    fm: FragmentManager,
    private val creator: FragmentCreator<E, F>
) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var dataList: List<E>? = null

    public fun setDataList(data: List<E>) {
        dataList = data
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        return creator.create(dataList?.get(position), position)
    }

    override fun getCount(): Int {
        return dataList?.size ?: 0
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return creator.getTitle(dataList?.get(position))
    }

    interface FragmentCreator<E, F : Fragment> {
        fun create(data: E?, pos: Int): F
        fun getTitle(data: E?): String
    }
}