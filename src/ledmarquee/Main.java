package ledmarquee;
import processing.core.*;
public class Main extends PApplet
{
	//Global
	int bgColor=0;
	Marquee m;
	Bulb bulb;
	PFont font; 
	int textBufferWidth;
	int textBufferHeight;

	public void setup()
	{
	  size(500, 200);
	  
	  font = createFont("Times New Roman", 20);
	  bulb = new Bulb(this, 8);
	  m    = new Marquee(this, 0, 0, width, height * 2, bulb, font, 1);
	  
	}

	public void draw()
	{
		background(bgColor);
		m.update(); 
		
	}

	public static void main(String _args[])
	{
		PApplet.main(new String[] { ledmarquee.Main.class.getName() });
	}
}











