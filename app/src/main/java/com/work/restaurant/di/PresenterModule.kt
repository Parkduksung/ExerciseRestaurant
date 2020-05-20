package com.work.restaurant.di

import com.work.restaurant.view.calendar.presenter.CalendarContract
import com.work.restaurant.view.calendar.presenter.CalendarPresenter
import com.work.restaurant.view.diary.add_eat.presenter.AddEatContract
import com.work.restaurant.view.diary.add_eat.presenter.AddEatPresenter
import com.work.restaurant.view.diary.add_exercise.presenter.AddExerciseContract
import com.work.restaurant.view.diary.add_exercise.presenter.AddExercisePresenter
import com.work.restaurant.view.diary.main.presenter.DiaryContract
import com.work.restaurant.view.diary.main.presenter.DiaryPresenter
import com.work.restaurant.view.diary.update_or_delete_eat.presenter.UpdateOrDeleteEatContract
import com.work.restaurant.view.diary.update_or_delete_eat.presenter.UpdateOrDeleteEatPresenter
import com.work.restaurant.view.diary.update_or_delete_exercise.presenter.UpdateOrDeleteExerciseContract
import com.work.restaurant.view.diary.update_or_delete_exercise.presenter.UpdateOrDeleteExercisePresenter
import com.work.restaurant.view.home.address.presenter.HomeAddressContract
import com.work.restaurant.view.home.address.presenter.HomeAddressPresenter
import com.work.restaurant.view.home.daum_maps.presenter.MapContract
import com.work.restaurant.view.home.daum_maps.presenter.MapPresenter
import com.work.restaurant.view.home.main.presenter.HomeContract
import com.work.restaurant.view.home.main.presenter.HomePresenter
import com.work.restaurant.view.loading.LoadingContract
import com.work.restaurant.view.loading.LoadingPresenter
import com.work.restaurant.view.mypage.find.presenter.MyPageFindPassContract
import com.work.restaurant.view.mypage.find.presenter.MyPageFindPassPresenter
import com.work.restaurant.view.mypage.logout.presenter.MyPageLogoutContract
import com.work.restaurant.view.mypage.logout.presenter.MyPageLogoutPresenter
import com.work.restaurant.view.mypage.main.presenter.MyPageContract
import com.work.restaurant.view.mypage.main.presenter.MyPagePresenter
import com.work.restaurant.view.mypage.notification.presenter.MyPageNotificationContract
import com.work.restaurant.view.mypage.notification.presenter.MyPageNotificationPresenter
import com.work.restaurant.view.mypage.question.presenter.MyPageQuestionContract
import com.work.restaurant.view.mypage.question.presenter.MyPageQuestionPresenter
import com.work.restaurant.view.mypage.register.presenter.MyPageRegisterContract
import com.work.restaurant.view.mypage.register.presenter.MyPageRegisterPresenter
import com.work.restaurant.view.mypage.withdraw.presenter.MyPageWithdrawalContract
import com.work.restaurant.view.mypage.withdraw.presenter.MyPageWithdrawalPresenter
import com.work.restaurant.view.search.bookmarks.presenter.SearchBookmarksContract
import com.work.restaurant.view.search.bookmarks.presenter.SearchBookmarksPresenter
import com.work.restaurant.view.search.lookfor.presenter.SearchLookForContract
import com.work.restaurant.view.search.lookfor.presenter.SearchLookForPresenter
import com.work.restaurant.view.search.rank.presenter.SearchRankContract
import com.work.restaurant.view.search.rank.presenter.SearchRankPresenter
import org.koin.dsl.module

val presenterModule = module {
    factory<SearchRankContract.Presenter> { (view: SearchRankContract.View) ->
        SearchRankPresenter(
            view,
            get(),
            get()
        )
    }

    factory<SearchLookForContract.Presenter> { (view: SearchLookForContract.View) ->
        SearchLookForPresenter(
            view,
            get(),
            get()
        )
    }

    factory<SearchBookmarksContract.Presenter> { (view: SearchBookmarksContract.View) ->
        SearchBookmarksPresenter(
            view,
            get()
        )
    }

    factory<MyPageWithdrawalContract.Presenter> { (view: MyPageWithdrawalContract.View) ->
        MyPageWithdrawalPresenter(
            view,
            get(),
            get()
        )
    }


    factory<MyPageRegisterContract.Presenter> { (view: MyPageRegisterContract.View) ->
        MyPageRegisterPresenter(
            view,
            get(),
            get()
        )
    }
    factory<MyPageQuestionContract.Presenter> { (view: MyPageQuestionContract.View) ->
        MyPageQuestionPresenter(
            view,
            get()
        )
    }

    factory<MyPageNotificationContract.Presenter> { (view: MyPageNotificationContract.View) ->
        MyPageNotificationPresenter(
            view,
            get()
        )
    }

    factory<MyPageContract.Presenter> { (view: MyPageContract.View) ->
        MyPagePresenter(
            view,
            get(),
            get()
        )
    }

    factory<MyPageLogoutContract.Presenter> { (view: MyPageLogoutContract.View) ->
        MyPageLogoutPresenter(
            view,
            get()
        )
    }

    factory<MyPageFindPassContract.Presenter> { (view: MyPageFindPassContract.View) ->
        MyPageFindPassPresenter(
            view,
            get()
        )
    }

    factory<LoadingContract.Presenter> { (view: LoadingContract.View) ->
        LoadingPresenter(
            view,
            get(),
            get()
        )
    }

    factory<CalendarContract.Presenter> { (view: CalendarContract.View) ->
        CalendarPresenter(
            view,
            get(),
            get()
        )
    }

    factory<HomeContract.Presenter> { (view: HomeContract.View) ->
        HomePresenter(
            view,
            get()
        )
    }

    factory<HomeAddressContract.Presenter> { (view: HomeAddressContract.View) ->
        HomeAddressPresenter(
            view,
            get()
        )
    }

    factory<MapContract.Presenter> { (view: MapContract.View) ->
        MapPresenter(
            view,
            get(),
            get()
        )
    }

    factory<DiaryContract.Presenter> { (view: DiaryContract.View) ->
        DiaryPresenter(
            view,
            get(),
            get()
        )
    }
    factory<AddExerciseContract.Presenter> { (view: AddExerciseContract.View) ->
        AddExercisePresenter(
            view,
            get()
        )
    }

    factory<AddEatContract.Presenter> { (view: AddEatContract.View) ->
        AddEatPresenter(
            view,
            get()
        )
    }
    factory<UpdateOrDeleteEatContract.Presenter> { (view: UpdateOrDeleteEatContract.View) ->
        UpdateOrDeleteEatPresenter(
            view,
            get()
        )
    }
    factory<UpdateOrDeleteExerciseContract.Presenter> { (view: UpdateOrDeleteExerciseContract.View) ->
        UpdateOrDeleteExercisePresenter(
            view,
            get()
        )
    }

}