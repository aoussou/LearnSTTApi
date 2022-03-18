import kotlinx.coroutines.*
import java.io.File
import javax.sound.sampled.*

class VoiceRecorder(
    private val format: AudioFormat = AudioFormat(
        AudioFormat.Encoding.PCM_SIGNED,
        16000.0F,
        16,
        1,
        2,
        16000.0F,
        false
    )
) {


    private val info = DataLine.Info(TargetDataLine::class.java, format)


    private val line = AudioSystem.getLine(info) as TargetDataLine
    private val BUFFER_SIZE = line.bufferSize
    private val cr = CoroutineScope(Dispatchers.IO)

    init {
        if (!AudioSystem.isLineSupported(info)) {
            println("Line is not supported!!!")
        } else {
            println("Line is supported.")
        }
    }

    fun recordForSpecificDuration(duration: Double): ByteArray {

        line.open()
        line.start()

        val bytesBuffer = ByteArray(BUFFER_SIZE)
        var recordingBuffer = ByteArray(0)
        val recordingStream = AudioInputStream(line)
        val outputFile = File("src/main/resources/record.wav")
        val frameRate = format.frameRate

        cr.launch { AudioSystem.write(recordingStream, AudioFileFormat.Type.WAVE, outputFile) }

        runBlocking {


            while (line.framePosition / frameRate < duration) {

                val time = line.framePosition / frameRate
                delay(100)
                println("position: ${line.framePosition}, time: $time")

                recordingStream.read(bytesBuffer,0,BUFFER_SIZE)
                recordingBuffer += bytesBuffer

            }
        }


        line.stop()
        line.close()

        println(recordingBuffer.size)

        return recordingBuffer

    }
}

