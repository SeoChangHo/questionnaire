package com.fineinsight.zzango.questionnaire.LocalList

import java.io.Serializable


//로컬 저장 리스트
data class Paper(
var isChecked: Boolean,
var exam_no: String,
var signature: ByteArray,
var first_serial: String,
var last_serial: String,
var name: String
) : Serializable


//구강검진 리스트
data class Paper_ORAL(
var exam_date:String,
var exam_no:String,
var name:String,
var first_serial:String,
var last_serial:String,
var category:String,
var oral_1 :String,
var oral_2 :String,
var oral_3 :String,
var oral_4 :String,
var oral_5 :String,
var oral_6 :String,
var oral_7 :String,
var oral_8 :String,
var oral_9 :String,
var oral_10:String,
var oral_11:String,
var oral_12:String,
var oral_13:String,
var oral_14:String,
var oral_15:String,
var oral_16:String,
var oral_Remark :String
): Serializable

//공통 리스트
data class Paper_COMMON(

var exam_date :String,
var exam_no :String,
var name :String,
var first_serial :String,
var last_serial :String,
var category :String,
var mj1_1_1 :String,
var mj1_1_2 :String,
var mj1_2_1 :String,
var mj1_2_2 :String,
var mj1_3_1 :String,
var mj1_3_2 :String,
var mj1_4_1 :String,
var mj1_4_2 :String,
var mj1_5_1 :String,
var mj1_5_2 :String,
var mj1_6_1 :String,
var mj1_6_2 :String,
var mj1_7_1 :String,
var mj1_7_2 :String,
var mj2_1 :String,
var mj2_2 :String,
var mj2_3 :String,
var mj2_4 :String,
var mj2_5 :String,
var mj3 :String,
var mj4 :String,
var mj4_1_1 :String,
var mj4_1_2 :String,
var mj4_2_1 :String,
var mj4_2_2 :String,
var mj4_2_3 :String,
var mj5 :String,
var mj5_1_1 :String,
var mj5_1_2 :String,
var mj5_2_1 :String,
var mj5_2_2 :String,
var mj5_2_3 :String,
var mj6 :String,
var mj6_1 :String,
var mj71 :String,
var mj72 :String,
var mj73 :String,
var mj74 :String,
var mj7_1_11 :String,
var mj7_1_12 :String,
var mj7_1_13 :String,
var mj7_1_14 :String,
var mj7_1_21 :String,
var mj7_1_22 :String,
var mj7_1_23 :String,
var mj7_1_24 :String,
var mj7_1_31 :String,
var mj7_1_32 :String,
var mj7_1_33 :String,
var mj7_1_34 :String,
var mj7_1_41 :String,
var mj7_1_42 :String,
var mj7_1_43 :String,
var mj7_1_44 :String,
var mj7_1_51 :String,
var mj7_1_52 :String,
var mj7_1_53 :String,
var mj7_1_54 :String,
var mj7_1_etc :String,
var mj7_2_11 :String,
var mj7_2_12 :String,
var mj7_2_13 :String,
var mj7_2_14 :String,
var mj7_2_21 :String,
var mj7_2_22 :String,
var mj7_2_23 :String,
var mj7_2_24 :String,
var mj7_2_31 :String,
var mj7_2_32 :String,
var mj7_2_33 :String,
var mj7_2_34 :String,
var mj7_2_41 :String,
var mj7_2_42 :String,
var mj7_2_43 :String,
var mj7_2_44 :String,
var mj7_2_51 :String,
var mj7_2_52 :String,
var mj7_2_53 :String,
var mj7_2_54 :String,
var mj7_2_etc :String,
var mj8_1 :String,
var mj8_2_1 :String,
var mj8_2_2 :String,
var mj9_1 :String,
var mj9_2_1 :String,
var mj9_2_2 :String,
var mj10 :String
):Serializable


//정신건강
data class Paper_MENTAL(
var exam_date:String,
var exam_no:String,
var name:String,
var first_serial:String,
var last_serial :String,
var category :String,
var mj_mtl_1 :String,
var mj_mtl_2 :String,
var mj_mtl_3 :String,
var mj_mtl_4 :String,
var mj_mtl_5 :String,
var mj_mtl_6 :String,
var mj_mtl_7 :String,
var mj_mtl_8 :String,
var mj_mtl_9 :String,
var mj_mtl_sum :String
):Serializable


