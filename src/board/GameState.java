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
		ArrayList<QuadTreeNode> seen = new ArrayList<QuadTreeNode>();
		
		int xCoord = c.getX();
		int yCoord = c.getY();
		seen.add(state.root);
		state.root.setValue(Grid.tiles[xCoord][yCoord]);
		generate(9, state.root, state, seen, xCoord, yCoord);
	
		return state;
	}
	
	// Recursive algorithm to generate the tree
	public static void generate(int depth, QuadTreeNode current, QuadTree tree, List<QuadTreeNode> seen, int xPosition, int yPosition){
		if(depth == -1){
			return;
		}
		
		// If left available
		if(xPosition - 1 >= 0){
			tree.insertNodeLeft(current, Grid.tiles[xPosition - 1][yPosition]);
			seen.add(current);
		}
		
		//If right available
		if(xPosition + 1 < Grid.tiles[0].length){
			tree.insertNodeRight(current, Grid.tiles[xPosition + 1][yPosition]);
			seen.add(current);
		}
		
		// If down available
		if(yPosition + 1 < Grid.tiles.length){
			tree.insertNodeDown(current, Grid.tiles[xPosition][yPosition + 1]);
			seen.add(current);
		}
		
		// If up available
		if(yPosition - 1 >= 0){
			tree.insertNodeUp(current, Grid.tiles[xPosition][yPosition - 1]);
			seen.add(current);
		}
		if(current.left != null)
			generate(depth - 1, current.left, tree, seen, xPosition - 1, yPosition);
		if(current.right != null)
			generate(depth - 1, current.right, tree, seen, xPosition + 1, yPosition);
		if(current.down != null)
			generate(depth - 1, current.down, tree, seen, xPosition, yPosition + 1);
		if(current.up != null)
			generate(depth - 1, current.up, tree, seen, xPosition, yPosition - 1);
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
		
		// Can't find any DNA within depth radius
		if(bestScore == 0.0){
			return GameState.getRandomDirection();
		}
		
		// Else get the best direction with respect to the DNA found
		return GameState.getDirectionFromDNA(bestDNA, c);
		
	}
	
	public static Direction getDirectionFromDNA(DNA dna, Creature c){
		if ((Math.abs(dna.getX() - c.getX()) <= Math.abs(dna.getY() - c.getY()) 
				&& Math.abs(dna.getX() - c.getX()) != 0) || 
				(Math.abs(dna.getX() - c.getX()) > Math.abs(dna.getY() - c.getY()) 
				&& Math.abs(dna.getY() - c.getY()) == 0)){
			if (c.getX() < dna.getX()){
				return Direction.RIGHT;
			}
			return Direction.LEFT;
		}
		else{
			if (c.getY() > dna.getY()){
				return Direction.UP;
			}
			return Direction.DOWN;
		}
	}
	
	public static Direction getRandomDirection(){
		System.out.println("RANDOM DIRECTION CHOSEN");
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
