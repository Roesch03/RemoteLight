/*-
 * >===license-start
 * RemoteLight
 * ===
 * Copyright (C) 2019 - 2020 Lars O.
 * ===
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * <===license-end
 */

package de.lars.remotelightcore.animation.animations;

import de.lars.remotelightcore.utils.color.Color;

import de.lars.remotelightcore.RemoteLightCore;
import de.lars.remotelightcore.animation.Animation;
import de.lars.remotelightcore.out.OutputManager;
import de.lars.remotelightcore.utils.color.PixelColorUtils;
import de.lars.remotelightcore.utils.color.RainbowWheel;
import de.lars.remotelightcore.utils.maths.TimeUtil;

public class Jump extends Animation {
	
	private TimeUtil time;

	public Jump() {
		super("Jump");
	}
	
	@Override
	public void onEnable() {
		time = new TimeUtil(RemoteLightCore.getInstance().getAnimationManager().getDelay()*3);
		super.onEnable();
	}
	
	@Override
	public void onLoop() {
		time.setInterval(RemoteLightCore.getInstance().getAnimationManager().getDelay()*3);
		
		if(time.hasReached()) {
			Color color = RainbowWheel.getRandomColor();
			OutputManager.addToOutput(PixelColorUtils.colorAllPixels(color, RemoteLightCore.getLedNum()));
		}
		super.onLoop();
	}

}
