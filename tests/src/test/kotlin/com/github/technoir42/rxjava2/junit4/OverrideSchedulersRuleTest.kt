package com.github.technoir42.rxjava2.junit4

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert.assertSame
import org.junit.Rule
import org.junit.Test

class OverrideSchedulersRuleTest {
    private val scheduler = TestScheduler()

    @Rule
    @JvmField
    val overrideSchedulersRule = OverrideSchedulersRule(scheduler)

    @Test
    fun `Overrides standard schedulers`() {
        assertSame(scheduler, Schedulers.computation())
        assertSame(scheduler, Schedulers.io())
        assertSame(scheduler, Schedulers.newThread())
        assertSame(scheduler, Schedulers.single())
        assertSame(scheduler, AndroidSchedulers.mainThread())
    }
}
