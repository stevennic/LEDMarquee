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
	private int bulbSeparation;
	private Bulb [][] marqueeGrid;
	private int bufferOffsetX;
	private int bufferOffsetY;
	
	private PGraphics textBuffer;
	private int textBufferWidth;
	private int textBufferHeight;
	PFont font;
	int x, y;
	
	Marquee(PApplet parent,
			int		x,
			int		y,
			int     marqueeWidth,
			int     marqueeHeight,
			Bulb    bulb,
			PFont   font,
			int separation)
	{
		p = parent;
		this.x 				= x;
		this.y				= y;
		this.bulbSize       = bulb.size;
		this.bulbSeparation = separation;
		this.marqueeWidth     = marqueeWidth;
		this.marqueeHeight    = marqueeHeight;
		this.font 			  = font;
		
		bufferOffsetX = 0;
		bufferOffsetY = 0;
		
		rowOfBulbs = (int)Math.floor(marqueeHeight / (bulbSize + bulbSeparation));
		colOfBulbs = (int)Math.floor(marqueeWidth  / (bulbSize + bulbSeparation));
		
		
		////From MAIN
		textBufferHeight = p.height;
		textBufferWidth  = p.width * 2;
		textBuffer=p.createGraphics(textBufferWidth,textBufferHeight); //Buffer to hold text
		p.stroke(255);

		//Initialize Text Buffer
		textBuffer.beginDraw();
		textBuffer.textFont(font);
		textBuffer.textAlign(p.LEFT,p.TOP);
		textBuffer.fill(255);
		textBuffer.text("Testing",0,0);
		textBuffer.endDraw();
		p.imageMode(p.CORNER); //Only needed if rendering the text directly for debugging
		
		marqueeGrid = new Bulb[colOfBulbs][rowOfBulbs];
		for(int x1 = 0; x1 < colOfBulbs; x1++)
		{
		    for (int y1 = 0; y1 < rowOfBulbs; y1++)
		    {
		      marqueeGrid[x1][y1] = new Bulb(bulb, convertToCoord(x1) + x, convertToCoord(y1) + y);
		    }
		}
		
		p.imageMode(p.CENTER);
	
	}
	
	
	//Constructor
	///////////////////////////////////////////////////////////////////////////////
	/*Marquee(PApplet parent, 
			int marqueeWidth,
			int marqueeHeight,
			int bulbSize,
			int bulbSeparation)
	{
		
		p = parent;
		
		this.bulbSize       = bulbSize;
		this.bulbSeparation = bulbSeparation;
		this.marqueeWidth     = marqueeWidth;
		this.marqueeHeight    = marqueeHeight;
				
		bufferOffsetX = 0;
		bufferOffsetY = 0;
		
		rowOfBulbs = (int)Math.floor(marqueeHeight / (bulbSize + bulbSeparation));
		colOfBulbs = (int)Math.floor(marqueeWidth  / (bulbSize + bulbSeparation));
		marqueeGrid = new Bulb[colOfBulbs][rowOfBulbs];
		
		////From MAIN
		textBufferHeight = p.height;
		textBufferWidth  = p.width*2;
		textBuffer=p.createGraphics(textBufferWidth,textBufferHeight); //Buffer to hold text
		p.stroke(255);

		//f=p.loadFont("Consolas-48.vlw");
		f = p.createFont("Times New Roman", 20); //Just to see if this would work here.
		
		//Initialize Text Buffer
		textBuffer.beginDraw();
		textBuffer.textFont(f);
		textBuffer.textAlign(p.LEFT,p.TOP);
		textBuffer.fill(255);
		textBuffer.text("Testing",0,0);
		textBuffer.endDraw();
		p.imageMode(p.CORNER); //Only needed if rendering the text directly for debugging
		
		for(int x = 0; x < colOfBulbs; x++)
		{
		    for (int y = 0; y < rowOfBulbs; y++)
		    {
		      marqueeGrid[x][y] = new Bulb(p, bulbSize, convertToCoord(x), convertToCoord(y));
		    }
		}
		
		p.imageMode(p.CENTER);
	}*/
	
	
	private void initializeBuffer()
	{
		textBufferHeight = p.height;
		textBufferWidth  = p.width * 2;
		textBuffer       = p.createGraphics(textBufferWidth,textBufferHeight); //Buffer to hold text
	}
	
	private int convertToCoord(int var)
	{
		return var * (bulbSize + bulbSeparation) + bulbSize; 
	}
	
	int convertToIndex(int x, int y, int w)
	{
	  return x + (y * w); //Map 2 dimensions to 1-D array
	} 
	
	public void update()
	{
		
		for (int x = 0; x < colOfBulbs; x++) {
			for (int y = 0;y< rowOfBulbs; y++) {
				int xy = convertToIndex(x + bufferOffsetX, 
										y + bufferOffsetY,
									    textBufferWidth); //Get coinciding buffer pixel
				
				
				//textBuffer.loadPixels(); <--- This was causing the slowdown

				//Return to beginning when scrolling is complete
				if(bufferOffsetX > textBufferWidth / bulbSize)
				{
					bufferOffsetX = 0;
				}

				//Determine on/off. Ignore any buffer pixels that would fall outside the viewable area
				if (xy < textBuffer.pixels.length && textBuffer.pixels[xy] == 0)
					//if (AverageBrightness > 0)
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