package dev.accelerated.pro_writing_aid.integration

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.Content
import com.intellij.ui.content.ContentFactory
import com.intellij.ui.dsl.builder.panel
import nl.hannahsten.texifyidea.file.LatexFileType
import nl.hannahsten.texifyidea.util.currentTextEditor
import nl.hannahsten.texifyidea.util.files.psiFile

public class ProWritingAidToolWindowFactory : ToolWindowFactory, DumbAware {

    private val LOG: Logger = Logger.getInstance(ProWritingAidToolWindowFactory::class.java)

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
//        toolWindow.title = "Grammar suggestions"
        val contentFactory = ContentFactory.getInstance()

        // get the file extension of the current file
        // if it is a latex file, then show the grammar suggestions
        // else show a message that the current file is not a latex file

//        project.currentTextEditor()?.

//        ProjectRootManager.getInstance(project).contentRoots.find { it. }
//        val loc: FileEditorLocation? = project.currentTextEditor()?.currentLocation
        val name = project.currentTextEditor()?.editor?.document?.psiFile(project)?.name
        val displayName: String = project.currentTextEditor()?.file?.fileType?.displayName ?: "NO DISPLAY NAME"
        val content: Content = if (project.currentTextEditor()?.file?.fileType == LatexFileType) {
            LOG.info("Latex project")
            contentFactory.createContent(
                panel {
                    row(displayName) {
                        label(project.currentTextEditor()?.file?.fileType.toString())
                    }
                    row("Name") {
                        label(name ?: "NO NAME")
                    }
                },
                "Grammar suggestions",
                false
            )
        } else {
            LOG.info("Not a latex project")
            contentFactory.createContent(
                panel {
                    row(displayName) {
                        label("NOT latex")
                    }
                },
                "Grammar suggestions",
                false
            )
        }
        toolWindow.contentManager.addContent(content)

    }
}