//인지기능
data class Paper_COGNITIVE(
var exam_date :String,
var exam_no :String,
var name :String,
var first_serial :String,
var last_serial :String,
var category :String,
var mj_inji_1 :String,
var mj_inji_2 :String,
var mj_inji_3 :String,
var mj_inji_4 :String,
var mj_inji_5 :String,
var mj_inji_6 :String,
var mj_inji_7 :String,
var mj_inji_8 :String,
var mj_inji_9 :String,
var mj_inji_10 :String,
var mj_inji_11 :String,
var mj_inji_12 :String,
var mj_inji_13 :String,
var mj_inji_14 :String,
var mj_inji_15 :String,
var mj_inji_sum :String
):Serializable

//노인기능
data class Paper_ELDERLY(
var exam_date:String,
var exam_no :String,
var name :String,
var first_serial:String,
var last_serial :String,
var category :String,
var mj66_1 :String,
var mj66_2 :String,
var mj66_3_1:String,
var mj66_3_2:String,
var mj66_3_3:String,
var mj66_3_4:String,
var mj66_3_5:String,
var mj66_3_6:String,
var mj66_4 :String,
var mj66_5 :String
):Serializable

//흡연
data class Paper_SMOKING(
var exam_date:String,
var exam_no :String,
var name :String,
var first_serial:String,
var last_serial :String,
var category :String,
var sg2_spSmoke1 :String,
var sg2_spSmoke2 :String,
var sg2_spSmoke3:String,
var sg2_spSmoke4:String,
var sg2_spSmoke5:String,
var sg2_spSmoke6:String,
var sg2_spSmoke7:String,
var sg2_spSmoke8:String,
var sg2_spSmokeSum :String
):Serializable

//음주
data class Paper_DRINKING(
var exam_date:String,
var exam_no :String,
var name :String,
var first_serial:String,
var last_serial :String,
var category :String,
var sg2_spDrink1 :String,
var sg2_spDrink2_1 :String,
var sg2_spDrink2_2:String,
var sg2_spDrink3:String,
var sg2_spDrink4:String,
var sg2_spDrink5:String,
var sg2_spDrink6:String,
var sg2_spDrink7:String,
var sg2_spDrink8 :String,
var sg2_spDrink9 :String,
var sg2_spDrink10 :String,
var sg2_spDrinkSum :String
):Serializable

//암
data class Paper_CANCER(
var exam_date:String,
var exam_no :String,
var name :String,
var first_serial:String,
var last_serial :String,
var category :String,
var ck1 :String,
var ck1_1 :String,
var ck2:String,
var ck2_1:String,
var ck3_1:String,
var ck3_1_1:String,
var ck3_1_2:String,
var ck3_1_3:String,
var ck3_1_4:String,
var ck3_1_5:String,
var ck3_2:String,
var ck3_2_1:String,
var ck3_2_2:String,
var ck3_2_3:String,
var ck3_2_4:String,
var ck3_2_5:String,
var ck3_3:String,
var ck3_3_1:String,
var ck3_3_2:String,
var ck3_3_3:String,
var ck3_3_4:String,
var ck3_3_5:String,
var ck3_4:String,
var ck3_4_1:String,
var ck3_4_2:String,
var ck3_4_3:String,
var ck3_4_4:String,
var ck3_4_5:String,
var ck3_5:String,
var ck3_5_1:String,
var ck3_5_2:String,
var ck3_5_3:String,
var ck3_5_4:String,
var ck3_5_5:String,
var ck3_6:String,
var ck3_6_1:String,
var ck3_6_2:String,
var ck3_6_3:String,
var ck3_6_4:String,
var ck3_6_5:String,
var ck3_6_kita:String,
var ck4_1:String,
var ck4_2:String,
var ck4_3:String,
var ck4_4:String,
var ck4_5:String,
var ck4_6:String,
var ck4_7:String,
var ck4_8:String,
var ck4_9:String,
var ck5_1:String,
var ck5_2:String,
var ck5_3:String,
var ck5_4:String,
var ck5_5:String,
var ck6_1:String,
var ck6_2:String,
var ck6_3:String,
var ck6_4:String,
var ck6_5:String,
var ck7_1:String,
var ck7_2:String,
var ck7_3:String,
var ck7_4:String,
var ck7_5:String,
var ck8_1 :String,
var ck8_2 :String,
var ck9_1 :String,
var ck9_2 :String,
var ck10 :String,
var ck11 :String,
var ck12 :String,
var ck13 :String,
var ck14 :String,
var ck15_5 :String,
var ck15_5_1 :String,
var ck15_5_2 :String,
var ck15_5_3 :String,
var ck15_5_4 :String,
var ck15_5_5 :String,
var ck16_1 :String,
var ck16_2 :String,
var ck16_3 :String,
var ck16_4 :String,
var ck16_5 :String,
var ck16_6 :String
):Serializable

