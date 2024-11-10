package org.doochul.ui.riot.dto;

import java.util.Map;

public record ChampionData(
        Map<String, Champion> data
) {
    public record Champion(
            String id,
            String key,
            String name,
            String title
    ) {
    }
}
