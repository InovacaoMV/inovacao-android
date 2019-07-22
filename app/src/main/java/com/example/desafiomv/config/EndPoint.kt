package com.example.desafiomv.config

import com.example.desafiomv.model.Contact
import com.example.desafiomv.model.ContactDTO
import com.example.desafiomv.model.User
import com.example.desafiomv.model.UserDTO
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.http.*

interface EndPoint {

    /////////////////////////////////////////////////////////
    // CONTACT
    ////////////////////////////////////////////////////////
    @GET("/api/contact-list/person-email/{userEmail}")
    fun getContactList(@Path("userEmail") userEmail: String) : Single<List<Contact>>

    @HTTP(method = "DELETE", path = "/api/contact-list", hasBody = true)
    fun deleteContactList(@Body contact: Contact) : Single<Contact>

    @POST("/api/contact-list")
    fun saveContactList(@Body contact: ContactDTO) : Single<Contact>

    @PUT("/api/contact-list")
    fun updateContactList(@Body contact: Contact) : Single<List<Contact>>


    /////////////////////////////////////////////////////////
    // USER
    ////////////////////////////////////////////////////////
    @GET("/api/user/email/{email}")
    fun getUser(@Path("email") email: String) : Single<List<User>>

    @GET("/api/user/email/{email}/password/{password}")
    fun login(@Path("email") email: String,
              @Path("password") password: String) : Single<List<User>>

    @HTTP(method = "DELETE", path = "/api/user", hasBody = true)
    fun deleteUser(@Body user: User) : Single<User>

    @POST("/api/user")
    fun createAccount(@Body user: UserDTO) : Single<User>

    @PUT("/api/user")
    fun updateUser(@Body user: User) : Single<List<User>>

}