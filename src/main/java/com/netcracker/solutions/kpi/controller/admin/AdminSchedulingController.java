package com.netcracker.solutions.kpi.controller.admin;

import com.netcracker.solutions.kpi.persistence.dto.*;
import com.netcracker.solutions.kpi.persistence.model.*;
import com.netcracker.solutions.kpi.persistence.model.enums.RoleEnum;
import com.netcracker.solutions.kpi.persistence.model.enums.SchedulingStatusEnum;
import com.netcracker.solutions.kpi.service.*;
import com.netcracker.solutions.kpi.service.util.SenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("scheduling")
public class AdminSchedulingController {

    //TODO: rewrite or delete (Olesia)

}
