package org.langchain;

import org.langchain.agents.fruits.Workflow;

public class Main {

    public static void main(String[] args) {
        var orchestration = new Workflow();
        orchestration.run();
    }
}
