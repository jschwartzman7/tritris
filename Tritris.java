import java.util.HashSet;
import java.util.LinkedList;

import edu.princeton.cs.introcs.StdDraw;

public class Tritris {
	//public static int scale = 11;
	public static final int up = 38;
	public static final int right = 39;
	public static final int down = 40;
	public static final int left = 37;
	public static final double sq3 = Math.sqrt(3);
	static int score = 0;
	public static LinkedList<TritrisShape> shapes;
	public static LinkedList<int[]> floor;
	public static LinkedList<int[]> ceiling;
	//public static LinkedList<Integer> fullFloorLayers;
	//public static LinkedList<Integer> fullCeilingLayers;
	// Tetris on a hexagonal grid, with pieces made from triangles
	// Shapes start at top and slowly move down
	// Ability to move shapes side to side and rotate while falling
	// Objects:
	//     - Hexagonal board
	//          - drawBoard() with shapes on it
	//          - Increment time to move current shape down 1 and know when shape hits bottom
	//          - clear row when full and above shapes fall down like gravity
	//     - Different shapes
	//          - moveLeft()
	//          - moveRIght()
	//          - rotate()
	//          - pushDown()
	//     - Additional:
	//          - Score board
    //          - Next shapes

	public Tritris() {
		shapes = new LinkedList<>();
		floor = new LinkedList<>();
		ceiling = new LinkedList<>();
		//fullFloorLayers = new LinkedList<Integer>();
		//fullCeilingLayers = new LinkedList<Integer>();
		for(int i = 1; i <=5; ++i) {
			floor.add(new int[] {i, -19 + i});
			floor.add(new int[] {i, -20 + i});
			floor.add(new int[] {-i, -19 + i});
			floor.add(new int[] {-i, -20 + i});


			ceiling.add(new int[] {i, 20 - i});
			ceiling.add(new int[] {i, 19 - i});
			ceiling.add(new int[] {-i, 20 - i});
			ceiling.add(new int[] {-i, 19 - i});
		}
	}



	public static void drawGrid() {
		StdDraw.setPenColor(15, 30, 37);
		StdDraw.filledSquare(0, 0, 12);
		StdDraw.setPenColor(74, 92, 108);
		double xOffset = (2.5*sq3);
		double deltaX = 0.5*sq3;
		double yOffset = 7.5;
		double deltaY = 0.5;
		for(int i = 0; i < 11; ++i) {
			StdDraw.line(-xOffset + i*deltaX, -yOffset - deltaY*(-Math.abs(i-5)+5), -xOffset + i*deltaX, yOffset + deltaY*(-Math.abs(i-5)+5));
		}
		for(int i = 0; i < 6; ++i) {
			StdDraw.line(-i*sq3*0.5, -yOffset - 2.5 + i*0.5, xOffset, -yOffset + i); // up to the right
			StdDraw.line(-xOffset, -yOffset + i, i*sq3*0.5, -yOffset - 2.5 + i*0.5); // up to the left
		}
		for(int i = 1; i <= 9; ++i) {
			StdDraw.line(-xOffset, -yOffset + i, xOffset, -yOffset + 5.0 + i);
			StdDraw.line(-xOffset, -yOffset + 5.0 + i, xOffset, -yOffset + i);
		}
		for(int i = 0; i < 6; ++i) {
			StdDraw.line(-xOffset, yOffset - i, i*sq3*0.5, yOffset + 2.5 - i*0.5); // up to the right
			StdDraw.line(-i*sq3*0.5, yOffset + 2.5 - i*0.5, xOffset, yOffset - i); // up to the right
		}
	}

	public static boolean gameOver(TritrisShape curShape) {
		for(TritrisShape shape : shapes) {
			if(shape != curShape) {
				if(shape.touching(shape.curCords, ceiling)) {
					return true;
				}
			}
		}
		return false;
	}

	public static LinkedList<Integer> getFullFloorLayers(){
		LinkedList<Integer> fullIndices = new LinkedList<>();
		for(int height = 0; height < 30; height+=2) {
			boolean layerFull = true;
			for(int[] floorCord : floor) {
				boolean cordFull = false;
				for(TritrisShape shape : shapes) {
					for(int[] shapeCord : shape.curCords) {
						if(shapeCord[0] == floorCord[0] && shapeCord[1] == floorCord[1] + height) {
							cordFull = true;
						}
					}
				}
				if(!cordFull) {
					layerFull = false;
					break;
				}
			}
			if(layerFull) {
				fullIndices.add(height);
			}
		}
		return fullIndices;
	}

