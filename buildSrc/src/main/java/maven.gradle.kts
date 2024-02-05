fun aaa() {
    tasks.register("publishToGithub", Jar::class) {
        group = "publishing"
        from("android.sourceSets.main.java.srcDirs")
        archiveClassifier.set("sources")
    }
}