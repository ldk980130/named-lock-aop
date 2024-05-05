package org.example.namedlockaop.app

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import kotlin.random.Random

@SpringBootTest
class GuestServiceTest {

    @Autowired
    private lateinit var guestService: GuestService

    @Autowired
    private lateinit var eventRepository: EventRepository

    @Autowired
    private lateinit var guestRepository: GuestRepository

    private var eventId: Long = 0

    @BeforeEach
    fun setUp() {
        eventId = Event.create("첫 번째 이벤트")
            .apply { eventRepository.save(this) }
            .id!!
    }

    @AfterEach
    fun clear() {
        guestRepository.deleteAll()
        eventRepository.deleteAll()
    }

    @Test
    fun `게스트가 이벤트에 참여하면 이벤트 참가자 수가 늘어난다`() {
        // when
        val applyCount = Random.nextInt(1, 100)
        repeat(applyCount) {idx ->
            guestService.apply("Guest:$idx", eventId)
        }

        // then
        val event = eventRepository.findByIdOrNull(eventId)!!

        event.guestCount shouldBe applyCount
    }
}
