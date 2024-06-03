import java.awt.Color;
import java.util.HashMap;
import java.util.LinkedList;

//import Stuff.Tritris.Shape;
import edu.princeton.cs.introcs.StdDraw;

public class TritrisShape {

	public int type;
	public int rotation;
	public static final double sq3 = Math.sqrt(3);
	public static final int up = 38;
	public static final int right = 39;
	public static final int down = 40;
	public static final int left = 37;
	//public HashSet<int[]> curCords;
	//public HashSet<int[]> cords0, cords1, cords2, cords3, cords4, cords5;
	public LinkedList<int[]> curCords, shadow, cords0, cords1, cords2, cords3, cords4, cords5;
	//public HashMap<Integer, HashSet<int[]>> cordsMap;
	public HashMap<Integer, LinkedList<int[]>> cordsMap;
	public LinkedList<TritrisShape> shapes;
	public LinkedList<int[]> floor, ceiling;


	public TritrisShape(int shapeType, LinkedList<TritrisShape> shapes, LinkedList<int[]> floor, LinkedList<int[]> ceiling) {
		//cordsMap = new HashMap<Integer, HashSet<int[]>>();
		cordsMap = new HashMap<>();
		type = shapeType;
		rotation = 0;
		this.shapes = shapes;
		this.floor = floor;
		this.ceiling = ceiling;
		/**cords0 = new HashSet<int[]>();
		cords1 = new HashSet<int[]>();
		cords2 = new HashSet<int[]>();
		cords3 = new HashSet<int[]>();
		cords4 = new HashSet<int[]>();
		cords5 = new HashSet<int[]>();**/
		cords0 = new LinkedList<>();
		cords1 = new LinkedList<>();
		cords2 = new LinkedList<>();
		cords3 = new LinkedList<>();
		cords4 = new LinkedList<>();
		cords5 = new LinkedList<>();
		shadow = new LinkedList<>();

		if (shapeType == 0) {
			cords0.add(new int[] {1, 19});
			cords0.add(new int[] {1, 18});
			cords0.add(new int[] {1, 17});
			cords0.add(new int[] {1, 16});
			cords0.add(new int[] {2, 18});
			cords0.add(new int[] {2, 17});
			cords0.add(new int[] {2, 16});
			cords0.add(new int[] {2, 15});

			cords1.add(new int[] {-1, 17});
			cords1.add(new int[] {1, 17});
			cords1.add(new int[] {1, 18});
			cords1.add(new int[] {2, 18});
			cords1.add(new int[] {1, 16});
			cords1.add(new int[] {2, 16});
			cords1.add(new int[] {2, 17});
			cords1.add(new int[] {3, 17});

			cords2.add(new int[] {1, 15});
			cords2.add(new int[] {1, 16});
			cords2.add(new int[] {1, 17});
			cords2.add(new int[] {1, 18});
			cords2.add(new int[] {2, 16});
			cords2.add(new int[] {2, 17});
			cords2.add(new int[] {2, 18});
			cords2.add(new int[] {2, 19});

			cords3.add(new int[] {1, 19});
			cords3.add(new int[] {1, 18});
			cords3.add(new int[] {1, 17});
			cords3.add(new int[] {1, 16});
			cords3.add(new int[] {2, 18});
			cords3.add(new int[] {2, 17});
			cords3.add(new int[] {2, 16});
			cords3.add(new int[] {2, 15});

			cords4.add(new int[] {-1, 17});
			cords4.add(new int[] {1, 17});
			cords4.add(new int[] {1, 18});
			cords4.add(new int[] {2, 18});
			cords4.add(new int[] {1, 16});
			cords4.add(new int[] {2, 16});
			cords4.add(new int[] {2, 17});
			cords4.add(new int[] {3, 17});

			cords5.add(new int[] {1, 15});
			cords5.add(new int[] {1, 16});
			cords5.add(new int[] {1, 17});
			cords5.add(new int[] {1, 18});
			cords5.add(new int[] {2, 16});
			cords5.add(new int[] {2, 17});
			cords5.add(new int[] {2, 18});
			cords5.add(new int[] {2, 19});
		}
		if (shapeType == 1) {
			cords0.add(new int[] {1, 19});
			cords0.add(new int[] {1, 18});
			cords0.add(new int[] {1, 17});
			cords0.add(new int[] {1, 16});
			cords0.add(new int[] {1, 15});
			cords0.add(new int[] {2, 18});
			cords0.add(new int[] {2, 17});
			cords0.add(new int[] {2, 16});

			cords1.add(new int[] {-1, 17});
			cords1.add(new int[] {1, 18});
			cords1.add(new int[] {1, 17});
			cords1.add(new int[] {1, 16});
			cords1.add(new int[] {2, 15});
			cords1.add(new int[] {2, 18});
			cords1.add(new int[] {2, 17});
			cords1.add(new int[] {2, 16});

			cords2.add(new int[] {3, 17});
			cords2.add(new int[] {1, 18});
			cords2.add(new int[] {1, 17});
			cords2.add(new int[] {1, 16});
			cords2.add(new int[] {1, 15});
			cords2.add(new int[] {2, 18});
			cords2.add(new int[] {2, 17});
			cords2.add(new int[] {2, 16});

			cords3.add(new int[] {2, 19});
			cords3.add(new int[] {1, 18});
			cords3.add(new int[] {1, 17});
			cords3.add(new int[] {1, 16});
			cords3.add(new int[] {2, 15});
			cords3.add(new int[] {2, 18});
			cords3.add(new int[] {2, 17});
			cords3.add(new int[] {2, 16});

			cords4.add(new int[] {1, 19});
			cords4.add(new int[] {1, 18});
			cords4.add(new int[] {1, 17});
			cords4.add(new int[] {1, 16});
			cords4.add(new int[] {3, 17});
			cords4.add(new int[] {2, 18});
			cords4.add(new int[] {2, 17});
			cords4.add(new int[] {2, 16});

			cords5.add(new int[] {2, 19});
			cords5.add(new int[] {1, 18});
			cords5.add(new int[] {1, 17});
			cords5.add(new int[] {1, 16});
			cords5.add(new int[] {-1, 17});
			cords5.add(new int[] {2, 18});
			cords5.add(new int[] {2, 17});
			cords5.add(new int[] {2, 16});

		}
		if (shapeType == 2) {
			cords0.add(new int[] {1, 19});
			cords0.add(new int[] {1, 18});
			cords0.add(new int[] {1, 17});
			cords0.add(new int[] {1, 16});
			cords0.add(new int[] {1, 15});
			cords0.add(new int[] {1, 14});
			cords0.add(new int[] {1, 13});
			cords0.add(new int[] {1, 12});

			cords1.add(new int[] {-2, 18});
			cords1.add(new int[] {-1, 18});
			cords1.add(new int[] {-1, 17});
			cords1.add(new int[] {1, 17});
			cords1.add(new int[] {1, 16});
			cords1.add(new int[] {2, 16});
			cords1.add(new int[] {2, 15});
			cords1.add(new int[] {3, 15});


			cords2.add(new int[] {-2, 15});
			cords2.add(new int[] {-2, 16});
			cords2.add(new int[] {-1, 16});
			cords2.add(new int[] {-1, 17});
			cords2.add(new int[] {1, 17});
			cords2.add(new int[] {1, 18});
			cords2.add(new int[] {2, 18});
			cords2.add(new int[] {2, 19});

			cords3.add(new int[] {-1, 20});
			cords3.add(new int[] {-1, 19});
			cords3.add(new int[] {-1, 18});
			cords3.add(new int[] {-1, 17});
			cords3.add(new int[] {-1, 16});
			cords3.add(new int[] {-1, 15});
			cords3.add(new int[] {-1, 14});
			cords3.add(new int[] {-1, 13});

			cords4.add(new int[] {-3, 17});
			cords4.add(new int[] {-2, 17});
			cords4.add(new int[] {-2, 16});
			cords4.add(new int[] {-1, 16});
			cords4.add(new int[] {-1, 15});
			cords4.add(new int[] {1, 15});
			cords4.add(new int[] {1, 14});
			cords4.add(new int[] {2, 14});

			cords5.add(new int[] {-2, 13});
			cords5.add(new int[] {-2, 14});
			cords5.add(new int[] {-1, 14});
			cords5.add(new int[] {-1, 15});
			cords5.add(new int[] {1, 15});
			cords5.add(new int[] {1, 16});
			cords5.add(new int[] {2, 16});
			cords5.add(new int[] {2, 17});
		}
		if (shapeType == 3) { // start with this
			cords0.add(new int[] {1, 19});
			cords0.add(new int[] {1, 18});
			cords0.add(new int[] {1, 17});
			cords0.add(new int[] {1, 16});
			cords0.add(new int[] {1, 15});
			cords0.add(new int[] {1, 14});
			cords0.add(new int[] {2, 14});
			cords0.add(new int[] {2, 13});

			cords1.add(new int[] {-2, 16});
			cords1.add(new int[] {-1, 16});
			cords1.add(new int[] {-1, 15});
			cords1.add(new int[] {1, 15});
			cords1.add(new int[] {1, 14});
			cords1.add(new int[] {2, 14});
			cords1.add(new int[] {2, 15});
			cords1.add(new int[] {3, 15});

			cords2.add(new int[] {-1, 12});
			cords2.add(new int[] {-1, 13});
			cords2.add(new int[] {1, 13});
			cords2.add(new int[] {1, 14});
			cords2.add(new int[] {2, 14});
			cords2.add(new int[] {2, 15});
			cords2.add(new int[] {2, 16});
			cords2.add(new int[] {2, 17});

			cords3.add(new int[] {2, 11});
			cords3.add(new int[] {2, 12});
			cords3.add(new int[] {2, 13});
			cords3.add(new int[] {2, 14});
			cords3.add(new int[] {2, 15});
			cords3.add(new int[] {2, 16});
			cords3.add(new int[] {1, 16});
			cords3.add(new int[] {1, 17});

			cords4.add(new int[] {4, 14});
			cords4.add(new int[] {3, 14});
			cords4.add(new int[] {3, 15});
			cords4.add(new int[] {2, 15});
			cords4.add(new int[] {2, 16});
			cords4.add(new int[] {1, 16});
			cords4.add(new int[] {1, 15});
			cords4.add(new int[] {-1, 15});

			cords5.add(new int[] {3, 18});
			cords5.add(new int[] {3, 17});
			cords5.add(new int[] {2, 17});
			cords5.add(new int[] {2, 16});
			cords5.add(new int[] {1, 16});
			cords5.add(new int[] {1, 15});
			cords5.add(new int[] {1, 14});
			cords5.add(new int[] {1, 13});

		}
		if (shapeType == 4) {
			cords0.add(new int[] {1, 19});
			cords0.add(new int[] {1, 18});
			cords0.add(new int[] {1, 17});
			cords0.add(new int[] {1, 16});
			cords0.add(new int[] {2, 16});
			cords0.add(new int[] {2, 15});
			cords0.add(new int[] {2, 14});
			cords0.add(new int[] {2, 13});

			cords1.add(new int[] {-1, 17});
			cords1.add(new int[] {3, 17});
			cords1.add(new int[] {1, 17});
			cords1.add(new int[] {1, 16});
			cords1.add(new int[] {2, 16});
			cords1.add(new int[] {2, 17});
			cords1.add(new int[] {3, 16});
			cords1.add(new int[] {4, 16});

			cords2.add(new int[] {3, 20});
			cords2.add(new int[] {3, 19});
			cords2.add(new int[] {2, 19});
			cords2.add(new int[] {2, 18});
			cords2.add(new int[] {2, 17});
			cords2.add(new int[] {2, 16});
			cords2.add(new int[] {1, 16});
			cords2.add(new int[] {1, 15});

			cords3.add(new int[] {1, 21});
			cords3.add(new int[] {1, 20});
			cords3.add(new int[] {1, 19});
			cords3.add(new int[] {1, 18});
			cords3.add(new int[] {2, 18});
			cords3.add(new int[] {2, 17});
			cords3.add(new int[] {2, 16});
			cords3.add(new int[] {2, 15});

			cords4.add(new int[] {3, 17});
			cords4.add(new int[] {2, 17});
			cords4.add(new int[] {2, 18});
			cords4.add(new int[] {1, 18});
			cords4.add(new int[] {1, 17});
			cords4.add(new int[] {-1, 17});
			cords4.add(new int[] {-1, 18});
			cords4.add(new int[] {-2, 18});

			cords5.add(new int[] {2, 19});
			cords5.add(new int[] {2, 18});
			cords5.add(new int[] {1, 18});
			cords5.add(new int[] {1, 17});
			cords5.add(new int[] {1, 16});
			cords5.add(new int[] {1, 15});
			cords5.add(new int[] {-1, 15});
			cords5.add(new int[] {-1, 14});
		}
		if (shapeType == 5) { // reverse of first one
			cords0.add(new int[] {-1, 19});
			cords0.add(new int[] {-1, 18});
			cords0.add(new int[] {-1, 17});
			cords0.add(new int[] {-1, 16});
			cords0.add(new int[] {-1, 15});
			cords0.add(new int[] {-1, 14});
			cords0.add(new int[] {-2, 14});
			cords0.add(new int[] {-2, 13});

			cords1.add(new int[] {-3, 18});
			cords1.add(new int[] {-3, 17});
			cords1.add(new int[] {-2, 17});
			cords1.add(new int[] {-2, 16});
			cords1.add(new int[] {-1, 16});
			cords1.add(new int[] {-1, 15});
			cords1.add(new int[] {-1, 14});
			cords1.add(new int[] {-1, 13});

			cords2.add(new int[] {-4, 14});
			cords2.add(new int[] {-3, 14});
			cords2.add(new int[] {-3, 15});
			cords2.add(new int[] {-2, 15});
			cords2.add(new int[] {-2, 16});
			cords2.add(new int[] {-1, 16});
			cords2.add(new int[] {-1, 15});
			cords2.add(new int[] {1, 15});

			cords3.add(new int[] {-2, 11});
			cords3.add(new int[] {-2, 12});
			cords3.add(new int[] {-2, 13});
			cords3.add(new int[] {-2, 14});
			cords3.add(new int[] {-2, 15});
			cords3.add(new int[] {-2, 16});
			cords3.add(new int[] {-1, 16});
			cords3.add(new int[] {-1, 17});

			cords4.add(new int[] {1, 12});
			cords4.add(new int[] {1, 13});
			cords4.add(new int[] {-1, 13});
			cords4.add(new int[] {-1, 14});
			cords4.add(new int[] {-2, 14});
			cords4.add(new int[] {-2, 15});
			cords4.add(new int[] {-2, 16});
			cords4.add(new int[] {-2, 17});

			cords5.add(new int[] {2, 16});
			cords5.add(new int[] {1, 16});
			cords5.add(new int[] {1, 15});
			cords5.add(new int[] {-1, 15});
			cords5.add(new int[] {-1, 14});
			cords5.add(new int[] {-2, 14});
			cords5.add(new int[] {-2, 15});
			cords5.add(new int[] {-3, 15});
		}
		if (shapeType == 6) {
			cords0.add(new int[] {-1, 19});
			cords0.add(new int[] {-1, 18});
			cords0.add(new int[] {-1, 17});
			cords0.add(new int[] {-1, 16});
			cords0.add(new int[] {-2, 16});
			cords0.add(new int[] {-2, 15});
			cords0.add(new int[] {-2, 14});
			cords0.add(new int[] {-2, 13});

			cords1.add(new int[] {-2, 19});
			cords1.add(new int[] {-2, 18});
			cords1.add(new int[] {-1, 18});
			cords1.add(new int[] {-1, 17});
			cords1.add(new int[] {-1, 16});
			cords1.add(new int[] {-1, 15});
			cords1.add(new int[] {1, 15});
			cords1.add(new int[] {1, 14});

			cords2.add(new int[] {-3, 17});
			cords2.add(new int[] {-2, 17});
			cords2.add(new int[] {-2, 18});
			cords2.add(new int[] {-1, 18});
			cords2.add(new int[] {-1, 17});
			cords2.add(new int[] {1, 17});
			cords2.add(new int[] {1, 18});
			cords2.add(new int[] {2, 18});

			cords3.add(new int[] {-1, 21});
			cords3.add(new int[] {-1, 20});
			cords3.add(new int[] {-1, 19});
			cords3.add(new int[] {-1, 18});
			cords3.add(new int[] {-2, 18});
			cords3.add(new int[] {-2, 17});
			cords3.add(new int[] {-2, 16});
			cords3.add(new int[] {-2, 15});

			cords4.add(new int[] {-3, 20});
			cords4.add(new int[] {-3, 19});
			cords4.add(new int[] {-2, 19});
			cords4.add(new int[] {-2, 18});
			cords4.add(new int[] {-2, 17});
			cords4.add(new int[] {-2, 16});
			cords4.add(new int[] {-1, 16});
			cords4.add(new int[] {-1, 15});

			cords5.add(new int[] {1, 17});
			cords5.add(new int[] {-3, 17});
			cords5.add(new int[] {-1, 17});
			cords5.add(new int[] {-1, 16});
			cords5.add(new int[] {-2, 16});
			cords5.add(new int[] {-2, 17});
			cords5.add(new int[] {-3, 16});
			cords5.add(new int[] {-4, 16});

		}
		if (shapeType == 7) {
			cords0.add(new int[] {-1, 19});
			cords0.add(new int[] {-1, 18});
			cords0.add(new int[] {-1, 17});
			cords0.add(new int[] {-1, 16});
			cords0.add(new int[] {-1, 15});
			cords0.add(new int[] {-1, 14});
			cords0.add(new int[] {-1, 13});
			cords0.add(new int[] {-1, 12});

			cords5.add(new int[] {2, 18});
			cords5.add(new int[] {1, 18});
			cords5.add(new int[] {1, 17});
			cords5.add(new int[] {-1, 17});
			cords5.add(new int[] {-1, 16});
			cords5.add(new int[] {-2, 16});
			cords5.add(new int[] {-2, 15});
			cords5.add(new int[] {-3, 15});


			cords4.add(new int[] {2, 15});
			cords4.add(new int[] {2, 16});
			cords4.add(new int[] {1, 16});
			cords4.add(new int[] {1, 17});
			cords4.add(new int[] {-1, 17});
			cords4.add(new int[] {-1, 18});
			cords4.add(new int[] {-2, 18});
			cords4.add(new int[] {-2, 19});

			cords3.add(new int[] {1, 20});
			cords3.add(new int[] {1, 19});
			cords3.add(new int[] {1, 18});
			cords3.add(new int[] {1, 17});
			cords3.add(new int[] {1, 16});
			cords3.add(new int[] {1, 15});
			cords3.add(new int[] {1, 14});
			cords3.add(new int[] {1, 13});

			cords2.add(new int[] {3, 17});
			cords2.add(new int[] {2, 17});
			cords2.add(new int[] {2, 16});
			cords2.add(new int[] {1, 16});
			cords2.add(new int[] {1, 15});
			cords2.add(new int[] {-1, 15});
			cords2.add(new int[] {-1, 14});
			cords2.add(new int[] {-2, 14});

			cords1.add(new int[] {2, 13});
			cords1.add(new int[] {2, 14});
			cords1.add(new int[] {1, 14});
			cords1.add(new int[] {1, 15});
			cords1.add(new int[] {-1, 15});
			cords1.add(new int[] {-1, 16});
			cords1.add(new int[] {-2, 16});
			cords1.add(new int[] {-2, 17});

		}

		curCords = cords0;
		cordsMap.put(0,  cords0);
		cordsMap.put(1,  cords1);
		cordsMap.put(2,  cords2);
		cordsMap.put(3,  cords3);
		cordsMap.put(4,  cords4);
		cordsMap.put(5,  cords5);
	}

