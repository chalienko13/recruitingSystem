package com.netcracker.solutions.kpi.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Line {

	private List<Object> cells;

	public Line() {
		cells = new ArrayList<>();
	}

	public List<Object> getCells() {
		return cells;
	}

	public void setCells(List<Object> cells) {
		this.cells = cells;
	}

	public boolean addCell(Object arg0) {
		return cells.add(arg0);
	}

	public boolean addCells(Collection arg0) {
		return cells.addAll(arg0);
	}

	public void addFirstCell(Object arg0) {
		cells.add(0, arg0);
	}
}
