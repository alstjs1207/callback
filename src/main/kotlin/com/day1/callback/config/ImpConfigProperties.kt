package com.day1.callback.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.stereotype.Component

@ConstructorBinding
@ConfigurationProperties(prefix = "iamport")
data class ImpConfigProperties (
    val endpoint: String
)