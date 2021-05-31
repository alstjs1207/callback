package com.day1.callback.service.imp

import com.day1.callback.web.dto.ImpRequestDto
import com.day1.callback.web.dto.ImpResponseDto

interface ImpServiceImpl {

    /**
     * day1 callback api 호출
     */
    fun callbackDay1(impRequestDto: ImpRequestDto): ImpResponseDto
}