package com.example.kotlinsampie.model

import java.util.*

//======SQLite 1 sample

data class StudentModel(
    var id: Int = getAutoId(),
    var name:String = "",
    var email:String = ""
){
    companion object{
        fun getAutoId():Int{
            val random = Random()
            return random.nextInt(100)
        }
    }
}