package main;

public class Element {
	private String element,description;
	
	public Element(String element,String description) {
		this.element=element;
		this.description=description;
	}

	public String getElement() {
		return element;
	}

	public String toString() {
		return description;
	}
}
