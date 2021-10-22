package dad.calculadoracompleja;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
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

	// VISTAS
	private ComboBox<String> operador;
	private TextField realText1;
	private TextField imagText1;
	private TextField realText2;
	private TextField imagText2;
	private TextField realResultText;
	private TextField imagResultText;

	// MODELO
	private DoubleProperty real1 = new SimpleDoubleProperty();
	private DoubleProperty imag1 = new SimpleDoubleProperty();
	private DoubleProperty real2 = new SimpleDoubleProperty();
	private DoubleProperty imag2 = new SimpleDoubleProperty();
	private DoubleProperty resulReal = new SimpleDoubleProperty();
	private DoubleProperty resulImag = new SimpleDoubleProperty();
	private StringProperty operacionCombo = new SimpleStringProperty();

	@Override
	public void start(Stage primaryStage) throws Exception {

		operador = new ComboBox<String>();
		operador.getItems().addAll("+", "-", "*", "/");
		operador.setOnAction(e -> onCambiarOperacion(e));

		realText1 = new TextField();
		realText1.setPrefColumnCount(4);

		imagText1 = new TextField();
		imagText1.setPrefColumnCount(4);

		realText2 = new TextField();
		realText2.setPrefColumnCount(4);

		imagText2 = new TextField();
		imagText2.setPrefColumnCount(4);
		
		realResultText = new TextField();
		realResultText.setPrefColumnCount(4);
		realResultText.setDisable(true);
		
		imagResultText = new TextField();
		imagResultText.setPrefColumnCount(4);
		imagResultText.setDisable(true);

		HBox h1 = new HBox(5, realText1, new Label("+"), imagText1, new Label("i"));
		HBox h2 = new HBox(5, realText2, new Label("+"), imagText2, new Label("i"));
		HBox h3 = new HBox(5, realResultText, new Label("+"), imagResultText, new Label("i"));

		Separator sep = new Separator();

		VBox v1 = new VBox(operador);
		v1.setAlignment(Pos.CENTER);
	
		VBox v2 = new VBox(5, h1, h2, sep, h3);
		v2.setAlignment(Pos.CENTER);

		HBox root = new HBox(5,v1, v2);
		root.setAlignment(Pos.CENTER);

		Scene scene = new Scene(root, 320, 200);

		Stage stage = new Stage();
		stage.setTitle("Calculadora");
		stage.setScene(scene);
		stage.show();
		
		
		//BINDINGS
		Bindings.bindBidirectional(realText1.textProperty(), real1, new NumberStringConverter());
		Bindings.bindBidirectional(imagText1.textProperty(), imag1, new NumberStringConverter());
		Bindings.bindBidirectional(realText2.textProperty(), real2, new NumberStringConverter());
		Bindings.bindBidirectional(imagText2.textProperty(), imag2, new NumberStringConverter());
		
		operacionCombo.bind(operador.getSelectionModel().selectedItemProperty());
		operador.getSelectionModel().selectFirst();
	}
	
	public void onCambiarOperacion(ActionEvent e) {
		switch (operacionCombo.get()) {
		
		case "+":
			resulReal.bind(Bindings.add(real1, real2));
			resulImag.bind(Bindings.add(imag1, imag2));
			
			Complejo sum = new Complejo();
			sum.setReal(resulReal);
			sum.setImag(resulImag);
			
			realResultText.textProperty().bind(sum.getReal().asString());
			imagResultText.textProperty().bind(sum.getImag().asString());
			break;
			
			
		case "-":
			resulReal.bind(Bindings.subtract(real1, real2));
			resulImag.bind(Bindings.subtract(imag1, imag2));
			
			Complejo res = new Complejo();
			res.setReal(resulReal);
			res.setImag(resulImag);
			
			realResultText.textProperty().bind(res.getReal().asString());
			imagResultText.textProperty().bind(res.getImag().asString());
			break;
			
			
		case "*":
			resulReal.bind(Bindings.subtract(
					(Bindings.multiply(real1, real2)),
					(Bindings.multiply(imag1,imag2))));
			resulImag.bind(Bindings.add(
					(Bindings.multiply(real1, imag2)),
					(Bindings.multiply(imag1,real2))));
			
			Complejo mult = new Complejo();
			mult.setReal(resulReal);
			mult.setImag(resulImag);
			
			realResultText.textProperty().bind(mult.getReal().asString());
			imagResultText.textProperty().bind(mult.getImag().asString());
			break;
			
			
		case "/":
			resulReal.bind(Bindings.divide(
					(Bindings.add(
					(Bindings.multiply(real1, real2)),
					(Bindings.multiply(imag1,imag2)))), 
			(Bindings.add(
					(Bindings.multiply(real2, real2)),
					(Bindings.multiply(imag2, imag2))))));
			resulImag.bind(Bindings.divide(
					(Bindings.subtract(
					(Bindings.multiply(imag1, real2)),
					(Bindings.multiply(real1,imag2)))), 
			(Bindings.add(
					(Bindings.multiply(real2, real2)),
					(Bindings.multiply(imag2, imag2))))));
			
			Complejo div = new Complejo();
			div.setReal(resulReal);
			div.setImag(resulImag);
			
			realResultText.textProperty().bind(div.getReal().asString());
			imagResultText.textProperty().bind(div.getImag().asString());
			break;
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
