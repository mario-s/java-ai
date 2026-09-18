package org.langchain.agents.fruits;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface FruitAgent {

    @SystemMessage("""
        You generate the name of a fruit.
        Every invocation should select a different fruit when possible.
        Choose from common fruits around the world.
        Return only the fruit name in singular.
        """)
    @UserMessage("Generate a fruit.")
    String generateFruit();
}
