package pt.ulusofona.deisi.cm2223.g22102554_22103941.model

data class Filme(
    val id : String,
    val nomeImdb : String,
    val generoImdb : String,
    val dataImdb : Long,
    val avaliacaoImdb : String,
    val imgImdb : String,
    val sinopse : String? = null
)
