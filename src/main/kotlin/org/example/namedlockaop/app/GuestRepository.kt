package org.example.namedlockaop.app

import org.springframework.data.jpa.repository.JpaRepository

interface GuestRepository: JpaRepository<Guest, Long>
