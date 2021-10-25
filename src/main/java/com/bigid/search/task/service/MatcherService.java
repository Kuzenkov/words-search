package com.bigid.search.task.service;

import com.bigid.search.task.model.WordOffset;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

public class MatcherService {

    public Map<String, Set<WordOffset>> match(String text, Set<String> wordToMatch, int startLineOffset) {
        if (isNull(wordToMatch) || wordToMatch.isEmpty() || isNull(text) || text.isBlank()) {
            return new HashMap<>();
        }
        Map<String, Set<WordOffset>> result = new HashMap<>();
        String[] textLines = text.split(System.lineSeparator());

        for (int i = 0; i < textLines.length; i++) {
            String line = textLines[i];

            int lineOffset = startLineOffset + i;

            for (String wordToFind : wordToMatch) {
                Set<WordOffset> wordsOffset = new HashSet<>();
                Pattern pattern = Pattern.compile(wordToFind, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(line);

                while (matcher.find()) {
                    wordsOffset.add(new WordOffset(lineOffset, matcher.start() + 1));
                }

                if (result.containsKey(wordToFind) && !wordsOffset.isEmpty()) {
                    result.get(wordToFind).addAll(wordsOffset);
                } else if (!wordsOffset.isEmpty()) {
                    result.put(wordToFind, wordsOffset);
                }
            }
        }
        return result;
    }
}
