package com.mervi.control;

import com.mervi.model.ProgramProperties;

public class ProgramController {
	
	ProgramProperties pp;
	
	public ProgramController(ProgramProperties pp) {
		this.pp = pp;
	}

	public void selectionOn(double relxpos, double relypos) {
		int realxpos = (int) (this.pp.maxColProperty().floatValue() * relxpos);
		int realypos = (int) (this.pp.maxRowProperty().floatValue() * relypos);
		
		this.pp.colProperty().set(realxpos);
		this.pp.rowProperty().set(realypos);
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
