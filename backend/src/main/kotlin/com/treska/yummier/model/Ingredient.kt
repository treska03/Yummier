package com.treska.yummier.model

import jakarta.persistence.*

@Entity
@Table(name = "ingredients")
data class Ingredient(@Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long = 0, val name: String)