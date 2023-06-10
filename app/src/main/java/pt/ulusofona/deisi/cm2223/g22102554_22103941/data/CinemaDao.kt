package pt.ulusofona.deisi.cm2223.g22102554_22103941.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pt.ulusofona.deisi.cm2223.g22102554_22103941.data.entidades.CinemaDB

@Dao
interface CinemaDao {
    @Insert
    fun inserirCinema(cinema: CinemaDB)

    @Query("SELECT * FROM cinemas")
    fun getAllCinema() : List<CinemaDB>

    @Query("SELECT * FROM cinemas WHERE id=:id")
    fun getCinemaById(id:Int) : CinemaDB

    @Query("SELECT * FROM cinemas WHERE nome=:nome")
    fun getCinemaByNome(nome: String) : CinemaDB

    @Query("DELETE FROM cinemas")
    fun deleteAll()
}