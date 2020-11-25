package de.lars.remotelightcore.musicsync.modes;

import javax.swing.plaf.synth.SynthOptionPaneUI;

import de.lars.remotelightcore.musicsync.MusicEffect;

public class Flickering extends MusicEffect {

	
	double avgLoudnes =0;
	double counter =0;
	double actLaut =0;
	double avgAmplitudes=1;
	int avgAmplitudesIndex;
	double avgCounterAmplitudes=0;

	
	public Flickering() {
		super("Flickerig");
	}
	

//	@Override
//	public void onEnable() {
//		avgAmplitudes = 1.0;
//		super.onEnable();
//	}
	
	
	@Override
	public void onLoop() {
		float[] amplitudes = getSoundProcessor().getAmplitudes();
		if(counter >= Double.MAX_VALUE-1) {
			counter = 1.0;
		}
	
		counter++;
		for(int i = 0; i < amplitudes.length; i++) {
			actLaut+=amplitudes[i];
			avgCounterAmplitudes += i*amplitudes[i];
			
		}
		
		avgAmplitudes=(avgAmplitudes+(avgCounterAmplitudes/actLaut))/(2);
		avgLoudnes = (getVolume() + counter * avgLoudnes)/(counter+1);

		System.out.print(avgAmplitudes + " \t | " + counter + " \t " + avgLoudnes + " \t | " + avgCounterAmplitudes + "\n");
		super.onLoop();
	}

}
