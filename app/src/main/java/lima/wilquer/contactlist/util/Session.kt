package lima.wilquer.contactlist.util

import android.app.Application
import lima.wilquer.contactlist.data.User

class Session : Application() {
    companion object {
        var loggedUser : User? = null
    }
}