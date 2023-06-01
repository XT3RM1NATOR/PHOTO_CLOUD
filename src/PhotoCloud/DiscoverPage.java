package PhotoCloud;
import Account.PhotoEditor;
import Account.ProfilePage;
import PhotoCloud.Login.IDandPassword;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class DiscoverPage{
	
    private JFrame frame;
    private JPanel imagePanel;
    private JScrollPane scrollPane;
    private JScrollBar scrollBar;
    private ArrayList<String> userInfo = new ArrayList<String>();
    private IDandPassword idAndPassword;
    private static ArrayList<ArrayList<String>> idToPhoto = new ArrayList<ArrayList<String>>();
    private static ArrayList<String> newUser = new ArrayList<String>();
    private static ArrayList<ArrayList<String>> users = new ArrayList<ArrayList<String>>();
    private static ArrayList<JButton> buttons = new ArrayList<JButton>();
    
    public DiscoverPage(ArrayList<String> userInfo) {
    	this.userInfo = userInfo;
    	frame = new JFrame("Discover Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1200, 900));
        frame.setLayout(new BorderLayout());
        
        idAndPassword = new IDandPassword("Accounts.txt");

        // Create the left panel for buttons
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(250, leftPanel.getPreferredSize().height));

        JButton otherUsersButton = new JButton("Other Users");
        otherUsersButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        otherUsersButton.setMaximumSize(new Dimension(200, 50));
        leftPanel.add(otherUsersButton);

        // Add "Post" button
        JButton postButton = new JButton("Post");
        postButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        postButton.setMaximumSize(new Dimension(200, 50));
        leftPanel.add(postButton);

        // Add "View Profile" button
        JButton viewProfileButton = new JButton("View Profile");
        viewProfileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewProfileButton.setMaximumSize(new Dimension(200, 50));
        leftPanel.add(viewProfileButton);

        frame.add(leftPanel, BorderLayout.WEST);

        imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
        findPhoto("picturesToUsers.txt"); // assigning the values of each line(where users is and photo names are linked) too an ArrayList
        
        otherUsersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                		UserSearch userSearch = new UserSearch(idAndPassword.getInfo(), userInfo);
                		frame.dispose();
                    }
                });
        postButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            			PhotoEditor photoEditor = new PhotoEditor(userInfo);
            			frame.dispose();
                    }
                });
        viewProfileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                		ProfilePage profiePage = new ProfilePage(userInfo);
                		frame.dispose();
                    }
                });

        String folderPath = "DiscoverPage";
        int count = countFilesInFolder(folderPath);
        for (int i = 1; i < count; i++) {
        	String id = null; // every time we create a panel and then ad it to the scroll bar
        	String nickName = null;
        	final int photoNumber = i; // Final variable to use inside the ActionListener
        	if(!isFileExists("DiscoverPage", "photo_" + photoNumber + ".jpeg")) { // if there is no such file meaning it was deleted thenn we skip
        		continue;
        	}
        	for(ArrayList<String> line : idToPhoto) {
        		for(String element:line) {
        			if(element.length()>2) {
        				int a;
        				if(element.length()>12) {
        					a = (element.charAt(6) - '0')*10 + (element.charAt(7) - '0');
        				}else {
        					a = element.charAt(6) - '0';
        				}
        				
	        			if(a == i){
	        				id = line.get(0);
	        				break;
	        			}
	        			if(id!=null) {
	        				break;
	        			}
        			}
        		}
        	}
        	

        	for(ArrayList<String> line : idAndPassword.getInfo()) {
        		if(line.get(5).charAt(0) == id.charAt(0)){
        			newUser = line;
        			nickName = newUser.get(0);
        			break;
        		}
        	}
        	users.add(newUser);
        	ImageIcon pp = new ImageIcon("photos/photo_" + id + ".jpg");
        	Image image = pp.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        	pp = new ImageIcon(image);  	
        	JButton ppButton = new JButton(pp);
        	buttons.add(ppButton);
        	ppButton.setPreferredSize(new Dimension(100, 100));
        	ppButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                		for(int i=0;i<buttons.size();i++) {
                			if(buttons.get(i) == ppButton) {
                				ViewUserAccount viewUserAccount = new ViewUserAccount(userInfo, users.get(i));
                			}
                		}
                		
                	}
                
            });
        	
        	ImageIcon trash = new ImageIcon("PhotoCloud/trash.png");
        	Image trashh = trash.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        	trash = new ImageIcon(trashh);
        	
        	JButton nameButton = new JButton(nickName);
        	nameButton.setPreferredSize(new Dimension(200, 30));
        	nameButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ViewUserAccount viewUserAccount = new ViewUserAccount(userInfo, newUser);
                }
            });
        	

            JPanel photoPanel = new JPanel();
            photoPanel.setLayout(new BorderLayout());
            Border border = BorderFactory.createLineBorder(Color.GRAY, 2);
            photoPanel.setBorder(border);

            JPanel ppPanel = new JPanel();
            ppPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            ppPanel.add(ppButton);

            JLabel photoLabel = new JLabel(new ImageIcon(getScaledImage(folderPath + "/photo_" + i + ".jpeg", 400, 400)));
            photoLabel.setHorizontalAlignment(SwingConstants.CENTER);

            JPanel likesPanel = new JPanel();
            likesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

            JButton likeButton = new JButton("Like");
            likeButton.setPreferredSize(new Dimension(80, 30));

            JTextArea likesArea = new JTextArea("Likes: " + readLikes(photoNumber));
            likesArea.setEditable(false);
            likesArea.setPreferredSize(new Dimension(80, 15));

            likesPanel.add(likeButton);
            likesPanel.add(likesArea);
            likeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int likes = incrementLikes(photoNumber);
                    likesArea.setText("Likes: " + likes);
                }
            });

            JPanel commentsPanel = new JPanel();
            commentsPanel.setLayout(new BorderLayout());


            JTextArea commentsArea = new JTextArea();
            commentsArea.setPreferredSize(new Dimension(210, 100));
            commentsArea.setLineWrap(true);
            showComments(photoNumber, commentsArea);

            JButton commentButton = new JButton("write a comment");//comments
            commentButton.setPreferredSize(new Dimension(140, 30));
            likesPanel.add(commentButton);
            commentButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JFrame commentFrame = new JFrame("Add Comment");
                    commentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    commentFrame.setPreferredSize(new Dimension(400, 300));
                    commentFrame.setLayout(new BorderLayout());

                    JTextArea commentTextArea = new JTextArea();
                    JButton postButton = new JButton("Post");

                    postButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            String comment = commentTextArea.getText();
                            addComment(photoNumber, comment); // adding comments to the file of the photo
                            commentTextArea.setText("");
                            commentFrame.dispose();
                            showComments(photoNumber, commentsArea); // displaying the comments from the file
                        }
                    });

                    commentFrame.add(commentTextArea, BorderLayout.CENTER);
                    commentFrame.add(postButton, BorderLayout.SOUTH);
                    commentFrame.pack();
                    commentFrame.setLocationRelativeTo(null);
                    commentFrame.setVisible(true);
                }
            });
            
            
            commentsPanel.add(commentsArea);
            photoPanel.add(photoLabel, BorderLayout.CENTER);
            photoPanel.add(nameButton, BorderLayout.NORTH);
            photoPanel.add(likesPanel, BorderLayout.SOUTH);
            photoPanel.add(commentsPanel, BorderLayout.EAST);
            photoPanel.add(ppPanel, BorderLayout.WEST);
            imagePanel.add(photoPanel);   
            
            if(userInfo.get(6).charAt(0) == 'A') {
            	JButton trashButton = new JButton(trash);
            	trashButton.setPreferredSize(new Dimension(20, 20));
            	trashButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    	renameFile("DiscoverPage/"+"photo_" + photoNumber + ".jpeg", "deleted/deleted.txt"); //same thing as in the profilee page
                    	//check there for explanation
                    	incrementNumber("deleted/deleted.txt");
                        deleteRefFromFile("picturesToUsers.txt", " photo_" + photoNumber + ".jpeg");
                        renameFile("photoStats/"+"file_photo_" + photoNumber + ".txt", "deleted/deleted.txt");
                        incrementNumber("deleted/deleted.txt");
                        DiscoverPage discoverPage = new DiscoverPage(userInfo);
                        frame.dispose();
                    }
                });
            	likesPanel.add(trashButton,BorderLayout.WEST);
            }
        }
        

        scrollPane = new JScrollPane(imagePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollBar = scrollPane.getVerticalScrollBar();
        scrollBar.setUnitIncrement(20);

        frame.add(scrollPane, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static boolean isFileExists(String folderPath, String fileName) {// checking the existence of the file in the folder
        File file = new File(folderPath, fileName);
        return file.exists();
    }
    public static void incrementNumber(String filePath) { // changing the number in the "deleted" folder
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
    public static void deleteRefFromFile(String filePath, String stringToDelete) { //deleting photos from the ids
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
    public static void renameFile(String filePath, String numberFilePath) {
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

    
       
    public static int countFilesInFolder(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        int count = 0;
        for (File file : files) {
            if (file.isFile()) {
                count++;
            }
        }

        return count;
    }

    private Image getScaledImage(String imagePath, int width, int height) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return scaledImage;
    }

    private int readLikes(int photoNumber) { //getting the numbe of the likes for each photo
        String fileName = "photoStats/file_photo_" + photoNumber + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            return Integer.parseInt(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int incrementLikes(int photoNumber) { //changing the number of likes
    	String fileName = "photoStats/file_photo_" + photoNumber + ".txt";
        try (RandomAccessFile file = new RandomAccessFile(fileName, "rw")) {
            String line = file.readLine();
            int likes = Integer.parseInt(line) + 1;

            file.seek(0); // Here we arre moving the pointer to initial position in the file
            file.writeBytes(Integer.toString(likes)); // Overwriting the first line with updated likes

            return likes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void addComment(int photoNumber, String comment) {//adding comments
    	 String fileName = "photoStats/file_photo_" + photoNumber + ".txt";
    	    try (BufferedReader reader = new BufferedReader(new FileReader(fileName));
    	         BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
    	        String line;
    	        boolean hasContent = false;
    	        while ((line = reader.readLine()) != null) {
    	            if (!line.isEmpty()) {
    	                hasContent = true;
    	                break;
    	            }
    	        }
    	        if (hasContent) {
    	            writer.newLine();
    	        }
    	        writer.write(comment);
    	    } catch (IOException e) {
    	        e.printStackTrace();
    	    }
    }
    private void showComments(int photoNumber, JTextArea commentsArea) {
        String fileName = "photoStats/file_photo_" + photoNumber + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            StringBuilder comments = new StringBuilder();
            while ((line = reader.readLine()) != null) {

                comments.append(line).append("\n");
            }
            commentsArea.setText(comments.substring(2, comments.length()).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void findPhoto(String filePath){ //adding all the elements from the idtophoto folde
        // Read the data from the file into an ArrayList of sublists
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                ArrayList<String> sublist = new ArrayList<>();
                for (String part : parts) {
                	if(part!="") {
                		sublist.add(part);
                	}
                }
                idToPhoto.add(sublist);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}















