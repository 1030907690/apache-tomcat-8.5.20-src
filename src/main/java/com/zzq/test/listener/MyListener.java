package com.zzq.test.listener;

import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;

public class MyListener implements LifecycleListener{

	@Override
	public void lifecycleEvent(LifecycleEvent event) {
		/*System.out.println(" MyListener lifecycleEvent(LifecycleEvent event)");
		System.out.println("组件类型："+event.getLifecycle());  
        System.out.println("生命周期阶段 ： "+event.getType()); */
	}

}
