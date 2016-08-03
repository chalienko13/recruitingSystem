package com.netcracker.solutions.kpi.controller.student;

import com.google.gson.Gson;
import com.netcracker.solutions.kpi.persistence.dto.MessageDto;
import com.netcracker.solutions.kpi.persistence.dto.MessageDtoType;
import com.netcracker.solutions.kpi.persistence.dto.StudentScheduleDto;
import com.netcracker.solutions.kpi.persistence.dto.StudentSchedulePriorityDto;
import com.netcracker.solutions.kpi.persistence.model.*;
import com.netcracker.solutions.kpi.persistence.model.enums.StatusEnum;
import com.netcracker.solutions.kpi.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping("/student")
@RestController
public class StudentScheduleController {

	//TODO: rewrite or delete (Olesia)

}
