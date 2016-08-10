package com.netcracker.solutions.kpi.report.renderer;

import com.google.gson.Gson;
import com.netcracker.solutions.kpi.report.Report;

import java.io.OutputStream;
import java.io.PrintStream;

public class JSONRenderer implements ReportRenderer {

    @Override
    public void render(Report report, OutputStream out) {
        Gson gson = new Gson();
        PrintStream printStream = new PrintStream(out);
        printStream.print(gson.toJson(report));
        printStream.flush();
    }

}
