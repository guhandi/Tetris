/**
 *NAME: Nathan Kaslan, Guhan Sundar
 *LOGIN: nkaslan, gusundar
 *ID: A11994249, A11889992
 **/

/* Class that creates a new shape
 */
public class TetrisShape {
   private Coord[] shapeArray; //shapeArray goes x, y; column 0 is x column 1 is y 
   private String[][] printArray;
   final Coord ZERO_ZERO = new Coord(0, 0);
   final Coord ZERO_ONE = new Coord(0, 1);
   final Coord ZERO_TWO = new Coord(0, 2);
   final Coord ZERO_THREE = new Coord(0, 3);
   final Coord ONE_ZERO = new Coord(1, 0);
   final Coord ONE_ONE = new Coord(1, 1);
   final Coord ONE_TWO = new Coord(1, 2);
   final Coord ONE_THREE = new Coord(1, 3);
   final Coord NONE_ZERO = new Coord(-1, 0);
   final Coord ZERO_NONE = new Coord(0, -1);
   final Coord NONE_NONE = new Coord(-1, -1);
   final Coord NONE_ONE = new Coord(-1, 1);
   final Coord NONE_TWO = new Coord(-1, 2);
   private int offR = 0, offC = 0;
   /** Constructor that makes a new array of Coordinates to form a shape
    */
    public TetrisShape() {
      shapeArray = new Coord[4];
      shapeArray[0] = ZERO_ZERO; //Origin; same for all shapes 
   }
    /** Mathod that nakes a square
     */
   public void Square() {
      shapeArray[1] = ZERO_ONE;
      shapeArray[2] = ONE_ZERO;
      shapeArray[3] = ONE_ONE;
      offR = offC = 0;
   }
    /** Mathod that nakes a square
     */
   public void IShape() {
      shapeArray[1] = ZERO_ONE;
      shapeArray[2] = ZERO_TWO;
      shapeArray[3] = ZERO_THREE;
      offR = offC = 0;
   }
    /** Mathod that nakes an S shape
     */
   public void SShape() {
      shapeArray[1] = ONE_ZERO;
      shapeArray[2] = ZERO_ONE;
      shapeArray[3] = NONE_ONE;
      offR = offC = 0;
   }
    /** Mathod that nakes a Z shape
     */
   public void ZShape() {
      shapeArray[1] = NONE_ZERO;
      shapeArray[2] = ZERO_ONE;
      shapeArray[3] = ONE_ONE;
      offR = offC = 0;
   }
    /** Mathod that nakes a L shape
     */
   public void LShape() {
      shapeArray[1] = ZERO_ONE;
      shapeArray[2] = ZERO_TWO;
      shapeArray[3] = ONE_TWO;
      offR = offC = 0;
   }
    /** Mathod that nakes a J shape
     */
   public void JShape() {
      shapeArray[1] = ZERO_ONE;
      shapeArray[2] = ZERO_TWO;
      shapeArray[3] = NONE_TWO;
      offR = offC = 0;
   }
    /** Mathod that nakes a T shape
     */
   public void TShape () {
      shapeArray[1] = ONE_ZERO;
      shapeArray[2] = NONE_ZERO;
      shapeArray[3] = ZERO_ONE;
      offR = offC = 0;
   }
    /** Method that rotates the shape counter-clockwise
     */
  public void LeftRotate() {
      int tempInt;
      for (int i = 0; i < shapeArray.length ; ++i) {
         tempInt =  shapeArray[i].row - offR;
         shapeArray[i].row = -1 * (shapeArray[i].col - offC) + offR;
         shapeArray[i].col = tempInt + offC;
      }
  }
    /** Method that rotates the shape clockwise
     */
  public void RightRotate() {
     int tempInt;
      for (int i = 0; i < shapeArray.length ; ++i) {
         tempInt = shapeArray[i].col - offC;
         shapeArray[i].col = -1 * (shapeArray[i].row - offR) + offC;
         shapeArray[i].row = tempInt + offR;
      }
  }
    /** Mehtod that gets the array that holds the shape
     * @return Coord[] which is the shape
     */
  public Coord[] getCoord() {
     return shapeArray;
  }
    /** Mehtod that gets the array that holds the shape at an offset
     * @return Coord[] which is the shape
     * @param offsetR the amount to change the row value
     * @param offsetC the amount to change the column value
     */
  public Coord[] getCoord(int offsetR, int offsetC) {
     offR += offsetR;
     offC += offsetC;
     for (int i = 0; i < shapeArray.length; ++i) {
        shapeArray[i].row = shapeArray[i].row + offsetR;
        shapeArray[i].col = shapeArray[i].col + offsetC;
     } 
     return shapeArray;
  }
    /** Method to print the shape
     * @return the String to print
     */
  public String toString() {
     printArray = new String[4][4];
     String myString = "";
     for (int i = 0; i < printArray.length; i++) {
	for (int j = 0; j < printArray[0].length; j++) {
	    printArray[i][j] = ".";
	}
     }
     int r;
     int c;
     int rmin = 0;
     int cmin = 0;
     for (int i = 0; i < shapeArray.length; ++i) {
	if (shapeArray[i].row < 0 || shapeArray[i].col < 0) {
	    if (shapeArray[i].row < rmin) {
		rmin = shapeArray[i].row;
	    }
	    if (shapeArray[i].col < cmin) {
		cmin = shapeArray[i].col;
	    }
	}
     }
     rmin = -rmin;
     cmin = -cmin;
     shapeArray = getCoord(rmin, cmin);
     for (int i = 0; i < shapeArray.length; i++) {	
	r = shapeArray[i].row;
	c = shapeArray[i].col;
	printArray[r][c] = "#";
     }
     for (int i = 0; i < printArray.length; i++) {
	for (int j = 0; j < printArray[0].length; j++) {
		myString+=printArray[i][j];
	}
	myString+='\n';
     }
     return myString;
  }
}
