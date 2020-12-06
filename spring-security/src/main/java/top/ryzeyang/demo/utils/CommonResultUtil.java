package top.ryzeyang.demo.utils;


import top.ryzeyang.demo.common.api.CommonResult;
import top.ryzeyang.demo.common.api.ResultEnum;

/**
 * @author RYZEYANG
 * @date 2020/11/12 8:25
 */
public class CommonResultUtil {

    public static <T> CommonResult<T> success(T data){
        return new CommonResult<>(ResultEnum.SUCCESS, data);
    }
}
