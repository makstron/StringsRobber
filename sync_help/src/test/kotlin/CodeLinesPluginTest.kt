import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome.SUCCESS
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class CodeLinesPluginTest {

    @get:Rule
    var testProjectDir = TemporaryFolder()

    private lateinit var buildFile: File
    private lateinit var gradleRunner: GradleRunner

    @Before
    fun setup() {
//        buildFile = testProjectDir.newFile("build.gradle")
//        buildFile.appendText("""
//            plugins {
//                id 'java'
//                id 'com.github.code-lines'
//            }
//
//        """.trimIndent())
//
        gradleRunner = GradleRunner.create()
            .withPluginClasspath()
            .withProjectDir(testProjectDir.root)
            .withTestKitDir(testProjectDir.newFolder())
            .apply {
                // gradle testkit jacoco support
//                File("./build/testkit/testkit-gradle.properties")
//                    .copyTo(File(projectDir, "gradle.properties"))
            }
    }

//    @Test
//    fun `try to do something`() {
//        assertTrue(true)
//    }
//
//    @Test
//    fun `codeLines task should print '0' when there is no source code`() {
////        val result = gradleRunner
////            .withArguments("myFirstTask")
////            .build()
////
////        result.task(":myFirstTask")//!!.outcome
////
////        assertEquals(SUCCESS, result.task(":myFirstTask")!!.outcome)
////        assertTrue(result.output.contains("Total lines: 0"))
//        assertTrue(true)
//    }
//
//    @Test
//    fun `codeLines task should print 'Total lines 6'`() {
//        val testClassLocation: File = testProjectDir.newFolder("src", "main", "java").resolve("TestClass.java")
//        CodeLinesPluginTest::class.java.classLoader
//            .getResource("TestClass.java")!!.file.let(::File)
//            .copyTo(testClassLocation)
//
//        val result = gradleRunner
//            .withArguments("codeLines")
//            .build()
//
//        assertEquals(SUCCESS, result.task(":codeLines")!!.outcome)
//        assertTrue(result.output.contains("Total lines: 6"))
//    }
//
//    @Test
//    fun `codeLines should skip blank lines`() {
//        val testClassLocation: File = testProjectDir.newFolder("src", "main", "java").resolve("TestClass.java")
//        CodeLinesPluginTest::class.java.classLoader
//            .getResource("TestClass.java")!!.file.let(::File)
//            .copyTo(testClassLocation)
//
//        buildFile.appendText("""
//            codeLinesStat {
//                sourceFilters.skipBlankLines = true
//            }
//        """.trimIndent())
//
//        val result = gradleRunner
//            .withArguments("codeLines")
//            .build()
//
//        assertEquals(SUCCESS, result.task(":codeLines")!!.outcome)
//        assertTrue(result.output.contains("Total lines: 5"))
//    }
}