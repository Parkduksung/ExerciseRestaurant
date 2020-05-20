package com.work.restaurant.data.model

data class KakaoSearchModel(
    val addressName: String,
    val distance: String,
    val phone: String,
    val placeName: String,
    val placeUrl: String,
    val locationX: String,
    val locationY: String
) {
    fun toBookmarkModel(userId: String): BookmarkModel =
        BookmarkModel(
            userId,
            placeName,
            placeUrl,
            addressName
        )

    fun toDisplayBookmarkKakaoModel(toggleBookmark: Boolean): DisplayBookmarkKakaoModel =
        DisplayBookmarkKakaoModel(
            placeName,
            placeUrl,
            addressName,
            toggleBookmark,
            distance
        )

}
