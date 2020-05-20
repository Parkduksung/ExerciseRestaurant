package com.work.restaurant

import com.work.restaurant.data.repository.notification.NotificationRepository
import com.work.restaurant.data.repository.notification.NotificationRepositoryImpl
import com.work.restaurant.data.repository.question.QuestionRepository
import com.work.restaurant.data.repository.question.QuestionRepositoryImpl
import com.work.restaurant.data.repository.user.UserRepository
import com.work.restaurant.data.repository.user.UserRepositoryImpl
import com.work.restaurant.data.source.remote.notification.NotificationRemoteDataSourceImpl
import com.work.restaurant.data.source.remote.question.QuestionRemoteDataSourceImpl
import com.work.restaurant.data.source.remote.user.UserRemoteDataSourceImpl
import com.work.restaurant.network.RetrofitInstance


object Injection {

//    fun provideBookmarkRepository(): BookmarkRepository =
//        BookmarkRepositoryImpl.getInstance(
//            BookmarkLocalDataSourceImpl(
//                AppExecutors(),
//                BookmarkDatabase.getInstance(
//                    App.instance.context()
//                )
//            )
//        )

//    fun provideExerciseRepository(): ExerciseRepository =
//        ExerciseRepositoryImpl.getInstance(
//            ExerciseLocalDataSourceImpl(
//                AppExecutors(),
//                ExerciseDatabase.getInstance(
//                    App.instance.context()
//                )
//            )
//
//        )

//    fun provideKakaoRepository(): KakaoRepository =
//        KakaoRepositoryImpl.getInstance(
//            KakaoRemoteDataSourceImpl.getInstance(
//                RetrofitInstance.getInstance(
//                    KAKAO_URL
//                )
//            )
//        )


    fun provideNotificationRepository(): NotificationRepository =
        NotificationRepositoryImpl.getInstance(
            NotificationRemoteDataSourceImpl.getInstance(
                RetrofitInstance.getInstance(
                    WEB_URL
                )
            )
        )

    fun provideQuestionRepository(): QuestionRepository =
        QuestionRepositoryImpl.getInstance(
            QuestionRemoteDataSourceImpl.getInstance(
                RetrofitInstance.getInstance(
                    WEB_URL
                )
            )
        )

    fun provideUserRepository(): UserRepository =
        UserRepositoryImpl.getInstance(
            UserRemoteDataSourceImpl.getInstance(
                RetrofitInstance.getInstance(
                    WEB_URL
                )
            )
        )

//    fun provideLoginRepository(): LoginRepository =
//        LoginRepositoryImpl.getInstance(
//            LoginLocalDataSourceImpl.getInstance(
//                AppExecutors(),
//                LoginDatabase.getInstance(
//                    App.instance.context()
//                )
//            )
//        )


    const val WEB_URL = "https://duksung12.cafe24.com"
    const val KAKAO_URL = "https://dapi.kakao.com/"
}