package ledmarquee;
import processing.core.*;
public class LEDMarquee extends PApplet {
	//Global
	int centerX, centerY;
	int bgColor=255;
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
	  centerY=height/2;
	  m=new Marquee(5);
	  
	  f=loadFont("Consolas-48.vlw");
	  //textFont(f,48);
	  
	  textBuffer.beginDraw();

	  //Render bounding box for debugging
	  //textBuffer.stroke(128);
	  //textBuffer.noFill();
	  //textBuffer.rect(0,0,400,400);
	  textBuffer.textFont(f,128);
	  textBuffer.textAlign(LEFT,TOP);
	  textBuffer.fill(0);
	  textBuffer.text("Testing",0,0);
	  textBuffer.endDraw();
	  imageMode(CORNER);

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
		  drawOff();
		  if(m.BufferOffsetX==textBufferWidth)
		  {
			  m.BufferOffsetX=0;
		  }
		  int xy=xy(x+m.BufferOffsetX,y+m.BufferOffsetY,textBufferWidth);
		  textBuffer.loadPixels();

		  if (xy<textBuffer.pixels.length && textBuffer.pixels[xy]==0)
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
	    //pushStyle();
	    pushMatrix();
	    translate(x, y);
	    fill(ColorOn);
	    //shape(bulbOn, 0, 0);
	    ellipse(0,0,size,size);
	    popMatrix();
	    //popStyle();
	  }

	  private void drawOff()
	  {
	    //pushStyle();
	    pushMatrix();
	    translate(x, y);
	    fill(ColorOff);
	    //println("x:"+x, "y:"+y);
	    //shape(bulbOff, 0, 0);
	    ellipse(0,0,size,size);
	    //text(" ", 1, 1); //@#$%@#$@ Why NPE???
	    popMatrix();
	    //popStyle();
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
	  
	  /*
	  public int BufferOffsetX()
	  {
		  return 0;
	  
	  }
	  public int BufferOffsetY()
	  {
		  return 0;
	  }
	  */
	  
	  void draw()
	  {
	    //pushMatrix();
	    for (int x=0;x<columns;x++)
	    {
	      for (int y=0;y<rows;y++)
	      {
	        //println("x:"+x, "y:"+y);
	    	//marqueeGrid[x][y].drawOff();
	    	//v=Vxy
	    	  //if (textBuffer.pixels[])

	    	//TODO: Key question: Given a pixel x,y where the Font exists, which bulb should light?
	    	  //Need conversion function from x to bulb X in grid
	    	//Solution: Make text buffer available to bulbs. When they render, they check if they coincide with the textBuffer's location and locally decide to turn on or off
	    	  
	    	marqueeGrid[x][y].draw();
	    	
	    	/*
	        if (noise(x+y) >= 0.5)
	        {
	          marqueeGrid[x][y].drawOff();
	        }
	        else
	        {
	          marqueeGrid[x][y].drawOn();
	        }
	        */
	      }
	    }
	    BufferOffsetX+=bulbSize; //Scroll
	    //popMatrix();
	  }
	}

	public void mouseMoved()
	{
	  int x=mouseX;
	  int y=mouseY;
	  //int xy=x+(y*width);
	 // println(x, y);
	}

	public static void main(String _args[]) {
		PApplet.main(new String[] { ledmarquee.LEDMarquee.class.getName() });
	}
}
