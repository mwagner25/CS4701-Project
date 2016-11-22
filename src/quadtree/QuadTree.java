package quadtree;

import board.Tile;

public class QuadTree {
	
	public QuadTreeNode root;
	
	public QuadTree(){
		this.root = new QuadTreeNode();
	}
	
	public void insertNodeLeft(Tile t){
		this.root.left = new QuadTreeNode();
		this.root.left.setValue(t);
	}
	
	public void insertNodeRight(Tile t){
		this.root.right = new QuadTreeNode();
		this.root.right.setValue(t);
	}
	
	public void insertNodeUp(Tile t){
		this.root.up = new QuadTreeNode();
		this.root.up.setValue(t);
	}
	
	public void insertNodeDown(Tile t){
		this.root.down = new QuadTreeNode();
		this.root.down.setValue(t);
	}

}
