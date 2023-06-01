package PhotoCloud.Login;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import PhotoCloud.ViewUserAccount;
import Account.AccountPage;
import PhotoCloud.DiscoverPage;



public class LoginPage implements ActionListener {
	
	private ArrayList<ArrayList<String>> userData;
	private ArrayList<String> nothing = new ArrayList<String>();
	private JFrame frame = new JFrame(); // login checkin tthat the nickname and password match with the file
	private JButton loginButton;
	private JButton signinButton;
	private JTextField userIDField = new JTextField ();
	private JPasswordField userPasswordField = new JPasswordField();
	private JLabel userIDLabel = new JLabel("UserId:");
	private JLabel userPasswordLabel = new JLabel("Password:");
	private JLabel messageLabel = new JLabel("wrong info");
	private ImageIcon logoIcon = new ImageIcon("PhotoCloud/logo.png");
	private Image logoImage = logoIcon.getImage().getScaledInstance(550, 200, Image.SCALE_SMOOTH);
	private ImageIcon logoIconS = new ImageIcon(logoImage);
	private JLabel logoLabel = new JLabel(logoIconS);

	public LoginPage(ArrayList<ArrayList<String>> userDataOriginal) {
        userData = userDataOriginal;

        JFrame frame = new JFrame("Login Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 900);
        frame.setLayout(null);

        userIDLabel.setBounds(450, 375, 75, 25);
        userPasswordLabel.setBounds(450, 425, 75, 25);

        userIDField = new JTextField();
        userIDField.setBounds(525, 375, 200, 25);

        userPasswordField = new JPasswordField();
        userPasswordField.setBounds(525, 425, 200, 25);

        loginButton = new JButton("Login");
        loginButton.setBounds(525, 475, 100, 25);
        loginButton.setFocusable(false);
        loginButton.addActionListener(this);

        signinButton = new JButton("Sign In");
        signinButton.setBounds(625, 475, 100, 25);
        signinButton.setFocusable(false);
        signinButton.addActionListener(this);
        
        logoLabel.setBounds(300,175,550,200);
        frame.add(logoLabel);

        frame.add(userIDLabel);
        frame.add(userPasswordLabel);
        frame.add(userIDField);
        frame.add(userPasswordField);
        frame.add(loginButton);
        frame.add(signinButton);
        frame.add(messageLabel);

        frame.setVisible(true);
    }
	public ArrayList<String> isUserValid(String username, String password, ArrayList<ArrayList<String>> userData) {
		ArrayList<String> nothing = new ArrayList<String>();
        for (ArrayList<String> sublist : userData) {
            if (sublist.get(0).equals(username) && sublist.get(1).equals(password)) {
                return sublist;            
            }
        }
        return nothing;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==signinButton) {
			Signin signin = new Signin();
			frame.dispose();
		}
		if(e.getSource()==loginButton) {
			String userID = userIDField.getText();
			String password = String.valueOf(userPasswordField.getPassword());
			if(userData.contains(isUserValid(userID,password,userData))) {
				System.out.println(isUserValid(userID,password,userData));
				DiscoverPage discoverPage = new DiscoverPage(isUserValid(userID,password,userData));
				frame.dispose();
					
			}else {
				messageLabel.setForeground(Color.red);
				messageLabel.setText("Login no");
			}
		}
	}
		
}


