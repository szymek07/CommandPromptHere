package com.intellij.plugin.cph;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.Nullable;

@State(name = "CommandPromptSettings", storages = @Storage("command_prompt_here_settings.xml"))
public class CommandPromptSettings implements PersistentStateComponent<CommandPromptSettings.State> {

    public static class State {
        @Nullable public String terminalPath = null;
        @Nullable public String chdirDirective = null;
    }

    private State myState = new State();

    public State getState() {
        return myState;
    }

    public void loadState(State state) {
        myState = state;
    }

    public static CommandPromptSettings getInstance() {
        return ServiceManager.getService(CommandPromptSettings.class);
    }

    @Nullable
    public String getTerminalPath() {
        return myState.terminalPath;
    }

    @Nullable
    public String getChangeDirectoryDirective() {
        return myState.chdirDirective;
    }

    public void setTerminalPath(@Nullable  String terminalPath) {
        myState.terminalPath = terminalPath;
    }

    public void setChangeDirectoryDirective(@Nullable String chdirDirective) {
        myState.chdirDirective = chdirDirective;
    }

}
