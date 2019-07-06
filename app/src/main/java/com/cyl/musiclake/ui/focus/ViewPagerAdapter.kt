package com.cyl.musiclake.ui.focus

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by yonglong on 2015/6/29.
 */
class ViewPagerAdapter(fm: FragmentManager, private var mFragments: MutableList<FocusEnvFragment>) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getCount(): Int {
        return mFragments.size
    }

    fun setFragments(mFragments: MutableList<FocusEnvFragment>) {
        this.mFragments = mFragments
    }
}
