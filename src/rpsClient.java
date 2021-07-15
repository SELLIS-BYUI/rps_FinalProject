import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class rpsClient {

    public static void main(String args[]) throws Exception {
        String playerInputChoice = "";
        String serverResponse;
        String localHost;
        int portNumber;
        boolean isPlaying = true;

        // Checking for arguments to connect to the server if not send and error message
        if (args.length == 2) {
            // First commandline argument
            localHost = args[0];
            // Second commandline argument
            portNumber = Integer.parseInt(args[1]);

            // Running our client TCP RPS game
            while (isPlaying) {
                // Establishing connection with the server
                Socket client = new Socket(localHost, portNumber);
                // Receiving input from player via keyboard
                BufferedReader playerChoice = new BufferedReader(new InputStreamReader(System.in));
                // Sending message to the server
                DataOutputStream messageToServer = new DataOutputStream(client.getOutputStream());
                // Receiving message from the server
                BufferedReader serverResponseMessage = new BufferedReader(new InputStreamReader(client.getInputStream()));

                // Tell the user the game and the choices are available
                rpsGameOptions();

                // Players choice
                playerInputChoice = playerChoice.readLine();
                // Sending message to the server for the logic of who wins
                messageToServer.writeBytes(playerInputChoice + "\n");
                System.out.println("--> YOUR WEAPON OF CHOICE: " + playerInputChoice);
                serverResponse = serverResponseMessage.readLine();

                // Handling if the player quits the game
                if (playerInputChoice.equals("q") || serverResponse.equals("q")) {
                    System.out.println(playerInputChoice + "\n--> THANK YOU FOR PLAYING");
                    isPlaying = false;
                } else if (serverResponse.equals(null)) {
                    System.out.println("--> SERVER NOT RESPONDING...\nQUITING THE GAME...");
                    client.close();
                    isPlaying = false;
                } else
                    System.out.println("--> GAME RESULT: " + serverResponse);
                // Closing the socket for the client
                client.close();
            }
        } else {
            errorMessage();
        }
    }

    // TCP RPS client game instructions
    public static void rpsGameOptions() {
        System.out.println("--> WELCOME TO RPS GAME\n--> CHOOSE YOUR WEAPON - r, p, s, or q:");
    }

    // If no arguments are provided at commandline for connecting to server
    public static void errorMessage() {
        String errorMessage = "--> Error!\n" + "--> Must provide IP Address / LocalHost and Port Number...\n" +
                "--> Template: [Command] [Filename (no file extension)] [IP Address or LocalHost] [Port Number]\n" +
                "--> Example: java rpsClient 10.0.0.1 1616\n" + "--> Please re-enter command...";
        System.out.println(errorMessage);
    }

}
