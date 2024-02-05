import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.register

abstract class GithubPublishTask : DefaultTask() {
    @TaskAction
    fun hahaha(jar: Jar) {
        println("CreateGithubPlugin2")
        jar.run {
            group = "publishing"
            from("android.sourceSets.main.java.srcDirs")
            archiveClassifier.set("sources")
        }
    }
}