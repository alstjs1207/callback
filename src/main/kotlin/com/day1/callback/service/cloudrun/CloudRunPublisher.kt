package com.day1.callback.service.cloudrun

import com.day1.callback.aspect.ChannelsAspect
import com.day1.callback.service.exception.ErrorCode
import com.day1.callback.service.exception.ErrorException
import com.day1.callback.util.CommonDef
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForObject

private val logger = KotlinLogging.logger {}

@Service
class CloudRunPublisher(val channelsAspect: ChannelsAspect) {

    fun sendCloudRun(message: String?, channel: String) {
        logger.info { "channel: $channel message: $message" }
        var url = ""
        when (channel) {
            channelsAspect.toChannelName(CommonDef.PING_BUS.key) -> {
                url = "https://XXX/.ping"
            }
            channelsAspect.toChannelName(CommonDef.EMPLOYEE_BUS.key) -> {
            }
            else -> throw ErrorException(ErrorCode.NO_CHANNEL)
        }
        val result: String = RestTemplate().postForObject(url, message, String.Companion)
        logger.info { result }
    }
}