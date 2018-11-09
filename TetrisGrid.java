/**
 *NAME: Nathan Kaslan, Guhan Sundar
 *LOGIN: nkaslan, gsundar
 *ID: A11994249, A11889992
 **/
import java.util.ArrayList;
/** A class that can contain TetrisShapes and keeps track of whether spaces are full or not
 */
public class TetrisGrid {
    private final int GRID_HEIGHT = 20;
    private final int GRID_WIDTH = 10;
    private final int SHAPE_LENGTH = 4;
    private ArrayList<TetrisShape> shapeTrack = new ArrayList<TetrisShape>();
    private boolean[][] grid;
    /** creates a TetrisGrid, an array of booleans that can have occupied coordinates ("true") or empty ("false")
     */
    public TetrisGrid() {
        grid = new boolean[GRID_HEIGHT][GRID_WIDTH];
        for (int i = 0; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
                grid[i][j] = false;
            }
        }
    }
    /** adds a shape to the current tetris grid by occupying the spaces, checks to see if the shape is added or not
     *@param shape a Tetris shape that wants to be added
     *@param offsetX the offset at which the shape will be added on the grid
     *@param offsetY the offset at which the shape will be added on the grid
     *@return added a boolean that will say if it was added or not
     */
    public synchronized boolean addShape(TetrisShape shape, int offsetX, int offsetY) {
        boolean added = true;
        Coord[] shapeArray = shape.getCoord(offsetX, offsetY);
        if (isOffGrid(shapeArray) || isOccupied(shapeArray)) {
            added = false;
        }
        else {
            shapeTrack.add(shape);
            for (int i = 0; i < shapeArray.length; ++i) {
                int r = (shapeArray[i]).row;
                int c = (shapeArray[i]).col;
                grid[r][c] = true;
            }
      		}
      		return added;
    }
    /** helper to see if added shape is off the grid
     *@param shapeArray an array of coords that are check to see if they are off the grid
     *@return off a boolean that says whether the shape is off the grid or not
     */
    public boolean isOffGrid(Coord[] shapeArray) {
        boolean off = false;
        for (int i = 0; i < SHAPE_LENGTH; ++i) {
            if((shapeArray[i].row >= GRID_HEIGHT) || (shapeArray[i].col <  0) || (shapeArray[i].col  >= GRID_WIDTH) || (shapeArray[i].row < 0)) {
                off = true;
            }
        }
        return off;
    }
    /** helper to see if a shape is placed where there already exists a shape
     *@param shapeArray an array of coords that are check to see if they are in an occupied space
     *@return occupied a boolean that says whether the current spaces are occupied
     */
    private boolean isOccupied(Coord[] shapeArray) {
        boolean occupied = false;
        for (int i = 0; i < SHAPE_LENGTH; i++) {
            int r = (shapeArray[i]).row;
            int c = (shapeArray[i]).col;
            if (grid[r][c]) {
                occupied = true;
            }
        }
        return occupied;
    }
    /** A method that removes a shape from the grid by saying the spaces are no longer occupied
     *@param shape the shape that is to be removed
     *@return boolean removed says whether the shape was successfully removed
     */
    public synchronized boolean removeShape(TetrisShape shape) {
        boolean removed = true;
        Coord[] shapeArray = shape.getCoord();
        if (isOffGrid(shapeArray)) {
            removed = false;
        }
        else {
            for (int i = 0; i < SHAPE_LENGTH; i++) {
                int r = shapeArray[i].row;
                int c = shapeArray[i].col;
                grid[r][c] = false;
            }
        }
        return removed;
    }
    /** checks to see if a row is full in order to see if it needs to be deleted
     *@param r the row that is to be checked
     *@return boolean filled a boolean that returns if it was filled or not
     */
    public boolean isRowFull(int r) {
        boolean filled = true;
      		for (int i = 0; i < GRID_WIDTH; ++i) {
                if (grid[r][i] == false) {
                    filled = false;
                    break;
                }
            }
      		return filled;
    }
    /** Tries to delete a row if it is full.  Uses isRowFull()
     */
    public void deleteRow() {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            if (isRowFull(i)) {
                count++;
                for (int j = 0; j < grid[0].length; j++) {
                    grid[i][j] = false;
                    
                }
                switch (count) {
                    case 1:
                        ShapeMover.myScore += 100;
                        break;
                    case 2:
                        ShapeMover.myScore += 300;
                        break;
                    case 3:
                        ShapeMover.myScore += 400;
                        break;
                    case 4:
                        ShapeMover.myScore += 800;
                        break;
                }
                for (int r = i; r > 1; r--) {
                    for (int c = 0; c < grid[0].length; c++) {
                        grid[r][c] = grid[r-1][c];
                    }
                }
                i = 0;
            }
        }
    }
    /** Clears all the values in the grid by setting everything in the gird to false
     */
    public void clearGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = false;
            }
        }
    }
    /** Getter for the current TetrisGrid
     */
    public boolean[][] getGrid() {
        return grid;
    }
    /** toString() for printing and debugging of TetrisGrid
     */
    public String toString() {
        String myString = "";
        for (int i = 0; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
                if (grid[i][j]) {
                    myString += "#";
                }
                else {
                    myString += ".";
                }
            }
            myString += '\n';
        }
        return myString;
    }
}
