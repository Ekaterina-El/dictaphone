package el.ka.dictophone.models

data class Record(
    var id: Int,
    var name: String,
    var filePath: String,
    var createAt: Int,
    var duration: Int
)
