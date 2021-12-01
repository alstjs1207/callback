package com.day1.callback.service.cloudrun

import com.day1.callback.aspect.ChannelsAspect
import com.day1.callback.service.exception.ErrorCode
import com.day1.callback.service.exception.ErrorException
import com.day1.callback.util.CommonDef
import mu.KotlinLogging
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate
import org.springframework.stereotype.Service
import org.springframework.util.concurrent.ListenableFuture

private val logger = KotlinLogging.logger {}

@Service
class CloudPublisher(private val pubSubTemplate: PubSubTemplate, val channelsAspect: ChannelsAspect) {

    fun getCloudPublisher(message: String?, channel: String) {
        val topic = convertChannelIntoCloudTopicName(channel)
        logger.info { "$topic , $message" }
        val messageIdFuture: ListenableFuture<String>? = pubSubTemplate.publish(topic, message)
        val messageId = messageIdFuture!!.get()
        logger.info { "Published message ID: $messageId" }

    }

    private fun convertChannelIntoCloudTopicName(channel: String): String {
        val channelName: List<String> = channel.split(":", limit = 3)
        val topic = when(channelName.last()) {
            CommonDef.PING_BUS.key -> {
                channelsAspect.toCloudChannelName(CommonDef.PING_BUS.cloudPubName)
            }
            CommonDef.EMPLOYEE_BUS.key -> {
                channelsAspect.toCloudChannelName(CommonDef.EMPLOYEE_BUS.cloudPubName)
            }
            CommonDef.EMAIL_BUS.key -> {
                channelsAspect.toCloudChannelName(CommonDef.EMAIL_BUS.cloudPubName)
            }
            else -> throw ErrorException(ErrorCode.NO_CHANNEL)
        }
        return topic
    }
}