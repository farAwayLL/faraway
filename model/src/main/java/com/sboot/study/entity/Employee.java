package com.sboot.study.entity;

import java.io.Serializable;

/**
 * tb_employee
 * @author 
 */
public class Employee implements Serializable {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 用户编号
     */
    private String idBo;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * wbs系统登录密码
     */
    private String wbsPwd;

    /**
     * 理财师系统登录密码
     */
    private String financialPwd;

    /**
     * 员工编号
     */
    private String employeeId;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 证件类型(0身份证1护照)
     */
    private Integer certificatesType;

    /**
     * 同步过来身份证号
     */
    private String idcardNo;

    /**
     * 实名认证的身份证号
     */
    private String authIdcardNo;

    /**
     * 身份证号有效期
     */
    private String certificatesValid;

    /**
     * 认证状态(0未认证 1已认证 2其他)
     */
    private Integer authState;

    /**
     * 0普通理财师，1超级理财师
     */
    private Byte level;

    /**
     * 认证时间
     */
    private Long authTime;

    /**
     * 工作室名称
     */
    private String studioname;

    /**
     * 推荐人id
     */
    private String recommender;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 性别(0未指定1男2女)
     */
    private Byte sex;

    /**
     * 所属区域
     */
    private String area;

    /**
     * 职位ID
     */
    private Integer positionId;

    /**
     * 所属机构部门ID
     */
    private Integer orgId;

    /**
     * 员工类型(0未指定1普通人员2业务人员,3燎原app注册理财师)
     */
    private Byte type;

    /**
     * 员工状态(1启用2禁用)
     */
    private Byte state;

    /**
     * 关注的其他机构部门ID(多个时逗号分隔)
     */
    private String otherOrgId;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 最近修改时间
     */
    private Long lastModifiedTime;

    /**
     * 最近登录时间
     */
    private Long lastLoginTime;

    /**
     * 是否有效(1有效0无效)
     */
    private Byte yn;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 是否内容员工：1是，-1否
     */
    private Byte onJob;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdBo() {
        return idBo;
    }

    public void setIdBo(String idBo) {
        this.idBo = idBo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWbsPwd() {
        return wbsPwd;
    }

    public void setWbsPwd(String wbsPwd) {
        this.wbsPwd = wbsPwd;
    }

    public String getFinancialPwd() {
        return financialPwd;
    }

    public void setFinancialPwd(String financialPwd) {
        this.financialPwd = financialPwd;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getCertificatesType() {
        return certificatesType;
    }

    public void setCertificatesType(Integer certificatesType) {
        this.certificatesType = certificatesType;
    }

    public String getIdcardNo() {
        return idcardNo;
    }

    public void setIdcardNo(String idcardNo) {
        this.idcardNo = idcardNo;
    }

    public String getAuthIdcardNo() {
        return authIdcardNo;
    }

    public void setAuthIdcardNo(String authIdcardNo) {
        this.authIdcardNo = authIdcardNo;
    }

    public String getCertificatesValid() {
        return certificatesValid;
    }

    public void setCertificatesValid(String certificatesValid) {
        this.certificatesValid = certificatesValid;
    }

    public Integer getAuthState() {
        return authState;
    }

    public void setAuthState(Integer authState) {
        this.authState = authState;
    }

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    public Long getAuthTime() {
        return authTime;
    }

    public void setAuthTime(Long authTime) {
        this.authTime = authTime;
    }

    public String getStudioname() {
        return studioname;
    }

    public void setStudioname(String studioname) {
        this.studioname = studioname;
    }

    public String getRecommender() {
        return recommender;
    }

    public void setRecommender(String recommender) {
        this.recommender = recommender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public String getOtherOrgId() {
        return otherOrgId;
    }

    public void setOtherOrgId(String otherOrgId) {
        this.otherOrgId = otherOrgId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public Long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Byte getYn() {
        return yn;
    }

    public void setYn(Byte yn) {
        this.yn = yn;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Byte getOnJob() {
        return onJob;
    }

    public void setOnJob(Byte onJob) {
        this.onJob = onJob;
    }
}