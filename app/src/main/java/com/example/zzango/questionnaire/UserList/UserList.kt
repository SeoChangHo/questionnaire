package com.example.zzango.questionnaire.UserList

import java.io.Serializable


data class UserList(var user: String, var pass: String) : Serializable

var jeongyeon = "qwer"
var jeongyeonpass = "1q2w3e4r"

var changho = "changho"
var changhopass = "changho"


object User
{
    val Map: HashMap<String, String> = hashMapOf(jeongyeon to jeongyeonpass, changho to changhopass)
}