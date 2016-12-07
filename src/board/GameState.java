package board;

import java.util.ArrayList;
import java.util.List;

import creature.*;
import graph.Graph;
import graph.Node;

public class GameState {
	
	public static ArrayList<Creature> creatures;
	public static boolean gameOver = false;
	
	// Generate graph representing grid
	public static Graph generateGraph(Creature c){
		return new Graph();
	}
	
	public static Direction nextBestMove(Creature c){
		
		// Random chance of choosing a random direction. TODO: Fix condition
//		if((int)(Math.random() * 2) < c.getEvolutionStage()){
//			return GameState.getRandomDirection();
//		}
		
		Graph gameGrid = generateGraph(c);
		
		double bestScore = 0.0;
		DNA bestDNA = null;
		
		List<Node> unvisited = new ArrayList<Node>();
		unvisited.add(gameGrid.get(c.getX(), c.getY()));
		double steps = 0.0;
		
		ArrayList<String> explored = new ArrayList<String>();
		
		// Breadth-first-search. Determine the tile to go to next
		while(!unvisited.isEmpty()){
			steps++;
			Node current = unvisited.remove(0);
			current.visited = true;
			explored.add(current.x + ", " + current.y);
			
			if(current.getValue().getDNA() != null){
				double score = current.getValue().getDNA().getValue() / steps;

				if(score > bestScore){
					bestScore = score;
					bestDNA = current.getValue().getDNA();
				}
			}
			
			List<Node> neighbors = current.getNeighbors();
			
			for (Node neighbor : neighbors){
				if(!neighbor.visited){
					unvisited.add(neighbor);
					neighbor.visited = true;
				}
			}
		}
		
		// Can't find any DNA within depth radius
		if(bestScore == 0.0){
			System.out.println("NEVER SHOULD HAPPEN");
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
