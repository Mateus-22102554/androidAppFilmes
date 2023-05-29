package pt.ulusofona.deisi.cm2223.g22102554_22103941.model

import pt.ulusofona.deisi.cm2223.g22102554_22103941.data.FilmeDB

abstract class Filmes {
    abstract fun getAllFilmes(onFinished: (Result<List<Filme>>) -> Unit)
    abstract fun inserirFilme(filme: Filme, onFinished: () -> Unit)
    abstract fun getFilme(id: String, onFinished: (Result<Filme>) -> Unit)
    abstract fun deleteFilme(id: String, onFinished: (Result<Filme>) -> Unit)

}
