package PhotoCloud.Login;

import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class IDandPassword{
	private static ArrayList<ArrayList<String>> userData;

    public IDandPassword(String filePath){
        // reading the data from the file into an ArrayList of sublists
        this.userData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                ArrayList<String> sublist = new ArrayList<>();
                for (String part : parts) {
                    sublist.add(part);
                }
                this.userData.add(sublist);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<ArrayList<String>> getInfo(){
    	return userData;
    }
    public void setinfo(ArrayList<ArrayList<String>> userData) {
    	this.userData = userData;
    }
    
}
	
	

	


