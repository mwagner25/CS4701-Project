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
					startGame(primaryStage, width, height);
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
	
	public void startGame(Stage primaryStage, double width, double height){
		
		// Create grid
		Grid.screenWidth = width;
		Grid.screenHeight = height;
		int gridHeight = 5;
		int gridWidth = 5;
		
		final Grid grid = new Grid(gridHeight, gridWidth);
					
		// GameState variables
		ArrayList<Creature> creatures = new ArrayList<Creature>();	
		GameState.creatures = creatures;
		
		// Populate the grid
		addRandomCreatures(0);
		addRandomDNA(1);
		
		Scene scene = new Scene(grid.getGrid(), width, height);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setTitle("Darwin's Nightmare");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		
		final Creature c = new Creature(EvolutionTrack.CAT);
		Grid.addCreature(c, 0, 0);
		
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), ev -> {
			 Direction d = GameState.nextBestMove(c);
			 System.out.println(d);
			 c.move(d);
			 Grid.refresh();
		 }));
	    timeline.setCycleCount(Animation.INDEFINITE);
	    timeline.play();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private void addRandomCreatures(int n){
		for(int i = 0; i < n; i++){
			// Insert the creature at a random point in the grid
			int randX = (int) (Math.random() * Grid.tiles[0].length);
			int randY = (int) (Math.random() * Grid.tiles.length);
			
			if(Grid.tiles[randX][randY].getDNA() == null){
				Creature c = new Creature(EvolutionTrack.ROCK, randX, randY);
				Grid.addCreature(c, randX, randY);
			}
			else{
				i--;
			}
		}
	}
	
	private void addRandomDNA(int n){
		System.out.println("GENERATING RANDOM DNA:");
		
		for(int i = 0; i < n; i++){
			int randX = (int) (Math.random() * Grid.tiles[0].length);
			int randY = (int) (Math.random() * Grid.tiles.length);
			
			if(Grid.tiles[randX][randY].getCreature() == null){
				DNA dna = new DNA(randX, randY, 10);
				System.out.println("X: " + randX + ", Y:" + randY);
				Grid.addDNA(dna, randX, randY);
			}
			else{
				i--;
			}
			
		}
	}
}
