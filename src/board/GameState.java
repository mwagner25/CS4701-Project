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
		// QuadTreeNode root = state.root;
		ArrayList<String> seen = new ArrayList<String>();
		
		int xCoord = c.getX();
		int yCoord = c.getY();
		
		seen.add(Integer.toString(xCoord) + "," + Integer.toString(yCoord));
		// Depth of the tree should be equal to the "foresight" (i.e. the evolution stage) of the creature
		// for(int i = 1; i <= c.getEvolutionStage() + 2; i++){
			
			
			
		// }
		
		return generate(9, state.root, state, seen, xCoord, yCoord);
		// return null;
	}
	
	public static QuadTree generate(int depth, QuadTreeNode current, QuadTree tree, ArrayList<String> seen, int xPosition, int yPosition){
		
		if(depth == 0){
			return tree;
		}
		
		// If at the far left side
		if(xPosition - 1 >= 0 && !seen.contains(Integer.toString(xPosition) + "," + Integer.toString(yPosition))){
			tree.insertNodeLeft(current, Grid.tiles[xPosition][yPosition]);
			seen.add(Integer.toString(xPosition) + "," + Integer.toString(yPosition));
			return generate(depth - 1, current.left, tree, seen, xPosition - 1, yPosition);
		}
		
		//If at the far right side
		if(xPosition + 1 < Grid.tiles[0].length && !seen.contains(Integer.toString(xPosition) + "," + Integer.toString(yPosition))){
			tree.insertNodeRight(current, Grid.tiles[xPosition][yPosition]);
			seen.add(Integer.toString(xPosition) + "," + Integer.toString(yPosition));
			return generate(depth - 1, current.right, tree, seen, xPosition + 1, yPosition);
		}
		
		// If at bottom of board
		if(yPosition - 1 >= 0 && !seen.contains(Integer.toString(xPosition) + "," + Integer.toString(yPosition))){
			tree.insertNodeDown(current, Grid.tiles[xPosition][yPosition]);
			seen.add(Integer.toString(xPosition) + "," + Integer.toString(yPosition));
			return generate(depth - 1, current.down, tree, seen, xPosition, yPosition - 1);
		}
		
		// If at top of board
		if(yPosition + 1 < Grid.tiles.length && !seen.contains(Integer.toString(xPosition) + "," + Integer.toString(yPosition))){
			tree.insertNodeUp(current, Grid.tiles[xPosition][yPosition]);
			seen.add(Integer.toString(xPosition) + "," + Integer.toString(yPosition));
			return generate(depth - 1, current.up, tree, seen, xPosition, yPosition + 1);
		}
		
		return null;
	}
	
	public static Direction nextBestMove(Creature c){
		
		// Random chance of choosing a random direction. TODO: Fix condition
//		if((int)(Math.random() * 2) < c.getEvolutionStage()){
//			return GameState.getRandomDirection();
//		}
		
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
