package org.langchain.model;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;

public final class ModelFactory {

    public static ChatModel llama(String name, double temperature) {
        // llama uses OpenAI chat format
        return OpenAiChatModel.builder()
                .baseUrl("http://localhost:8080/v1")
                .modelName(name)
                .temperature(temperature)
                .build();
    }

    public static StreamingChatModel streamingLlama(String name, double temperature) {
        return OpenAiStreamingChatModel.builder()
                .baseUrl("http://localhost:8080/v1")
                .modelName(name)
                .temperature(temperature)
                .build();
    }

    public static ChatModel ollama(String name, double temperature) {
        return OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName(name)
                .temperature(temperature)
                .build();
    }

    public static StreamingChatModel streamingOllama(String name, double temperature) {
        return OllamaStreamingChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName(name)
                .temperature(temperature)
                .build();
    }
}
