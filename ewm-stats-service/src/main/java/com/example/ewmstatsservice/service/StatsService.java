package com.example.ewmstatsservice.service;

import com.example.ewmstatsservice.dto.ViewStats;
import com.example.ewmstatsservice.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {


    void save(EndpointHit endpointHit);


    Integer getViews(String uri);


    List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique);
}
