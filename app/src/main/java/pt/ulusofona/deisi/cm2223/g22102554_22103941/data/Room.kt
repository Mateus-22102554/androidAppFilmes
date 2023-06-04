package pt.ulusofona.deisi.cm2223.g22102554_22103941.data

import pt.ulusofona.deisi.cm2223.g22102554_22103941.data.entidades.FilmeDB
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Avaliacao
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.FilmeIMDB
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.FilmesIMDB

class Room (
    private val dao: Operations
) : FilmesIMDB() {
    override fun getAllFilmes(onFinished: (Result<List<FilmeIMDB>>) -> Unit) {
        dao.getAllFilmes()
    }

    override fun inserirFilme(filme: FilmeIMDB, onFinished: () -> Unit) {
        val filmeDB = filme.sinopse?.let {
            FilmeDB(
                filme.id,
                filme.nomeImdb,
                filme.generoImdb,
                filme.dataImdb,
                filme.avaliacaoImdb,
                filme.imgImdb,
                filme.sinopse
            )
        }
        if (filmeDB != null) {
            dao.inserirFilme(filmeDB)
        }
        onFinished()
    }
    override fun getFilme(id : String, onFinished: (Result<FilmeIMDB>) -> Unit) {
        dao.getFilme(id)
    }

    override fun deleteFilme(id : String, onFinished: (Result<FilmeIMDB>) -> Unit) {
        dao.deleteFilme(id)
    }

}