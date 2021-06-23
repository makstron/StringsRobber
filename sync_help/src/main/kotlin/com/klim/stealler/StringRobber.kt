package com.klim.stealler

//import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.translate.*
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class StringRobber : Plugin<Project> {

    override fun apply(project: Project) {
//        println("StringRobber ${project.extensions}")
        println("StringRobber 0.0.3")

        val extension = project.extensions.create("string_robber", PluginParameters::class.java)
//        println("extension.message = " + extension.message)

        val outputPath = "${project.buildDir}/generated/res" //todo shoud be set
//        val outputPath = "${project.projectDir.path}/translations" //todo shoud be set


        project.tasks.register("translateResources", TranslateResourcesTask::class.java
        ) { translateTask ->
//            translateTask.setT(translate)
            translateTask.group = "string_robber"

//            val outputDirectory = File("$outputPath/${variant.dirName}").apply { mkdir() }
//            translateTask.projectPath = outputDirectory.path

            // We write our output in the build folder. Also note that we want to have a
            // reference to this so we can later mark it as a generated resource folder

//                translateTask.outputFile = outputDirectory.path +  "values/generated_colors.xml"

            // Marks the output directory as an app resource folder
//                variant.registerGeneratedResFolders(
//                    project.files(outputDirectory).builtBy(
//                        translateTask
//                    )
//                )
        }
    }

}
