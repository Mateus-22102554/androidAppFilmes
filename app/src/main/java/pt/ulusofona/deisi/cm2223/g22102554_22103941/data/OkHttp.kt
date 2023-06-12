package pt.ulusofona.deisi.cm2223.g22102554_22103941.data

import android.util.Log
import org.json.JSONObject
import java.io.IOException
import okhttp3.*
import pt.ulusofona.deisi.cm2223.g22102554_22103941.IMDB_API_BASE_URL
import pt.ulusofona.deisi.cm2223.g22102554_22103941.IMDB_API_TOKEN
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Avaliacao
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Cinema
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Filme

import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Operacoes
import java.io.File

class OkHttp (
    val baseUrl: String = IMDB_API_BASE_URL,
    private val apiKey: String = IMDB_API_TOKEN,
    private val client: OkHttpClient
) : Operacoes() {

    override fun getAllFilmes(onFinished: (Result<List<Avaliacao>>) -> Unit) {
    }

    override fun inserirFilme(filme: Filme, avaliacao: Avaliacao, onFinished: () -> Unit) {
        Log.e("APP", "web service is not able to insert characters")
    }

    override fun getAllAvaliacoes(onFinished: (Result<List<Avaliacao>>) -> Unit) {
        Log.e("APP", "web service is not able to insert characters")
    }

    override fun getAvaliacao(id : String, onFinished: (Result<Avaliacao>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun inserirAvaliacao(filme: Filme, avaliacao: Avaliacao, onFinished: (Result<Filme>) -> Unit) {
        Log.e("APP", "web service is not able to insert characters")
    }

    override fun getAvaliacaoIdFromFilmeName(nome: String, onFinished: (Result<String>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun inserirFotosAvaliacao(fotos: List<File>, idAvaliacao: String, onFinished: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getAllFotosFromAvaliacao(id: String, onFinished: (Result<List<File>>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getCinemasJSON(onFinished: (Result<List<Cinema>>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun inserirCinemas(cinemas : List<Cinema>, onFinished: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getCinemaByNome(cinema: String, onFinished: (Result<Cinema>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getCinemaById(idCinema: Int, onFinished: (Result<Cinema>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun verificarCinema(nome: String, onFinished: (Int) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getAllCinemasNomes(onFinished: (Result<List<String>>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun clearAllCinemas(onFinished: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun countAvaliacoes(onFinished: (Result<Int>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun top5Avaliacoes(onFinished: (Result<List<Avaliacao>>) -> Unit) {
        TODO("Not yet implemented")
    }


    override fun getFilme(id: String, onFinished: (Result<Filme>) -> Unit) {
        Log.e("APP", "web service is not able to insert characters")
    }

    override fun verificarFilme(nome: String, onFinished: (Int) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getFilmeIMDB(id: String, onFinished: (Result<Filme>) -> Unit) {

        // Aqui estamos a preparar o pedido. Precisamos da apiKey e do url
        val request: Request = Request.Builder()
            .url("$baseUrl/?type=movie&t=$id&apikey=$apiKey")

            //.addHeader("Authorization", "Bearer $apiKey")
            .build()

        // Nesta linha executamos o pedido ao servidor
        // em caso caso de erro, o método onFailure será invocado (ex: timeout)
        // se tudo correr bem, teremos a resposta ao pedido no método onResponse
        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                onFinished(Result.failure(e))
            }

            // Processar a resposta ao pedido
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()

                val resposta = body?.let { JSONObject(it).getString("Response") }

                // Se a resposta devolver um erro, ex: 403 acesso negado ao web service
                if (resposta == "False" || resposta == null) {
                    onFinished(Result.failure(IOException("O filme indicado não existe!")))
                } else {

                    if (body != null) {
                        // Estamos a guardar o objeto assinalado a amarelo no exemplo aqui
                        val jsonObject = JSONObject(body)
                        // Aqui vamos guardar o array ainda em formato json dos personagens


                        val filme = Filme(
                            jsonObject.getString("imdbID"),
                            jsonObject.getString("Title"),
                            jsonObject.getString("Genre"),
                            jsonObject.optString("Year").toLong(),
                            jsonObject.getString("imdbRating"),
                            jsonObject.getString("Poster"),
                            jsonObject.getString("Plot")
                        )



                        // Devolve o filme com dados do IMDB
                        onFinished(Result.success(filme))

                    }
                }
            }
        })
    }
}