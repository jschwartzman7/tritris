import java.awt.Color;

import edu.princeton.cs.introcs.StdDraw;

public class SnakeGame {

	public static final int up = 38;
	public static final int right = 39;
	public static final int down = 40;
	public static final int left = 37;
	public static int highScore = 0;

	public static void RunGame() {

		// Glitch where square moves a little to the side before changing directions

		StdDraw.enableDoubleBuffering();
		StdDraw.clear();
		StdDraw.setScale(0, 19);
		StdDraw.setPenColor(87, 138, 52);
		StdDraw.filledSquare(10, 10, 10);
		StdDraw.setPenColor(74, 117, 44);
		StdDraw.filledRectangle(10, 18, 10, 1);
		StdDraw.setPenColor(162, 209, 72);
		StdDraw.filledRectangle(9.5, 8.5, 8.5, 7.5);
		StdDraw.setPenColor(170, 215, 80);
		for(double i = 1.5; i <= 17.5; i+=2) {
			for(double j = 1.5; j <= 15.5; j+=2) {
					StdDraw.filledSquare(i, j, 0.5);
			}
		}
		for(double i = 2.5; i <= 16.5; i+=2) {
			for(double j = 2.5; j <= 14.5; j+=2) {
					StdDraw.filledSquare(i, j, 0.5);
			}
		}

		int score = 0;
		StdDraw.setPenColor(Color.WHITE);
		StdDraw.text(3.4, 17.7, "Current Score: " + score);
		StdDraw.text(10, 17.7, "High Score: " + SnakeGame.highScore);
		new SnakeFood(13.5, 8.5);
		double snakeFoodPreviousXCenter = SnakeFood.getXCenter();
		double snakeFoodPreviousYCenter = SnakeFood.getYCenter();
		SnakeFood.drawSnakeFood();
		new Snake();
		int direction = 0;
		int wallCord = 6;

		Snake.touchingSelf = false;
		StdDraw.show(300);

			while(direction == 0) {

				for(int i = 38; i <= 40; ++i) {
					if(StdDraw.isKeyPressed(i)) {
						direction = i;
						if(i == 39) {
							wallCord = 0;
						}
					}
				}
			}

		while(direction != 0 && Snake.frontXCenter < 17.7 && Snake.frontXCenter > 1.3 && Snake.frontYCenter < 15.7 && Snake.frontYCenter > 1.3 && !Snake.touchingSelf) {
			StdDraw.clear();

			// Background
			StdDraw.setPenColor(87, 138, 52);
			StdDraw.filledSquare(10, 10, 10);
			StdDraw.setPenColor(74, 117, 44);
			StdDraw.filledRectangle(10, 18, 10, 1);
			StdDraw.setPenColor(162, 209, 72);
			StdDraw.filledRectangle(9.5, 8.5, 8.5, 7.5);
			StdDraw.setPenColor(170, 215, 80);
			for(double i = 1.5; i <= 17.5; i+=2) {
				for(double j = 1.5; j <= 15.5; j+=2) {
						StdDraw.filledSquare(i, j, 0.5);
				}
			}
			for(double i = 2.5; i <= 16.5; i+=2) {
				for(double j = 2.5; j <= 14.5; j+=2) {
						StdDraw.filledSquare(i, j, 0.5);
				}
			}

			StdDraw.setPenColor(Color.WHITE);
			StdDraw.text(3.4, 17.7, "Current Score: " + score);
			StdDraw.text(10, 17.7, "High Score: " + SnakeGame.highScore);

			Snake.Move(direction, wallCord);
			SnakeFood.drawSnakeFood();

		// Changing Direction

			// Up
			if(StdDraw.isKeyPressed(up) && direction == right) {
				wallCord = (int)(Snake.frontX+1);
				direction = up;
			}
			if(StdDraw.isKeyPressed(up) && direction == left) {
				wallCord = (int)(Snake.frontX);
				direction = up;
			}



			// Right
			if(StdDraw.isKeyPressed(right) && direction == up) {
				wallCord = (int)(Snake.frontY+1);
				direction = right;
			}
			if(StdDraw.isKeyPressed(right) && direction == down) {
				wallCord = (int)(Snake.frontY);
				direction = right;
			}




			// Down
			if(StdDraw.isKeyPressed(down) && direction == right) {
				wallCord = (int)(Snake.frontX+1);
				direction = down;
			}
			if(StdDraw.isKeyPressed(down) && direction == left) {
				wallCord = (int)(Snake.frontX);
				direction = down;
			}



			// Left
			if(StdDraw.isKeyPressed(left) && direction == up) {
				wallCord = (int)(Snake.frontY+1);
				direction = left;
			}
			if(StdDraw.isKeyPressed(left) && direction == down) {
				wallCord = (int)(Snake.frontY);
				direction = left;
			}

		// Eating Food
			boolean upTest = direction == up && Snake.frontY > SnakeFood.getYCenter() - 0.1 && Snake.frontXCenter == SnakeFood.getXCenter();
			boolean rightTest = direction == right && Snake.frontX > SnakeFood.getXCenter() - 0.1 && Snake.frontYCenter == SnakeFood.getYCenter();
			boolean downTest = direction == down && Snake.frontY < SnakeFood.getYCenter() + 0.1 && Snake.frontXCenter == SnakeFood.getXCenter();
			boolean leftTest = direction == left && Snake.frontX < SnakeFood.getXCenter() + 0.1 && Snake.frontYCenter == SnakeFood.getYCenter();
			if(upTest || rightTest || downTest || leftTest) {
				new SnakeFood();
				while((SnakeFood.getXCenter() <= snakeFoodPreviousXCenter + 2 && SnakeFood.getXCenter() >= snakeFoodPreviousXCenter - 2) && (SnakeFood.getYCenter() >= snakeFoodPreviousYCenter - 2 && SnakeFood.getYCenter() <= snakeFoodPreviousYCenter + 2)) {
					new SnakeFood();
				}
				for(double[] cords : Snake.listAllSquareCenterCords) {
					if((SnakeFood.getXCenter() <= cords[0] + 0.5 && SnakeFood.getXCenter() >= cords[0] - 0.5) && (SnakeFood.getYCenter() <= cords[1] + 0.5 && SnakeFood.getYCenter() >= cords[1] - 0.5)) {
						new SnakeFood();
					}
				}
				snakeFoodPreviousXCenter = SnakeFood.getXCenter();
				snakeFoodPreviousYCenter = SnakeFood.getYCenter();
				Snake.AddElement();
				++score;
				if(score > highScore) {
					++highScore;
				}
			}
			StdDraw.show(10);
		}
		StdDraw.setPenColor();
		StdDraw.setPenRadius(0.01);
		StdDraw.line(4.5, 5, 4.5, 16);
		StdDraw.line(14.5, 5, 14.5, 16);
		StdDraw.line(4.5, 5, 14.5, 5);
		StdDraw.line(4.5, 16, 14.5, 16);
		StdDraw.line(9.5-3.5, 3.5+1.25, 9.5-3.5, 3.5-1.25);
		StdDraw.line(9.5-3.5, 3.5+1.25, 9.5+3.5, 3.5+1.25);
		StdDraw.line(9.5-3.5, 3.5-1.25, 9.5+3.5, 3.5-1.25);
		StdDraw.line(9.5+3.5, 3.5+1.25, 9.5+3.5, 3.5-1.25);
		StdDraw.setPenColor(77, 193, 249);
		StdDraw.filledRectangle(9.5, 12, 5, 4);
		StdDraw.setPenColor(70, 115, 232);
		for(int i = 0; i < 6; ++i) {
			StdDraw.filledSquare(i + 5.5, 8.5, 0.5);
		}
		StdDraw.filledRectangle(4.75, 8.5, 0.25, 0.5);
		StdDraw.setPenColor(190, 235, 100);
		StdDraw.filledRectangle(9.5, 6.5, 5, 1.5);
		for(int i = 5; i < 15; ++i) {
			for(int j = 5; j < 8; ++j) {
				if((i % 2 != 0 && j % 2 != 0) || (i % 2 == 0 && j % 2 == 0)) {
					StdDraw.setPenColor(182, 229, 92);
					StdDraw.filledSquare(i, j+0.5, 0.5);
				}
			}
		}
		StdDraw.setPenColor(Color.WHITE);
		StdDraw.text(9.5, 14.5, "Your Score: " + score);
		StdDraw.text(9.5, 12.5, "High Score: " + highScore);
		StdDraw.text(9.5, 10.5, "Press Space to Play Again");
		StdDraw.setPenColor(18, 85, 204);
		StdDraw.filledRectangle(9.5, 3.5, 3.5, 1.25);
		StdDraw.setPenColor(Color.WHITE);
		StdDraw.text(10, 3.5, "PLAY");
		double[] xCords = new double[] {8, 8, 8 + Math.sqrt(3)/2};
		double[] yCords = new double[] {3, 4, 3.5};
		StdDraw.setPenRadius(0.005);
		StdDraw.polygon(xCords, yCords);
		StdDraw.show();
		while(!StdDraw.isKeyPressed(32) && (!StdDraw.isMousePressed() || ((StdDraw.mouseX() > 13 || StdDraw.mouseX() < 6) || (StdDraw.mouseY() > 4.75 || StdDraw.mouseY() < 2.25)))) {
		}
		RunGame();



	}

	public static void main(String[] args) {
		RunGame();


	}

}
