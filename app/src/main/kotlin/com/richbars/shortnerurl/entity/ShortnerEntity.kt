package com.richbars.shortnerurl.entity

import jakarta.persistence.*

@Entity
@Table(name = "urls")
data class ShortnerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    val url: String = "",

    @Column(nullable = false, unique = true)
    val code: String = "",
)