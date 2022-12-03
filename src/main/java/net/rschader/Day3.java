package net.rschader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.rschader.utils.ResourceLoader;

public class Day3 {

    private static String alphabet = "abcdefghijklmnopqrstuvwxyz";

    private static Map<Character, Integer> priorities = createPrioritiesMap();

    private static Map<Character, Integer> createPrioritiesMap() {
        Map<Character, Integer> prio = new HashMap<>();
        for (int i = 0; i < alphabet.length(); i++) {
            prio.put(alphabet.charAt(i), i + 1);
        }
        return Collections.unmodifiableMap(prio);
    }

    private static String findCommonItems(String a, String b) {
        StringBuilder sb = new StringBuilder();

        Set<Character> set = new HashSet<>();
        for (int i = 0; i < a.length(); i++) {
            set.add(a.charAt(i));
        }

        for (int i = 0; i < b.length(); i++) {
            Character c = b.charAt(i);
            if (set.contains(c) && sb.toString().indexOf(c) == -1)
                sb.append(c);
        }

        return sb.toString();
    }

    private static Integer calculatePriorities(String s) {
        int points = 0;
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            if (Character.isLowerCase(c))
                points += priorities.get(c);
            else
                points += priorities.get(Character.toLowerCase(c)) + 26;
        }
        return points;

    }

    private static Integer calculatePrioritiesPerGroup(List<String> group) {
        Set<Character> set = new HashSet<>();
        String first = group.get(0);

        for (int i = 0; i < first.length(); i++) {
            set.add(group.get(0).charAt(i));
        }

        for (int i = 1; i < group.size(); i++) {
            Set<Character> s = new HashSet<>();
            String curr = group.get(i);
            for (int j = 0; j < curr.length(); j++) {
                s.add(curr.charAt(j));
            }
            set.retainAll(s);
        }

        StringBuilder sb = new StringBuilder();
        for (Character c : set) {
            sb.append(c);
        }

        return calculatePriorities(sb.toString());
    }

    private static Integer part1(List<String> input) {
        int points = 0;
        for (String line : input) {
            int n = line.length();
            String compartment1 = line.substring(0, n / 2);
            String compartment2 = line.substring(n / 2, n);
            String pairs = findCommonItems(compartment1, compartment2);
            points += calculatePriorities(pairs);
        }
        return points;
    }

    private static Integer part2(List<String> input) {
        int points = 0;

        List<List<String>> groups = new ArrayList<>();
        for (int i = 0; i < input.size(); i += 3) {
            groups.add(input.subList(i, i + 3));
        }

        for (List<String> group : groups) {
            points += calculatePrioritiesPerGroup(group);
        }

        return points;
    }

    public static void main(String[] args) {
        ResourceLoader resourceLoader = new ResourceLoader();

        List<String> input = resourceLoader.load("day3.txt");
        assert (part1(input) == 7863);
        assert (part2(input) == 2488);
    }

}