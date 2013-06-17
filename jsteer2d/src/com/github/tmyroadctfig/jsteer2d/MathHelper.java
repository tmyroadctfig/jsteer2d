package com.github.tmyroadctfig.jsteer2d;

/**
 * Math helper.
 */
public class MathHelper
{
    public static float clamp(float value, float min, float max)
    {
        return value > max ? max : (value < min ? min : value);
    }
}
