package pt.ulusofona.deisi.cm2223.g22102554_22103941.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pt.ulusofona.deisi.cm2223.g22102554_22103941.data.entidades.FotoDB

@Dao
interface FotoDao {

    @Insert
    fun inserirFoto(foto: FotoDB)

    @Query("SELECT * FROM fotos WHERE idAvaliacao=:idAvaliacao")
    fun getAllFotosFromAvaliacao(idAvaliacao: String) : List<FotoDB>

}