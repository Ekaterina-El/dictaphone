package el.ka.dictophone.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import el.ka.dictophone.models.Record

class DBHelper(
    context: Context?,
) : SQLiteOpenHelper(
    context,
    DictaphoneContract.NAME,
    null,
    DictaphoneContract.VERSION
) {
    override fun onCreate(db: SQLiteDatabase) {
        val qr = "CREATE TABLE ${DictaphoneContract.RecordsTable.TABLE} (" +
                "${DictaphoneContract.RecordsTable.COL_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${DictaphoneContract.RecordsTable.COL_NAME} TEXT NOT NULL, " +
                "${DictaphoneContract.RecordsTable.COL_FILE_PATH} TEXT NOT NULL, " +
                "${DictaphoneContract.RecordsTable.COL_CREATE_AT} LONG NOT NULL, " +
                "${DictaphoneContract.RecordsTable.COL_DURATION} INTEGER NOT NULL)"
        db.execSQL(qr)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        val qr = "DROP TABLE IF EXISTS ${DictaphoneContract.RecordsTable.TABLE}"
        db.execSQL(qr)
        onCreate(db)
    }

    fun addRecord(record: Record) {
        val cv = ContentValues()
        cv.put(DictaphoneContract.RecordsTable.COL_NAME, record.name)
        cv.put(DictaphoneContract.RecordsTable.COL_FILE_PATH, record.filePath)
        cv.put(DictaphoneContract.RecordsTable.COL_CREATE_AT, record.createAt)
        cv.put(DictaphoneContract.RecordsTable.COL_DURATION, record.duration)

        writableDatabase.insert(
            DictaphoneContract.RecordsTable.TABLE,
            null, cv
        )
        writableDatabase.close()
    }

    fun getRecords(): MutableList<Record> {
        val records = mutableListOf<Record>()

        val cursor = readableDatabase.query(
            DictaphoneContract.RecordsTable.TABLE,
            arrayOf(
                DictaphoneContract.RecordsTable.COL_ID,
                DictaphoneContract.RecordsTable.COL_NAME,
                DictaphoneContract.RecordsTable.COL_FILE_PATH,
                DictaphoneContract.RecordsTable.COL_CREATE_AT,
                DictaphoneContract.RecordsTable.COL_DURATION
            ), null, null, null, null, null
        )

        while (cursor.moveToNext()) {
            val idxId = cursor.getColumnIndex(DictaphoneContract.RecordsTable.COL_ID)
            val idxName = cursor.getColumnIndex(DictaphoneContract.RecordsTable.COL_NAME)
            val idxFilePath = cursor.getColumnIndex(DictaphoneContract.RecordsTable.COL_FILE_PATH)
            val idxCreateAt = cursor.getColumnIndex(DictaphoneContract.RecordsTable.COL_CREATE_AT)
            val idxDuration = cursor.getColumnIndex(DictaphoneContract.RecordsTable.COL_DURATION)

            val record = Record(
                cursor.getInt(idxId),
                cursor.getString(idxName),
                cursor.getString(idxFilePath),
                cursor.getLong(idxCreateAt),
                cursor.getInt(idxDuration),
            )
            records.add(record)
        }

        cursor.close()
        readableDatabase.close()
        return records
    }

    fun changeName(id: Int, newName: String) {
        val cv = ContentValues()
        cv.put(DictaphoneContract.RecordsTable.COL_NAME, newName)

        writableDatabase.update(
            DictaphoneContract.RecordsTable.TABLE,
            cv,
            "${DictaphoneContract.RecordsTable.COL_ID}=?",
            arrayOf(id.toString())
        )

        writableDatabase.close()
    }

    fun getRecordById(id: Int): Record? {
        val cursor = writableDatabase.query(
            DictaphoneContract.RecordsTable.TABLE,
            arrayOf(
                DictaphoneContract.RecordsTable.COL_ID,
                DictaphoneContract.RecordsTable.COL_NAME,
                DictaphoneContract.RecordsTable.COL_FILE_PATH,
                DictaphoneContract.RecordsTable.COL_CREATE_AT,
                DictaphoneContract.RecordsTable.COL_DURATION
            ),
            "${DictaphoneContract.RecordsTable.COL_ID}=?",
            arrayOf(
                id.toString()
            ),
            null, null, null, null
        )

        var record: Record? = null

        if (cursor.moveToFirst()) {
            val idxId = cursor.getColumnIndex(DictaphoneContract.RecordsTable.COL_ID)
            val idxName = cursor.getColumnIndex(DictaphoneContract.RecordsTable.COL_NAME)
            val idxFilePath = cursor.getColumnIndex(DictaphoneContract.RecordsTable.COL_FILE_PATH)
            val idxCreateAt = cursor.getColumnIndex(DictaphoneContract.RecordsTable.COL_CREATE_AT)
            val idxDuration = cursor.getColumnIndex(DictaphoneContract.RecordsTable.COL_DURATION)

            record = Record(
                cursor.getInt(idxId),
                cursor.getString(idxName),
                cursor.getString(idxFilePath),
                cursor.getLong(idxCreateAt),
                cursor.getInt(idxDuration),
            )

        }

        cursor.close()
        readableDatabase.close()
        return record
    }
}