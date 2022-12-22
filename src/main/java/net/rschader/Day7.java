package net.rschader;

import java.util.*;

import net.rschader.utils.ResourceLoader;

public class Day7 {

    private static int getDirSize(String dir, Map<String, Set<String>> dirToFiles,
            Map<String, Set<String>> dirToChildren, Map<String, Integer> fileToSize) {
        Set<String> visited = new HashSet<>();
        Stack<String> stack = new Stack<>();
        stack.add(dir);
        int size = 0;
        while (!stack.isEmpty()) {
            String d = stack.pop();
            visited.add(d);
            Set<String> children = dirToChildren.getOrDefault(d, new LinkedHashSet<>());
            Set<String> fileList = dirToFiles.getOrDefault(d, new LinkedHashSet<>());

            for (String child : children) {
                if (!visited.contains(child))
                    stack.push(child);
            }

            for (String fileName : fileList) {
                size += fileToSize.get(fileName);
            }
        }

        return size;
    }

    private static int part1(List<String> input) {
        List<String> path = new ArrayList<>();

        Map<String, Set<String>> dirToChildren = new LinkedHashMap<>();
        Map<String, Set<String>> dirToFiles = new LinkedHashMap<>();
        Map<String, Integer> fileToSize = new LinkedHashMap<>();

        for (String line : input) {

            if (line.startsWith("$ ls"))
                continue;

            else if (line.startsWith("$ cd .."))
                path.remove(path.size() - 1);

            else if (line.startsWith("$ cd /")) {
                String home = "/";
                List<String> temp = new ArrayList<>();
                temp.add(home);
                path = temp;
                dirToFiles.put(home, dirToFiles.getOrDefault(home, new LinkedHashSet<>()));
                dirToChildren.put(home, dirToChildren.getOrDefault(home, new LinkedHashSet<>()));
            }

            else if (line.startsWith("$ cd")) {
                String direction = line.substring(5);
                String parent = path.get(path.size() - 1);
                path.add(direction);
                dirToFiles.put(direction, dirToFiles.getOrDefault(direction, new LinkedHashSet<>()));

                Set<String> parentDirChildren = dirToChildren.getOrDefault(parent, new LinkedHashSet<>());
                parentDirChildren.add(direction);
                dirToChildren.put(parent, parentDirChildren);
            }

            else if (line.startsWith("dir")) {
                String parent = path.get(path.size() - 1);
                Set<String> children = dirToChildren.getOrDefault(parent, new LinkedHashSet<>());
                children.add(line.substring(4));
                dirToChildren.put(parent, children);
            }

            else {
                Integer fileSize = Integer.parseInt(line.split(" ")[0]);
                String fileName = line.split(" ")[1];
                fileToSize.put(fileName, fileSize);

                String currentDir = path.get(path.size() - 1);
                Set<String> fileList = dirToFiles.getOrDefault(currentDir, new LinkedHashSet<>());
                fileList.add(fileName);
                dirToFiles.put(currentDir, fileList);
            }

        }

        System.out.println(path);

        int totalSize = 0;
        for (String dir : dirToFiles.keySet()) {
            int dirSize = getDirSize(dir, dirToFiles, dirToChildren, fileToSize);
            if (dirSize <= 100000)
                totalSize += dirSize;
        }

        System.out.println(dirToChildren);
        System.out.println(dirToFiles);

        return totalSize;
    }
    // 1006358; 787432, 1140440
    // 1140440, 191661, 982640, 716338 787432, 679307

    private static int part2(List<String> input) {
        return -1;
    }

    public static void main(String[] args) {
        ResourceLoader resourceLoader = new ResourceLoader();
        List<String> input = resourceLoader.load("day7.txt");
        System.out.println(part1(input));
    }

}