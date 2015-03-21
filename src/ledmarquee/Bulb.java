package ledmarquee;

import processing.core.PApplet;
import processing.core.PImage;

public class Bulb
{
    float x, y, size;
    int ColorOn = 0;
    int ColorOff = 255;
    PApplet p;
    public static PImage bulbOn;
    public static PImage bulbOff;
    int fadeOutTime;
    private static int height, width;
    boolean on = false;

    //Setting the bulb to start
    Bulb(PApplet parent) //, PImage bulbOn, PImage bulbOff)
    {
        this.p = parent;
        on = false;
        /*
        this.bulbOn = bulbOn;
		this.bulbOff = bulbOff;
		*/
    }

	/*
    Bulb()//Bulb bulb)
	{

		bulbOn = bulb.bulbOn;
		bulbOff = bulb.bulbOff;
	}
	*/

    public static void SetImages(PImage _bulbOn, PImage _bulbOff)
    {
        bulbOn = _bulbOn;
        bulbOff = _bulbOff;
        width = bulbOn.width;
        height = bulbOn.height;
    }

    public static int getWidth()
    {
        return width;
    }

    public static int getHeight()
    {
        return height;
    }

    public void setLocation(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public void TurnOn()
    {
        on = true;
    }

    public void TurnOff()
    {
        on = false;
    }

    public void Render()
    {
        if (on)
        {
            //Render bulbOn
            p.image(bulbOn, this.x, this.y);
        }
        else
        {
            //Render bulbOff
            p.image(bulbOff, this.x, this.y);
        }
    }
}