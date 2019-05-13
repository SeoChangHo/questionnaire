package com.fineinsight.zzango.questionnaire.UserList

import java.io.Serializable


data class UserList(var user: String, var pass: String) : Serializable


var USER1 = "fine"
var USER1PASS = "1111"

var USER2 = "mokpohos"
var USER2PASS = "mokpohos1678"

var USER3 = "hanshin"
var USER3PASS = "hanshin1678"

var USER4 = "bestian"
var USER4PASS = "best1234"


object User
{
    val Map: HashMap<String, String> = hashMapOf(USER1 to USER1PASS, USER2 to USER2PASS, USER3 to USER3PASS, USER4 to USER4PASS)
}