package ledmarquee;

import java.util.ArrayList;
import java.util.Random;

public class LEDObject {
	ArrayList<Vector> onOffArray;
	
	LEDObject() {
		onOffArray = new ArrayList<Vector>();
		
	}
	
	void testSet() {
		Random rnd = new Random();
		for(int row = 0; row < 5; row++) {
			for(int col = 0; col < 5; col++) {
				if(rnd.nextBoolean())
					onOffArray.add(new Vector(row, col));
			}
		}
	}
	
}
