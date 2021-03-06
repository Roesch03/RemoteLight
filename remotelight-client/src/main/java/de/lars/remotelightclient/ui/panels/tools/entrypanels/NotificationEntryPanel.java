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

package de.lars.remotelightclient.ui.panels.tools.entrypanels;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import de.lars.remotelightclient.Main;
import de.lars.remotelightclient.ui.Style;
import de.lars.remotelightclient.ui.components.ListElement;
import de.lars.remotelightclient.ui.panels.tools.ToolsPanel;
import de.lars.remotelightclient.ui.panels.tools.ToolsPanelEntry;
import de.lars.remotelightcore.notification.Notification;
import de.lars.remotelightcore.notification.NotificationManager;

/**
 * Tools panel showing the notification history.
 */
public class NotificationEntryPanel extends ToolsPanelEntry {

	private NotificationManager manager;
	
	public NotificationEntryPanel() {
		manager = Main.getInstance().getCore().getNotificationManager();
	}
	
	@Override
	public String getName() {
		return "Notification History";
	}
	
	@Override
	public JPanel getMenuPanel(ToolsPanel context) {
		return new NotificationPanel();
	};
	
	private class NotificationPanel extends JPanel {
		private static final long serialVersionUID = 3931920450322502L;
		
		public NotificationPanel() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setBackground(Style.panelBackground);
			setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
			
			List<Notification> notis = manager.getNotificationHistory();
			
			if(notis.size() == 0) {
				JLabel lblEmpty = new JLabel("There are no notifications", SwingConstants.CENTER);
				lblEmpty.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
				lblEmpty.setFont(Style.getFontBold(14));
				lblEmpty.setForeground(Style.textColor);
				lblEmpty.setAlignmentY(Component.CENTER_ALIGNMENT);
				add(lblEmpty);
			} else {
				
				for(Notification noti : notis) {
					ListElement el = new ListElement();
					el.setAlignmentY(Component.TOP_ALIGNMENT);
					el.setBorder(new CompoundBorder(
							new LineBorder(Style.getNotificationColor(noti.getNotificationType())),
							BorderFactory.createEmptyBorder(2, 10, 2, 10)));
					
					JLabel lblTitle = new JLabel(noti.getTitle());
					lblTitle.setForeground(Style.textColor);
					el.add(lblTitle);
					el.add(Box.createHorizontalStrut(10));
					
					JLabel lblMessage = new JLabel(noti.getMessage());
					lblMessage.setForeground(Style.textColorDarker);
					lblMessage.setMinimumSize(new Dimension(50, lblMessage.getPreferredSize().height));
					el.add(lblMessage);
					el.add(Box.createHorizontalGlue());
					
					long millis = noti.getCreationTime();
					String creationTime = DateFormat.getTimeInstance().format(new Date(millis));
					JLabel lblTime = new JLabel(creationTime);
					lblTime.setForeground(Style.textColorDarker);
					el.add(Box.createHorizontalGlue());
					el.add(Box.createHorizontalStrut(10));
					el.add(lblTime);
					
					JButton btnView = new JButton("View");
					btnView.setContentAreaFilled(false);
					btnView.setBorderPainted(false);
					btnView.setFocusPainted(false);
					btnView.setFocusable(true);
					btnView.setOpaque(true);
					btnView.setBackground(null);
					btnView.setForeground(Style.textColor);
					btnView.setCursor(new Cursor(Cursor.HAND_CURSOR));
					btnView.addActionListener(e -> {
						// TODO add details panel
					});
					
					//el.add(btnView); TODO add notification details panel
					add(el);
					add(Box.createVerticalStrut(10));
				}
			}
		}
		
	}

}
