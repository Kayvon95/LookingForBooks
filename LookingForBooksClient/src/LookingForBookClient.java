import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Kayvon Rahimi on 21-5-2017.
 */
public class LookingForBookClient {
    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.err.println(
                    "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        //Get hostname(localhost) and port(9000) from program arguments.
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        //Attempt to create socket.
        try (
                Socket lfbSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(lfbSocket.getOutputStream(), true);
                BufferedReader bufferR = new BufferedReader(
                        new InputStreamReader(lfbSocket.getInputStream()));
        ) {
            BufferedReader stdIn =
                    new BufferedReader(new InputStreamReader(System.in));

            //Server+client strings to print respective outputs in console.
            String fromServer;
            String fromUser;

            while ((fromServer = bufferR.readLine()) != null) {
                System.out.println("LookingForBook Server::: " + fromServer);
                if (fromServer.equals("The session has been terminated."))
                    break;

                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    System.out.println("Your Input: " + fromUser);
                    out.println(fromUser);
                }
            }
            //Error handling for connection with unknown party or inability to connect.
        } catch (UnknownHostException e) {
            System.err.println("The host " + hostName + " is unknown.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }
}
