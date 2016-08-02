package com.netcracker.solutions.kpi.util.export;

import com.itextpdf.text.DocumentException;
import com.netcracker.solutions.kpi.persistence.model.ApplicationForm;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Korzh
 */
public interface ExportApplicationForm {

    void export(ApplicationForm applicationForm, HttpServletResponse response) throws IOException, DocumentException;
}
