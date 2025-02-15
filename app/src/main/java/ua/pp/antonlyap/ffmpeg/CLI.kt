package ua.pp.antonlyap.ffmpeg

import com.arthenica.ffmpegkit.FFmpegKit
import com.arthenica.ffmpegkit.FFmpegKitConfig
import com.arthenica.ffmpegkit.FFprobeKit
import com.arthenica.ffmpegkit.Level
import com.arthenica.ffmpegkit.Log
import kotlin.system.exitProcess


class CLI {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val envOptions = System.getenv("FFMPEG_OPTIONS")?.split(" ")?.toTypedArray()
            val argOptions = args.drop(1).toTypedArray()
            val options = envOptions ?: argOptions
            if (args[0].endsWith("ffmpeg")) {
                FFmpegKitConfig.enableLogCallback { handleLog(it) }
                val session = FFmpegKit.executeWithArguments(options)
                Thread.sleep(200)
                exitProcess(session.returnCode.value)
            } else if (args[0].endsWith("ffprobe")) {
                val session = FFprobeKit.executeWithArguments(options)
                print(session.output)
                exitProcess(session.returnCode.value)
            } else {
                System.err.println(args[0] + ": unknown command")
                exitProcess(1)
            }
        }

        private fun handleLog(log: Log) {
            if (log.level.value == Level.AV_LOG_STDERR.value) {
                System.err.print(log.message)
            } else {
                print(log.message)
            }
        }
    }
}