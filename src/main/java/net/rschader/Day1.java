package net.rschader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.rschader.utils.ResourceLoader;

public class Day1 {

    private static List<Integer> part1(List<String> inputList) {
        List<Integer> result = new ArrayList<>();
        Integer acc = 0;
        for (String line : inputList) {
            if (!line.isEmpty()) acc += Integer.parseInt(line);
            else {
                result.add(acc);
                acc = 0;
            }
        }
        Collections.sort(result, Collections.reverseOrder());
        return result;
    }

    private static Integer part2(List<Integer> part1) {
        Integer result = 0;
        for (int i = 0; i < 3; i++) result += part1.get(i);
        return result;
    }

    public static void main(String[] args) {
        ResourceLoader resourceLoader = new ResourceLoader();
        
        List<String> input = resourceLoader.load("day1.txt");
        List<Integer> part1 = part1(input);
        
        Integer result1 = part1.get(0);
        Integer result2 = part2(part1);

        assert(result1 == 69693);
        assert(result2 == 200945);
    }

}