package com.market.risk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;
import com.market.risk.mcp.MCPWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final MCPWebSocketHandler mcpWebSocketHandler;

    public WebSocketConfig(MCPWebSocketHandler mcpWebSocketHandler) {
        this.mcpWebSocketHandler = mcpWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(mcpWebSocketHandler, "/mcp")
                .setAllowedOrigins("*"); // allow cross-origin
    }
}
