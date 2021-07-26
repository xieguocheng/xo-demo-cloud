/****************************************************
 * 创建人：  @author xieguocheng
 * 创建时间: 2021/06/24 17:52
 * 项目名称:  yh-risk-parent
 * 文件名称: Test
 * 文件描述: Test
 * 公司名称: 深圳市赢和信息技术有限公司
 *
 * All rights Reserved, Designed By 深圳市赢和信息技术有限公司
 * @Copyright:2016-2019
 *
 ********************************************************/
package com.xo.common.executor.ForkJoinPool;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.LongStream;

/**
 * 包名称： com.xo.common.executor.ForkJoinPool
 * 类名称：Test
 * 类描述：Test
 * 创建人： xieguocheng
 * 创建时间：2021/06/24 17:52
 */
public class Test {

    //参考：https://blog.csdn.net/m0_37542889/article/details/92640903
    public static void main(String[] args) {
//        long[] numbers = LongStream.rangeClosed(1, 10000000).toArray();
//
//        Instant start = Instant.now();
//        ForkJoinCalculator forkJoinCalculator = new ForkJoinCalculator();
//        long result = forkJoinCalculator.sumUp(numbers);
//
//        Instant end = Instant.now();
//        System.out.println("耗时：" + Duration.between(start, end).toMillis() + "ms");
//        System.out.println("结果为：" + result); // 打印结果500500


        byte[] allocation1, allocation2, allocation3, allocation4, allocation5, allocation6;
        allocation1 = new byte[60000 * 1024];

        allocation2 = new byte[800000*1024];

//        allocation3 = new byte[1000 * 1024];
//        allocation4 = new byte[1000 * 1024];
//        allocation5 = new byte[1000 * 1024];
//        allocation6 = new byte[1000 * 1024];

    }
}
