package ledmarquee;

import processing.core.*;

public class Marquee
{
	
	PApplet p;
	private int marqueeWidth; 
	private int marqueeHeight; 
	private int bulbSize; 
	private int rowOfBulbs;
	private int colOfBulbs;
	private Bulb [][] marqueeGrid;
	private int bulbSeparation;
	int x, y;
	private PGraphics backgroundBuffer;
	private Bulb bulb;
	private MarqueeText marqueeText;
	private int bufferOffsetX = 0;
	private int bufferOffsetY = 0;
	
	Marquee(PApplet parent,
			int	x, int y,
			int marqueeWidth,
			int marqueeHeight,
			Bulb bulb,
			int bulbSeparation)
	{
		p = parent;
		this.x = x;
		this.y = y;
		this.bulbSize = bulb.size;
		this.marqueeWidth = marqueeWidth;
		this.marqueeHeight = marqueeHeight;
		this.bulbSeparation = bulbSeparation;
		this.bulb = bulb;
		this.bufferOffsetX = 0;
		this.bufferOffsetY = 0;
		
		createMarqueeBackground(); 
		createMarqueeArray(); 
	}
	
	private void createMarqueeBackground() {
		backgroundBuffer = p.createGraphics(marqueeWidth, marqueeHeight); 
		backgroundBuffer.beginDraw();
		for(int i = 0; i < marqueeWidth; i = i + bulbSize + bulbSeparation)
		{
			for(int j = 0; j < marqueeWidth; j = j + bulbSize + bulbSeparation)
			{
				backgroundBuffer.ellipse(i, j, bulbSize, bulbSize);
			}
		}
		backgroundBuffer.endDraw();	
	}
	
	private void createMarqueeArray() {
		rowOfBulbs = (int)Math.floor(marqueeHeight / (bulbSize + bulbSeparation));
		colOfBulbs = (int)Math.floor(marqueeWidth  / (bulbSize + bulbSeparation));
		
		marqueeGrid = new Bulb[colOfBulbs][rowOfBulbs];
		for(int x1 = 0; x1 < colOfBulbs; x1++)
		{
		    for (int y1 = 0; y1 < rowOfBulbs; y1++)
		    {
		      marqueeGrid[x1][y1] = new Bulb(bulb, convertToCoord(x1) + x, convertToCoord(y1) + y);
		    }
		}
	}
	
	public void addText(MarqueeText marqueeText) {
		this.marqueeText = marqueeText;
	}
	
	private int convertToCoord(int var)
	{
		return var * (bulbSize + bulbSeparation) + bulbSize; 
	}
	
	int convertToIndex(int x, int y, int w)
	{
	  return x + (y * w); //Map 2 dimensions to 1-D array
	} 
	
	
	public void drawBackground() {
		p.image(backgroundBuffer, x, y); 
	}
	
	
	public void update()
	{
		drawBackground();
		if(marqueeText != null) {
			for (int x = 0; x < colOfBulbs; x++)
			{
				for (int y = 0;y< rowOfBulbs; y++)
				{
					int xy = convertToIndex(x + bufferOffsetX, y + bufferOffsetY, marqueeText.textBufferWidth); 
					
					//Return to beginning when scrolling is complete
					if(bufferOffsetX > marqueeText.textBufferWidth / bulbSize)
					{
						bufferOffsetX = 0;
					}
	
					//Determine on/off. Ignore any buffer pixels that would fall outside the viewable area
					if (xy < marqueeText.textBuffer.pixels.length && marqueeText.textBuffer.pixels[xy] == 0)
					{
						marqueeGrid[x][y].drawOn();
					}
					else
					{
						marqueeGrid[x][y].drawOff();
					}
				}
			}
			bufferOffsetX += bulbSize; 
		}
	}
	
}