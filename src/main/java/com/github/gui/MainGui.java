package com.github.gui;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.gui.base64.Base64UrlPanel;
import com.github.gui.json.JsonViewerPanel;

public class MainGui {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainGui.class);

    private JFrame mainFrame;

    public static void main(String[] args) {

        MainGui mainGui = new MainGui();

        mainGui.init();

        SwingUtilities.invokeLater(() -> mainGui.setVisible());

    }

    private void setVisible() {

        mainFrame.setVisible(true);
    }

    private void init() {

        LOGGER.info("starting the gui in {}*{}", GuiConstants.MAIN_GUI_WIDTH, GuiConstants.MAIN_GUI_HEIGHT);
        mainFrame = new JFrame(GuiConstants.MAIN_GUI_TITLE);
        mainFrame.setSize(GuiConstants.MAIN_GUI_WIDTH, GuiConstants.MAIN_GUI_HEIGHT);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(true);
        mainFrame.setLocationByPlatform(true);
        mainFrame.setLayout(new GridLayout(1, 1));

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(getJMenu("JSON Viewer", 'J', "A JSON Formatter", JsonViewerPanel.getInstance()));
        menuBar.add(getJMenu("Base64/URL", 'B', "A Base64/Url Encoder/Decoder", Base64UrlPanel.getInstance()));
        menuBar.add(getJMenu("Compare", 'C', "A Text Compare Tool", Base64UrlPanel.getInstance()));
        mainFrame.setJMenuBar(menuBar);
        mainFrame.setVisible(true);
    }

    private JMenu getJMenu(String title, char mnemonic, String toolTip, AbstractPanel panel) {

        JMenu jMenu = new JMenu(title);
        jMenu.setMnemonic(mnemonic);
        jMenu.setToolTipText(toolTip);
        jMenu.addMenuListener(new MenuListener() {

            @Override
            public void menuSelected(MenuEvent e) {

                Container contentPane = mainFrame.getContentPane();

                if (contentPane.getComponents().length <= 0 || contentPane.getComponent(0) != panel) {
                    resetMainGui();
                    mainFrame.getContentPane().add(panel);
                    refreshMainGui();
                }
            }

            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {

                System.out.println("menuCanceled");
            }
        });
        return jMenu;
    }

    private void resetMainGui() {

        mainFrame.getContentPane().removeAll();
    }

    private void refreshMainGui() {

        mainFrame.revalidate();
        mainFrame.repaint();
    }
}

// close popup when clicked outside
// labels instead popup for json exception
