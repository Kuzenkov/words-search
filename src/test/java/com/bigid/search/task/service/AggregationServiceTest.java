package com.bigid.search.task.service;

import com.bigid.search.task.model.WordOffset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class AggregationServiceTest {

    public static final String TIMOTHY_KEY_WORD = "Timothy";
    public static final String RAYMOND_KEY_WORD = "Raymond";

    private AggregationService aggregationService;

    @BeforeEach
    public void init() {
        aggregationService = new AggregationService();
    }

    @Test
    @DisplayName("Success aggregate data")
    void givenListOfDataToAggregate_whenAggregate_thenReturnAggregatedData() {
        // given
        Set<WordOffset> setPartOne = new HashSet<>(Set.of(new WordOffset(13388, 1), new WordOffset(13752, 30)));
        Map<String, Set<WordOffset>> resultPartOne = Map.of(TIMOTHY_KEY_WORD, setPartOne);
        Set<WordOffset> setPartTwo = new HashSet<>(Set.of(new WordOffset(128346, 341)));
        Map<String, Set<WordOffset>> resultPartTwo = Map.of(RAYMOND_KEY_WORD, setPartTwo);
        Set<WordOffset> setPartThree = new HashSet<>(Set.of(new WordOffset(128405, 352)));
        Map<String, Set<WordOffset>> resultPartThree = Map.of(TIMOTHY_KEY_WORD, setPartThree);

        // when
        Map<String, Set<WordOffset>> aggregatedResult = this.aggregationService.aggregate(List.of(
                resultPartOne,
                resultPartTwo,
                resultPartThree));

        // then
        assertTrue(aggregatedResult.containsKey(TIMOTHY_KEY_WORD));
        assertTrue(aggregatedResult.containsKey(RAYMOND_KEY_WORD));
        assertEquals(3, aggregatedResult.get(TIMOTHY_KEY_WORD).size());
        assertEquals(1, aggregatedResult.get(RAYMOND_KEY_WORD).size());
    }

    @Test
    @DisplayName("Empty data for aggregation")
    void givenEmptyAggregationData_whenAggregate_thenReturnAggregatedData() {
        // given
        Map<String, Set<WordOffset>> resultPartOne = Map.of();
        Map<String, Set<WordOffset>> resultPartTwo = Map.of();

        // when
        Map<String, Set<WordOffset>> aggregatedResult = this.aggregationService.aggregate(List.of(resultPartOne, resultPartTwo));
        // then
        assertFalse(aggregatedResult.containsKey(TIMOTHY_KEY_WORD));
        assertFalse(aggregatedResult.containsKey(RAYMOND_KEY_WORD));
    }
}