package com.bigid.search.task.util;

import com.bigid.search.task.Main;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

@Slf4j
@UtilityClass
public class FileReaderUtil {

    public static LineIterator createLineIteratorFromFile(String fileName) {
        LineIterator lineIterator = null;
        try {
            Optional<URL> resource = Optional.ofNullable(Main.class.getClassLoader().getResource(fileName));
            File file = new File(resource.orElseThrow(() -> new FileNotFoundException("File not found " + fileName)).getFile());
            lineIterator = FileUtils.lineIterator(file);
        } catch (IOException e) {
            log.error("Could create lineIterator from file {}", fileName, e);
        }
        return lineIterator;
    }
}
