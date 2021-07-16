import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
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

public class rpsServer {

    public static void main(String args[]) throws Exception {
        String player1, player2;
        String player1Choice, player2Choice;
        int serverPort;

        // Checking if the port number argument is provided at commandline
        if (args.length == 1) {
            serverPort = Integer.parseInt(args[0]);
            // Creating an open server socket
            ServerSocket server = new ServerSocket(serverPort);
            System.out.println("--> SERVER IS RUNNING...\nPORT: " + serverPort);

            // While the server socket is not closed to keep receiving and send clients game
            while (!server.isClosed()) {
                // Player 1 socket
                Socket clientPlayer1 = server.accept();
                // How we will send messages to Player or Client 1 in Java
                DataOutputStream messageToClient1 = new DataOutputStream(clientPlayer1.getOutputStream());
                // How we handle receiving input from client 1 in Java
                BufferedReader player1Input = new BufferedReader(new InputStreamReader(clientPlayer1.getInputStream()));
                System.out.println("--> GOT PLAYER 1...");

                // Player 2 socket
                Socket clientPlayer2 = server.accept();
                // For lines 31 - 32 refer to comments on line 22 and 24
                DataOutputStream messageToClient2 = new DataOutputStream(clientPlayer2.getOutputStream());
                BufferedReader player2Input = new BufferedReader(new InputStreamReader(clientPlayer2.getInputStream()));
                System.out.println("--> GOT PLAYER 2...");

                // Receive the clients inputs and store in variable for game logic
                player1Choice = player1Input.readLine();
                System.out.println("--> PLAYER 1 WEAPON: " + player1Choice);
                player2Choice = player2Input.readLine();
                System.out.println("--> PLAYER 2 WEAPON: " + player2Choice);

                // Determining the results of the game
                player1 = gameLogicResultsForPlayer1(player1Choice, player2Choice);
                player2 = gameLogicResultsForPlayer2(player1Choice, player2Choice);

                System.out.println("--> THE VICTOR HAS BEEN DECIDED!!!!");

                // Checks to see if any player quits to close the server socket
                if (player1Choice.equals("q") || player2Choice.equals("q")) {
                    // Sending the message to the clients
                    messageToClient1.writeBytes("Player has quit. Closing game...");
                    messageToClient2.writeBytes("Player has quit. Closing game...");
                    // Closing both sockets
                    clientPlayer1.close();
                    clientPlayer2.close();
                }
                // Sending players the results of the current round
                messageToClient1.writeBytes(player1);
                messageToClient2.writeBytes(player2);
                // For server validation of current round results
                System.out.println("--> SENDING THE RESULTS:\n    PLAYER 1: " + player1 + "\n    PLAYER 2: " + player2);
                // Closing the sockets
                clientPlayer1.close();
                clientPlayer2.close();
            }
        } else {
            errorMessage();
        }
    }
    public static void errorMessage() {
        String errorMessage = "--> Error!\n" + "--> Must provide Port Number...\n" +
                "--> Template: [Command] [Filename (no file extension)] [Port Number]\n" +
                "--> Example: java rpsClient 1616\n" + "--> Please re-enter command...";
        System.out.println(errorMessage);
    }

    // To handle player 1 compared to player 2 value and yes this is needed or logic is invalid output for message
    public static String gameLogicResultsForPlayer1(String player1Choice, String player2Choice) {
        String results = "--> NONE VALUE PROVIDED...";

        // In Java we must use .equals() because we are comparing the values of two objects.
        // Can not use == because then we are comparing the locations.
        if (player1Choice.equals(player2Choice)) {
            results = "DRAW";
            return results;
        } else if (player1Choice.equals("r") && player2Choice.equals("p")) {
            results = "LOSES";
            return results;
        } else if (player1Choice.equals("p") && player2Choice.equals("r")) {
            results = "WIN";
            return results;
        } else if (player1Choice.equals("p") && player2Choice.equals("s")) {
            results = "LOSES";
            return results;
        } else if (player1Choice.equals("s") && player2Choice.equals("p")) {
            results = "WIN";
            return results;
        } else if (player1Choice.equals("s") && player2Choice.equals("r")) {
            results = "LOSES";
            return results;
        } else if (player1Choice.equals("r") && player2Choice.equals("s")) {
            results = "WIN";
            return results;
        } else if (player1Choice.equals("q") || player2Choice.equals("q")) {
            results = "QUIT";
            return results;
        } else
            System.out.println(results);
        return results;
    }

    // To handle player 2 compared to player 1 and yes this is needed or logic is invalid output for message
    public static String gameLogicResultsForPlayer2(String player1Choice, String player2Choice) {
        String results = "--> NONE VALUE PROVIDED...";

        if (player2Choice.equals(player1Choice)) {
            results = "DRAW";
            return results;
        } else if (player2Choice.equals("r") && player1Choice.equals("p")) {
            results = "LOSES";
            return results;
        } else if (player2Choice.equals("p") && player1Choice.equals("r")) {
            results = "WIN";
            return results;
        } else if (player2Choice.equals("p") && player1Choice.equals("s")) {
            results = "LOSES";
            return results;
        } else if (player2Choice.equals("s") && player1Choice.equals("p")) {
            results = "WIN";
            return results;
        } else if (player2Choice.equals("s") && player1Choice.equals("r")) {
            results = "LOSES";
            return results;
        } else if (player2Choice.equals("r") && player1Choice.equals("s")) {
            results = "WIN";
            return results;
        } else
            System.out.println(results);
        return results;
    }
}
