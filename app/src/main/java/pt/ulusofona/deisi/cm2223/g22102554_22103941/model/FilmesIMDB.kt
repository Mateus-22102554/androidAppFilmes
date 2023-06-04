package pt.ulusofona.deisi.cm2223.g22102554_22103941.model

abstract class FilmesIMDB {
    abstract fun getAllFilmes(onFinished: (Result<List<FilmeIMDB>>) -> Unit)
    abstract fun inserirFilme(filme: FilmeIMDB, onFinished: () -> Unit)
    abstract fun getFilme(id: String, onFinished: (Result<FilmeIMDB>) -> Unit)
    abstract fun deleteFilme(id: String, onFinished: (Result<FilmeIMDB>) -> Unit)

}
