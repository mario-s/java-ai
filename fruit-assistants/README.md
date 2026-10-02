# Fruit Assistants

A project to play around with AI assistants of [LangChain4j](https://docs.langchain4j.dev) 🦜 and a locally available model.

## Requirements
It requires a model provider, this can be:
- [Ollama](https://ollama.com) or
- [llama.cpp](https://llama.app)

### Starting llama.cpp
`llama serve -hf Qwen/Qwen3-8B-GGUF:Q4_K_M --temp 1.3 --top-p 0.95 --top-k 40`

## Run the application
The Application accepts arguments to select the model provider and model. For all arguments, run with: <br/>
`./gradlew run --args='-h'`
