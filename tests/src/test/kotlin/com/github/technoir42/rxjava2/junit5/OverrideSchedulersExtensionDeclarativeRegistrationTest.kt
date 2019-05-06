package com.github.technoir42.rxjava2.junit5

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.schedulers.ComputationScheduler
import io.reactivex.internal.schedulers.IoScheduler
import io.reactivex.internal.schedulers.NewThreadScheduler
import io.reactivex.internal.schedulers.SingleScheduler
import io.reactivex.schedulers.Schedulers
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.extension.ExtendWith

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OverrideSchedulersExtensionDeclarativeRegistrationTest {
    @Test
    @Order(0)
    @ExtendWith(OverrideSchedulersExtension::class)
    fun `Overrides standard schedulers`() {
        val scheduler = Schedulers.trampoline()
        assertSame(scheduler, Schedulers.computation())
        assertSame(scheduler, Schedulers.io())
        assertSame(scheduler, Schedulers.newThread())
        assertSame(scheduler, Schedulers.single())
        assertSame(scheduler, AndroidSchedulers.mainThread())
    }

    @Test
    @Order(1)
    fun `Resets standard schedulers to defaults`() {
        assertTrue(Schedulers.computation() is ComputationScheduler)
        assertTrue(Schedulers.io() is IoScheduler)
        assertTrue(Schedulers.newThread() is NewThreadScheduler)
        assertTrue(Schedulers.single() is SingleScheduler)
        assertSame(HANDLER_SCHEDULER, AndroidSchedulers.mainThread().javaClass)
    }

    companion object {
        private val HANDLER_SCHEDULER = Class.forName("io.reactivex.android.schedulers.HandlerScheduler")
    }
}
