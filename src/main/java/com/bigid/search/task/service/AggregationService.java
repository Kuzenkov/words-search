package com.bigid.search.task.service;

import com.bigid.search.task.model.WordOffset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AggregationService {

    public Map<String, Set<WordOffset>> aggregate(List<Map<String, Set<WordOffset>>> wordsOffsets) {
        Map<String, Set<WordOffset>> aggregatedResult = new HashMap<>();
        wordsOffsets.stream()
                .flatMap(wordsOffset -> wordsOffset.entrySet().stream())
                .forEach(wordOffset -> {
                    if (aggregatedResult.containsKey(wordOffset.getKey())) {
                        aggregatedResult.get(wordOffset.getKey()).addAll(wordOffset.getValue());
                    } else {
                        aggregatedResult.put(wordOffset.getKey(), wordOffset.getValue());
                    }
                });
        return aggregatedResult;
    }
}
