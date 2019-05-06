package com.github.technoir42.rxjava2.junit4

import com.github.technoir42.rxjava2.junit.RxAndroidPluginsInvoker
import io.reactivex.Scheduler
import io.reactivex.functions.Function
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * Replaces standard schedulers with the specified scheduler (by default [Schedulers.trampoline])
 * before test execution and restores default schedulers after test execution.
 */
class OverrideSchedulersRule(
    private val scheduler: Scheduler = Schedulers.trampoline()
) : TestRule {

    private val rxAndroidPluginsInvoker = RxAndroidPluginsInvoker()

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                val handler = Function<Scheduler, Scheduler> { scheduler }
                RxJavaPlugins.setComputationSchedulerHandler(handler)
                RxJavaPlugins.setIoSchedulerHandler(handler)
                RxJavaPlugins.setNewThreadSchedulerHandler(handler)
                RxJavaPlugins.setSingleSchedulerHandler(handler)
                rxAndroidPluginsInvoker.setMainThreadSchedulerHandler(handler)

                try {
                    base.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    rxAndroidPluginsInvoker.reset()
                }
            }
        }
    }
}
