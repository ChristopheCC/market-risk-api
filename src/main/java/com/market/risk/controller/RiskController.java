package com.market.risk.controller;

import com.market.risk.service.RiskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class RiskController {

    private final RiskService riskService;

    public RiskController(RiskService riskService) {
        this.riskService = riskService;
    }

    @GetMapping("/positions")
    public List<Map<String, Object>> getPositions() {
        return riskService.getPositions();
    }

    @GetMapping("/marketdata/{instrument}")
    public List<Map<String, Object>> getMarketData(@PathVariable String instrument) {
        return riskService.getMarketData(instrument);
    }

    @GetMapping("/var")
    public Map<String, Object> getVar(
            @RequestParam String portfolio,
            @RequestParam(defaultValue = "2.33") double confidence) {
        return riskService.getVarEstimate(portfolio, confidence);
    }
}
