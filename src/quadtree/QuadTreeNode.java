package quadtree;

import java.util.ArrayList;
import java.util.List;

import board.Tile;

public class QuadTreeNode {
	public QuadTreeNode left;
	public QuadTreeNode right;
	public QuadTreeNode up;
	public QuadTreeNode down;
	public boolean visited;
	
	private Tile value;
	
	public QuadTreeNode(){
		this.left = null;
		this.right = null;
		this.up = null;
		this.down = null;
		this.visited = false;
	}
	
	public List<QuadTreeNode> getChildren(){
		List<QuadTreeNode> result = new ArrayList<QuadTreeNode>();
		if (this.left != null) result.add(left);
		if (this.right != null) result.add(right);
		if (this.up != null) result.add(up);
		if (this.down != null) result.add(down);
		
		return result;
	}
	
	public void setValue(Tile value){
		this.value = value;
	}
	
	public Tile getValue(){
		return this.value;
	}
}
