package el.ka.dictophone.objects

import android.media.MediaPlayer

class DictaphonePlayer {
    var playing = false
    var duration = 0
    var currentDuration = 0
    private var mediaPlayer: MediaPlayer? = null

    fun startPlaying(filePath: String) {
        mediaPlayer = MediaPlayer().apply {
            setDataSource(filePath)
            prepare()
            start()
            setOnCompletionListener {
                stopPlaying()
            }
        }
        duration = mediaPlayer!!.duration
        currentDuration = 0
        playing = true
    }

    fun stopPlaying() {
        mediaPlayer?.release()
        playing = false
        mediaPlayer = null
    }

    fun getDurationOfFile(filePath: String): Int {
        val mp = MediaPlayer()
        mp.setDataSource(filePath)

        val r = mp.duration
        mp.release()
        return r
    }
}