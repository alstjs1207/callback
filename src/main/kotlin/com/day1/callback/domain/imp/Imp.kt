package com.day1.callback.domain.imp

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.io.Serializable

@RedisHash("imp")
data class Imp(
    @Id
    var imp_uid: String,
    var merchant_uid: String,
    var status: String): Serializable