package quadtree;

import board.Tile;

public class QuadTree {
	
	public QuadTreeNode root;
	
	public QuadTree(){
		this.root = new QuadTreeNode();
	}
	
	public void insertNodeLeft(QuadTreeNode node, Tile t){
		if(node != null){
			this.insertNodeLeft(node.left, t);
		}
		else{
			root = new QuadTreeNode();
			root.setValue(t);
		}
	}
	
	public void insertNodeRight(QuadTreeNode node, Tile t){
		if(node != null){
			this.insertNodeRight(node.right, t);
		}
		else{
			root = new QuadTreeNode();
			root.setValue(t);
		}
		
	}
	
	public void insertNodeUp(QuadTreeNode node, Tile t){
		if(node != null){
			this.insertNodeUp(node.up, t);
		}
		else{
			root = new QuadTreeNode();
			root.setValue(t);
		}
		
	}
	
	public void insertNodeDown(QuadTreeNode node, Tile t){
		if(node != null){
			this.insertNodeDown(node.down, t);
		}
		else{
			root = new QuadTreeNode();
			root.setValue(t);
		}
		
	}

}
