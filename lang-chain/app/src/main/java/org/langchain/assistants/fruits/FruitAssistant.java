package org.langchain.assistants.fruits;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface FruitAssistant {

    @SystemMessage("""
        You generate the name of a fruit.
        Every invocation should select a different fruit when possible.
        Choose from common fruits around the world.
        Return only the fruit name in singular.
        """)
    @UserMessage("Generate a fruit.")
    String generateFruit();
}
