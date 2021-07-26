/****************************************************
 * 创建人：  @author xieguocheng
 * 创建时间: 2021/06/24 17:45
 * 项目名称:  yh-risk-parent
 * 文件名称: Calculator
 * 文件描述: Calculator
 * 公司名称: 深圳市赢和信息技术有限公司
 *
 * All rights Reserved, Designed By 深圳市赢和信息技术有限公司
 * @Copyright:2016-2019
 *
 ********************************************************/
package com.xo.common.executor.executorPool;

/**
 * 包名称： com.xo.common.executor.executorService
 * 类名称：Calculator
 * 类描述：Calculator
 * 创建人： xieguocheng
 * 创建时间：2021/06/24 17:45
 */
public interface Calculator {

    /**
     * 把传进来的所有numbers 做求和处理
     *
     * @param numbers
     * @return 总和
     */
    long sumUp(long[] numbers);
}
