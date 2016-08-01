package com.netcracker.solutions.kpi.report.renderer;

import com.netcracker.solutions.kpi.report.poi.RenderXLS;
import com.netcracker.solutions.kpi.report.poi.RenderXLSX;

public class RendererFactory {

	public static ReportRenderer getXLSRenderer() {
		return new RenderXLS();
	}

	public static ReportRenderer getXLSXRenderer() {
		return new RenderXLSX();
	}

	public static ReportRenderer getJSONRenderer() {
		return new JSONRenderer();
	}
}
