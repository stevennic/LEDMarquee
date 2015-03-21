package ledmarquee;

import java.util.ArrayList;
import java.util.Random;


public class LEDObject
{
    ArrayList<Vector> onOffArray;

    //Bounds
    private float minX = Float.MAX_VALUE;
    private float minY = Float.MAX_VALUE;
    private float maxX = -1.0f;
    private float maxY = -1.0f;
    private boolean boundsInitialized = false;

    LEDObject()
    {
        onOffArray = new ArrayList<Vector>();
    }

    public void testSet()
    {
        Random rnd = new Random();
        for (int row = 0; row < 5; row++)
        {
            for (int col = 0; col < 5; col++)
            {
                if (rnd.nextBoolean())
                    onOffArray.add(new Vector(row, col));
            }
        }
    }

    private void initializeBounds()
    {
        for (Vector v : onOffArray)
        {
            if (v.x < minX)
            {
                minX = v.x;
            }
            if (v.x > maxX)
            {
                maxX = v.x;
            }
            if (v.y < minY)
            {
                minY = v.y;
            }
            if (v.y > maxY)
            {
                maxY = v.y;
            }
        }
        boundsInitialized = true;
    }

    public float getMinX()
    {
        if (!boundsInitialized)
        {
            initializeBounds();
        }
        return minX;
    }

    public float getMinY()
    {
        if (!boundsInitialized)
        {
            initializeBounds();
        }
        return minY;
    }

    public float getMaxX()
    {
        if (!boundsInitialized)
        {
            initializeBounds();
        }
        return maxX;
    }

    public float getMaxY()
    {
        if (!boundsInitialized)
        {
            initializeBounds();
        }
        return maxY;
    }

}
