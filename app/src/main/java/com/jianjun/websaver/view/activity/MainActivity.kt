package com.jianjun.websaver.view.activity

import android.os.Bundle
import android.util.SparseArray
import android.widget.ImageView
import androidx.core.util.set
import androidx.core.util.valueIterator
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.jianjun.base.base.BaseActivity
import com.jianjun.websaver.R
import com.jianjun.websaver.view.fragment.HomeFragment
import com.jianjun.websaver.view.fragment.SettingsFragment
import com.jianjun.websaver.view.fragment.TagsFragment
import com.jianjun.websaver.view.widgets.BottomNavigationView
import com.jianjun.websaver.view.widgets.Navigation

class MainActivity : BaseActivity(), BottomNavigationView.OnSelectedListener {

    private val fragments: SparseArray<Fragment> = SparseArray()

    override fun onSelected(view: ImageView, pos: Int) {
        showFragment(pos)
    }

    private fun showFragment(pos: Int) {
        val transition: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragments.valueIterator().forEach { fragment ->
            transition.hide(fragment)
        }
        if (fragments[pos] == null) {
            fragments[pos] = when (pos) {
                1 -> TagsFragment()
                2 -> SettingsFragment()
                else -> HomeFragment()
            }
        }
        if (fragments[pos].isAdded) {
            transition.show(fragments[pos])
        } else {
            transition.add(R.id.fl_home, fragments[pos])
        }
        transition.commitAllowingStateLoss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNav.addItem(
            Navigation(R.drawable.ic_home),
            Navigation(R.drawable.ic_tags),
//            Navigation(R.drawable.ic_notes),
            Navigation(R.drawable.ic_settings)
        )
        bottomNav.setSelectedListener(this)
        showFragment(0)
    }

    override fun onDestroy() {
        super.onDestroy()
        fragments.clear()
    }
}
