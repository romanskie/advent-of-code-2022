package net.rschader;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.rschader.utils.ResourceLoader;

public class Day6 {

    private static boolean unique(String s) {
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            set.add(s.charAt(i));
        }
        return s.length() == set.size();
    }

    private static int part1(List<String> input) {
        for (String line : input) {
            for (int i = 0; i < line.length(); i++) {
                String sub = line.substring(i, i + 4);
                if (unique(sub))
                    return i + 4;
            }
        }
        return -1;
    }

    private static int part2(List<String> input) {
        for (String line : input) {
            for (int i = 0; i < line.length(); i++) {
                String sub = line.substring(i, i + 14);
                if (unique(sub))
                    return i + 14;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        ResourceLoader resourceLoader = new ResourceLoader();
        List<String> input = resourceLoader.load("day6.txt");
        System.out.println(part1(input)); // 1538
        System.out.println(part2(input)); // 2315
    }

}