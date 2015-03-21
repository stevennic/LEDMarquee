package ledmarquee;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class LEDSurface
{
    ArrayList<LEDAnimation> ledAnimations;
    PVector origin; //Relative position of LEDSurface on canvas
    Bulb[][] bulbs;
    int xBulbs, yBulbs; //Number of bulb columns and rows
    PApplet p;

    LEDSurface(PApplet p, PVector location)
    {
        this.p = p;
        this.origin = location;
        this.xBulbs = p.width / Bulb.getWidth();
        this.yBulbs = p.height / Bulb.getHeight();
        ledAnimations = new ArrayList<LEDAnimation>();
        buildSurface(xBulbs, yBulbs);
    }

    void buildSurface(int xBulbs, int yBulbs)
    {
        bulbs = new Bulb[xBulbs][yBulbs];
        float xLoc, yLoc;
        for (int row = 0; row < yBulbs; row++)
        {
            for (int col = 0; col < xBulbs; col++)
            {
                //Calculate bulb's physical location
                xLoc = (col * Bulb.getWidth());
                yLoc = (row * Bulb.getHeight());
                Bulb b = new Bulb(p);
                b.setLocation(xLoc, yLoc);
                bulbs[col][row] = b;
            }
        }
    }

    void drawSurface()
    {
        //Turn all bulbs off
        for (Bulb[] bulbRows : bulbs)
        {
            for (Bulb bulb : bulbRows)
                bulb.TurnOff();
        }

        //Turn selective bulbs on
        for (LEDAnimation animation : ledAnimations)
        {
            animation.Move();

            for (Vector v : animation.ledObject.onOffArray)
            {
                int x = (int) (origin.x + v.x + animation.offset.x);
                int y = (int) (origin.y + v.y + animation.offset.y);
                if (x < xBulbs && x >= 0 && y < yBulbs && y >= 0)
                {
                    bulbs[x][y].TurnOn();
                }
            }
        }

        //Render all bulbs
        for (Bulb[] bulbRows : bulbs)
        {
            for (Bulb bulb : bulbRows)
                bulb.Render();
        }
    }

    boolean onSurface(PVector vector)
    {
        if (vector.x >= 0 && vector.x < xBulbs)
            if (vector.y >= 0 && vector.y < yBulbs)
                return true;
        return false;
    }

    void addAnimation(LEDAnimation animation)
    {
        animation.surface = this;
        ledAnimations.add(animation);
    }
}
