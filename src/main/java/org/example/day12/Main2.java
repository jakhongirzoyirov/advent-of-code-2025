package org.example.day12;

import java.io.*;
import java.util.*;

public class Main2 {

    static class Piece {
        List<List<char[]>> rotations;
        int count;

        Piece(List<List<char[]>> rotations, int count) {
            this.rotations = rotations;
            this.count = count;
        }
    }

    static class Coord {
        int r, c;
        Coord(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    static class InputData {
        Map<Integer, List<char[]>> shapeMap = new HashMap<>();
        Map<String, List<List<Integer>>> dimensionMap = new HashMap<>();
    }

    static InputData readData(String filePath) throws IOException {
        InputData data = new InputData();

        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;

        Integer currentShapeId = null;
        List<char[]> currentShape = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;

            if (Character.isDigit(line.charAt(0)) && line.endsWith(":")) {
                if (currentShapeId != null) {
                    data.shapeMap.put(currentShapeId, currentShape);
                    currentShape = new ArrayList<>();
                }
                currentShapeId = Integer.parseInt(line.substring(0, line.length() - 1));
            }
            else if (line.chars().allMatch(c -> c == '#' || c == '.')) {
                currentShape.add(line.toCharArray());
            }
            else if (line.contains(":")) {
                String[] parts = line.split(":");
                String dimension = parts[0].trim();
                String[] nums = parts[1].trim().split("\\s+");

                List<Integer> shapeIds = new ArrayList<>();
                for (String n : nums) shapeIds.add(Integer.parseInt(n));

                data.dimensionMap
                        .computeIfAbsent(dimension, k -> new ArrayList<>())
                        .add(shapeIds);
            }
        }

        if (currentShapeId != null && !currentShape.isEmpty()) {
            data.shapeMap.put(currentShapeId, currentShape);
        }

        return data;
    }

    static List<List<char[]>> getAllRotations(List<char[]> shape) {
        Set<String> seen = new HashSet<>();
        List<List<char[]>> result = new ArrayList<>();

        List<char[]> current = deepCopy(shape);

        for (int i = 0; i < 4; i++) {
            addIfUnique(current, seen, result);

            // horizontal flip
            List<char[]> flipped = new ArrayList<>();
            for (char[] row : current) {
                char[] r = row.clone();
                for (int l = 0, h = r.length - 1; l < h; l++, h--) {
                    char tmp = r[l]; r[l] = r[h]; r[h] = tmp;
                }
                flipped.add(r);
            }
            addIfUnique(flipped, seen, result);

            current = rotate90(current);
        }
        return result;
    }

    static void addIfUnique(List<char[]> shape, Set<String> seen, List<List<char[]>> out) {
        String key = canonical(shape);
        if (seen.add(key)) {
            out.add(deepCopy(shape));
        }
    }

    static String canonical(List<char[]> shape) {
        StringBuilder sb = new StringBuilder();
        for (char[] row : shape) {
            sb.append(row).append('|');
        }
        return sb.toString();
    }

    static List<char[]> rotate90(List<char[]> shape) {
        int h = shape.size();
        int w = shape.get(0).length;
        List<char[]> rotated = new ArrayList<>();

        for (int c = 0; c < w; c++) {
            char[] row = new char[h];
            for (int r = 0; r < h; r++) {
                row[r] = shape.get(h - 1 - r)[c];
            }
            rotated.add(row);
        }
        return rotated;
    }

    static List<char[]> deepCopy(List<char[]> shape) {
        List<char[]> copy = new ArrayList<>();
        for (char[] row : shape) copy.add(row.clone());
        return copy;
    }

    static List<Coord> getShapeCoords(List<char[]> shape) {
        List<Coord> coords = new ArrayList<>();
        for (int r = 0; r < shape.size(); r++) {
            for (int c = 0; c < shape.get(r).length; c++) {
                if (shape.get(r)[c] == '#') {
                    coords.add(new Coord(r, c));
                }
            }
        }
        return coords;
    }

    static boolean canPlace(char[][] grid, List<Coord> coords, int sr, int sc) {
        int h = grid.length, w = grid[0].length;

        for (Coord p : coords) {
            int r = sr + p.r;
            int c = sc + p.c;
            if (r < 0 || r >= h || c < 0 || c >= w) return false;
            if (grid[r][c] != '.') return false;
        }
        return true;
    }

    static void place(char[][] grid, List<Coord> coords, int sr, int sc) {
        for (Coord p : coords) {
            grid[sr + p.r][sc + p.c] = 'X';
        }
    }

    static void remove(char[][] grid, List<Coord> coords, int sr, int sc) {
        for (Coord p : coords) {
            grid[sr + p.r][sc + p.c] = '.';
        }
    }

    static boolean solve(char[][] grid, List<Piece> pieces) {

        boolean done = true;
        for (Piece p : pieces) if (p.count > 0) done = false;
        if (done) return true;

        int empty = 0;
        for (char[] row : grid)
            for (char c : row)
                if (c == '.') empty++;

        int required = 0;
        for (Piece p : pieces) {
            if (p.count > 0) {
                required += getShapeCoords(p.rotations.get(0)).size() * p.count;
            }
        }
        if (empty < required) return false;

        int er = -1, ec = -1;
        outer:
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == '.') {
                    er = r; ec = c;
                    break outer;
                }
            }
        }

        for (Piece p : pieces) {
            if (p.count == 0) continue;

            for (List<char[]> rot : p.rotations) {
                List<Coord> coords = getShapeCoords(rot);

                for (Coord anchor : coords) {
                    int sr = er - anchor.r;
                    int sc = ec - anchor.c;

                    if (canPlace(grid, coords, sr, sc)) {
                        place(grid, coords, sr, sc);
                        p.count--;

                        if (solve(grid, pieces)) return true;

                        p.count++;
                        remove(grid, coords, sr, sc);
                    }
                }
            }
        }

        grid[er][ec] = 'B';
        if (solve(grid, pieces)) return true;
        grid[er][ec] = '.';

        return false;
    }

    static int countValidRegions(
            Map<Integer, List<char[]>> shapeMap,
            Map<String, List<List<Integer>>> dimensionMap
    ) {

        Map<Integer, List<List<char[]>>> allRotations = new HashMap<>();
        for (var e : shapeMap.entrySet()) {
            allRotations.put(e.getKey(), getAllRotations(e.getValue()));
        }

        int valid = 0;

        for (var entry : dimensionMap.entrySet()) {
            String[] dim = entry.getKey().split("x");
            int width = Integer.parseInt(dim[0]);
            int height = Integer.parseInt(dim[1]);

            for (List<Integer> pieceList : entry.getValue()) {
                char[][] grid = new char[height][width];
                for (char[] row : grid) Arrays.fill(row, '.');

                List<Piece> pieces = new ArrayList<>();
                for (int i = 0; i < pieceList.size(); i++) {
                    int cnt = pieceList.get(i);
                    if (cnt > 0) {
                        pieces.add(new Piece(allRotations.get(i), cnt));
                    }
                }

                pieces.sort((a, b) ->
                        getShapeCoords(b.rotations.get(0)).size()
                                - getShapeCoords(a.rotations.get(0)).size()
                );

                if (solve(grid, pieces)) valid++;
            }
        }
        return valid;
    }

    public static void main(String[] args) {
        try {
            InputData data = readData("D:\\my_files\\projects\\AoC2025\\my-AoC\\src\\main\\java\\org\\example\\day12\\input.txt");

            int result = countValidRegions(data.shapeMap, data.dimensionMap);

            System.out.println("Number of valid regions: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}