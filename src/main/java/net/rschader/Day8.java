package net.rschader;

import java.util.List;

import net.rschader.utils.ResourceLoader;

public class Day8 {

    private static int[][] createGrid(List<String> input) {
        int n = input.size();
        int m = input.get(0).length();

        int[][] grid = new int[n][m];
        int row = 0;
        for (String line : input) {
            for (int col = 0; col < line.length(); col++) {
                grid[row][col] = Character.getNumericValue(line.charAt(col));
            }
            row++;
        }
        return grid;
    }

    private static int countVisible(int[][] grid) {
        int count = 0;
        int n = grid.length;
        for (int i = 1; i < n - 1; i++) {
            int m = grid[i].length;
            for (int j = 1; j < m - 1; j++) {
                int tree = grid[i][j];

                int left = Integer.MIN_VALUE;
                for (int col = j - 1; col >= 0; col--)
                    left = Math.max(left, grid[i][col]);

                int right = Integer.MIN_VALUE;
                for (int col = j + 1; col < m; col++)
                    right = Math.max(right, grid[i][col]);

                int top = Integer.MIN_VALUE;
                for (int row = i - 1; row >= 0; row--)
                    top = Math.max(top, grid[row][j]);

                int bottom = Integer.MIN_VALUE;
                for (int row = i + 1; row < n; row++)
                    bottom = Math.max(bottom, grid[row][j]);

                if (left < tree || right < tree || top < tree || bottom < tree)
                    count++;
            }
        }

        return count;
    }

    private static int calculateStore(int[][] grid) {
        int max = Integer.MIN_VALUE;
        int n = grid.length;

        for (int i = 0; i < n; i++) {
            int m = grid[i].length;
            for (int j = 0; j < m; j++) {
                int tree = grid[i][j];

                int left = 0;
                for (int col = j - 1; col >= 0; col--) {
                    if (grid[i][col] >= tree) {
                        left++;
                        break;
                    }
                    if (j == 0)
                        break;
                    else
                        left++;
                }

                int right = 0;
                for (int col = j + 1; col < m; col++) {
                    if (grid[i][col] >= tree) {
                        right++;
                        break;
                    }
                    if (j == m - 1)
                        break;
                    else
                        right++;
                }

                int top = 0;
                for (int row = i - 1; row >= 0; row--) {
                    if (grid[row][j] >= tree) {
                        top++;
                        break;
                    }
                    if (i == 0)
                        break;
                    else
                        top++;
                }

                int bottom = 0;
                for (int row = i + 1; row < n; row++) {
                    if (i == n - 1)
                        break;
                    if (grid[row][j] >= tree) {
                        bottom++;
                        break;
                    } else
                        bottom++;
                }

                int score = left * right * top * bottom;
                max = Math.max(max, score);
            }
        }

        return max;
    }

    private static int part1(List<String> input) {
        int[][] grid = createGrid(input);
        int n = grid.length;
        int m = grid[0].length;
        int count = 4 + n - 2 + n - 2 + m - 2 + m - 2;
        return count + countVisible(grid);
    }

    private static int part2(List<String> input) {
        int[][] grid = createGrid(input);
        return calculateStore(grid);
    }

    public static void main(String[] args) {
        ResourceLoader resourceLoader = new ResourceLoader();
        List<String> input = resourceLoader.load("day8.txt");
        System.out.println(part1(input)); // 1798
        System.out.println(part2(input)); //259308
    }

}