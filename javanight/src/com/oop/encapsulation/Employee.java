package com.oop.encapsulation;

public class Employee {
	private int id;
	private String name;
	private String phoneNumber;

	private void displayDetail() {
		System.out.println("My ID is : " + id);
		System.out.println("My Name is : " + name);
		System.out.println("My Phone Number is : " + phoneNumber);
	}

	public void displayInformation() {
		displayDetail();
	}

	// Getter, Setter
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	// Setter
	public void setId(int id) {
		this.id = id;
	}

	public void setName(String nameFromOtherClass) {
		this.name = nameFromOtherClass;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
