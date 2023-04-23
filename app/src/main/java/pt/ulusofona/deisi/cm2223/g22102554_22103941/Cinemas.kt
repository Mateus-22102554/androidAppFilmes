package pt.ulusofona.deisi.cm2223.g22102554_22103941


object Cinemas {
    val cinema1 = Cinema("Fórum Montijo")
    val cinema2 = Cinema("Cinema Ideal")
    val cinema3 = Cinema("Cinema City Campo Pequeno")
    val cinema4 = Cinema("El Corte Inglês")
    val cinema5 = Cinema("Cinemas NOS Centro Comercial Colombo")
    val cinema6 = Cinema("Cinemas NOS Alvaláxia")
    val cinema7 = Cinema("Cinema São Jorge")
    val cinema8 = Cinema("Cinema do Parque")
    val cinema9 = Cinema("UCI Cinemas Ubbo")
    val cinema10 = Cinema("Cinema City Alegro Alfragide")
    val cinema11 = Cinema("Cinema City Almada Forum")

    private val _listCinemas = mutableListOf<Cinema>(
        cinema1,
        cinema2,
        cinema3,
        cinema4,
        cinema5,
        cinema6,
        cinema7,
        cinema8,
        cinema9,
        cinema10,
        cinema11
    )

    val getListCinemas get() = _listCinemas.toList()

    fun nomesCinemasGet(): List<String> {

        return getListCinemas.map { it.cinemaName }
    }
}