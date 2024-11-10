package org.doochul.application.riot;

import org.doochul.ui.riot.dto.Summoner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SummonerService {

    private final BasicRiotApiService basicRiotApiService;

    @Value("${riot.api.key}")
    private String API_KEY;

    public SummonerService(BasicRiotApiService basicRiotApiService) {
        this.basicRiotApiService = basicRiotApiService;
    }

    public Summoner getSummonerInformationByPUUID(String PUUID) {
        String api_url = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-puuid/" + PUUID;
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Riot-Token", API_KEY);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Summoner> response = restTemplate.exchange(
                api_url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Summoner.class
        );
        Summoner summoner = response.getBody();
        return summoner;
    }

    public String getSummonerProfileIcon(Integer profileIconID) {
        String latestVersion = basicRiotApiService.getLatestLOLVersion();
        if (profileIconID == null) {
            profileIconID = 1;
        }
        String url = "https://ddragon.leagueoflegends.com/cdn/" + latestVersion + "/img/profileicon/" + Integer.toString(profileIconID) + ".png";
        return url;
    }


}