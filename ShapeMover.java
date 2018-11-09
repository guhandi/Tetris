/**
 *NAME: Nathan Kaslan, Guhan Sundar
 *LOGIN: nkaslan, gusundar
 *ID: A11994249, A11889992
 **/
import java.lang.Runnable;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.util.Scanner;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
/** Class that moves a shape along the Tetris grid
 * implements Runnable and Keylistener
 */
public class ShapeMover implements Runnable, KeyListener {
   private TetrisGrid grid;
   private TetrisShape shape;
   private GraphicsGrid graphGrid;
   private boolean go;
   final private int TIME = 50;
   static public int wait = 20;
   static private int waitTime;
    public static int myScore = -10;
    public static int myHighScore;
   /** Constructor that adds a shape to the Tetris grid
    * @param shape TetrisShape that is the instance of the random shape to be added
    * @param Grid TetrisGrid the shape is added to
    * @param gGrid GraphicsGrid the TetrisGrid is added to
    */
   public ShapeMover(TetrisShape Shape, TetrisGrid Grid, GraphicsGrid gGrid) {
      grid = Grid;
      graphGrid = gGrid;
      shape = Shape;
      go = true;
      if (grid.addShape(shape, 0, 4)) {}
      else {
         Tetris.gameOver = true;
          Tetris.endLabel.setVisible(true);
      }
   }
    /** Moves the shape down until it cannot move any further
     * @return boolean whether the move down was successful
     */
  public synchronized boolean moveDown() {
      boolean moved;
      grid.removeShape(shape);
      if (grid.addShape(shape, 1, 0)) {
         moved = true;
          score();
      }
      else {
         grid.addShape(shape, -1, 0);
         moved = false;
      }
      return moved;      
   }
    /** Moves shape left
     */
  public void moveLeft() {
      grid.removeShape(shape);
      if (!grid.addShape(shape, 0, -1)) {
         grid.addShape(shape, 0, 1);
      }
   }
    /** Moves shape right
     */
   public void moveRight() {
      grid.removeShape(shape);
      if (grid.addShape(shape, 0, 1)) {}
      else {
         grid.addShape(shape, 0, -1);
      }
   }
    /** Rotates shape counter-clockwise
     */
   public void rotateLeft() {
      grid.removeShape(shape);
      shape.LeftRotate();
      if (grid.addShape(shape, 0, 0)) {}
      else {
         shape.RightRotate();
         grid.addShape(shape, 0, 0);
      }
   }
    /** Rotates shape clockwise
     */
   public void rotateRight() {
      grid.removeShape(shape);
      shape.RightRotate();
      if (grid.addShape(shape, 0, 0)) {}
      else {
         shape.LeftRotate();
         grid.addShape(shape, 0, 0);
      }
   }
    /** Increments score and highscore
     */
    public void score() {
        myScore += 10;
        if (myScore >= myHighScore) {
            myHighScore = myScore;
        }
        (Tetris.score).setText(" " + myScore);
        (Tetris.highScore).setText(" " + myHighScore);
    }
    /** Speeds up the movement of the movement
     */
   public void speedUp() {
      wait -= 1;
      if (wait <= 0) {
         wait = 1;
      }
       Tetris.speedSlider.setValue(20 - wait);
   }
   public void keyTyped(KeyEvent e) {}
   public void keyReleased(KeyEvent e) {}
    /** Mehtod that takes a pressed key and turns it into a command for the shape
     * @param e KeyEvent that the user presses
     */
   public void keyPressed(KeyEvent e) {
      if (!go) return;
      char key = e.getKeyChar();
      graphGrid.clearCell();
      switch (key) {
         case 'h':
           moveLeft();
           break;
         case 'l':
           moveRight();
           break;
        case 'j':
           rotateLeft();
           break;
        case 'k':
           rotateRight();
           break;
        case ' ':
           while(moveDown());
      }
      graphGrid.fillCell(grid);
   }
    /** Runs a thread for the moves the shape until it cant be moved 
     */
    public void run() {
        Tetris.endLabel.setVisible(false);
       graphGrid.fillCell(grid);
        score();
       while(go) {
           if (myScore % 2000 == 0) {
               speedUp();
           }
          waitTime = wait * TIME;
          try {
             TimeUnit.MILLISECONDS.sleep(waitTime);
             graphGrid.clearCell();
             if (Tetris.newGame) {
                grid.clearGrid();
                 graphGrid.fillCell(grid);
                myScore = 0;
                break;
             }
             if (moveDown()) {
                graphGrid.fillCell(grid);
             }
             else {
                grid.deleteRow();
                 myScore -= 10;
                go = false;
             }
             TimeUnit.MILLISECONDS.sleep(waitTime);
             graphGrid.clearCell();
          }
          catch (InterruptedException e) {}
           if (Tetris.isReset) {
               grid.clearGrid();
               graphGrid.fillCell(grid);
               myScore = 0;
               myHighScore = 0;
               wait = 20;
               Tetris.score.setText(" " + myHighScore);
               Tetris.highScore.setText(" " + myScore);
               go = false;
               Tetris.isReset = false;
               Tetris.gameOver = true;
           }
       }
        if (Tetris.makeNew) {
            Tetris.addNewShape();
        }
        
    }
}
