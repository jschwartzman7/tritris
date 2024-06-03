import java.util.LinkedList;

import edu.princeton.cs.introcs.StdDraw;

public class Snake {


		public static final int up = 38;
		public static final int right = 39;
		public static final int down = 40;
		public static final int left = 37;
		public static final int delay = 10;

		public static double frontX;
		public static double frontY;
		public static double frontXCenter;
		public static double frontYCenter;
		public static boolean touchingSelf = false;
		public static LinkedList<double[]> listAllSquareCenterCords;
		public static LinkedList<double[]> listFrontSquareCenterCords;
		// 0 is initial.
		// 1 = up
		// 2 = right
		// 3 = down
		// 4 = left

		public Snake() {
			Snake.listAllSquareCenterCords = new LinkedList<>();
			Snake.listFrontSquareCenterCords = new LinkedList<>();
			Snake.frontX = 6;
			Snake.frontXCenter = 5.5;
			Snake.frontYCenter = 8.5;
			StdDraw.setPenColor(70, 115, 232);
			for(double i = 2.5; i < 6.5; ++i) {
				double[] cords = new double[2];
				cords[0] = i;
				cords[1] = 8.5;
				listAllSquareCenterCords.add(cords);
				StdDraw.filledSquare(cords[0], cords[1], 0.5);
			}
			for(double i = 0; i < 3.1; i += 0.1) {
				double[] firstFrontCords = new double[] {i + 2.5, 8.5};
				listFrontSquareCenterCords.add(firstFrontCords);
			}

		}
		public static void AddElement() {
			double[] newSquare = new double[] {0, 0};
			listAllSquareCenterCords.add(newSquare);
		}


		public static void Move(int direction, int wallCord) {
			StdDraw.setPenColor(70, 115, 232);

			// Up
			if(direction == up) {
				// Snake is moving right
				if(frontXCenter + 0.5 < wallCord) {
					// Keep moving right
					frontXCenter += 0.1;
				}
				// Snake is moving left
				else if (frontXCenter - 0.5 > wallCord) {
					// Keep moving left
					frontXCenter -= 0.1;
				}
				else {
					// Move up
					frontXCenter = (int)(frontXCenter) + 0.5;
					frontYCenter = frontYCenter + 0.1;

				}
					frontY = frontYCenter + .5;
			}

			// Down
			if(direction == down) {
				// Snake is moving right
				if(frontXCenter + 0.5 < wallCord) {
					// Keep moving right
					frontXCenter += 0.1;
				}
				// Snake is moving left
				else if(frontXCenter - 0.5 > wallCord) {
					// Keep moving left
					frontXCenter -= 0.1;
				}
				else {
					// Move down
					frontXCenter = (int)(frontXCenter) + 0.5;
					frontYCenter -= 0.1;
				}
				frontY = frontYCenter - .5;
			}

			// Right
			if(direction == right) {
				// If right is not initial direction
					// Snake is moving up
					if(frontYCenter + 0.5 < wallCord && wallCord != 0) {
						// Keep moving up
						frontYCenter += 0.1;
					}
					// Snake is moving down
					else if(frontYCenter - 0.5 > wallCord && wallCord != 0) {
						// Keep moving down
						frontYCenter -= 0.1;
					}
					else {
						// Move right
						frontXCenter += 0.1;
						frontYCenter = (int)(frontYCenter) + 0.5;
					}
				frontX = frontXCenter + .5;

			}

			// Left
			if(direction == left) {
					// Snake is moving up
					if(frontYCenter + 0.5 < wallCord) {
						// Keep moving up
						frontYCenter += 0.1;
					}
					// Snake is moving down
					else if(frontYCenter - 0.5 > wallCord) {
						// Keep moving down
						frontYCenter -= 0.1;
					}
					else {
						// Move left
						frontXCenter -= 0.1;
						frontYCenter = (int)(frontYCenter) + 0.5;
					}
				frontX = frontXCenter - .5;
			}

			double[] frontCords = new double[] {frontXCenter, frontYCenter};
			listFrontSquareCenterCords.add(frontCords);
			StdDraw.filledSquare(frontXCenter, frontYCenter, 0.5);

			for(int i = 0; i < listAllSquareCenterCords.size()-1; ++i) {
				StdDraw.filledSquare(listFrontSquareCenterCords.get(listFrontSquareCenterCords.size()-delay*(listAllSquareCenterCords.size()-1-i))[0], listFrontSquareCenterCords.get(listFrontSquareCenterCords.size()-delay*(listAllSquareCenterCords.size()-1-i))[1], 0.5);
				double xCenter = listFrontSquareCenterCords.get(listFrontSquareCenterCords.size()-delay*(listAllSquareCenterCords.size()-1-i))[0];
				double yCenter = listFrontSquareCenterCords.get(listFrontSquareCenterCords.size()-delay*(listAllSquareCenterCords.size()-1-i))[1];
				if((frontXCenter > xCenter - 0.3 && frontXCenter < xCenter + 0.3)   &&   (frontYCenter > yCenter - 0.3 && frontYCenter < yCenter + 0.3)) {
					touchingSelf = true;
				}
			}
		}



