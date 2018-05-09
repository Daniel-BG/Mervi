package com.mervi.control;

import com.mervi.model.properties.ProgramProperties;

public class ProgramController {
	
	ProgramProperties pp;
	
	public ProgramController(ProgramProperties pp) {
		this.pp = pp;
	}
	
	public void setRselection(int index) {
		//todo assert that it is possible
		this.pp.valueFactoryRedProperty().setValue(index);
	}
	
	public void setGselection(int index) {
		this.pp.valueFactoryGreenProperty().setValue(index);
	}
	
	public void setBselection(int index) {
		this.pp.valueFactoryBlueProperty().setValue(index);
	}

}
