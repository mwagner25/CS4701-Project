package graph;

import java.util.ArrayList;

import board.Tile;

public class Node {
	private Node leftNeighbor;
	private Node rightNeighbor;
	private Node downNeighbor;
	private Node upNeighbor;
	private Tile value;
	public int x;
	public int y;
	public boolean visited;
	
	public Node(Tile value){
		this.value = value;
		this.visited = false;
	}
	
	public void addLeftNeighbor(Node neighbor){
		this.leftNeighbor = neighbor;
	}
	
	public void addRightNeighbor(Node neighbor){
		this.rightNeighbor = neighbor;
	}
	
	public void addUpNeighbor(Node neighbor){
		this.upNeighbor = neighbor;
	}
	
	public void addDownNeighbor(Node neighbor){
		this.downNeighbor = neighbor;
	}
	
	public ArrayList<Node> getNeighbors(){
		ArrayList<Node> result = new ArrayList<Node>();
		
		if (this.leftNeighbor != null){
			result.add(this.leftNeighbor);
		}
		
		if (this.rightNeighbor != null){
			result.add(this.rightNeighbor);
		}
		
		if (this.upNeighbor != null){
			result.add(this.upNeighbor);
		}
		
		if (this.downNeighbor != null){
			result.add(this.downNeighbor);
		}
		
		return result;
	}
	
	
	public Tile getValue(){
		return this.value;
	}
	
	public void setValue(Tile t){
		this.value = t;
	}
	
	public Node getLeftNeighbor(){
		return this.leftNeighbor;
	}
	
	public Node getRightNeighbor(){
		return this.rightNeighbor;
	}
	
	public Node getUpNeighbor(){
		return this.upNeighbor;
	}
	
	public Node getDownNeighbor(){
		return this.downNeighbor;
	}
	
	
}
