package it.polito.tdp.borders.model;

public class Border {
    private int b1;
    private int b2;
	public int getB1() {
		return b1;
	}
	public void setB1(int b1) {
		this.b1 = b1;
	}
	public int getB2() {
		return b2;
	}
	public void setB2(int b2) {
		this.b2 = b2;
	}
	public Border(int b1, int b2) {
		super();
		this.b1 = b1;
		this.b2 = b2;
	}
    
}
