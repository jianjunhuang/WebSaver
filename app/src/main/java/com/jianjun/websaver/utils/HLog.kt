package com.jianjun.websaver.utils

import android.util.Log

object HLog {
    private val isDebugEnabled: Boolean
        get() = true

    //get invoke class name
    private val tag: String
        get() = Exception().stackTrace[1].className //get invoke class name

    //get invoke method
    private val invokedMethod: String
        get() = Exception().stackTrace[1].methodName //get invoke method

    fun i(msg: String, vararg args: Any) {
        if (isDebugEnabled) {
            Log.i(
                tag,
                invokedMethod + ": " + format(msg, args)
            )
        }
    }

    fun d(msg: String, vararg args: Any) {
        if (isDebugEnabled) {
            Log.d(
                tag,
                invokedMethod + ": " + format(msg, args)
            )
        }
    }

    fun w(msg: String, tr: Throwable?) {
        if (isDebugEnabled) {
            Log.w(tag, "$invokedMethod: $msg", tr)
        }
    }

    fun w(msg: String, vararg args: Any) {
        if (isDebugEnabled) {
            Log.w(
                tag,
                invokedMethod + ": " + format(msg, args)
            )
        }
    }

    fun e(msg: String, tr: Throwable?) {
        if (isDebugEnabled) {
            Log.e(tag, "$invokedMethod: $msg", tr)
        }
    }

    fun e(msg: String, vararg args: Any) {
        if (isDebugEnabled) {
            Log.e(
                tag,
                invokedMethod + ": " + format(msg, args)
            )
        }
    }

    fun v(msg: String, vararg args: Any) {
        if (isDebugEnabled) {
            Log.v(
                tag,
                invokedMethod + ": " + format(msg, args)
            )
        }
    }

    private fun format(msg: String, args: Array<*>): String {
        var msg = msg
        if (args.isNotEmpty()) {
            msg = String.format(msg, *args)
        }
        return msg
    }
}