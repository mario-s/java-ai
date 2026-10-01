# open-weather
This is a service that exposes a MCP server to request 
weather information from
[Openweathermap](https://openweathermap.org).

It requires an API Key from Openweathermap.

## Connecting to an MCP Client
### Claude Desktop
Open Settings > Developer > Edit Config. This should bring up `claude_desktop_config.json`.
There you can add a new entry for `mcpServers`.
#### STDIO
In this example the channel between client and server is based on stdio. This is the simplest setup for an MCP server. However, it might come with costs for the startup time.
```
  "mcpServers": {
    "open-weather-server": {
      "command": "java",
      "args": [
        "-Dspring.ai.mcp.server.stdio=true",
        "-Dspring.main.web-application-type=none",
        "-DOPENWEATHER_API_KEY=<YOUR-API-KEY>",
        "-jar",
        "/<PATH-TO-PROJECT>/open-weather/build/libs/weather-server-0.0.1-SNAPSHOT.jar"
      ]
    }
  }
```
#### HTTP
Another way for communication between client and server is by using the HTTP protocol. However, Claude Desktop can not directly communicate via HTTP, and therefore you need an HTTP bridge.
Here is a configuration that uses the [hyper-mcp-remote](https://github.com/hyper-mcp-rs/hyper-mcp-remote) bridge:
```
  "mcpServers": {
    "open-weather-server": {
      "command": "hyper-mcp-remote",
      "args": [
        "--no-auth",
        "http://127.0.0.1:8080/mcp"
      ]
    }
  }
```
This setup requires that the MCP server already runs. To do so, navigate to the project folder, export the Openweatermap API key and start the server.
```
export OPENWEATHER_API_KEY=<YOUR_KEY>
./gradlew bootRun
```
##### Pro & Contra
|                        | Direct Java via STDIO                | Rust HTTP Bridge + Spring Server                                  |
| ---------------------- | ------------------------------------ |-------------------------------------------------------------------|
| Processes              | 1 Java process                       | 1 Rust + 1 Java process                                           |
| Additional hop         | None                                 | Rust → HTTP → Java                                                |
| JSON-RPC serialization | 1×                                   | Additional serialization via HTTP                                 |
| TCP/HTTP overhead      | None                                 | Low                                                               |
| Startup                | **Slower**                           | Bridge starts almost instantly; Spring must be running separately |
| First request          | **Slower** if Java is still starting | **Fast** if server is already running                             |
| Subsequent requests    | **Very good**                        | **Very good**, but with slightly more overhead                    |
| RAM                    | Java                   | Java + small Rust process                                         |
| Complexity             | **Minimal**                          | Higher                                                            |


### Conclusion
To achieve fast startup without overhead, this project could be implemented in [Quarkus and build as a native executable](https://quarkus.io/guides/building-native-image/), or even written in Rust, and then use STDIO.

