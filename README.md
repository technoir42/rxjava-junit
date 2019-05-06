RxJava-JUnit
============

[ ![Download](https://api.bintray.com/packages/sch/maven/rxjava-junit/images/download.svg) ](https://bintray.com/sch/maven/rxjava-junit/_latestVersion)

JUnit 4 & 5 extensions to simplify testing RxJava code.

## Extensions

`OverrideSchedulersRule` (JUnit 4) and `OverrideSchedulersExtension` (JUnit 5) replace standard schedulers
`Schedulers.computation()`, `Schedulers.io()`, `Schedulers.newThread()`, `Schedulers.single()` and `AndroidSchedulers.mainThread()`
with the specified scheduler (by default `Schedulers.trampoline()`) before test execution and restore default schedulers
after test execution.

## JUnit 5

```gradle
testImplementation "com.github.technoir42:rxjava2-junit5:1.0.0"
```

Use `@ExtendWith` for declarative registration if you wish to use the default scheduler:

```kotlin
@ExtendWith(OverrideSchedulersExtension::class)
class MyTest {
    @Test
    fun test() {
        Observable.fromCallable { 1 }
            .subscribeOn(Schedulers.computation())
            .test()
            .assertResult(1)
    }
}
```

Use `@RegisterExtension` for programmatic registration if you wish to use a custom scheduler:

```kotlin
class MyTest {
    private val scheduler = TestScheduler()

    @RegisterExtension
    @JvmField
    val overrideSchedulersExtension = OverrideSchedulersExtension(scheduler)

    @Test
    fun test() {
        val observer = Observable.timer(1, TimeUnit.SECONDS).test()
        scheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        observer.assertResult(0)
    }
}
```

## JUnit 4

```gradle
testImplementation "com.github.technoir42:rxjava2-junit4:1.0.0"
```

Apply rule to your test class, optionally passing a custom scheduler:

```kotlin
class MyTest {
    private val scheduler = TestScheduler()

    @Rule
    @JvmField
    val overrideSchedulersRule = OverrideSchedulersRule(scheduler)

    @Test
    fun test() {
        val observer = Observable.timer(1, TimeUnit.SECONDS).test()
        scheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        observer.assertResult(0)
    }
}
```

## License

```
Copyright 2019 Sergey Chelombitko

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
