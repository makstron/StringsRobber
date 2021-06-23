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
internal open class TranslateResourcesTask_ : DefaultTask() {

    companion object {
            val ModeSoft = "soft"
            val ModeHard = "hard"
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
    var mode: String = "soft" //hard, soft


    private val outputString = StringBuilder()
    private lateinit var sourceStringsFilePath: String
    private lateinit var targetStringsFilePath: String
    private val existedStringsFromTarget= HashMap<String, String>()

    @TaskAction
    //The @TaskAction annotation denotes the entry method that Gradle will call when running the task. The method name itself is irrelevant.
    fun main() {
        println("I am running")
        init()

        if (mode == ModeSoft) {
            val targetFile = File(targetStringsFilePath)
            if (targetFile.exists()) {
                try {
                    val reader = Scanner(targetFile)
                    while (reader.hasNextLine()) {
                        val line: String = reader.nextLine()
                        if (line.contains("<string ") && !line.contains("translatable=\"false\"")) {
                            existedStringsFromTarget.put(lineGetKey(line), line)
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
                if (line.contains("<string ") && !line.contains("translatable=\"false\"")) {
                    var resultString: String? = null
                    if (mode == ModeSoft) {
                        resultString = existedStringsFromTarget[lineGetKey(line)]
                    }
                    if (resultString == null) {
                        val stringStart = line.indexOf(">")
                        val stringEnd = line.indexOf("<", stringStart)

                        outputString.append(line.substring(0, stringStart))
                        outputString.append(
                            getTranslated(
                                line.substring(stringStart, stringEnd),
                                srcLanguage,
                                targetLanguage
                            )
                        )
                        outputString.append(line.substring(stringEnd))
                    } else {
                        outputString.append(resultString)
                    }
                } else {
                    outputString.append(line)
                }
                outputString.append("\r\n")
                println(line)
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

//        val translation: Translation = translate.translate("¡Hola Mundo!")
//        System.out.printf("Translated Text:\n\t%s\n", translation.getTranslatedText())

        return src + "__"
    }


}