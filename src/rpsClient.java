import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/********************************************************************************************
 * TCP RPS Final project for CSE 354 Computer Network class. Instructions to use this project
 * and how to run it.
 *
 * 1) Download IntelliJ
 *
 * 2) Download Amazon Corretto JDK 11
 *
 * 3) Open project in IntelliJ and run/build both of the programs. This is just in case the
 *    project is not built properly (out of the box).
 *
 * 4) Either in your termainl or in IntelliJ their termainal
 *    - Open three different terminals in order to run the server and the two clients.
 *
 * 5) In the terminals you will need to naivatage to the rpsServer and rpsClient files
 *    - example in terminal cd Desktop cd rps_FinalPorject [Note: However you name or where you
 *      place the project] cd out cd production cd rps_FinalProject
 *
 * 6) Run the rpsServer file first
 *    - example java rpsServer 6896 [Note: You do not need to use file extension just enter the
 *      file name.6898 is the port number but you can use any 4 digit port number.]
 *
 * 7) Run two copies of the rpsClient in two different termainl windows
 *    - example java rpsClient localhost 6898 [Note: refer to step 5 Note. Additionally, you
 *      will need to provide the IP address or localhost and the server port number.Since your
 *      just running it on your own computer and two different computers just enter localhost
 *      as shown in example.]
 *
 * 8) Play the game. The Clients will give you intructions on how to play.
 *
 *  [Note: Which ever client you run first must provide the input or choice first or the
 *  project will crash. You can provide q for quit or use ctrl-c to quit running the game.
 *  There are comments in the files themselves to help you understand the flow and how the
 *  program functions line by line. It also has some explanations for why I coded the logic
 *  in specific way. Enjoy the project/game.]
 ******************************************************************************************/

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
