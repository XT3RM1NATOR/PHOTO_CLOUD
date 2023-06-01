package PhotoCloud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Account.AccountPage;
import Account.PhotoEditor;
import Account.ProfilePage;
import Account.Settings;
import PhotoCloud.Login.IDandPassword;
import PhotoCloud.Login.LoginPage;

public class ViewUserAccount implements ActionListener{
	private JFrame frame;
    private JLabel profilePicture;
    private JButton otherUsersButton = new JButton("DiscoverPage");
    private JButton postButton = new JButton("Post");
    private JButton viewProfileButton = new JButton("View Profile");
    //almost a copy of the profilepage with minor differences
    private JLabel name;
    private JLabel nickname;
    private JLabel email;
    private JLabel personalPhotosLabel;
    private JLabel tierLabel;
    
    private ImageIcon logoIcon = new ImageIcon("PhotoCloud/logo.png");
	private Image logoImage = logoIcon.getImage().getScaledInstance(150, 30, Image.SCALE_SMOOTH);
	private ImageIcon logoIconS = new ImageIcon(logoImage);
	private JLabel logoLabel = new JLabel(logoIconS);

    private ArrayList<String> userInfo = new ArrayList<String>();
    private ArrayList<String> userInfoNew = new ArrayList<String>();
    private int count = 0;
    
    public ViewUserAccount(ArrayList<String> userInfo, ArrayList<String> userInfoNew) {
    	this.userInfo = userInfo;
    	this.userInfoNew = userInfoNew;
    	 	
        frame = new JFrame("User Profile Page");
        frame.setSize(1200, 900);
        frame.setLayout(null);

        postButton.setBounds(50,500,125,25);
        postButton.setFocusable(false);
        postButton.addActionListener((ActionListener) this);
        frame.add(postButton);
        
        otherUsersButton.setBounds(50,400,125,25);
        otherUsersButton.setFocusable(false);
        otherUsersButton.addActionListener((ActionListener) this);
        frame.add(otherUsersButton);
        
        viewProfileButton.setBounds(50,300,125,25);
        viewProfileButton.setFocusable(false);
        viewProfileButton.addActionListener((ActionListener) this);
        frame.add(viewProfileButton);
        
        if(extractPhotoReferences("picturesToUsers.txt", userInfoNew.get(5)).size()>0) {
        	for(String photo:extractPhotoReferences("picturesToUsers.txt", userInfoNew.get(5))) {
        		JLabel photoLabel = new JLabel(new ImageIcon(getScaledImage("DiscoverPage/" + photo, 150, 150)));
        		if(count<=6) {
        			photoLabel.setBounds(200+200*count,400,200,200); // i go one by one and a the photos to the frame
        		}else {
        			photoLabel.setBounds(200+200*count,610,200,200);
        		}
        		frame.add(photoLabel);
        		count++;
        	}
        }
        
        personalPhotosLabel = new JLabel(userInfoNew.get(0) + "' Posted Photos");
        personalPhotosLabel.setBounds(550, 300, 200, 20);
        personalPhotosLabel.setFont(new Font("Montserrat", Font.PLAIN, 18));
        frame.add(personalPhotosLabel);
        
        tierLabel = new JLabel(userInfoNew.get(6) + " Account");
        tierLabel.setBounds(900,50,250,30);
        tierLabel.setFont(new Font("Verdana", Font.BOLD, 18));
        frame.add(tierLabel);
       
        name = new JLabel(userInfoNew.get(3) + " " + userInfoNew.get(4));
        name.setBounds(500, 150, 200, 30);
        name.setFont(new Font("Verdana", Font.BOLD, 18));
        frame.add(name);
          
        email = new JLabel("Email: " + userInfoNew.get(2));
        email.setBounds(500, 200, 250, 30);
        email.setFont(new Font("Verdana", Font.PLAIN, 18));
        frame.add(email);
        
        nickname = new JLabel(userInfoNew.get(3) + "'s Account");
        nickname.setBounds(500, 50, 200, 30);
        nickname.setFont(new Font("Verdana", Font.PLAIN, 18));
        frame.add(nickname);
        
        profilePicture = new JLabel();
        profilePicture.setBounds(250, 25, 200, 200);
        
        String imagePath = "photos/photo_" + userInfoNew.get(userInfoNew.size()-2) + ".jpg";
    	ImageIcon imageIcon = new ImageIcon(imagePath);
    	Image image = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
    	ImageIcon scaledImageIcon = new ImageIcon(image);
    	profilePicture.setIcon(scaledImageIcon);
        frame.add(profilePicture);
        
        logoLabel.setBounds(30,820,150,30);
        frame.add(logoLabel);
      
        frame.setVisible(true);
        
        
    }
    private Image getScaledImage(String imagePath, int width, int height) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return scaledImage;
    }
    public static List<String> extractPhotoReferences(String filename, String id) {
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
                    break; // we stop extracting info here after the relevant id
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return photoReferences;
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == postButton) {
            PhotoEditor photoEditor = new PhotoEditor(userInfo);
        }else if(e.getSource() == otherUsersButton) {
			DiscoverPage discoverPAge = new DiscoverPage(userInfo);
			frame.dispose();
		}else if (e.getSource() == viewProfileButton) {
			ProfilePage profilePage = new ProfilePage(userInfo);
		}
    }
    
    
}
