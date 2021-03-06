# game-of-three

**Problem Definition**

The Goal is to implement a game with two independent units – the players –
communicating with each other using an API.

When a player starts, it incepts a random (whole) number and sends it to the second
player as an approach of starting the game. The receiving player can now always choose
between adding one of {-1, 0, 1} to get to a number that is divisible by 3. Divide it by three. The
resulting whole number is then sent back to the original sender. The same rules are applied until one player reaches the number 1(after the division).

Players can choose between playing manually or automatically.

**Solution Design Process**

I first intended to create 2 different Spring Boot projects. The first one was supposed 
to run as different instances for each player. The second project was going to be the "game"
and both players were going to communicate over the game. I actually implemented these 2 projects.
They are in the git history of this repository. 

Then, I decided on one Spring Boot project which has 2 parts and opens 3 threads,
1 for player-1, 1 for player-2, 1 for the game thread. These will run asynchronously and there is
1 server socket and 2 client sockets which accepts connection from the server socket. 

In one project solution, I also could not decide for the communication strategy until I re-read the
definition of the problem. 
First, I implemented a solution where there are 2 queues, 1 for players, 1 for game events.
Then, I decided on using both queues and event listeners to make use of the both patterns.
I used one singleton Blocking Queue instance to communicate from both players to game. 
I used an event listening strategy to communicate from game to players.

In the latest version, for each player, game event listeners are registered. 
When a player does something, a player event is pushed to the Blocking Queue to send player-to-game events.
When the game sends a command or information to the players, 
game events are created which then be listened by the previously created event listeners of the players.

**Environment**

java 8

telnet or netcat (a tcp network connection tool)

**Cleaning:** 

`gradlew clean` or `.\gradlew clean`

**Building:** ,

`gradlew build` or `.\gradlew build`

gradlew is a wrapper for gradle and it automatically 
downloads the gradle version specified in the properties

**Running:** 

You should first start the application and this means that you started the game. Start the application by

`java -jar game-of-three-0.0.1-SNAPSHOT.jar` or run it on your IDE (Eclipse, IntelliJ etc.) by using the main(String[] args) method in Application class as entrypoint.

gradlew is a wrapper for gradle and it automatically downloads the gradle version specified in the properties

Players can connect to the game through a tcp network tool via terminal.

``````````telnet localhost 6666``````````

or

``````````nc localhost 6666``````````

**Rules**

- The game of three allows only maximum 2 players at a given time.
- A player should first enter their user name.
- If a player enters the same user name with the current user, game gives the warning and closes the connection.
- A player should choose their player type.
- For start move, the number should be >=2.
- When game is over the connections are closed. If player wants to play again they should connect again.
- If there is a game started by another user, the connected player has only one option to be included in the already started game.

**Demo**

**Auto players**

Player 1:
``````````
telnet localhost 6666
Please enter your user name:
bb
You logged in.
Select your play type
Please enter one of {A, M}
A
Enter a value to start the game.
Playing automatically.
1267556604
Your opponent played. Your input is :422518868
Playing automatically.
1
You played with :1. And output is 140839623
Your opponent played. Your input is :46946541
Playing automatically.
0
You played with :0. And output is 15648847
Your opponent played. Your input is :5216282
Playing automatically.
1
You played with :1. And output is 1738761
Your opponent played. Your input is :579587
Playing automatically.
1
You played with :1. And output is 193196
Your opponent played. Your input is :64399
Playing automatically.
-1
You played with :-1. And output is 21466
Your opponent played. Your input is :7155
Playing automatically.
0
You played with :0. And output is 2385
Your opponent played. Your input is :795
Playing automatically.
0
You played with :0. And output is 265
Your opponent played. Your input is :88
Playing automatically.
-1
You played with :-1. And output is 29
Your opponent played. Your input is :10
Playing automatically.
-1``````````


Connection to host lost. 
``````````

Player 2
``````````
telnet localhost 6666
Please enter your user name:
bb
You logged in.
Select your play type
Please enter one of {A, M}
A
Enter a value to start the game.
Playing automatically.
1267556604
Your opponent played. Your input is :422518868
Playing automatically.
1
You played with :1. And output is 140839623
Your opponent played. Your input is :46946541
Playing automatically.
0
You played with :0. And output is 15648847
Your opponent played. Your input is :5216282
Playing automatically.
1
You played with :1. And output is 1738761
Your opponent played. Your input is :579587
Playing automatically.
1
You played with :1. And output is 193196
Your opponent played. Your input is :64399
Playing automatically.
-1
You played with :-1. And output is 21466
Your opponent played. Your input is :7155
Playing automatically.
0
You played with :0. And output is 2385
Your opponent played. Your input is :795
Playing automatically.
0
You played with :0. And output is 265
Your opponent played. Your input is :88
Playing automatically.
-1
You played with :-1. And output is 29
Your opponent played. Your input is :10
Playing automatically.
-1


Connection to host lost.
``````````

**Manual Players**

Player 1
``````````
telnet localhost 6666
Please enter your user name:
ss
You logged in.
Select your play type
Please enter one of {A, M}
M
Enter a value to start the game.
Please enter a number >=2
Your opponent played. Your input is :5
Please enter one of {1,0,-1}
1
You played with :1. And output is 2
Your opponent succeeded to calculate 1. You Lost!


Connection to host lost.
``````````
Player 2
``````````
Please enter your user name:
gg
You logged in.
Select your play type
Please enter one of {A, M}
M
Enter a value to start the game.
Please enter a number >=2
5
Your opponent played. Your input is :2
Please enter one of {1,0,-1}
1
You played with :1. And output is 1
You Won!


Connection to host lost.
``````````

Any combination of players can play together, one of them can be a manual player and the other can be Auto Player.

**Exceptional Scenarios**

``````````
Please enter your user name:
dd
There is a game already started.
Select your play type
Please enter one of {A, M}
You logged in.
XX
Please enter one of {A, M}
M
There is a game already started.
Opponent start value is 849952171
Please enter one of {1,0,-1}
2
Please enter one of {1,0,-1}
0
Your input does not result in a number divisable with 3, please enter again.
Please enter one of {1,0,-1}
``````````
Notice that: 

-There is no exit strategy when a game starts. For everything to work properly, any started game should be finalized. 

-Closing the terminal(s) which are connected to the game is not caught.

-If a manually playing player closes the terminal, the game waits for their input infinitely unless the application is restarted. 

-Players should reconnect to the game to play again.
