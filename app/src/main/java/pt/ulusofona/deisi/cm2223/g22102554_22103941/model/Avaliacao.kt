package pt.ulusofona.deisi.cm2223.g22102554_22103941.model

import java.io.File
import java.util.*
import java.io.Serializable

data class Avaliacao (
    val id : String = UUID.randomUUID().toString(),
    val filme : Filme,
    val cinema : Cinema,
    val avaliacao : Int,
    val dataVisualizacao : Long,
    val fotos: List<File>? = null,
    val observacoes : String
    ) : Serializable