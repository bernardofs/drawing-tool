<p align="center"><img src="app/src/main/ic_launcher-web.png" alt="Drawing Tool" height="200px"></p>


# Drawing Tool
Android Application to execute different types of draws implemented on Android Studio in Java Programming language. The application offers the following kinds of draws.
* Draw a number
* Draw a participant
* Draw teams

## Draw a number

The user chooses some range of numbers, and the app draws a number randomly in this interval.

<img src="/images/OptionsNumberDraw.png" width="275" height="500"> <img src="/images/ResultNumberDraw.png" width="275" height="500">

## Draw a Participant 

The user can choose between two  options:
* Completly random draw
* Draw based on probabilities

The second one is important to use in draws like raffles because some participants have more chances to win than others. In this case, the probability of each participant should be described by the user.

<img src="/images/AddParticipantsProb.png" width="275" height="500"> <img src="/images/ResultParticipantProb.png" width="275" height="500">

## Draw a Team

The user can choose between two  options:
* Completly random draw
* Draw based on skills

The second one is important to try to balance teams. In this case, given the skill of each participant, the app builds a determined number of teams.
The user can also choose the level of randomness of the draw. If the user prefers using a more balanced draw, the draw is done a determined number of times based on heuristics, and the best solution is chosen. 

<img src="/images/OptionsTeamDraw.png" width="275" height="500"> <img src="/images/AddParticipantsDrawTeam.png" width="275" height="500"> <img src="/images/ResultDrawTeam.png" width="275" height="500">

## Edit Participant
Any participant can be added, updated or removed. To do that, the user must click on the item from the list. Then, a dialog is displayed to let the user edit participant.

<img src="/images/EditParticipant.png" width="275" height="500">

## Database
Participants can be saved and reused in other draws. It was used SQLite to store participants as their data of probability and skill. The participants can be added and removed in Saved Participants Screen. Besides, they can be added after adding a participant before executing a draw.  

<img src="/images/SavedParticipants.png" width="275" height="500">

