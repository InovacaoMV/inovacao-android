package lima.wilquer.contactlist.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitApi constructor(val url: String) {

    var retrofit: Retrofit? = null
    val client: Retrofit
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(url)
                    .build()
            }

            return retrofit!!
        }
}