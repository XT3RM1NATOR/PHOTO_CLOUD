package PhotoCloud;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UserSearch {
    private ArrayList<ArrayList<String>> userList;
    private ArrayList<String> userInfo;
    private boolean found = false;
    private ImageIcon logoIcon = new ImageIcon("PhotoCloud/logo.png");
	private Image logoImage = logoIcon.getImage().getScaledInstance(150, 30, Image.SCALE_SMOOTH);
	private ImageIcon logoIconS = new ImageIcon(logoImage);
	private JLabel logoLabel = new JLabel(logoIconS);

    public UserSearch(ArrayList<ArrayList<String>> userList, ArrayList<String> userInfo) {
        this.userList = userList;
        this.userInfo = userInfo;

        JFrame frame = new JFrame("User Search");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 900);
        frame.setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        frame.add(centerPanel, BorderLayout.CENTER);

        JTextField nicknameField = new JTextField(20);
        centerPanel.add(nicknameField);

        JButton searchButton = new JButton("Search");
        centerPanel.add(searchButton);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchNickname = nicknameField.getText();

                ArrayList<String> foundUser = null;
                for (ArrayList<String> user : userList) {
                    if (user.get(0).equals(searchNickname)) {
                        ViewUserAccount viewUserAccount = new ViewUserAccount(userInfo, user); // just get the nickname and look for it
                        // in the arrayList of all the nicknames
                        System.out.print(user);
                        found = true;
                        frame.dispose();
                    }
                }
                if (!found) {
                    JOptionPane.showMessageDialog(frame, "User not found");
                }
            }
        });

        centerPanel.setBackground(Color.lightGray);
        logoLabel.setBounds(30,820,150,30);// adding logo
        centerPanel.add(logoLabel, BorderLayout.SOUTH);
        nicknameField.setFont(new Font("Arial", Font.PLAIN, 18));
        searchButton.setFont(new Font("Arial", Font.BOLD, 18));

        // Centering the frame on the screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}