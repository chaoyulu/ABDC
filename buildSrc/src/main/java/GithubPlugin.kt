import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.register

class GithubPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("CreateGithubPlugin : " + target.name)
        target.tasks.register("publishToGithub", Jar::class) {
            println("CreateGithubPlugin2 : " + target.name)
            group = "publishing"
            from("android.sourceSets.main.java.srcDirs")
            archiveClassifier.set("sources")
        }
    }
}