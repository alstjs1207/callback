package com.day1.callback.domain.imp

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ImpRepository : CrudRepository<Imp, String>{
}