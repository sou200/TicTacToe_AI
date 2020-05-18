String checkWinner(){
  String winner=null;
  
  if(isDraw())
    winner = "Draw";
  
  // V
  if(board[0][0] == board[0][1] && board[0][1] == board[0][2] && board[0][0] != " ")
    winner=board[0][0];
  if(board[1][0] == board[1][1] && board[1][1] == board[1][2] && board[1][0] != " ")
    winner=board[1][0];
  if(board[2][0] == board[2][1] && board[2][1] == board[2][2] && board[2][0] != " ")
    winner=board[2][0];
  // H
  if(board[0][0] == board[1][0] && board[1][0] == board[2][0] && board[0][0] != " ")
    winner=board[0][0];
  if(board[0][1] == board[1][1] && board[1][1] == board[2][1] && board[0][1] != " ")
    winner=board[0][1];
  if(board[0][2] == board[1][2] && board[1][2] == board[2][2] && board[0][2] != " ")
    winner=board[0][2];
  // H&V
  if(board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != " ")
    winner=board[0][0];
  if(board[2][0] == board[1][1] && board[1][1] == board[0][2] && board[2][0] != " ")
    winner=board[2][0];
  
  return winner;
  
}

boolean isDraw(){
  int sps=0;
  for(String[] r:board){
    for(String c:r){
      if(c == " "){
        sps++;
      }
    }
  }
  return sps == 0;
}
