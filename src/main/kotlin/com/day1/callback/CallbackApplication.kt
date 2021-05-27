package com.day1.callback

import com.day1.callback.config.ImpConfigProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(ImpConfigProperties::class)
class CallbackApplication

fun main(args: Array<String>) {
	runApplication<CallbackApplication>(*args)
}
