package pt.ulusofona.deisi.cm2223.g22102554_22103941.model

data class FilmeIMDB(
    val id : String,
    val nomeImdb : String,
    val generoImdb : String,
    val dataImdb : String,
    val avaliacaoImdb : String,
    val imgImdb : String,
    val sinopse : String? = null
)
