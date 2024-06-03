var turn;
var board;
var winner;
var gameMode;

document.querySelector("#twoPlayerButton").addEventListener("click", function(){startGame(2)});
document.querySelector("#onePlayerButton").addEventListener("click", function(){startGame(1)});    

document.querySelector("#returnToMenuButtonInGame").addEventListener("click", displayMenu);

document.querySelector("#returnToMenuButton").addEventListener("click", displayMenu);
document.querySelector("#playAgainButton").addEventListener("click", function(){startGame(gameMode)});

displayMenu();

function displayMenu(){ //called when page is opened or when returned to menu after game over
    //hide game board screen
    document.querySelectorAll(".game").forEach((item)=>{
        item.style.visibility = 'hidden';
    });
    document.querySelector("#playerTurn").style.visibility = 'hidden';
    document.querySelector("#returnToMenuButtonInGame").style.visibility = 'hidden';
    document.querySelector("#gameMode").style.visibility = 'hidden';
    
    //hide game over screen
    document.querySelectorAll(".gameOver").forEach((item)=>{
        item.style.visibility = 'hidden';
    });
    document.querySelector("#result").style.visibility = 'hidden';

    //show menu screen
    document.querySelectorAll(".menu").forEach((item)=>{
        item.style.visibility = 'visible';
    });
}

function startGame(mode){ // called when game starts from menu or from Play Again
    turn = "X";
    board = [" ", " ", " ", " ", " ", " ", " ", " ", " "];
    winner = " ";
    gameMode = mode;

    //hide menu screen
    document.querySelectorAll(".menu").forEach((item)=>{
        item.style.visibility = 'hidden';
    });

    //hide game over screen
    document.querySelectorAll(".gameOver").forEach((item)=>{
        item.style.visibility = 'hidden';
    });
    document.querySelector("#result").style.visibility = 'hidden';


    //show game board screen
    document.querySelectorAll(".grid").forEach((item)=>{
        item.style.visibility = 'visible';
    });
    document.querySelectorAll(".square").forEach((item)=>{
        item.style.visibility = 'visible';
        item.innerHTML = "";
    });

    document.querySelector("#playerTurn").style.visibility = 'visible';
    document.querySelector("#returnToMenuButtonInGame").style.visibility = 'visible';
    document.querySelector("#gameMode").style.visibility = 'visible';

    if(gameMode == 2){
        document.querySelector("#gameMode").innerHTML = "2 Player"
        document.querySelector("#playerTurn").innerHTML = "Player X's turn";
    }
    else{
        document.querySelector("#gameMode").innerHTML = "1 Player"
        document.querySelector("#playerTurn").innerHTML = "User is X";
    }
    

    for(let i = 1; i <= 9; ++i){
        let currentSquare = document.querySelector("#s" + i);
        currentSquare.addEventListener("click", async function(){
            if(currentSquare.innerHTML != "X" && currentSquare.innerHTML != "O"){ // square not filled in yet
                if(gameMode == 2){ //two player
                    currentSquare.innerHTML = turn;
                    board[i-1] = turn;
                    if(done() || full()){
                        turn = "";
                        setTimeout(gameOver, 500);
                        return;
                    }
                    if(turn == "X"){
                        turn = "O";
                        document.querySelector("#playerTurn").innerHTML = "Player O's turn";
                    }
                    else{
                        turn = "X";
                        document.querySelector("#playerTurn").innerHTML = "Player X's turn";
                    }
                }
                else{ //one player
                    currentSquare.innerHTML = "X";
                    board[i-1] = "X";
                    if(done() || full()){
                        //setTimeout(gameOver, 500);
                        await sleep(500);
                        return gameOver();
                    }
                    await sleep(267);
                    playerOMove();
                    if(done() || full()){
                        //setTimeout(gameOver, 500);
                        await sleep(500);
                        return gameOver();
                    }
                    
                }
            }        
        });
    }
}
function sleep(ms) {
    return new Promise(resolve => {
        setTimeout(resolve, ms)
    });
}


