package com.day1.callback.web.dto

data class ImpResponseDto (
    var imp_uid: String,
    var merchant_uid: String,
    var status: String,
    var imp_success: Boolean
    )