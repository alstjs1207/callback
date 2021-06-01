package com.day1.callback.web

import com.day1.callback.domain.imp.Imp
import com.day1.callback.domain.imp.ImpRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/redis")
class ImpRedisController (val impRepository: ImpRepository){

    @PostMapping("/pg/imp")
    fun ImpCall(@RequestBody imp: Imp): ResponseEntity<Any> {
        impRepository.save(imp)

        val data = HashMap<String, Any>()
        data["status"] = "Success"
        return ResponseEntity.ok(data)
    }
}