package ledmarquee;

import processing.core.PApplet;
import processing.core.PFont;

public class LEDTextObject extends LEDObject
{
	Pixelate pixelate; 
	
	LEDTextObject(PApplet p, String text, String fontname, int size)
	{
		PFont font  = p.createFont(fontname, size);
		pixelate = new Pixelate(p);
		boolean [][] array = pixelate.textToBooleanArray(font, text, 1);
		for(int row = 0; row < array.length; row++) {
			for(int col = 0; col < array[0].length; col++)
			{
				if(array[row][col])
					onOffArray.add(new Vector(row, col));
			}
		}
	}
}
