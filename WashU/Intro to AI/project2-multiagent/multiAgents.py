# multiAgents.py
# --------------
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to http://ai.berkeley.edu.
# 
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and
# Pieter Abbeel (pabbeel@cs.berkeley.edu).


from util import manhattanDistance
from game import Directions
import random, util

from game import Agent

class ReflexAgent(Agent):
    """
    A reflex agent chooses an action at each choice point by examining
    its alternatives via a state evaluation function.

    The code below is provided as a guide.  You are welcome to change
    it in any way you see fit, so long as you don't touch our method
    headers.
    """


    def getAction(self, gameState):
        """
        You do not need to change this method, but you're welcome to.

        getAction chooses among the best options according to the evaluation function.

        Just like in the previous project, getAction takes a GameState and returns
        some Directions.X for some X in the set {NORTH, SOUTH, WEST, EAST, STOP}
        """
        # Collect legal moves and successor states
        
        legalMoves = gameState.getLegalActions()

        # Choose one of the best actions
        scores = [self.evaluationFunction(gameState, action) for action in legalMoves]
        bestScore = max(scores)
        bestIndices = [index for index in range(len(scores)) if scores[index] == bestScore]
        chosenIndex = random.choice(bestIndices) # Pick randomly among the best

        "Add more of your code here if you want to"
        
        return legalMoves[chosenIndex]

    def evaluationFunction(self, currentGameState, action):
        """
        Design a better evaluation function here.

        The evaluation function takes in the current and proposed successor
        GameStates (pacman.py) and returns a number, where higher numbers are better.

        The code below extracts some useful information from the state, like the
        remaining food (newFood) and Pacman position after moving (newPos).
        newScaredTimes holds the number of moves that each ghost will remain
        scared because of Pacman having eaten a power pellet.

        Print out these variables to see what you're getting, then combine them
        to create a masterful evaluation function.
        """
        # Useful information you can extract from a GameState (pacman.py)
        successorGameState = currentGameState.generatePacmanSuccessor(action)
        newPos = successorGameState.getPacmanPosition()
        newFood = successorGameState.getFood()
        newGhostStates = successorGameState.getGhostStates()
        newScaredTimes = [ghostState.scaredTimer for ghostState in newGhostStates]

        "*** YOUR CODE HERE ***"
        functionValue = 0
        
        prevFoodList = currentGameState.getFood().asList()
        newFoodList = newFood.asList()
        numPrevFood = len(prevFoodList)
        numNewFood = len(newFoodList)
        functionValue += 2*(numPrevFood - numNewFood)
        foodDistances = []
        for food in newFoodList:
            foodDistances.append(((newPos[0]-food[0])**2+(newPos[1]-food[1])**2)**0.5)
        if len(foodDistances) != 0:
            functionValue += 1/min(foodDistances)
        for i in [-1, 0, 1]:
            for j in [-1, 0, 1]:
                if i != 0 or j != 0:
                    for food in newFoodList:
                        if food == (newPos[0] + i, newPos[1] + j):
                            functionValue += 0.5
                    for ghost in newGhostStates:
                        if ghost.getPosition() == (newPos[0] + i, newPos[1] + j):
                            functionValue -= 1
        for ghost in newGhostStates:
            if ghost.getPosition() == newPos:
                functionValue -= 2
        
        #if eats a food: +2
        #adds reciprocal of distance to closest food
            #should add 1 if next to food
        #if food within 1 layer after actions: +0.5
        #if ghost within 1 layer actions: -1
        #if touches ghost: -2

        return functionValue

def scoreEvaluationFunction(currentGameState):
    """
    This default evaluation function just returns the score of the state.
    The score is the same one displayed in the Pacman GUI.

    This evaluation function is meant for use with adversarial search agents
    (not reflex agents).
    """
    return currentGameState.getScore()

class MultiAgentSearchAgent(Agent):
    """
    This class provides some common elements to all of your
    multi-agent searchers.  Any methods defined here will be available
    to the MinimaxPacmanAgent, AlphaBetaPacmanAgent & ExpectimaxPacmanAgent.

    You *do not* need to make any changes here, but you can if you want to
    add functionality to all your adversarial search agents.  Please do not
    remove anything, however.

    Note: this is an abstract class: one that should not be instantiated.  It's
    only partially specified, and designed to be extended.  Agent (game.py)
    is another abstract class.
    """

    def __init__(self, evalFn = 'scoreEvaluationFunction', depth = '2'):
        self.index = 0 # Pacman is always agent index 0
        self.evaluationFunction = util.lookup(evalFn, globals())
        self.depth = int(depth)

