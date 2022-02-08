package view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public JButton playButton, exitButton, loadButton, helpButton;
    public JPanel titlePanel, menuPanel;
    private Container con;

    public MainFrame() {
        setFrameConfigs();
        con = getContentPane();

        setAllButtons();
        setAllPanels();

        menuPanel.add(playButton);
        menuPanel.add(exitButton);
        menuPanel.add(loadButton);
        menuPanel.add(helpButton);

        con.add(menuPanel);
        setVisible(true);
    }

    private void setFrameConfigs() {

        setSize(900, 700);
        setResizable(false); // enable the resize of the frame
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.blue);
        setLayout(null);
        setVisible(true);

    }

    private void setAllButtons() {
        playButton = createJButton("Play Game", 150, 50, false, Color.WHITE, Color.GREEN);
        exitButton = createJButton("Exit Game", 150, 20, false, Color.white, Color.green);
        loadButton = createJButton("Load Game", 200, 20, false, Color.white, Color.red);
        helpButton = createJButton("Help Menu", 200, 20, false, Color.white, Color.blue);
    }

    private void setAllPanels() {
        titlePanel = createJPanel(200, 50, 500, 150, Color.yellow, true);
        menuPanel = createJPanel(350, 300, 175, 225, Color.yellow, true);
    }

    public void clearMenuPanel() {
        con.remove(menuPanel);
    }

    private JButton createJButton(String title, int width, int height, boolean focusable, Color foreground, Color background) {
        JButton product = new JButton(title);
        product.setPreferredSize(new Dimension(width, height));
        product.setFocusable(focusable);
        product.setForeground(foreground);
        product.setBackground(background);
        return product;
    }

    private JPanel createJPanel(int x, int y, int width, int height, Color background, boolean visible) {
        JPanel product = new JPanel();
        product.setBounds(x, y, width, height);
        product.setBackground(background);
        product.setVisible(visible);
        return product;
    }
}