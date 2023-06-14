package dev.accelerated.pro_writing_aid.integration

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener

class ProjectManagerSubscriber : ProjectManagerListener {

    private val LOG: Logger = Logger.getInstance(ProjectManagerSubscriber::class.java)

//    override fun projectOpened(project: Project) {
//        super.projectOpened(project)
//
//        LOG.warn("Project opened: ${project.name}")
//    }

//    override fun projectClosed(project: Project) {
//        super.projectClosing(project)
//
//        LOG.warn("Project closed: ${project.name}")
//    }
}