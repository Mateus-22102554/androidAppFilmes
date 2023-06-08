package pt.ulusofona.deisi.cm2223.g22102554_22103941.data.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.File
import java.util.*

@Entity(tableName = "avaliacoes")
data class AvaliacaoDB(
    @PrimaryKey val id: String,
    val nome: String,
    val avaliacao: Int,
    val dataVisualizacao: String, //ADAPTAR PARA LONG
    val observacoes: String,
    val idImdb: String,
    val idFotos: String? = null,
    val idCinema: String? = null
)



