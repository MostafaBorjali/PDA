plugins {
    id(GradleDependency.KTLINT_GRADLE) version CoreVersions.KTLINT_GRADLE
}
buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(GradleDependency.GRADLE_BUILD_TOOLS)
        classpath(GradleDependency.KOTLIN_GRADLE)
        classpath(GradleDependency.OBJECT_BOX_GRADEL)
        // classpath(GradleDependency.KTLINT_GRADLE_PATH)
    }
}
allprojects {
    repositories {
        google()
        jcenter()
    }
}
subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
