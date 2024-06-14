import java.io.File;
import java.io.IOException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javax.sound.sampled.*;


public class GameSettings extends JFrame {

    private Clip clip;
    private int currentVolume = (int)(Planewar.volume * 100);
    private String currentDifficulty = Planewar.Dfficulty;
    private String currentWindowSize = Planewar.widowSize;

    // Constructor to set up the GUI components
    public GameSettings() {
        setTitle("Game Settings");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set patterns
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //better layout setting
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); //things' distance

        // custom styles
        JLabel volumeLabel = new JLabel("Volume:");
        volumeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JSlider volumeSlider = new JSlider(0, 100, currentVolume);
        volumeSlider.setMajorTickSpacing(10);
        volumeSlider.setMinorTickSpacing(10); //let it be multiples of 10
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);
        volumeSlider.setSnapToTicks(true);

        JLabel difficultyLabel = new JLabel("Difficulty:");
        difficultyLabel.setFont(new Font("Arial", Font.BOLD, 14));
        String[] difficulties = {"Easy", "Medium", "Hard", "Hell"};
        JComboBox<String> difficultyComboBox = new JComboBox<>(difficulties);
        difficultyComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        difficultyComboBox.setSelectedItem(currentDifficulty);

        JLabel windowSizeLabel = new JLabel("Window Size:");
        windowSizeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        String[] windowSizes = {"600x600", "800x800", "1000x1000"};
        JComboBox<String> windowSizeComboBox = new JComboBox<>(windowSizes);
        windowSizeComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        windowSizeComboBox.setSelectedItem(currentWindowSize);

        JButton saveButton = new JButton("Save");
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setBackground(Color.GREEN);
        saveButton.setForeground(Color.WHITE);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setBackground(Color.RED);
        cancelButton.setForeground(Color.WHITE);

        // layout constraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(volumeLabel, gbc);

        gbc.gridx = 1;
        panel.add(volumeSlider, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(difficultyLabel, gbc);

        gbc.gridx = 1;
        panel.add(difficultyComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(windowSizeLabel, gbc);

        gbc.gridx = 1;
        panel.add(windowSizeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(saveButton, gbc);

        gbc.gridx = 1;
        panel.add(cancelButton, gbc);

        // Add the panel to the frame
        add(panel);

        // Define button actions
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentVolume = volumeSlider.getValue(); 
                String difficulty = (String) difficultyComboBox.getSelectedItem();
                String windowSize = (String) windowSizeComboBox.getSelectedItem();

                Planewar.volume = (float)currentVolume/100;
                Planewar.Dfficulty = difficulty;

                if(difficulty.equals("Easy")){
                    BossObj.life = 5;
                    BossObj.basiclife = 5;
                    Planewar.bossSpeed = 3;
                    Planewar.bulletSpeed = 3;
                    Planewar.enemySpeed = 3;
                    Planewar.shellSpeed = 8;
                    Planewar.bulletProductivity = 25;
                    Planewar.enemyProductivity = 25;
                    Planewar.shellProductivity = 10;
                }

                if(difficulty.equals("Midium")){
                    BossObj.life = 10;
                    BossObj.basiclife = 10;
                    Planewar.bossSpeed = 5;
                    Planewar.bulletSpeed = 5;
                    Planewar.enemySpeed = 5;
                    Planewar.shellSpeed = 5;
                    Planewar.bulletProductivity = 15;
                    Planewar.enemyProductivity = 15;
                    Planewar.shellProductivity = 15;
                }

                if(difficulty.equals("Hard")){
                    BossObj.life = 15;
                    BossObj.basiclife = 15;
                    Planewar.bossSpeed = 8;
                    Planewar.bulletSpeed = 8;
                    Planewar.enemySpeed = 8;
                    Planewar.shellSpeed = 4;
                    Planewar.bulletProductivity = 10;
                    Planewar.enemyProductivity = 10;
                    Planewar.shellProductivity = 20;
                }

                if(difficulty.equals("Hell")){
                    BossObj.life = 30;
                    BossObj.basiclife = 30;
                    Planewar.bossSpeed = 10;
                    Planewar.bulletSpeed = 10;
                    Planewar.enemySpeed = 10;
                    Planewar.shellSpeed = 5;
                    Planewar.bulletProductivity = 8;
                    Planewar.enemyProductivity = 5;
                    Planewar.shellProductivity = 25;
                }


                String[] dimensions = windowSize.split("x");
                int width = Integer.parseInt(dimensions[0]);
                int height = Integer.parseInt(dimensions[1]);
                Planewar.width = width;
                Planewar.height = height;

                Planewar.mainFrame.setSize(width,height);
                Planewar.mainFrame.repaint();

                Planewar.reinitializeComponents(Planewar.mainFrame);

                dispose();
                Planewar.open = SoundUtil.playSoundWithVolume(GameUtil.rickMusic, true, Planewar.volume); //close open music
            }
        });


        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the settings window without saving
                dispose();
                Planewar.open = SoundUtil.playSoundWithVolume(GameUtil.rickMusic, true, Planewar.volume); //close open music
            }
        });

        // Initialize the clip and add a change listener to the slider
        initClip(GameUtil.testSound);
        volumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                currentVolume = volumeSlider.getValue();
                adjustClipVolume(currentVolume);
                playSound(); //remind sound for user
            }
        });
    }

    // initialize the clip
    private void initClip(String soundFile) {
        try {
            File soundPath = new File(soundFile);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundPath);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // adjust clip volume
    private void adjustClipVolume(int volume) {        
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float min = volumeControl.getMinimum();
            float max = volumeControl.getMaximum();
            float mid = (max - min) / 2.0f + min;
            float range = max - min;
            float gain = (volume - 50) * (range / 2) / 50 + mid;
            volumeControl.setValue(gain);
    }

    //  play a sound
    private void playSound() {
        if (clip != null) {
            clip.setFramePosition(0); // Rewind to the beginning
            clip.start();
        }
    }

}

