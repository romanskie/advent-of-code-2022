package net.rschader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.rschader.utils.ResourceLoader;

public class Day11 {

    private static class Monkey {
        private long itemCount;
        private List<Long> items;

        public Monkey(long itemCount, List<Long> items) {
            this.itemCount = itemCount;
            this.items = items;
        }

        public long getItemCount() {
            return this.itemCount;
        }

        public void setItemCount(long itemCount) {
            this.itemCount = itemCount;
        }

        public List<Long> getItems() {
            return this.items;
        }

        public void setItems(List<Long> items) {
            this.items = items;
        }

    }

    private static long applyOperation(String line, long item, long divisor, Long mod) {
        String operation = line.substring(19, line.length());
        String[] split = operation.split(" ");
        long left = split[0].equals("old") ? item : Long.parseLong(split[0]);
        long right = split[2].equals("old") ? item : Long.parseLong(split[2]);
        if (split[1].equals("+"))
            return (mod != null) ? (left + right) % mod : Math.round((left + right) / divisor);
        else
            return (mod != null) ? (left * right) % mod : Math.round((left * right) / divisor);
    }

    private static boolean testCondition(String line, long item) {
        String[] split = line.split(" ");
        long n = Long.parseLong(split[split.length - 1]);
        return (item % n) == 0;
    }

    private static List<Monkey> initialParse(List<String> input) {
        List<Monkey> monkeyState = new ArrayList<>();
        for (String line : input) {
            if (line.startsWith("Monkey")) {
                monkeyState.add(new Monkey(0, new ArrayList<>()));
            } else if (line.contains("Starting")) {
                Monkey last = monkeyState.get(monkeyState.size() - 1);
                List<Long> items = last.getItems();
                for (String s : line.substring(18).split(", ")) {
                    items.add(Long.parseLong(s));
                }
                last.setItems(items);
                monkeyState.set(monkeyState.size() - 1, last);
            }
        }

        return monkeyState;
    }

    private static List<Monkey> play(List<String> input, List<Monkey> monkeyState,
            int from, long divisor, Long mod) {

        int monkeyId = Integer.parseInt(input.get(from).substring(7, input.get(from).length() - 1));
        Monkey curr = monkeyState.get(monkeyId);
        List<Long> items = curr.getItems();

        curr.setItemCount(curr.getItemCount() + items.size());
        curr.setItems(new ArrayList<>());
        monkeyState.set(monkeyId, curr);

        List<Long> itemsToMove = new ArrayList<>();
        for (long item : items) {
            long modified = applyOperation(input.get(from + 2), item, divisor, mod);
            itemsToMove.add(modified);
        }

        int trueMonkeyIndex = Character.getNumericValue(input.get(from + 4).charAt(input.get(from + 4).length() - 1));
        int falseMonkeyIndex = Character.getNumericValue(input.get(from + 5).charAt(input.get(from + 5).length() - 1));

        for (long itemToMove : itemsToMove) {
            int receiverIndex = testCondition(input.get(from + 3), itemToMove) ? trueMonkeyIndex : falseMonkeyIndex;
            Monkey receiver = monkeyState.get(receiverIndex);
            List<Long> receiverItems = receiver.getItems();
            receiverItems.add(itemToMove);
            receiver.setItems(receiverItems);
            monkeyState.set(receiverIndex, receiver);
        }

        return monkeyState;
    }

    private static long part1(List<String> input, int rounds, long divisor) {
        List<Monkey> monkeyState = initialParse(input);

        while (0 < rounds--) {
            for (int i = 0; i < input.size(); i += 7) {
                monkeyState = play(input, monkeyState, i, divisor, null);
            }
        }

        List<Long> result = new ArrayList<>();
        for (Monkey m : monkeyState) {
            result.add(m.getItemCount());
        }

        Collections.sort(result);
        return result.get(result.size() - 2) * result.get(result.size() - 1);
    }

    private static long part2(List<String> input, int rounds, long divisor, long mod) {
        List<Monkey> monkeyState = initialParse(input);

        while (0 < rounds--) {
            for (int i = 0; i < input.size(); i += 7)
                monkeyState = play(input, monkeyState, i, divisor, mod);
        }

        List<Long> result = new ArrayList<>();
        for (Monkey m : monkeyState) {
            result.add(m.getItemCount());
        }

        Collections.sort(result);
        return result.get(result.size() - 2) * result.get(result.size() - 1);
    }

    public static void main(String[] args) {
        ResourceLoader resourceLoader = new ResourceLoader();
        List<String> input = resourceLoader.load("day11.txt");
        long mod = 1;
        for (String line: input) {
            if (line.contains("divisible")) {
                String[] split = line.split(" ");
                mod = mod * Long.parseLong(split[split.length - 1]);
            }
        }

        System.out.println(part1(input, 20, 3));
        System.out.println(part2(input, 10000, 1, mod));
    }

}