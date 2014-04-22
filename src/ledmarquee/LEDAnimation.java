package ledmarquee;

public class LEDAnimation {
	Vector location;
	Vector velocity; 
	LEDObject ledObject; 
	
	LEDAnimation(LEDObject ledObject, Vector start, Vector velocity) {
		this.location = start;
		this.velocity = velocity;
		this.ledObject = ledObject;  
		
	}
	
	void update() {
		for(Vector ledVector : ledObject.onOffArray) {
			ledVector.add(velocity);
		}
	}
}
