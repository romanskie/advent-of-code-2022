package net.rschader;

import java.util.*;

import net.rschader.utils.ResourceLoader;

public class Day10 {

    private static int part1(List<String> input) {
        Map<Integer, Integer> map = new HashMap<>();
        int cycles = 0;
        int x = 1;
        for (String line : input) {
            if (line.startsWith("noop")) {
                map.put(++cycles, x);
            } else {
                map.put(++cycles, x);
                map.put(++cycles, x);
                x += Integer.parseInt(line.split(" ")[1]);
            }
        }

        int sum = 0;
        for (int i = 20; i < 221; i += 40) {
            sum += (map.get(i) * i);
        }

        return sum;
    }

    private static boolean inSpriteRange(int crtPos, int x) {
        if ((x - 1) == crtPos) return true;
        else if (x == crtPos) return true;
        else if ((x + 1) == crtPos) return true;
        else return false;
    }

    private static void part2(List<String> input) {
        int n = 6; 
        int m = 40;
        char[][] screen = new char[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                screen[i][j] = '.';
            }
        }

        int crtRow = 0;
        int crtPos = 0;
        int x = 1;
        for (String line : input) {
            if (line.startsWith("noop")) {
                if (inSpriteRange(crtPos, x)) screen[crtRow][crtPos] = '#';
                crtPos++;
                if (crtPos == m) {
                    crtPos = 0;
                    crtRow++;
                }

            } else {
                if (inSpriteRange(crtPos, x)) screen[crtRow][crtPos] = '#';
                crtPos++;
                if (crtPos == m) {
                    crtPos = 0;
                    crtRow++;
                }
                if (inSpriteRange(crtPos, x)) screen[crtRow][crtPos] = '#';
                crtPos++;
                if (crtPos == m) {
                    crtPos = 0;
                    crtRow++;
                }
                x += Integer.parseInt(line.split(" ")[1]);
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(screen[i][j]);
            }
            System.out.println();
        }

    }

    public static void main(String[] args) {
        ResourceLoader resourceLoader = new ResourceLoader();
        List<String> input = resourceLoader.load("day10.txt");
        System.out.println(part1(input));
        part2(input);
    }

}