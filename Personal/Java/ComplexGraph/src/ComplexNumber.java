import edu.princeton.cs.introcs.StdDraw;
import java.lang.Math;
import java.awt.event.KeyEvent;


public class ComplexNumber {

    public static final ComplexNumber i = new ComplexNumber(0, 1);
    public static final ComplexNumber negi = new ComplexNumber(0, -1);
    public double x;
    public double y;
    public double modulus;
    public double arg;

    public ComplexNumber(double x, double y){
        this.x = x;
        this.y = y;
        this.modulus = Math.hypot(x, y);
        this.arg = Math.atan2(y, x);
    }

    public ComplexNumber f(){
        return this.multiplyBy(this).divideBy(exp());

    }

    public ComplexNumber conjugate(){
        return new ComplexNumber(this.x, -this.y);
    }

    public ComplexNumber add(ComplexNumber w){
        return new ComplexNumber(this.x+w.x, this.y+w.y);
    }
    public ComplexNumber subtract(ComplexNumber w){
        return new ComplexNumber(this.x-w.x, this.y-w.y);
    }
    public ComplexNumber multiplyBy(ComplexNumber w){
        return new ComplexNumber(this.x*w.x-this.y*w.y, this.x*w.y+this.y*w.x);
    }
    public ComplexNumber divideBy(ComplexNumber w){
        if(w.x != 0 || w.y != 0){
            double denominator = w.x*w.x + w.y*w.y;
            return new ComplexNumber((this.x*w.x+this.y*w.y)/denominator, (this.y*w.x-this.x*w.y)/denominator);
        }
        System.out.println("divide by 0");
        return null;
        
    }
    
    public ComplexNumber exp(){
        return new ComplexNumber(Math.exp(this.x)*Math.cos(this.y), Math.exp(this.x)*Math.sin(this.y));
    }

    /*public ComplexNumber raiseTo(ComplexNumber w){
        return new ComplexNumber(Math, y)
    }*/
    public static void main(String[] args) {
        System.out.println(ComplexPlane.mod(-36, 13));
    }
}