data class Paper_EXERCISE (
var exam_date : String,
var exam_bun_no : String,
var name : String,
var first_serial : String,
var last_serial : String,
var category : String,
var sg2_spSports1_1 : String,
var sg2_spSports1_2 : String,
var sg2_spSports1_3_1 : String,
var sg2_spSports1_3_2 : String,
var sg2_spSports1_4 : String,
var sg2_spSports1_5 : String,
var sg2_spSports1_6_1 : String,
var sg2_spSports1_6_2 : String,
var sg2_spSports2_1 : String,
var sg2_spSports2_2 : String,
var sg2_spSports2_3_1 : String,
var sg2_spSports2_3_2 : String,
var sg2_spSports3_1 : String,
var sg2_spSports3_2 : String,
var sg2_spSports3_3_1 : String,
var sg2_spSports3_3_2 : String,
var sg2_spSports3_4 : String,
var sg2_spSports3_5 : String,
var sg2_spSports3_6_1 : String,
var sg2_spSports3_6_2 : String,
var sg2_spSports4_1_1 : String,
var sg2_spSports4_1_2 : String,
var sg2_spSports5 : String,
var sg2_spSports6 : String,
var sg2_spSports7 : String,
var sg2_spSports8 : String,
var sg2_spSports9 : String,
var sg2_spSports10 : String,
var sg2_spSports11 : String,
var sg2_spSports12 : String,
var sg2_spSportsSum : String
) : Serializable

data class Paper_NUTRITION (
var exam_date : String,
var exam_bun_no : String,
var name : String,
var first_serial : String,
var last_serial : String,
var category : String,
var sg2_spFood1 : String,
var sg2_spFood2 : String,
var sg2_spFood3 : String,
var sg2_spFood4 : String,
var sg2_spFood5 : String,
var sg2_spFood6 : String,
var sg2_spFood7 : String,
var sg2_spFood8 : String,
var sg2_spFood9 : String,
var sg2_spFood10 : String,
var sg2_spFood11 : String,
var sg2_spFoodSum : String,
var sg2_spHeight : String,
var sg2_spWeight : String,
var sg2_spWaistSize : String,
var sg2_spBmi : String,
var sg2_spFat1 : String,
var sg2_spFat2 : String,
var sg2_spFat3 : String
) : Serializable


data class Paper_AGREE (
        var HOSPITAL:String,
        var SYS_DATE:String,
        var USER_ID:String,
        var UPD_DATE:String,
        var BUNHO:String,
        var IO_GUBUN:String,
        var BASIC:String,
        var GUNJIN:String,
        var MOBILE:String,
        var EVENT:String,
        var SMS:String,
        var CONSULT:String,
        var DAERI:String,
        var GOYU:String,
        var MINGAM:String,
        var SCAN:String,
        var CAR_NO:String,
        var NAME:String,
        var JUMIN:String,
        var SIGN:ByteArray
) : Serializable













data class READ_AGREE (
        var HOSPITAL:String,
        var SYS_DATE:String,
        var USER_ID:String,
        var UPD_DATE:String,
        var BUNHO:String,
        var IO_GUBUN:String,
        var BASIC:String,     //개인정보 최소정보 제공동의
        var GUNJIN:String,    //건진실시 따른 사전사후 서비스 관련 정보 제공
        var MOBILE:String,    //진료예약, 입원 및 검사예정에 따른 모바일 안내
        var EVENT:String,     //병원이용 및 병원의 새로운 서비스, 행사정보안내
        var SMS:String,       //건강정보 발송을 위한 휴대폰 SMS 발송
        var CONSULT:String,   //병원간의 상호 진료협력을 위해 의료정보 제공
        var DAERI:String,     //환자대리인 정보이용 동의
        var GOYU:String,      //고유식별 정보
        var MINGAM:String,    //민감정보
        var SCAN:String,
        var CAR_NO:String,
        var NAME:String,
        var JUMIN:String,
        var SIGN: SIGNINFO
) : Serializable


data class SIGNINFO(
        val type:String,
        val data:ByteArray
) : Serializable
