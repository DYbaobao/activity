package com.test.activiti.f_sequenceFlow;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class SequenceFlowTest {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	
	/**��������*/
	@Test 
	public void prcessDefinition_InputStreamTest() {
		InputStream inputStreamBpmn = this.getClass().getResourceAsStream("sequenceFlow.bpmn");
		InputStream inputStreamPng = this.getClass().getResourceAsStream("sequenceFlow.png");
		Deployment deploy = processEngine.getRepositoryService()
		       .createDeployment()
		       .name("����")
		       .addInputStream("sequenceFlow.bpmn", inputStreamBpmn)
		       .addInputStream("sequenceFlow.png", inputStreamPng)
		       .deploy();
		System.out.println("���̲���ID : " +deploy.getId()); 
		System.out.println("���̲������� : " +deploy.getName()); 
	}
	
	/**��������*/
	@Test
	public void processDefinitionStartTest() {
		String processDefinitionKey ="processSequenceFlow";
		ProcessInstance processInstance = processEngine.getRuntimeService()
		      .startProcessInstanceByKey(processDefinitionKey);
		System.out.println("���̶���ID : " + processInstance.getProcessInstanceId());
		System.out.println("����ʵ��ID : " + processInstance.getId());	      
	}
	/**��ѯ���˵�ҵ��*/
	@Test
	public void findMyPersonProcessTest(){
		String assignee = "����";
		List<Task> list = processEngine.getTaskService()
		.createTaskQuery()
		.taskAssignee(assignee)
		.list();
		if (list != null && list.size() > 0) {
			for (Task task : list) {
				System.out.println("**************************************************");
				System.out.println("����ID : " + task.getId());
				System.out.println("�������� : " + task.getName());
				System.out.println(
						"���񴴽�ʱ�� : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(task.getCreateTime()));
				System.out.println("��������� : " + task.getAssignee());
				System.out.println("����ʵ��ID : " + task.getProcessInstanceId());
				System.out.println("ִ�ж���ID : " + task.getExecutionId());
				System.out.println("���̶���ID : " + task.getProcessDefinitionId());
				System.out.println("**************************************************");
			}
		}
	}
	
	/**��ɱ��˵�����*/
	@Test
	public void completeTaskTest () {
		String taskId = "3303";
		Map<String, Object> variables =  new HashMap<String, Object>();	
		variables.put("message", "��Ҫ");
	    processEngine.getTaskService()
	       .complete(taskId, variables);
	    System.out.println("�������,�����ID��"  + taskId);
	}
}