	public static void main(String[] args) {

		/*
		 * StdDraw.setPenColor(70, 115, 232);
			// Up
			if(direction == up) {
				// Snake is moving right
				if(listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0] + .5 < wallCord) {

					// Keep moving right
					Snake.frontXCenter = listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0] + 0.1;
					Snake.frontYCenter = listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1];
					listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0] += 0.1;
					double[] frontCords = new double[] {listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0], listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1]};
					listFrontSquareCenterCords.add(frontCords);
				}
				// Snake is moving left
				else if (listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0] - .5 > wallCord) {

					// Keep moving left
					Snake.frontXCenter = listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0] - 0.1;
					Snake.frontYCenter = listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1];
					listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0] -= 0.1;
					double[] frontCords = new double[] {listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0], listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1]};
					listFrontSquareCenterCords.add(frontCords);
				}

				else {

					// Move up
					Snake.frontXCenter = (int)(listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0]) + 0.5;
					Snake.frontYCenter = listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1] + 0.1;
					listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1] += 0.1;

					double[] frontCords = new double[] {(int)(listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0]) + 0.5, listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1]};
					listFrontSquareCenterCords.add(frontCords);
				}
					Snake.frontY = listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1] + .5;

			}

			// Down
			if(direction == down) {
				// Snake is moving right
				if(listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0] + .5 < wallCord) {

					// Keep moving right
					Snake.frontXCenter = listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0] + 0.1;
					Snake.frontYCenter = listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1];
					listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0] += 0.1;
					double[] frontCords = new double[] {listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0], listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1]};
					listFrontSquareCenterCords.add(frontCords);
				}
				// Snake is moving left
				else if(listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0] - .5 > wallCord) {

					// Keep moving left
					Snake.frontXCenter = listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0] - 0.1;
					Snake.frontYCenter = listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1];
					listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0] -= 0.1;
					double[] frontCords = new double[] {listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0], listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1]};
					listFrontSquareCenterCords.add(frontCords);
				}

				else {

				// Move down
				Snake.frontXCenter = (int)(listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0]) + 0.5;
				Snake.frontYCenter = listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1] - 0.1;
				listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1] -= 0.1;

				double[] frontCords = new double[] {(int)(listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0]) + 0.5, listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1]};
				listFrontSquareCenterCords.add(frontCords);
				}
				Snake.frontY = listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1] - .5;

			}

			// Right
			if(direction == right) {
				// If right is not initial direction
				if(wallCord != 0) {
					// Snake is moving up
					if(listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1] + .5 < wallCord) {

						// Keep moving up
						Snake.frontXCenter = listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0];
						Snake.frontYCenter = listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1] + 0.1;
						listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1] += 0.1;
						double[] frontCords = new double[] {listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0], listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1]};
						listFrontSquareCenterCords.add(frontCords);
					}
					// Snake is moving down
					else if(listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1] - .5 > wallCord) {

						// Keep moving down
						Snake.frontXCenter = listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0];
						Snake.frontYCenter = listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1] - 0.1;
						listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1] -= 0.1;
						double[] frontCords = new double[] {listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0], listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1]};
						listFrontSquareCenterCords.add(frontCords);
					}

					else {

						// Move right
						Snake.frontXCenter = listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0]  + 0.1;
						Snake.frontYCenter = (int)(listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1]) + 0.5;
						listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0] += 0.1;
						double[] frontCords = new double[] {listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0], (int)(listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1]) + 0.5};
						listFrontSquareCenterCords.add(frontCords);
					}
				}
				// If right is initial direction
				else {
					// Move right
					Snake.frontXCenter = listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0]  + 0.1;
					Snake.frontYCenter = (int)(listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1]) + 0.5;
					listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0] += 0.1;
					double[] frontCords = new double[] {listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0], listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1]};
					listFrontSquareCenterCords.add(frontCords);
				}
				Snake.frontX = listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0] + .5;

			}

			if(direction == left) {
					// Snake is moving up
					if(listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1] + .5 < wallCord) {

						// Keep moving up
						Snake.frontXCenter = listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0];
						Snake.frontYCenter = listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1] + 0.1;
						listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1] += 0.1;
						double[] frontCords = new double[] {listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0], listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1]};
						listFrontSquareCenterCords.add(frontCords);
					}
					// Snake is moving down
					else if(listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1] - .5 > wallCord) {

						// Keep moving down
						Snake.frontXCenter = listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0];
						Snake.frontYCenter = listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1] - 0.1;
						listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1] -= 0.1;
						double[] frontCords = new double[] {listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0], listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1]};
						listFrontSquareCenterCords.add(frontCords);
					}

					else {
						// Move left
						Snake.frontXCenter = listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0] - 0.1;
						Snake.frontYCenter = (int)(listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1]) + 0.5;
						listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0] -= 0.1;
						double[] frontCords = new double[] {listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0], (int)(listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[1]) + 0.5};
						listFrontSquareCenterCords.add(frontCords);
					}
				Snake.frontX = listAllSquareCenterCords.get(listAllSquareCenterCords.size()-1)[0] - .5;

			}

			StdDraw.filledSquare(Snake.frontXCenter, Snake.frontYCenter, 0.5);

			for(int i = 0; i < listAllSquareCenterCords.size()-1; ++i) {
				StdDraw.filledSquare(listFrontSquareCenterCords.get(listFrontSquareCenterCords.size()-delay*(listAllSquareCenterCords.size()-1-i))[0], listFrontSquareCenterCords.get(listFrontSquareCenterCords.size()-delay*(listAllSquareCenterCords.size()-1-i))[1], 0.5);
				double xCenter = listFrontSquareCenterCords.get(listFrontSquareCenterCords.size()-delay*(listAllSquareCenterCords.size()-1-i))[0];
				double yCenter = listFrontSquareCenterCords.get(listFrontSquareCenterCords.size()-delay*(listAllSquareCenterCords.size()-1-i))[1];
				if((Snake.frontXCenter > xCenter - 0.3 && Snake.frontXCenter < xCenter + 0.3)   &&   (Snake.frontYCenter > yCenter - 0.3 && Snake.frontYCenter < yCenter + 0.3)) {
					touchingSelf = true;
				}
			}
		}
		 */
	}

}
