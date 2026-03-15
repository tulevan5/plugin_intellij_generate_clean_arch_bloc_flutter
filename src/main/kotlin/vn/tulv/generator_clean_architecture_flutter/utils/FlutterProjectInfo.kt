package vn.tulv.generator_clean_architecture_flutter.utils

import com.intellij.openapi.project.Project

object FlutterProjectInfo {
    fun getPackageName(project: Project): String {
        // Tìm file pubspec.yaml ở thư mục gốc của project
        val pubspecFile = project.baseDir.findChild("pubspec.yaml")
        if (pubspecFile != null) {
            val content = String(pubspecFile.contentsToByteArray())
            // Dùng Regex để tìm dòng "name: "
            val regex = Regex("^name:\\s*(\\w+)", RegexOption.MULTILINE)
            val match = regex.find(content)
            return match?.groupValues?.get(1) ?: "flutter_project"
        }
        return "flutter_project"
    }
}