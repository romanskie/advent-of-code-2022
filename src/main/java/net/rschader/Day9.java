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
        if (isAdjacent(head, tail))
            return tail;

        Point update = tail.getLocation();
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

    private static int part1(List<String> input) {
        Point head = new Point(0, 0);
        Point tail = new Point(0, 0);

        Set<Point> positions = new HashSet<>();
        positions.add(tail);

        for (String line : input) {
            String direction = line.split(" ")[0];
            int steps = Integer.valueOf(line.split(" ")[1]);
            for (int i = 0; i < steps; i++) {
                head = changeDirection(direction, head);
                tail = followUp(head, tail);
                positions.add(tail);
            }
        }

        return positions.size();
    }

    private static int part2(List<String> input) {
        Point start = new Point(0, 0);
        Point head = new Point(0, 0);

        List<Point> snake = new ArrayList<>();
        snake.add(start);

        Set<Point> tailPositions = new LinkedHashSet<>();
        tailPositions.add(start);

        for (String line : input) {
            String direction = line.split(" ")[0];
            int steps = Integer.valueOf(line.split(" ")[1]);
            while (steps-- > 0) {
                head = changeDirection(direction, head);
                Point prev = head;
                List<Point> snakeUpdate = new ArrayList<>();
                for (Point current : snake) {
                    Point followUpPoint = followUp(prev, current);
                    snakeUpdate.add(followUpPoint);
                    Point currentTail = snakeUpdate.get(snakeUpdate.size() - 1);
                    if (snakeUpdate.size() < 9 && !currentTail.equals(start) && current.equals(start)) snakeUpdate.add(current);
                    prev = followUpPoint;
                }
                snake = snakeUpdate;
                Point tail = snakeUpdate.get(snakeUpdate.size() - 1);
                tailPositions.add(tail);
            }
        }

        return tailPositions.size();
    }

    public static void main(String[] args) {
        ResourceLoader resourceLoader = new ResourceLoader();
        List<String> input = resourceLoader.load("day9.txt");
        // System.out.println(part1(first)); // 6284
        System.out.println(part2(input)); // 1415, 1485, 1847, 1635, 530

    }

}