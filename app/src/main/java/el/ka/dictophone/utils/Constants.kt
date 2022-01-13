package el.ka.dictophone.utils

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import el.ka.dictophone.MainActivity
import el.ka.dictophone.R
import el.ka.dictophone.database.DBHelper

object Constants {
    const val RECORD_AUDIO_PERMISSION = 1

    val permissions = arrayOf(
        android.Manifest.permission.RECORD_AUDIO
    )
}

@SuppressLint("StaticFieldLeak")
lateinit var MAIN_ACTIVITY: MainActivity
lateinit var db: DBHelper

fun changeFragment(fragment: Fragment, addToBack: Boolean = true) {
    val fragmentManager = MAIN_ACTIVITY.supportFragmentManager.beginTransaction()

    if (addToBack) {
        fragmentManager
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
    } else {
        fragmentManager
            .replace(R.id.fragmentContainer, fragment)
    }

    fragmentManager.commit()
}