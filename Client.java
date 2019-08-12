import javax.swing.JOptionPane;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
  Connects the client to the server is not connect it will return error but can still play game
*/
public class Client {
  static GameFrame gameFrame;
  public static void main(String[] args) {
     try {
       Socket socket = new Socket("127.0.0.1", 9898);
       Scanner inputStream = new Scanner(socket.getInputStream());
       PrintWriter outputStream = new PrintWriter(socket.getOutputStream(), true);
       String[] sendData = new String[2];
       String input = JOptionPane.showInputDialog("Enter UserName");
       String input2 = JOptionPane.showInputDialog("Enter password");

       sendData[0] = input;
       sendData[1] = input2;

       //login
       outputStream.println("1:" + sendData[0]+ " , " + sendData[1]);
       JOptionPane.showMessageDialog(null, inputStream.nextLine());
      //
      // //chnage score
       input = JOptionPane.showInputDialog("Enter userName to chnage score");
       input2 = JOptionPane.showInputDialog("Enter new score");
       sendData[0] = input;
       sendData[1] = input2;
       outputStream.println("3:" + sendData[0] + " , " + sendData[1]);
       JOptionPane.showMessageDialog(null, inputStream.nextLine());


     } catch(IOException e) {
      JOptionPane.showMessageDialog(null, "Error");
    }
    gameFrame = new GameFrame();
  }
}
