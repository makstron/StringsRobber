import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class StringRobber : Plugin<Project> {

    override fun apply(project: Project) {
//        println("Magic goes here _____________")
//        project.android().variants().all { variant ->
//            // Make a task for each combination of build type and product flavor
//            val translateResourcesTask = "translateResources${variant.name.capitalize()}"
//
////            val outputPath = "${project.buildDir}/generated/res" //todo shoud be set
//            val outputPath = "${project.projectDir.path}/translations" //todo shoud be set
//
//            project.tasks.register(translateResourcesTask, TranslateResourcesTask::class.java) { translateTask ->
//                translateTask.group = "StringRobber"
//
//                val outputDirectory = File("$outputPath/${variant.dirName}").apply { mkdir() }
//                translateTask.projectPath = outputDirectory.path
//
//                // We write our output in the build folder. Also note that we want to have a
//                // reference to this so we can later mark it as a generated resource folder
//
////                translateTask.outputFile = outputDirectory.path +  "values/generated_colors.xml"
//
//                // Marks the output directory as an app resource folder
////                variant.registerGeneratedResFolders(
////                    project.files(outputDirectory).builtBy(
////                        translateTask
////                    )
////                )
//            }
//        }
    }

    fun apply_1(project: Project) {
        println("Magic goes here _____________")

        project.android().variants().all { variant ->

            // Make a task for each combination of build type and product flavor
            val myTask = "myFirstTask${variant.name.capitalize()}"

            // Register a simple task as a lambda. We can later move this to its own
            // class to make our code cleaner and also add some niceties.
            project.tasks.create(myTask){task ->

                // Group all our plugin's tasks together
                task.group = "StringRobber"
                task.doLast {
                    File("${project.projectDir.path}/myFirstGeneratedFile.txt").apply {
                        writeText("Magic goes here\nHello Gradle!\nPrinted at: ${SimpleDateFormat("HH:mm:ss").format(
                            Date()
                        )}")
                    }
                }
            }
        }
    }

    fun apply_2(project: Project) {
        println("Magic goes here")
        project.android().variants().all { variant ->

            // Make a task for each combination of build type and product flavor
            val colorTaskName = "generateColors${variant.name.capitalize()}"

            val outputPath = "${project.buildDir}/generated/res"

            // Register a simple task as a lambda. We'll later move it its own class and add some niceties
            project.tasks.register(colorTaskName, ColorsTask::class.java) { colorTask ->
                colorTask.group = "StringRobber"

                // We write our output in the build folder. Also note that we want to have a
                // reference to this so we can later mark it as a generated resource folder
                val outputDirectory =
                    File("$outputPath/${variant.dirName}").apply { mkdir() }
                colorTask.outputFile = File(outputDirectory, "values/generated_colors.xml")

                // Marks the output directory as an app resource folder
                variant.registerGeneratedResFolders(
                    project.files(outputDirectory).builtBy(
                        colorTask
                    )
                )
            }
        }
    }
}