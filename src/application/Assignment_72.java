package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.InputMismatchException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Assignment_72 extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Grade Distribution");
        BorderPane root = new BorderPane();
        // Distribution object
        Distribution dis = new Distribution(0,5);
		
        // Insert grade text area
        FlowPane topbar = new FlowPane();
        topbar.setAlignment(Pos.CENTER);
        topbar.setPadding(new Insets(5, 5, 5, 5));
        topbar.setHgap(10);
        
        Label label1 = new Label("Grade:");
        TextField textField1 = new TextField();
        Button btn = new Button("Insert");
        
        topbar.getChildren().add(label1);
        topbar.getChildren().add(textField1);
        topbar.getChildren().add(btn);
        
        root.setTop(topbar);
        
        // bar chart area
        final String[] names = {"0", "1", "2", "3", "4", "5"};
		
        CategoryAxis xAxis = new CategoryAxis(); //String category
        NumberAxis yAxis = new NumberAxis();  
        BarChart<String, Number> barchart = new BarChart<>(xAxis,yAxis);
        
        barchart.setTitle("Grade Distribution");
        barchart.setLegendVisible(false); //Just one series, legend is not needed

        xAxis.setLabel("Grade");       
        yAxis.setLabel("Frequency");
 
        XYChart.Series<String, Number> series1 = new Series<>();
        for(int i = 0; i < names.length; i++) {
            series1.getData().add(new Data<>(names[i], dis.frequency(i)));
        }
      
        root.setCenter(barchart);
        
        // Result text area
        
        Text resultText = new Text();
        root.setBottom(resultText);
        
        Scene scene  = new Scene(root,600,500);
        barchart.getData().add(series1);
        stage.setScene(scene);
        stage.show();		
        
        // btn event
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
            	int num = 0;
            	try {
            		num = Integer.parseInt(textField1.getText());
            	} catch(NumberFormatException ex1) {
            		Alert alert = new Alert(AlertType.ERROR, "Check your input!");
            		alert.showAndWait();
            		return;
            	}
                
                if (num == 0 || num == 1 || num == 2 || num == 3 || num == 4 || num == 5) {
                	dis.insertValue(num);
                } else {
                	Alert alert = new Alert(AlertType.ERROR, "Input out of range!");
            		alert.showAndWait();
            		return;
                }
                
                // Updating the series
                for(int i = 0; i < names.length; i++) {
                    series1.getData().add(new Data<>(names[i], dis.frequency(i)));
                }
                
                // Text area
                resultText.setText("average: " + dis.average() + " Count: " + dis.getCount());
                
                // re-render the scene
                stage.setScene(scene);
                stage.show();
            }
        });
        
    }

    public static void main(String[] args) {
    	launch(args);
    }
}