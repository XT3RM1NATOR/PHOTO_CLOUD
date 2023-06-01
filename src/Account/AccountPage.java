package Account;
import PhotoCloud.DiscoverPage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class AccountPage implements ActionListener {
    private JFrame frame = new JFrame();
    private JLabel welcomeLabel;
    private JButton viewProfileButton, editPhotosButton;
    private JButton discoverPageButton;
    private ArrayList<String> userInfo = new ArrayList<String>();

    public AccountPage(ArrayList<String> userInfo) {
        this.userInfo = userInfo;
        //create labels
        welcomeLabel = new JLabel("Welcome, " + userInfo.get(0) + "!");
        welcomeLabel.setBounds(50, 50, 200, 30);

        viewProfileButton = new JButton("View Profile");
        viewProfileButton.setBounds(50, 100, 150, 30);
        viewProfileButton.setFocusable(false);
        viewProfileButton.addActionListener(this);
        
        discoverPageButton = new JButton("Open Discover Page");
        discoverPageButton.setBounds(50, 200, 200, 30);
        discoverPageButton.setFocusable(false);
        discoverPageButton.addActionListener(this);

        editPhotosButton = new JButton("Edit Photos");
        editPhotosButton.setBounds(50, 150, 150, 30);
        editPhotosButton.setFocusable(false);
        editPhotosButton.addActionListener(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 900);
        frame.setLayout(null);
        frame.setVisible(true);

        frame.add(welcomeLabel);
        frame.add(viewProfileButton);
        frame.add(editPhotosButton);
        frame.add(discoverPageButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewProfileButton) {
        	// disposing of the frame of the Account Page
        	frame.dispose(); 
        	//initiating the ProfilePage class
            ProfilePage userProfilePage = new ProfilePage(userInfo);
        } else if (e.getSource() == editPhotosButton) {
            PhotoEditor photoEditor = new PhotoEditor(userInfo);
            frame.dispose();
        } else if (e.getSource() == discoverPageButton) {
        	DiscoverPage discoverPage = new DiscoverPage(userInfo);
        	frame.dispose();
        }
    }
}