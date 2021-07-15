package com.day1.callback.service.redis.impl

import com.day1.callback.service.exception.ErrorCode
import com.day1.callback.service.exception.ErrorException
import com.day1.callback.service.redis.BasePublisher
import com.day1.callback.service.redis.DaumPublisher
import com.day1.callback.service.redis.GooglePublisher
import com.day1.callback.service.redis.NaverPublisher
import com.day1.callback.util.CommonDef
import org.springframework.stereotype.Service

@Service
class PublisherMachine(
    val daum: DaumPublisher,
    val naver: NaverPublisher,
    val google: GooglePublisher,
) {

    fun getSiteRedisPublisher(site: String): BasePublisher {
        return when (site) {
            CommonDef.DAUM.name -> {
                daum
            }
            CommonDef.NAVER.name -> {
                naver
            }
            CommonDef.GOOGLE.name -> {
                google
            }
            CommonDef.NATE.name -> {
                daum
            }
            else -> throw ErrorException(ErrorCode.NO_SITE)
        }
    }

}