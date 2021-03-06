import io.ktor.util.*
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.IOException
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.UnsupportedAudioFileException

class SpeechAPIrequest {

    private val service = PostService.create()

    @OptIn(InternalAPI::class)
    fun sendSTTAPIrequest(byteArray:ByteArray): String {
        val pr = PostRequest(byteArray)

        val response = runBlocking { service.createPost(pr) }

        require(response != null){"response null"}

        println(response.transcription)

        return response.transcription
    }


    @OptIn(InternalAPI::class)
    @Throws(UnsupportedAudioFileException::class, IOException::class)
    fun getPcmByteArray(filename: String?): ByteArray {

        val inputFile = File(filename)
        val audioInputStream = AudioSystem.getAudioInputStream(inputFile)

        return audioInputStream.readBytes()

    }


}