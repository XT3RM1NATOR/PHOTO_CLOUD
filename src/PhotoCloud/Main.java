package PhotoCloud;
import PhotoCloud.Login.*;

public class Main {
    public static void main(String[] args) {
        IDandPassword idandPasswords = new IDandPassword("Accounts.txt");
        LoginPage loginPage = new LoginPage(IDandPassword.getInfo()); // initializing everything
        
    }
}
