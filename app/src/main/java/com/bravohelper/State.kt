package com.bravohelper

object State {
    var phoneNumber: String = ""
    var referralCode: String = "F2AKUX"
    var otp: String = ""
    var step: Int = 0

    const val STEP_START = 1
    const val STEP_CLICK_CONTINUE = 2
    const val STEP_WAIT_OTP = 3
    const val STEP_FILL_OTP = 4
    const val STEP_CONFIRM_OTP = 5
    const val STEP_DONE = 6
}
