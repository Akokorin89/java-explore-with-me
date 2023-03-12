package ru.practicum.ewm_stats.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewm_stats.dto.GetStatsDto;

import java.util.Map;

@Service
public class ViewStatsClient extends BaseClient {
    private static final String API_PREFIX = "/stats";

    @Autowired
    public ViewStatsClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build()
        );
    }

    public ResponseEntity<Object> getStats(GetStatsDto getStatsDto) {
        Map<String, Object> parameters;
        if (getStatsDto.getUris() != null) {
            parameters = Map.of(
                    "start", getStatsDto.getStart(),
                    "end", getStatsDto.getEnd(),
                    "uris", getStatsDto.getUris(),
                    "unique", getStatsDto.getUnique()
            );
            return get("?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
        }
        return get("?start={start}&end={end}&unique={unique}", Map.of(
                "start", getStatsDto.getStart(),
                "end", getStatsDto.getEnd(),
                "unique", getStatsDto.getUnique()));
    }

}
