import edu.princeton.cs.introcs.StdDraw;

public class JuliaSets{
	public static int n = 800;
	public static int[][] iterationsArray = new int[n][n];
	public static double[] zn = new double[] {0, 0};
	public static final double[] cords = new double[] {-0.7269, 0.1889};
	public static final double xMinDefault = -1.7;
	public static final double xMaxDefault = 1.7;
	public static double xMin =xMinDefault;
	public static double xMax = xMaxDefault;
	public static double xRange = xMax - xMin;
	public static final double yMinDefault = -1.7;
	public static final double yMaxDefault = 1.7;
	public static double yMin = yMinDefault;
	public static double yMax = yMaxDefault;
	public static double yRange = yMax - yMin;
	public static int maxIterations = 200;

	public static double absValCmplxNum(double[] num) {
		return Math.hypot(num[0], num[1]);
	}

	public static double[] juliaSetEqtn(double[] zn) {
		double realPart = zn[0];
		double imagPart = zn[1];
		zn[0] = realPart*realPart-imagPart*imagPart + cords[0];
		zn[1] = 2*realPart*imagPart + cords[1];
		return zn;
		}


	public static int isInSet(double[] zn, int iterations) {
		if(iterations >= maxIterations){ // in set
			return -1;
		}
		else if(absValCmplxNum(zn) > 2) { // not in set
			return iterations;
		}
		else {
			return isInSet(juliaSetEqtn(zn), iterations + 1);
		}
	}

	public static void defaultScale() {
		xMin = xMinDefault;
		xMax = xMaxDefault;
		yMin = yMinDefault;
		yMax = yMaxDefault;
		StdDraw.setXscale(xMinDefault, xMaxDefault);
		StdDraw.setYscale(yMinDefault, yMaxDefault);
	}

	public static void changeScale(double xMinNew, double xMaxNew, double yMinNew, double yMaxNew) {
		xMin = xMinNew;
		xMax = xMaxNew;
		xRange = xMaxNew - xMinNew;
		yMin = yMinNew;
		yMax = yMaxNew;
		yRange = yMaxNew - yMinNew;
		StdDraw.setXscale(xMin, xMax);
		StdDraw.setYscale(yMin, yMax);
	}



	public static void generateBoard() {
		//this method is where the speeding up will occur if possible
		int iterationsMin = Integer.MAX_VALUE;
		for(int i = 0; i < n; ++i) {
			for(int j = 0; j < n; ++j) {
				zn[0] = xMin + (xMax-xMin)/(2*n) + j*(xMax-xMin)/n;
				zn[1] = yMax - (yMax-yMin)/(2*n) - i*(yMax-yMin)/n;
				iterationsArray[i][j] = isInSet(zn, 0);
				if(iterationsArray[i][j] == -1) {
					StdDraw.setPenColor();
				}
				else {
					if(iterationsArray[i][j] < iterationsMin) {
						iterationsMin = iterationsArray[i][j];
					}
					StdDraw.setPenColor(0, (int)(((1.0*(iterationsArray[i][j]-iterationsMin))/(maxIterations - 1 - iterationsMin))*255), 70);//use color range for each window
				}
				StdDraw.point(xMin + (xMax-xMin)/(2*n) + j*(xMax-xMin)/n, yMax - (yMax-yMin)/(2*n) - i*(yMax-yMin)/n);
			}
		}
	}


	public static void juliaSet() {
		StdDraw.enableDoubleBuffering();
	//	StdDraw.setPenRadius();
	//	StdDraw.setPenRadius(0.004);
		defaultScale();
		generateBoard();
/*		StdDraw.setPenColor(Color.BLUE);
		StdDraw.filledCircle(1, 0, 0.02);
		StdDraw.filledCircle(0, 0, 0.02);
		StdDraw.setPenColor();
		StdDraw.line(-2, 0, 2, 0);
		StdDraw.line(0, -2, 0, 2);
		StdDraw.text(1, 0.13, "(1, 0)");
		StdDraw.setPenColor(Color.BLUE);
		StdDraw.line(1, -.08, 1, .08);
		StdDraw.filledCircle(0, 0, 0.02);
		StdDraw.line(1, -.08, 1, .08);
		StdDraw.line(-.08, 1, .08, 1);
		StdDraw.line(-1, -.08, -1, .08);
		StdDraw.line(-.08, -1, .08, -1);*/
		StdDraw.show();
		while(true) {

			if(StdDraw.mousePressed()) {
				double mouseX = StdDraw.mouseX();
				double mouseY = StdDraw.mouseY();
				System.out.println(xRange);
				System.out.println(yRange);
				changeScale(mouseX-.1*xRange, mouseX+.1*xRange, mouseY-.1*yRange, mouseY+.1*yRange);
				StdDraw.clear();
				generateBoard();
			}
			if(StdDraw.isKeyPressed(32)) {
				defaultScale();
				StdDraw.clear();
				generateBoard();
			}
			StdDraw.show();
		}
	}


	public static void main(String[] args) {
		juliaSet();
		// TODO Auto-generated method stub

	}

}

