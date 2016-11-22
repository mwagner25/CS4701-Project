package quadtree;

import board.Tile;

public class QuadTreeNode {
	public QuadTreeNode left;
	public QuadTreeNode right;
	public QuadTreeNode up;
	public QuadTreeNode down;
	
	private Tile value;
	
	public QuadTreeNode(){
		this.left = null;
		this.right = null;
		this.up = null;
		this.down = null;
	}
	
	public void setValue(Tile value){
		this.value = value;
	}
	
	public Tile getValue(){
		return this.value;
	}
}
