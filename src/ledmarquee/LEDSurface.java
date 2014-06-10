package ledmarquee;
import java.util.ArrayList;

import processing.core.PApplet;

public class LEDSurface
{
	ArrayList<LEDAnimation> ledAnimations; 
	Vector location;
	Bulb [][] bulbs;
	int width, height; 
	PApplet p; 
	
	LEDSurface(PApplet p, Bulb bulb, Vector location, int width, int height)
	{
		this.p = p;
		this.location = location;
		this.width = width;
		this.height = height;
		ledAnimations = new ArrayList<LEDAnimation>();
		buildSurface(bulb, width, height); 
	}
	
	void buildSurface(Bulb bulb, int width, int height)
	{
		bulbs = new Bulb[width][height];
		float xLoc, yLoc; 
		for(int row = 0; row < height; row++)
		{
			for(int col = 0; col < width; col++)
			{
				xLoc = (col * bulb.width);
				yLoc = (row * bulb.height);
				bulbs[col][row] = new Bulb(bulb);
				bulbs[col][row].setLocation(xLoc, yLoc);
			}
		}
	}
	
	void drawSurface()
	{
		drawBackground(); 
		drawAnimations();
	}
	
	void drawBackground()
	{
		for(int row = 0; row < height; row++)
		{
			for(int col = 0; col < width; col++)
				p.image(bulbs[col][row].bulbOff, bulbs[col][row].x + location.x, bulbs[col][row].y + location.y);
		}
	}
	
	void drawAnimations()
	{
		int x, y; 
		for(LEDAnimation animation : ledAnimations)
		{
			animation.update();
			
			for(Vector ledVector : animation.ledObject.onOffArray)
			{
				if(onSurface(ledVector))
				{
					x = (int)ledVector.x;
					y = (int)ledVector.y;
					p.image(bulbs[x][y].bulbOn, bulbs[x][y].x + location.x, bulbs[x][y].y + location.y);
				}
					
			}
		}
	}
	
	boolean onSurface(Vector vector)
	{
		if(vector.x >= 0 && vector.x < width)
			if(vector.y >= 0 && vector.y < height)
				return true;
		return false;
	}
	
	void addAnimation(LEDAnimation animation)
	{
		ledAnimations.add(animation);
	}
	
}
