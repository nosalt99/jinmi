package com.cyl.musiclake.ui.sleep

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.cyl.musiclake.ui.sleep.SleepEnvFragment

/**
 * Created by yonglong on 2015/6/29.
 */
class ViewPagerAdapter(fm: FragmentManager, private var mFragments: MutableList<SleepEnvFragment>) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getCount(): Int {
        return mFragments.size
    }

    fun setFragments(mFragments: MutableList<SleepEnvFragment>) {
        this.mFragments = mFragments
    }
}
