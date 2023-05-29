package pt.ulusofona.deisi.cm2223.g22102554_22103941.data

import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Filme
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Filmes

class Room (
    private val dao: Operations
) : Filmes() {
    override fun getAllFilmes(onFinished: (Result<List<Filme>>) -> Unit) {
        dao.getAllFilmes()
    }

    override fun inserirFilme(filme: Filme, onFinished: () -> Unit) {
        val filmeDB = FilmeDB(
            filme.id,
            filme.nome, filme.cinema, filme.avaliacao,
            filme.dataVisualizacao, filme.fotos, filme.observacoes
        )
        dao.inserirFilme(filmeDB)
        onFinished()
    }
    override fun getFilme(id : String, onFinished: (Result<Filme>) -> Unit) {
        dao.getFilme(id)
    }

    override fun deleteFilme(id : String, onFinished: (Result<Filme>) -> Unit) {
        dao.deleteFilme(id)
    }

}