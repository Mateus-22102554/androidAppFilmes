package pt.ulusofona.deisi.cm2223.g22102554_22103941.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pt.ulusofona.deisi.cm2223.g22102554_22103941.data.entidades.AvaliacaoDB

@Dao
interface AvaliacaoDao {
    @Insert
    fun inserirAvaliacao(avalicao: AvaliacaoDB)

    @Query("SELECT * FROM avaliacoes")
    fun getAllAvaliacoes() : List<AvaliacaoDB>

    @Query("SELECT * FROM avaliacoes WHERE id=:id")
    fun getAvaliacao(id:String) : AvaliacaoDB

    @Query("DELETE FROM avaliacoes WHERE id=:id")
    fun deleteAvaliacao(id:String)

}