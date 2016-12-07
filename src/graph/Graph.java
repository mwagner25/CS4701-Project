package graph;

import java.util.ArrayList;
import java.util.Map;

import board.Grid;

public class Graph {
	private Node[][] nodes;
	
	public Graph(){
		nodes = new Node[Grid.tiles[0].length][Grid.tiles.length];
		
		for(int i = 0; i < Grid.tiles[0].length; i++){
			for(int j = 0; j < Grid.tiles.length; j++){
				nodes[i][j] = new Node(Grid.tiles[i][j]);
				nodes[i][j].x = i;
				nodes[i][j].y = j;
			}
		}
		
		for(int i = 0; i < Grid.tiles[0].length; i++){
			for(int j = 0; j < Grid.tiles.length; j++){
				boolean canMoveLeft = i - 1 >= 0;
				boolean canMoveRight = i + 1 < Grid.tiles[0].length;
				boolean canMoveDown = j + 1 < Grid.tiles.length;
				boolean canMoveUp = j - 1 >= 0;
				
				// boolean canMove = canMoveLeft || canMoveRight || canMoveDown || canMoveUp;
				
				if(canMoveLeft){
					nodes[i][j].addLeftNeighbor(nodes[i - 1][j]);
				}
				if(canMoveRight){
					nodes[i][j].addRightNeighbor(nodes[i + 1][j]);
				}
				if(canMoveUp){
					nodes[i][j].addUpNeighbor(nodes[i][j - 1]);
				}
				if(canMoveDown){
					nodes[i][j].addDownNeighbor(nodes[i][j + 1]);
				}
			}
		}
	}
	
	public Node get(int i, int j){
		return nodes[i][j];
	}
}
