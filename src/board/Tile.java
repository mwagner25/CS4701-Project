package board;

import java.util.ArrayList;
import java.util.List;

import creature.Creature;
import creature.DNA;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import quadtree.QuadTreeNode;

public class Tile extends StackPane{
	
	private Creature creature;
	private ImageView img;
	private double width;
	private double height;
	private DNA dna;
	private boolean hasDNA;
	public boolean left;
	public boolean right;
	public boolean down;
	public boolean up;
	public boolean visited;
	public int x;
	public int y;
	
	// Construct a tile of a given width and height
	public Tile(double width, double height, int x, int y){
		this.img = null;
		this.width = width;
		this.height = height;
		this.hasDNA = false;
		this.left = false;
		this.right = false;
		this.down = false;
		this.up = false;
		this.visited = false;
		this.x = x;
		this.y = y;
		
		Rectangle border = new Rectangle(width, height);
		border.setFill(null);
		border.setStroke(Color.BLACK);
		setAlignment(Pos.CENTER);
		getChildren().addAll(border);
		this.creature = null;
		this.dna = null;
	}
	
	// Construct a tile with a creature initially in it
	public Tile(double width, double height, int x, int y, Creature c){
		this.img = new ImageView(c.getImage());
		this.img.setFitWidth(0.5 * width);
		this.img.setFitHeight(0.5 * height);
		
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.visited = false;
		
		Rectangle border = new Rectangle(width, height);
		border.setFill(null);
		border.setStroke(Color.BLACK);
		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(border, img);
		this.creature = c;
	}
	
	// Getter for Creature object
	public Creature getCreature(){
		return this.creature;
	}
	
	public DNA getDNA(){
		return this.dna;
	}
	
	public void setDNA(DNA d){
		this.dna = d;
		this.hasDNA = true;
		this.img = new ImageView(d.getImage());
		this.img.setFitWidth(0.5 * this.width);
		this.img.setFitHeight(0.5 * this.height);
		
		this.getChildren().add(img);
	}
	
	// Change the Creature for this tile
	public void setCreature(Creature c){
		this.creature = c;
		this.img = new ImageView(c.getImage());
		this.img.setFitWidth(0.5 * this.width);
		this.img.setFitHeight(0.5 * this.height);
		
		this.getChildren().add(img);
	}
	
	public void clearTile(){
		this.getChildren().remove(this.img);
		this.creature = null;
		this.dna = null;
	}
	
	// Getter for tile height
	public double getTileHeight(){
		return this.height;
	}
	
	// Getter for tile width
	public double getTileWidth(){
		return this.width;
	}
	
	// Returns if the tile has DNA on it
	public boolean hasDNA(){
		return this.hasDNA;
	}
	public List<Tile> getKids(){
		List<Tile> result = new ArrayList<Tile>();
		if (this.left == true) result.add(Grid.tiles[this.x - 1][this.y]);
		if (this.right == true) result.add(Grid.tiles[this.x + 1][this.y]);
		if (this.up == true) result.add(Grid.tiles[this.x][this.y - 1]);
		if (this.down == true) result.add(Grid.tiles[this.x][this.y + 1]);
		
		return result;
	}

}
