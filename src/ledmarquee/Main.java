package ledmarquee;
import processing.core.*;

public class Main extends PApplet
{
	//Global
	int bgColor=0;
	Bulb bulb;
	Marquee marquee;
	MarqueeText marqueeText;
	
	public void setup()
	{
	  size(500, 500);
	  frameRate(4);
	  bulb = new Bulb(this, 10);
	  marquee = new Marquee(this, 0, 0, width, height, bulb, 2);
	  
	  marqueeText = new MarqueeText(this);
	  marqueeText.setText();
	  marquee.addText(marqueeText);
	  
	}

	public void draw()
	{
		background(bgColor);
		marquee.update(); 
		
	}

	public static void main(String _args[])
	{
		PApplet.main(new String[] { ledmarquee.Main.class.getName() });
	}
}











