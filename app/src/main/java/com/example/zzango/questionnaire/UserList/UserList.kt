package com.example.zzango.questionnaire.UserList

import java.io.Serializable


data class UserList(var user: String, var pass: String) : Serializable

var USER1 = "qwer"
var USER1PASS = "1q2w3e4r"

var USER2 = "fine"
var USER2PASS = "1111"

var USER3 = "qq"
var USER3PASS ="1111"


object User
{
    val Map: HashMap<String, String> = hashMapOf(USER1 to USER1PASS, USER2 to USER2PASS, USER3 to USER3PASS)
}