	public void transformLeft(int[] cord) {
		if(cord[0] == 1) {
			cord[0] = -1;
		}
		else {
			cord[0] -= 1;
		}
		cord[1] -= 1;
	}

	public void transformRight(int[] cord) {
		if(cord[0] == -1) {
			cord[0] = 1;
		}
		else {
			cord[0] += 1;
		}
		cord[1] -= 1;
	}

	public void transformDown(int[] cord) {
		cord[1] -= 2;
	}

	public void moveLeft() {
		for(int i = 0; i < 6; ++i) {
			for(int[] cord: cordsMap.get(i)) {
				transformLeft(cord);
			}
		}
	}

	public void moveRight() {
		for(int i = 0; i < 6; ++i) {
			for(int[] cord: cordsMap.get(i)) {
				transformRight(cord);
			}
		}
	}

	public void moveDown() {
		for(int i = 0; i < 6; ++i) {
			for(int[] cord : cordsMap.get(i)) {
				transformDown(cord);
			}
		}
	}

	public void moveUp() {
		for(int i = 0; i < 6; ++i) {
			for(int[] cord : cordsMap.get(i)) {
				cord[1] += 2;
			}
		}
	}

	public HashMap<Integer, LinkedList<int[]>> copy(HashMap<Integer, LinkedList<int[]>> map){
		HashMap<Integer, LinkedList<int[]>> copy = new HashMap<>();
		for(Integer i : map.keySet()) {
			LinkedList<int[]> cordsCopy = new LinkedList<>();
			for(int[] cord : map.get(i)) {
				cordsCopy.add(new int[] {cord[0], cord[1]});
			}
			copy.put(i, cordsCopy);
		}
		return copy;
	}



