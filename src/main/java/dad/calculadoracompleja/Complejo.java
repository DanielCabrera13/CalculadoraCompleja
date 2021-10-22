package dad.calculadoracompleja;

import javafx.beans.property.DoubleProperty;

public class Complejo {

	private DoubleProperty real;
	private DoubleProperty imag;
	
	public Complejo() {
	}
	
	public DoubleProperty getReal() {
		return real;
	}
	public void setReal(DoubleProperty real) {
		this.real = real;
	}
	public DoubleProperty getImag() {
		return imag;
	}
	public void setImag(DoubleProperty imag) {
		this.imag = imag;
	}
	
	@Override
	public String toString() {
		return "Complejo: ["+real+","+imag+"i]";
	}
}