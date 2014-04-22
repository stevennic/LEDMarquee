package ledmarquee;
import processing.core.*;

public class Bulb
{
	float x, y, size;
	int ColorOn = 0;
	int ColorOff = 255;
	PApplet p;
	PImage bulbOn;
	PImage bulbOff;
	int fadeOutTime; 
	int height, width;
	
	//Setting the bulb to start
	Bulb(PApplet parent, PImage bulbOn, PImage bulbOff)
	{
		this.p = parent;
		this.bulbOn = bulbOn;
		this.bulbOff = bulbOff;
		width = bulbOn.width;
		height = bulbOn.height;
	}

	Bulb(Bulb bulb) {
		bulbOn = bulb.bulbOn;
		bulbOff = bulb.bulbOff;
	}

	void setLocation(float x, float y) {
		this.x = x;
		this.y = y;
	}
	

}



 
