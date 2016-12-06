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
	
	// Main function for generating the quadtree. Creates the initial tree and generates
	// the tree based on the creature coordinates
	public static QuadTree generateQuadTree(Creature c){
		QuadTree state = new QuadTree();
		ArrayList<String> seen = new ArrayList<String>();
		
		int xCoord = c.getX();
		int yCoord = c.getY();
				
		generate(9, state.root, state, seen, xCoord, yCoord);
		
		return state;
	}
	
	
	// TODO: Fix below algorithm. Doesn't seem to be accounting for all possible steps. Check to see that correct tiles are being
	// added in the tree, because our BFS algorithm can't find the correct tiles with DNA on them.
	
	// Recursive algorithm to generate the tree
	public static void generate(int depth, QuadTreeNode current, QuadTree tree, ArrayList<String> seen, int xPosition, int yPosition){
		if(depth == 0){
			return;
		}
		
		// If at the far left side
		if(xPosition - 1 >= 0 && !seen.contains(Integer.toString(xPosition - 1) + "," + Integer.toString(yPosition))){
			tree.insertNodeLeft(current, Grid.tiles[xPosition - 1][yPosition]);
			seen.add(Integer.toString(xPosition) + "," + Integer.toString(yPosition));
			generate(depth - 1, current.left, tree, seen, xPosition - 1, yPosition);
		}
		
		//If at the far right side
		if(xPosition + 1 < Grid.tiles[0].length && !seen.contains(Integer.toString(xPosition + 1) + "," + Integer.toString(yPosition))){
			tree.insertNodeRight(current, Grid.tiles[xPosition + 1][yPosition]);
			seen.add(Integer.toString(xPosition) + "," + Integer.toString(yPosition));
			generate(depth - 1, current.right, tree, seen, xPosition + 1, yPosition);
		}
		
		// If at bottom of board
		if(yPosition + 1 < Grid.tiles.length && !seen.contains(Integer.toString(xPosition) + "," + Integer.toString(yPosition - 1))){
			tree.insertNodeDown(current, Grid.tiles[xPosition][yPosition + 1]);
			seen.add(Integer.toString(xPosition) + "," + Integer.toString(yPosition));
			generate(depth - 1, current.down, tree, seen, xPosition, yPosition + 1);
		}
		
		// If at top of board
		if(yPosition - 1 >= 0  && !seen.contains(Integer.toString(xPosition) + "," + Integer.toString(yPosition + 1))){
			tree.insertNodeUp(current, Grid.tiles[xPosition][yPosition - 1]);
			seen.add(Integer.toString(xPosition) + "," + Integer.toString(yPosition));
			generate(depth - 1, current.up, tree, seen, xPosition, yPosition - 1);
		}
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
						System.out.println("VALUE FOUND:" + child.getValue().getDNA().getValue());
						System.out.println("STEPS:" + steps);
						System.out.println("SCORE FOUND:" + score);
						
						if(score > bestScore){
							System.out.println("SCORE HAS BEEN RESET TO " + score);
							bestScore = score;
							bestDNA = child.getValue().getDNA();
						}
					}
					
					unvisited.add(child);
					child.visited = true;
				}
			}
		}
		
		// Can't find any DNA within depth radius
		if(bestScore == 0.0){
			System.out.println("THIS SHOULD NOT HAPPEN!!!!");
			return GameState.getRandomDirection();
		}
		
		// Else get the best direction with respect to the DNA found
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
