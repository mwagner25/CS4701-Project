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
	
	
	// TODO: Fix below algorithm. Doesn't seem to be accounting for all possible steps. Check to see that correct tiles are being
	// added in the tree, because our BFS algorithm can't find the correct tiles with DNA on them.
	
	// Recursive algorithm to generate the tree
	public static void generate(int depth, QuadTreeNode current, QuadTree tree, List<QuadTreeNode> seen, int xPosition, int yPosition){
		if(depth == -1){
//			if(xPosition - 1 >= 0 && current.getValue().left == false){
//				tree.insertNodeLeft(current, Grid.tiles[xPosition - 1][yPosition]);
//				current.getValue().left = true;
//				//seen.add(Integer.toString(xPosition - 1) + "," + Integer.toString(yPosition));
//			}
//			if(xPosition + 1 < Grid.tiles[0].length && current.getValue().right == false){
//				tree.insertNodeRight(current, Grid.tiles[xPosition + 1][yPosition]);
//				current.getValue().right = true;
//				//seen.add(Integer.toString(xPosition) + "," + Integer.toString(yPosition - 1));
//			}
//			if(yPosition + 1 < Grid.tiles.length && current.getValue().down == false){
//				tree.insertNodeDown(current, Grid.tiles[xPosition][yPosition + 1]);
//				current.getValue().down = true;
//				//seen.add(Integer.toString(xPosition) + "," + Integer.toString(yPosition + 1));
//			}
//			if(yPosition - 1 >= 0  && current.getValue().up == false){
//				tree.insertNodeUp(current, Grid.tiles[xPosition][yPosition - 1]);
//				current.getValue().up = true;
//				//seen.add(Integer.toString(xPosition) + "," + Integer.toString(yPosition - 1));
//			}
			return;
		}
		
		// If left available
		if(xPosition - 1 >= 0){
			tree.insertNodeLeft(current, Grid.tiles[xPosition - 1][yPosition]);
			//current.getValue().left = true;
			seen.add(current);
		}
		
		//If right available
		if(xPosition + 1 < Grid.tiles[0].length){
			tree.insertNodeRight(current, Grid.tiles[xPosition + 1][yPosition]);
			//current.getValue().right = true;
			seen.add(current);
		}
		
		// If down available
		if(yPosition + 1 < Grid.tiles.length){
			tree.insertNodeDown(current, Grid.tiles[xPosition][yPosition + 1]);
			//current.getValue().down = true;
			seen.add(current);
		}
		
		// If up available
		if(yPosition - 1 >= 0){
			tree.insertNodeUp(current, Grid.tiles[xPosition][yPosition - 1]);
			//current.getValue().up = true;
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
						//System.out.println("VALUE FOUND:" + child.getValue().getDNA().getValue());
						//System.out.println("STEPS:" + steps);
						//System.out.println("SCORE FOUND:" + score);
						
						if(score > bestScore){
							//System.out.println("SCORE HAS BEEN RESET TO " + score);
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
			//System.out.println("THIS SHOULD NOT HAPPEN!!!!");
			return GameState.getRandomDirection();
		}
		
		// Else get the best direction with respect to the DNA found
		return GameState.getDirectionFromDNA(bestDNA, c);
		
	}
	
	public static Direction getDirectionFromDNA(DNA dna, Creature c){
		System.out.print(dna.getX()+",");
		System.out.println(dna.getY());
		if ((Math.abs(dna.getX() - c.getX()) <= Math.abs(dna.getY() - c.getY()) 
				&& Math.abs(dna.getX() - c.getX()) != 0) || 
				(Math.abs(dna.getX() - c.getX()) > Math.abs(dna.getY() - c.getY()) 
				&& Math.abs(dna.getY() - c.getY()) == 0)){
			if (c.getX() < dna.getX()){
				System.out.println("right");
				return Direction.RIGHT;
			}
			System.out.println("left");
			return Direction.LEFT;
		}
		else{
			if (c.getY() > dna.getY()){
				System.out.println("up");
				return Direction.UP;
			}
			System.out.println("down");
			return Direction.DOWN;
		}
	}
	
	public static Direction getRandomDirection(){
		System.out.println("random");
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