function playerOMove(){
    //      If O can win, win
    //      if X can win, block one path.
    //      else, go in a random available square
    // 0 1 2
    // 3 4 5
    // 6 7 8
    var moved = false;
    var boards = [[0, 1, 2], [3, 4, 5], [6, 7, 8], [0, 3, 6], [1, 4, 7], [2, 5, 8], [0, 4, 8], [2, 4, 6]];
    boards.random
    boards.forEach((deck)=>{
        if(!moved){
            let arr = [board[deck[0]], board[deck[1]], board[deck[2]]];
            arr.sort();
            if((arr[0] == " " && arr[1] == "O" && arr[2] == "O") || (arr[0] == " " && arr[1] == "X" && arr[2] == "X")){    
                for(let i = 0; i < 2; ++i){
                    if(board[deck[i]] == " "){
                        document.querySelector("#s" + (deck[i] + 1)).innerHTML = "O";
                        board[deck[i]] = "O";
                        moved = true;
                        return;
                    }
                }  
            }
        }
        
    });
    boards.forEach((deck)=>{
        if(!moved){
            let arr = [board[deck[0]], board[deck[1]], board[deck[2]]];
            arr.sort();
            if(arr[0] == " " && arr[1] == "X" && arr[2] == "X"){    
                for(let i = 0; i < 2; ++i){
                    if(board[deck[i]] == " "){
                        document.querySelector("#s" + (deck[i] + 1)).innerHTML = "O";
                        board[deck[i]] = "O";
                        moved = true;
                        return;
                    }
                }  
            }
        }
        
    });
    if(!moved){
        var availableIndices = [];
        for(let i = 0; i < 9; ++i){
            if(board[i] == " "){
                availableIndices.push(i);
            }
        }
        let indexOfMove = availableIndices[Math.floor(availableIndices.length*Math.random())];
        document.querySelector("#s" + (indexOfMove + 1)).innerHTML = "O";
        board[indexOfMove] = "O";
        return;
    }
    
}

function gameOver(){

    if(winner != " "){
        document.querySelector("#result").innerHTML = "Player " + winner + " Wins";
    }
    else{
        document.querySelector("#result").innerHTML = "The Game is a Draw";
    }

    //hide game board screen
    document.querySelectorAll(".game").forEach((item)=>{
        item.style.visibility = 'hidden';
    });
    document.querySelector("#playerTurn").style.visibility = 'hidden';
    document.querySelector("#returnToMenuButtonInGame").style.visibility = 'hidden';
    document.querySelector("#gameMode").style.visibility = 'hidden';
    

    //show game over screen
    document.querySelectorAll(".gameOver").forEach((item)=>{
        item.style.visibility = 'visible';
    });
    document.querySelector("#result").style.visibility = 'visible';
    document.querySelector("#returnToMenuButton").style.visibility = 'visible';
    document.querySelector("#playAgainButton").style.visibility = 'visible';
}

function gameOver(){

    if(winner != " "){
        document.querySelector("#result").innerHTML = "Player " + winner + " Wins";
    }
    else{
        document.querySelector("#result").innerHTML = "The Game is a Draw";
    }

    //hide game board screen
    document.querySelectorAll(".game").forEach((item)=>{
        item.style.visibility = 'hidden';
    });
    document.querySelector("#playerTurn").style.visibility = 'hidden';
    document.querySelector("#returnToMenuButtonInGame").style.visibility = 'hidden';
    document.querySelector("#gameMode").style.visibility = 'hidden';
    

    //show game over screen
    document.querySelectorAll(".gameOver").forEach((item)=>{
        item.style.visibility = 'visible';
    });
    document.querySelector("#result").style.visibility = 'visible';
    document.querySelector("#returnToMenuButton").style.visibility = 'visible';
    document.querySelector("#playAgainButton").style.visibility = 'visible';
}

function full(){
    for(let i = 0; i < 9; ++i){
        if(board[i] != "X" && board[i] != "O"){
            return false;
        }
    }
    return true;
}

function done(){
    // board indices
    // 0 1 2
    // 3 4 5
    // 6 7 8
    for(let i = 0; i < 3; ++i){
        if((board[i] == "X" || board[i] == "O") && board[i+3] == board[i] && board[i+6] == board[i]){
            winner = board[i] == "X" ? "X" : "O";
            return true;
        }
        if((board[3*i] == "X" || board[3*i] == "O") && board[3*i+1] == board[3*i] && board[3*i+2] == board[3*i]){
            winner = board[3*i] == "X" ? "X" : "O";
            return true;
        }
    }
    if((board[0] == "X" || board[0] == "O") && board[4] == board[0] && board[8] == board[0]){
        winner = board[0] == "X" ? "X" : "O";
        return true;
    }
    if((board[2] == "X" || board[2] == "O") && board[4] == board[2] && board[6] == board[2]){
        winner = board[2] == "X" ? "X" : "O";
        return true;
    }
    return false;
}
