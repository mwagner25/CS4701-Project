package quadtree;

import board.Tile;

public class QuadTree {
	
	public QuadTreeNode root;
	
	public QuadTree(){
		this.root = new QuadTreeNode();
	}
	
	public void insertNodeLeft(QuadTreeNode node, Tile t){
		if(node.left != null){
			this.insertNodeLeft(node.left, t);
		}
		else{
			node.left = new QuadTreeNode();
			node.left.setValue(t);
		}
	}
	
	public void insertNodeRight(QuadTreeNode node, Tile t){
		if(node.right != null){
			this.insertNodeRight(node.right, t);
		}
		else{
			node.right = new QuadTreeNode();
			node.right.setValue(t);
		}
	}
	
	public void insertNodeUp(QuadTreeNode node, Tile t){
		if(node.up != null){
			this.insertNodeUp(node.up, t);
		}
		else{
			node.up = new QuadTreeNode();
			node.up.setValue(t);
		}
	}
	
	public void insertNodeDown(QuadTreeNode node, Tile t){
		if(node.down != null){
			this.insertNodeDown(node.down, t);
		}
		else{
			node.down = new QuadTreeNode();
			node.down.setValue(t);
		}
	}

}
