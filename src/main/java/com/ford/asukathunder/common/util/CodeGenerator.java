package com.ford.asukathunder.common.util;

import com.ford.asukathunder.service.CodeToolsService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Code生成器，根据类型生成指定规则的CODE
 *
 * @author ShawnQiu
 * @date 2019/4/26
 */
@Component
public class CodeGenerator {

    @Resource
    private CodeToolsService codeToolsService;

    public enum CodeType {

        HE("医院设备"),
        CE("公共设备"),
        HS("医院"),
        /**
         * 高校
         */
        University("高校"),
        LG("台账"),
        AU("审核");

        private String value;

        CodeType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 生成医院设备code
     * HE+医院编码+创建日期+系统当天设备序号
     *
     * @return
     */
    public String geneHospitalEquipmentCode(String hospitalCode) {
//        if (StringUtils.isEmpty(hospitalCode)) {
//            throw new IllegalArgumentException(ErrorCode.HospitalCodeEmpty.name());
//        }
        StringBuilder code = new StringBuilder(CodeType.HE.name());
        LocalDate today = LocalDate.now();
        String order = generateEquipmentOrder();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd");
        if (hospitalCode.startsWith(CodeType.HS.name())) {
            hospitalCode = hospitalCode.substring(CodeType.HS.name().length());
        }
        code.append(hospitalCode)
                .append(today.format(fmt))
                .append(order);
        return code.toString();
    }

    /**
     * 生成台账编码
     *
     * @param hospitalCode
     * @return
     */
    public String geneLedgerCode(String hospitalCode) {
//        if (StringUtils.isEmpty(hospitalCode)) {
//            throw new IllegalArgumentException(ErrorCode.HospitalCodeEmpty.name());
//        }
        StringBuilder code = new StringBuilder(CodeType.LG.name());
        LocalDate today = LocalDate.now();
        String order = getTodayOrder(today, CodeType.LG);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd");
        if (hospitalCode.startsWith(CodeType.HS.name())) {
            hospitalCode = hospitalCode.substring(CodeType.HS.name().length());
        }
        code.append(hospitalCode)
                .append(today.format(fmt))
                .append(order);
        return code.toString();
    }

    /**
     * 审核单编码
     *
     * @param hospitalCode
     * @return
     */
    public String geneAuditApplyCode(String hospitalCode) {
//        if (StringUtils.isEmpty(hospitalCode)) {
//            throw new IllegalArgumentException(ErrorCode.HospitalCodeEmpty.name());
//        }
        StringBuilder code = new StringBuilder(CodeType.AU.name());
        LocalDate today = LocalDate.now();
        String order = getTodayOrder(today, CodeType.AU);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd");
        if (hospitalCode.startsWith(CodeType.HS.name())) {
            hospitalCode = hospitalCode.substring(CodeType.HS.name().length());
        }
        code.append(hospitalCode)
                .append(today.format(fmt))
                .append(order);
        return code.toString();
    }

    /**
     * 生成高校编码
     *
     * @return
     */
    public String geneSchoolCode() {
        String order = getSequenceNum(CodeType.University);
        StringBuilder code = new StringBuilder(CodeType.University.name());
        code.append(order);
        return code.toString();
    }

    /**
     *
     * @return
     */
    private String generateEquipmentOrder() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    /**
     * 根据当天日期和code类型获取当前指定类型代码code的序号
     *
     * @param today
     * @param codeType
     * @return
     */
    private String getTodayOrder(LocalDate today, CodeType codeType) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd");
        int order = codeToolsService.getOrder(today.format(fmt), codeType.name());
        order = order + 1000000;
        return String.valueOf(order).substring(1);
    }

    /**
     * 根据当天日期和code类型获取当前指定类型代码code的序号
     *
     * @param codeType
     * @return
     */
    private String getSequenceNum(CodeType codeType) {
        int order = codeToolsService.getOrder(codeType.name());
        order = order + 1000000;
        return String.valueOf(order).substring(1);
    }
}
