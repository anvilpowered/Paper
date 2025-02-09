import jetbrains.buildServer.configs.kotlin.BuildFeatures
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.DslContext
import jetbrains.buildServer.configs.kotlin.FailureAction
import jetbrains.buildServer.configs.kotlin.buildFeatures.PullRequests
import jetbrains.buildServer.configs.kotlin.buildFeatures.commitStatusPublisher
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildFeatures.pullRequests
import jetbrains.buildServer.configs.kotlin.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.project
import jetbrains.buildServer.configs.kotlin.projectFeatures.githubIssues
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import jetbrains.buildServer.configs.kotlin.version

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2023.11"

project {

    val publish = Publish()

    buildType(publish)

    features {
        githubIssues {
            id = "PROJECT_EXT_3"
            displayName = "papermc/paper"
            repositoryURL = "https://github.com/anvilpowered/paper"
            authType = accessToken {
                accessToken = "credentialsJSON:f57a4fdd-fb30-41c0-9983-620364336d03"
            }
            param("tokenId", "")
        }
    }
}

fun BuildType.configureVcs() {
    vcs {
        root(DslContext.settingsRoot)
    }
}

fun BuildFeatures.configureBaseFeatures() {
    perfmon {}
    commitStatusPublisher {
        vcsRootExtId = "${DslContext.settingsRoot.id}"
        publisher = github {
            githubUrl = "https://api.github.com"
            authType = personalToken {
                token = "credentialsJSON:f57a4fdd-fb30-41c0-9983-620364336d03"
            }
        }
    }
}

class Publish : BuildType() {
    init {
        name = "publish"

        configureVcs()
        features {
            configureBaseFeatures()
        }

        requirements {
            contains("env.AGENT_NAME", "publishing")
        }

        steps {
            gradle {
                tasks = "applyPatches"
                gradleParams = "--refresh-dependencies"
            }
            gradle {
                tasks = "publish"
            }
        }
    }
}
