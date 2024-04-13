/*
 * Group Assignment 1
 * Elle Pavenko
 */

package assignment;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Represents an input board, holding information about each cell's state.
 * This class allows reading a game board from input, checking cell states
 * and calculating mines around cells.
 * @author Elle Pavenko
 * @version 1.0
 */
public class InputBoard {
    /** The number of lines (rows) in the board. */
    private final int myLines;
    /** The number of columns in the board. */
    private final int myColumns;
    /** The data representing the state of each cell in the board. */
    private final BoardCell[][] myData;

    /**
     * Constructs an InputBoard with specified dimensions and cell states.
     *
     * @param theLines the number of lines (rows) of the board.
     * @param theColumns the number of columns of the board.
     * @param theData the data representing the state of each cell in the board.
     */
    public InputBoard(final int theLines, final int theColumns, final BoardCell[][] theData) {
        myLines = theLines;
        myColumns = theColumns;
        myData = theData;
    }

    /**
     * Returns the number of lines in the board.
     *
     * @return the number of lines.
     */
    public int getLines() {
        return myLines;
    }

    /**
     * Returns the number of columns in the board.
     *
     * @return the number of columns.
     */
    public int getColumns() {
        return myColumns;
    }

    /**
     * Converts a character to a corresponding {@link BoardCell} value.
     *
     * @param theChar the character to convert.
     * @return the corresponding {@link BoardCell} value.
     * @throws IllegalStateException if the character is not valid.
     */
    private static BoardCell convertCharToBoardCell(final char theChar)
            throws IllegalStateException {
        return switch (theChar) {
            case '.' -> BoardCell.Empty;
            case '*' -> BoardCell.Mine;

            default -> throw new IllegalStateException("Unexpected value: " + theChar);
        };
    }

    /**
     * Reads board data from a {@link Scanner} input,
     * creating an {@link InputBoard} instance from it.
     *
     * @param theScanner the {@link Scanner} to read the board data from.
     * @return an {@link InputBoard} instance representing the read board,
     * or null if the board is empty.
     */
    public static InputBoard readBoard(final Scanner theScanner) {
        try {
            final int lines = theScanner.nextInt();
            final int columns = theScanner.nextInt();
            theScanner.nextLine();

            if (lines == 0 || columns == 0) {
                return null;
            }

            final BoardCell[][] data = new BoardCell[lines][];

            for (int i = 0; i < lines; i++) {
                data[i] = new BoardCell[columns];

                final String line = theScanner.nextLine();

                for (int c = 0; c < columns; c++) {
                    data[i][c] = convertCharToBoardCell(line.charAt(c));
                }
            }

            return new InputBoard(lines, columns, data);
        } catch (final NoSuchElementException | IllegalStateException e) {
            return null;
        }
    }

    /**
     * Checks if a cell at specified coordinates is a mine.
     *
     * @param theLine the line index of the cell.
     * @param theColumn the column index of the cell.
     * @return true if the cell is a mine, false otherwise.
     */
    public boolean isMine(final int theLine, final int theColumn) {
        return myData[theLine][theColumn] == BoardCell.Mine;
    }

    /**
     * Calculates the number of mines around a specific cell.
     *
     * @param theLine the line index of the cell.
     * @param theColumn the column index of the cell.
     * @return the number of mines surrounding the cell.
     */
    public int calculateMineCountAroundCell(final int theLine, final int theColumn) {
        int total = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }

                final int l = theLine + i;
                final int c = theColumn + j;

                if (!checkBounds(l, c)) {
                    continue;
                }

                final BoardCell cell = myData[l][c];
                if (cell == BoardCell.Mine) {
                    total++;
                }
            }
        }

        return total;
    }

    /**
     * Checks if the specified coordinates are within the bounds of the board.
     *
     * @param theLine the line index to check.
     * @param theColumn the column index to check.
     * @return true if the coordinates are within bounds, false otherwise.
     */
    private boolean checkBounds(final int theLine, final int theColumn) {
        return theLine >= 0 && theLine < myLines && theColumn >= 0 && theColumn < myColumns;
    }
}
