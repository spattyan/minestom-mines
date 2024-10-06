package com.yanspatt.util;

import org.tomlj.Toml;
import org.tomlj.TomlParseResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TomlUtils {

    public static TomlParseResult loadTomlFile(String path) {
        try {
            return Toml.parse(Files.newBufferedReader(Path.of(path)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getConfigValue(TomlParseResult toml, String key) {
        return toml.getString(key);
    }

}
