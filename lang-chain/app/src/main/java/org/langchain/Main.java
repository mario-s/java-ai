package org.langchain;

import dev.langchain4j.model.chat.ChatModel;
import org.langchain.agents.fruits.Workflow;
import org.langchain.model.ModelFactory;

public class Main {

    public static void main(String[] args) {
        ChatModel model = ModelFactory.llama("Qwen/Qwen3-8B-GGUF:Q4_K_M", 1.0);
        var orchestration = new Workflow();
        orchestration.run(model);
    }
}
