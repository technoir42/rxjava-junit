package com.github.technoir42.rxjava2.junit5

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class OverrideSchedulersExtensionProgrammaticRegistrationTest {
    private val scheduler = TestScheduler()

    @RegisterExtension
    @JvmField
    val overrideSchedulersExtension = OverrideSchedulersExtension(scheduler)

    @Test
    fun `Overrides standard schedulers`() {
        assertSame(scheduler, Schedulers.computation())
        assertSame(scheduler, Schedulers.io())
        assertSame(scheduler, Schedulers.newThread())
        assertSame(scheduler, Schedulers.single())
        assertSame(scheduler, AndroidSchedulers.mainThread())
    }
}
