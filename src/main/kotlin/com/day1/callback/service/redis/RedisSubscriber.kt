package com.day1.callback.service.redis

import org.springframework.data.redis.connection.MessageListener

interface RedisSubscriber: MessageListener {

}