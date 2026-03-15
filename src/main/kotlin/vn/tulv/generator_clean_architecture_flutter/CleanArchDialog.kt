package vn.tulv.generator_clean_architecture_flutter

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent
import javax.swing.JPanel

class CleanArchDialog : DialogWrapper(true) {
    private val featureNameField = JBTextField("feature_name")
    private val classNameField = JBTextField("ClassName")

    init {
        init()
        title = "Generate Clean Architecture Feature"
    }
    override fun createCenterPanel(): JComponent? {
        return FormBuilder.createFormBuilder()
            .addLabeledComponent(JBLabel("Feature name: "), featureNameField, 1, false)
            .addLabeledComponent(JBLabel("Class name: "), classNameField, 1, false)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    fun  getFeatureName(): String = featureNameField.text
    fun  getClassName(): String = classNameField.text

}