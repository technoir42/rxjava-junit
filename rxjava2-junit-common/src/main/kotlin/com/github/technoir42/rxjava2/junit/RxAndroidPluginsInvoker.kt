package com.github.technoir42.rxjava2.junit

import io.reactivex.Scheduler
import io.reactivex.functions.Function

class RxAndroidPluginsInvoker {
    private val rxAndroidPlugins: Class<*>? = try {
        Class.forName("io.reactivex.android.plugins.RxAndroidPlugins")
    } catch (e: ClassNotFoundException) {
        null
    }

    fun setMainThreadSchedulerHandler(handler: Function<Scheduler, Scheduler>) {
        rxAndroidPlugins
            ?.getDeclaredMethod("setMainThreadSchedulerHandler", Function::class.java)
            ?.invoke(null, handler)
    }

    fun reset() {
        rxAndroidPlugins
            ?.getDeclaredMethod("reset")
            ?.invoke(null)
    }
}
