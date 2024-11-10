package org.doochul.ui.riot.dto;

public record Summoner(
        String accountId,
        Long revisionDate,
        String id,
        String puuid,
        String name,
        Long summonerLevel,
        Integer profileIconId
) {
}
