import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import javax.swing.JOptionPane; 
import java.util.Arrays; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class XO extends PApplet {




final int back = color(238,238,238);
final int XS = color(242,71,106);
final int OS = color(140,232,140);
final int lineC = color(70,70,70);


String board[][] = {

  {" "," "," "},
  {" "," "," "},
  {" "," "," "}
  
};

int digitBoard[][] = {
  {0,0,0},
  {0,0,0},
  {0,0,0}
};

int player = 1;
int w,h;

// AI variables

int bestMove[];
int score;
HashMap<String,Integer> scores;

public void setup(){

  
  background(back);
  strokeWeight(2);
  drawLine();
  
  w = width/3;
  h = height/3;
  
  scores = new HashMap();
  scores.put("X",10);
  scores.put("O",-10);
  scores.put("Draw",0);
}

public void draw(){
    if(checkWinner() != null){
        println(checkWinner());
        background(back);
        textSize(50);
        fill(25);
        textAlign(CENTER,CENTER);
        if(checkWinner() != "Draw")
        text("Player "+checkWinner()+" win",width/2,height/2);
        else
        text(checkWinner(),width/2,height/2);
     } else if(player == 2){
    /*int mCase[] = getRandom();
    board[mCase[1]][mCase[0]]="X";
    digitBoard[mCase[1]][mCase[0]]=1;
    drawGame();
    if(!checkWinner())
    switchPlayer();*/
    bestMove();
    drawGame();
    switchPlayer();
  } else if(mousePressed && player == 1){
   int i = floor(mouseX / w);
   int j = floor(mouseY / h);
   //int mCase = getCase(mouseX,mouseY);
   if(digitBoard[j][i] != 1){
     board[j][i]="O";
     digitBoard[j][i]=1;
     drawGame();
     switchPlayer();
     }
  }
}

public void drawLine(){
  stroke(lineC);
  for(int i=1; i<=2; i++){
  line((width/3)*i,0,(width/3)*i,height);
  line(0,(height/3)*i,width,(height/3)*i);
  }
}

public void drawGame(){
  background(back);
  drawLine();
  for(int r=0; r<3; r++){
    for(int c=0; c<3; c++){
      int x = w * c + w / 2;
      int y = h * r + h / 2;
      String spot = board[r][c];
      textSize(32);
      int R = w / 4;
      if (spot == "O") {
        noFill();
        stroke(OS);
        ellipse(x, y, R * 2, R * 2);
      } else if (spot == "X") {
        stroke(XS);
        line(x - R, y - R, x + R, y + R);
        line(x + R, y - R, x - R, y + R);
      }
    }
  }
  
}


public void switchPlayer(){
    if(player == 1) player = 2;
    else player = 1;
}

// AI functions

public void bestMove(){

  int bestScore = -MAX_INT;
  
  int move[] = {};
  for(int j=0; j<3; j++){
    for(int i=0; i<3; i++){
      if(board[j][i] == " "){
        board[j][i] = "X";
        score = minimax(board, 0, false);
        board[j][i] = " ";
        if(score > bestScore){
          bestScore = score;
          move = new int[]{j,i};
        }
      }
    }
  }
  board[move[0]][move[1]] = "X";
  digitBoard[move[0]][move[1]] = 1;
}

public int minimax(String board[][], int depth, boolean isMaximizing) {
  String result = checkWinner();
  if (result != null) {
    return scores.get(result);
  }

  if (isMaximizing) {
    int bestScore = -MAX_INT;
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        // Is the spot digitBoardilable?
        if (board[i][j] == " ") {
          board[i][j] = "X";
          int score = minimax(board, depth + 1, false);
          board[i][j] = " ";
          bestScore = max(score, bestScore);
        }
      }
    }
    return bestScore;
  } else {
    int bestScore = MAX_INT;
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        // Is the spot digitBoardilable?
        if (board[i][j] == " ") {
          board[i][j] = "O";
          int score = minimax(board, depth + 1, true);
          board[i][j] = " ";
          bestScore = min(score, bestScore);
        }
      }
    }
    return bestScore;
  }
}
public String checkWinner(){
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

public boolean isDraw(){
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
  public void settings() {  size(400,400); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "XO" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
