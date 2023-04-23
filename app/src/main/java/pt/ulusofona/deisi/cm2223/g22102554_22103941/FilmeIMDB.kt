package pt.ulusofona.deisi.cm2223.g22102554_22103941

import android.media.Image
import java.util.*

class FilmeIMDB {
    var nomeImdb : String
    var generoImdb : String
    var dataImdb : String
    var avaliacaoImdb : String
    var imgImdb : String
    var sinopse : String

    constructor(
        nomeImdb: String, generoImdb: String, dataImdb: String, avaliacaoImdb: String, imgImdb:String,
        sinopse: String) {
        this.nomeImdb = nomeImdb
        this.generoImdb = generoImdb
        this.dataImdb = dataImdb
        this.avaliacaoImdb = avaliacaoImdb
        this.imgImdb = imgImdb
        this.sinopse = sinopse


    }
}