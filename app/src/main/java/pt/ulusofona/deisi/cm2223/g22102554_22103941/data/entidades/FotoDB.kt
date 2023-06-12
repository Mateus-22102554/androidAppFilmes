package pt.ulusofona.deisi.cm2223.g22102554_22103941.data.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fotos")
class FotoDB(
    @PrimaryKey val id: String,
    val caminho: String,
    @ColumnInfo(name = "id_avaliacao") val idAvaliacao: String
)



