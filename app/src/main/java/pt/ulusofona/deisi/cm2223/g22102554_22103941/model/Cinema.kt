package pt.ulusofona.deisi.cm2223.g22102554_22103941.model

data class Cinema(
    val cinema_id : Int,
    val cinema_name : String,
    val latitude: Double,
    val longitude: Double,
    val morada: String,
    val localidade: String
)