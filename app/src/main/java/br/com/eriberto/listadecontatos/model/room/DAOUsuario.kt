package br.com.eriberto.listadecontatos.model.room

import android.arch.persistence.room.*
import br.com.eriberto.listadecontatos.model.modelo.Usuario

@Dao
interface DAOUsuario {
    @Query("SELECT * FROM usuario LIMIT 1")
    fun getUsuario(): Usuario?

    @Insert
    fun add(vararg usuario: Usuario)

    @Update
    fun update(usuario: Usuario)

    @Delete
    fun delete(usuario: Usuario)
}