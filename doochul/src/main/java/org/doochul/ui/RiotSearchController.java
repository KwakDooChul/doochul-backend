package org.doochul.ui;

import lombok.AllArgsConstructor;
import org.doochul.application.riot.*;

import org.doochul.ui.riot.dto.Account;
import org.doochul.ui.riot.dto.Summoner;
import org.doochul.ui.riot.dto.ChampionMastery;
import org.doochul.ui.riot.dto.ChampionData;
import org.doochul.ui.riot.dto.LeagueEntry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class RiotSearchController {

    private final AccountService accountService;
    private final SummonerService summonerService;
    private final ChampionMasteryService championMasteryService;
    private final ChampionDataService championDataService;
    private final ChampionImgService championImgService;
    private final RecordService recordService;

    @GetMapping("/search")
    public String search(@RequestParam String gameName, @RequestParam("tagLine") String tagLine) {
        Account account = accountService.getAccountInfomation(gameName, tagLine);

        Summoner summoner = summonerService.getSummonerInformationByPUUID(account.puuid());

        List<ChampionMastery> championMasteryList = championMasteryService.getChampionMasteryInformationsByPUUID(account.puuid(), 5);
        for (ChampionMastery championMasteryEach : championMasteryList) {
            ChampionData.Champion champion = championDataService.getChampionInfoByChampionId(Long.toString(
                    championMasteryEach.championId()));

            championMasteryEach.championName(champion.name());
            championMasteryEach.championPortraitImgURL(championImgService.getChampionPortraitImgURLByChampionName(champion.id()));
        }

        List<LeagueEntry> leagueEntryDtoList = championMasteryService.getUserTear(summoner.id());
        String[] matchId = recordService.getMatchId(account.puuid(), 5);


        return "search";
    }
}