	public void rotate() {
		//rotate while staying in bounds, but if this new in bounds rotation position overlaps a shape, revert to old position
		//copy(cordsMap);
		HashMap<Integer, LinkedList<int[]>> cordsMapCopy = copy(cordsMap);
		int prevRotation = rotation;
		rotation = (rotation + 1) % 6;
		curCords = cordsMap.get(rotation);
		//LinkedList<Integer> moves = new LinkedList<int[]>();
		/**for(int[] cord : curCords) {
			int x = cord[0];
			int y = cord[1];

			tempCords.add(new int[] {x, y});
		}**/

		while (outOfBounds(curCords) == right) {
			moveLeft();
		}
		while(outOfBounds(curCords) == left) {
			moveRight();
		}
		while(outOfBounds(curCords) == up) {
			moveDown();
		}
		while(outOfBounds(curCords) == down) {
			moveUp();
		}
		if (outOfBounds(curCords) == -1) {
			cordsMap = cordsMapCopy;
			rotation = prevRotation;
			curCords = cordsMap.get(rotation);
		}
	//	rotation = prevRotation
	//	curCords = cordsMap.get(rotation)
	}




	public LinkedList<int[]> checkDirection(int direction, LinkedList<int[]> cords) {
		LinkedList<int[]> tempCords = new LinkedList<>();
		for(int[] cord : cords) {
			//int x = cord[0];
			//int y = cord[1];
			int[] tempCord = new int[] {cord[0], cord[1]};
			if(direction == left){
					transformLeft(tempCord);
			}
			if(direction == right){
					transformRight(tempCord);
			}
			if(direction == down){
					transformDown(tempCord);
			}
			tempCords.add(tempCord);
		}
		return tempCords;

	}

