package org.example.namedlockaop.app

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class GuestService(
    private val guestRepository: GuestRepository,
    private val eventRepository: EventRepository
) {

    fun apply(name: String, eventId: Long) {
        val event = eventRepository.findByIdOrNull(eventId)
            ?: throw IllegalArgumentException("Event with id $eventId not found")

        Guest.apply(name, event)
            .apply { guestRepository.save(this) }
    }
}
