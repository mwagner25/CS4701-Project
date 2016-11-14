package board;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public class Grid {
	
	// Variable to represent the grid
	private Pane grid;
	
	/*
	 * Constructor to create a grid with a specified number of
	 * rows and columns
	 * 
	 * @param rows - the number of rows
	 * @param columns - the number of columns
	 * 
	 * */
	public Grid(int rows, int columns){
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
		root.setPrefSize(600, 600);
		
		for(int i = 0; i < rows; i++){
			for(int g = 0; g < columns; g++){
				Tile t = new Tile();
				t.setTranslateX(g * 200);
				t.setTranslateY(i * 200);
				
				root.getChildren().add(t);
			}
		}
		
		return root;
	}
	
	/*
	 * Getter to return the grid
	 * 
	 * */
	public Pane getGrid(){
		return this.grid;
	}

}
