package application;
	
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import java.util.ArrayList;

import board.EvolutionTrack;
import board.GameState;
import board.Grid;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import creature.*;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			// Get screen width
			Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
			double width = primaryScreenBounds.getWidth();
			double height = primaryScreenBounds.getHeight();
			
			StackPane root = new StackPane();
			VBox titleComponents = new VBox();
			titleComponents.setAlignment(Pos.CENTER);
			
			Label title = new Label("Welcome to Darwin's Nightmare");
			titleComponents.getChildren().add(title);
			
			Button btn = new Button();
			btn.setText("Play");
			btn.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event){
					try {
						startGame(primaryStage, width, height);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			titleComponents.getChildren().add(btn);
			
			root.getChildren().add(titleComponents);
			Scene scene = new Scene(root, width, height);
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Darwin's Nightmare");
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void startGame(Stage primaryStage, double width, double height) throws Exception{
		
		// Create grid
		Grid.screenWidth = width;
		Grid.screenHeight = height;
		int gridHeight = 20;
		int gridWidth = 20;
		
		final Grid grid = new Grid(gridHeight, gridWidth);
		
		// Populate the grid
		addRandomDNA(10);
		
		Scene scene = new Scene(grid.getGrid(), width, height);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setTitle("Darwin's Nightmare");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		
		int x = (int)(Math.random() * gridHeight);
		int y = (int)(Math.random() * gridWidth);
		final Creature c = new Creature(EvolutionTrack.CAT, x, y);
		Grid.addConsumable(c, x, y);
		GameState.allCreatures.add(c);
		
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.4), ev -> {
			Direction nextStep;
			 
			try {
				
				for(Creature creature : GameState.allCreatures){
					nextStep = GameState.nextBestMove(creature);
					creature.move(nextStep);
				}
				
				Grid.refresh();
				
				if(Math.random() < 0.08){
					addRandomCreatures(1);
				}
				if(Math.random() < 0.2){
					addRandomDNA(1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		 }));
	    timeline.setCycleCount(Animation.INDEFINITE);
	    timeline.play();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private void addRandomCreatures(int n) throws Exception{
		for(int i = 0; i < n; i++){
			// Insert the creature at a random point in the grid
			int randX = (int) (Math.random() * Grid.tiles[0].length);
			int randY = (int) (Math.random() * Grid.tiles.length);
			
			if(Grid.tiles[randX][randY].getConsumable() == null){
				int randCreature = (int) (Math.random() * 7);
				Creature c = null;
				switch(randCreature){
				case 0:
					c = new Creature(EvolutionTrack.BIRD, randX, randY);
					break;
				case 1:
					c = new Creature(EvolutionTrack.CAT, randX, randY);
					break;
				case 2:
					c = new Creature(EvolutionTrack.PLANT, randX, randY);
					break;
				case 3:
					c = new Creature(EvolutionTrack.ELECTRICITY, randX, randY);
					break;
				case 4:
					c = new Creature(EvolutionTrack.ELEPHANT, randX, randY);
					break;
				case 5:
					c = new Creature(EvolutionTrack.ROBOT, randX, randY);
					break;
				case 6:
					c = new Creature(EvolutionTrack.ROCK, randX, randY);
					break;
				}
		
				GameState.allCreatures.add(c);
				Grid.addConsumable(c, randX, randY);
			}
			else{
				i--;
			}
		}
	}
	
	private void addRandomDNA(int n) throws Exception{
		System.out.println("GENERATING RANDOM DNA:");
		
		for(int i = 0; i < n; i++){
			int randX = (int) (Math.random() * Grid.tiles[0].length);
			int randY = (int) (Math.random() * Grid.tiles.length);
			
			if(Grid.tiles[randX][randY].getCreature() == null){
				DNA dna = new DNA(randX, randY, 10);
				System.out.println("X: " + randX + ", Y:" + randY);
				Grid.addConsumable(dna, randX, randY);
			}
			else{
				i--;
			}
			
		}
	}
}
