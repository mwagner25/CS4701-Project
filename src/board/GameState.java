package board;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import creature.*;
import graph.Graph;
import graph.Node;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class GameState {
	
	public static boolean gameOver = false;
	public static ArrayList<Creature> allCreatures = new ArrayList<Creature>();
	
	// Generate graph representing grid
	public static Graph generateGraph(Creature c){
		return new Graph();
	}
	
	public static Direction bfs(Creature c) throws Exception{
		
		// Random chance of choosing a random direction.
		if(Math.random() < 1.0 / (c.getDNA() + 5)){
			return GameState.getRandomDirection();
		}
		
		Graph gameGrid = generateGraph(c);
		
		double bestScore = 0.0;
		Consumable best = null;
		
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
		
			Creature tileCreature = current.getValue().getCreature();
			
			double score = GameState.getBestDNAValue(current) / steps;
			
			boolean itself = false;
			
			// If the creature is looking to eat itself...
			if(tileCreature == c){
				itself = true;
			}
			
			if(score > bestScore && !itself && score < c.getDNA()){ //not eating yourself or someone bigger than you
				bestScore = score;
				best = current.getValue().getConsumable();
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
			return GameState.getRandomDirection();
		}
		
		// Else get the best direction with respect to the DNA found
		return GameState.getDirectionFromConsumable(best, c);
		
	}
	
	// Depth-first search alternative to finding the next best move
	public static Direction dfs(Creature c) throws Exception{
		
		// Random chance of choosing a random direction
		if(Math.random() < 1.0 / (c.getDNA() + 5)){
			return GameState.getRandomDirection();
		}
		
		Graph gameGrid = generateGraph(c);
		
		double bestScore = 0.0;
		Consumable bestDNA = null;
		
		Stack<Node> unvisited = new Stack<Node>();
		unvisited.push(gameGrid.get(c.getX(), c.getY()));
				
		// Depth-first-search. Determine the tile to go to next
		while(!unvisited.isEmpty()){
			Node current = unvisited.pop();
			
			Creature tileCreature = current.getValue().getCreature();
			boolean itself = false;
			
			// If the creature is looking to eat itself...
			if(tileCreature == c)
				itself = true;
	
			if(!current.visited){
				current.visited = true;
				if(current.getValue().getConsumable() != null){
					double score = current.getValue().getConsumable().getDNA() / GameState.distance(c,current);
					if(score > bestScore && !itself){
						bestScore = score;
						bestDNA = current.getValue().getConsumable();
					}
				}
				List<Node> neighbors = current.getNeighbors();
				for (Node neighbor : neighbors){
					unvisited.push(neighbor);
				}
			}
		}
		// Can't find any DNA within depth radius
		if(bestScore == 0.0){
			return GameState.getRandomDirection();
		}
		
		// Else get the best direction with respect to the DNA found
		return GameState.getDirectionFromConsumable(bestDNA, c);
		
	}
	
	// Returns either 0 or the best consumable value of the node
	public static double getBestDNAValue(Node n){
		double bestScore = 0.0;
		
		if(n.getValue().getConsumable() != null){
			bestScore = n.getValue().getConsumable().getDNA();
		}
		else if(n.getValue().getCreature() != null){
			bestScore = n.getValue().getCreature().getDNA() > bestScore ? n.getValue().getCreature().getDNA() : bestScore;
		}
		
		return bestScore;
	}
	
	public static Direction getDirectionFromConsumable(Consumable dna, Creature c) throws Exception{
		if (dna == null){
			throw new Exception();
		}
		
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
	
	// Get a random direction in the case that the creature can't compute the best move
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
	//Get number of steps from Creature to another Consumable
	public static double distance(Creature n1, Node n2){
		return Math.abs(n2.x - n1.getX()) + Math.abs(n2.y - n1.getY());
	}
	
	// Helper function to play a sound with the passed filename
	public static synchronized void playSound(String filename){
		new Thread(new Runnable(){
			public void run(){
				try {
					InputStream in = new FileInputStream("assets/sounds/" + filename);
					AudioStream audio = new AudioStream(in);
					AudioPlayer.player.start(audio);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}).start();
	}

}
