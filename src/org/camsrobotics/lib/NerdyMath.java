package org.camsrobotics.lib;

/**
 * Math class with added functionality
 * 
 * @author Wesley
 *
 */
public class NerdyMath {
    public static double radsToDeg(double deg)	{
    	// Native algorithm
    	return deg * 360 / (2 * Math.PI);
    }
    
    public static double degToRads(double rads)	{
    	// Native algorithm
    	return rads * (2 * Math.PI) / 360;
    }
    
    /**
     * Thresholds the value between a upper and lower limit
     * 
     * @param val	The value
     * @param min	The lower limit
     * @param max	The upper limit
     * @return		The thresholded value
     */
    public static double threshold(double val, double min, double max)	{
    	return Math.min(max, Math.max(min, val));
    }
    
    /**
     * Normalizes the array to -1 or 1
     * 
     * @param values	The values
     * @param scaleUp	True makes the values scale no matter what
     * @return			The normalized values
     */
    public static double[] normalize(double[] values, boolean scaleUp){
        double[] normalizedValues = new double[values.length];
        double max = Math.max(Math.abs(values[0]), Math.abs(values[1]));
        for(int i = 2; i < values.length; i++){
            max = Math.max(Math.abs(values[i]), max);
        }
        if(max < 1 && scaleUp == false) {
            for(int i = 0; i < values.length; i++){
                normalizedValues[i] = values[i];
            }
        }   else    {
            for(int i = 0; i < values.length; i++){
                normalizedValues[i] = values[i] / max;
            }
        }
        
        return normalizedValues;
    }
}
