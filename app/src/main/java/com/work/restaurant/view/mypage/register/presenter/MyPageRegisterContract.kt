package com.work.restaurant.view.mypage.register.presenter

interface MyPageRegisterContract {

    interface View {

        fun showRegisterOk()

        fun showRegisterNo(sort: Int)

        fun showEmailDuplicationCheck(check: Boolean)

        fun showProgressState(state: Boolean)

    }

    interface Presenter {

        fun register(nickName: String, email: String, pass: String)

        fun emailDuplicationCheck(userId: String)


    }

}