package com.fineinsight.zzango.questionnaire

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_agreement.*

class AgreementActivity : RootActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agreement)

        agreeAll.setOnClickListener { agreeAll() }
        disAgreeAll.setOnClickListener { disAgreeAll() }

    }

    fun agreeAll(){
        noticeWarningAgree.isChecked = true
        uniqueInfoAgree.isChecked = true
        sensitiveInfoAgree.isChecked = true
        beforeAfterInfoProvideAgree.isChecked = true
        mobileInfoAgree.isChecked = true
        hospitalEventInfoAgree.isChecked = true
        sendSMSAgree.isChecked = true
        MedicalCooperationInfoAgree.isChecked = true
        patientInfoAgree.isChecked = true
    }

    fun disAgreeAll(){
        noticeWarningDisAgree.isChecked = true
        uniqueInfoDisAgree.isChecked = true
        sensitiveInfoDisAgree.isChecked = true
        beforeAfterInfoProvideDisAgree.isChecked = true
        mobileInfoDisAgree.isChecked = true
        hospitalEventInfoDisAgree.isChecked = true
        sendSMSDisAgree.isChecked = true
        MedicalCooperationInfoDisAgree.isChecked = true
        patientInfoDisAgree.isChecked = true
    }

}