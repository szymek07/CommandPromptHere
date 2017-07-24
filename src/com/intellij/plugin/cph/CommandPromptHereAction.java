package com.intellij.plugin.cph;

import com.intellij.ide.projectView.ProjectView;
import com.intellij.ide.projectView.impl.AbstractProjectViewPane;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.ide.util.treeView.NodeDescriptor;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.openapi.diagnostic.Logger;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class CommandPromptHereAction extends AnAction {

    public static final String PLUGIN_NAME = "CommandPromptHere";
    public static final String CMD_PATH = "/usr/bin/gnome-terminal";

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();
        if (project != null) {
            executeAction(project);
        }
    }

    private void executeAction(Project project) {
        VirtualFile file = getSelectedDirectory(project);
        if (file != null) {
            String path = file.getPath();
            openTerminalInDirectory(path);
        } else {
            Notificator.notifyError("Cannot determine selected file path!");
        }
    }

    private VirtualFile getSelectedDirectory(Project project) {
        AbstractProjectViewPane projectPane = ProjectView.getInstance(project).getCurrentProjectViewPane();
        DefaultMutableTreeNode node = projectPane.getSelectedNode();
        Object userObject = node.getUserObject();
        Object selectedObject = null;

        if (userObject instanceof AbstractTreeNode) {
            selectedObject = ((AbstractTreeNode) userObject).getValue();
        } else if (userObject instanceof NodeDescriptor) {
            selectedObject = ((NodeDescriptor) userObject).getElement();
        }

        VirtualFile vf = null;
        if (selectedObject != null && selectedObject instanceof PsiDirectory) {
            vf = ((PsiDirectory) selectedObject).getVirtualFile();
        } else if (selectedObject != null && selectedObject instanceof PsiElement) {
            vf = ((PsiElement) selectedObject).getContainingFile().getVirtualFile().getParent();
        }

        return vf;
    }

    private void openTerminalInDirectory(String path) {
        String[] cmdArr = {CMD_PATH, "/k", "start", "cd", path};
        try {
            Runtime.getRuntime().exec(cmdArr, null, new File(path));
        } catch (IOException e) {
            Notificator.notifyError("Error during executing action: " + Arrays.toString(cmdArr) +
                    "\n" + e.getMessage());
        }
    }

}
