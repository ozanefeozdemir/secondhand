package com.project.secondhand.model

import java.time.LocalDate

data class BaseEntity (
    val createdDate: LocalDate? = null,
    val updatedDate: LocalDate? = null,
){
}