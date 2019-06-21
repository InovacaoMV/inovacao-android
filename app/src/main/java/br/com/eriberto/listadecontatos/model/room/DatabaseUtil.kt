package br.com.eriberto.listadecontatos.model.room

import android.arch.persistence.room.Room
import android.content.Context

object DatabaseUtil {
    fun openConnectionDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "minha_agenda.db")
            .allowMainThreadQueries()
            .build()
    }
}