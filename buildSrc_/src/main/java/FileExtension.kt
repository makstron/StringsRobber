import org.gradle.api.GradleException
import java.io.File

// New extension to write pre-formatted xml within resource tags
fun File.writeXlmWithTags(body: String) {
    ("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<resources>" +
            "$body\n" +
            "</resources>")
        .also { resXml ->
            try {
                createNewFile()
                writeText(resXml)
            } catch (e: Exception) {
                throw GradleException(e.message)
            }
        }
}

fun File.writeStr(body: String) {
    try {
        createNewFile()
        writeText(body)
    } catch (e: Exception) {
        throw GradleException(e.message)
    }
}