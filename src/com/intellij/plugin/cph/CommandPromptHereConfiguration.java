package com.intellij.plugin.cph;

import com.intellij.openapi.components.*;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.SystemInfo;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Objects;

@State(
        name = "RenameComponent",
        storages = {@Storage(id = "CommandPromptHerePlugin", file = StoragePathMacros.MODULE_FILE)}
)
public class CommandPromptHereConfiguration implements ApplicationComponent, Configurable, PersistentStateComponent<CommandPromptHereConfiguration.State> {

    static class State {
        public String terminalPath;
        public String chdirDirective;
    }

    private State state;

    @com.intellij.util.xmlb.annotations.Transient
    private JTextField terminaPath;
    @com.intellij.util.xmlb.annotations.Transient
    private JButton browse;
    @com.intellij.util.xmlb.annotations.Transient
    private JTextField chdirDirective;
    @com.intellij.util.xmlb.annotations.Transient
    private JButton resetToDefaults;


    @Nls
    @Override
    public String getDisplayName() {
        return "CommandPromptHere";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        JPanel panel = getSettingsPanel();
        loadConfiguration();
        return panel;
    }

    @Override
    public boolean isModified() {
        String tp1 = (state!=null)? state.terminalPath: null;
        String chdir1= (state!=null)? state.chdirDirective: null;
        String tp2 = (terminaPath!=null)? terminaPath.getText(): null;
        String chdir2= (chdirDirective!=null)? chdirDirective.getText(): null;
        if (Objects.equals(tp1, tp2) &&
                Objects.equals(chdir1, chdir2)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void apply() throws ConfigurationException {
        if (state == null) {
            state = new State();
        }
        state.terminalPath = terminaPath.getText();
        state.chdirDirective = chdirDirective.getText();
    }

    private JPanel getSettingsPanel() {
        terminaPath = new JTextField();
        browse = new JButton("...");
        chdirDirective = new JTextField();
        chdirDirective.setToolTipText("Use $PATH for selected path");
        resetToDefaults = new JButton("Reset To Defaults");
        resetToDefaults.addActionListener(e -> resetToDefault());

        FormLayout layout = new FormLayout("f:p, 5dlu, f:p:g, p, 5dlu, p", "p, p, 5dlu, p");
        PanelBuilder builder = new PanelBuilder(layout);
        builder.setDefaultDialogBorder();

        CellConstraints cc = new CellConstraints();

        builder.addLabel("Terminal path:", cc.xy(1,1));
        builder.add(terminaPath, cc.xyw(3, 1, 2));
        builder.add(browse, cc.xy(6,1));
        builder.addLabel("Change directory directive:", cc.xy(1,2));
        builder.add(chdirDirective, cc.xyw(3,2, 2));
        builder.add(resetToDefaults, cc.xy(4,4));

        return builder.getPanel();
    }

    private void loadConfiguration() {
        if (state != null) {
            terminaPath.setText(state.terminalPath);
            chdirDirective.setText(state.chdirDirective);
        }
    }

    private void resetToDefault() {
        String cmdPath;
        String chdirCmd;

        if (SystemInfo.isLinux) {
            cmdPath = CommandPromptHereAction.LINUX_DEFAULT_CMD_PATH;
            chdirCmd = CommandPromptHereAction.LINUX_DEFAULT_CHDIR_CMD;
        } else if (SystemInfo.isWindows) {
            cmdPath = CommandPromptHereAction.WINDOWS_DEFUALT_CMD_PATH;
            chdirCmd = CommandPromptHereAction.WINDOWS_DEFUALT_CHDIR_CMD;
        } else if (SystemInfo.isMac) {
            cmdPath = CommandPromptHereAction.MAC_DEFUALT_CMD_PATH;
            chdirCmd = CommandPromptHereAction.MAC_DEFUALT_CHDIR_CMD;
        } else {
            cmdPath = "Cannot determine default terminal path";
            chdirCmd = "Cannot determine";
        }

        terminaPath.setText(cmdPath);
        chdirDirective.setText(chdirCmd);
    }


    @Nullable
    @Override
    public State getState() {
        return state;
    }

    @Override
    public void loadState(State state) {
        this.state = state;
    }

}
