package Account;

import javax.swing.JButton;
import PhotoCloud.DiscoverPage;
import PhotoCloud.UserSearch;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import PhotoCloud.Login.IDandPassword;

import javax.swing.JFileChooser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ProfilePage implements ActionListener {
    private JFrame frame;
    private JLabel profilePicture;
    private JLabel name;
    private JLabel nickname;
    private JLabel email;
    private JLabel personalPhotosLabel;
    private JLabel tierLabel;
    
    private JButton editPhotosButton;
    private JButton goBackButton;
    private JButton uploadProfilePictureButton;
    private JButton settingsButton;
    private JButton otherUsersButton = new JButton("DiscoverPage");
    private JButton postButton = new JButton("Post");
    private JButton viewProfileButton = new JButton("View Profile");
    // creating buttons
   
    
    private ImageIcon settingsIcon = new ImageIcon("PhotoCloud/settings.png");
    private Image settingsImage = settingsIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
    private ImageIcon settingsIconS = new ImageIcon(settingsImage);
    // getting a scaled version of the settings image
    
    private JFileChooser chooser = new JFileChooser();
    private String filename = null;
    private ArrayList<String> userInfo = new ArrayList<String>();
    private IDandPassword array = new IDandPassword("Accounts.txt");
    private int count = 0;
    private ImageIcon logoIcon = new ImageIcon("PhotoCloud/logo.png");
	private Image logoImage = logoIcon.getImage().getScaledInstance(150, 30, Image.SCALE_SMOOTH);
	private ImageIcon logoIconS = new ImageIcon(logoImage);
	private JLabel logoLabel = new JLabel(logoIconS);
	//getting a photo of my logo
    
    public ProfilePage(ArrayList<String> userInfo) {
    	this.userInfo = userInfo;
    	
    	ImageIcon trash = new ImageIcon("PhotoCloud/trash.png");
    	Image trashh = trash.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
    	trash = new ImageIcon(trashh);
    	//getting the button logo for the trash
    	
        frame = new JFrame("User Profile Page");
        frame.setSize(1200, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(null);
        postButton.setBounds(50,500,125,25);
        postButton.setFocusable(false);
        postButton.addActionListener(this);
        frame.add(postButton);
        
        otherUsersButton.setBounds(50,400,125,25);
        otherUsersButton.setFocusable(false);
        otherUsersButton.addActionListener(this);
        frame.add(otherUsersButton);
        
        viewProfileButton.setBounds(50,300,125,25);
        viewProfileButton.setFocusable(false);
        viewProfileButton.addActionListener(this);
        frame.add(viewProfileButton);
        // we check if there are any photos on user's id meaning has he posted anything
        if(extractPhotoReferences("picturesToUsers.txt", userInfo.get(5)).size()>0) { // if it is more than 0 then he did post
        	for(String photo:extractPhotoReferences("picturesToUsers.txt", userInfo.get(5))) { // get the photo names
        		System.out.print(photo);
        		JButton trashButton = new JButton(trash);
            	trashButton.addActionListener(new ActionListener() { //if trash is pressed
                    public void actionPerformed(ActionEvent e) {
                    	renameFile("DiscoverPage/"+photo, "deleted/deleted.txt"); //we rename the file to deleted so we dont encounter it anymore and give it a name with a non repeating number 
                    	incrementNumber("deleted/deleted.txt"); //this number we get from this file and here we increment it
                        deleteRefFromFile("picturesToUsers.txt", " " + photo); //now we delete the photo from our id list
                        renameFile("photoStats/"+"file_photo" + photo.substring(5, photo.indexOf(".")) + ".txt", "deleted/deleted.txt"); //here we rename files of the stats of the photo
                        incrementNumber("deleted/deleted.txt");
                        ProfilePage profilePicture = new ProfilePage(userInfo); //initializing new prof page with the deleted photos
                        frame.dispose();
                    }
                });
            	frame.add(trashButton);
        		JLabel photoLabel = new JLabel(new ImageIcon(getScaledImage("DiscoverPage/" + photo, 150, 150)));
        		if(count<=5) {
        			photoLabel.setBounds(200+200*count,400,150,150);
        			trashButton.setBounds(205+200*count,400,20,20);//displaying new photos
        		}else {
        			photoLabel.setBounds(200+200*count,610,150,150);
        			trashButton.setBounds(205+200*count,610,20,20);
        		}
        		frame.add(photoLabel);
        		count++;
        	}
        }
        
        settingsButton = new JButton();
        settingsButton.setBounds(700, 50, 25, 25);
        settingsButton.setFocusable(false);
        settingsButton.addActionListener(this);
        settingsButton.setIcon(settingsIconS);
        frame.add(settingsButton);
        
        personalPhotosLabel = new JLabel("Your Posted Photos");
        personalPhotosLabel.setBounds(550, 300, 200, 20);
        personalPhotosLabel.setFont(new Font("Montserrat", Font.PLAIN, 18));
        frame.add(personalPhotosLabel);
                
        name = new JLabel(userInfo.get(3) + " " + userInfo.get(4));
        name.setBounds(500, 150, 200, 30);
        name.setFont(new Font("Verdana", Font.BOLD, 18));
        frame.add(name);
              
        email = new JLabel("Email: " + userInfo.get(2));
        email.setBounds(500, 200, 250, 30);
        email.setFont(new Font("Verdana", Font.PLAIN, 18));
        frame.add(email);
        
        tierLabel = new JLabel(userInfo.get(6) + " Account");
        tierLabel.setBounds(900,50,250,30);
        tierLabel.setFont(new Font("Verdana", Font.BOLD, 18));
        frame.add(tierLabel);
        
        nickname = new JLabel(userInfo.get(3) + "'s Account");
        nickname.setBounds(500, 50, 200, 30);
        nickname.setFont(new Font("Verdana", Font.PLAIN, 18));
        frame.add(nickname);
        
        profilePicture = new JLabel();
        profilePicture.setBounds(250, 25, 200, 200);
        
        String imagePath = "photos/photo_" + userInfo.get(userInfo.size()-2) + ".jpg";
    	ImageIcon imageIcon = new ImageIcon(imagePath);
    	Image image = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
    	ImageIcon scaledImageIcon = new ImageIcon(image);
    	profilePicture.setIcon(scaledImageIcon);
    	logoLabel.setBounds(30,820,150,30);
        frame.add(logoLabel);
        frame.add(profilePicture);

        uploadProfilePictureButton = new JButton("Change Profile Picture");// the option to change tthe profile picture
        uploadProfilePictureButton.setBounds(250, 235, 200, 30);
        uploadProfilePictureButton.setFocusable(false);
        uploadProfilePictureButton.addActionListener(this);
        frame.add(uploadProfilePictureButton);
        
        frame.setVisible(true);
    }
    private Image getScaledImage(String imagePath, int width, int height) { // getting scaled image
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return scaledImage;
    }
    public static List<String> extractPhotoReferences(String filename, String id) {// get what photos are assigned too your id
        List<String> photoReferences = new ArrayList<>();

        try {
            File file = new File(filename);
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String currentLine;
            boolean shouldExtract = false;
            while ((currentLine = reader.readLine()) != null) {
                String[] lineElements = currentLine.split("\\s+");
                String currentId = lineElements[0];

                if (currentId.equals(id)) {
                    shouldExtract = true;
                    for (int i = 1; i < lineElements.length; i++) {
                        photoReferences.add(lineElements[i]);
                    }
                } else if (shouldExtract) {
                    break; // Stop extracting after the relevant ID's line
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return photoReferences;
    }
    public static boolean isFileExists(String folderPath, String fileName) {//checking if the file we are looking for exists
        File file = new File(folderPath, fileName);
        return file.exists();
    }
    public static void incrementNumber(String filePath) {//incrementing the number in the file. Every time we assign a deleted name we have a unique number
        try {
            File numberFile = new File(filePath);

            BufferedReader reader = new BufferedReader(new FileReader(numberFile));
            String numberStr = reader.readLine();
            reader.close();

            int number = Integer.parseInt(numberStr);
            int incrementedNumber = number + 1;

            BufferedWriter writer = new BufferedWriter(new FileWriter(numberFile));
            writer.write(Integer.toString(incrementedNumber));
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void deleteRefFromFile(String filePath, String stringToDelete) {// deleting photo from the id
    	try {
            File inputFile = new File(filePath);
            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                String updatedLine = currentLine.replaceAll(stringToDelete, "");
                writer.write(updatedLine);
                writer.newLine();
            }

            writer.close();
            reader.close();

            // Replace the original file with the modified temp file
            Path inputPath = inputFile.toPath();
            Path tempPath = tempFile.toPath();
            Files.move(tempPath, inputPath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void renameFile(String filePath, String numberFilePath) {//renaming files to deleted
        try {
            File fileToRename = new File(filePath);
            File numberFile = new File(numberFilePath);

            BufferedReader reader = new BufferedReader(new FileReader(numberFile));
            String number = reader.readLine();
            reader.close();

            File renamedFile = new File(fileToRename.getParent(), "deleted_" + number);

            boolean renamed = fileToRename.renameTo(renamedFile);

            if (renamed) {
                System.out.println("yay " + renamedFile.getName());
            } else {
                System.out.println("no yay");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
   

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == editPhotosButton) {
            DiscoverPage discoverPage = new DiscoverPage(userInfo);
        }else if (e.getSource() == uploadProfilePictureButton) { // if upload profile is pessed we can choose the prof picture and change it
        	chooser.showOpenDialog(null);
        	File f = chooser.getSelectedFile();
            profilePicture.setIcon(new ImageIcon(f.toString()));
            filename = f.getAbsolutePath();
           
            BufferedImage img;
			try {
				img = ImageIO.read(f);
				Image scaledImg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
				ImageIcon icon = new ImageIcon(scaledImg);
				profilePicture.setIcon(icon);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
          
            frame.add(profilePicture);
            frame.setVisible(true);
            String sourceImagePath = filename;
            String targetImagePath = "photos/photo_" + userInfo.get(userInfo.size()-2) + ".jpg"; 
            
            try {
                Path sourcePath = Paths.get(sourceImagePath);
                Path targetPath = Paths.get(targetImagePath);

                Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING); //replacing current prof picture in the folder


            } catch (DirectoryNotEmptyException e1) {
                System.err.println(e1.getMessage());
            } catch (IOException e1) {
                System.err.println(e1.getMessage());
            }    
            
        }else if(e.getSource() == goBackButton) {
        	DiscoverPage discoverPage = new DiscoverPage(userInfo);
        	frame.dispose();
        }else if(e.getSource() == settingsButton) {
        	Settings settings = new Settings(userInfo);
        }else if(e.getSource() == editPhotosButton) {
        	PhotoEditor photoEditor = new PhotoEditor(userInfo);
        	frame.dispose();
        }else if (e.getSource()==postButton){
			PhotoEditor photoEditor = new PhotoEditor(userInfo);
		}else if(e.getSource() == otherUsersButton) {
			DiscoverPage discoverPAge = new DiscoverPage(userInfo);
			frame.dispose();
		}else if (e.getSource() == viewProfileButton) {
			ProfilePage profilePage = new ProfilePage(userInfo);
		}
    }
}
