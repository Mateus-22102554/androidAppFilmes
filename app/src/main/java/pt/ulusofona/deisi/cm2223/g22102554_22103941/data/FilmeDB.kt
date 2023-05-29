package pt.ulusofona.deisi.cm2223.g22102554_22103941.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.File
import java.util.*

@Entity(tableName = "filmes")
class FilmeDB(
    @PrimaryKey val id: String,
    val nome: String,
    val cinema: String,
    val avaliacao: Int,
    val dataVisualizacao: Calendar,
    val fotos: List<File>,
    val observacoes: String
    )



