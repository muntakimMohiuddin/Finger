package org.example.interfaces;

import java.io.IOException;

public interface FingerprintMatcher {
    public void add(String filename) throws IOException;

    public String match(String filepath) throws IOException;
}
