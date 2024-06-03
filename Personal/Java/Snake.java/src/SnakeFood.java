import edu.princeton.cs.introcs.StdDraw;

public class SnakeFood {

	private static double xCenter;
	private static double yCenter;


	public SnakeFood(){
		SnakeFood.xCenter = (int)(Math.random() * 17 + 1) + 0.5;
		SnakeFood.yCenter = (int)(Math.random() * 15 + 1) + 0.5;


	}
	public SnakeFood(double xCord, double yCord){
		SnakeFood.xCenter = xCord;
		SnakeFood.yCenter = yCord;

	}
	public static double getXCenter() {
		return SnakeFood.xCenter;
	}
	public static double getYCenter() {
		return SnakeFood.yCenter;
	}
	public static void setXCenter(double xCord) {
		SnakeFood.xCenter = xCord;
	}
	public static void setYCenter(double yCord) {
		SnakeFood.yCenter = yCord;
	}
	public static void drawSnakeFood() {
		StdDraw.setPenColor(230, 72, 29);
		StdDraw.filledSquare(SnakeFood.xCenter, SnakeFood.yCenter, 0.5);
	}






	public static void main(String[] args) {

	}

}
