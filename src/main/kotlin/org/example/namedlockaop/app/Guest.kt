package org.example.namedlockaop.app

import jakarta.persistence.*

@Entity
class Guest private constructor(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val name: String,

    val eventId: Long
) {

    companion object {
        fun apply(name: String, event: Event): Guest {
            event.increaseGuestCount()
            return Guest(name = name, eventId = event.id!!)
        }
    }
}
