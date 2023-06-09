package pt.ulusofona.deisi.cm2223.g22102554_22103941.model

abstract class FilmesIMDB {

    //Filmes
    abstract fun getAllFilmes(onFinished: (Result<List<FilmeIMDB>>) -> Unit)
    abstract fun inserirFilme(filme: FilmeIMDB, avaliacao: Avaliacao, onFinished: () -> Unit)
    abstract fun getFilme(id: String, avaliacao: Avaliacao, onFinished: (Result<FilmeIMDB>) -> Unit)
    abstract fun deleteFilme(id: String, onFinished: (Result<FilmeIMDB>) -> Unit)

    //Avaliações
    abstract fun getAllAvaliacoes(onFinished: (Result<List<Avaliacao>>) -> Unit)

}
