package com.task.autocomplete;

public class Record {

	private String name;
	private String age;
	private String department;
	
	
	public Record(String name, String age, String department) {
		super();
		this.name = name;
		this.age = age;
		this.department = department;
	}


	public String getName() {
		return name;
	}

	public String getAge() {
		return age;
	}

	public String getDepartment() {
		return department;
	}
}
