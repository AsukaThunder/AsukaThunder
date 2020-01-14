package com.ford.asukathunder.controller.user.dto;

import com.ford.asukathunder.common.entity.role.Role;
import com.ford.asukathunder.common.entity.role.UserRoleRef;
import com.ford.asukathunder.common.entity.user.User;
import com.ford.asukathunder.util.CheckoutUtil;
import com.google.common.base.Converter;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: SaveUserDTO
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/1/9 下午 5:47
 **/
@Getter
@Setter
public class SaveUserDTO {

    private String userId;
    /**
     * 账号
     */
    @NotNull(message = "账号不能为空")
    private String account;
    /**
     * 真实姓名
     */
    @NotNull(message = "真实姓名不能为空")
    private String realName;
    /**
     * 昵称
     */
    @NotNull(message = "昵称不能为空")
    private String nickName;
    /**
     * 手机号
     */
    @NotNull(message = "手机号不能为空")
    private String mobilePhone;
    /**
     * 是否启用
     */
    private Boolean isUse;
    /**
     * 出生日期
     */
    private Date birthday;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 星座
     */
    private String constellation;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 密码
     */
    @NotNull(message = "密码不能为空")
    private String password;
    /**
     *格言
     */
    private String motto;
    /**
     * 角色
     */
    @NotNull(message = "角色不能为空")
    private List<String> roleIds;


    public User convertTo() {
        SaveUserConverter converter = new SaveUserConverter();
        return converter.convert(this);
    }

    public SaveUserDTO convertFrom(User user) {
        SaveUserConverter converter = new SaveUserConverter();
        return converter.reverse().convert(user);
    }

    private static class SaveUserConverter extends Converter<SaveUserDTO, User> {

        @Override
        protected User doForward(SaveUserDTO dto) {
            User user = new User();
            user.setRealName(dto.getRealName());
            user.setNickname(dto.getNickName());
            user.setAccount(dto.getAccount());
            user.setMobilePhone(dto.getMobilePhone());
            user.setIsUse(dto.getIsUse());
            user.setEmail(dto.getEmail());
            user.setGender(dto.getGender());
            user.setBirthday(dto.getBirthday());
            if (dto.getBirthday() != null) {
                user.setAge(getAgeByBirthDay(dto.getBirthday()));
                user.setConstellation(getConstellation(dto.getBirthday()));
            }
            user.setMotto(dto.getMotto());
            user.setPassword(CheckoutUtil.md5(dto.getPassword()));

            // 设置权限
            List<String> roleIds = dto.getRoleIds();
            List<UserRoleRef> userRoleRefs = new ArrayList<>();
            for (String roleId : roleIds) {
                Role role = new Role();
                role.setRoleId(roleId);

                UserRoleRef ref = new UserRoleRef();
                ref.setRole(role);
                ref.setUser(user);
                userRoleRefs.add(ref);
            }
            user.setUserRoleRef(userRoleRefs);
            return user;
        }

        @Override
        protected SaveUserDTO doBackward(User user) {
            return null;
        }
    }

//    private static String getConstellation(Date date) {
//        Calendar cal = Calendar.getInstance();
//        if (date != null) {
//            cal.setTime(date);
//        }
//        int month = cal.get(Calendar.MONTH);
//        int day = cal.get(Calendar.DATE);
//        String[] starArr = {"魔羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座"};
//        int[] dayArr = {22, 20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22};
//        return day < dayArr[month] ? starArr[month - 1] : starArr[month];
//    }

    public static final String[] zodiacArr = { "猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊" };

    public static final String[] constellationArr = { "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座" };

    public static final int[] constellationEdgeDay = { 20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22 };

    /**
     * 根据日期获取生肖
     * @return
     */
    public static String getZodica(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return zodiacArr[cal.get(Calendar.YEAR) % 12];
    }

    /**
     * 根据日期获取星座
     * @return
     */
    public static String getConstellation(Date date) {
        if (date == null) {
            return "";
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (day < constellationEdgeDay[month]) {
            month = month - 1;
        }
        if (month >= 0) {
            return constellationArr[month];
        }
        // default to return 魔羯
        return constellationArr[11];
    }

    public static Integer getAgeByBirthDay(Date birthDay){
        int age = 0;
        Calendar cal = Calendar.getInstance();
        //出生日期晚于当前时间，无法计算
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        //当前年份
        int yearNow = cal.get(Calendar.YEAR);
        //当前月份
        int monthNow = cal.get(Calendar.MONTH);
        //当前日期
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        if (birthDay != null) {
            cal.setTime(birthDay);
        }
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        //计算整岁数
        age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                //当前日期在生日之前，年龄减一
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                //当前月份在生日之前，年龄减一
                age--;
            }
        }
        return age;
    }
}
