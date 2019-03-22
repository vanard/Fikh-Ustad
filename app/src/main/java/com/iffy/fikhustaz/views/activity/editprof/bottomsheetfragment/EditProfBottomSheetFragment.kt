package com.iffy.fikhustaz.views.activity.editprof.bottomsheetfragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import com.iffy.fikhustaz.R
import kotlinx.android.synthetic.main.fragment_edit_prof_bottom_sheet.*

class EditProfBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_prof_bottom_sheet, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = MyViewPagerAdapter(childFragmentManager)
        adapter.addFragment(ScheduleStartFragment() , " Starts From ")
        adapter.addFragment(ScheduleEndFragment() , " Ends At ")
        viewPager_editProf.adapter = adapter
        tabLayout_editProf.setupWithViewPager(viewPager_editProf)

    }

    class MyViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager){

        private val fragmentList : MutableList<Fragment> = ArrayList()
        private val titleList : MutableList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        fun addFragment(fragment: Fragment,title:String){
            fragmentList.add(fragment)
            titleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }

    }
}
