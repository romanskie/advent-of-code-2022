package net.rschader;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.rschader.utils.ResourceLoader;

public class Day5 {
    private static class Move {
        private int amount;
        private int from;
        private int to;

        public Move(int amount, int from, int to) {
            this.amount = amount;
            this.from = from;
            this.to = to;
        }
    }

    private static Map<Integer, Deque<Character>> parseStacks(List<String> input) {
        Map<Integer, Deque<Character>> stacks = new HashMap<>();
        for (String line : input) {
            if (!line.isEmpty() && !line.matches("[0-9 ]+") && !line.startsWith("move")) {
                int key = 1;
                for (int i = 1; i < line.length(); i += 4) {
                    Deque<Character> stack = stacks.getOrDefault(key, new ArrayDeque<>());
                    Character character = line.charAt(i);
                    if (Character.isAlphabetic(character))
                        stack.addLast(character);
                    stacks.put(key, stack);
                    key++;
                }
            }
        }
        return stacks;
    }

    private static Move buildMove(String line) {
        line = line.replaceAll("\\D+", ",");
        String[] splitted = line.split(",");
        List<Integer> list = new ArrayList<>();
        for (String s : splitted) {
            if (!s.isEmpty())
                list.add(Integer.valueOf(s));
        }
        return new Move(list.get(0), list.get(1), list.get(2));
    }

    private static String part1(List<String> input) {
        Map<Integer, Deque<Character>> stacks = parseStacks(input);
        for (String line : input) {
            if (line.startsWith("move")) {
                Move move = buildMove(line);

                Deque<Character> from = stacks.get(move.from);
                Deque<Character> to = stacks.get(move.to);

                for (int i = 0; i < move.amount; i++) {
                    to.addFirst(from.removeFirst());
                }

                stacks.put(move.from, from);
                stacks.put(move.to, to);
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= stacks.size(); i++) {
            sb.append(stacks.get(i).peekFirst());
        }

        return sb.toString();
    }

    private static String part2(List<String> input) {
        Map<Integer, Deque<Character>> stacks = parseStacks(input);
        for (String line : input) {
            if (line.startsWith("move")) {
                Move move = buildMove(line);

                Deque<Character> from = stacks.get(move.from);
                Deque<Character> to = stacks.get(move.to);

                List<Character> temp = new ArrayList<>();
                for (int i = 0; i < move.amount; i++)
                    temp.add(0, from.removeFirst());

                for (Character c : temp) {
                    to.addFirst(c);
                }

                stacks.put(move.from, from);
                stacks.put(move.to, to);
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= stacks.size(); i++) {
            sb.append(stacks.get(i).peekFirst());
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        ResourceLoader resourceLoader = new ResourceLoader();
        List<String> input = resourceLoader.load("day5.txt");
        System.out.println(part1(input)); // JRVNHHCSJ
        System.out.println(part2(input)); // GNFBSBJLH
    }

}