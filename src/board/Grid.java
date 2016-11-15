package board;

import creature.Creature;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public class Grid {
	
	public static double screenWidth;
	public static double screenHeight;
	
	
	// Variable to represent the grid
	private Pane grid;
	
	private Tile[][] tiles;
	
	/*
	 * Constructor to create a grid with a specified number of
	 * rows and columns
	 * 
	 * @param rows - the number of rows
	 * @param columns - the number of columns
	 * 
	 * */
	public Grid(int rows, int columns){
		this.tiles = new Tile[rows][columns];
		this.grid = (Pane) generateGrid(rows, columns);
	}
	
	/*
	 * Function to generate the Pane of a specified number of 
	 * rows and columns
	 * 
	 * @param rows - the number of rows
	 * @param columns - the number of columns
	 * 
	 * @returns the Pane object
	 * */
	private Parent generateGrid(int rows, int columns){
		Pane root = new Pane();
		root.setPrefSize(Grid.screenWidth, Grid.screenHeight);
		
		// Calculate the width of each rectangle, depending on screen size
		double tileWidth = Grid.screenWidth / rows;
		double tileHeight = Grid.screenHeight / columns;
		
		for(int i = 0; i < rows; i++){
			for(int g = 0; g < columns; g++){
				Tile t = new Tile(tileWidth, tileHeight);
				this.tiles[i][g] = t;
				
				t.setTranslateX(g * tileWidth);
				t.setTranslateY(i * tileHeight);
				
				root.getChildren().add(t);
			}
		}
		
		return root;
	}
	
	public void addCreature(Creature c, int x, int y){
		this.tiles[x][y].setCreature(c);
	}
	
	/*
	 * Getter to return the grid
	 * 
	 * */
	public Pane getGrid(){
		return this.grid;
	}
	
	/*
	 * Getter to return the tiles on the grid
	 */
	public Tile[][] getTiles(){
		return this.tiles;
	}

}
