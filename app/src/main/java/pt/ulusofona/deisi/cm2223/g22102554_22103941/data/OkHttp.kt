package pt.ulusofona.deisi.cm2223.g22102554_22103941.data

import android.util.Log
import org.json.JSONObject
import java.io.IOException
import okhttp3.*
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.Avaliacao
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.FilmeIMDB
import pt.ulusofona.deisi.cm2223.g22102554_22103941.model.FilmesIMDB

class OkHttp (
    private val baseUrl: String,
    private val apiKey: String,
    private val client: OkHttpClient
) : FilmesIMDB() {

    override fun getAllFilmes(onFinished: (Result<List<FilmeIMDB>>) -> Unit) {
    }

    override fun inserirFilme(filme: FilmeIMDB, onFinished: () -> Unit) {
        Log.e("APP", "web service is not able to insert characters")
    }

    override fun deleteFilme(id: String, onFinished: (Result<FilmeIMDB>) -> Unit) {
        Log.e("APP", "web service is not able to clear all characters")
    }

    override fun getFilme(id: String, onFinished: (Result<FilmeIMDB>) -> Unit) {

        // Aqui estamos a preparar o pedido. Precisamos da apiKey e do url
        val request: Request = Request.Builder()
            .url("$baseUrl/character")
            .addHeader("Authorization", "Bearer $apiKey")
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
                // Se a resposta devolver um erro, ex: 403 acesso negado ao web service
                if (!response.isSuccessful) {
                    onFinished(Result.failure(IOException("Unexpected code $response")))
                } else {
                    val body = response.body?.string()
                    if (body != null) {
                        // Estamos a guardar o objeto assinalado a amarelo no exemplo aqui
                        val jsonObject = JSONObject(body)
                        // Aqui vamos guardar o array ainda em formato json dos personagens


                        val filme = FilmeIMDB(
                            jsonObject.getString("imdbID"),
                            jsonObject.getString("Title"),
                            jsonObject.getString("Genre"),
                            jsonObject.optString("Year"),
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