package ledmarquee;

import processing.core.PApplet;
import processing.core.PVector;

public class Main extends PApplet
{
    //Global
    //Bulb bulb;
    LEDSurface ledSurface;
    LEDObject ledObject;
    LEDAnimation ledAnimation1, ledAnimation2;
    LEDObject ledTextObject;

    public void setup()
    {
        size(1000, 500);
        //bulb = new Bulb(this);

        Bulb.SetImages(loadImage("on2.png"), loadImage("off2.png"));
        ledSurface = new LEDSurface(this, new PVector(0, 0));

        ledObject = new LEDObject();
        ledObject.testSet();
        ledAnimation1 = new LEDAnimation(this, ledObject, new PVector(0, 0), new PVector(0.1f, 0.1f));
        ledSurface.addAnimation(ledAnimation1);

        ledTextObject = new LEDTextObject(this, "Here", "Verdana", 12);
        ledAnimation2 = new LEDAnimation(this, ledTextObject, new PVector(0, 0), new PVector(0.1f, 0.0f));
        ledSurface.addAnimation(ledAnimation2);
    }

    public void draw()
    {
        background(255);
        ledSurface.drawSurface();
    }

    public static void main(String _args[])
    {
        PApplet.main(new String[]{ledmarquee.Main.class.getName()});
    }
}