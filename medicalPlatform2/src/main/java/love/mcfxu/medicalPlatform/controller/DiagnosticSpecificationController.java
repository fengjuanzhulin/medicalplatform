package love.mcfxu.medicalPlatform.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import love.mcfxu.medicalPlatform.domain.entity.DiagnosticSpecification;
import love.mcfxu.medicalPlatform.service.DiagnosticSpecificationServive;
import love.mcfxu.medicalPlatform.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@Api(value = "诊断书模块")
@RestController
@RequestMapping("authe/dia")
public class DiagnosticSpecificationController {

    @Autowired
    DiagnosticSpecificationServive diagnosticSpecificationServive;


    /**
     * 更新诊断书
     * @param request
     * @param content
     * @param diagnosticSpecificationId
     * @return
     */
    @ApiOperation(value = "更新诊断书")
    @PutMapping("update")
    public JsonData update(HttpServletRequest request,
                           @RequestParam(value = "content", required = true) String content,
                           @RequestParam(value = "diagnostic_specification_id", required = true) int diagnosticSpecificationId) {
        String role = request.getSession().getAttribute("user_role").toString();
        int userRole = Integer.parseInt(role);
        if (userRole == 1) {
            Object user_id = request.getSession().getAttribute("user_id");
            int doctorId = Integer.parseInt(user_id.toString());
            int doctorById = diagnosticSpecificationServive.findDoctorById(diagnosticSpecificationId);
            if (doctorById!=doctorId){
                return JsonData.buildError("你没有权限查看别的医生的诊断书",-10001);
            }

            int row = diagnosticSpecificationServive.updateDiagnosticSpecificationByDoctor(content, diagnosticSpecificationId);
            return row == 1 ? JsonData.buildSuccess() : JsonData.buildError("医嘱添加失败",row);
        }
        if (userRole == 2) {
            Object user_id = request.getSession().getAttribute("user_id");
            int patientId = Integer.parseInt(user_id.toString());
            int patientById = diagnosticSpecificationServive.findPatientById(diagnosticSpecificationId);
            if (patientById!=patientId){
                return JsonData.buildError("你无权查看别的病人的诊断书",-10001);
            }

            int row = diagnosticSpecificationServive.updateDiagnosticSpecificationByPatient(content, diagnosticSpecificationId);
            return row == 1 ? JsonData.buildSuccess() : JsonData.buildError("病情描述添加失败",row);
        }
        return JsonData.buildError("不是医生也不是病人的用户暂不负责",-10001);
    }

    /**
     * 查询诊断书
     * @param request
     * @return
     */
    @ApiOperation(value = "查询诊断书")
    @GetMapping("find")
    public JsonData findADiagnosticSpecification(HttpServletRequest request) {
//        int patientId=17;

        Object user_role = request.getSession().getAttribute("user_role");
        int userRole = Integer.parseInt(user_role.toString());

        if (userRole==1){
            Object user_id = request.getSession().getAttribute("user_id");
            int doctorId = Integer.parseInt(user_id.toString());
            List<DiagnosticSpecification> list = diagnosticSpecificationServive.findDiagnosticSpecificationfordoc(doctorId);
            return list==null?JsonData.buildError("还没人找你挂号",-1):JsonData.buildSuccess(list);

        }


        if (userRole==2){
            Object user_id = request.getSession().getAttribute("user_id");
            int patientId = Integer.parseInt(user_id.toString());
            List<DiagnosticSpecification> list = diagnosticSpecificationServive.findDiagnosticSpecificationforpat(patientId);
            return list==null?JsonData.buildError("你还没挂号",-1):JsonData.buildSuccess(list);

        }

        return JsonData.buildError("管理员不能查看诊断书",-10001);

    }

}
