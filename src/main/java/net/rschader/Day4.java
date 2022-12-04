package net.rschader;

import net.rschader.utils.ResourceLoader;

import java.util.List;

public class Day4 {

    private static final class Range {

        private final int from;
        private final int to;

        public Range(int from, int to) {
            this.from = from;
            this.to = to;
        }

        public boolean fullyContains(Range range) {
            return this.from <= range.from && this.to >= range.to;
        }
 
        public boolean overlapsWith(Range range) {
            if (this.to < range.from && this.from < range.to) return false;
            else if (this.to > range.from && this.from > range.to) return false;
            return true;
        }
    }

    private static Range getRange(String sections) {
        int from = Integer.parseInt(sections.split("-")[0]);
        int to = Integer.parseInt(sections.split("-")[1]);
        return new Range(from, to);
    }

    private static int part1(List<String> input) {
        int count = 0;
        for (String line : input) {
            String[] sections = line.split(",");
            Range range1 = getRange(sections[0]);
            Range range2 = getRange(sections[1]);
            if (range1.fullyContains(range2) || range2.fullyContains(range1))
                count++;
        }
        return count;
    }

    private static int part2(List<String> input) {
        int count = 0;
        for (String line : input) {
            String[] sections = line.split(",");
            Range range1 = getRange(sections[0]);
            Range range2 = getRange(sections[1]);
            if (range1.overlapsWith(range2))
                count++;
        }
        return count;
    }

    public static void main(String[] args) {
        ResourceLoader resourceLoader = new ResourceLoader();
        List<String> input = resourceLoader.load("day4.txt");
        assert (part1(input) == 431);
        assert (part2(input) == 823);
    }

}