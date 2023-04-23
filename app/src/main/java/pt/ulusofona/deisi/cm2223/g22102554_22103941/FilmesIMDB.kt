package pt.ulusofona.deisi.cm2223.g22102554_22103941

import java.util.*

object FilmesIMDB {
    val filme1 = FilmeIMDB("John Wick 4","Ação","2023", "8.2/10" ,"john", "John Wick uncovers a path to defeating The High Table.")
    val filme2 = FilmeIMDB("Avatar 2","Ação","2022", "7.7/10" ,"avatar", "Jake Sully lives with his newfound family formed on the extrasolar moon Pandora.")
    val filme3 = FilmeIMDB("Shazam","Ação","20219", "7.0/10" , "shazam","A newly fostered young boy in search of his mother instead finds unexpected super powers and soon gains a powerful enemy.")
    val filme4 = FilmeIMDB("Homem-Aranha 3","Ação","2007", "8.2/10" , "aranha","With Spider-Man's identity now revealed, Peter asks Doctor Strange for help")


    private val _listFilmesImdb = mutableListOf<FilmeIMDB>(filme1,filme2,filme3,filme4)

    val getListFilmesImdb get() = _listFilmesImdb.toList()

    fun nomesFilmesGet(): List<String> {

        return getListFilmesImdb.map { it.nomeImdb }
    }
}