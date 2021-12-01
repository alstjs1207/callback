package com.day1.callback.util

enum class CommonDef(val key: String, val cloudPubName: String) {
    IMP_BUS("pg:imp", "pgImp"),
    PING_BUS("ping", "ping"),
    EMAIL_BUS("send:email", "email"),
    EMPLOYEE_BUS("create:bulk:employee", "employeeBulkCreate"),
    EXCEL_BUS("create:export:excel", "createExportExcelFile");
}