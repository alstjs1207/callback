package com.day1.callback.util

enum class CommonDef(val key: String) {
    IMP_BUS("pg:imp"),
    PING_BUS("ping"),
    EMAIL_BUS("send:email"),
    EMPLOYEE_BUS("create:employee"),
    EXCEL_BUS("create:export:excel");
}
