package com.example.servlets;

import java.io.Serializable;

import com.example.annotations.AuditControl;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@ViewScoped
@Named(value = "dataBean")
public class DataControl implements Serializable {

    private String output;

    public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}	
    
    @AuditControl(application = "dataControl", module = "test")
    public void test() {
        output = "saved";
        System.out.println("saved");
    }
}