class MinimaxAgent(MultiAgentSearchAgent):
    """
    Your minimax agent (question 2)
    """

    def getAction(self, gameState):
        """
        Returns the minimax action from the current gameState using self.depth
        and self.evaluationFunction.

        Here are some method calls that might be useful when implementing minimax.

        gameState.getLegalActions(agentIndex):
        Returns a list of legal actions for an agent
        agentIndex=0 means Pacman, ghosts are >= 1

        gameState.generateSuccessor(agentIndex, action):
        Returns the successor game state after an agent takes an action

        gameState.getNumAgents():
        Returns the total number of agents in the game

        gameState.isWin():
        Returns whether or not the game state is a winning state

        gameState.isLose():
        Returns whether or not the game state is a losing state
        """
        "*** YOUR CODE HERE ***"

        possibleActions = gameState.getLegalActions(0)
        successorStates = [gameState.generateSuccessor(0, action) for action in possibleActions]
        
        
        actionValues = [self.minValue(succState, 1, 1) for succState in successorStates]
        bestIndices = [index for index in range(len(actionValues)) if actionValues[index] == max(actionValues)]
        return possibleActions[bestIndices[0]]
    
    def decide(self, gameState, agentIndex, depth):
        if agentIndex == gameState.getNumAgents():
            return self.maxValue(gameState, depth + 1)
        else:
            return self.minValue(gameState, agentIndex, depth)

    def minValue(self, gameState, agentIndex, depth):
        if depth > self.depth or gameState.isWin() or gameState.isLose():
            return self.evaluationFunction(gameState)
        value = float('inf')
        possibleActions = gameState.getLegalActions(agentIndex)
        for successor in [gameState.generateSuccessor(agentIndex, action) for action in possibleActions]:
            value = min(value, self.decide(successor, agentIndex + 1, depth))
        return value

    def maxValue(self, gameState, depth):
        if depth > self.depth or gameState.isWin() or gameState.isLose():
            return self.evaluationFunction(gameState)
        value = float('-inf')
        possibleActions = gameState.getLegalActions(0)
        for successor in [gameState.generateSuccessor(0, action) for action in possibleActions]:
            value = max(value, self.minValue(successor, 1, depth))
        return value

class AlphaBetaAgent(MultiAgentSearchAgent):
    """
    Your minimax agent with alpha-beta pruning (question 3)
    """

    def getAction(self, gameState):
        """
        Returns the minimax action using self.depth and self.evaluationFunction
        """
        "*** YOUR CODE HERE ***"
        
        min = float('inf')
        minAct = None
        max = float('-inf')
        maxAct = None  
        alpha = float('-inf')
        beta = float('inf')
        alpha2 = alpha
        beta2 = beta
        count =0  
        legalMoves = gameState.getLegalActions()
        scores = []
       
        for action in legalMoves:
            action2, val = self.findMoves(gameState.generateSuccessor(0, action),1,1,action,alpha2,beta2)
            if(val > max):
                max = val
                maxAct = action
            if val > alpha2:
                alpha2 = val
            if alpha2 >= beta2:
                return action,val

        return maxAct

    def findMoves(self,gameState, id,depth,action, alpha,beta):
            if(gameState.isWin()):
                return action,self.evaluationFunction(gameState)
            if(gameState.isLose()):
                return action,self.evaluationFunction(gameState)
            if((depth == (self.depth)) and (id == (gameState.getNumAgents()))):
                    return action, self.evaluationFunction(gameState)
           
            nextId = id +1
            nextDepth = depth
           
            if(id == gameState.getNumAgents()):
                nextId =1
                id = 0
                nextDepth = depth +1
            legalMoves = gameState.getLegalActions(int(id))
            total = 0
            min = float('inf')
            minAct = None
            max = float('-inf')
            maxAct = None  
            alpha2 = alpha
            beta2 = beta
            count =0  

            for action in legalMoves:
                count+=1
                move,val = self.findMoves(gameState.generateSuccessor(id, action),nextId,nextDepth,action,alpha2,beta2)
                
                if(id ==0):
                    if(val > max):
                        max = val
                        maxAct = action
                    if val > alpha2:
                        alpha2 = val
                    if alpha2 > beta2:
                        return action,val
                else:
                    if(val < min):
                        min = val
                        minAct = action
                    if val < beta2:
                        beta2 = val
                    
                    if alpha2 >beta2:
                        return action,val
               
            if(id == 0):
                return (maxAct,max)
            else:
                return(minAct,min)



