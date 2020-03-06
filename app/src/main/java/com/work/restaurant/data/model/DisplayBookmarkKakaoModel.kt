package com.work.restaurant.data.model

data class DisplayBookmarkKakaoModel(
    val displayName: String,
    val displayUrl: String,
    val displayAddress: String,
    var toggleBookmark: Boolean,
    val distance: String
) {
    fun toBookmarkModel(userId: String): BookmarkModel =
        BookmarkModel(
            userId,
            displayName,
            displayUrl,
            displayAddress
        )
}