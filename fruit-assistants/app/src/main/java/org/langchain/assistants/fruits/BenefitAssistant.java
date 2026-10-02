package org.langchain.assistants.fruits;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

@SystemMessage("""
             Your answer should contain a line break when there are more than 80 characters per a line.
             Do not add new empty lines between sentences.
        """)
public interface BenefitAssistant {

    @Agent("Returns the health benefit about a given fruit.")
    @UserMessage("""
            You are an expert for nutrition.
            Tell me at least one health benefit about the specified fruit.
            
             Fruit:
             {{fruit}}
        """)
    TokenStream generateBenefit(@V("fruit") String fruit);
}
