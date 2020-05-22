package com.meteoro.omdbarch.dsl

import com.meteoro.omdbarch.components.R

enum class Visibility {
    DISPLAYED, HIDDEN
}

enum class ErrorImage(val resource: Int) {
    IMAGE_SERVER_DOWN(R.drawable.img_server_down),
    IMAGE_NETWORK_ISSUE(R.drawable.img_network_issue),
    IMAGE_NO_RESULTS(R.drawable.img_no_results),
    IMAGE_NO_CONNECT(R.drawable.img_no_connect),
    IMAGE_BUG_FOUND(R.drawable.img_bug_found)
}

enum class ErrorMessage(val resource: Int) {
    MESSAGE_SERVER_DOWN(R.string.error_server_down),
    MESSAGE_NETWORK_ISSUE(R.string.error_network),
    MESSAGE_NO_RESULTS(R.string.error_no_results),
    MESSAGE_NO_CONNECT(R.string.error_no_connectivity),
    MESSAGE_BUG_FOUND(R.string.error_bug_found),
}
