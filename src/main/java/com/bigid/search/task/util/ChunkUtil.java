package com.bigid.search.task.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.LineIterator;

import static java.util.Objects.isNull;

@Slf4j
@UtilityClass
public class ChunkUtil {
    public static final int CHUNK_SIZE = 1000;

    public static String createMatchersChunks(LineIterator lineIterator) {
        int size = 0;
        StringBuilder chunk = new StringBuilder();
        while ((size < CHUNK_SIZE) && lineIterator.hasNext()) {
            String line = lineIterator.nextLine();
            if (isNull(line)) {
                break;
            }
            chunk.append(line).append(System.lineSeparator());
            size++;
        }
        return chunk.toString();
    }
}
