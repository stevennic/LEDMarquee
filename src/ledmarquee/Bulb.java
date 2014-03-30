package ledmarquee;

import processing.core.*;

public class Bulb
{
	int x, y, size;
	int ColorOn=0;
	int ColorOff=255;
	PApplet p;
	PGraphics bulbGraphicOn;
	PGraphics bulbGraphicOff;
	int fadeOutTime; 
	
	//Copy constructor for the single bulb used by the
	//Marquee to place on the 
	Bulb(Bulb copy, int x, int y) {
		this.p    = copy.p;
		this.size = copy.size;
		this.x    = x;
		this.y    = y;
	}
	
	//Setting the bulb to start
	Bulb(PApplet parent, int size)
	{
		this.p    = parent;
		this.size = size;
		//Implement bulbGraphics, fade time
	}

	public void drawOn()
	{
		p.pushMatrix();
		p.translate(x, y);
		p.fill(ColorOn);
		p.ellipse(0,0,size,size);
		p.popMatrix();
	}

	public void drawOff()
	{
		p.pushMatrix();
		p.translate(x, y);
		p.fill(ColorOff);
		p.ellipse(0,0,size,size);
		p.popMatrix();
	}
}



 