	public static LinkedList<Integer> getFullCeilingLayers(){
		LinkedList<Integer> fullIndices = new LinkedList<>();
		for(int height = -28; height <= 0; height+=2) {
			boolean layerFull = true;
			for(int[] ceilingCord : ceiling) {
				boolean cordFull = false;
				for(TritrisShape shape : shapes) {
					for(int[] shapeCord : shape.curCords) {
						if(shapeCord[0] == ceilingCord[0] && shapeCord[1] == ceilingCord[1] + height) {
							cordFull = true;
						}
					}
				}
				if(!cordFull) {
					layerFull = false;
					break;
				}
			}
			if(layerFull) {
				fullIndices.add(height);
			}
		}
		return fullIndices;
	}



	public static void removeFullLayers(LinkedList<Integer> fullLayers, LinkedList<int[]> object) {

		//HashSet<TritrisShape> shapesToMove = new HashSet<TritrisShape>();
		for(Integer height : fullLayers) {
			//StdDraw.clear();
			for(int[] objectCord : object) {
				for(TritrisShape shape : shapes) {
					//shape.drawShape();
					for (int[] element : shape.curCords) {
						if(objectCord[0] == element[0] && objectCord[1] + height == element[1]) {
							shape.removeCord(new int[] {element[0], element[1]});
						}
						//if(i < shape.curCords.size()) {
							//if(floorCord[0] == shape.curCords.get(i)[0] && floorCord[1] + height < shape.curCords.get(i)[1]) {
								//shape.curCords.get(i)[1] -= 2;
								//shapesToMove.add(shape);
							//}
						//}
					}
				}
			}
			//StdDraw.show(1000);
		}

		MoveShapesDown(fullLayers, object);
	}




	/**public static void removeFullCeilingLayers(LinkedList<Integer> fullCeilingLayers) {
		//int numMoves = fullCeilingLayers.size();
		//HashSet<TritrisShape> shapesToMove = new HashSet<TritrisShape>();
		for(Integer height : fullCeilingLayers) {
			System.out.println(height);
			for(int[] layerCord : ceiling) {
				for(TritrisShape shape : shapes) {
					for(int i = 0; i < shape.curCords.size(); ++i) {
						if(layerCord[0] == shape.curCords.get(i)[0] && layerCord[1] + height == shape.curCords.get(i)[1]) {
							shape.removeCord(new int[] {shape.curCords.get(i)[0], shape.curCords.get(i)[1]});
						}
						//if(i < shape.curCords.size()) {
							//if(layerCord[0] == shape.curCords.get(i)[0] && layerCord[1] + layer < shape.curCords.get(i)[1]) {
								//shape.curCords.get(i)[1] -= 2;
								//shapesToMove.add(shape);
							//}
						//}
					}
				}
			}
		}
		MoveShapesDown(fullCeilingLayers, ceiling);


	}**/

	public static void MoveShapesDown(LinkedList<Integer> fullLayers, LinkedList<int[]> object) {

		for(int i = fullLayers.size()-1; i >= 0; --i){
			int x = 10;
			for(int[] objectCord : object) {
				if(x != objectCord[0]) {
					for(TritrisShape shape : shapes) {
						for(int[] cord : shape.curCords) {
							if(objectCord[0] == cord[0] && objectCord[1] + fullLayers.get(i) < cord[1]) {
								cord[1] -= 2;
							}
						}
					}
				}
				x = objectCord[0];
			}
		}
		//for(int i = 0; i < numMoves; ++i) {
			//for(TritrisShape shape : shapesToMove) {
				//shape.moveDown();
			//}
		//}
	}


