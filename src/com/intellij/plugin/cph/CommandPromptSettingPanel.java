package com.intellij.plugin.cph;

import com.intellij.openapi.util.SystemInfo;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.*;
import java.util.Objects;

public class CommandPromptSettingPanel extends JPanel {

    private final CommandPromptSettings settings;

    private JTextField terminalPath;
    private JButton browse;
    private JTextField chdirDirective;
    private JButton resetToDefaults;

    public CommandPromptSettingPanel() {
        settings = CommandPromptSettings.getInstance();
        initComponents();
        initLayout();

        reset();
    }

    private void initComponents() {
        terminalPath = new JTextField();
        browse = new JButton("...");
        chdirDirective = new JTextField();
        chdirDirective.setToolTipText("Use $PATH for selected path");
        resetToDefaults = new JButton("Reset To Defaults");
        resetToDefaults.addActionListener(e -> resetToDefault());
    }

    private void initLayout() {
        FormLayout layout = new FormLayout("f:p, 5dlu, f:p:g, p, 5dlu, p", "p, p, 5dlu, p");
        PanelBuilder builder = new PanelBuilder(layout);
        builder.setDefaultDialogBorder();

        CellConstraints cc = new CellConstraints();

        builder.addLabel("Terminal path:", cc.xy(1,1));
        builder.add(terminalPath, cc.xyw(3, 1, 2));
        builder.add(browse, cc.xy(6,1));
        builder.addLabel("Change directory directive:", cc.xy(1,2));
        builder.add(chdirDirective, cc.xyw(3,2, 2));
        builder.add(resetToDefaults, cc.xy(4,4));


        setLayout(new FormLayout("f:p:g", "f:p:g"));
        add(builder.getPanel(), new CellConstraints().xy(1, 1));
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

        terminalPath.setText(cmdPath);
        chdirDirective.setText(chdirCmd);
    }

    public boolean isModified() {
        if (Objects.equals((settings != null)? settings.getTerminalPath(): null, (terminalPath !=null)? terminalPath.getText(): null) &&
                Objects.equals((settings != null)? settings.getChangeDirectoryDirective(): null, (chdirDirective!=null)? chdirDirective.getText(): null)) {
            return false;
        } else {
            return true;
        }
    }

    public void reset() {
        if (settings != null ) {
            terminalPath.setText(settings.getTerminalPath());
        }
        if (settings != null) {
            chdirDirective.setText(settings.getChangeDirectoryDirective());
        }
    }

    public void apply() {
        if (settings != null) {
            settings.setTerminalPath(terminalPath.getText());
            settings.setChangeDirectoryDirective(chdirDirective.getText());
        }
    }


}
