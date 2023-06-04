package pt.ulusofona.deisi.cm2223.g22102554_22103941.model

abstract class Avaliacoes {
    abstract fun getAllFilmes(onFinished: (Result<List<Avaliacao>>) -> Unit)
    abstract fun inserirFilme(avaliacao: Avaliacao, onFinished: () -> Unit)
    abstract fun getFilme(id: String, onFinished: (Result<FilmeIMDB>) -> Unit)
    abstract fun deleteFilme(id: String, onFinished: (Result<FilmeIMDB>) -> Unit)

}