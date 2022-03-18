// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.UnsupportedAudioFileException

@Composable
@Preview
fun App() {

    MaterialTheme {

        var voiceRecorder = VoiceRecorder()
        val speechAPI = SpeechAPIrequest()

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(onClick = {

                println("before click")

                val byteArray = voiceRecorder.recordForSpecificDuration(3.0)

                speechAPI.sendSTTAPIrequest(byteArray)

                println("after click")

            }){ Text("record") }
        }

    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}

@Throws(UnsupportedAudioFileException::class, IOException::class)
fun getFormatFromFile(filename: String?): javax.sound.sampled.AudioFormat {
    val inputFile = File(filename)
    val audioInputStream = AudioSystem.getAudioInputStream(inputFile)
    return audioInputStream.format

}