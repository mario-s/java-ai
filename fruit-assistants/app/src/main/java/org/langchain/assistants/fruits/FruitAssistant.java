package org.langchain.assistants.fruits;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface FruitAssistant {

    @SystemMessage("""
        You generate the name of a fruit. Choose from common fruits around the world.
        Every invocation should select a different fruit. Do not use {{last}} again.
        Return only the fruit name in singular.
        """)
    @UserMessage("Generate a fruit.")
    String generateFruit(@V("last") String last);
}
