package love.mcfxu.medicalPlatform.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import love.mcfxu.medicalPlatform.config.WeChatConfig;
import love.mcfxu.medicalPlatform.domain.entity.DiagnosticSpecification;
import love.mcfxu.medicalPlatform.domain.entity.RegistrationSheet;
import love.mcfxu.medicalPlatform.domain.entity.ServiceEvaluation;
import love.mcfxu.medicalPlatform.domain.model.RegistrationSheetList;
import love.mcfxu.medicalPlatform.service.DiagnosticSpecificationServive;
import love.mcfxu.medicalPlatform.service.RegistrationSheetService;
import love.mcfxu.medicalPlatform.service.ServiceEvaluationService;
import love.mcfxu.medicalPlatform.utils.CommonUtils;
import love.mcfxu.medicalPlatform.utils.JsonData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "挂号模块")
@RestController
@RequestMapping("authe/reg")
public class RegistrationSheetController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Logger dataLogger = LoggerFactory.getLogger("dataLogger");


    @Autowired
    RegistrationSheetService registrationSheetService;

    @Autowired
    DiagnosticSpecificationServive diagnosticSpecificationServive;

    @Autowired
    WeChatConfig weChatConfig;

    @Autowired
    ServiceEvaluationService serviceEvaluationService;


    /**
     * 查看病人自身挂号情况
     * @param page
     * @param size
     * @param request
     * @return
     */
    @ApiOperation(value = "查看病人自身挂号情况")
    @GetMapping("list_allsheetsforone")
    public JsonData listAllSheetsForSomeone(@RequestParam(value = "page", defaultValue = "1") int page,
                                            @RequestParam(value = "size", defaultValue = "10") int size,
                                            HttpServletRequest request){

        PageHelper.startPage(page, size);
        Object id = request.getSession().getAttribute("user_id");
        int patientId = Integer.parseInt(id.toString());
//        int patientId=17;

        List<RegistrationSheet> list = registrationSheetService.findAllRegistrationSheetByPatientId(patientId);
        PageInfo<RegistrationSheet> sheetPageInfo = new PageInfo<>(list);

        return JsonData.buildSuccess(CommonUtils.sortPage(sheetPageInfo));
    }

    /**
     * 支付后则成功挂号
     * @param doctorId
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "支付后则成功挂号")
    @GetMapping("add")
    public void saveOrder(@RequestParam(value = "doctor_id",required = true)int doctorId,
                          HttpServletRequest request,
                          HttpServletResponse response) throws Exception {
        String ip = CommonUtils.getIpAddr(request);
        int patientId = (Integer) request.getSession().getAttribute("user_id");

//        int patientId = 17;
        RegistrationSheet list = registrationSheetService.sheetDuplicateCheck(patientId, doctorId);

//        if (list != null){
//            return ;
//        }

        RegistrationSheetList registrationSheet = new RegistrationSheetList();
        registrationSheet.setDoctorId(doctorId);
        registrationSheet.setPatientId(patientId);
        registrationSheet.setIp(ip);
        registrationSheet.setTotalFee(10);

        String codeUrl = registrationSheetService.getUrl(registrationSheet);

        DiagnosticSpecification diagnosticSpecification = new DiagnosticSpecification();
        diagnosticSpecification.setPatientId(patientId);
        diagnosticSpecification.setDoctorId(doctorId);

        diagnosticSpecificationServive.addDiagnosticSpecification(diagnosticSpecification);

        ServiceEvaluation serviceEvaluation = new ServiceEvaluation();
        serviceEvaluation.setDoctorId(doctorId);
        serviceEvaluation.setPatientId(patientId);

        serviceEvaluationService.addServiceEvaluation(serviceEvaluation);


        if(codeUrl == null) {
            throw new NullPointerException();
        }

        try{
            //生成二维码配置
            Map<EncodeHintType,Object> hints =  new HashMap<>();

            //设置纠错等级
            hints.put(EncodeHintType.ERROR_CORRECTION,ErrorCorrectionLevel.L);
            //编码类型
            hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");

            BitMatrix bitMatrix = new MultiFormatWriter().encode(codeUrl,BarcodeFormat.QR_CODE,400,400,hints);
            OutputStream out =  response.getOutputStream();

            MatrixToImageWriter.writeToStream(bitMatrix,"png",out);

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @ApiOperation(value = "评价医疗服务")
    @PutMapping("evaluate")
    public JsonData updateEvaluation(@RequestParam(value = "service_evaluation_details") String serviceEvaluationDetails,
                                     @RequestParam(value = "service_evaluation_id") int serviceEvaluationId,
                                     HttpServletRequest request){

        int user_id = (Integer) request.getSession().getAttribute("user_id");
        int o = serviceEvaluationService.ensurePatientById(serviceEvaluationId);

        if (user_id!=o){
            return JsonData.buildError("不能评价别人的诊断服务", -10001);
        }

        int i = serviceEvaluationService.updateServiceEvaluationDetailsById(serviceEvaluationDetails, serviceEvaluationId);

        return i==1?JsonData.buildSuccess():JsonData.buildError("添加评价失败", i);
    }


}