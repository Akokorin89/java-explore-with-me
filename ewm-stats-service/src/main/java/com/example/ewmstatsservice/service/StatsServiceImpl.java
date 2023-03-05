package com.example.ewmstatsservice.service;

import com.example.ewmstatsservice.dto.ViewStats;
import com.example.ewmstatsservice.model.EndpointHit;
import com.example.ewmstatsservice.service.repository.StatsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StatsRepository repository;

    @Override
    public void save(EndpointHit endpointHit) {
        log.info("StatsService: сохранение нового просмотра {}", endpointHit);
        repository.save(endpointHit);
    }

    @Override
    public Integer getViews(String uri) {
        log.info("StatsService: получение статистики просмотров по uri={}.", uri);
        return repository.getViews(uri);
    }

    @Override
    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique) {
        log.info("StatsService: получение статистики просмотров в период с {} по {} для uri={}, где unique={},",
                start, end, uris, unique);
        List<ViewStats> viewStats;
        if (!unique) {
            viewStats = repository.findAllNotUniqueOrderByViewDesc(start, end);
        } else {
            viewStats = repository.findAllUniqueOrderByViewDesc(start, end);
        }
        if (uris != null) {
            return viewStats.stream()
                    .map(view -> filterByUri(view, uris))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } else {
            return viewStats;
        }
    }

    private ViewStats filterByUri(ViewStats viewStats, String[] uris) {
        for (String uri : uris) {
            if (viewStats.getUri().equals(uri)) {
                return viewStats;
            }
        }
        return null;
    }
}
