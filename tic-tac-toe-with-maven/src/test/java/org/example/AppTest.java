package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    @Test
    void testIsValid() {
        assertFalse(App.isValid("user"));
        assertFalse(App.isValid("easy"));
        assertFalse(App.isValid("medium"));
        assertFalse(App.isValid("hard"));
        assertTrue(App.isValid("invalid"));
    }

    @Test
    void testEasyMovePlacesSymbol() {
        char[][] board = new char[3][3];
        fillBoard(board);

        App.easyMove(board, 'X');

        assertEquals(1, countSymbol(board, 'X'));
    }

    @Test
    void testMediumMoveWinningMove() {
        char[][] board = {
                {'X', 'X', ' '},
                {'O', ' ', ' '},
                {' ', ' ', ' '}
        };

        App.mediumMove(board, 'X');

        assertEquals('X', board[0][2]);
    }

    @Test
    void testMediumMoveBlockingMove() {
        char[][] board = {
                {'O', 'O', ' '},
                {'X', ' ', ' '},
                {' ', ' ', ' '}
        };

        App.mediumMove(board, 'X');

        assertEquals('X', board[0][2]);
    }

    @Test
    void testHardMoveFindsWinningMove() {
        char[][] board = {
                {'X', 'X', ' '},
                {'O', 'O', ' '},
                {' ', ' ', ' '}
        };

        App.hardMove(board, 'X');

        assertEquals('X', board[0][2]);
    }

    @Test
    void testCheckStatusRowWin() {
        char[][] board = {
                {'X', 'X', 'X'},
                {'O', ' ', ' '},
                {' ', ' ', ' '}
        };

        assertEquals("X wins", App.checkStatus(board));
    }

    @Test
    void testCheckStatusColumnWin() {
        char[][] board = {
                {'O', 'X', ' '},
                {'O', 'X', ' '},
                {'O', ' ', ' '}
        };

        assertEquals("O wins", App.checkStatus(board));
    }

    @Test
    void testCheckStatusDiagonalWin() {
        char[][] board = {
                {'X', 'O', ' '},
                {' ', 'X', 'O'},
                {' ', ' ', 'X'}
        };

        assertEquals("X wins", App.checkStatus(board));
    }

    @Test
    void testCheckStatusDraw() {
        char[][] board = {
                {'X', 'O', 'X'},
                {'X', 'O', 'O'},
                {'O', 'X', 'X'}
        };

        assertEquals("Draw", App.checkStatus(board));
    }


    @Test
    void testPrintBoard() {
        char[][] board = {
                {'X', 'O', 'X'},
                {' ', 'X', ' '},
                {'O', ' ', 'O'}
        };

        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));

        App.printBoard(board);

        String output = out.toString();

        assertTrue(output.contains("---------"));
        assertTrue(output.contains("| X O X |"));
        assertTrue(output.contains("|   X   |"));
        assertTrue(output.contains("| O   O |"));
    }

    @Test
    void testMainBadParameters() {
        String input = "start user wrong\nexit\n";

        java.io.ByteArrayInputStream in =
                new java.io.ByteArrayInputStream(input.getBytes());
        java.io.ByteArrayOutputStream out =
                new java.io.ByteArrayOutputStream();

        System.setIn(in);
        System.setOut(new java.io.PrintStream(out));

        App.scanner = new java.util.Scanner(System.in); // ⭐ IMPORTANT

        App.main(new String[]{});

        String output = out.toString();
        assertTrue(output.contains("Bad parameters!"));
    }

    @Test
    void testMainStartEasyExit() {
        String input = "start easy easy\nexit\n";

        java.io.ByteArrayInputStream in =
                new java.io.ByteArrayInputStream(input.getBytes());
        java.io.ByteArrayOutputStream out =
                new java.io.ByteArrayOutputStream();

        System.setIn(in);
        System.setOut(new java.io.PrintStream(out));

        App.scanner = new java.util.Scanner(System.in); // ⭐ IMPORTANT

        App.main(new String[]{});

        String output = out.toString();
        assertTrue(output.contains("Making move level"));
    }




    private void fillBoard(char[][] board) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = ' ';
    }

    private int countSymbol(char[][] board, char symbol) {
        int count = 0;
        for (char[] row : board)
            for (char c : row)
                if (c == symbol) count++;
        return count;
    }
}