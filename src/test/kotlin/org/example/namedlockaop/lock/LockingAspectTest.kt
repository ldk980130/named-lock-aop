package org.example.namedlockaop.lock

import com.ninjasquad.springmockk.MockkBean
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.stereotype.Component
import kotlin.random.Random

@SpringBootTest
class LockingAspectTest {

    @Autowired
    private lateinit var testTarget: TestTarget

    @MockkBean
    private lateinit var lockManager: LockManager

    @BeforeEach
    fun setUp() {
        every { lockManager.lock(any()) } just Runs
        every { lockManager.unlock(any()) } just Runs
    }

    @Test
    fun `lock aspect with key`() {
        val prefix = "test"
        val key = Random.nextLong()

        // when
        testTarget.doSomething(key)

        // then
        verify { lockManager.lock("$prefix:$key") }
        verify { lockManager.unlock("$prefix:$key") }
    }

    @Test
    fun `lock aspect`() {
        val prefix = "test"

        // when
        testTarget.doSomething()

        // then
        verify { lockManager.lock(prefix) }
        verify { lockManager.unlock(prefix) }
    }
}

@Component
class TestTarget {

    @Locking("test")
    fun doSomething(@LockKey key: Long) {
    }

    @Locking("test")
    fun doSomething() {
    }
}
