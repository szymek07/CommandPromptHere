package com.intellij.plugin.cph;

import com.intellij.openapi.components.*;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class CommandPromptHereConfiguration implements ApplicationComponent, Configurable {

    private CommandPromptSettingPanel panel;

    @Nls
    @Override
    public String getDisplayName() {
        return "CommandPromptHere";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (panel == null) {
            panel = new CommandPromptSettingPanel();
        }
        return panel;
    }

    @Override
    public boolean isModified() {
        return panel != null && panel.isModified();
    }

    @Override
    public void apply() throws ConfigurationException {
        if (panel != null) {
            panel.apply();
        }
    }

    @Override
    public void reset() {
        if (panel != null) {
            panel.reset();
        }
    }

    @Override
    public void disposeUIResources() {
        panel = null;
    }

}
