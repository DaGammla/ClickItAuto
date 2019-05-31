package da.gammla;

import da.gammla.AnchoredTable.AnchoredTable;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileSystemView;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.event.*;
import java.io.IOException;

/**
 * Created by Mega
 * Intellij IDEA
 */
public class ClickItAuto extends JFrame{

    public JButton changeKeyButton;
    public JPanel panel;
    public JLabel key;
    public JLabel buttonDownFor;
    public JSlider clickQualitySlider;
    public JCheckBox isActiveCheckBox;
    public JLabel waitBetweenFor;
    public JComboBox mouseButtonComboBox;
    public JSpinner cpsSpinner;
    public JRadioButton holdRadioButton;
    public JComboBox clicksPerComboBox;
    public JSpinner clickAmountSpinner;
    private JCheckBox altCheckBox;
    private JCheckBox ctrlCheckBox;
    private JCheckBox shiftCheckBox;
    private JRadioButton toggleRadioButton;

    AnchoredTable settings;

    public ClickItAuto(AnchoredTable settings){
        super("Click It Auto");

        this.settings = settings;

        setContentPane(panel);
        pack();
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        waitBetweenFor.setText("Wait " + 500.0 + " ms between every click");
        buttonDownFor.setText("Hold mouse button down for " + 500.0 + " ms");
        loadFromSettings();

        changeKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.isKeyRequested = true;
                key.setText("...");
            }
        });

        mouseButtonComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (mouseButtonComboBox.getSelectedItem().equals("Left click")) {
                    Main.mouseButton = InputEvent.BUTTON1_MASK;
                } else if (mouseButtonComboBox.getSelectedItem().equals("Middle click")) {
                    Main.mouseButton = InputEvent.BUTTON2_MASK;
                } else if (mouseButtonComboBox.getSelectedItem().equals("Right click")) {
                    Main.mouseButton = InputEvent.BUTTON3_MASK;
                }
                saveSettings();
            }
        });

        clickQualitySlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Main.wait_ms = Math.round(((1000.0 /Main.clicks) * (100 - (double)clickQualitySlider.getValue())) * 10.0) / 1000.0;
                Main.down_ms = Math.round(((1000.0 / Main.clicks) * (double)clickQualitySlider.getValue()) * 10.0) / 1000.0;

                waitBetweenFor.setText("Wait " + Main.wait_ms + " ms between every click");
                buttonDownFor.setText("Hold mouse button down for " + Main.down_ms + " ms");

                double full_ms = Math.min(Main.wait_ms, Main.down_ms);
                Main.ms = (long) Math.floor(full_ms);
                Main.nanos = (int) Math.min((Math.ceil(full_ms - (double) Main.ms) * 1000000.0), 999999.0);

                saveSettings();
            }
        });

        cpsSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Main.clicks = (int) cpsSpinner.getValue();

                if (clicksPerComboBox.getSelectedItem().equals("second")) {

                } else if (clicksPerComboBox.getSelectedItem().equals("minute")) {
                    Main.clicks = Main.clicks * (1.0 / 60.0);
                } else if (clicksPerComboBox.getSelectedItem().equals("hour")) {
                    Main.clicks = Main.clicks * (1.0 / 60.0) * (1.0 / 60.0);
                } else if (clicksPerComboBox.getSelectedItem().equals("day")) {
                    Main.clicks = Main.clicks * (1.0 / 60.0) * (1.0 / 60.0) * (1.0 / 24.0);
                }

                if (Main.clicks < 0)
                    Main.clicks = 1;

                Main.wait_ms = Math.round(((1000.0 /Main.clicks) * (100 - (double)clickQualitySlider.getValue())) * 10.0) / 1000.0;
                Main.down_ms = Math.round(((1000.0 / Main.clicks) * (double)clickQualitySlider.getValue()) * 10.0) / 1000.0;

                waitBetweenFor.setText("Wait " + Main.wait_ms + " ms between every click");
                buttonDownFor.setText("Hold mouse button down for " + Main.down_ms + " ms");

                double full_ms = Math.min(Main.wait_ms, Main.down_ms);
                Main.ms = (long) Math.floor(full_ms);
                Main.nanos = (int) Math.min((Math.ceil(full_ms - (double) Main.ms) * 1000000.0), 999999.0);

                saveSettings();
            }
        });
        clicksPerComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Main.clicks = (int) cpsSpinner.getValue();

                if (clicksPerComboBox.getSelectedItem().equals("second")) {

                } else if (clicksPerComboBox.getSelectedItem().equals("minute")) {
                    Main.clicks = Main.clicks * (1.0 / 60.0);
                } else if (clicksPerComboBox.getSelectedItem().equals("hour")) {
                    Main.clicks = Main.clicks * (1.0 / 60.0) * (1.0 / 60.0);
                } else if (clicksPerComboBox.getSelectedItem().equals("day")) {
                    Main.clicks = Main.clicks * (1.0 / 60.0) * (1.0 / 60.0) * (1.0 / 24.0);
                }

                if (Main.clicks < 0)
                    Main.clicks = 1;

                Main.wait_ms = Math.round(((1000.0 /Main.clicks) * (100 - (double)clickQualitySlider.getValue())) * 10.0) / 1000.0;
                Main.down_ms = Math.round(((1000.0 / Main.clicks) * (double)clickQualitySlider.getValue()) * 10.0) / 1000.0;

                waitBetweenFor.setText("Wait " + Main.wait_ms + " ms between every click");
                buttonDownFor.setText("Hold mouse button down for " + Main.down_ms + " ms");

                double full_ms = Math.min(Main.wait_ms, Main.down_ms);
                Main.ms = (long) Math.floor(full_ms);
                Main.nanos = (int) Math.min((Math.ceil(full_ms - (double) Main.ms) * 1000000.0), 999999.0);

                saveSettings();
            }
        });

        ActionListener list = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveSettings();
            }
        };

        holdRadioButton.addActionListener(list);
        toggleRadioButton.addActionListener(list);
        ctrlCheckBox.addActionListener(list);
        shiftCheckBox.addActionListener(list);
        altCheckBox.addActionListener(list);



        setLocationRelativeTo(null);


        setDefaultLookAndFeelDecorated(true);
        setResizable(true);

        setVisible(true);

        pack();
    }

    private void createUIComponents() {
        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
        cpsSpinner = new JSpinner(model);
        SpinnerNumberModel model1 = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
        clickAmountSpinner = new JSpinner(model1);
    }

    void loadFromSettings(){
        if (settings.hasAnchor("timing_scale")){
            clicksPerComboBox.setSelectedIndex(Integer.parseInt(settings.getData("timing_scale")));
        }
        if (settings.hasAnchor("mouse_button")){
            mouseButtonComboBox.setSelectedIndex(Integer.parseInt(settings.getData("mouse_button")));
        }
        if (settings.hasAnchor("click_speed")){
            cpsSpinner.setValue(Integer.parseInt(settings.getData("click_speed")));
        }
        if (settings.hasAnchor("click_amount")){
            clickAmountSpinner.setValue(Integer.parseInt(settings.getData("click_amount")));
        }
        if (settings.hasAnchor("click_quality")){
            clickQualitySlider.setValue(Integer.parseInt(settings.getData("click_quality")));
        }
        if (settings.hasAnchor("hold")){
            holdRadioButton.setSelected(settings.getData("hold").equals("true") ? true : false);
            toggleRadioButton.setSelected(settings.getData("hold").equals("true") ? false : true);
        }
        if (settings.hasAnchor("key")){
            Main.keyCode= Integer.parseInt(settings.getData("key"));
        }
        if (settings.hasAnchor("ctrl")){
            ctrlCheckBox.setSelected(settings.getData("ctrl").equals("true") ? true : false);
        }
        if (settings.hasAnchor("alt")){
            altCheckBox.setSelected(settings.getData("alt").equals("true") ? true : false);
        }
        if (settings.hasAnchor("shift")){
            shiftCheckBox.setSelected(settings.getData("shift").equals("true") ? true : false);
        }

        Main.clicks = (int) cpsSpinner.getValue();

        if (clicksPerComboBox.getSelectedItem().equals("second")) {

        } else if (clicksPerComboBox.getSelectedItem().equals("minute")) {
            Main.clicks = Main.clicks * (1.0 / 60.0);
        } else if (clicksPerComboBox.getSelectedItem().equals("hour")) {
            Main.clicks = Main.clicks * (1.0 / 60.0) * (1.0 / 60.0);
        } else if (clicksPerComboBox.getSelectedItem().equals("day")) {
            Main.clicks = Main.clicks * (1.0 / 60.0) * (1.0 / 60.0) * (1.0 / 24.0);
        }

        if (Main.clicks < 0)
            Main.clicks = 1;

        Main.wait_ms = Math.round(((1000.0 /Main.clicks) * (100 - (double)clickQualitySlider.getValue())) * 10.0) / 1000.0;
        Main.down_ms = Math.round(((1000.0 / Main.clicks) * (double)clickQualitySlider.getValue()) * 10.0) / 1000.0;

        waitBetweenFor.setText("Wait " + Main.wait_ms + " ms between every click");
        buttonDownFor.setText("Hold mouse button down for " + Main.down_ms + " ms");

        double full_ms = Math.min(Main.wait_ms, Main.down_ms);
        Main.ms = (long) Math.floor(full_ms);
        Main.nanos = (int) Math.min((Math.ceil(full_ms - (double) Main.ms) * 1000000.0), 999999.0);
        key.setText(KeyEvent.getKeyText(Main.keyCode));
    }

    void saveSettings(){
        settings.setData("timing_scale", clicksPerComboBox.getSelectedIndex() + "");
        settings.setData("mouse_button", mouseButtonComboBox.getSelectedIndex() + "");
        settings.setData("click_speed", (int) cpsSpinner.getValue() + "");
        settings.setData("click_amount", (int) clickAmountSpinner.getValue() + "");
        settings.setData("click_quality", clickQualitySlider.getValue() + "");
        settings.setData("hold", holdRadioButton.isSelected() + "");
        settings.setData("key", Main.keyCode + "");
        settings.setData("ctrl", ctrlCheckBox.isSelected() + "");
        settings.setData("alt", altCheckBox.isSelected() + "");
        settings.setData("shift", shiftCheckBox.isSelected() + "");

        try {
            settings.saveToXML(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/DaGammla/ClickItAuto/settings.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
