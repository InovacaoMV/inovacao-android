package br.com.eriberto.listadecontatos.model.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import br.com.eriberto.listadecontatos.model.modelo.Usuario

@Database(entities = [
    Usuario::class
], version = 1)

abstract class AppDatabase: RoomDatabase() {
    abstract fun daoUsuario(): DAOUsuario
}