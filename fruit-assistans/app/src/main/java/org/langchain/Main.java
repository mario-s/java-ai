package org.langchain;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import me.bechberger.femtocli.FemtoCli;
import me.bechberger.femtocli.annotations.Command;
import me.bechberger.femtocli.annotations.Option;
import org.langchain.assistants.fruits.Workflow;
import org.langchain.model.ModelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

@Command(name = "langchain-app", description = "LangChain workflow runner", mixinStandardHelpOptions = true)
public class Main implements Callable<Integer> {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    @Option(names = {"-p", "--provider"}, description = "Model provider (llama or ollama)", defaultValue = "llama")
    private String provider = "llama";

    @Option(names = {"-m", "--model"}, description = "Model name", defaultValue = "Qwen/Qwen3-8B-GGUF:Q4_K_M")
    private String modelName = "Qwen/Qwen3-8B-GGUF:Q4_K_M";

    @Option(names = {"-t", "--temperature"}, description = "Temperature for sampling", defaultValue = "1.0")
    private double temperature = 1.0;

    @Override
    public Integer call() {
        LOG.info("Starting workflow with provider '{}', model '{}', temperature '{}'", provider, modelName, temperature);

        ChatModel model;
        StreamingChatModel streamingModel;

        if ("ollama".equalsIgnoreCase(provider)) {
            model = ModelFactory.ollama(modelName, temperature);
            streamingModel = ModelFactory.streamingOllama(modelName, temperature);
        } else {
            model = ModelFactory.llama(modelName, temperature);
            streamingModel = ModelFactory.streamingLlama(modelName, temperature);
        }

        new Workflow(model, streamingModel).run();

        return 0;
    }

    public static void main(String[] args) {
        FemtoCli.run(new Main(), args);
    }
}