	public static void animateLayer(LinkedList<Integer> fullFloorLayers) {
		HashSet<int[]> cordsToCover = new HashSet<>();
		for(Integer layer : fullFloorLayers) {
			for(int x = 1; x <= 5; ++x) {
				StdDraw.clear();
				drawGrid();
				//curShape.drawShadow();
				for(TritrisShape shape : shapes) {
					shape.drawShape();
				}
				drawScore(score);

				cordsToCover.add(new int[] {x, -19 + x + layer});
				cordsToCover.add(new int[] {x, -20 + x + layer});
				cordsToCover.add(new int[] {-x, -19 + x + layer});
				cordsToCover.add(new int[] {-x, -20 + x + layer});
				for(int [] cord : cordsToCover) {
					StdDraw.setPenColor(15, 30, 37);
					TritrisShape.drawCord(cord, false);
					StdDraw.setPenColor(74, 92, 108);
					TritrisShape.drawCord(cord, true);
				}




				StdDraw.show(30);
			}
		}

	}



	public static void checkFullLayers() {
		LinkedList<Integer> fullFloorLayers = getFullFloorLayers();
		if(fullFloorLayers.size() > 0) {
			animateLayer(fullFloorLayers);
			removeFullLayers(fullFloorLayers, floor);
			score += 5*fullFloorLayers.size();
		}
		LinkedList<Integer> fullCeilingLayers = getFullCeilingLayers();
		if(fullCeilingLayers.size() > 0) {
			removeFullLayers(fullCeilingLayers, ceiling);
			score += 5*fullCeilingLayers.size();
		}
	}

	public static void drawScore(int score) {
		StdDraw.setPenColor(230, 230, 240);
		StdDraw.filledRectangle(-8, 0, 2, 1);
		StdDraw.setPenColor();
		StdDraw.text(-8, .5, "Score");
		StdDraw.text(-8, -.5, ""+score);
	}



	public static void runGame() {
		Tritris game = new Tritris();
		StdDraw.enableDoubleBuffering();
		StdDraw.setScale(-11, 11);
		drawGrid();
		TritrisShape curShape = new TritrisShape((int)(8*Math.random()), shapes, floor, ceiling);
		//TritrisShape curShape = new TritrisShape(2, shapes, floor, ceiling);
		shapes.add(curShape);
		curShape.drawShape();
		curShape.drawShadow();
		drawScore(score);
		StdDraw.show(250);
		int time = 0;
		while(!gameOver(curShape)) {
			StdDraw.clear();
			drawGrid();
			curShape.drawShadow();
			for(TritrisShape shape : shapes) {
				shape.drawShape();
			}
			drawScore(score);

			if(time % 500 == 0) {
				//if (curShape.touching(curShape.curCords, floor) || curShape.onTopOfShape(curShape.curCords)){
				if (curShape.outOfBounds(curShape.checkDirection(down, curShape.curCords)) != 0){
					//System.out.println("New Shape Time");
					checkFullLayers();
					curShape = new TritrisShape((int)(8*Math.random()), shapes, floor, ceiling);
					//curShape = new TritrisShape(7, shapes, floor, ceiling);
					shapes.add(curShape);
					++score;
				}
				else {
					curShape.moveDown();
				}
			}
			else {
				if(StdDraw.isKeyPressed(left) && curShape.outOfBounds(curShape.checkDirection(left, curShape.curCords)) == 0) {
					curShape.moveLeft();
				}
				if(StdDraw.isKeyPressed(right) && curShape.outOfBounds(curShape.checkDirection(right, curShape.curCords)) == 0) {
					curShape.moveRight();
				}
				if(StdDraw.isKeyPressed(down) && curShape.outOfBounds(curShape.checkDirection(down, curShape.curCords)) == 0) {
					curShape.moveDown();
				}
				if(StdDraw.isKeyPressed(up)) {
					curShape.rotate();
				}
			}




			StdDraw.show(50);
			time += 50;
		}
		StdDraw.setPenColor();
		StdDraw.text(0, 0, "Game Over");
		StdDraw.show();
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		runGame();
		//HashSet<int[]> set1 = new HashSet<int[]>();
		int[] arr1 = new int[] {3, 5};
		//set1.add(arr1);
		//HashSet<int[]> set2 = new HashSet<int[]>();
		int[] arr2 = new int[] {arr1[0], arr1[1]};
		//set2.add(arr1);
		System.out.print(arr2==arr1);

	}

}
