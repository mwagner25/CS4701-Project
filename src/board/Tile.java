package board;


import creature.Consumable;
import creature.Creature;
import creature.DNA;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends StackPane{
	
	private Creature creature;
	private ImageView img;
	private double width;
	private double height;
	private Consumable dna;
	private boolean hasDNA;
	
	// Construct a tile of a given width and height
	public Tile(double width, double height){
		this.img = null;
		this.width = width;
		this.height = height;
		this.hasDNA = false;
		
		Rectangle border = new Rectangle(width, height);
		border.setFill(null);
		border.setStroke(Color.BLACK);
		setAlignment(Pos.CENTER);
		getChildren().addAll(border);
		this.creature = null;
		this.dna = null;
	}
	
	// Construct a tile with a creature initially in it
	public Tile(double width, double height, Creature c){
		this.img = new ImageView(c.getImage());
		this.img.setFitWidth(0.5 * width);
		this.img.setFitHeight(0.5 * height);
		
		this.width = width;
		this.height = height;
		
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
	
	public Consumable getConsumable(){
		return this.dna;
	}
	
	public void setConsumable(Consumable dna) throws Exception{
		
		if(dna == null){
			throw new Exception("DNA cannot be null");
		}

		this.dna = dna;
		this.hasDNA = true;
		
		if(dna instanceof DNA){
			this.img = new ImageView(((DNA) dna).getImage());
			this.img.setFitWidth(0.5 * this.width);
			this.img.setFitHeight(0.5 * this.height);
			this.getChildren().add(img);
		}
		else if(dna instanceof Creature){
			this.creature = (Creature) dna;
			this.img = new ImageView(((Creature) dna).getImage());
			this.img.setFitWidth(0.5 * this.width);
			this.img.setFitHeight(0.5 * this.height);
			
			this.getChildren().add(img);
		}
	}
	
	// Removes its registered images from the tile
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

}
