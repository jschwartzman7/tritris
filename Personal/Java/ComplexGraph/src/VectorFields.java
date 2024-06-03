import edu.princeton.cs.introcs.StdDraw;
import java.lang.Math;
import java.awt.Color;
import java.awt.event.KeyEvent; 



public class VectorFields implements VisualMethod{

    static final double resolution = 0.7;
    static final double vectorLength = 0.3;
    static final double tipRadius = 0.1;
    

    public static void mapPoint(ComplexNumber z){
        ComplexNumber w = z.f();
        double mag = Math.hypot(w.x, w.y);
        if(mag != 0){
            w.x *= (vectorLength/mag);
            w.y *= (vectorLength/mag);
        }
        StdDraw.setPenColor();
        StdDraw.line(z.x, z.y, z.x+w.x, z.y+w.y);
        StdDraw.setPenColor(Color.green);
        StdDraw.filledCircle(z.x+w.x, z.y+w.y, tipRadius);

    }


    public void drawFunction(int X_MIN, int X_MAX, int Y_MIN, int Y_MAX){
        for(double x = X_MIN; x <= X_MAX+resolution; x += resolution){
            for(double y = Y_MIN; y <= Y_MAX+resolution; y += resolution){
                mapPoint(new ComplexNumber(x, y));
            }
        }
    }

   /*  public void graphFunction(){
        StdDraw.setScale(DEFAULT_XY_MIN, DEFAULT_XY_MAX);
        StdDraw.enableDoubleBuffering();
        while(true){
            StdDraw.clear();
            ComplexPlane.drawPlane();
            this.drawFunction();
            //System.out.println(Math.atan2(StdDraw.mouseY(), StdDraw.mouseX())*180/Math.PI);
            StdDraw.show(30);

        }
    }*/

    public static void main(String[] args) {
        VectorFields VF = new VectorFields();
        //VF.graphFunction();
    }
    


}