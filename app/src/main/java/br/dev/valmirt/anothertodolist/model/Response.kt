package br.dev.valmirt.anothertodolist.model

data class Response <T> (
    val value: T,
    val message: String
)