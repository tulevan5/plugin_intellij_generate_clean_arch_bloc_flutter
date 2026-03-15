
package vn.tulv.generator_clean_architecture_flutter

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.vfs.VirtualFile
import vn.tulv.generator_clean_architecture_flutter.utils.FlutterProjectInfo
import vn.tulv.generator_clean_architecture_flutter.utils.TemplateGenerator

class CreateCleanArchAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val selectedDir = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return

        val dialog = CleanArchDialog()
        if (dialog.showAndGet()) {
            val fName = dialog.getFeatureName()
            val cName = dialog.getClassName()
            val prjName = FlutterProjectInfo.getPackageName(project)

            WriteCommandAction.runWriteCommandAction(project) {
                val root = selectedDir.createChildDirectory(this, fName)
                val blocContent = TemplateGenerator.getTemplate("bloc", fName, cName, prjName)
                val eventContent = TemplateGenerator.getTemplate("event", fName, cName, prjName)
                val stateContent = TemplateGenerator.getTemplate("state", fName, cName, prjName)
                val entityContent = TemplateGenerator.getTemplate("entity", fName, cName, prjName)
                val repositoryContent = TemplateGenerator.getTemplate("repository", fName, cName, prjName)
                val repositoryImplContent = TemplateGenerator.getTemplate("repository_impl", fName, cName, prjName)
                val remoteDSContent = TemplateGenerator.getTemplate("remote_ds", fName, cName, prjName)
                val modelContent = TemplateGenerator.getTemplate("model", fName, cName, prjName)
                val useCaseContent = TemplateGenerator.getTemplate("use_case", fName, cName, prjName)

                // -- Data layer ---
                val data = root.createChildDirectory(this, "data")
                val ds = data.createChildDirectory(this, "datasource")
                createFile(ds, "${fName}_local_data_source.dart", "abstract class ${cName}LocalDataSource {}")
                createFile(ds, "${fName}_remote_data_source.dart", remoteDSContent)
                createFile(data.createChildDirectory(this, "models"), "${fName}_response.dart", modelContent)
                createFile(data.createChildDirectory(this, "repositories"), "${fName}_repository_impl.dart", repositoryImplContent)

                // --- Tầng Domain ---
                val domain = root.createChildDirectory(this, "domain")
                createFile(domain.createChildDirectory(this, "entities"), "${fName}.dart", entityContent)
                createFile(domain.createChildDirectory(this, "repositories"), "${fName}_repository.dart", repositoryContent)
                createFile(domain.createChildDirectory(this, "usecases"), "get_${fName}.dart", useCaseContent)

                // --- Tầng Presentation ---
                val presentation = root.createChildDirectory(this, "presentation")
                val bloc = presentation.createChildDirectory(this, "bloc")
                createFile(bloc, "${fName}_bloc.dart", blocContent)
                createFile(bloc, "${fName}_event.dart", eventContent)
                createFile(bloc, "${fName}_state.dart", stateContent)
                presentation.createChildDirectory(this, "pages")
            }
        }
    }

    private fun createFile(dir: VirtualFile, name: String, content: String) {
        dir.createChildData(this, name).setBinaryContent(content.toByteArray())
    }

}