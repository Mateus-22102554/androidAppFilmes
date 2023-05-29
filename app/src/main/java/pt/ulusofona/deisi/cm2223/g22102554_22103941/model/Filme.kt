package pt.ulusofona.deisi.cm2223.g22102554_22103941.model

import java.io.File
import java.util.*
import java.io.Serializable

data class Filme (
    val id : String,
    val nome : String,
    val cinema : String,
    val avaliacao : Int = 0,
    val dataVisualizacao : Calendar,
    val fotos: List<File>,
    val observacoes : String
    ) : Serializable