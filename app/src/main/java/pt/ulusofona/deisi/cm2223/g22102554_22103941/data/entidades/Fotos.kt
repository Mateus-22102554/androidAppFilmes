package pt.ulusofona.deisi.cm2223.g22102554_22103941.data.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.File
import java.util.*

@Entity(tableName = "fotos")
class Fotos(
    @PrimaryKey val id: String,
    val caminho: String
)



