package com.xo.web.akka.test;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import cn.hutool.core.thread.ThreadUtil;

import java.util.Optional;

/**
 * 包名称： com.example.test
 * 类名称：SupervisingActor
 * 类描述：SupervisingActor
 * 创建人： xieguocheng
 * 创建时间：2021/03/17 18:41
 */
class SupervisingActor extends AbstractActor {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("testSystem");
        ActorRef supervisingActor = system.actorOf(SupervisingActor.props(), "supervising-actor");
        supervisingActor.tell("failChild", ActorRef.noSender());
        supervisingActor.tell("failChild", ActorRef.noSender());
    }

    static Props props() {
        return Props.create(SupervisingActor.class, SupervisingActor::new);
    }
    ActorRef child = getContext().actorOf(SupervisedActor.props(), "supervised-actor");
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals("failChild", f -> {
                    child.tell("fail", getSelf());
                })
                .build();
    }
}

class SupervisedActor extends AbstractActor {
    static Props props() {
        return Props.create(SupervisedActor.class, SupervisedActor::new);
    }
    @Override
    public void preStart() {
        System.out.println("supervised actor started");
    }

    @Override
    public void preRestart(Throwable reason, Optional<Object> message) throws Exception, Exception {
        System.out.println("preRestart");
    }

    @Override
    public void postRestart(Throwable reason) throws Exception, Exception {
        System.out.println("postRestart");
    }

    @Override
    public void postStop() {
        System.out.println("supervised actor stopped");
    }

    static int  i = 0;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals("fail", f -> {
                    i++;

                    if (i % 2 == 0) {
                        Thread.sleep(2000);
                        System.out.println("我睡觉了一会" + i);
                    }else {
                        Thread.sleep(90000);
                        System.out.println("我睡觉了一会" + i);
                    }
                    System.out.println("supervised actor fails now");
                    throw new Exception("I failed!");
                })
                .build();
    }
}
