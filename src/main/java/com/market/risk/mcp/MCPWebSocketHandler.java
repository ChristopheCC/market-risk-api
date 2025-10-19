package com.market.risk.mcp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.risk.service.RiskService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import java.util.Map;

@Component
public class MCPWebSocketHandler extends TextWebSocketHandler {

    private final RiskService riskService;
    private final ObjectMapper objectMapper;

    public MCPWebSocketHandler(RiskService riskService) {
        this.riskService = riskService;
        this.objectMapper = new ObjectMapper();
        // Register module for LocalDate, LocalDateTime, etc.
        this.objectMapper.registerModule(new JavaTimeModule());
        // Optional: write dates as ISO strings
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        Map<String, String> request = objectMapper.readValue(payload, Map.class);

        String action = request.get("action");
        Object response;

        switch (action) {
            case "positions":
                response = riskService.getPositions();
                break;
            case "marketdata":
                String instrument = request.get("instrument");
                response = riskService.getMarketData(instrument);
                break;
            default:
                response = Map.of("error", "Unknown action");
        }

        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(response)));
    }
}