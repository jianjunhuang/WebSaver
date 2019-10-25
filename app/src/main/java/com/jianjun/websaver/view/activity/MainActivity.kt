package com.jianjun.websaver.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.jianjun.websaver.R
import com.jianjun.websaver.view.fragment.HomeFragment
import com.jianjun.websaver.view.fragment.NotesFragment
import com.jianjun.websaver.view.fragment.SettingsFragment
import com.jianjun.websaver.view.fragment.TagsFragment
import com.jianjun.websaver.view.widgets.BottomNavigationView
import com.jianjun.websaver.view.widgets.Navigation

class MainActivity : AppCompatActivity(), BottomNavigationView.OnSelectedListener {

    private val fragments: ArrayList<Fragment> = ArrayList()

    override fun onSelected(view: ImageView, pos: Int) {
        showFragment(pos)
    }

    private fun showFragment(pos: Int) {
        val transition: FragmentTransaction = supportFragmentManager.beginTransaction()
        for (fragment in fragments) {
            transition.hide(fragment)
        }
        transition.show(fragments[pos])
        transition.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNav.addItem(
            Navigation(R.drawable.ic_home),
            Navigation(R.drawable.ic_tags),
            Navigation(R.drawable.ic_notes),
            Navigation(R.drawable.ic_settings)
        )
        bottomNav.setSelectedListener(this)
        fragments.add(HomeFragment())
        fragments.add(TagsFragment())
        fragments.add(NotesFragment())
        fragments.add(SettingsFragment())
        val transition: FragmentTransaction = supportFragmentManager.beginTransaction()
        for (fragment in fragments) {
            transition.add(R.id.fl_home, fragment)
        }
        transition.commit()
        showFragment(0)
    }
}
