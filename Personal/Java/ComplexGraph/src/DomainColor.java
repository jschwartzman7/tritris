import edu.princeton.cs.introcs.StdDraw;
import java.lang.Math;
import java.awt.Color;
import java.awt.event.KeyEvent;

/* Coloring Scheme
    Color gets whiter as radius decreases
    Color gets darker as radius increases
    Hue depends on angle


 */


public class DomainColor implements VisualMethod{

    static final double resolution = 0.3;
    static final double pointRadius = 0.15;
  

    static double sigmoid(double x, double a, double k){
        return a/(1+Math.exp(k*x));
    }

    static double getColor(double angle, double magnitude, int colorCode){
        double brightnessWeight = sigmoid(magnitude, 2, .5);
        double damping = Math.exp(-.05*magnitude);
        switch(colorCode){
            case 1:
                return damping*(brightnessWeight*sigmoid(magnitude, 2, .1)+(1-brightnessWeight)*Math.pow(Math.sin(angle/2-Math.PI/6), 4));
            case 2:
                return damping*(brightnessWeight*sigmoid(magnitude, 2, .1)+(1-brightnessWeight)*Math.pow(Math.sin(angle/2+Math.PI/6), 4));
            default:
                return damping*(brightnessWeight*sigmoid(magnitude, 2, .1)+(1-brightnessWeight)*Math.pow(Math.sin(angle/2+Math.PI/2), 4));
        }
    }

    static void mapPoint(ComplexNumber z){
        // Brightness variables in [0, 1]
        //
        ComplexNumber w = z.f();
        //double[] functionValue = ComplexOperations.add(new double[]{x, y}, new double[]{0, 0});
        double mappedX = w.x;
        double mappedY = w.y;
        double magnitude = Math.hypot(mappedX, mappedY); // mag = 13
        double angle = Math.atan2(mappedY, mappedX);
        double redStrength = getColor(angle, magnitude, 1);
        double greenStrength = getColor(angle, magnitude, 2);
        double blueStrength = getColor(angle, magnitude, 3);
        
      
        StdDraw.setPenColor((int)(255*redStrength), (int)(255*greenStrength), (int)(255*blueStrength));
        //StdDraw.filledCircle(x, y, pointRadius);
        StdDraw.filledSquare(z.x, z.y, pointRadius);

    }

    public void drawFunction(int X_MIN, int X_MAX, int Y_MIN, int Y_MAX){
        for(double x = X_MIN; x <= X_MAX+resolution; x += resolution){
            for(double y = Y_MIN; y <= Y_MAX+resolution; y += resolution){
                mapPoint(new ComplexNumber(x, y));
            }
        }
    }

    /*public void graphFunction(){
        StdDraw.setScale(DEFAULT_XY_MIN, DEFAULT_XY_MAX);
        StdDraw.enableDoubleBuffering();
        while(true){
            StdDraw.clear();
            ComplexPlane.drawPlane();
            drawFunction();
            StdDraw.show(30);
        }
    }*/

    public static void main(String[] args) {
        DomainColor DC = new DomainColor();
        //DC.graphFunction();
    }
    
}
