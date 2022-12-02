package net.rschader.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ResourceLoader {

    public List<String> load(String resourceName) {
        InputStream in = getClass().getClassLoader().getResourceAsStream(resourceName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        List<String> result = new ArrayList<>();
        try {
            while (reader.ready()) {
                String line = reader.readLine();
                result.add(line);
            }
            reader.close();
            return result;

        } catch (IOException e) {
            System.out.println("Failed to load input file: " + e.getMessage());
            throw new RuntimeException(e);

        }
    }

}
