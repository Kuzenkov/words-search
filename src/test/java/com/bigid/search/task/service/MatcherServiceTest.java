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

class MatcherServiceTest {

    public static final String TIMOTHY_KEY_WORD = "Timothy";
    public static final String RAYMOND_KEY_WORD = "Raymond";
    public static final String TEXT = "Timothy Flint, who published his entertaining _Recollections_ in 1826, \n"
            + "(Dr. Kenneth Maclachan's case.)] \n"
            + "hull  ominous  overboard  uproar  cavern  combine  respectively  menace  pilgrims  jeff  peak  currency  silken violet  khan  mastery  objective  plucked  litter  memorial  bids  fondly  clapped  tariff  beneficial  unsolicitedreluctant  separately  patronage  revenues  dragon  zeus  mike  ranges  vexation  indicates  overheard  tray  raymond  thereafter  exporting  mound  taxation  frenzy  horizontal  thirsty  disputed  charter  redistribution  boasted  item  moscow  termination  eminently  suggestive  linger  shady  calculation  expansion  mast  confer  sophia  commanders  pitied  twist  traditional  involve  interfered  achilles  wanton  repay  brother in law  routine  son in law  gaul  groom  solve  grassy  tempt  unsuccessful  witty  politician  yearning  lid  noticing  courtiers  cheering  bounty  consequent  renown  regulation  fowl  mayor  wrinkled  defy  threads  violation  junction  boss  particles \n"
            + "deficiency  operating  overthrown  gallows  diligent  hindu  blunt  omen  bleak  vehemently  wretchedness  e'er ensure  denotes  sentenced  unfair  encampment  possessor  absorbing  descendant  sub  drugs  engineers  independently  bucket  clerical  ache  glitter  ordinance  bamboo  amsterdam  vocation  admirer  limp  pallid  mildly  organisation  timothy  dealer  yorkshire  auspicious  deuce  emblem  gibson  primarily  reducing  ritual  decorations  thigh  groaning  scant  fiscal  mien  charging  cor  railing  peers  inferred  sanctity  accumulation  cynical  inspector  wardrobe  jesuit  texture  adjustment  epistle  adventurer  priesthood  seaman  turbulent  chant  marsh  palmer  unaware  vase  ty  initial  baths  weighty  minimum  correction  morsel  overlook  meagre  unanimous  magician  mystical  twenty three  inhabit  shaggy  unaccountable  nightmare  carbon  coil  lawless  stairway  willingness  sarcasm  crisp \n";
    public static final String KEY_WORDS = "Timothy,Raymond,Kenneth";

    private MatcherService matcherService;

    @BeforeEach
    public void init() {
        this.matcherService = new MatcherService();
    }

    @Test
    @DisplayName("Success match key words")
    void givenTextToMatch_whenMatch_thenReturnMatchedResult() {
        // given
        int currentLineOffset = 1;
        HashSet<String> wordsToFind = new HashSet<>(List.of(KEY_WORDS.split(",")));

        // when
        Map<String, Set<WordOffset>> result = matcherService.match(TEXT, wordsToFind, currentLineOffset);

        // then
        assertFalse(result.isEmpty());
        assertEquals(wordsToFind.size(), result.keySet().size());
        assertEquals(2, result.get(TIMOTHY_KEY_WORD).size());
        assertEquals(1, result.get(RAYMOND_KEY_WORD).size());
    }

    @Test
    @DisplayName("Empty text")
    void givenEmptyText_whenMatch_thenReturnEmptyResult() {
        // given
        int currentLineOffset = 1;
        HashSet<String> wordsToFind = new HashSet<>(List.of(KEY_WORDS.split(",")));

        // when
        Map<String, Set<WordOffset>> result = matcherService.match("", wordsToFind, currentLineOffset);

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Empty key words")
    void givenEmptyKeyWordsSet_whenMatch_thenReturnEmptyResult() {
        // given
        int currentLineOffset = 1;
        HashSet<String> wordsToFind = new HashSet<>();

        // when
        Map<String, Set<WordOffset>> result = matcherService.match(TEXT, wordsToFind, currentLineOffset);

        // then
        assertTrue(result.isEmpty());
    }
}