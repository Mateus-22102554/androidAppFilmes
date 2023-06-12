package pt.ulusofona.deisi.cm2223.g22102554_22103941.data.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.File
import java.util.*

@Entity(tableName = "avaliacoes")
data class AvaliacaoDB(
    @PrimaryKey val id: String,
    val avaliacao: Int,
    @ColumnInfo(name = "data_visualizacao") val dataVisualizacao: Long,
    val observacoes: String,
    @ColumnInfo(name = "id_imbd")val idImdb: String,
    @ColumnInfo(name = "id_fotos")val idFotos: String? = null,
    @ColumnInfo(name = "id_cinema")val idCinema: Int
)



