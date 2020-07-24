## Endless Games Kalah 
#### Introduction
This is a *Java RESTful Web Service* that runs a game of 6-stone Kalah. 
Kalah is played on a board with two rows of 6 holes and two stores, called *kalahah*. The two players (North
and South) sit at each side of the board. We assume that South always starts the game. The game usually opens
with 4 counters in every hole, but other amounts (2, 3, 5, 6) are possible. A move is made by selecting a nonempty hole at the player’s side of the board. The counters are lifted from this hole and sown in anti-clockwise
direction, starting with the next hole. The player’s own kalahah is included in the sowing, but the opponent’s
kalahah is skipped. In some implementations of Kalah, the hole from which the sowing is started is skipped
too (in the case that more than twelve stones are to be sown), but we will refrain from this rule.
There are three possible outcomes of a move: (1) if the last counter is put into the player’s kalahah, the player
is allowed to move again (such a move is called a *Kalah-move*); (2) if the last counter is put in an empty hole on
the player’s side of the board, a capture takes place: all stones in the opposite opponent’s pit and the last stone
of the sowing are put into the player’s store and the turn is over; and (3) if the last counter is put anywhere else,
the turn is over directly. The game ends whenever a move leaves no counters on one player’s side, in which
case the other player captures all remaining counters. The player who collects the most counters is the winner.
 
#### Kalah Rules 
Each of the two players has **six pits** in front of him/her. To the right of the six pits, each player has a larger pit, his Kalah or house. At the start of the game, six stones are put in each pit. 
The player who begins picks up all the stones in any of their own pits, and sows the stones on to the right, one in each of the following pits, including his own Kalah. No stones are put in the opponent's' Kalah. If the players last stone lands in his own Kalah, he gets another turn. This can be repeated any number of times before it's the other player's turn. 
When the last stone lands in an own empty pit, the player captures this stone and all stones in the opposite pit (the other players' pit) and puts them in his own Kalah. 
The game is over as soon as one of the sides run out of stones. The player who still has stones in his/her pits keeps them and puts them in his/hers Kalah. The winner of the game is the player who has the most stones in his Kalah. 
#### Endpoint design specification 
1. Creation of the game should be possible with the command: 
```
curl --header "Content-Type: application/json" \ 
--request POST \ http://<host>:<port>/games 
```
Response: 
```
HTTP code: 201 Response Body: { "id": "1234", "uri": "http://<host>:<port>/games/1234" } 
id: unique identifier of a game url: link to the game created
``` 
2. Make a move: 
```
curl --header "Content-Type: application/json" \ 
--request PUT \ http://<host>:<port>/games/{gameId}/pits/{pitId} 
gameId: unique identifier of a game pitId: id of the pit selected to make a move. Pits are numbered from 1 to 14 where 7 and 14 are the kalah (or house) of each player 
```
Response: 
```
HTTP code: 200 Response Body: {"id":"1234","url":"http://<host>:<port>/games/1234","status":{"1":"4","2":"4","3":"4","4":"4","5":"4","6":"4","7":"0","8":"4"," 9":"4","10":"4","11":"4","12":"4","13":"4","14":"0"}} 
status: json object key-value, where key is the pitId and value is the number of stones in the pit 
```