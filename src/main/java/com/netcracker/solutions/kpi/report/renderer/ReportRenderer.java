package com.netcracker.solutions.kpi.report.renderer;

import com.netcracker.solutions.kpi.report.Report;

import java.io.OutputStream;

public interface ReportRenderer {

	public void render(Report report, OutputStream out);

}
