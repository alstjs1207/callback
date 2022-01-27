package com.day1.callback.service.exception

enum class ErrorCode(
    val status: Int,
    val rtnCode: String,
    val rtnMsg: String
) {
    NO_CHANNEL(400, "ER001", "채널이 없습니다."),
    NO_REGISTER_CHANNEL(400, "ER002", "채널이 등록되지 않았습니다."),
    NOT_CONVERTER(400, "ERR003", "데이터 변환 중 에러가 발생했습니다."),
    NO_SITE(400, "ER004", "사이트가 없습니다.")
}
