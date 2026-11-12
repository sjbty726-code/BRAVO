package com.bravohelper

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

class BravoService : AccessibilityService() {
    private val handler = Handler(Looper.getMainLooper())
    private val delay = 1500L

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.packageName != "istinara.bravocash") return
        handler.postDelayed({ runStep() }, delay)
    }

    private fun runStep() {
        when (State.step) {
            State.STEP_START -> {
                tap(500f, 260f) // خانة الرقم
                handler.postDelayed({
                    pasteText(State.phoneNumber)
                    State.step = State.STEP_CLICK_CONTINUE
                }, 800)
            }
            State.STEP_CLICK_CONTINUE -> {
                tap(360f, 1480f) // زر استمرار
                State.step = State.STEP_WAIT_OTP
            }
            State.STEP_WAIT_OTP -> {
                if (State.otp.isNotEmpty()) {
                    State.step = State.STEP_FILL_OTP
                    runStep()
                }
            }
            State.STEP_FILL_OTP -> {
                tap(500f, 300f) // خانة الـ OTP
                handler.postDelayed({
                    pasteText(State.otp)
                    State.step = State.STEP_CONFIRM_OTP
                }, 800)
            }
            State.STEP_CONFIRM_OTP -> {
                tap(360f, 1480f) // زر تأكيد
                State.step = State.STEP_DONE
            }
        }
    }

    private fun tap(x: Float, y: Float) {
        val path = Path().apply { moveTo(x, y) }
        val gesture = GestureDescription.Builder()
            .addStroke(GestureDescription.StrokeDescription(path, 0, 100))
            .build()
        dispatchGesture(gesture, null, null)
    }

    private fun pasteText(text: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText("text", text))
        
        val root = rootInActiveWindow ?: return
        val focused = root.findFocus(AccessibilityNodeInfo.FOCUS_INPUT) ?: return
        
        val args = Bundle()
        args.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text)
        focused.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, args)
    }

    override fun onInterrupt() {}
}
