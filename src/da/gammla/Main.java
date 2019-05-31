package da.gammla;

import da.gammla.AnchoredTable.AnchoredTable;
import org.jnativehook.GlobalScreen;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Mega
 * Intellij IDEA
 */
public class Main {

    public static int keyCode = KeyEvent.VK_C, mouseButton = InputEvent.BUTTON1_MASK;
    public static double clicks = 1.0, wait_ms = 500.0, down_ms = 500.0;
    public static int clicks_done;
    public static ClickItAuto clickItAuto;
    public static boolean isKeyRequested = false;
    public static boolean keyPress = false;
    public static boolean keyActive = false;
    public static long lastTimeClicked = System.currentTimeMillis();

    public static long ms = 500;
    public static int nanos = 0;


    static Robot bot = null;


    public static void main(String[] args){

        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        try {
            GlobalScreen.registerNativeHook();
        } catch (Exception e) {
            e.printStackTrace();
        }try {
            bot = new Robot();
        } catch (Exception e) {
            e.printStackTrace();
        }try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e){
            e.printStackTrace();
        }
        GlobalScreen.addNativeKeyListener(new GlobalKeyListener());

        AnchoredTable settings = new AnchoredTable("Settings");

        try {
            settings = new AnchoredTable(new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/DaGammla/ClickItAuto/settings.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        clickItAuto = new ClickItAuto(settings);

        while (true) {

            if(!keyPress && !keyActive) {
                lastTimeClicked = System.currentTimeMillis();
                clicks_done = 0;
            } else if(keyPress && keyActive) {
                keyPress = clickItAuto.holdRadioButton.isSelected();
                keyActive = !clickItAuto.holdRadioButton.isSelected();
            }


            if(System.currentTimeMillis() - lastTimeClicked > ((1000 / clicks) * clickItAuto.clickQualitySlider.getValue()) / 100)
                bot.mouseRelease(mouseButton);


            if(
                    (keyPress && clickItAuto.holdRadioButton.isSelected() && clickItAuto.isActiveCheckBox.isSelected())
                    ||
                    (keyActive && !clickItAuto.holdRadioButton.isSelected() && clickItAuto.isActiveCheckBox.isSelected())) {

                if(System.currentTimeMillis() - lastTimeClicked > 1000 / clicks && ((int)clickItAuto.clickAmountSpinner.getValue() == 0 || clicks_done < (int)clickItAuto.clickAmountSpinner.getValue())) {
                    bot.mousePress(mouseButton);
                    lastTimeClicked = System.currentTimeMillis();
                    clicks_done++;
                }
            }

            try{
                Thread.sleep(ms, nanos);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(!clickItAuto.isVisible()){
                bot.mouseRelease(InputEvent.BUTTON1_MASK);
                bot.mouseRelease(InputEvent.BUTTON2_MASK);
                bot.mouseRelease(InputEvent.BUTTON3_MASK);
                System.exit(0);
            }
        }
    }
}
