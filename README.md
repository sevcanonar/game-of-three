# game-of-three

####**Problem Definition**
The Goal is to implement a game with two independent units – the players –
communicating with each other using an API.

When a player starts, it incepts a random (whole) number and sends it to the second
player as an approach of starting the game. The receiving player can now always choose
between adding one of {-1, 0, 1} to get to a number that is divisible by 3. Divide it by three. The
resulting whole number is then sent back to the original sender. The same rules are applied until one player reaches the number 1(after the division).

Players can choose between playing manually or automatically.
####**Solution Design Process**
I first intended to create 2 different Spring Boot projects. The first one was supposed 
to run as different instances for each player. The second project was going to be the "game"
and both players were going to communicate over the game. I actually implemented these 2 projects.
They are in the git history of this repository. 

Then, I decided on one Spring Boot project
####**Solution**
####**Rules**

~~~~
- The game of three provides socket interfaces for 2 players.
- The starting person enters an integer >=2 and the opponent enters one of {-1,0,1} to be 
  able to reach a number that is divisable by 3. The game then divides the resulting number
  by 3 and sends the output to the first player, and game goes on like this until one of the player 
  reaches 1 as the output of their move. That user wins and other one loses.
- When game ends their connection is reset.
- Anyone with a netcat or telnet can connect to these sockets. Maximum 2 client can connect at the same time.
- Each Player can choose between automatic or manual play options
- Each Player is forced to enter their user name before entering the game.
- Each Player is forced to enter their play type before they start.
- If there is a game started by another user, game gives you the 
  only option to be included in the already started game 
- If you enter the same name with the other user, you are disconnected from the game and you should connect again.

- I used sending event objects to communicate from game to players.

- I used blocking queue to communicate from players to game.

