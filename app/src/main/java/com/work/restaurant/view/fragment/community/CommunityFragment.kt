package com.work.restaurant.view.fragment.community

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.work.restaurant.R
import com.work.restaurant.view.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.community_fragment.*

class CommunityFragment : Fragment() {


    override fun onAttach(context: Context) {
        Log.d(TAG, "onAttach")
        super.onAttach(context)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.community_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)

        init()

    }


    private fun init() {
        val fragmentMap: Map<String, Fragment> = mapOf(
            "헬스장리뷰" to CommunityGymListFragment(),
            "운동일지" to CommunityExerciseListFragment(),
            "영양일지" to CommunityNutritionListFragment()
        )

        val adapter = ViewPagerAdapter(this.requireFragmentManager(), fragmentMap)
        vp_community.adapter = adapter
//        tl_community.setupWithViewPager(vp_community)

        tl_community.run {
            setupWithViewPager(vp_community)
            getTabAt(0)?.setIcon(R.drawable.dumbell)
            getTabAt(1)?.setIcon(R.drawable.exercise)
            getTabAt(2)?.setIcon(R.drawable.eat)

        }

    }


    override fun onStart() {
        Log.d(TAG, "onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d(TAG, "onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG, "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d(TAG, "onDetach")
        super.onDetach()
    }

    companion object {
        private const val TAG = "CommunityFragment"
    }
}