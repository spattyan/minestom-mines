package com.yanspatt.loader;

import java.io.IOException;

public interface ConfigLoader<T> {
    T loadConfig(String filePath) throws IOException;
}
