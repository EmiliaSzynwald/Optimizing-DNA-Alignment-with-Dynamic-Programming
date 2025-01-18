import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;
import java.lang.Math;

public abstract class NeedlemanWunsch{
    abstract int score(int i, int k);
    abstract int penalty(boolean edge);

    public String seqA;
    public String seqB;
    public int i;
    public int j;
    public int[][] seqMatrix;
    public int[][] tracebackMatrix;

    public NeedlemanWunsch(String A, String B){
        seqA = "."+A;  
        seqB = "."+B;
        i = seqA.length();
        j = seqB.length();
        seqMatrix = new int[i][j];
        tracebackMatrix = new int[i][j];
    }//super constructor

    public int maxOf(int upleft, int up, int left){
        return Math.max(upleft,Math.max(up,left));
    }//max value of the 3
    public int locMax(int max, int upleft, int up, int left, int row, int col){
        // if (max == upleft){return 1;}
        // else if (max == up){return 2;}
        // else {return 3;}

        if (row>0 && col>0 && max==upleft){return 1;}
        else if(row>0 && max == up){return 2;}
        else{return 3;}

    }//where max came from: preferance to upleft, then up, then left

    public int D(){
        int val;
        boolean isEdge;
        int ALIGNMENT_SCORE;
        for (int row = 0; row<i; row++){
            for (int col = 0; col<j; col++){

                if (row == 0 || col == 0 || row == i-1 || col == j-1) {isEdge = true;} 
                else {isEdge = false;}

                if (row == 0 && col == 0){
                    seqMatrix[row][col] = 0;
                    tracebackMatrix[row][col] = 0;
                }
                else if (row == 0){
                    val = seqMatrix[row][col-1] + penalty(isEdge);
                    seqMatrix[row][col] = val;
                    tracebackMatrix[row][col] = 3;//from left
                }
                else if (col == 0){
                    val = seqMatrix[row-1][col] + penalty(isEdge);
                    seqMatrix[row][col] = val;
                    tracebackMatrix[row][col] = 2;//from up
                }
                else{
                    int upleft = seqMatrix[row-1][col-1] + score(row,col);//1
                    int up = seqMatrix[row-1][col] + penalty(isEdge);//2
                    int left = seqMatrix[row][col-1] + penalty(isEdge);//3
                    //int max = Math.max(upleft,Math.max(up,left));
                    int max = maxOf(upleft,up,left);
                    
                    seqMatrix[row][col] = max;
                    tracebackMatrix[row][col] = locMax(max, upleft, up, left, row, col);
                }//not on an edge

            }//for cols
        }//for rows
        ALIGNMENT_SCORE = seqMatrix[i-1][j-1];
        return ALIGNMENT_SCORE;   
    }//D

    public void traceback(){
        String newA = "";
        String newB = "";
        String dashes = "";

        int row = i-1;//length of A-1
        int col = j-1;//length of B-1
        int TBMV = tracebackMatrix[row][col];//traceback matrix value

        while (TBMV != 0){
            if (TBMV == 1){//&& (seqA.charAt(row) == seqB.charAt(col))//soemthing wrong with upleft??
                newA = seqA.charAt(row) + newA;
                newB = seqB.charAt(col) + newB;
                dashes = (seqA.charAt(row) == seqB.charAt(col) ? "|" : " ") + dashes;//if A==B then "|" else " "
                row--;
                col--;
            }//from upleft
            else if (TBMV == 2){
                newA = seqA.charAt(row) + newA;
                newB = "-" + newB;
                dashes = " " + dashes;
                row--;
            }//from up
            else {
                newA = "-" + newA;
                newB = seqB.charAt(col) + newB;
                dashes = " " + dashes;
                col--;
            }//from left
            TBMV = tracebackMatrix[row][col];
        }//while going through TBMatrix

        System.out.println(newA + "\n" + dashes + "\n" + newB);
    }//traceback

}//abstract class

//////////////////////////

/// simple scoring scheme, no gap penalty
class scheme1 extends NeedlemanWunsch {  //put this in a different file
    int score(int i, int k) {
        if (seqA.charAt(i) == seqB.charAt(k)) return 1;
        else return 0;
    } 

    int penalty(boolean edge) { return 0; }

    public scheme1(String A, String B) { super(A,B); }
}// scheme1

// this is from the sample in the wikipedia article
class scheme2 extends NeedlemanWunsch { 
    int score(int i, int k) {
        if (seqA.charAt(i) == seqB.charAt(k)) return 1;
        else return -1;
    }

    int penalty(boolean edge) { return -1; }

    public scheme2(String A, String B) { super(A,B); }
}// scheme2


/// This is the scoring scheme you must apply to take the online QUIZ. 
/// Be sure that your implementation correctly identifies edge gaps.
class scheme3 extends NeedlemanWunsch { 
   int score(int i, int k) {
     if ((seqA.charAt(i)|32) == (seqB.charAt(k)|32)) return 3; 
     else return -1;
   }  // |32 allows matching upper or lower case.

   int penalty(boolean edge) {
      if (edge) return 0; else return -2;
   }

   public scheme3(String A, String B) { super(A,B); }
}//scheme3