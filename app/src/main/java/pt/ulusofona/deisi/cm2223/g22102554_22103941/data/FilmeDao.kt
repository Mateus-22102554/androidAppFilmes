package pt.ulusofona.deisi.cm2223.g22102554_22103941.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pt.ulusofona.deisi.cm2223.g22102554_22103941.data.entidades.FilmeDB

@Dao
interface FilmeDao {
    @Insert
    fun inserirFilme(filme: FilmeDB)

    @Query("SELECT * FROM filmes")
    fun getAllFilmes() : List<FilmeDB>

    @Query("SELECT * FROM filmes WHERE id=:id")
    fun getFilme(id:String) : FilmeDB

    @Query("DELETE FROM filmes WHERE id=:id")
    fun deleteFilme(id:String)

    @Query("SELECT * FROM filmes WHERE nome=:nome")
    fun verificarFilme(nome:String) : Boolean


}