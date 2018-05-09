package com.mervi.control;

import com.mervi.model.properties.ProgramProperties;

public class SelectionController {

	ProgramProperties pp;
	
	public SelectionController(ProgramProperties pp) {
		this.pp = pp;
	}

	public void selectionOn(double relxpos, double relypos) {
		int realxpos = (int) (this.pp.maxColProperty().floatValue() * relxpos);
		int realypos = (int) (this.pp.maxRowProperty().floatValue() * relypos);
		
		this.pp.colProperty().set(realxpos);
		this.pp.rowProperty().set(realypos);
	}
}
