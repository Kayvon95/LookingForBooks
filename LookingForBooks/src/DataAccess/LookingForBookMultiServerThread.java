package DataAccess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Kayvon Rahimi on 21-5-2017.
 */
public class LookingForBookMultiServerThread extends Thread {
    private Socket socket = null;

    public LookingForBookMultiServerThread(Socket socket) {
        super("LookingForBookMultiServerThread");
        this.socket = socket;
    }

    public void run() {

        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));
        ) {
            String inputLine, outputLine;
            LookForBookProtocol prot = new LookForBookProtocol();
            outputLine = prot.processInput(null);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                outputLine = prot.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("This session has been terminated."))
                    break;
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
