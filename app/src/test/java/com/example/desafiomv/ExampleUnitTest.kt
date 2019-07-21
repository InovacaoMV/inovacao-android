package com.example.desafiomv

import com.example.desafiomv.model.Contact
import com.example.desafiomv.service.ListService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun getContacts() {
        val contact: MutableList<Contact> = mutableListOf()
        ListService.getContactList("mizaelteste123@teste.com")
            .doOnNext {
                contact.add(it)
            }
            .doOnComplete {
                assert(contact.isNotEmpty())
            }
            .subscribe()
    }
}
