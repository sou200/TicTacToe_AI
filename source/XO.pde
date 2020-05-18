import javax.swing.JOptionPane;
import java.util.Arrays;

final color back = color(238,238,238);
final color XS = color(242,71,106);
final color OS = color(140,232,140);
final color lineC = color(70,70,70);


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

void setup(){

  size(400,400);
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

void draw(){
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

void drawLine(){
  stroke(lineC);
  for(int i=1; i<=2; i++){
  line((width/3)*i,0,(width/3)*i,height);
  line(0,(height/3)*i,width,(height/3)*i);
  }
}

void drawGame(){
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


void switchPlayer(){
    if(player == 1) player = 2;
    else player = 1;
}

// AI functions

void bestMove(){

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

int minimax(String board[][], int depth, boolean isMaximizing) {
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
