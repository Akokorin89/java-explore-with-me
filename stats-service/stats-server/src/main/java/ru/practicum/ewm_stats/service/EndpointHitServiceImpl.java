package ru.practicum.ewm_stats.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm_stats.dto.EndpointHitDto;
import ru.practicum.ewm_stats.dto.GetStatsDto;
import ru.practicum.ewm_stats.dto.ViewStatsDto;
import ru.practicum.ewm_stats.model.EndpointHit;
import ru.practicum.ewm_stats.repository.EndpointHitRepository;

import java.util.List;

import static ru.practicum.ewm_stats.EndpointHitMapper.toEndpointHit;
import static ru.practicum.ewm_stats.EndpointHitMapper.toEndpointHitDto;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EndpointHitServiceImpl implements EndpointHitService {

    private final EndpointHitRepository endpointHitRepository;

    @Transactional
    @Override
    public EndpointHitDto saveHit(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = toEndpointHit(endpointHitDto);
        log.info("Save endpoint hit.");
        return toEndpointHitDto(endpointHitRepository.save(endpointHit));
    }

    @Override
    public List<ViewStatsDto> getViewStats(GetStatsDto getStatsDto) {

        List<ViewStatsDto> viewStats;
        List<String> uris = getStatsDto.getUris();

        if (getStatsDto.getUris().isEmpty()) {
            viewStats = (getStatsDto.getUnique() ? endpointHitRepository.getStatsUniqueByTime(getStatsDto.getStart(), getStatsDto.getEnd())
                    : endpointHitRepository.getAllStatsByTime(getStatsDto.getStart(), getStatsDto.getEnd()));
        } else {
            viewStats = (getStatsDto.getUnique() ? endpointHitRepository.getStatsUniqueByTimeAndUris(getStatsDto.getStart(), getStatsDto.getEnd(), uris)
                    : endpointHitRepository.getStatsByTimeAndUris(getStatsDto.getStart(), getStatsDto.getEnd(), uris));
        }
        return viewStats;
    }
}
