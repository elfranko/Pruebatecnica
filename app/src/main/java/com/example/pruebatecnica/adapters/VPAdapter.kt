package com.example.pruebatecnica.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class VPAdapter(SupportFragmentManager: FragmentManager) :FragmentPagerAdapter(SupportFragmentManager)  {

    private val mFragmentList = ArrayList<Fragment>()

    private val mFragmentTitleList = ArrayList<String>()

   override fun getCount(): Int {
      return mFragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitleList[position]
    }

    fun addFragment(fragment: Fragment, tittle: String){
        mFragmentList.add(fragment)
        mFragmentTitleList.add(tittle)
    }

}