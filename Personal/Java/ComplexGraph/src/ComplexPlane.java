import edu.princeton.cs.introcs.StdDraw;
import java.lang.Math;
import java.awt.Color;
import java.awt.event.KeyEvent;

/* Coloring Scheme
    Color gets whiter as radius decreases
    Color gets darker as radius increases
    Hue depends on angle


 */


public class ComplexPlane {

    static final int DEFAULT_XY_MIN = -10;
    static final int DEFAULT_XY_MAX = 10;
    static int X_MIN = DEFAULT_XY_MIN;
    static int X_MAX = DEFAULT_XY_MAX;
    static int Y_MIN = DEFAULT_XY_MIN;
    static int Y_MAX = DEFAULT_XY_MAX;
    static int labelInterval = 10;
    //static VisualMethod VM = new DomainColor();
    static VisualMethod VM = new DomainColor();


// ymin = -8 + -8mod10 = -8 + 2
/*
 * if 10 | ceil(x) 
 * ceil(x) + 10-ceil(x)%10
 * 
 * 
 */

    public static int mod(int a, int m){
        m = Math.abs(m); // Z/m, m positive
        if(a > 0){
            return a % m;
        }
        else{
            return m + a%m;
        }

    }


    public static void drawAxis(){
        StdDraw.setPenColor();
        StdDraw.line(X_MIN, 0, X_MAX, 0);
        StdDraw.line(0, Y_MIN, 0, Y_MAX);
        for(int x = ((X_MIN)-mod(X_MIN, 10)); x <= X_MAX; x+=labelInterval){
            if(x != 0){
                StdDraw.text(x, 0, (int)(x)+"");
            }
        }
        for(int y = ((Y_MIN)-mod((Y_MIN), 10)); y <= Y_MAX; y+=labelInterval){
            if(y != 0){
                StdDraw.text(0, y, (int)(y)+"");
            }
        }

    }

    public static boolean updateDimensions(){

        // translate along x axis
        if(StdDraw.isKeyPressed(KeyEvent.VK_D)){
            X_MIN += 1;
            X_MAX += 1;
        }
        else if (StdDraw.isKeyPressed(KeyEvent.VK_A)){
            X_MIN -= 1;
            X_MAX -= 1;
        }

        // translate along y axis
        if(StdDraw.isKeyPressed(KeyEvent.VK_W)){
            Y_MIN += 1;
            Y_MAX += 1;
        }
        else if (StdDraw.isKeyPressed(KeyEvent.VK_S)){
            Y_MIN -= 1;
            Y_MAX -= 1;
        }
        // zoom in / zoom out
        if(StdDraw.isKeyPressed(KeyEvent.VK_Q)){
            if(Y_MAX - Y_MIN < 3){
                System.out.println("Max Y zoom reached");
                return false;
            }
            if(X_MAX - X_MIN < 3){
                System.out.println("Max X zoom reached");
                return false;
            }
            Y_MIN += 1;
            Y_MAX -= 1;
            X_MIN += 1;
            X_MAX -= 1;
        }
        else if (StdDraw.isKeyPressed(KeyEvent.VK_E)){
            Y_MIN -= 1;
            Y_MAX += 1;
            X_MIN -= 1;
            X_MAX += 1;
        }

        // y axis zoom in / zoom out
        if (StdDraw.isKeyPressed(KeyEvent.VK_UP)){
            Y_MIN -= 1;
            Y_MAX += 1;
        }
        else if(StdDraw.isKeyPressed(KeyEvent.VK_DOWN)){
            if(Y_MAX - Y_MIN < 3){
                System.out.println("Max Y zoom reached");
                return false;
            }
            Y_MIN += 1;
            Y_MAX -= 1;

        }

        // x axis zoom in / zoom out
        if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)){
            X_MIN -= 1;
            X_MAX += 1;
        }
        else if(StdDraw.isKeyPressed(KeyEvent.VK_LEFT)){
            if(X_MAX - X_MIN < 3){
                System.out.println("Max X zoom reached");
                return false;
            }
            X_MIN += 1;
            X_MAX -= 1;

        }

        if (StdDraw.isKeyPressed(KeyEvent.VK_R)){
            X_MIN = DEFAULT_XY_MIN;
            X_MAX = DEFAULT_XY_MAX;
            Y_MIN = DEFAULT_XY_MIN;
            Y_MAX = DEFAULT_XY_MAX;
        }
        return true;

    }

    public static void drawPlane(){
        updateDimensions();
        StdDraw.setXscale(X_MIN, X_MAX);
        StdDraw.setYscale(Y_MIN, Y_MAX);
        drawAxis();
    }


    public static void graph(){
        StdDraw.setScale(DEFAULT_XY_MIN, DEFAULT_XY_MAX);
        StdDraw.enableDoubleBuffering();
        while(true){
            StdDraw.clear();
            drawPlane();
            VM.drawFunction(X_MIN, X_MAX, Y_MIN, Y_MAX);
            //System.out.println(Math.atan2(StdDraw.mouseY(), StdDraw.mouseX())*180/Math.PI);
            StdDraw.show(30);

        }
    }

    public static void main(String[] args) {
        graph();
    }
    
}
 