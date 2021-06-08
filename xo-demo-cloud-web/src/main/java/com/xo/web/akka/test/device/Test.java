/****************************************************
 * 创建人：  @author xieguocheng
 * 创建时间: 2021/03/18 16:45
 * 项目名称:  yh-risk-parent
 * 文件名称: Test
 * 文件描述: Test
 * 公司名称: 深圳市赢和信息技术有限公司
 *
 * All rights Reserved, Designed By 深圳市赢和信息技术有限公司
 * @Copyright:2016-2019
 *
 ********************************************************/
package com.xo.web.akka.test.device;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.testkit.typed.javadsl.TestKitJunitResource;
import akka.testkit.javadsl.TestKit;
import org.junit.ClassRule;

/**
 * 包名称： com.xo.web.akka.test.device
 * 类名称：Test
 * 类描述：Test
 * 创建人： xieguocheng
 * 创建时间：2021/03/18 16:45
 */
public class Test {

    @ClassRule
    public static final TestKitJunitResource testKit = new TestKitJunitResource();

    ActorSystem system = ActorSystem.create("system");

    @org.junit.Test
    public void testReplyToRegistrationRequests() {
        TestKit probe = new TestKit(system);
        ActorRef deviceActor = system.actorOf(Device.props("group", "device"));
        deviceActor.tell(new DeviceManager.RequestTrackDevice("group", "device"), probe.getRef());
        probe.expectMsgClass(DeviceManager.DeviceRegistered.class);
//        assertEquals(deviceActor, probe.getLastSender());
    }

    @org.junit.Test
    public void testIgnoreWrongRegistrationRequests() {
        TestKit probe = new TestKit(system);
        ActorRef deviceActor = system.actorOf(Device.props("group", "device"));
        //error 01
        deviceActor.tell(new DeviceManager.RequestTrackDevice("wrongGroup", "device"), probe.getRef());
        probe.expectNoMessage();
        //error 02
        deviceActor.tell(new DeviceManager.RequestTrackDevice("group", "wrongDevice"), probe.getRef());
        probe.expectNoMessage();
    }

    @org.junit.Test
    public void testListActiveDevices() {
        TestKit probe = new TestKit(system);

        ActorRef groupActor = system.actorOf(DeviceGroup.props("group"));
        groupActor.tell(new DeviceManager.RequestTrackDevice("group", "device1"), probe.getRef());
        probe.expectMsgClass(DeviceManager.DeviceRegistered.class);

        groupActor.tell(new DeviceManager.RequestTrackDevice("group", "device2"), probe.getRef());
        probe.expectMsgClass(DeviceManager.DeviceRegistered.class);

        groupActor.tell(new DeviceGroup.RequestDeviceList(0L), probe.getRef());
        DeviceGroup.ReplyDeviceList reply = probe.expectMsgClass(DeviceGroup.ReplyDeviceList.class);
//        assertEquals(0L, reply.requestId);
//        assertEquals(Stream.of("device1", "device2").collect(Collectors.toSet()), reply.ids);
    }

    @org.junit.Test
    public void testListActiveDevicesAfterOneShutsDown() {
        TestKit probe = new TestKit(system);

        ActorRef groupActor = system.actorOf(DeviceGroup.props("group"));

        groupActor.tell(new DeviceManager.RequestTrackDevice("group", "device1"), probe.getRef());
        probe.expectMsgClass(DeviceManager.DeviceRegistered.class);

        ActorRef toShutDown = probe.getLastSender();
        groupActor.tell(new DeviceManager.RequestTrackDevice("group", "device2"), probe.getRef());
        probe.expectMsgClass(DeviceManager.DeviceRegistered.class);

        groupActor.tell(new DeviceGroup.RequestDeviceList(0L), probe.getRef());//要停止的actor
        DeviceGroup.ReplyDeviceList reply = probe.expectMsgClass(DeviceGroup.ReplyDeviceList.class);

//        assertEquals(0L, reply.requestId);
//        assertEquals(Stream.of("device1", "device2").collect(Collectors.toSet()), reply.ids);
        probe.watch(toShutDown);
        toShutDown.tell(PoisonPill.getInstance(), ActorRef.noSender());
        probe.expectTerminated(toShutDown);
        // using awaitAssert to retry because it might take longer for the groupActor
        // to see the Terminated, that order is undefined
        probe.awaitAssert(() -> {
            groupActor.tell(new DeviceGroup.RequestDeviceList(1L), probe.getRef());
            DeviceGroup.ReplyDeviceList r = probe.expectMsgClass(DeviceGroup.ReplyDeviceList.class);
//            assertEquals(1L, r.requestId);
//            assertEquals(Stream.of("device2").collect(Collectors.toSet()), r.ids);
            return null;
        });
    }
}
