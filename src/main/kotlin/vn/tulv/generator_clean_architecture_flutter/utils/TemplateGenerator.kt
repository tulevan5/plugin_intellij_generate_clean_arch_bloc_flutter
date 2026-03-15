package vn.tulv.generator_clean_architecture_flutter.utils

object TemplateGenerator {
    fun getTemplate(templateName: String, fName: String, cName: String, prjName: String): String {
        // Đọc file từ resources
        val inputStream = this.javaClass.getResourceAsStream("/templates/$templateName.txt")
        val content = inputStream?.bufferedReader()?.use { it.readText() } ?: ""

        // Thay thế các placeholder
        return content
            .replace("{{fName}}", fName)
            .replace("{{cName}}", cName)
            .replace("{{projectName}}", prjName)
    }
}