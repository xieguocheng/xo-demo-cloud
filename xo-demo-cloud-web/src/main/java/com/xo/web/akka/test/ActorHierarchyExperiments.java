package com.xo.web.akka.test;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * 包名称： com.example.test
 * 类名称：ActorHierarchyExperiments
 * 类描述：ActorHierarchyExperiments
 * 创建人： xieguocheng
 * 创建时间：2021/03/17 17:38
 */
public class ActorHierarchyExperiments {

    public static void main(String[] args) throws java.io.IOException {
        ActorSystem system = ActorSystem.create("testSystem");
        ActorRef firstRef = system.actorOf(PrintMyActorRefActor.props(), "first-actor");
        System.out.println("First: " + firstRef);
        firstRef.tell("msg", ActorRef.noSender());
        System.out.println(">>> Press ENTER to exit <<<");
        try {
            System.in.read();
        } finally {
            system.terminate();
        }
    }
}

class PrintMyActorRefActor extends AbstractActor {

    public static Props props() {
        return Props.create(PrintMyActorRefActor.class, PrintMyActorRefActor::new);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals("msg", p -> {
                    ActorRef secondRef = getContext().actorOf(Props.empty(), "second-actor");
                    System.out.println("Second: " + secondRef);
                })
                .build();
    }
}
