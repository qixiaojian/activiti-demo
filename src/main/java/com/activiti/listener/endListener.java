package com.activiti.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;


/**
 * Created by qixiaojian on 2016/12/2.
 */
public class endListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution delegateTask){

        System.out.println("Task End!");
    }

}
