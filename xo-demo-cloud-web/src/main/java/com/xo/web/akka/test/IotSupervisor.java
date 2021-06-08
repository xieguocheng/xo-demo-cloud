/****************************************************
 * 创建人：  @author xieguocheng
 * 创建时间: 2021/03/17 19:00
 * 项目名称:  yh-risk-parent
 * 文件名称: IotSupervisor
 * 文件描述: IotSupervisor
 * 公司名称: 深圳市赢和信息技术有限公司
 *
 * All rights Reserved, Designed By 深圳市赢和信息技术有限公司
 * @Copyright:2016-2019
 *
 ********************************************************/
package com.xo.web.akka.test;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.io.IOException;

/**
 * 包名称： com.example.test
 * 类名称：IotSupervisor
 * 类描述：IotSupervisor
 * 创建人： xieguocheng
 * 创建时间：2021/03/17 19:00
 */

public class IotSupervisor extends AbstractActor {

    public static void main(String[] args) throws IOException {
        ActorSystem system = ActorSystem.create("iot-system");
        try {
            // Create top level supervisor
            ActorRef supervisor = system.actorOf(IotSupervisor.props(), "iot-supervisor");
            System.out.println(supervisor);
            System.out.println("Press ENTER to exit the system");
            System.in.read();
        } finally {
            system.terminate();
        }
    }

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    public static Props props() {
        return Props.create(IotSupervisor.class, IotSupervisor::new);
    }

    @Override
    public void preStart() {
        log.info("IoT Application started");
    }

    @Override
    public void postStop() {
        log.info("IoT Application stopped");
    }

    // No need to handle any messages
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .build();
    }
}
