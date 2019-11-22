import kotlin.String
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

/**
 * Generated by https://github.com/jmfayard/buildSrcVersions
 *
 * Find which updates are available by running
 *     `$ ./gradlew buildSrcVersions`
 * This will only update the comments.
 *
 * YOU are responsible for updating manually the dependency version.
 */
object Versions {
  const val de_fayard_buildsrcversions_gradle_plugin: String = "0.5.0" // available: "0.7.0"

  const val com_android_tools_build_gradle: String = "3.5.0" // available: "3.5.2"

  const val gradle_maven_publish_plugin: String = "0.8.0"

  const val constraint_layout: String = "1.0.2" // available: "1.1.3"

  const val mpandroidchart: String = "v3.0.2" // available: "3.1.0"

  const val espresso_core: String = "2.2.2" // available: "3.0.2"

  const val appcompat_v7: String = "27.0.2" // available: "28.0.0"

  const val lint_gradle: String = "26.5.0" // available: "26.5.2"

  const val junit: String = "4.12"

  /**
   *
   * See issue 19: How to update Gradle itself?
   * https://github.com/jmfayard/buildSrcVersions/issues/19
   */
  const val gradleLatestVersion: String = "6.0.1"

  const val gradleCurrentVersion: String = "5.4.1"
}

/**
 * See issue #47: how to update buildSrcVersions itself
 * https://github.com/jmfayard/buildSrcVersions/issues/47
 */
val PluginDependenciesSpec.buildSrcVersions: PluginDependencySpec
  inline get() =
      id("de.fayard.buildSrcVersions").version(Versions.de_fayard_buildsrcversions_gradle_plugin)
