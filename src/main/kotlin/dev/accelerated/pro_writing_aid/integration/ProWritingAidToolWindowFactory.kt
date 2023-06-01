import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory

public class ProWritingAidToolWindowFactory : ToolWindowFactory {

    private val LOG: Logger = Logger.getInstance(ProWritingAidToolWindowFactory::class.java)

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        LOG.debug("Hello tool window!")
        TODO("Not yet implemented")
    }
}