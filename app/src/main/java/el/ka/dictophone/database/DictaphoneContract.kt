package el.ka.dictophone.database

class DictaphoneContract {
    companion object {
        const val NAME = "el.ka.dictophone.database"
        const val VERSION = 1
    }

    class RecordsTable {
        companion object {
            const val TABLE = "records"
            const val COL_ID = "id"
            const val COL_NAME = "name"
            const val COL_FILE_PATH = "file_path"
            const val COL_CREATE_AT = "create_at"
            const val COL_DURATION = "duration"
        }
    }
}