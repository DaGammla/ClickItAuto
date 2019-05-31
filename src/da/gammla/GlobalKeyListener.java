package da.gammla;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.awt.event.KeyEvent;

/**
 * Created by Mega
 * Intellij IDEA
 */
public class GlobalKeyListener implements NativeKeyListener {

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeEvent) {

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
        if (Main.isKeyRequested) {
            Main.clickItAuto.key.setText(KeyEvent.getKeyText(nativeEvent.getRawCode()));
            Main.keyCode = nativeEvent.getRawCode();
            Main.isKeyRequested = false;
            Main.clickItAuto.saveSettings();
        }

        if (nativeEvent.getRawCode() == Main.keyCode && Main.clickItAuto.isActiveCheckBox.isSelected() && Main.clickItAuto.holdRadioButton.isSelected()) {
            Main.keyPress = true;
        }

        if (nativeEvent.getRawCode() == Main.keyCode && Main.clickItAuto.isActiveCheckBox.isSelected() && !Main.clickItAuto.holdRadioButton.isSelected()) {
            Main.keyActive = !Main.keyActive;
        }

    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
        if (nativeEvent.getRawCode() == Main.keyCode && Main.clickItAuto.isActiveCheckBox.isSelected()) {
            Main.keyPress = false;
        }
    }
}
