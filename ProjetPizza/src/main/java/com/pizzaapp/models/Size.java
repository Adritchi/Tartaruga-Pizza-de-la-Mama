package com.pizzaapp.models;

public class Size {
    private String name;
    private String size;
    private String price;

    public Size(String name,String size,String price) {
        this.name = name;
        this.size = size;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    
	public String getSize() {
		return size;
	}
	
	public void setSize(String size) {
		this.size = size;
	}
}
