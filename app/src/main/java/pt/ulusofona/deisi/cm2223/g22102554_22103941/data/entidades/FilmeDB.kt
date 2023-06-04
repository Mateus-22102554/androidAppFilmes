package pt.ulusofona.deisi.cm2223.g22102554_22103941.data.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Avaliacao
import java.io.File
import java.util.*

@Entity(tableName = "filmes")
class FilmeDB(
    @PrimaryKey val id: String,
    val nome: String,
    val genero: String,
    val data: String,
    val avaliacao: String,
    val poster: String,
    val sinopse: String
    )



