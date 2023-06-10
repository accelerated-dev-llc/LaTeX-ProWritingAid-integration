package dev.accelerated.pro_writing_aid.integration

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.FileEditorManagerEvent
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiPlainText
import com.intellij.psi.util.PsiTreeUtil
import nl.hannahsten.texifyidea.file.LatexFileType
import nl.hannahsten.texifyidea.psi.LatexContent
import nl.hannahsten.texifyidea.psi.LatexNormalText
import nl.hannahsten.texifyidea.psi.LatexVisitor
import nl.hannahsten.texifyidea.psi.impl.LatexContentImpl
import nl.hannahsten.texifyidea.psi.impl.LatexEnvironmentContentImpl
import nl.hannahsten.texifyidea.psi.impl.LatexNoMathContentImpl
import nl.hannahsten.texifyidea.psi.impl.LatexNormalTextImpl
import nl.hannahsten.texifyidea.psi.impl.LatexParameterTextImpl
import nl.hannahsten.texifyidea.util.currentTextEditor
import nl.hannahsten.texifyidea.util.files.psiFile

class FileEditorManagerSubscriber : FileEditorManagerListener {

    private val LOG: Logger = Logger.getInstance(FileEditorManagerSubscriber::class.java)

//    override fun fileOpened(source: FileEditorManager, file: VirtualFile) {
//        super.fileOpened(source, file)
//
//        // is this the currently active file?
//        // is this a LaTeX file?
//        // how to listen for complete openening / init of the project?
//
//        if (file.fileType == LatexFileType) {
//            LOG.warn("TEX File opened: ${file.name}")
//
//            val psi= file.psiFile(source.project)
//            val txt = psi?.text
//            LOG.warn("Starting to visit")
//            psi?.accept(LatexTextVisitor())
//        } else {
//            LOG.warn("NON-TEX TILE OPENED: ${file.name}")
//        }
//    }

//    override fun fileClosed(source: FileEditorManager, file: VirtualFile) {
//        super.fileClosed(source, file)
//
//        // how to cleanup resources?
//
//        LOG.warn("File closed: ${file.name}")
//    }

    override fun selectionChanged(event: FileEditorManagerEvent) {
        super.selectionChanged(event)

        LOG.warn("Selection changed: ${event.newFile?.name}")
        if (event.newFile?.fileType == LatexFileType) {
            LOG.warn("TEX File opened: ${event.newFile?.name ?: "NULL"}")

            val psi= event.newFile?.psiFile(event.manager.project)

            LOG.warn("Starting to visit")
            val visitor = LatexTextVisitor()
//            psi?.acceptChildren(visitor)
//            psi?.accept(LatexTextVisitor())
//            psi?.acc

            PsiTreeUtil
                .findChildrenOfType(psi, LatexEnvironmentContentImpl::class.java)
                .forEach {
                    it.accept(visitor)
                }

//            PsiTreeUtil.findChildrenOfAnyType(
//                content,
//                true,
//                LatexNoMathContentImpl::class.java,
//                LatexParameterTextImpl::class.java,
//                LatexNormalTextImpl::class.java,
//            ).forEach {
//                it.accept(visitor)
//                LOG.warn("Found: ${it.text}")
//            }
        } else {
            LOG.warn("NON-TEX TILE OPENED: ${event.newFile?.name}")
        }

        LOG.warn("Selection changed: ${event.newFile?.name}")
    }
}

