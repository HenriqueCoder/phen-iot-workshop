package com.phen.firebasesensor

import java.text.SimpleDateFormat
import java.util.*

class Alerts (){
    var humidity: Long = 0
    var timestamp: Long = 0
    var data : String = ""
        get() {
            val format = SimpleDateFormat("dd/MM/yyy HH:mm::ss")
            return format.format(Date(timestamp))
        }
}
