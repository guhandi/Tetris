/**
*NAME: Nathan Kaslan, Guhan Sundar
*LOGIN: nkaslan, gusundar
*ID: A11994249, A11889992
**/
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import java.io.IOException;
import java.lang.Thread;
import java.lang.Runnable;
import java.util.concurrent.TimeUnit;
import java.awt.event.*;
/** Class that creates a graphics version of a tetris grid
*/
public class GraphicsGrid extends JPanel implements Runnable {
	
	private static int pixels = Tetris.pixels;
	private static int width = 10 * pixels;
	private static int height = 20 * pixels;
	private ArrayList<Point> fillCells;	
   /** Constructor that initializes the ArrayList of filled cells 
   */
	public GraphicsGrid() {
	   fillCells = new ArrayList<Point>();
	}
	
	@Override
   /** Method called by swing that will check and see preferred size of the grid 
   */
	public Dimension getPreferredSize()
	{
		return new Dimension(width+2, height+2);
	}
   /** Method that will refill and repaint the grid once a change has been made in the ArrayList of a shape 
   *@param g the graphics object that will be painted
   */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (fillCells == null) return;
		for (Point fillCell : fillCells) {
			int cellX = (fillCell.x * pixels);
			int cellY = (fillCell.y * pixels);
			g.setColor(Color.RED);
			g.fillRect(cellX, cellY, pixels, pixels);
		}
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width, height);

		for (int i = 0; i < width; i += pixels) {
			g.drawLine(i, 0, i, height);
		}

		for (int i = 0; i < height; i += pixels) {
			g.drawLine(0, i, width, i);
		}
	}
   /** Method that "makes" the tetris grid into a graphics grid by adding it (and its shapes) to the fill cells.
   *   Calls repaint() which will actually draw the graphics 
   *@param tgrid TetrisGrid that will be made into a Graphics Grid  
   */
	public void fillCell(TetrisGrid tgrid) {
		boolean[][] tetrisArray = tgrid.getGrid();
		for (int i = 0; i < tetrisArray.length; i++) {
       	         for (int j = 0; j < tetrisArray[0].length; j++) {
                          if (tetrisArray[i][j]) {
      	                         fillCells.add(new Point(j, i));
       	                 }
      	         }
      	 }	
	  repaint();
	}
   /** Will delete all instances of the tetris grid from the Graphics Grid. Useful for redrawing with a new shape 
   */
	public void clearCell() {
      fillCells.clear();
	}
	public void run() {
	}
}

