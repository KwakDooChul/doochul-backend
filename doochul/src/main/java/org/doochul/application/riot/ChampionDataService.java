package org.doochul.application.riot;

import lombok.AllArgsConstructor;
import org.doochul.ui.riot.dto.ChampionData;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@AllArgsConstructor
public class ChampionDataService {
    private final BasicRiotApiService basicRiotApiService;

    public ChampionData.Champion getChampionInfoByChampionId(String receivedChampionKey) {
        if(receivedChampionKey == null) {
            receivedChampionKey = "266";
        }

        String latestLOLVersion = basicRiotApiService.getLatestLOLVersion();

        String api_url = "https://ddragon.leagueoflegends.com/cdn/"+ latestLOLVersion +"/data/ko_KR/champion.json";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ChampionData> response = restTemplate.exchange(
                api_url,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<ChampionData>() {}

        );

        ChampionData championData = response.getBody();

        Map<String, ChampionData.Champion> championMappingMap = championData.data();

        for (ChampionData.Champion value : championMappingMap.values()) {
            if (value.key().equals(receivedChampionKey)) {
                return value;
            }

        }
        return null;
    }
}
