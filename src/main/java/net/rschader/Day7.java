package net.rschader;

import java.util.*;

import net.rschader.utils.ResourceLoader;

public class Day7 {

    private static Map<String, Integer> parse(List<String> input) {
        String path = "/home";
        Map<String, Integer> map = new HashMap<>();

        for (String line : input) {
            if (line.startsWith("$ ls"))
                continue;

            else if (line.startsWith("$ cd .."))
                path = path.substring(0, path.lastIndexOf("/"));

            else if (line.startsWith("$ cd /"))
                path = "/home";

            else if (line.startsWith("$ cd"))
                path = path + "/" + line.substring(5);

            else if (line.startsWith("dir")) {
                String dirName = line.split(" ")[1];
                map.putIfAbsent(path + "/" + dirName, 0);
            }

            else {
                Integer fileSize = Integer.parseInt(line.split(" ")[0]);
                String tempPath = path;
                while (tempPath.length() > 0) {
                    map.put(tempPath, map.getOrDefault(tempPath, 0) + fileSize);
                    tempPath = tempPath.substring(0, tempPath.lastIndexOf("/"));
                }
            }

        }

        return map;
    }

    private static int part1(List<String> input) {
        Map<String, Integer> map = parse(input);
        int totalSize = 0;
        for (String key : map.keySet()) {
            int dirSize = map.get(key);
            if (dirSize <= 100000)
                totalSize += dirSize;
        }
        return totalSize;
    }

    private static int part2(List<String> input) {
        Map<String, Integer> map = parse(input);
        int unusedSpace = 70000000 - map.get("/home");
        int missingSpace = 30000000 - unusedSpace;

        int smallestDir = Integer.MAX_VALUE;
        for (String key : map.keySet()) {
            if (key.equals("/home")) continue;
            int dirSize = map.get(key);
            if (dirSize >= missingSpace) smallestDir = Math.min(smallestDir, dirSize);
        }

        return smallestDir;
    }

    public static void main(String[] args) {
        ResourceLoader resourceLoader = new ResourceLoader();
        List<String> input = resourceLoader.load("day7.txt");
        System.out.println(part1(input)); // 1427048
        System.out.println(part2(input)); // 2940614

    }

}