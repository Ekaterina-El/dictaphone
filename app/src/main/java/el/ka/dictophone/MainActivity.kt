package el.ka.dictophone

import android.app.AlertDialog
import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import el.ka.dictophone.database.DBHelper
import el.ka.dictophone.fragments.RecordingFragment
import el.ka.dictophone.fragments.RecordsListFragment
import el.ka.dictophone.utils.*

class MainActivity : AppCompatActivity() {
    private var permissionToRecordAudion = false

    private lateinit var nameInput: EditText
    private var idForChange: Int = -1

    companion object {
        const val CHANGE_RECORD_NAME = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestAllPermissions()

        MAIN_ACTIVITY = this
        RECORDS_LIST_FRAGMENT = RecordsListFragment()
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

    override fun onCreateDialog(dialogId: Int): Dialog {
        return when (dialogId) {
            CHANGE_RECORD_NAME -> createDialogChangeName()
            else -> super.onCreateDialog(dialogId)
        }
    }

    override fun onPrepareDialog(dialogId: Int, dialog: Dialog?) {
        when (dialogId) {
            CHANGE_RECORD_NAME -> {
                nameInput.setText(db.getRecordById(idForChange)?.name ?: "Error")
            }
        }
        super.onPrepareDialog(dialogId, dialog)
    }

    private fun changeName() {
        val id = idForChange
        val newName = nameInput.text.toString()

        db.changeName(id, newName)
        RECORDS_LIST_FRAGMENT.changeDataById(id)
        idForChange = -1
    }

    private fun createDialogChangeName(): Dialog {
        val dialog = AlertDialog.Builder(MAIN_ACTIVITY)
        val view = layoutInflater.inflate(R.layout.dialog_change_record_name, null)
        nameInput = view.findViewById(R.id.dialog_change_name_input)

        dialog.setView(view)
        dialog.setPositiveButton(R.string.OK
        ) { _, _ -> changeName()}
        dialog.setNeutralButton(R.string.cancel, null)
        return dialog.create()
    }


    fun showDialogChangeName(id: Int) {
        idForChange = id
        showDialog(CHANGE_RECORD_NAME)
    }

}