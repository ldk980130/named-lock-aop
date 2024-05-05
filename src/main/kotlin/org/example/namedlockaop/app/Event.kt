package org.example.namedlockaop.app

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Event private constructor(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val name: String,

    var guestCount: Int
) {

    companion object {
        fun create(name: String): Event {
            return Event(name = name, guestCount = 0)
        }
    }

    fun increaseGuestCount() {
        guestCount++
    }
}
