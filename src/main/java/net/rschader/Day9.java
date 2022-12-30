package net.rschader;

import java.awt.Point;
import java.util.*;

import net.rschader.utils.ResourceLoader;

public class Day9 {

    private static boolean isAdjacent(Point head, Point tail) {
        boolean x = Math.abs(head.x - tail.x) <= 1;
        boolean y = Math.abs(head.y - tail.y) <= 1;
        return x && y;
    }

    private static Point changeDirection(String direction, Point head) {
        Point update = head.getLocation();
        if (direction.equals("R"))
            update.setLocation(head.getX() + 1, head.getY());
        else if (direction.equals("L"))
            update.setLocation(head.getX() - 1, head.getY());
        else if (direction.equals("U"))
            update.setLocation(head.getX(), head.getY() + 1);
        else
            update.setLocation(head.getX(), head.getY() - 1);
        return update;
    }

    private static Point followUp(Point head, Point tail) {
        Point update = head.getLocation();
        if (tail.x > head.x)
            update.x = tail.x - 1;
        else if (tail.x < head.x)
            update.x = tail.x + 1;

        if (tail.y > head.y)
            update.y = tail.y - 1;
        else if (tail.y < head.y)
            update.y = tail.y + 1;

        return update;
    }

    private static int trackTailPositions(List<String> input, int ropeLength) {
        Set<Point> positions = new HashSet<>();
        List<Point> rope = new ArrayList<>();
        for (int i = 0; i < ropeLength; i++) {
            rope.add(new Point(0, 0));
        }
        for (String line : input) {
            String direction = line.split(" ")[0];
            int steps = Integer.valueOf(line.split(" ")[1]);
            while (steps-- > 0) {
                rope.set(0, changeDirection(direction, rope.get(0)));
                for (int i = 1; i < rope.size(); i++) {
                    Point head = rope.get(i - 1);
                    Point tail = rope.get(i);
                    if (!isAdjacent(head, tail))
                        rope.set(i, followUp(head, tail));
                }
                positions.add(rope.get(rope.size() - 1));
            }
        }
        return positions.size();
    }

    private static int part1(List<String> input, int n) {
        return trackTailPositions(input, n);
    }

    private static int part2(List<String> input, int n) {
        return trackTailPositions(input, n);
    }

    public static void main(String[] args) {
        ResourceLoader resourceLoader = new ResourceLoader();
        List<String> input = resourceLoader.load("day9.txt");
        System.out.println(part1(input, 2)); // 6284
        System.out.println(part2(input, 10)); // 2661

    }

}