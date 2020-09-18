package de.lars.remotelightcore.utils.color.palette;

import java.awt.Color;

import de.lars.remotelightcore.utils.color.ColorUtil;

public class EvenGradientPalette extends ColorPalette {
	
	public final static float DEFAULT_STEPSIZE = 0.1f;
	
	protected float stepSize;
	protected float currentStep;
	protected int targetIndex;
	
	public EvenGradientPalette(float stepSize, Color... colors) {
		this.stepSize = stepSize;
		add(colors);
	}
	
	/**
	 * Fade to the next color in the defined step size and get the color.
	 * Resets index to 0 when the last color is reached.
	 * @return		next color of the palette
	 */
	@Override
	public Color getNext() {
		if(listColor.size() == 0)
			throw new IllegalStateException("Could not return next item. The list is empty!");
		if(curIndex == targetIndex && size() > 1)
			increaseTargetIndex(1);
		Color c = ColorUtil.fadeToColor(get(curIndex), get(targetIndex), currentStep);
		currentStep += stepSize; // increase step position by stepSize
		if(currentStep > 1.0f) {
			currentStep = 0.0f;
			curIndex = targetIndex;
			increaseTargetIndex(1);
		}
		return c;
	}
	
	protected void increaseTargetIndex(int amount) {
		targetIndex += amount;
		if(targetIndex >= size())
			targetIndex -= size();
	}
	
	@Override
	public void skip(int indices) {
		super.skip(indices);
		increaseTargetIndex(indices);
	}
	
	public static EvenGradientPalette fromColorPalette(ColorPalette palette, float stepSize) {
		EvenGradientPalette gp = new EvenGradientPalette(stepSize);
		gp.listColor = palette.listColor;
		return gp;
	}

}
