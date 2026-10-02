package org.langchain.assistants.fruits;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.service.AiServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static dev.langchain4j.service.AiServices.create;

/**
 * Workflow to interact with two assistants about the health benefit of fruits.
 */
public class Workflow {
    private static final int MAX_REPEATS = 6;
    private static final Logger LOG = LoggerFactory.getLogger(Workflow.class);

    private final FruitAssistant fruitAssistant;
    private final BenefitAssistant benefitAssistant;

    public Workflow(ChatModel model, StreamingChatModel streamingModel) {
        fruitAssistant = AiServices.builder(FruitAssistant.class)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(MAX_REPEATS))
                .chatModel(model).build();
        benefitAssistant = create(BenefitAssistant.class, streamingModel);
    }

    public void run() {
        Set<String> fruits = new HashSet<>();
        String fruit = "";
        do {
            fruit = fruitAssistant.generateFruit(fruit).toLowerCase(Locale.ENGLISH);
            if (fruits.add(fruit)) {
                System.out.printf("%n> %s:%n", fruit);

                CompletableFuture<Void> future = new CompletableFuture<>();
                benefitAssistant.generateBenefit(fruit)
                        .onPartialResponse(System.out::print)
                        .onCompleteResponse(response -> {
                            System.out.println();
                            future.complete(null);
                        })
                        .onError(future::completeExceptionally)
                        .start();

                future.join();
            } else {
                LOG.warn("The fruit {} was already selected by first assistant. Ignoring it!\n", fruit);
            }
        } while (fruits.size() < MAX_REPEATS);
    }
}
