package org.doochul.ui.riot.dto;

public record LeagueEntry(
        String leagueId,
        String queueType,
        String tier,
        String rank,
        String summonerId,
        String summonerName,
        Long leaguePoints,
        Long wins,
        Long losses,
        boolean veteran,
        boolean inactive,
        boolean freshBlood,
        boolean hotStreak
) {
}
