package ledmarquee;

import processing.core.PApplet;
import processing.core.PVector;

public class LEDAnimation
{
    public PVector offset;
    private PVector velocity;
    public LEDObject ledObject;
    public LEDSurface surface;
    private PApplet p;

    LEDAnimation(PApplet _p, LEDObject ledObject, PVector start, PVector velocity)
    {
        this.p = _p;
        this.offset = start;
        this.velocity = velocity;
        this.ledObject = ledObject;
    }

    public void Move()
    {
        offset.add(velocity);
        if (offset.x >= surface.xBulbs || offset.y >= surface.yBulbs)
        {
            if (velocity.x > 0.0f)
            {
                offset.x = -ledObject.getMaxX();
            }
            if (velocity.y > 0.0f)
            {
                offset.y = -ledObject.getMaxY();
            }
        }
    }
}
