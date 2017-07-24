package com.intellij.plugin.cph;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class CommandPromptHereConfiguration implements ApplicationComponent, Configurable {

    @Nls
    @Override
    public String getDisplayName() {
        return "CommandPromptHere";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return new JTextField("dddd");
    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public void apply() throws ConfigurationException {

    }
}