	public int outOfBounds(LinkedList<int[]> cords) {
		for(int[] cord : cords) {
			if (cord[0] > 5) {
				//System.out.println("right");
				return right;
			}
			if (cord[0] < -5) {
				//System.out.println("left");
				return left;

			}
			for(int[] ceilingCord : ceiling) {
				if(cord[0] == ceilingCord[0] && cord[1] - ceilingCord[1] > 1) {
					//System.out.println("up");
					return up;
				}
			}
			for(int[] floorCord : floor) {
				if(cord[0] == floorCord[0] && cord[1] - floorCord[1] < -1) {
					//System.out.println("down");
					return down;
				}
			}
			for(TritrisShape thatShape : shapes) {
				if(thatShape != this) {
					for(int[] thatCord : thatShape.curCords) {
						if(cord[0] == thatCord[0] && cord[1] == thatCord[1]) {
							return -1;
						}
					}
				}
			}
		}
		return 0;
	}


	public boolean touching(LinkedList<int[]> shapeCords, LinkedList<int[]> object) {
		for (int[] shapeCord : shapeCords) {
			for(int[] objectCord: object) {
				if (shapeCord[0] == objectCord[0] && shapeCord[1] == objectCord[1]) {
					return true;
				}
			}
		}
		return false;

	}

