package love.mcfxu.medicalPlatform.service.impl;

import love.mcfxu.medicalPlatform.config.WeChatConfig;
import love.mcfxu.medicalPlatform.domain.entity.RegistrationSheet;
import love.mcfxu.medicalPlatform.domain.entity.ServiceInformation;
import love.mcfxu.medicalPlatform.mapper.RegistrationSheetMapper;
import love.mcfxu.medicalPlatform.mapper.ServiceInformationMapper;
import love.mcfxu.medicalPlatform.service.RegistrationSheetService;
import love.mcfxu.medicalPlatform.utils.CommonUtils;
import love.mcfxu.medicalPlatform.utils.HttpUtils;
import love.mcfxu.medicalPlatform.utils.WXPayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;


@Service
public class RegistrationSheetServiceImpl implements RegistrationSheetService {

    private Logger dataLogger = LoggerFactory.getLogger("dataLogger");

    @Autowired
    RegistrationSheetMapper registrationSheetMapper;

    @Autowired
    ServiceInformationMapper serviceInformationMapper;

    @Autowired
    private WeChatConfig weChatConfig;



    @Override
    public List<RegistrationSheet> findAllRegistrationSheet() {
        return registrationSheetMapper.findAllRegistrationSheet();
    }

    @Override
    public List<RegistrationSheet> findAllRegistrationSheetByPatientId(int patientId) {
        return registrationSheetMapper.findAllRegistrationSheetByPatientId(patientId);
    }

    @Override
    public int deleteRegistrationSheetById(int registrationCardId) {
        return registrationSheetMapper.deleteRegistrationSheetById(registrationCardId);
    }

    @Override
    public int addRegistrationSheet(RegistrationSheet registrationSheet) {
        return registrationSheetMapper.addRegistrationSheet(registrationSheet);
    }

    /**
     * 挂号单去重
     * @param patientId
     * @param doctorId
     * @return
     */
    @Override
    public RegistrationSheet sheetDuplicateCheck(int patientId, int doctorId) {
        return registrationSheetMapper.sheetDuplicateCheck(patientId,doctorId);
    }

    /**
     * 添加挂号单
     * @param registrationSheet1
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String getUrl(RegistrationSheet registrationSheet1) throws Exception {

        dataLogger.info("module=video_order`api=save`patient_id={}`doctor_id={}",
                registrationSheet1.getPatientId(),registrationSheet1.getDoctorId());

        ServiceInformation information = serviceInformationMapper
                .findServiceInformationByDoctorId(registrationSheet1.getDoctorId());


        RegistrationSheet registrationSheet  = new RegistrationSheet();
        registrationSheet.setRegisterTime(new Date());
        registrationSheet.setDoctorId(information.getDoctorId());
        registrationSheet.setDepartment(information.getServiceDepartment());
        registrationSheet.setFurtherDepartment(information.getServiceFurtherDepartment());

        registrationSheet.setPatientId(registrationSheet1.getPatientId());
        registrationSheet.setTotalFee(registrationSheet1.getTotalFee());
        registrationSheet.setState(0);
        registrationSheet.setOutTradeNo(CommonUtils.generateUUID());
        registrationSheet.setIp(registrationSheet1.getIp());

        registrationSheetMapper.addRegistrationSheet(registrationSheet);

        //获取codeurl
        String codeUrl = unifiedOrder(registrationSheet);

        return codeUrl;
    }

    /**
     * 根据下单唯一标识流水号找寻挂号单
     * @param outTradeNo
     * @return
     */
    @Override
    public RegistrationSheet findByOutTradeNo(String outTradeNo) {
        return registrationSheetMapper.findByOutTradeNo(outTradeNo);
    }


    /**
     * 根据下单唯一标识流水号更新挂号单
     * @param registrationSheet
     * @return
     */
    @Override
    public int updateRegistrationSheetByOutTradeNo(RegistrationSheet registrationSheet) {

        return registrationSheetMapper.updateRegistrationSheetByOutTradeNo(registrationSheet);
    }


    /**
     * 微信支付挂号方法，获取url
     * @param registrationSheet
     * @return
     * @throws Exception
     */
    private String unifiedOrder(RegistrationSheet registrationSheet) throws Exception {


        SortedMap<String,String> params = new TreeMap<>();
        params.put("appid",weChatConfig.getAppId());
        params.put("mch_id", weChatConfig.getMchId());
        params.put("nonce_str",CommonUtils.generateUUID());
        params.put("body",registrationSheet.getFurtherDepartment());
        params.put("out_trade_no",registrationSheet.getOutTradeNo());
        params.put("total_fee",registrationSheet.getTotalFee().toString());
        params.put("spbill_create_ip",registrationSheet.getIp());

//        SimpleDateFormat sd =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        registrationSheet.setRegisterTime(new Date());
//        params.put("register_time", sd.format(registrationSheet.getRegisterTime()));

        params.put("notify_url",weChatConfig.getPayCallbackUrl());
        params.put("trade_type","NATIVE");


        String sign = WXPayUtils.createSign(params, weChatConfig.getKey());
        params.put("sign",sign);


        String payXml = WXPayUtils.mapToXml(params);

        System.out.println(payXml);

        String orderStr = HttpUtils.doPost(WeChatConfig.getUnifiedOrderUrl(),payXml,4000);
        if(null == orderStr) {
            return null;
        }

        Map<String, String> unifiedOrderMap =  WXPayUtils.xmlToMap(orderStr);
        System.out.println(unifiedOrderMap);
//        if(unifiedOrderMap.get("result_code").equals("SUCCESS")){
//            registrationSheet.setState(1);
//        }
        if(unifiedOrderMap != null) {
            return unifiedOrderMap.get("code_url");
        }

        return null;
    }

}
