package pt.ulusofona.deisi.cm2223.g22102554_22103941.data.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cinemas")
class CinemaDB(
    @PrimaryKey val id: Int,
    val nome: String,
)



