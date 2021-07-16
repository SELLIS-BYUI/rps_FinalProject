# rps_FinalProject

TCP RPS Final project for CSE 354 Computer Network class.
Instructions to use this project and to run it.
1) Download IntelliJ
2) Download Amazon Corretto JDK 11
3) Open project in IntelliJ and run/build both the programs. This is just in case the 
   terminal project is not built properly.
   
3) Either in your termainl or in IntehelliJ their termainal
    - Open three different terminals in order to run the server and the two clients.
    
4) In the terminals you will need to naivatage to the rpsServer and rpsClient files
    - example in terminal 
    cd Desktop
    cd rps_FinalPorject [Note: However you name or where you place the project]
    cd out
    cd production
    cd rps_FinalProject
    
5) Run the rpsServer file first
    - example 
    java rpsServer 6896 [Note: You do not need to use file extension just the file name. 
                               6898 is the port but you can use any 4 digit port number.]
                               
6) Run two copies of the rpsClient in two different termainl windows
    - example 
    java rpsClient localhost 6898 [Note: refer to step 5 Note. Additionally, you will
                                         need to provide the IP address or localhost
                                         and the server port number.]
                                         
7) Play the game. The Clients will give you intructions on how to play. 

[Note: Which ever client you run first must provide the input or choice first or the project
       will crash. You can provide q for quit or use ctrl+c quit running the game. There are
       comments in the files themselves to help you understand the flow and how the program
       functions line by line. It also has some explanations for why I code logic in specific
       way. Enjoy the project/game.]
