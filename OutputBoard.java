/*
 * Group Assignment 1
 * Elle Pavenko
 */

package assignment;

/**
 * Represents the output board for a game, displaying hints based on the proximity of mines.
 * The output board is generated from an input board and provides visual hints to the player,
 * indicating the number of mines adjacent to each cell or marking the cell as a mine.
 * @author Elle Pavenko
 * @version 1.0
 */
public class OutputBoard {
    /**
     * A 2D array holding the hint characters for each cell of the board.
     * Each cell contains either a '*' indicating a mine,
     * or a digit representing the number of adjacent mines.
     */
    private final char[][] myHints;

    /**
     * Constructs an OutputBoard with provided hint data.
     *
     * @param theHints A 2D array of characters representing
     *                 the hints for each cell of the board.
     */
    public OutputBoard(final char[][] theHints) {
        myHints = theHints;
    }

    /**
     * Creates an OutputBoard from an InputBoard, calculating hints
     * based on the positions of mines.
     * For each cell, if it contains a mine, a '*' is placed.
     * Otherwise, the cell is filled with a digit
     * representing the count of mines in adjacent cells.
     *
     * @param theBoard The InputBoard from which to generate the OutputBoard.
     * @return An OutputBoard instance containing hints based on the provided InputBoard.
     */
    public static OutputBoard createFromInputBoard(final InputBoard theBoard) {
        final char[][] hints = new char[theBoard.getLines()][];

        for (int i = 0; i < theBoard.getLines(); i++) {
            hints[i] = new char[theBoard.getColumns()];

            for (int j = 0; j < theBoard.getColumns(); j++) {
                if (theBoard.isMine(i, j)) {
                    hints[i][j] = '*';
                } else {
                    final int minesAround = theBoard.calculateMineCountAroundCell(i, j);
                    hints[i][j] = (char) ('0' + minesAround);
                }

            }
        }

        return new OutputBoard(hints);
    }

    /**
     * Generates a string representation of the OutputBoard.
     *
     * @return A string representation of the OutputBoard.
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < myHints.length; i++) {
            final char[] line = myHints[i];

            for (int j = 0; j < line.length; j++) {
                sb.append(line[j]);
            }
            sb.append('\n');
        }

        return sb.toString();
    }
}
