package application;
	
import java.io.FileInputStream;

import board.Tile;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;



public class Main extends Application {
	
	private Parent createContent(){
		Pane root = new Pane();
		root.setPrefSize(600, 600);
		
		for(int i = 0; i < 3; i++){
			for(int g = 0; g < 3; g++){
				Tile t = new Tile();
				t.setTranslateX(g * 200);
				t.setTranslateY(i * 200);
				
				root.getChildren().add(t);
			}
		}
		
		return root;
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
//	        TilePane grid = new TilePane();
//	        grid.setPrefColumns(5);
//	        grid.setPrefRows(10);
//	        grid.setStyle("-fx-background-color: Black;");
//	        FileInputStream input = new FileInputStream("assets/png/cat_cell.png");
//	        Image cat = new Image(input);
//	        ImageView imageView = new ImageView(cat);
//	        HBox hbox = new HBox(imageView);
//			BorderPane root = new BorderPane();
			Scene scene = new Scene(createContent(),2000,1000);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Darwin's Nightmare");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
