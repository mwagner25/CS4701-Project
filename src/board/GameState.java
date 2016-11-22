package board;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import creature.*;
import quadtree.QuadTree;
import quadtree.QuadTreeNode;

public class GameState {
	
	public static ArrayList<Creature> creatures;
	public static boolean gameOver = false;
	
	public static QuadTree generateQuadTree(Creature c){
		QuadTree state = new QuadTree();
		QuadTreeNode root = state.root;
		
		int xCoord = c.getX();
		int yCoord = c.getY();
		
		// Depth of the tree should be equal to the "foresight" (i.e. the evolution stage) of the creature
		for(int i = 1; i <= c.getEvolutionStage() + 1; i++){			
			if(xCoord - i >= 0){
				state.insertNodeLeft(root, Grid.tiles[xCoord - i][yCoord]);
			}
			
			if(xCoord + i < Grid.tiles[0].length){
				state.insertNodeRight(root, Grid.tiles[xCoord + i][yCoord]);
			}
			
			if(yCoord - i >= 0){
				state.insertNodeUp(root, Grid.tiles[xCoord][yCoord - i]);
			}
			
			if(yCoord + i < Grid.tiles.length){
				state.insertNodeDown(root, Grid.tiles[xCoord][yCoord + i]);
			}
			
		}
		
		return state;
	}
	
	public static Direction nextBestMove(Creature c){
		
		QuadTree options = generateQuadTree(c);
		
		double bestScore = 0.0;
		DNA bestDNA = null;
		
		List<QuadTreeNode> unvisited = new ArrayList<QuadTreeNode>();
		unvisited.add(options.root);
		int steps = 0;
		
		
		// Breadth-first-search. Determine the tile to go to next
		while(!unvisited.isEmpty()){
			steps++;
			QuadTreeNode parent = unvisited.remove(0);
			parent.visited = true;
			
			List<QuadTreeNode> children = parent.getChildren();
			
			for (QuadTreeNode child : children){
				if(!child.visited){
					
					if(child.getValue().getDNA() != null){
						double score = child.getValue().getDNA().getValue() / steps;
						
						if(score > bestScore){
							bestScore = score;
							bestDNA = child.getValue().getDNA();
						}
					}
					
					unvisited.add(child);
					child.visited = true;
				}
			}
		}
		
		if(bestScore == 0.0){
			return GameState.getRandomDirection();
		}
		
		return GameState.getDirectionFromDNA(bestDNA, c);
		
	}
	
	public static Direction getDirectionFromDNA(DNA dna, Creature c){
		int verticalOrHorizontal = (int) (Math.random());
		if (verticalOrHorizontal == 0){
			if (c.getX() < dna.getX()){
				return Direction.RIGHT;
			}
			return Direction.LEFT;
		}
		else{
			if (c.getY() < dna.getY()){
				return Direction.UP;
			}
			return Direction.DOWN;
		}
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
