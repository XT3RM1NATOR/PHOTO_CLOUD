package PhotoCloud.Login;
import javax.imageio.ImageIO;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.*;
import java.lang.Integer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;



import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Signin implements ActionListener{
	HashMap<String, String> LoginInfo = new HashMap<String,String>();
	ArrayList<String> userInfo = new ArrayList<String>();
	JLabel wrongLabel = new JLabel("you have to enter a unique nickanme and email");
	JButton photoButton = new JButton("Choose Photo");
	int usernameCount = 0;
	int emailCount = 0;
	int id = IDandPassword.getInfo().size() + 1;
	int count = 0;
	JButton chooseTierButton= new JButton("Select a tier");
	String selectedTier;
	ImageIcon logoIcon = new ImageIcon("PhotoCloud/logo.png");
	Image logoImage = logoIcon.getImage().getScaledInstance(550, 200, Image.SCALE_SMOOTH);
	ImageIcon logoIconS = new ImageIcon(logoImage);
	JLabel logoLabel = new JLabel(logoIconS);
	
	
	JFrame frame = new JFrame();

	JButton signinButton = new JButton("Sign in");
	JTextField userIDField = new JTextField ();
	JTextField userPasswordField = new JTextField();
	JTextField userEmailField = new JTextField();
	JTextField userNameField = new JTextField();
	JTextField userSurnameField = new JTextField();
	
	JLabel userIDLabel = new JLabel("UserId:");
	JLabel userPasswordLabel = new JLabel("Password:");
	JLabel userEmailLabel = new JLabel("Email:");
	JLabel userNameLabel = new JLabel("Name:");
	JLabel userSurnameLabel = new JLabel("Surname:");
	
	
	
	
	
	public Signin(){
		
		
		photoButton = new JButton("Upload a p.Photo");
		photoButton.setBounds(525, 450, 200, 25);
		photoButton.setFocusable(false);
		photoButton.addActionListener(this);
		
		
		userIDLabel.setBounds(450,200,75,25);
		userPasswordLabel.setBounds(450,250,75,25);
		userEmailLabel.setBounds(450,300,75,25);
		userNameLabel.setBounds(450,350,75,25);
		userSurnameLabel.setBounds(450,400,75,25);
		
		
		userIDField.setBounds(525,200,200,25);
		userPasswordField.setBounds(525,250,200,25);
		userEmailField.setBounds(525,300,200,25);
		userNameField.setBounds(525,350,200,25);
		userSurnameField.setBounds(525,400,200,25);
		
		wrongLabel.setBounds(210,650,75,25);
		
		chooseTierButton.setBounds(575,500,100,25);
		chooseTierButton.setFocusable(false);
		chooseTierButton.addActionListener(this);
		
		signinButton.setBounds(575,650,100,50);
		signinButton.setFocusable(false);
		signinButton.addActionListener(this);
		
		
		
		String[] options = {"Free", "Hobbyist", "Professional"};

        JComboBox<String> tierOptions = new JComboBox<>(options);
        
        tierOptions.setBounds(575,550,100,100);
        tierOptions.setFocusable(true);
        tierOptions.addActionListener(this);
        
        tierOptions.setVisible(false);
        
        frame.add(tierOptions);

        // Add ActionListener to the button
        chooseTierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tierOptions.setVisible(true);
            }
        });

        // Add ActionListener to the dropdown menu
        tierOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
                selectedTier = (String) comboBox.getSelectedItem();
                tierOptions.setVisible(false);
                
            }
        });
        logoLabel.setBounds(300,50,550,200);
        frame.add(logoLabel);
		
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200,900);
		frame.setLayout(null);
		frame.setVisible(true);
		
		frame.add(userIDLabel);
		frame.add(userPasswordLabel);
		frame.add(userIDField);
		frame.add(userPasswordField);
		frame.add(signinButton);
		frame.add(userEmailLabel);
		frame.add(userNameLabel);
		frame.add(userSurnameLabel);
		frame.add(userNameField);
		frame.add(userSurnameField);
		frame.add(userEmailField);	
		frame.add(photoButton);
		frame.add(chooseTierButton);

		
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==signinButton) {
			for(ArrayList array:IDandPassword.getInfo()) {
				if(array.contains(userIDField.getText())) {
					usernameCount++;
				}
				if(array.contains(userEmailField.getText()) ) {
					emailCount++;
				}
			}
			if(usernameCount==0 && emailCount==0 && userIDField.getText()!="the_username_is_not_unique" && userEmailField.getText()!="the_email_is_not_unique" && userPasswordField.getText()!="Password_min._8_symbols") {
				try {
					FileWriter writer = new FileWriter("Accounts.txt", true);
		            BufferedWriter buffer = new BufferedWriter(writer);
		            // Write data to file
		            buffer.write(userIDField.getText() + " " + userPasswordField.getText() + " ");
		            buffer.write(userEmailField.getText() + " " + userNameField.getText() + " ");
		            buffer.write(userSurnameField.getText() + " " + id + " " + selectedTier + "\n");

		            // Close the writer
		            buffer.close();
		           

				} catch (IOException e1) {
					// catches an exception
					System.out.print(e1.getMessage());
				}
				try {
					FileWriter writer = new FileWriter("PicturesToUsers.txt", true);
		            BufferedWriter buffer = new BufferedWriter(writer);
		            // Write data to file
		            buffer.write(id + " " + "\n");

		            // Close the writer
		            buffer.close();
		           

				} catch (IOException e1) {
					// catches an exception
					System.out.print(e1.getMessage());
				}
				IDandPassword newIDandPassword = new IDandPassword("Accounts.txt");
				LoginPage login = new LoginPage(newIDandPassword.getInfo());
				frame.dispose();
				

			}
			if(usernameCount > 0){
				userIDField.setText("the_username_is_not_unique");
				usernameCount = 0;
			}
			if(emailCount > 0) {
				userEmailField.setText("the_email_is_not_unique");
				emailCount = 0;
			}
			
		
			
		}else if(e.getSource() == photoButton) {
			JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                try {
                    String saveDirectory = "photos";
                    String fileName = "photo_"+ id + ".jpg";  // Change the file name as desired

                    Path destination = Path.of(saveDirectory, fileName);
                    Files.copy(selectedFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

                    JOptionPane.showMessageDialog(null, "Photo saved successfully!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error saving photo: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                count++;
            }
            
        }
		if(count == 0) {
			try {
			    String saveDirectory = "photos";
			    String fileName = "photo_" + id + ".jpg";  // Change the file name as desired
			    String imagePath = "default.jpeg"; // Provide the path to your image here

			    Path selectedFilePath = Paths.get(imagePath);
			    Path destination = Path.of(saveDirectory, fileName);
			    Files.copy(selectedFilePath, destination, StandardCopyOption.REPLACE_EXISTING);
			} catch (Exception ex) {
			    JOptionPane.showMessageDialog(null, "Error saving photo: " + ex.getMessage(),
			            "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
}
		
	

		  
         
       


		



