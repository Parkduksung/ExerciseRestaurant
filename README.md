# **ExerciseRestaurant**

![exercise_restaurant_main_icon](https://user-images.githubusercontent.com/54328309/80931858-76ee7900-8df7-11ea-900a-e5a0f8fd6988.PNG)


ExerciseRestaurant App 은 위치에 따른 주변 헬스장 정보 및 식단, 운동을 기록하여 볼 수 있는 앱이다. 

## 기획의도

운동을 좋아하는 나에겐 매일 운동하는 습관이 있다. 그래서 약속이 있거나 하는 등의 일이 생기게 되면 기존에 다니던 헬스장을 가지 못해 항상 그 주변에 헬스장에 대해서 검색하는데 이러한 기능이 있는 앱을 만들어서 사용하면 편리하겠다고 생각이 들어 이 앱을 기획하게 되었다. 그러면서 항상 해야지 해야지 생각만 했던 먹었던 음식들이나 운동들을 기록하는 것을 추가하면 좋을 것 같다고 생각이 들어 기획에 추가하여 앱을 만들었다.

## 결과

![start!](https://user-images.githubusercontent.com/54328309/80933840-4bbc5780-8e00-11ea-96d5-a4eef147c9d7.gif)
![2!](https://user-images.githubusercontent.com/54328309/80933815-2b8c9880-8e00-11ea-9518-0abe6b540d83.gif)
![4!](https://user-images.githubusercontent.com/54328309/80933831-3fd09580-8e00-11ea-9dff-d33a5c28c32e.gif)

## Libraries and tools

- [Shared Preferences](https://developer.android.com/reference/kotlin/android/content/SharedPreferences?hl=en)
- [Constraint Layout](https://developer.android.com/reference/androidx/constraintlayout/widget/ConstraintLayout?hl=en)
- [RoomDB](https://developer.android.com/jetpack/androidx/releases/room)
- [Retrofit](https://square.github.io/retrofit/)
- [Material Design](https://material.io/develop/android/docs/getting-started/)
- [Firebase](https://firebase.google.com/docs/reference/android/com/google/firebase/package-summary?hl=ko)
- [Ted Permission](https://github.com/ParkSangGwon/TedPermission)
- [Prolificinteractive Calendarview](https://github.com/prolificinteractive/material-calendarview)
- [Crashlytics](https://firebase.google.com/docs/reference/android/com/google/firebase/crashlytics/FirebaseCrashlytics?hl=ko)
- [FileZilla](https://filezilla-project.org/)
- [Cafe24](https://www.cafe24.com/)
- [SQLGate for MySQL](https://www.sqlgate.com/)
- [Gson](https://github.com/google/gson/blob/master/UserGuide.md)
- [KAKAO REST API](https://developers.kakao.com/docs/latest/ko/local/dev-guide)
- [KAKAO MAP](https://apis.map.kakao.com/android/guide/)

## Architecture

이 앱은 MVP 구조를 사용하였다. 
- Model      :  Data와 연관된 전반적인 처리를 담당 (로컬/네트워크 포함).
- View       :  사용자 이벤트 발생시, Presenter로 전달.
- Presenter  :  View에게 전달받은 이벤트 처리하여 다시 View로 전달. 
- 상호 의존성을 떨어트림. 
  (View가 전달받은 data를 Presenter에서 가공하여 전달하므로)
- data 혹은 결과값에 대한 테스트가 편함.

![구조](https://user-images.githubusercontent.com/54328309/80933982-eddc3f80-8e00-11ea-9a4f-b6f2a7d9ac68.PNG)
