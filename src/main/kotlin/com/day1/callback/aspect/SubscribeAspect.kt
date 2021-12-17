package com.day1.callback.aspect

import org.springframework.stereotype.Component

//@Aspect
@Component
class SubscribeAspect(
    val channelsAspect: ChannelsAspect
) {

//    @Before("execution(* com.day1.callback.service.redis.*impl.*start*(...))")
//    fun startSub(joinPoint: JoinPoint) {
//        ChannelsAspect.channels
//    }
}