package com.example.groupalarm.data

import java.util.*
import kotlin.collections.ArrayList

data class Alarm(
    var time: Date = Date(),
    var isActive: Boolean = true,
    var users: ArrayList<User> = ArrayList()
)