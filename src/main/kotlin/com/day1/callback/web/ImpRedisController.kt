package com.day1.callback.web

import com.day1.callback.domain.imp.Imp
import com.day1.callback.domain.imp.ImpRepository
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import mu.KLogging
import org.springframework.data.redis.core.ListOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/redis")
class ImpRedisController (val impRepository: ImpRepository, val redisTemplate: RedisTemplate<String, String>){
    companion object: KLogging()

    @PostMapping("/pg/imp")
    fun ImpCall(@RequestBody imp: Imp): ResponseEntity<Any> {
        impRepository.save(imp)

        val data = HashMap<String, Any>()
        data["status"] = "Success"
        return ResponseEntity.ok(data)
    }

    @PostMapping("/pg/imp2")
    fun ImpCall2(@RequestBody imp: Imp): Imp {
        logger.info { "redis-controller" }
        val key = "bus:0:pg:imp"
        val om = jacksonObjectMapper()
        var jsonStr = om.writeValueAsString(imp)

        val list: ListOperations<String,String> = redisTemplate.opsForList()
        list.set(key,0,jsonStr)
        //redisTemplate.opsForValue().set("pg:imp",jsonStr)
        val jsonStr2 = list.index(key,0)
        logger.info { "data = $jsonStr2" }
        return om.readValue(jsonStr2 ?: "{}", Imp::class.java)

    }

}