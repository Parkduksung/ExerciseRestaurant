package com.work.restaurant.data.di

import com.work.restaurant.data.repository.bookmark.BookmarkRepository
import com.work.restaurant.data.repository.bookmark.BookmarkRepositoryImpl
import com.work.restaurant.data.repository.eat.EatRepository
import com.work.restaurant.data.repository.eat.EatRepositoryImpl
import com.work.restaurant.data.repository.exercise.ExerciseRepository
import com.work.restaurant.data.repository.exercise.ExerciseRepositoryImpl
import com.work.restaurant.data.repository.kakao.KakaoRepository
import com.work.restaurant.data.repository.kakao.KakaoRepositoryImpl
import com.work.restaurant.data.repository.login.LoginRepository
import com.work.restaurant.data.repository.login.LoginRepositoryImpl
import com.work.restaurant.data.repository.notification.NotificationRepository
import com.work.restaurant.data.repository.notification.NotificationRepositoryImpl
import com.work.restaurant.data.repository.question.QuestionRepository
import com.work.restaurant.data.repository.question.QuestionRepositoryImpl
import com.work.restaurant.data.repository.road.RoadRepository
import com.work.restaurant.data.repository.road.RoadRepositoryImpl
import com.work.restaurant.data.repository.user.UserRepository
import com.work.restaurant.data.repository.user.UserRepositoryImpl
import org.koin.dsl.module

val repositoryModule =
    module {
        single<EatRepository> { EatRepositoryImpl(get()) }
        single<ExerciseRepository> { ExerciseRepositoryImpl(get()) }
        single<BookmarkRepository> { BookmarkRepositoryImpl(get()) }
        single<LoginRepository> { LoginRepositoryImpl(get()) }
        single<RoadRepository> { RoadRepositoryImpl(get()) }
        single<KakaoRepository> { KakaoRepositoryImpl(get()) }
        single<UserRepository> { UserRepositoryImpl(get()) }
        single<NotificationRepository> { NotificationRepositoryImpl(get()) }
        single<QuestionRepository> { QuestionRepositoryImpl(get()) }
    }