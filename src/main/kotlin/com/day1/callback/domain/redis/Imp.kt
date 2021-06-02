package com.day1.callback.domain.redis
import java.io.Serializable

data class Imp(
    var imp_uid: String,
    var merchant_uid: String,
    var status: String): Serializable