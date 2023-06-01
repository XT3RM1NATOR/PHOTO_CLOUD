package Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Settings{
    private ArrayList<String> userData;
    private JTextField nameField;
    private JTextField surnameField;
    private JTextField emailField;
    private JTextField tierField;
    private String tier;
    
    JFrame frame = new JFrame("Settings");

    public Settings(ArrayList<String> userData) {
        this.userData = userData;
        tier = userData.get(6);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLayout(new GridLayout(6, 2));

        JLabel nameLabel = new JLabel("Name:");
        frame.add(nameLabel);
        nameField = new JTextField(userData.get(3));
        frame.add(nameField);

        JLabel surnameLabel = new JLabel("Surname:");
        frame.add(surnameLabel);
        surnameField = new JTextField(userData.get(4));
        frame.add(surnameField);
        
        JLabel emailLabel = new JLabel("Email:");
        frame.add(emailLabel);
        emailField = new JTextField(userData.get(2));
        frame.add(emailField);
           
        JLabel tierLabel = new JLabel("Tier");
        frame.add(tierLabel);
        tierField = new JTextField(userData.get(6));
        frame.add(tierField);
        
        String[] options = {"Free", "Hobbyist", "Professional"};
        JComboBox<String> tierOptions = new JComboBox<>(options);
        frame.add(tierOptions);
        tierOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
                tier = (String) comboBox.getSelectedItem();
                tierField.setText(tier);;
                tierOptions.setVisible(false);           
            }
        });// creates the option to choose the tiers

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new SaveButtonListener());
        frame.add(saveButton);

        // Display the frame
        frame.setVisible(true);
    }
    

    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateFile();

            // show that we saved the changes
            JOptionPane.showMessageDialog(null, "Changes saved successfully!");
            frame.dispose();
        }
    }

    private void updateFile() {
        String lineToModify = String.join(" ", userData.get(0), userData.get(1), userData.get(2), userData.get(3), userData.get(4), userData.get(5), userData.get(6));
        String newLine = String.join(" ", userData.get(0), userData.get(1), emailField.getText(), nameField.getText(), surnameField.getText(), userData.get(5), tier);
//changing the string in the file after the values are changes in the settings
        try {
            // Opening the file to analyze the data and writee to it
            BufferedReader reader = new BufferedReader(new FileReader("Accounts.txt"));
            ArrayList<String> lines = new ArrayList<>();

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.equals(lineToModify)) {
                    lines.add(newLine);
                } else {
                    lines.add(currentLine);
                }
            }

            reader.close();
            BufferedWriter writer = new BufferedWriter(new FileWriter("Accounts.txt"));

            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
