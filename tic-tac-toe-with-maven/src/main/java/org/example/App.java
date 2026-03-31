package org.example;

import java.util.*;

public class App {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.print("Input command: > ");
            String input = scanner.nextLine();
            if (input.equals("exit")) break;

            String[] parts = input.split("\\s+");
            if (parts.length != 3 || !parts[0].equals("start") || isValid(parts[1]) || isValid(parts[2])) {
                System.out.println("Bad parameters!");
                continue;
            }

            playGame(parts[1], parts[2]);
        }
    }

    public static boolean isValid(String s) {
        return !s.equals("user") && !s.equals("easy") && !s.equals("medium") && !s.equals("hard");
    }

    public static void playGame(String p1, String p2) {
        char[][] board = new char[3][3];
        for (char[] row : board) Arrays.fill(row, ' ');
        printBoard(board);

        String[] players = {p1, p2};
        char[] symbols = {'X', 'O'};
        int turn = 0;

        while (true) {
            char currentSymbol = symbols[turn % 2];
            String type = players[turn % 2];

            if (type.equals("user")) {
                userMove(board, currentSymbol);
            } else {
                System.out.println("Making move level \"" + type + "\"");
                switch (type) {
                    case "easy":
                        easyMove(board, currentSymbol);
                        break;
                    case "medium":
                        mediumMove(board, currentSymbol);
                        break;
                    case "hard":
                        hardMove(board, currentSymbol);
                        break;
                }
            }



            printBoard(board);
            String status = checkStatus(board);
            if (!status.equals("Game not finished")) {
                System.out.println(status);
                break;
            }
            turn++;
        }
    }


    public static void easyMove(char[][] board, char symbol) {
        Random random = new Random();
        while (true) {
            int r = random.nextInt(3), c = random.nextInt(3);
            if (board[r][c] == ' ') {
                board[r][c] = symbol;
                break;
            }
        }
    }

    public static void mediumMove(char[][] board, char symbol) {
        char opponent = (symbol == 'X') ? 'O' : 'X';
        if (tryComplete(board, symbol, symbol)) return;
        if (tryComplete(board, opponent, symbol)) return;
        easyMove(board, symbol);
    }

    public static void hardMove(char[][] board, char symbol) {
        int[] result = minimax(board, symbol, symbol);
        board[result[1]][result[2]] = symbol;
    }


    public static int[] minimax(char[][] board, char current, char ai) {
        char opponent = (ai == 'X') ? 'O' : 'X';
        String res = checkStatus(board);

        if (res.contains(ai + " wins")) return new int[]{10, -1, -1};
        if (res.contains(opponent + " wins")) return new int[]{-10, -1, -1};
        if (res.equals("Draw")) return new int[]{0, -1, -1};

        List<int[]> moves = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = current;
                    int score = minimax(board, (current == 'X' ? 'O' : 'X'), ai)[0];
                    moves.add(new int[]{score, i, j});
                    board[i][j] = ' ';
                }
            }
        }

        int best = 0;
        if (current == ai) {
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i)[0] > max) { max = moves.get(i)[0]; best = i; }
            }
        } else {
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i)[0] < min) { min = moves.get(i)[0]; best = i; }
            }
        }
        return moves.get(best);
    }


    public static boolean tryComplete(char[][] b, char target, char symbol) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (b[i][j] == ' ') {
                    b[i][j] = target;
                    if (checkStatus(b).contains(target + " wins")) {
                        b[i][j] = symbol;
                        return true;
                    }
                    b[i][j] = ' ';
                }
            }
        }
        return false;
    }

    public static void userMove(char[][] board, char symbol) {
        while (true) {
            System.out.print("Enter the coordinates: > ");
            String input = scanner.nextLine();
            try {
                String[] parts = input.split("\\s+");
                int r = Integer.parseInt(parts[0]) - 1;
                int c = Integer.parseInt(parts[1]) - 1;
                if (r < 0 || r > 2 || c < 0 || c > 2) System.out.println("Coordinates should be from 1 to 3!");
                else if (board[r][c] != ' ') System.out.println("This cell is occupied! Choose another one!");
                else { board[r][c] = symbol; break; }
            } catch (Exception e) { System.out.println("You should enter numbers!"); }
        }
    }

    public static String checkStatus(char[][] b) {
        for (int i = 0; i < 3; i++) {
            if (b[i][0] != ' ' && b[i][0] == b[i][1] && b[i][1] == b[i][2]) return b[i][0] + " wins";
            if (b[0][i] != ' ' && b[0][i] == b[1][i] && b[1][i] == b[2][i]) return b[0][i] + " wins";
        }
        if (b[1][1] != ' ' && ((b[0][0] == b[1][1] && b[1][1] == b[2][2]) || (b[0][2] == b[1][1] && b[1][1] == b[2][0])))
            return b[1][1] + " wins";
        for (char[] row : b) for (char c : row) if (c == ' ') return "Game not finished";
        return "Draw";
    }

    public static void printBoard(char[][] b) {
        System.out.println("---------");
        for (char[] row : b) {
            System.out.print("| ");
            for (char c : row) System.out.print(c + " ");
            System.out.println("|");
        }
        System.out.println("---------");
    }
}