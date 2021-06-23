package com.klim.stealler

import com.google.cloud.translate.Translate
import com.google.cloud.translate.TranslateOptions
import com.google.cloud.translate.Translation
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.io.FileNotFoundException
import java.util.*
import kotlin.collections.HashMap


//The Task class needs to be declared open or Gradle will not be able to register it.
internal open class TranslateResourcesTask : DefaultTask() {

    companion object {
        enum class TranslateMode {
            SOFT, HARD
        }
    }

    private lateinit var translate: Translate
    fun setT(t: Translate) {
        this.translate = t
    }

    @get:Input
    var projectPath: String = ""

    @get:Input
    var stringInputFilePath: String = ""

    @get:OutputFile
    var stringOutputFilePath: String = ""

    @get:Input
    var srcLanguage: String = ""

    @get:Input
    var targetLanguage: String = ""

    @get:Input
    var mode: TranslateMode = TranslateMode.SOFT

    @get:Input
    var keepCase: Boolean = true


    private val outputString = StringBuilder()
    private lateinit var sourceStringsFilePath: String
    private lateinit var targetStringsFilePath: String
    private val existedStringsFromTarget = HashMap<String, String>()

    @TaskAction
    //The @TaskAction annotation denotes the entry method that Gradle will call when running the task. The method name itself is irrelevant.
    fun main() {
        println("TranslateResourcesTask - running")
        init()

        translate =
            TranslateOptions.newBuilder().setApiKey("AIzaSyDnDsXT28joM0F91TRpalBKwtQL3r6UuLM")
                .build().service

        if (mode == TranslateMode.SOFT) {
            val targetFile = File(targetStringsFilePath)
            if (targetFile.exists()) {
                try {
                    val reader = Scanner(targetFile)
                    while (reader.hasNextLine()) {
                        val line: String = reader.nextLine()
                        if (line.contains("<string ") && !line.contains("translatable=\"false\"") && !line.contains("<!--")) {
                            existedStringsFromTarget[lineGetKey(line)] = line
                        }
                    }
                    existedStringsFromTarget
                } catch (e: Exception) {
                    throw GradleException(e.message)
                    e.printStackTrace()
                }
            }
        }


        try {
            val sourceFile = File(sourceStringsFilePath)
            val myReader = Scanner(sourceFile)
            while (myReader.hasNextLine()) {
                val line: String = myReader.nextLine()
                if (line.contains("<string ") && !line.contains("translatable=\"false\"") && !line.contains("<!--")) {
                    var resultString: String? = null
                    val lineKey = lineGetKey(line)
                    if (mode == TranslateMode.SOFT) {
                        resultString = existedStringsFromTarget[lineKey]
                    }
                    if (resultString == null) {
                        val stringStart = line.indexOf(">") + 1
                        val stringEnd = line.indexOf("<", stringStart)

                        outputString.append(line.substring(0, stringStart))
                        val srcString = line.substring(stringStart, stringEnd)
                        val translated = getTranslated(srcString, srcLanguage, targetLanguage)
                            .trim()
                            .let { keepCase(srcString, it) }
                        outputString.append(translated)
                        outputString.append(line.substring(stringEnd))

                        println("${lineKey}: ${srcString} -> ${translated}")
                    } else {
                        outputString.append(resultString)
                    }
                } else {
                    outputString.append(line)
                }
                outputString.append("\r\n")
            }
            myReader.close()
        } catch (e: FileNotFoundException) {
            println("An error occurred.")
            throw GradleException(e.message)
            e.printStackTrace()
        }

        File(targetStringsFilePath).writeStr(outputString.toString())


//        colorsMap.entries.joinToString { (colorName, color) ->
//            "\n    <color name=\"$colorName\">$color</color>"
//        }.also { xml ->
//            File(targetStringsFilePath).writeXlmWithTags(xml)
//        }
    }

    fun init() {
        projectPath = project.projectDir.path //C:\GradleProjects\StringsRobber\app
        sourceStringsFilePath = projectPath + File.separator + stringInputFilePath
        targetStringsFilePath = projectPath + File.separator + stringOutputFilePath
    }

    fun lineGetKey(line: String): String {
        var lineKey = line.substring(line.indexOf("name=\"") + 6)
        lineKey = lineKey.substring(0, lineKey.indexOf("\""))
        return lineKey
    }

    fun getTranslated(src: String, from: String, to: String): String {
        val translation: Translation = translate.translate(
            src,
            Translate.TranslateOption.sourceLanguage(from),
            Translate.TranslateOption.targetLanguage(to)
        )
        return translation.translatedText
    }

    private fun keepCase(src: String, translated: String): String {
        if (keepCase) {
            if (src[0].isLowerCase()) {
                return translated[0].toLowerCase() + translated.substring(1)
            } else {
                return translated[0].toUpperCase() + translated.substring(1)
            }
        }
        return translated
    }

}