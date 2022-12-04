package net.rschader;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.rschader.utils.ResourceLoader;

public class Day2 {

    private static final Map<String, String> rules = Map.of(
            "A", "C",
            "B", "A",
            "C", "B");

    private static final Map<String, String> shapeMapping = Map.of(
            "X", "A",
            "Y", "B",
            "Z", "C");

    private static final Map<String, Integer> shapePoints = Map.of(
            "A", 1,
            "B", 2,
            "C", 3);

    private static Integer getShapePoints(String shape) {
        if (shape.equals("A"))
            return 1;
        else if (shape.equals("B"))
            return 2;
        else
            return 3;
    }

    private static Integer getPointsPerRound(String left, String right) {
        int result = shapePoints.get(right);

        if (right.equals(left))
            result += 3;
        else if (right.equals("A") && left.equals("C"))
            result += 6;
        else if (right.equals("B") && left.equals("A"))
            result += 6;
        else if (right.equals("C") && left.equals("B"))
            result += 6;

        return result;
    }

    private static int part1(List<String> inputList) {
        int points = 0;
        for (String line : inputList) {
            String[] element = line.split(" ");
            String left = element[0];
            String right = shapeMapping.get(element[1]);
            Integer p = getPointsPerRound(left, right);
            points += p;
        }
        return points;
    }

    private static int part2(List<String> inputList) {
        int points = 0;
        for (String line : inputList) {
            String[] element = line.split(" ");
            String left = element[0];
            String right = element[1];

            if (right.equals("X")) {
                String loosingShape = rules.get(left);
                Integer shapePoints = Day2.shapePoints.get(loosingShape);
                points += shapePoints;
            } else if (right.equals("Y")) {
                Integer shapePoints = getShapePoints(left);
                points += shapePoints + 3;
            } else {
                for (Entry<String, String> entry : rules.entrySet()) {
                    if (entry.getValue().equals(left)) {
                        Integer shapePoints = getShapePoints(entry.getKey());
                        points += shapePoints + 6;
                    }
                }
            }

        }
        return points;
    }

    public static void main(String[] args) {
        ResourceLoader resourceLoader = new ResourceLoader();
        List<String> input = resourceLoader.load("day2.txt");
        int part1 = part1(input);
        assert (part1 == 15422);

        int part2 = part2(input);
        assert (part2 == 15442);
    }

}