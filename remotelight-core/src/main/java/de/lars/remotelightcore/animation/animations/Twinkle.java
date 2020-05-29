/*******************************************************************************
 * ______                     _       _     _       _     _   
 * | ___ \                   | |     | |   (_)     | |   | |  
 * | |_/ /___ _ __ ___   ___ | |_ ___| |    _  __ _| |__ | |_ 
 * |    // _ \ '_ ` _ \ / _ \| __/ _ \ |   | |/ _` | '_ \| __|
 * | |\ \  __/ | | | | | (_) | ||  __/ |___| | (_| | | | | |_ 
 * \_| \_\___|_| |_| |_|\___/ \__\___\_____/_|\__, |_| |_|\__|
 *                                             __/ |          
 *                                            |___/           
 * 
 * Copyright (C) 2019 Lars O.
 * 
 * This file is part of RemoteLight.
 ******************************************************************************/
package de.lars.remotelightcore.animation.animations;

import java.awt.Color;
import java.util.Random;

import de.lars.remotelightcore.RemoteLightCore;
import de.lars.remotelightcore.animation.Animation;
import de.lars.remotelightcore.animation.AnimationManager;
import de.lars.remotelightcore.out.OutputManager;
import de.lars.remotelightcore.settings.SettingsManager.SettingCategory;
import de.lars.remotelightcore.settings.types.SettingColor;
import de.lars.remotelightcore.utils.color.PixelColorUtils;
import de.lars.remotelightcore.utils.maths.TimeUtil;

public class Twinkle extends Animation {
	
	private AnimationManager am;
	private int max;
	private Color color;
	private TimeUtil time;

	public Twinkle() {
		super("Twinkle");
		this.addSetting(new SettingColor("animation.twinkle.color", "Color", SettingCategory.Intern,	null, new Color(255, 240, 255)));
	}
	
	@Override
	public void onEnable() {
		am = RemoteLightCore.getInstance().getAnimationManager();
		max = RemoteLightCore.getLedNum() / 10;
		time = new TimeUtil(am.getDelay());
		super.onEnable();
	}
	
	@Override
	public void onLoop() {
		if(time.hasReached()) {
			OutputManager.addToOutput(PixelColorUtils.colorAllPixels(Color.BLACK, RemoteLightCore.getLedNum()));
			color = ((SettingColor) getSetting("animation.twinkle.color")).getValue();
			
			for(int i = 0; i <= max; i++) {
				if(new Random().nextInt(3) == 0) {
					PixelColorUtils.setPixel(new Random().nextInt(RemoteLightCore.getLedNum()), color);
				}
			}
			
			int rnd = (am.getDelay() * 2) + new Random().nextInt(50);
			time.setInterval(rnd);
		}
		
		super.onLoop();
	}

}
