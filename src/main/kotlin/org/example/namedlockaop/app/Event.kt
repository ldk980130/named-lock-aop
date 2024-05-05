package org.example.namedlockaop.app

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Event(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    var guestCount: Int
) {

    fun increaseGuestCount() {
        guestCount++
    }
}
