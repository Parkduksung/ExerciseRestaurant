package com.work.restaurant.view.mypage.find_ok

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.work.restaurant.R
import com.work.restaurant.view.mypage.find_ok.presenter.MyPageFindOkContract
import com.work.restaurant.view.mypage.find_ok.presenter.MyPageFindOkPresenter
import kotlinx.android.synthetic.main.mypage_find_ok_fragment.*

class MyPageFindOkFragment : Fragment(), View.OnClickListener, MyPageFindOkContract.View {

    private lateinit var presenter: MyPageFindOkPresenter

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_send_change_pass -> {
                presenter.ok()
            }
        }
    }


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
        return inflater.inflate(R.layout.mypage_find_ok_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)


        presenter = MyPageFindOkPresenter(this)
        btn_send_change_pass.setOnClickListener(this)

    }


    override fun showOk() {
        this.requireFragmentManager().beginTransaction()
            .remove(
                this
            ).commit()
    }

    companion object {
        private const val TAG = "MyPageFindOkFragment"
    }

}
