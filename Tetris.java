/**
 *NAME: Nathan Kaslan, Guhan Sundar
 *LOGIN: nkaslan, gusundar
 *ID: A11994249, A11889992
 **/
import java.awt.*;
import javax.swing.*;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.util.Random;
import java.io.IOException;
/** Class that creates creates runs the Tetris game
 */
public class Tetris implements ActionListener, ChangeListener {
    public static boolean gameOver = false;
    public static boolean newGame = false;
    public static boolean isReset = false;
    public static boolean makeNew = true;
    public static int width;
    public static int height;
    public static int pixels;
    private JButton buttonNew, buttonReset;
    public static JLabel scoreLabel, hScoreLabel, endLabel, score, highScore;
    public static JSlider speedSlider;
    private JPanel pnlCenter = new JPanel();
    private JPanel pnlBottom = new JPanel();
    public static JPanel pnlTop = new JPanel();
    private static ShapeMover shapeMover;
    private static TetrisGrid tGrid;
    private static GraphicsGrid gGrid;
    private static JFrame frame = new JFrame();
    /** Main mehtod thats gets argument for block size of grid. Creates new 
     * insatnce of Tetris
     */
   public static void main(String[] args) {
        if (args.length == 1) {
            try {
                pixels = Integer.parseInt(args[0]);
                if (pixels < 5) {
                    usage();
                }
            }
            catch (NumberFormatException e) {
                usage();
            }
        }
        else if (args.length == 0) {
            pixels = 20;
        }
        else {
            usage();
        }
       width = pixels * 20;
       height = pixels * 30;
       tGrid = new TetrisGrid();
       gGrid = new GraphicsGrid();
        new Tetris();
   }
    /* Constructor that runs each instance of the Tetris game. Arranges layout on
     * the JFrame
     */
   public Tetris() {
       frame.setSize(width, height);
       frame.setTitle("Tetris!");
       buttonNew = new JButton("New Game");
       buttonReset = new JButton("Reset");
       scoreLabel = new JLabel("score");
       hScoreLabel = new JLabel("highScore");
       endLabel = new JLabel("GAME OVER!");
       speedSlider = new JSlider(0, 19, 0);
       
       pnlCenter.setLayout(new GridBagLayout());
       pnlCenter.add(gGrid);
       
       pnlBottom.setLayout(new GridLayout(1, 3));
       pnlBottom.add(buttonNew);
       pnlBottom.add(buttonReset);
       pnlBottom.add(speedSlider);
       buttonNew.setFocusable(false);
       buttonReset.setFocusable(false);
       speedSlider.setFocusable(false);
       
       pnlTop.setLayout(new GridLayout(2, 4));
       pnlTop.add(scoreLabel);
       pnlTop.add(score = new JLabel(" " + ShapeMover.myScore));
       pnlTop.add(endLabel);
       pnlTop.add(hScoreLabel);
       pnlTop.add(highScore = new JLabel(" " + ShapeMover.myHighScore));
       
       
       buttonNew.addActionListener(this);
       buttonReset.addActionListener(this);
       speedSlider.addChangeListener(this);
       
       frame.add(pnlBottom, BorderLayout.SOUTH);
       frame.add(pnlCenter, BorderLayout.CENTER);
       frame.add(pnlTop, BorderLayout.NORTH);
       
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setVisible(true);
       SwingUtilities.invokeLater(gGrid);
       addNewShape();

   }
    /** Method that gets the action perfromed by the user on the JFrame and sets
     * instructions to go along with each specific button press
     * @param evt the ActionEvent performed on the buttons on the JFrame
     */
    public void actionPerformed (ActionEvent evt) {
        if (evt.getSource() == buttonNew) {
            
            newGame = true;
            makeNew = true;
            isReset= false;
            ShapeMover.myScore = 0;
            endLabel.setVisible(false);
            if (gameOver) {
                tGrid.clearGrid();
                gGrid.fillCell(tGrid);
                newGame = false;
                gameOver = false;
                addNewShape();
            }
        }
        else if (evt.getSource() == buttonReset) {
            isReset = true;
            makeNew = false;
            endLabel.setVisible(false);
            if (gameOver) {
                tGrid.clearGrid();
                gGrid.fillCell(tGrid);
                newGame = false;
                isReset = false;
                ShapeMover.myScore = 0;
                ShapeMover.myHighScore = 0;
                ShapeMover.wait = 20;
                Tetris.score.setText(" " + ShapeMover.myHighScore);
                Tetris.highScore.setText(" " + ShapeMover.myScore);

            }
        }
            
    }
    /** Method that gets the changed action performed by the user on the JFrame
     * and sets instructions to go along with the change
     * @param e the ChangedEvent performed on the slider on the JFrame
     */
    public void stateChanged(ChangeEvent e) {
        JSlider sourceEvent = (JSlider) e.getSource();
        if (sourceEvent == speedSlider) {
            ShapeMover.wait = 20 - (speedSlider.getValue());
        }
        
    }
    /** Method thats adds a new random shape to the grid on the JFrame. Makes a
     * new instance of ShapeMover and starts a thread in that instance to move
     * the thread
     */
  public static void addNewShape() {
     TetrisShape shape = new TetrisShape();
     Random rndm = new Random();
     int random = rndm.nextInt(7);
         switch (random) { 
            case 0: 
              shape.Square();
              break;
            case 1: 
               shape.IShape();
               break;
            case 2: 
               shape.SShape();
               break;
            case 3:
               shape.ZShape();
               break;
           case 4:
               shape.LShape();
               break;
           case 5:
              shape.TShape();
              break;
          case 6:
              shape.JShape();
              break;
          default:
          break;
       }
      frame.removeKeyListener(shapeMover);
      shapeMover = new ShapeMover(shape, tGrid, gGrid);
      newGame = false;
      if (gameOver) {
         return;
      }
      frame.addKeyListener(shapeMover);
      frame.requestFocusInWindow();
      Thread thd = new Thread(shapeMover);
      thd.start();
      }
    /** Method that prints error statement when user input causes error
     */
    public static void usage() {
        System.out.println("Usage: [blocksize] - integer size of each block in pixels must be greater or equal than 5");
        System.exit(-1);
    }
}
 
