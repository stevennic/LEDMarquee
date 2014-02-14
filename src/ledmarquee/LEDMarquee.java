package ledmarquee;
import processing.core.*;
public class LEDMarquee extends PApplet {
	//Global
	int centerX, centerY;
	int bgColor=0;
	PImage s;
	Marquee m;
	PFont f;
	PGraphics textBuffer;
	int textBufferWidth;
	int textBufferHeight;

	public void setup()
	{
	  size(500, 200);
	  textBufferHeight=height;
	  textBufferWidth=width*2;
	  textBuffer=createGraphics(textBufferWidth,textBufferHeight); //Buffer to hold text
	  centerX=width/2;
	  centerY=height/2			  ;
	  stroke(255);
	  m=new Marquee(8);
	  
	  f=loadFont("Consolas-48.vlw");
	  
	  //Initialize Text Buffer
	  textBuffer.beginDraw();
	  //Render bounding box for debugging
	  //textBuffer.stroke(128);
	  //textBuffer.noFill();
	  //textBuffer.rect(0,0,400,400);
	  textBuffer.textFont(f,128);
	  textBuffer.textAlign(LEFT,TOP);
	  textBuffer.fill(255);
	  textBuffer.text("Testing",0,0);
	  textBuffer.endDraw();
	  imageMode(CORNER); //Only needed if rendering the text directly for debugging
	  
	}

	int xy(int x, int y, int w)
	{
	  return x+(y*w); //Map 2 dimensions to 1-D array
	}

	PVector Vxy(int xy, int w)
	{
	  return new PVector(xy % width, xy / width); //Map 1-D index to Vector
	}

	public void draw()
	{
	  background(bgColor);
	  m.draw();
	  //image(textBuffer,0,0);

	  //Test Light
	  //  shapeMode(CENTER);
	  //  fill(255, 255, 0);
	  //  noStroke();
	  //  pushMatrix();
	  //  translate(100, centerY);
	  //  directionalLight(255, 255, 255, 0, 0, -1);
	  //  sphere(50);
	  //  popMatrix();
	}

	class Bulb
	{  
	  //PShape bulbOn, bulbOff;
	  int x, y, size;
	  int ColorOn=0;
	  int ColorOff=255;
	  Bulb(int s, int _x, int _y)
	  {
	
	    x=_x;
	    y=_y;
	    size=s;
	    //bulbOn=createShape(ELLIPSE, 0, 0, size, size);
	    //bulbOn.setFill(ColorOn);
	    //bulbOff=createShape(ELLIPSE, 0, 0, size, size);
	    //bulbOff.setFill(ColorOff);
	  }

	  public void draw()
	  {
		  //drawOff();
		  int xy;
		  textBuffer.loadPixels();
		  
		  //Return to beginning when scrolling is complete
		  if(m.BufferOffsetX==textBufferWidth)
		  {
			  m.BufferOffsetX=0;
		  }
		  
		  //int AverageBrightness=0;
		  //Compact way to build all permutations needed to scan all 8 neighbouring pixels + the centre one
		  //for (int NeighbourX=-1;NeighbourX<=1;NeighbourX++)
		  //{
		//	  for (int NeighbourY=-1;NeighbourY<=1;NeighbourY++)
			//  {
				  xy=xy(x+m.BufferOffsetX,y+m.BufferOffsetY,textBufferWidth); //Get coinciding buffer pixel
		//		  if (xy<textBuffer.pixels.length && xy>=0)
		//		  {
		//			  AverageBrightness+=textBuffer.pixels[xy]; //Sum the brightness values
		//		  }
		//	  }
		 // }
		  
		  //Determine on/off. Ignore any buffer pixels that would fall outside the viewable area
		  if (xy<textBuffer.pixels.length && textBuffer.pixels[xy]==0)
		  //if (AverageBrightness > 0)
		  {
			  drawOn();
		  }
		  else
		  {
			  drawOff();
		  }
	  }
	  
	  private void drawOn()
	  {
	    pushMatrix();
	    translate(x, y);
	    fill(ColorOn);
	    //shape(bulbOn, 0, 0);
	    ellipse(0,0,size,size);
	    popMatrix();
	  }

	  private void drawOff()
	  {
	    pushMatrix();
	    translate(x, y);
	    fill(ColorOff);
	    //shape(bulbOff, 0, 0);
	    ellipse(0,0,size,size);
	    popMatrix();
	  }
	}

	class Marquee
	{
	  int bulbSize;
	  int radius;
	  int separation=1; //number of pixels of separation
	  int rows, columns;
	  int totalBulbSpace;
	  public int BufferOffsetX=0;
	  public int BufferOffsetY=0;
	  Bulb[][] marqueeGrid;
	  PVector v;

	  Marquee(int bs)
	  {
	    bulbSize=bs;
	    totalBulbSpace=2*separation+bulbSize;
	    //radius=bulbSize/2;

	    //println("bulbSize:"+bulbSize, "separation:"+separation, "height:"+height, "equation:"+height/(bulbSize+2*separation));
	    rows=height/(totalBulbSpace);
	    //println("rows:"+rows);
	    columns=width/(totalBulbSpace);
	    //println("cols:"+columns);

	    //Initialize Marquee
	    marqueeGrid=new Bulb[columns][rows];
	    for (int x=0;x<columns;x++)
	    {
	      for (int y=0;y<rows;y++)
	      {
	        //println("c:"+x, "r:"+y, "x:"+cTox(x),"y:"+rToy(y));
	        marqueeGrid[x][y]=new Bulb(bulbSize, cTox(x), rToy(y));
	      }
	    }

	    imageMode(CENTER);
	  }
	  int rToy(int row)
	  {
		//Convert Marquee Row to y coordinate 
	    return row*totalBulbSpace + bulbSize;//+radius;
	  }
	  int cTox(int col)
	  {
		//Convert Marquee Column to x coordinate
	    //println("col:"+col, "totalBulbSpace:"+totalBulbSpace, "radius:"+radius);
	    return col*totalBulbSpace + bulbSize;//+radius;
	  }
	  	  
	  void draw()
	  {
	    //pushMatrix();
	    for (int x=0;x<columns;x++)
	    {
	      for (int y=0;y<rows;y++)
	      {
	        //println("x:"+x, "y:"+y);
	    	marqueeGrid[x][y].draw();
	      }
	    }
	    BufferOffsetX+=bulbSize; //Scroll
	    //popMatrix();
	  }
	}

	/*
	public void mouseMoved()
	{
	  int x=mouseX;
	  int y=mouseY;
	  //int xy=x+(y*width);
	 // println(x, y);
	}
	*/

	public static void main(String _args[]) {
		PApplet.main(new String[] { ledmarquee.LEDMarquee.class.getName() });
	}
}