	public void removeCord(int[] cord) {
		for(int i = 0; i < curCords.size(); ++i) {
			int[] curCord = curCords.get(i);
			if(cord[0] == curCord[0] && cord[1] == curCord[1]){
				curCords.remove(i);
			}
		}
	}

	public static void drawCord(int[] cord, boolean shadow) {
		double[] triangleX = new double[3];
		double[] triangleY = new double[3];
		if(cord[0] > 0 && (cord[0] + cord[1]) % 2 == 0) { // triangle facing right and positive x
			triangleX[0] = (cord[0]-1)*0.5*sq3;
			triangleX[1] = (cord[0]-1)*0.5*sq3;
			triangleX[2] = cord[0]*0.5*sq3;

			triangleY[0] = (cord[1]/2.0) - 0.5;
			triangleY[1] = (cord[1]/2.0)  + 0.5;
			triangleY[2] = (cord[1]/2.0);
		}
		else if(cord[0] < 0 && (cord[0] + cord[1]) % 2 != 0) { // triangle facing right and negative x
			triangleX[0] = cord[0]*0.5*sq3;
			triangleX[1] = cord[0]*0.5*sq3;
			triangleX[2] = (cord[0]+1)*0.5*sq3;

			triangleY[0] = (cord[1]/2.0) - 0.5;
			triangleY[1] = (cord[1]/2.0) + 0.5;
			triangleY[2] = (cord[1]/2.0);
		}
		else if(cord[0] > 0) { // triangle facing left and positive x
			triangleX[0] = (cord[0]-1)*0.5*sq3;
			triangleX[1] = cord[0]*0.5*sq3;
			triangleX[2] = cord[0]*0.5*sq3;

			triangleY[0] = (cord[1]/2.0);
			triangleY[1] = (cord[1]/2.0) + 0.5;
			triangleY[2] = (cord[1]/2.0) - 0.5;
		}
		else if(cord[0] < 0) { // triangle facing left and negative x
			triangleX[0] = cord[0]*0.5*sq3;
			triangleX[1] = (cord[0]+1)*0.5*sq3;
			triangleX[2] = (cord[0]+1)*0.5*sq3;

			triangleY[0] = (cord[1]/2.0);
			triangleY[1] = (cord[1]/2.0) + 0.5;
			triangleY[2] = (cord[1]/2.0) - 0.5;
		}
		if(shadow) {
			StdDraw.polygon(triangleX, triangleY);
		}
		else {
			StdDraw.filledPolygon(triangleX, triangleY);
		}


	}


