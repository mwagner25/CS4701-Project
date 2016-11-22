package creature;

import javafx.scene.image.Image;

public class DNA {
	
	private int x;
	private int y;
	private String spritePathName;
	private Image sprite;
	private int value;
	
	// Constructor to create a creature of a given type
	public DNA(int x, int y, int value){
		this.x = x;
		this.y = y;
		this.spritePathName = "file:assets/png/DNA.png";
		this.sprite = new Image(this.spritePathName, true);
		this.value = value;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public int getValue(){
		return this.value; 
	}
	
	public Image getImage(){
		return this.sprite;
	}

}
