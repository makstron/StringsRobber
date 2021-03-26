import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class StringRobber : Plugin<Project> {

    override fun apply(project: Project) {
        println("Magic goes here")

        project.android().variants().all { variant ->

            // Make a task for each combination of build type and product flavor
            val myTask = "myFirstTask${variant.name.capitalize()}"

            // Register a simple task as a lambda. We can later move this to its own
            // class to make our code cleaner and also add some niceties.
            project.tasks.create(myTask){task ->

                // Group all our plugin's tasks together
                task.group = "MyPluginTasks"
                task.doLast {
                    File("${project.projectDir.path}/myFirstGeneratedFile.txt").apply {
                        writeText("Hello Gradle!\nPrinted at: ${SimpleDateFormat("HH:mm:ss").format(
                            Date()
                        )}")
                    }
                }
            }
        }
    }
}