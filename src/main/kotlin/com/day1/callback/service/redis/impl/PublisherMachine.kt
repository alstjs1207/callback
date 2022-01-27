package com.day1.callback.service.redis.impl

import com.day1.callback.service.exception.ErrorCode
import com.day1.callback.service.exception.ErrorException
import com.day1.callback.service.redis.BasePublisher
import com.day1.callback.service.redis.DaumPublisher
import com.day1.callback.service.redis.GooglePublisher
import com.day1.callback.service.redis.NaverPublisher
import com.day1.callback.util.SiteDef
import org.springframework.stereotype.Service

@Service
class PublisherMachine(
    val daum: DaumPublisher,
    val naver: NaverPublisher,
    val google: GooglePublisher,
) {

    fun getSiteRedisPublisher(site: String): BasePublisher {
        return when (site) {
            SiteDef.DAUM.name -> {
                daum
            }
            SiteDef.NAVER.name -> {
                naver
            }
            SiteDef.GOOGLE.name -> {
                google
            }
            SiteDef.NATE.name -> {
                daum
            }
            else -> throw ErrorException(ErrorCode.NO_SITE)
        }
    }
}
