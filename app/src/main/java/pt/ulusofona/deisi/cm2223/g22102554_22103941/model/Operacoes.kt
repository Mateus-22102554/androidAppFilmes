package pt.ulusofona.deisi.cm2223.g22102554_22103941.model

abstract class Operacoes {

    //Filmes
    abstract fun getAllFilmes(onFinished: (Result<List<Avaliacao>>) -> Unit)
    abstract fun inserirFilme(filme: Filme, avaliacao: Avaliacao, onFinished: () -> Unit)
    abstract fun getFilmeIMDB(nome: String, onFinished: (Result<Filme>) -> Unit)

    abstract fun getFilme(id: String, onFinished: (Result<Filme>) -> Unit)



    //Avaliações
    abstract fun getAllAvaliacoes(onFinished: (Result<List<Avaliacao>>) -> Unit)

    abstract fun inserirAvaliacao(filme: Filme, avaliacao: Avaliacao, onFinished: (Result<Filme>) -> Unit)

    //abstract fun getAllAvaliacoesNomes(onFinished: (Result<List<String>>) -> Unit)

}
