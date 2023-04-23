package pt.ulusofona.deisi.cm2223.g22102554_22103941

import android.widget.TextView
import java.io.File
import java.time.LocalDate
import java.util.*

class Filme {
    var nome : String
    var cinema : String
    var avaliacao : Int
    var dataVisualizacao : Calendar
    var fotos: List<File>
    var observacoes : String

    constructor(
        nome: String, cinema: String, avaliacao: Int,
        dataVisualizacao: Calendar, fotos: List<File>,  observacoes: String) {
        this.nome = nome
        this.cinema = cinema
        this.avaliacao = avaliacao
        this.dataVisualizacao = dataVisualizacao
        this.fotos = fotos
        this.observacoes = observacoes

    }

    val listImgGet get() = fotos.toList()

}