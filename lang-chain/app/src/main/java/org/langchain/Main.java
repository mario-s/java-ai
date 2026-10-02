package org.langchain;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import org.langchain.assistants.fruits.Workflow;
import org.langchain.model.ModelFactory;

public class Main {

    public static void main(String[] args) {
        runWorkflow();
    }

    private static void runWorkflow() {
        ChatModel model = ModelFactory.llama("Qwen/Qwen3-8B-GGUF:Q4_K_M", 1.0);
        StreamingChatModel streamingModel = ModelFactory.streamingLlama("Qwen/Qwen3-8B-GGUF:Q4_K_M", 1.0);
        var workflow = new Workflow(model, streamingModel);
        workflow.run();
    }
}
