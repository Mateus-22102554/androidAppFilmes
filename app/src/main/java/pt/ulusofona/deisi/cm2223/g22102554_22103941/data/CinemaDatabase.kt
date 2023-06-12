package pt.ulusofona.deisi.cm2223.g22102554_22103941.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pt.ulusofona.deisi.cm2223.g22102554_22103941.data.entidades.FilmeDB
import pt.ulusofona.deisi.cm2223.g22102554_22103941.data.entidades.AvaliacaoDB
import pt.ulusofona.deisi.cm2223.g22102554_22103941.data.entidades.CinemaDB
import pt.ulusofona.deisi.cm2223.g22102554_22103941.data.entidades.FotoDB

@Database(entities = [FilmeDB::class, AvaliacaoDB::class, CinemaDB::class, FotoDB::class], version = 1)
abstract class CinemaDatabase: RoomDatabase() {

    abstract fun filmeDao(): FilmeDao
    abstract fun avaliacaoDao(): AvaliacaoDao
    abstract fun cinemaDao(): CinemaDao
    abstract fun fotoDao(): FotoDao

    companion object {
        private var instance: CinemaDatabase? = null

        fun getInstance(applicationContext: Context): CinemaDatabase {
            synchronized(this) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        applicationContext,
                        CinemaDatabase::class.java,
                        "cinema_db"
                    ).build()
                }
                return instance as CinemaDatabase
            }
        }
    }
}