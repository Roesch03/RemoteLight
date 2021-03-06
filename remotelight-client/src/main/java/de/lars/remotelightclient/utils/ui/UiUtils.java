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

package de.lars.remotelightclient.utils.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.Locale;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.plaf.FontUIResource;

import org.tinylog.Logger;

import de.lars.remotelightclient.Main;
import de.lars.remotelightclient.ui.Style;
import de.lars.remotelightcore.notification.NotificationType;
import de.lars.remotelightcore.utils.DirectoryUtil;
import jiconfont.swing.IconFontSwing;

public class UiUtils {
	
	private static boolean disableTheming = true;
	private static int defaultFontSize = 11;
	
	public static void setThemingEnabled(boolean themingEnabled) {
		disableTheming = !themingEnabled;
	}
	
	public static boolean isThemingEnabled() {
		return !disableTheming;
	}
	
	public static Font loadFont(String name, int style) {
		String fName = DirectoryUtil.RESOURCES_CLASSPATH + "fonts/" + name;
		InputStream is = UiUtils.class.getResourceAsStream(fName);
		Font out = null;
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, is);
			out = font.deriveFont(style, defaultFontSize);
		} catch (FontFormatException | IOException e) {
			Logger.error(e, "Could not load font: " + fName);
		}
		return out;
	}
	
	//https://stackoverflow.com/a/7434935
	public static void setUIFont(FontUIResource f) {
		Enumeration<?> keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource) {
				UIManager.put(key, f);
			}
		}
	}
	
	/**
	 * Set the default font size used for initializing fonts.
	 * @param size		font size
	 */
	public static void setDefaultFontSize(int size) {
		defaultFontSize = size;
	}
	
	/**
	 * Get the default font size used for initializing fonts.
	 * @return			font size
	 */
	public static int getDefaultFontSize() {
		return defaultFontSize;
	}
	
	public static void registerIconFont(String path) {
		IconFontSwing.register(new MenuIconFont(DirectoryUtil.RESOURCES_CLASSPATH + "fonts/" + path));
	}
	
	public static String[] getAvailableFonts() {
		return GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames(Locale.US);
	}
	
	
	public static Component getComponentByName(JPanel panel, Object type, String name) {
		Component[] comp = panel.getComponents();
		for(int i = 0; i < comp.length; i++) {
			if(comp[i].getClass().isInstance(type)) {
				if(comp[i].getName().equals(name)) {
					return comp[i];
				}
			}
		}
		return null;
	}
	
	public static void configureButton(JButton btn) {
		configureButton(btn, true);
	}
	
	public static void configureButton(JButton btn, boolean hoverListener) {
		if(disableTheming) {
			btn.setContentAreaFilled(true);
			btn.setFocusable(false);
			return;
		}
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setFocusable(true);
        btn.setOpaque(true);
        btn.setBackground(Style.buttonBackground);
        btn.setForeground(Style.textColor);
        if(hoverListener)
        	btn.addMouseListener(buttonHoverListener);
	}
	
	public static void configureButtonWithBorder(JButton btn, Color border) {
        btn.setBorderPainted(true);
        btn.setBorder(BorderFactory.createLineBorder(border));
		if(disableTheming) return;
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setBackground(Style.buttonBackground);
        btn.setForeground(Style.textColor);
        btn.addMouseListener(buttonHoverListener);
	}
	
	private static MouseAdapter buttonHoverListener = new MouseAdapter() {
		@Override
		public void mouseEntered(MouseEvent e) {
			if(disableTheming) return;
			JButton btn = (JButton) e.getSource();
			btn.setBackground(Style.hoverBackground);
		}
		@Override
		public void mouseExited(MouseEvent e) {
			if(disableTheming) return;
			JButton btn = (JButton) e.getSource();
			btn.setBackground(Style.buttonBackground);
		}
	};
	
	public static void configureSpinner(JSpinner spinner) {
		// set width (columns)
		JComponent editor = spinner.getEditor();
		JFormattedTextField jftf = ((JSpinner.DefaultEditor) editor).getTextField();
		jftf.setColumns(4);
		spinner.setEditor(editor);
	}
	
	public static void addHoverColor(JComponent comp, Color main, Color hover) {
		comp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				((JComponent) e.getSource()).setBackground(hover);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				((JComponent) e.getSource()).setBackground(main);
			}
		});
	}
	
	public static void configureTabbedPane(JTabbedPane tp) {
		if(disableTheming) return;
		tp.setBackground(Style.panelBackground);
		tp.setBorder(BorderFactory.createEmptyBorder());
		tp.setOpaque(true);
		tp.setFocusable(false);
		for(int i = 0; i < tp.getTabCount(); i++) {
			tp.getComponentAt(i).setBackground(Style.panelBackground);
			tp.setBackgroundAt(i, Style.panelBackground);
			if(tp.getComponentAt(i) instanceof JPanel) {
				JPanel p = (JPanel) tp.getComponentAt(i);
				p.setOpaque(true);
				p.setBorder(BorderFactory.createEmptyBorder());
				for(Component co : p.getComponents()) {
					co.setBackground(Style.panelBackground);
					if(co instanceof AbstractColorChooserPanel) {
						AbstractColorChooserPanel ac = (AbstractColorChooserPanel) co;
						ac.setBorder(BorderFactory.createEmptyBorder());
						for(Component com : ac.getComponents()) {
							if(com instanceof JComponent) {
								JComponent jc = (JComponent) com;
								jc.setBackground(Style.panelBackground);
								jc.setOpaque(true);
								jc.setBorder(BorderFactory.createEmptyBorder());
								jc.setFocusable(false);
								jc.setForeground(Style.textColor);
								for(Component comp : jc.getComponents()) {
									if(comp instanceof JComponent) {
										JComponent jco = (JComponent) comp;
										jco.setBackground(Style.panelBackground);
										jco.setForeground(Style.textColor);
										jco.setOpaque(true);
										jco.setFocusable(false);
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	
	public static void addSliderMouseWheelListener(JSlider slider) {
		slider.addMouseWheelListener(sliderWheelListener);
	}
	
	private static MouseWheelListener sliderWheelListener = new MouseWheelListener() {
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			JSlider slider = (JSlider) e.getSource();
			int notches = e.getWheelRotation();
			if (notches < 0) {
				slider.setValue(slider.getValue() + 1);
			} else if(notches > 0) {
				slider.setValue(slider.getValue() - 1);
			}
		}
	};

	
	public static void addWebsiteHyperlink(JLabel lbl, String url) {
        lbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lbl.setToolTipText(url);
        lbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI(url));
				} catch (URISyntaxException | IOException ex) {
					Main.getInstance().showNotification(NotificationType.ERROR, "Could not open " + url);
				}
			}
		});
	}
	
}
