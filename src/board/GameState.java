package board;

import java.util.ArrayList;
import creature.*;
import quadtree.QuadTree;

public class GameState {
	
	public static ArrayList<Creature> creatures;
	public static boolean gameOver = false;
	
	public static QuadTree generateQuadTree(Creature c){
		QuadTree state = new QuadTree();
		
		int xCoord = c.getX();
		int yCoord = c.getY();
		
		// Depth of the tree should be equal to the "foresight" (i.e. the evolution stage) of the creature
		for(int i = 0; i < c.getEvolutionStage() + 1; i++){

			if(xCoord >= 1){
				
				state.insertNodeLeft(Grid.tiles[xCoord - i][yCoord]);
			}
			
			if(xCoord <= Grid.tiles[0].length){
				System.out.println(Grid.tiles[xCoord + i][yCoord]);
				state.insertNodeRight(Grid.tiles[xCoord + i][yCoord]);
			}
			
			if(yCoord >= 1){
				state.insertNodeUp(Grid.tiles[xCoord][yCoord - i]);
			}
			
			if(yCoord <= Grid.tiles.length){
				state.insertNodeDown(Grid.tiles[yCoord][yCoord + i]);
			}
			
		}
		
		return state;
	}
	
	public static Direction nextBestMove(Creature c){
		
		QuadTree options = generateQuadTree(c);
		
		return GameState.getRandomDirection();
	}
	
	public static Direction getRandomDirection(){
		int randInt = (int) (Math.random() * 4);
		
		switch(randInt){
		case 0:
			return Direction.LEFT;
		case 1:
			return Direction.RIGHT;
		case 2:
			return Direction.UP;
		default:
			return Direction.DOWN;
		}
	}

}