	public void drawShape() {
		if(type == 0) { // rhombus
			//StdDraw.setPenColor(Color.RED);}
			StdDraw.setPenColor(76, 49, 217);
		}
		if(type == 1) { // trapezoid
			//StdDraw.setPenColor(Color.ORANGE);
			StdDraw.setPenColor(33, 34, 238);
		}
		if(type == 2) { // straight
			StdDraw.setPenColor(Color.YELLOW);
			StdDraw.setPenColor(65, 162, 179);}
		if(type == 7) { // black straight
			StdDraw.setPenColor(Color.BLACK);
			StdDraw.setPenColor(144, 187, 87);}
		if(type == 3) { // L facing right
			StdDraw.setPenColor(Color.GREEN);
			StdDraw.setPenColor(203, 74, 67);}
		if(type == 5) { // L facing right
			StdDraw.setPenColor(Color.PINK);
			StdDraw.setPenColor(169, 57, 155);}
		if(type == 4) { // jagged L facing left
			StdDraw.setPenColor(Color.BLUE);
			StdDraw.setPenColor(209, 179, 15);}
		if(type == 6) { // jagged L facing left
			StdDraw.setPenColor(Color.CYAN);
			StdDraw.setPenColor(209, 123, 63);}


		//shadow.clear();

		for(int[] cord: curCords) {
			drawCord(cord, false);
			//shadow.add(new int[] {cord[0], cord[1]});
		}

		/**while(outOfBounds(checkDirection(down, shadow)) != 0) {
			for(int[] cord : shadow) {
				transformDown(cord);
			}

		}
		for(int[] cord : shadow) {
			drawCord(cord);
		}**/

		/**LinkedList<int[]> shadow = new LinkedList<int[]>();

		for(int[] cord: curCords) {
			drawCord(cord);
			shadow.add(cord);
		}

		while(!(touchingFloor() || aboveShape())) {
			for(int[] cord : shadow) {
				cord[1] -= 2;
			}

		}
		for(int[] cord : shadow) {
			drawCord(cord);
		}**/
	}

