package org.langchain.agents.fruits;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Workflow to interact with two agents about the health benefit of fruits.
 */
public class Workflow {

    private static final Logger LOG = LoggerFactory.getLogger(Workflow.class);

    public void run(ChatModel model) {
        FruitAgent fruitAgent = AiServices.create(FruitAgent.class, model);
        BenefitAgent benefitAgent = AiServices.create(BenefitAgent.class, model);

        Set<String> fruits = new HashSet<>();
        do {
            String fruit = fruitAgent.generateFruit().toLowerCase(Locale.ENGLISH);
            if (fruits.add(fruit)) {
                String benefit = benefitAgent.generateBenefit(fruit);
                LOG.info("\r\n> {}:\r\n{}\r\n", fruit, benefit);
            }
        } while (fruits.size() < 6);
    }
}
