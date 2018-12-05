package taskmanager.Interface;

import taskmanager.elements.Task;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TryNotificate {
    public void show(Task task) throws InterruptedException {
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();

            java.awt.Image image = Toolkit.getDefaultToolkit().getImage("images/tray.gif");
            TrayIcon trayIcon = new TrayIcon(image);
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                e.printStackTrace();
            }
            SimpleDateFormat formatForDateNow = new SimpleDateFormat(" kk:mm");
            trayIcon.displayMessage("Task: " + task.getTitle(),
                                        task.getDescription() + formatForDateNow.format( new Date()),
                                             TrayIcon.MessageType.INFO);
            Thread.sleep(10000);
            tray.remove(trayIcon);
        }
    }
}
