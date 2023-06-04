package pt.ulusofona.deisi.cm2223.g22102554_22103941.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pt.ulusofona.deisi.cm2223.g22102554_22103941.data.entidades.FilmeDB

@Database(entities = [FilmeDB::class], version = 1)
abstract class CinemaDatabase: RoomDatabase() {

    abstract fun Operations(): Operations

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