package ledmarquee;
import processing.core.*;

public class Main extends PApplet
{
	//Global
	int bgColor=0;
	Bulb bulb;
	LEDSurface ledSurface;
	LEDObject ledObject;
	LEDAnimation ledAnimation;
	
	public void setup()
	{
	  size(500, 500);
	  
	  bulb = new Bulb(this, loadImage("on.png"), loadImage("off.png"));
	  ledObject = new LEDObject();
	  ledAnimation = new LEDAnimation(ledObject, new Vector(0, 0), new Vector(0.1, 0.1));
	  ledSurface = new LEDSurface(this, bulb, new Vector(100, 100), 20, 20);
	  ledSurface.addAnimation(ledAnimation);
	  
	}

	public void draw()
	{
		background(255);
		ledSurface.drawSurface();
	}

	public static void main(String _args[])
	{
		PApplet.main(new String[] { ledmarquee.Main.class.getName() });
	}
}











