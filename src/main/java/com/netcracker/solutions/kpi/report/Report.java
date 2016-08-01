package com.netcracker.solutions.kpi.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Report {

	private String title;

	private Line header;

	private List<Line> lines;

	public Report() {
		header = new Line();
		lines = new ArrayList<>();
	}

	public Report(String title) {
		this();
		this.title = title;
	}

	public Line getHeader() {
		return header;
	}

	public void setHeader(Line header) {
		this.header = header;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Line> getLines() {
		return lines;
	}

	public void setLines(List<Line> lines) {
		this.lines = lines;
	}

	public boolean addRow(Line e) {
		return lines.add(e);
	}

	public boolean addLines(Collection<? extends Line> c) {
		return lines.addAll(c);
	}

}
