package el.ka.dictophone

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import el.ka.dictophone.database.DBHelper
import el.ka.dictophone.fragments.RecordingFragment
import el.ka.dictophone.utils.Constants
import el.ka.dictophone.utils.MAIN_ACTIVITY
import el.ka.dictophone.utils.changeFragment
import el.ka.dictophone.utils.db

class MainActivity : AppCompatActivity() {
    private var permissionToRecordAudion = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestAllPermissions()

        MAIN_ACTIVITY = this
        db = DBHelper(this)

        changeFragment(RecordingFragment(), false)
    }

    private fun requestAllPermissions() {
        requestPermissions(
            Constants.permissions,
            Constants.RECORD_AUDIO_PERMISSION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == Constants.RECORD_AUDIO_PERMISSION) {
            permissionToRecordAudion = grantResults[0] == PackageManager.PERMISSION_GRANTED
            if (!permissionToRecordAudion) finish()
        }
    }

}