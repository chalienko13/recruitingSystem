package com.netcracker.solutions.kpi.report.poi;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * Created by Алексей on 28.04.2016.
 */
public class RenderXLSX extends AbsRender {

	public RenderXLSX() {
		wb = new SXSSFWorkbook();
	}
}