class ExpectimaxAgent(MultiAgentSearchAgent):
    """
      Your expectimax agent (question 4)
    """

    def getAction(self, gameState):
        """
        Returns the expectimax action using self.depth and self.evaluationFunction

        All ghosts should be modeled as choosing uniformly at random from their
        legal moves.
        """
        "*** YOUR CODE HERE ***"
        possibleActions = gameState.getLegalActions(0)
        successorStates = [gameState.generateSuccessor(0, action) for action in possibleActions]
        
        
        actionValues = [self.expValue(succState, 1, 1) for succState in successorStates]
        bestIndices = [index for index in range(len(actionValues)) if actionValues[index] == max(actionValues)]
        return possibleActions[bestIndices[0]]
    
    def decide(self, gameState, agentIndex, depth):
        if agentIndex == gameState.getNumAgents():
            return self.maxValue(gameState, depth + 1)
        else:
            return self.expValue(gameState, agentIndex, depth)

    def expValue(self, gameState, agentIndex, depth):
        if depth > self.depth or gameState.isWin() or gameState.isLose():
            return self.evaluationFunction(gameState)
        value = 0
        possibleActions = gameState.getLegalActions(agentIndex)
        for successor in [gameState.generateSuccessor(agentIndex, action) for action in possibleActions]:
            value += (1/len(possibleActions))*(self.decide(successor, agentIndex + 1, depth))
        return value

    def maxValue(self, gameState, depth):
        if depth > self.depth or gameState.isWin() or gameState.isLose():
            return self.evaluationFunction(gameState)
        value = float('-inf')
        possibleActions = gameState.getLegalActions(0)
        for successor in [gameState.generateSuccessor(0, action) for action in possibleActions]:
            value = max(value, self.expValue(successor, 1, depth))
        return value

def betterEvaluationFunction(currentGameState):
    """
    Your extreme ghost-hunting, pellet-nabbing, food-gobbling, unstoppable
    evaluation function (question 5).

    DESCRIPTION: <write something here so we know what you did>
    """
    "*** YOUR CODE HERE ***"
 # Useful information you can extract from a GameState (pacman.py)
    #successorGameState = currentGameState.generatePacmanSuccessor(action)
    #newPos = successorGameState.getPacmanPosition()
    #newFood = successorGameState.getFood()
    #newGhostStates = successorGameState.getGhostStates()
    #newScaredTimes = [ghostState.scaredTimer for ghostState in newGhostStates]

    "*** YOUR CODE HERE ***"
    functionValue = 0
    pos = currentGameState.getPacmanPosition()
    foodList = currentGameState.getFood().asList()
    ghostStates = currentGameState.getGhostStates()
    scaredTimes = [ghostState.scaredTimer for ghostState in ghostStates]
    numFood = len(foodList)
    foodDistances = []
    
    functionValue -= len(currentGameState.getGhostStates())
    functionValue -= numFood/3

    for position in [(-1, -1), (0, -1), (1, -1), (-1, 0), (1, 0), (-1, 1), (0, 1), (1, 1)]:
        for ghost in ghostStates:
            if ghost.getPosition() == (pos[0] + position[0], pos[1] + position[1]) and ghost.scaredTimer == 0:
                functionValue -= 20          

    scaredTime = 0
    for time in scaredTimes:
        scaredTime += time
    functionValue += 1*scaredTime

    if currentGameState.isWin():
        functionValue += 1000
    if currentGameState.isLose():
        functionValue -= 1000

    return functionValue

# Abbreviation
better = betterEvaluationFunction
