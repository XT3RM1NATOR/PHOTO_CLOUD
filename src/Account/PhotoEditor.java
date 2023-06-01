package Account;


import PhotoCloud.DiscoverPage;
import PhotoCloud.UserSearch;
import PhotoCloud.Login.IDandPassword;
import javax.swing.JPanel;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.awt.image.RenderedImage;
import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.lang.Math;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class PhotoEditor implements ActionListener {
    private JFrame frame = new JFrame();
    private ArrayList<String> userInfo = new ArrayList<String>();
    private JButton blurButton, sharpenButton, grayscaleButton, edgeDetectionButton, contrastButton, brightnessButton, goBackButton;
    private JButton downloadButton = new JButton();
    private JButton choosePhoto = new JButton("upload a photo");
    private JButton otherUsersButton = new JButton("DiscoverPage");
    private JButton postButton = new JButton("Post");
    private JButton viewProfileButton = new JButton("View Profile");
    //creating buttons with the text values;
    
    private ImageIcon blurIcon = new ImageIcon("PhotoCloud/blur.jpeg");
    private ImageIcon contrastIcon = new ImageIcon("PhotoCloud/sharpen.png");
    private ImageIcon grayscaleIcon = new ImageIcon("PhotoCloud/poopy.jpeg");
    private ImageIcon edgeDetectionIcon = new ImageIcon("PhotoCloud/EdgeDetection.png");
    private ImageIcon sharpenIcon = new ImageIcon("PhotoCloud/contrast.png");
    private ImageIcon brightnessIcon = new ImageIcon("PhotoCloud/brightness.png");
    private ImageIcon downloadIcon = new ImageIcon("PhotoCloud/download.jpeg");
    private ImageIcon logoIcon = new ImageIcon("PhotoCloud/logo.png");
    //getting Icons from images which are stored in the PhotoCloud file
    
    private Image blurImage = blurIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
    private Image contrastImage = contrastIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
    private Image grayscaleImage = grayscaleIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
    private Image edgeDetectionImage = edgeDetectionIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
    private Image sharpenImage = sharpenIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
    private Image brightnessImage = brightnessIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
    private Image downloadImage = downloadIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
    private Image logoImage = logoIcon.getImage().getScaledInstance(150, 30, Image.SCALE_SMOOTH);
    //now scaling them to make them of the size that i want them to be
    
    private Image image;
    private Image imageCopy;
    //later on these will serve to display and store the edited photos
    
    private ImageIcon blurButtonIconS = new ImageIcon(blurImage);
    private ImageIcon contrastButtonIconS = new ImageIcon(contrastImage);
    private ImageIcon grayscaleButtonIconS = new ImageIcon(grayscaleImage);
    private ImageIcon edgeDetectionButtonIconS = new ImageIcon(edgeDetectionImage);
    private ImageIcon sharpenButtonIconS = new ImageIcon(sharpenImage);
    private ImageIcon brightnessButtonIconS = new ImageIcon(brightnessImage);
    private ImageIcon downloadIconS = new ImageIcon(downloadImage);
    private ImageIcon logoIconS = new ImageIcon(logoImage);
    // now assigning the icons to the buttons
    
    private JLabel brightnessLabel = new JLabel("brightness");
    private JLabel contrastLabel = new JLabel("contrast");
    private JLabel grayscaleLabel = new JLabel("grayscale");
    private JLabel blurLabel = new JLabel("blur");
    private JLabel edgeDetectionLabel = new JLabel("edgeDetection");
    private JLabel sharpenLabel = new JLabel("sharpen");
    //just creating text labels
    
    private JLabel editedPicture = new JLabel();
    private JLabel logoLabel = new JLabel(logoIconS);
    private JFileChooser chooser = new JFileChooser(); // to get files from the computer
    
    private JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
    private JSlider sliderSharp = new JSlider(JSlider.HORIZONTAL, 0, 2, 1);
    private JSlider sliderContrast = new JSlider(JSlider.HORIZONTAL, 0, 20, 10);
    private JSlider sliderBlur = sliderContrast;
    //sliders for the filters
    
    private static IDandPassword idAndPassword = new IDandPassword("Accounts.txt");
    // will need the info from the file in the future

    public PhotoEditor(ArrayList<String> userInfo) {
        this.userInfo = userInfo;
        String tier = userInfo.get(6);
        
        //locating all the buttons
        logoIcon = new ImageIcon(logoImage);
        goBackButton = new JButton("go back");
        goBackButton.setBounds(50, 30, 75, 20);
        goBackButton.setFocusable(false);
        goBackButton.addActionListener(this);
        
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

        blurButton = new JButton();
        blurButton.setBounds(200, 625, 100, 100);
        blurButton.setFocusable(false);
        blurButton.addActionListener(this);
        blurButton.setIcon(blurButtonIconS);
        
        downloadButton.setBounds(1050, 100, 50, 50);
        downloadButton.setIcon(downloadIconS);
        downloadButton.setFocusable(false);
        downloadButton.addActionListener(this);

        sharpenButton = new JButton();
        sharpenButton.setBounds(340, 625, 100, 100);
        sharpenButton.setFocusable(false);
        sharpenButton.addActionListener(this);
        sharpenButton.setIcon(sharpenButtonIconS);
        
        grayscaleButton = new JButton();
        grayscaleButton.setBounds(480, 625, 100, 100);
        grayscaleButton.setFocusable(false);
        grayscaleButton.addActionListener(this);
        grayscaleButton.setIcon(grayscaleButtonIconS);
        
        edgeDetectionButton = new JButton();
        edgeDetectionButton.setBounds(620, 625, 100, 100);
        edgeDetectionButton.setFocusable(false);
        edgeDetectionButton.addActionListener(this);
        edgeDetectionButton.setIcon(edgeDetectionButtonIconS);
        
        contrastButton = new JButton();
        contrastButton.setBounds(760, 625, 100, 100);
        contrastButton.setFocusable(false);
        contrastButton.addActionListener(this);
        contrastButton.setIcon(contrastButtonIconS);
        
        brightnessButton = new JButton();
        brightnessButton.setBounds(900, 625, 100, 100);
        brightnessButton.setFocusable(false);
        brightnessButton.addActionListener(this);
        brightnessButton.setIcon(brightnessButtonIconS);
        
        brightnessLabel.setBounds(900,775, 75, 25);
        contrastLabel.setBounds(760,775, 75, 25);
        grayscaleLabel.setBounds(480,775, 75, 25);
        blurLabel.setBounds(200,775, 75, 25);
        edgeDetectionLabel.setBounds(620,775, 75, 25);
        sharpenLabel.setBounds(340,775, 75, 25);
        
        editedPicture.setBounds(400,170,400,400);
        
        choosePhoto.setBounds(450, 300, 300, 25);
        choosePhoto.setFocusable(false);
        choosePhoto.addActionListener(this);
        
        //assigning sizes and values of the sliders
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setBounds(400,50, 400, 75);
        
        sliderSharp.setMajorTickSpacing(1);
        sliderSharp.setPaintTicks(true);
        sliderSharp.setPaintLabels(true);
        sliderSharp.setBounds(400,50, 400, 75);
        
        sliderContrast.setMajorTickSpacing(5);
        slider.setMinorTickSpacing(1);
        sliderContrast.setPaintTicks(true);
        sliderContrast.setPaintLabels(true);
        sliderContrast.setBounds(400,50, 400, 75);
        
        // if the tier is Free meaning it starts from F then we add certain filters
        if(tier.charAt(0) == 'F') {
        	frame.add(blurButton);
            frame.add(sharpenButton);
            
            frame.add(blurLabel);
            frame.add(sharpenLabel);
        }else if(tier.charAt(0) == 'H') { // The same goes for Hobbyist
        	frame.add(contrastButton);
            frame.add(brightnessButton);
            frame.add(blurButton);
            frame.add(sharpenButton);
            
            frame.add(brightnessLabel);
            frame.add(contrastLabel);
            frame.add(blurLabel);
            frame.add(sharpenLabel);
            
        }else { // for Admin and Profi the filter set is the same
        	frame.add(grayscaleButton);
            frame.add(edgeDetectionButton);
            frame.add(contrastButton);
            frame.add(brightnessButton);
            frame.add(blurButton);
            frame.add(sharpenButton);
            
            frame.add(brightnessLabel);
            frame.add(contrastLabel);
            frame.add(grayscaleLabel);
            frame.add(blurLabel);
            frame.add(edgeDetectionLabel);
            frame.add(sharpenLabel);
            
        }
        logoLabel.setBounds(30,820,150,30);
        frame.add(logoLabel);
               
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200,900);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.add(choosePhoto);    
        frame.add(downloadButton);
        frame.add(postButton);       
        frame.add(editedPicture);
        frame.add(slider);   
        
    }
    //method to get the photo from the laptop
    public static BufferedImage getPhoto() {
    	JFileChooser chooser = new JFileChooser();
    	chooser.showOpenDialog(null);
    	File f = chooser.getSelectedFile();
        String filename = f.getAbsolutePath();  
        BufferedImage img = null;
		try {
			img = ImageIO.read(f);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return img;
    }
    //sharpen filter
    public static BufferedImage sharpenImage(BufferedImage image, float sharpness) {
    	// just decreasing the value from the slider cuz it is really sensitive
    	sharpness /=2;
        float[] sharpenMatrix = {
                -sharpness, -sharpness, -sharpness,
                -sharpness, (float) (1 + 8 * sharpness), -sharpness,
                -sharpness, -sharpness, -sharpness
        };
        //using a 3x3 Kernell Matrix
        Kernel kernel = new Kernel(3, 3, sharpenMatrix);
        ConvolveOp convolveOp = new ConvolveOp(kernel);
        return convolveOp.filter(image, null);
    }
    //edge detection filter
    //here is use the Sobel Algorithm
    //every timme we look at the matrix in the form for x:
    //-1 0 +1
    //-2 0 +2
    //-1 0 +1
    //the numbers are the weights of the pixels
    public static BufferedImage edgeDetection(BufferedImage img, int threshold) {
    	int h = img.getHeight(), w = img.getWidth(), p = 0;
		BufferedImage edgeImg = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY); // changing the image to the black and white one
		int[][] vert = new int[w][h];
		int[][] horiz = new int[w][h];
		int[][] edgeWeight = new int[w][h];
		for (int y=1; y<h-1; y++) {
			for (int x=1; x<w-1; x++) {
				vert[x][y] = (int)(img.getRGB(x+1, y-1)& 0xFF + 2*(img.getRGB(x+1, y)& 0xFF) + img.getRGB(x+1, y+1)& 0xFF // 0xFF means in the 255 colorway
					- img.getRGB(x-1, y-1)& 0xFF - 2*(img.getRGB(x-1, y)& 0xFF) - img.getRGB(x-1, y+1)& 0xFF); // get the overall value using weighted pixels
				horiz[x][y] = (int)(img.getRGB(x-1, y+1)& 0xFF + 2*(img.getRGB(x, y+1)& 0xFF) + img.getRGB(x+1, y+1)& 0xFF
					- img.getRGB(x-1, y-1)& 0xFF - 2*(img.getRGB(x, y-1)& 0xFF) - img.getRGB(x+1, y-1)& 0xFF);
				edgeWeight[x][y] = (int)(Math.sqrt(vert[x][y] * vert[x][y] + horiz[x][y] * horiz[x][y])); // finding the distance between them
				if (edgeWeight[x][y] > threshold) // if the weight is more than the threshold we turn everything white
					p = (255<<24) | (255<<16) | (255<<8) | 255;
				else 
					p = (255<<24) | (0<<16) | (0<<8) | 0;// vice versa
				edgeImg.setRGB(x,y,p);
			}
		}
		return edgeImg;
	}
    
    
    
    // to gray scale
    public static BufferedImage toGrayScale (BufferedImage img) {
    	int width = img.getWidth();
        int height = img.getHeight();

        BufferedImage grayscaleImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(img.getRGB(x, y));
                int average = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                Color grayColor = new Color(average, average, average);
                grayscaleImage.setRGB(x, y, grayColor.getRGB());
            }
        }

        return grayscaleImage;
	}
    //just adding a certain value to all the rgb colors making the photo brighter
    public static BufferedImage brightenImage (BufferedImage img, int sliderValue) {
		int r=0, g=0, b=0, rgb=0, p=0;
		int increment = sliderValue*2;
		BufferedImage newImage = new BufferedImage(
			img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for (int y=0; y<img.getHeight(); y+=1) {
			for (int x=0; x<img.getWidth(); x+=1) {
				rgb = img.getRGB(x, y);
				r = ((rgb >> 16) & 0xFF) + increment;
				g = ((rgb >> 8) & 0xFF) + increment;
				b = (rgb & 0xFF) + increment;
				if (r>255) r=255;
				if (g>255) g=255;
				if (b>255) b=255;
				p = (255<<24) | (r<<16) | (g<<8) | b;
				newImage.setRGB(x,y,p);
			}
		}
		return newImage;
	}
    // counting files in the folder i need this method to store the photos in the folder
    // so the names dont repeat and i can easily keep tack of them i just count the number of photos
    // and assign the name like photo_(number of files)+1.jpg
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
    //blur
    public static BufferedImage blurImage(BufferedImage sourceImage, int sliderValue) {
    	int radius = sliderValue; // how strong the blur is going to be
        int size = radius * 2 + 1; // has to be odd
        float weight = 1.0f / (size * size);
        float[] data = new float[size * size];

        for (int i = 0; i < data.length; i++) {
            data[i] = weight;
        }

        Kernel kernel = new Kernel(size, size, data);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null); // ConvolveOp.EDGE_NO_OP nothing happens to the edges
        BufferedImage i = op.filter(sourceImage, null);
        return i;

    }
    //light blur for the edge detection
    public static BufferedImage lightBlurImage(BufferedImage sourceImage) {
    	int radius = 2;
        int size = radius * 2 + 1;
        float weight = 1.0f / (size * size);
        float[] data = new float[size * size];

        for (int i = 0; i < data.length; i++) {
            data[i] = weight;
        }

        Kernel kernel = new Kernel(size, size, data);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        BufferedImage i = op.filter(sourceImage, null);
        return i;

    }
    //changing contrast
    public static BufferedImage adjustContrast(BufferedImage image, float contrast) {
    	 float gamma = 1f / contrast;

         RescaleOp rescaleOp = new RescaleOp((float) Math.pow(2, gamma), 0, null);
         return rescaleOp.filter(image, null);
    }
    //method for saving the edited image on the laptop
    public static void saveImage(Image image) {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            // The user selected a file location
            File selectedFile = fileChooser.getSelectedFile();

            // Save the image to the selected file location
            try {
                ImageIO.write((RenderedImage) image, "jpg", selectedFile);
                System.out.println("Image saved successfully!");
            } catch (Exception e) {
                System.out.println("Error saving image: " + e.getMessage());
            }
        }
    }
    public static void saveImage(Image image, String folderPath, String imageName) {
        // Convert Image to BufferedImage
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics().drawImage(image, 0, 0, null);
        
        // Create the output directory if it doesn't exist
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        
        // Create the output file path
        String outputPath = folderPath + File.separator + imageName;
        
        // Save the image to the specified path
        try {
            ImageIO.write(bufferedImage, "jpeg", new File(outputPath));
        } catch (IOException e) {
            System.out.println("Failed to save the image: " + e.getMessage());
        }
    }
    // every time i use another fillter another slider has to appear so to detect which one
    //is on the frame right now i have to detect it and remove
    public static boolean containsSlider(JFrame frame, JSlider slider) {
        Component[] components = frame.getContentPane().getComponents();

        for (Component component : components) {
            if (component instanceof JSlider && component.equals(slider)) {
                return true;
            }
        }

        return false;
    }
    
    // if the user posts a new photo i create a new file in the folder photo stats
    // it has the same id as the photo and contains likes and comments of the photo
    public static void createTextFile(String folderPath, String fileName) {
        // Create the output directory if it doesn't exist
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // Create the output file path
        String outputPath = folderPath + File.separator + fileName;

        // Write the content to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            // Write "0" on the first line
            writer.write("0");
            writer.newLine();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    // to know whose photos are i append the photo name to the user id 
    public static void appendStringToId(String filename, String id, String appendString) {
        try {
            File file = new File(filename);
            File tempFile = new File("temp.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.trim().isEmpty()) {
                    continue;  // Skip empty lines
                }
                if (currentLine.trim().startsWith(id)) {
                    currentLine += " " + appendString;
                }
                writer.write(currentLine + System.lineSeparator());
            }

            reader.close();
            writer.close();

            if (file.delete()) {
                tempFile.renameTo(file);
            } else {
                System.out.println("Failed to update the file. Please check file permissions.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //from image to buferimage for the filters
    public static BufferedImage toBufferedImage(Image image) {
        // Create a BufferedImage with the same dimensions as the Image
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Get the Graphics object from the BufferedImage
        Graphics graphics = bufferedImage.getGraphics();

        // Draw the Image onto the BufferedImage
        graphics.drawImage(image, 0, 0, null);

        // Dispose the Graphics object
        graphics.dispose();

        return bufferedImage;
    }

    
    public void actionPerformed(ActionEvent e) {
    	if(e.getSource() == choosePhoto) {
    		// at first we just choose the photo an then apply the filters
    		BufferedImage img = getPhoto();
    	    ImageIcon imageIconn = new ImageIcon(img);
            Image originalImagee = imageIconn.getImage();
            Image scaledImagee = originalImagee.getScaledInstance(400, 400, Image.SCALE_DEFAULT);
            ImageIcon newImageIconn = new ImageIcon(scaledImagee);
            
            image = newImageIconn.getImage();
            editedPicture.setIcon(newImageIconn);
            frame.add(editedPicture);
            frame.remove(choosePhoto);
            imageCopy = image;
            
    	}else if(e.getSource()==blurButton) {
    		imageCopy = image; // storing the image
    		if(containsSlider(frame, sliderSharp)) {
    			frame.remove(sliderSharp);
    			frame.add(sliderBlur);
    		}
    		if(containsSlider(frame, slider)) {
    			frame.remove(slider);
    			frame.add(sliderBlur);
    		}
    		if(containsSlider(frame, sliderContrast)) {
    			frame.remove(sliderContrast);
    			frame.add(sliderBlur);
    		}
    		//removing sliders from previous filters
    	    sliderBlur.addChangeListener(new ChangeListener() {
    	        public void stateChanged(ChangeEvent e) {
    	        	JSlider source = (JSlider) e.getSource();
    	            int sliderValue = source.getValue();

    	            ImageIcon imageIcon = new ImageIcon(blurImage(toBufferedImage(imageCopy), sliderValue));
    	            Image originalImage = imageIcon.getImage();
    	            Image scaledImage = originalImage.getScaledInstance(400, 400, Image.SCALE_DEFAULT);
    	            ImageIcon newImageIcon = new ImageIcon(scaledImage);
    	            image = scaledImage;
    	            editedPicture.setIcon(newImageIcon);
    	            frame.add(editedPicture);
    	            
    	        }
    	    });	
    	}else if(e.getSource()==brightnessButton) {
    		imageCopy = image;
    		if(containsSlider(frame, sliderSharp)) {
    			frame.remove(sliderSharp);
    			frame.add(slider);
    		}
    		if(containsSlider(frame, sliderContrast)) {
    			frame.remove(sliderContrast);
    			frame.add(slider);
    		}
    		if(containsSlider(frame, sliderBlur)) {
    			frame.remove(sliderBlur);
    			frame.add(slider);
    		}
    	    slider.addChangeListener(new ChangeListener() {
    	        public void stateChanged(ChangeEvent e) {
    	            JSlider source = (JSlider) e.getSource();
    	            int sliderValue = source.getValue();

    	            // Update the brightness of the photo
    	            ImageIcon imageIcon = new ImageIcon(brightenImage(toBufferedImage(imageCopy), sliderValue));
    	            Image originalImage = imageIcon.getImage();
    	            Image scaledImage = originalImage.getScaledInstance(400, 400, Image.SCALE_DEFAULT);
    	            ImageIcon newImageIcon = new ImageIcon(scaledImage);
    	            image = scaledImage;
    	            editedPicture.setIcon(newImageIcon);
    	            frame.add(editedPicture);
    	        }
    	    });    	    

    	}else if(e.getSource()==sharpenButton) {
    		imageCopy = image;
    		if(containsSlider(frame, slider)) {
    			frame.remove(slider);
    			frame.add(sliderSharp);
    		}
    		if(containsSlider(frame, sliderContrast)) {
    			frame.remove(sliderContrast);
    			frame.add(sliderSharp);
    		}
    		if(containsSlider(frame, sliderBlur)) {
    			frame.remove(sliderBlur);
    			frame.add(sliderSharp);
    		}
    	    sliderSharp.addChangeListener(new ChangeListener() {
    	        public void stateChanged(ChangeEvent e) {
    	            JSlider source = (JSlider) e.getSource();
    	            int sliderValue = source.getValue();

    	            // Update the brightness of the photo
    	            ImageIcon imageIcon = new ImageIcon(sharpenImage(toBufferedImage(imageCopy), sliderValue));
    	            Image originalImage = imageIcon.getImage();
    	            Image scaledImage = originalImage.getScaledInstance(400, 400, Image.SCALE_DEFAULT);
    	            ImageIcon newImageIcon = new ImageIcon(scaledImage);
    	            image = scaledImage;
    	            editedPicture.setIcon(newImageIcon);
    	            frame.add(editedPicture);
    	        }
    	    });

    	}else if(e.getSource()==grayscaleButton) {
    		imageCopy = image;
    		if(containsSlider(frame, sliderSharp)) {
    			frame.remove(sliderSharp);
    		}
    		if(containsSlider(frame, sliderContrast)) {
    			frame.remove(sliderContrast);
    			
    		}
    		if(containsSlider(frame, slider)) {
    			frame.remove(slider);
    		}
    		if(containsSlider(frame, sliderBlur)) {
    			frame.remove(sliderBlur);
    		}
    		ImageIcon imageIcon = new ImageIcon(toGrayScale(toBufferedImage(imageCopy)));
    		Image originalImage = imageIcon.getImage();
    		Image scaledImage = originalImage.getScaledInstance(400, 400, Image.SCALE_DEFAULT);
    		ImageIcon newImageIcon = new ImageIcon(scaledImage);
    		image = scaledImage;		
    		editedPicture.setIcon(newImageIcon);
    	}else if(e.getSource()==edgeDetectionButton) {
    		imageCopy = image;
    		if(containsSlider(frame, sliderSharp)) {
    			frame.remove(sliderSharp);
    			frame.add(slider);

    		}
    		if(containsSlider(frame, sliderContrast)) {
    			frame.remove(sliderContrast);
    			frame.add(slider);
    		}	
    		if(containsSlider(frame, sliderBlur)) {
    			frame.remove(sliderBlur);
    			frame.add(slider);
    		}
    	    slider.addChangeListener(new ChangeListener() {
    	        public void stateChanged(ChangeEvent e) {
    	            JSlider source = (JSlider) e.getSource();
    	            int sliderValue = source.getValue();
    	            System.out.print(sliderValue);
    	            // Update the brightness of the photo
    	            ImageIcon imageIcon = new ImageIcon(edgeDetection(lightBlurImage(toBufferedImage(imageCopy)), sliderValue));
    	            Image originalImage = imageIcon.getImage();	            
    	            Image scaledImage = originalImage.getScaledInstance(400, 400, Image.SCALE_DEFAULT);
    	            ImageIcon newImageIcon = new ImageIcon(scaledImage);
    	            image = scaledImage;
    	            editedPicture.setIcon(newImageIcon);            
    	            frame.add(editedPicture);       
    	        }
    	    });

    	}else if(e.getSource()==contrastButton) {
    		imageCopy = image;
    		if(containsSlider(frame, slider)) {
    			frame.remove(slider);
    			frame.add(sliderContrast);
    		}
    		if(containsSlider(frame, sliderSharp)) {
    			frame.remove(sliderSharp);
    			frame.add(sliderContrast);
    		}
    		if(containsSlider(frame, sliderBlur)) {
    			frame.remove(sliderBlur);
    			frame.add(sliderContrast);
    		}

    	    sliderContrast.addChangeListener(new ChangeListener() {
    	        public void stateChanged(ChangeEvent e) {
    	            JSlider source = (JSlider) e.getSource();
    	            int sliderValue = source.getValue();
    	            // Update the brightness of the photo
    	            ImageIcon imageIcon = new ImageIcon(adjustContrast(toBufferedImage(imageCopy), sliderValue));           
    	            Image originalImage = imageIcon.getImage();
    	            Image scaledImage = originalImage.getScaledInstance(400, 400, Image.SCALE_DEFAULT);
    	            ImageIcon newImageIcon = new ImageIcon(scaledImage);
    	            image = scaledImage;
    	            editedPicture.setIcon(newImageIcon);
    	            frame.add(editedPicture);
    	            
    	        }
    	    });  

	    }else if(e.getSource()==goBackButton) {
	    	AccountPage accountPage = new AccountPage(userInfo);
	        frame.dispose();
	    }else if(e.getSource() == downloadButton) {
	    	imageCopy = image;
		    saveImage(imageCopy);
		    frame.remove(editedPicture);
		    frame.add(choosePhoto);
		    	 
		}else if (e.getSource()==postButton){
			//if we post we count photos
			imageCopy = image;
		    int counter = countFilesInFolder("DiscoverPage");
		    //assign name with the counter
		    saveImage(imageCopy,"DiscoverPage", "photo_" + counter+ ".jpeg");
		    createTextFile("photoStats", "file_photo_" + counter + ".txt");
		    appendStringToId("picturesToUsers.txt", userInfo.get(userInfo.size()-2), "photo_" + counter+ ".jpeg");
		    DiscoverPage discoverPage = new DiscoverPage(userInfo);
		    frame.dispose();    	 
		}else if(e.getSource() == otherUsersButton) {
			DiscoverPage discoverPage = new DiscoverPage(userInfo);
			frame.dispose();
		}else if (e.getSource() == viewProfileButton) {
			ProfilePage profilePage = new ProfilePage(userInfo);
			// if the button is pressed we go to the profile
		}
	    
    }
}