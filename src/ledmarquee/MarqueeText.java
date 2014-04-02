package ledmarquee;
import processing.core.*;

public class MarqueeText {
	private PApplet p;
	private String fontName; 
	private int fontSize;
	int x, y;
	private String text; 
	PGraphics textBuffer;
	int textBufferWidth;
	int textBufferHeight;
	private PFont font;
		
	MarqueeText(PApplet parent)
	{
		this.p = parent;
		x = 10;
		y = 10;
	}
	
	void setText()
	{
		font = p.createFont("Times New Roman", 20);
		textBufferHeight = p.height;
		textBufferWidth = p.width * 2;
		textBuffer = p.createGraphics(textBufferWidth,textBufferHeight); //Buffer to hold text
		p.stroke(255);

		textBuffer.beginDraw();
		textBuffer.textFont(font);
		textBuffer.textAlign(p.LEFT,p.TOP);
		textBuffer.fill(255);
		textBuffer.text("Hello",0,0);
		textBuffer.endDraw();
		p.imageMode(p.CORNER); 
		
	}

}
