package pt.ulusofona.deisi.cm2223.g22102554_22103941.model

import java.io.File

abstract class Operacoes {

    //Filmes
    abstract fun getAllFilmes(onFinished: (Result<List<Avaliacao>>) -> Unit)
    abstract fun inserirFilme(filme: Filme, avaliacao: Avaliacao, onFinished: () -> Unit)
    abstract fun getFilmeIMDB(nome: String, onFinished: (Result<Filme>) -> Unit)

    abstract fun getFilme(id: String, onFinished: (Result<Filme>) -> Unit)

    abstract fun verificarFilme(nome: String, onFinished: (Int) -> Unit)



    //Avaliações
    abstract fun getAllAvaliacoes(onFinished: (Result<List<Avaliacao>>) -> Unit)

    abstract fun getAvaliacao(id : String, onFinished: (Result<Avaliacao>) -> Unit)

    abstract fun inserirAvaliacao(filme: Filme, avaliacao: Avaliacao, onFinished: (Result<Filme>) -> Unit)

    abstract fun getAvaliacaoIdFromFilmeName(nome: String, onFinished: (Result<String>) -> Unit)

    abstract fun inserirFotosAvaliacao(fotos: List<File>, idAvaliacao: String, onFinished: () -> Unit)
    abstract fun getAllFotosFromAvaliacao(id: String, onFinished: (Result<List<File>>) -> Unit)

    //Cinemas

    abstract fun getCinemasJSON(onFinished: (Result<List<Cinema>>) -> Unit)

    abstract fun inserirCinemas(cinemas : List<Cinema>, onFinished: () -> Unit)

    abstract fun getCinemaByNome(cinema : String, onFinished: (Result<Cinema>) -> Unit)
    abstract fun getCinemaById(idCinema : Int, onFinished: (Result<Cinema>) -> Unit)

    abstract fun verificarCinema(nome : String, onFinished: (Int) -> Unit)

    abstract fun getAllCinemasNomes(onFinished: (Result<List<String>>) -> Unit)

    abstract fun clearAllCinemas(onFinished: () -> Unit)

    abstract fun countAvaliacoes(onFinished: (Result<Int>) -> Unit)

    abstract fun top5Avaliacoes(onFinished: (Result<List<Avaliacao>>) -> Unit)

}
