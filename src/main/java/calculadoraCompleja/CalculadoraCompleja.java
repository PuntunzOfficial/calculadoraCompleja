package calculadoraCompleja;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class CalculadoraCompleja extends Application {
	
	private TextField num11;
	private TextField num12;
	private TextField num21;
	private TextField num22;
	private ComboBox<String> comboOperator;
	private TextField num31;
	private TextField num32;

	private StringProperty operator = new SimpleStringProperty();
	
	private Complejo resultado = new Complejo();
	private Complejo firstcom = new Complejo();
	private Complejo secondcom = new Complejo();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		num11 = new TextField();
		num11.setText("0");
		num11.setMaxWidth(50);
		
		num12 = new TextField();
		num12.setText("0");
		num12.setMaxWidth(50);
		
		num21 = new TextField();
		num21.setText("0");
		num21.setMaxWidth(50);
		
		num22 = new TextField();
		num22.setText("0");
		num22.setMaxWidth(50);
		
		comboOperator = new ComboBox<String>();
		comboOperator.getItems().addAll("+","-","*","/");
		
		comboOperator.setMaxWidth(60);
		
		num31 = new TextField();
		num31.setText("0");
		num31.setMaxWidth(50);
		num31.setDisable(true);
		
		
		num32 = new TextField();
		num32.setText("0");
		num32.setMaxWidth(50);
		num32.setDisable(true);
		
		Separator separador = new Separator();
	
		
		HBox Line1 = new HBox(5,num11,new Label("+"),num12,new Label("i"));
		HBox Line2 = new HBox(5,num21,new Label("+"),num22,new Label("i"));			   
		HBox Result = new HBox(5,num31,new Label("+"),num32,new Label("i"));
		
		VBox operation = new VBox(5,Line1,Line2,separador,Result);
		operation.setAlignment(Pos.CENTER);
		operation.setFillWidth(false);
		
		VBox allSentences = new VBox(comboOperator);
		allSentences.setAlignment(Pos.CENTER);
		allSentences.setFillWidth(false);
		
		HBox root = new HBox(5,allSentences,operation);
		root.setAlignment(Pos.CENTER);
		allSentences.setFillWidth(false);
		
		
		Scene scene = new Scene(root,320,200);

		primaryStage.setTitle("Calculadora compleja");
		primaryStage.setScene(scene);
		primaryStage.show();

		Bindings.bindBidirectional(num11.textProperty(),firstcom.realProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(num12.textProperty(),firstcom.imaginarioProperty(), new NumberStringConverter());
		
		Bindings.bindBidirectional(num21.textProperty(),secondcom.realProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(num22.textProperty(),secondcom.imaginarioProperty(), new NumberStringConverter());
		
		Bindings.bindBidirectional(num31.textProperty(), resultado.realProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(num32.textProperty(), resultado.imaginarioProperty(), new NumberStringConverter());
		
		operator.bind(comboOperator.getSelectionModel().selectedItemProperty());
		
		//listener
		
		operator.addListener((o, ov, nv) -> onOperadorChanged(nv));
		
		comboOperator.getSelectionModel().selectFirst();
	
	}

	private void onOperadorChanged(String nv) {
		
		switch(nv) {
		case "+":
			resultado.realProperty().bind(firstcom.realProperty().add(secondcom.realProperty()));
			resultado.imaginarioProperty().bind(firstcom.imaginarioProperty().add(secondcom.imaginarioProperty()));
			break;
		case "-":
			resultado.realProperty().bind(firstcom.realProperty().subtract((secondcom.realProperty())));
			resultado.imaginarioProperty().bind(firstcom.imaginarioProperty().subtract((secondcom.imaginarioProperty())));	
			break;
		case "*": 
			resultado.realProperty().bind(
					firstcom.realProperty().multiply(secondcom.realProperty()).subtract(firstcom.imaginarioProperty().multiply(secondcom.imaginarioProperty())));
			resultado.imaginarioProperty().bind(
					firstcom.realProperty().multiply(secondcom.imaginarioProperty()).add(firstcom.imaginarioProperty().multiply(secondcom.realProperty())));
			break;
		case "/":
			resultado.realProperty().bind(
					(firstcom.realProperty().multiply(secondcom.realProperty()).add(firstcom.imaginarioProperty().multiply(secondcom.imaginarioProperty())))
					.divide((secondcom.realProperty().multiply(secondcom.realProperty())).add(secondcom.imaginarioProperty().multiply(secondcom.imaginarioProperty()))));
			resultado.imaginarioProperty().bind(
					firstcom.imaginarioProperty().multiply(secondcom.realProperty()).subtract(firstcom.realProperty().multiply(secondcom.imaginarioProperty()))
					.divide((secondcom.realProperty().multiply(secondcom.realProperty())).add(secondcom.imaginarioProperty().multiply(secondcom.imaginarioProperty()))));
			break;
		}
	
	}

	public static void main(String[] args) {
		launch(args);
	}

}