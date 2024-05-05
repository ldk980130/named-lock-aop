package org.example.namedlockaop.app

import jakarta.persistence.*

@Entity
class Guest(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val name: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    val event: Event
) {

    companion object {
        fun apply(name: String, event: Event): Guest {
            event.increaseGuestCount()
            return Guest(name = name, event = event)
        }
    }
}
