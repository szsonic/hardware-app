package com.hardware.model.guojia;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * 果加接口返回的通用格式<br>
 *
 * @author sunzhongshuai
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse implements Serializable {


    /**
     * 操作结果码
     */
    @JsonProperty(value = "rlt_code")
    private String rtlCode;

    /**
     * 操作结果码说明
     */
    @JsonProperty(value = "rlt_msg")
    private String rtlMessage;

    /**
     *  操作结果码和说明，定义如下
     *    操作码      说明
     *
     *  HH0000     success
     *  HH9999     服务器异常
     *  OPS00001   数据格式无法解析
     *  OPS04103   密码错误
     *  OPS00106   参数输入类型错误
     *  OPS03129   辅助信息长度不能超过 1024 个字符
     *  OPS00003   缺少 token 参数
     *  OPS00004   缺少业务流水单号参数
     *  OPS00005   缺少接口版本号参数
     *  OPS00103   没有接口访问权限
     *  OPS00104   请求不是 post 方法
     *  OPS00501   输入每页行数为 1~100 之间的正整数。
     *  OPS00502   输入页码为 1-2147483647 的正整数。
     *  OPS01102   网关信息不存在
     *  OPS02101   门锁编码不能为空。
     *  OPS02107   密码状态不正确。
     *  OPS02108   密码使用人手机格式不正确。
     *  OPS02109   输入密码编号为[1-29]或或[31-50][81-120]的正整数。
     *  OPS04100   token 无效
     *  OPS04110   token 已失效
     *  OPS04120   运营商申请接入时间已过期
     *  OPS00002   版本号不能为空。
     *  OPS01101   网关编码不能为空。
     *  OPS02101   门锁编码不能为空。
     *  OPS02102   门锁信息不存在，请检查。
     *  OPS02103   开门记录查询最多支持 30 天内的记录。
     *  OPS02104   只有 433 锁才可以远程开门。
     *  OPS03101   门锁密码编号不能为空
     *  OPS03102   门锁密码起始时间不能为空
     *  OPS03103   门锁密码结束时间不能为空
     *  OPS03104   使用人手机号不能为空
     *  OPS03105   门锁尚未安装。
     *  OPS03106   密码下发时间间隔小于 15 秒
     *  OPS03107   门锁或网关通讯异常，您暂时无法操作密码。
     *  OPS03108   与门锁绑定的网关正在配置信息，暂时无法操作密码。
     *  OPS03109   门锁正在修改网关信息，暂时无法操作密码，请稍后再试。
     *  OPS03110   无可用密码
     *  OPS03111   描述信息长度超出限制长度。
     *  OPS03112   新增密码失败。
     *  OPS03113   新增门锁密码历史记录信息失败。
     *  OPS03114   该手机号已经存在密码，不能再次分配密码
     *  OPS03115   密码长度不符合要求（433 门锁： 4～16 位，蓝牙锁： 6～16 位）
     *  OPS03116   您设置的门锁密码过于简单，请重新输入。
     *  OPS03117   您输入的门锁密码与其它密码相似性过高，请重新输入。
     *  OPS03118   系统已经无法为您自动分配密码，请尝试删除原有密码。
     *  OPS03119   修改密码失败
     *  OPS03120   密码不存在，无法操作
     *  OPS03121   只有已启用密码才可以修改。
     *  OPS03122   密码有效时间，起始要大于结束。
     *  OPS03123   密码有效期不能小于系统当前时间。
     *  OPS03124   手机号格式不正确。
     *  OPS03125   时间参数格式错误。
     *  OPS03126   密码有效期最多设置 30 年。
     *  OPS03127   密码使用人长度超过 20 个字符。
     *  OPS03128   描述长度超过 120 个字符。
     *  OPS03129   辅助信息长度不能超过 1024 个字符。
     *  OPS03130   证件号长度超过 20 个字符。
     *  OPS03131   门锁密码编号格式不符合规则。
     *  OPS03132   密码编号超出范围。
     *  OPS03133   当前密码正在处理中， 请稍后再试。
     *  OPS03134   只有已禁用的密码才能被启用。
     *  OPS03135   只有已启用的密码才能被禁用。北京火河科技有限公司
     *  OPS03136   密码加密出错。
     *  OPS03137   时效密码有效期开始时间与截止时间间隔不能超过 7 年。
     *  OPS03138   时效密码有效期截止时间必须早于 2036/01/01 00:00:00。
     *  OPS03139   自动生成密码失败。
     *  OPS03140   参数不全。
     *  OPS03141   一次性密码不可删除。
     *  OPS03142   只有蓝牙锁可以创建离线密码。
     *  OPS04101   帐号错误。
     *  OPS04102   账号已被禁用,不能登录本系统
     *  OPS04105   运营商信息不存在
     *  OPS06101   开放平台账号不能为空
     *  OPS06102   开放平台密码不能为空
     *  OPS06103   运营商信息不存在。
     *  OPS06104   营商配运置信息不存在。
     *  OPS06105   运营商申请接入时间过期，无法获取 token
     *  OPS06106   运营商无法操作该锁。
     *  OPS06108   不是运营商帐号无权限
     *  OPS06109   运营商无法操作该网关
     *
     */
    public final String getRtlCode() {
        return rtlCode;
    }

    public void setRtlCode(String rtlCode) {
        this.rtlCode = rtlCode;
    }

    public final String getRtlMessage() {
        return rtlMessage;
    }

    public void setRtlMessage(String rtlMessage) {
        this.rtlMessage = rtlMessage;
    }
}
