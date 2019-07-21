package com.example.desafiomv.config

import com.example.desafiomv.model.Contact
import com.example.desafiomv.model.User
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface EndPoint {

    /////////////////////////////////////////////////////////
    // CONTACT
    ////////////////////////////////////////////////////////
    @GET("/api/contact-list/person-email/{user_email}")
    fun getContactList(@Path("user_email") userEmail: String) : Observable<List<Contact>>

    @DELETE("/api/contact-list")
    fun deleteContactList(@Body contact: Contact) : Single<Contact>

    @POST("/api/contact-list")
    fun saveContactList(@Body contact: Contact) : Single<Contact>

    @PUT("/api/contact-list")
    fun updateContactList(@Body contact: Contact) : Single<Contact>


    /////////////////////////////////////////////////////////
    // USER
    ////////////////////////////////////////////////////////
    @GET("/api/user/email/{email}")
    fun getUser(@Path("email") email: String) : Single<User>

    @GET("/api/user/email/{email}/password/{password}")
    fun login(@Path("email") email: String,
              @Path("password") password: String) : Single<User>

    @DELETE("/api/user")
    fun deleteUser(@Body user: User) : Single<User>

    @POST("/api/user")
    fun registerUser(@Body user: User) : Single<User>

    @PUT("/api/user")
    fun updateUser(@Body user: User) : Single<User>

}