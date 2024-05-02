package org.doochul.ui.riot.dto;

public record ChampionMastery(

        Long championId,
        Integer championLevel,
        Integer championPoints,
        String championName,
        String championPortraitImgURL
) {
}
