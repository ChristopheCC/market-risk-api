package com.market.risk.service;

import com.market.risk.repository.DuckDbRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RiskService {

    private final DuckDbRepository duckDbRepository;

    public RiskService(DuckDbRepository duckDbRepository) {
        this.duckDbRepository = duckDbRepository;
    }

    public List<Map<String, Object>> getPositions() {
        String sql = "SELECT * FROM positions LIMIT 50;";
        return duckDbRepository.query(sql);
    }

    public List<Map<String, Object>> getMarketData(String instrument) {
        String sql = String.format("SELECT * FROM market_data WHERE instrument = '%s' ORDER BY date DESC LIMIT 50;", instrument);
        return duckDbRepository.query(sql);
    }

    public Map<String, Object> getVarEstimate(String portfolio, double confidence) {
        // simple example placeholder
        String sql = String.format(
                "SELECT portfolio, AVG(pnl) AS avg_pnl, STDDEV(pnl) AS std_pnl, " +
                        "(AVG(pnl) - %.2f * STDDEV(pnl)) AS var_estimate " +
                        "FROM pnl_data WHERE portfolio = '%s' GROUP BY portfolio;",
                confidence, portfolio);

        List<Map<String, Object>> results = duckDbRepository.query(sql);
        return results.isEmpty() ? Map.of() : results.get(0);
    }
}
