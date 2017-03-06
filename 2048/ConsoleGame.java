//package com.cs323.assign1;
/**
import AIsolver;
import ActionStatus;
import Board;
import Direction;
**/
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;


// The main class of the Game.
public class ConsoleGame {
    public static int sumScore=0;
    public final static int avgCount=100;//N moves
    public final static int hintDepth=7;
    public final static boolean algo=true;//t-alphaBeta, f-minimax
    public final static boolean accuracyFlag =false;//true for old

    public static void main(String[] args) throws CloneNotSupportedException {

        System.out.println("*************************************");
        System.out.println("  Welcome to our 2048 game");
        System.out.println("*************************************");
        System.out.println();
        while(true) {
            printMenu();
            int choice;
            try {
                Scanner sc = new Scanner (System.in);
                choice = sc.nextInt();
                switch (choice) {
                    case 1:  playGame();
                             break;
                    case 2:  calculateAccuracy(accuracyFlag);//true for old
                             break;
                    case 3:  help();
                             break;
                    case 4:  return;
                    default: throw new Exception();
                }
            }
            catch(Exception e) {
                System.out.println("Wrong choice");
            }
        }
    }
    public static void help() {
        System.out.println("After pressing 1, to play the game"+"\n    Use 8 for UP\n\t6 for RIGHT\n\t2 for DOWN \n\t4 for LEFT\nType 'C' to play automatically and 'q' to exit\nPress enter to submit your choice.\n");

    }

    public static void printMenu() {
        System.out.println();
        System.out.println("Choices:");
        System.out.println("1. Play the 2048 Game");
        System.out.println("2. Estimate the Accuracy of AI Solver (It is going to take a while)");
        System.out.println("3. Help");
        System.out.println("4. Quit");
        System.out.println();
        System.out.println("Enter a number from 1-4:");
    }

    /**
     * Estimates the accuracy of the AI solver by running multiple games.
     *
     * @throws CloneNotSupportedException
     */
    public static void calculateAccuracy(boolean old) throws CloneNotSupportedException {
        if(old== true){
        int wins=0;
        int total=10;
        System.out.println("Running "+total+" games to estimate the accuracy:");

        for(int i=0;i<total;++i) {
            int hintDepth = 7;
            Board theGame = new Board();
            Direction hint = AIsolver.findBestMove(theGame, hintDepth,1);
            ActionStatus result=ActionStatus.CONTINUE;
            while(result==ActionStatus.CONTINUE || result==ActionStatus.INVALID_MOVE) {
                result=theGame.action(hint);

                if(result==ActionStatus.CONTINUE || result==ActionStatus.INVALID_MOVE ) {
                    hint = AIsolver.findBestMove(theGame, hintDepth);
                }
            }

            if(result == ActionStatus.WIN) {
                ++wins;
                System.out.println("Game "+(i+1)+" - won");
            }
            else {
                System.out.println("Game "+(i+1)+" - lost");
            }
        }

        System.out.println(wins+" wins out of "+total+" games.");
    }else{//New Accuracy
                sumScore=0;
                int hintDepth = 7;
                Board theGame = new Board();
                Direction hint = AIsolver.findBestMove(theGame, hintDepth,1);
                ActionStatus result=ActionStatus.CONTINUE;
                int ctr=0;
                while((result==ActionStatus.CONTINUE || result==ActionStatus.INVALID_MOVE)&& ctr<avgCount) {
                    result=theGame.action(hint);

                    if(result==ActionStatus.CONTINUE || result==ActionStatus.INVALID_MOVE ) {
                        hint = AIsolver.findBestMove(theGame, hintDepth,1);
                    }
                    ++ctr;
                }
         System.out.println(" Avg time taken to findBestMove for "+avgCount+" moves/actions= "+(sumScore)/(float)avgCount+" ms.");
        }
    }//end of Accuracy

    /**
     * Method which allows playing the game.
     */
    public static void playGame() throws CloneNotSupportedException {
        System.out.println("Start the Game!");

        //int hintDepth = 7;
        Board theGame = new Board();
        Direction hint = AIsolver.findBestMove(theGame, hintDepth);
        printBoard(theGame.getBoardArray(), theGame.getScore(), hint);

        try {
            InputStreamReader unbuffered = new InputStreamReader(System.in, "UTF8");
            char inputChar;

            ActionStatus result=ActionStatus.CONTINUE;
            while(result==ActionStatus.CONTINUE || result==ActionStatus.INVALID_MOVE) {
                inputChar = (char)unbuffered.read();
                //inputChar = 'a';
                if(inputChar=='\n' || inputChar=='\r') {
                    continue;
                }
                else if(inputChar=='8'||inputChar=='w'||inputChar=='W') {
                    result=theGame.action(Direction.UP);
                }
                else if(inputChar=='6'||inputChar=='d'||inputChar=='D') {
                    result=theGame.action(Direction.RIGHT);
                }
                else if(inputChar=='2'||inputChar=='s'||inputChar=='S') {
                    result=theGame.action(Direction.DOWN);
                }
                else if(inputChar=='4'||inputChar=='a'||inputChar=='A') {
                    result=theGame.action(Direction.LEFT);
                }
                else if(inputChar=='C'||inputChar=='c') {
                    result=theGame.action(hint);
                }
                else if(inputChar=='q') {
                    System.out.println("Game ended, user quit.");
                    break;
                }
                else {
                    System.out.println("Invalid key! Use 8 for UP, 6 for RIGHT, 2 for DOWN and 4 for LEFT. Type a to play automatically and q to exit. Press enter to submit your choice.");
                    continue;
                }

                if(result==ActionStatus.CONTINUE || result==ActionStatus.INVALID_MOVE ) {
                    hint = AIsolver.findBestMove(theGame, hintDepth);
                }
                else {
                    hint = null;
                }
                printBoard(theGame.getBoardArray(), theGame.getScore(), hint);

                if(result!=ActionStatus.CONTINUE) {
                    System.out.println(result.getDescription());
                }
            }
        }
        catch (IOException e) {
            System.err.println(e);
        }
    }

    /**
     * Prints the Board
     *
     * @param boardArray
     * @param score
     * @param hint
     */
    public static void printBoard(int[][] boardArray, int score, Direction hint) {
        System.out.println("-------------------------");
        System.out.println("Score:\t" + String.valueOf(score));
        System.out.println();
        //System.out.println("Hint:\t" + hint);
        System.out.println();

        for(int i=0;i<boardArray.length;++i) {
            for(int j=0;j<boardArray[i].length;++j) {
                System.out.print(boardArray[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println("-------------------------");
    }
}
