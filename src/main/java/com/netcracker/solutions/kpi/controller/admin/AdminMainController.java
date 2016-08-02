package com.netcracker.solutions.kpi.controller.admin;

import com.netcracker.solutions.kpi.service.ApplicationFormService;
import com.netcracker.solutions.kpi.service.RecruitmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminMainController {

    @Autowired
    private RecruitmentService recruitmentService;// = ServiceFactory.getRecruitmentService();

    @Autowired
    private ApplicationFormService applicationFormService;// = ServiceFactory.getApplicationFormService();

    @RequestMapping(value = "recruitment", method = RequestMethod.POST)
    public Recruitment getRecruitmentData() {
        if(null == recruitmentService.getCurrentRecruitmnet()){
            return recruitmentService.getLastRecruitment();
        }
        return recruitmentService.getCurrentRecruitmnet();
    }

    @RequestMapping(value = "getCurrentRecruitmentStudents", method = RequestMethod.GET)
    public Long getCurrentRecruitmentStudents() {
        return applicationFormService.getCountRecruitmentStudents(recruitmentService.getCurrentRecruitmnet().getId());
    }

}
