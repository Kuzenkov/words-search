package com.bigid.search.task;

import com.bigid.search.task.model.WordOffset;
import com.bigid.search.task.service.AggregationService;
import com.bigid.search.task.service.MatcherService;
import com.bigid.search.task.util.FileReaderUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.LineIterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static com.bigid.search.task.util.ChunkUtil.CHUNK_SIZE;
import static com.bigid.search.task.util.ChunkUtil.createMatchersChunks;
import static java.util.Objects.isNull;

@Slf4j
public class Main {

    public static final String FILE_NAME = "big.txt";
    public static final String WORD_SEPARATOR = ",";

    public static final String WORDS_AS_STRING = "James,John,Robert,Michael,William,David,Richard," +
            "Charles,Joseph,Thomas,Christopher,Daniel,Paul,Mark,Donald" +
            ",George,Kenneth,Steven,Edward,Brian,Ronald,Anthony,Kevin," +
            "Jason,Matthew,Gary,Timothy,Jose,Larry,Jeffrey," +
            "Frank,Scott,Eric,Stephen,Andrew,Raymond,Gregory," +
            "Joshua,Jerry,Dennis,Walter,Patrick,Peter,Harold,Douglas," +
            "Henry,Carl,Arthur,Ryan,Roger";


    @SneakyThrows
    public static void main(String[] args) {
        int currentLineNumber = 1;

        MatcherService matcherService = new MatcherService();
        AggregationService aggregationService = new AggregationService();
        Set<String> keyWords = new HashSet<>(Arrays.asList(WORDS_AS_STRING.split(WORD_SEPARATOR)));
        List<CompletableFuture<Map<String, Set<WordOffset>>>> completableFutures = new ArrayList<>();

        try (LineIterator lineIterator = FileReaderUtil.createLineIteratorFromFile(FILE_NAME)) {
            if (isNull(lineIterator)) {
                log.error("Could not get line iterator");
                return;
            }

            while (lineIterator.hasNext()) {
                String matchersChunks = createMatchersChunks(lineIterator);
                int currentLine = currentLineNumber;
                completableFutures.add(CompletableFuture.supplyAsync(() -> matcherService.match(matchersChunks, keyWords,
                        currentLine)));
                currentLineNumber += CHUNK_SIZE;
            }
        }

        List<Map<String, Set<WordOffset>>> result = completableFutures.stream()
                .map(CompletableFuture::join)
                .toList();

        aggregationService
                .aggregate(result)
                .forEach((key, value) -> log.debug("{} --> {}", key, value));
    }
}
