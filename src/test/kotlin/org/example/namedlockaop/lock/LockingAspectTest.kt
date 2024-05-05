package org.example.namedlockaop.lock

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
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
    private lateinit var lockTemplate: LockTemplate

    @BeforeEach
    fun setUp() {
        every { lockTemplate.execute(any(), any(), any()) } answers { }
    }

    @Test
    fun `lock aspect with key`() {
        val prefix = "test"
        val key = Random.nextLong()

        // when
        testTarget.doSomething(key)

        // then
        verify { lockTemplate.execute("$prefix:$key", any(), any()) }
    }

    @Test
    fun `lock aspect`() {
        val prefix = "test"

        // when
        testTarget.doSomething()

        // then
        verify { lockTemplate.execute(prefix, any(), any()) }
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
