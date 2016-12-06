package com.activiti;

/**
 * Created by qixiaojian on 2016/12/6.
 */

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.activiti.model.Leave;

import org.junit.Test;

public class Demo {


    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    @Test
    public void startDeployment(){
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource("diagrams/VariableUsersDemo.bpmn")
                .addClasspathResource("diagrams/VariableUsersDemo.png")
                .deploy();
        System.out.println("Deploy deploy ID: " + deployment.getId());


    }

    @Test
    public void startProcessInstance(){

        String deployKey = "variableProcess";

        Map<String,Object> variables = new HashMap<>();

        variables.put("user","jianke");
        variables.put("manager","caohao");
        variables.put("boss","zhangqi");

        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(deployKey, variables);

        System.out.println("流程实例ID: " + processInstance.getId());
    }

    @Test
    public void startLeaveTask(){
        TaskService service = processEngine.getTaskService();
        String pID = "252501";
        List<Task> list = service.createTaskQuery().processInstanceId(pID).taskCandidateUser("jianke")
                .list();
        for(Task task:list){
            System.out.println("Task ID : " + task.getId() + "   " + task.getName());
            service.claim(task.getId(),"jianke");
            Leave leave = new Leave();
            leave.setName("jianke");
            leave.setContent("Go to Sleep");
            leave.setCreateTime(new Date());
            leave.setDays(22);
            service.setVariable(task.getId(), "info", leave);
            service.complete(task.getId());
        }

    }


    @Test
    public void findAppoveTask(){
        TaskService service = processEngine.getTaskService();
        String pID = "252501";
        List<Task> list = service.createTaskQuery().processInstanceId(pID).taskCandidateUser("caohao")
                .list();
        for(Task task:list){
            System.out.println("Task ID : " + task.getId() + "   " + task.getName());
            service.claim(task.getId(), "caohao");
            Leave leave = (Leave)service.getVariable(task.getId(),"info");

            Map<String,Object> object = new HashMap<>();
            object.put("days", leave.getDays());
            service.complete(task.getId(), object);
            System.out.println("complete!");
        }

    }

    @Test
    public void findBossTask(){
        TaskService service = processEngine.getTaskService();
        String pID = "252501";
        List<Task> list = service.createTaskQuery().processInstanceId(pID).taskCandidateUser("zhangqi")
                .list();
        for(Task task:list){
            System.out.println("Task ID : " + task.getId() + "   " + task.getName());
            service.claim(task.getId(), "zhangqi");
            Leave leave = (Leave)service.getVariable(task.getId(),"info");

            Map<String,Object> object = new HashMap<>();
            object.put("days", leave.getDays());
            service.complete(task.getId(), object);
            System.out.println("complete!");
        }

    }

}
