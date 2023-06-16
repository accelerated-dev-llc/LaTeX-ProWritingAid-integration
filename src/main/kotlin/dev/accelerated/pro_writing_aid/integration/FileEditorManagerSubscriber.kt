package dev.accelerated.pro_writing_aid.integration

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.fileEditor.FileEditorManagerEvent
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.PsiWhiteSpaceImpl
import com.intellij.psi.util.PsiTreeUtil
import dev.accelerated.pro_writing_aid.integration.latex.visitor.LatexTextVisitor
import nl.hannahsten.texifyidea.file.LatexFileType
import nl.hannahsten.texifyidea.psi.impl.LatexNoMathContentImpl
import nl.hannahsten.texifyidea.psi.impl.LatexNormalTextImpl
import nl.hannahsten.texifyidea.util.files.psiFile

class FileEditorManagerSubscriber : FileEditorManagerListener {

    private val LOG: Logger = Logger.getInstance(FileEditorManagerSubscriber::class.java)

    override fun selectionChanged(event: FileEditorManagerEvent) {
        super.selectionChanged(event)

        LOG.warn("Selection changed: ${event.newFile?.name}")
        if (event.newFile?.fileType == LatexFileType) {
            LOG.warn("TEX File opened: ${event.newFile?.name ?: "NULL"}")

            val psi= event.newFile?.psiFile(event.manager.project)

            LOG.warn("Starting to visit")
            val visitor = LatexTextVisitor()

            PsiTreeUtil
                .findChildrenOfAnyType(
                    psi,
                    true,
                    LatexNoMathContentImpl::class.java,
//                    LatexNormalTextImpl::class.java,
//                    PsiElement::class.java,
                    PsiWhiteSpaceImpl::class.java
                ).forEach {
                    it.accept(visitor)
                }

            LOG.warn("Appended texts:")
            LOG.warn(visitor.appendTexts())

        } else {
            LOG.warn("NON-TEX TILE OPENED: ${event.newFile?.name}")
        }

        LOG.warn("Selection changed: ${event.newFile?.name}")
    }
}