	public void drawShadow() {

		//StdDraw.setPenColor(15, 30, 37); background color


		if(type == 0) { // rhombus
			//StdDraw.setPenColor(Color.RED);}
			StdDraw.setPenColor(76, 49, 217);}
		if(type == 1) { // trapezoid
			//StdDraw.setPenColor(Color.ORANGE);
			StdDraw.setPenColor(33, 34, 238);}
		if(type == 2) { // straight
			//StdDraw.setPenColor(Color.YELLOW);
			StdDraw.setPenColor(65, 162, 179);}
		if(type == 7) { // black straight
			//StdDraw.setPenColor(Color.BLACK);
			StdDraw.setPenColor(144, 187, 87);}
		if(type == 3) { // L facing right
			//StdDraw.setPenColor(Color.GREEN);
			StdDraw.setPenColor(203, 74, 67);}
		if(type == 5) { // L facing right
			//StdDraw.setPenColor(Color.PINK);
			StdDraw.setPenColor(169, 57, 155);}
		if(type == 4) { // jagged L facing left
			//StdDraw.setPenColor(Color.BLUE);
			StdDraw.setPenColor(209, 179, 15);}
		if(type == 6) { // jagged L facing left
			//StdDraw.setPenColor(Color.CYAN);
			StdDraw.setPenColor(209, 123, 63);}


		shadow.clear();
		for(int[] cord : curCords) {
			shadow.add(new int[] {cord[0], cord[1]});
		}
		while(outOfBounds(checkDirection(down, shadow)) == 0) {
			for(int[] cord : shadow) {
				transformDown(cord);
			}
		}
		for(int[] cord : shadow) {
			drawCord(cord, true);
		}


	}






	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LinkedList<int[]> firstList = new LinkedList<>();
		firstList.add(new int[] {1, 2});
		firstList.add(new int[] {2, 3});
		firstList.add(new int[] {3, 2});
		LinkedList<int[]> secondList = firstList;
		for(int[] cord : firstList) {
			//System.out.println(cord[0]);
		}
		for (int[] element : secondList) {
			element[0] -= 1;
		}
		for(int[] cord : firstList) {
			//System.out.println(cord[0]);
		}
	}

}
