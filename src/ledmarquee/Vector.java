package ledmarquee;

public class Vector {
	float x, y;
	float magnitude;
	
	Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	Vector(double x, double y) {
		this.x = (float)x;
		this.y = (float)y;
	}
	
	void add(Vector vector) {
		x = x + vector.x;
		y = y + vector.y;
	}
}
