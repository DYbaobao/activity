package com.test.activiti.a_helloworld;

import java.text.SimpleDateFormat;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;


public class HelloWorld {

	
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	/**
	 * �������̶���
     *
     */   
	@Test
	public void  testDeploymentProcessDefinition(){
    	Deployment deployment= processEngine.getRepositoryService()//�����̶���Ͳ�����ص�service
    	             .createDeployment()//�����������
    	             .name("helloworld���ų���")
    	             .addClasspathResource("diagrams/helloworld.bpmn")
    	             .addClasspathResource("diagrams/helloworld.png")
    	             .deploy();
    	System.out.println("���̲���ID deployment.getId()"+deployment.getId());
    	System.out.println("deployment.getCategory()"+deployment.getCategory());
    	System.out.println("���̲������� deployment.getName()"+deployment.getName());
    	System.out.println("deployment.getClass()"+deployment.getClass());
    	System.out.println("deployment.getDeploymentTime()"+deployment.getDeploymentTime());
	}
    /**
     * ��������ʵ��
     */
	@Test
    public void testStartProcessInstance(){
		String processDefinitionKey = "helloworld";
		ProcessInstance processInstance =  processEngine.getRuntimeService()
		                                 .startProcessInstanceByKey(processDefinitionKey);
		System.out.println("����ʵ��Id "+processInstance.getId());
		System.out.println("���̶���Id "+processInstance.getProcessDefinitionId());
    }
	/**
	 * ��ѯ��ǰ�˵ĸ���ҵ��
	 */
	@Test
	public void testFindMyPersonTask(){
		String assignee = "����";
		List<Task> list =  processEngine.getTaskService()
		              .createTaskQuery().taskAssignee(assignee).list();
		if (list != null && list.size() > 0) {
			for (Task task : list) {
				System.out.println("**************************************************");
				System.out.println("����ID : " + task.getId());
				System.out.println("�������� : " + task.getName());
				System.out.println("���񴴽�ʱ�� : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(task.getCreateTime()));
				System.out.println("��������� : " + task.getAssignee());
				System.out.println("����ʵ��ID : " + task.getProcessInstanceId());
				System.out.println("ִ�ж���ID : " + task.getExecutionId());
				System.out.println("���̶���ID : " + task.getProcessDefinitionId());
				System.out.println("**************************************************");
			}
		}
	}
	
	@Test
	public void testCompleteMyPersponTask(){
		
		String taskId ="602";
		processEngine.getTaskService().complete(taskId);
		System.out.println("�������      ����id��" + taskId);
	}
	
	
}
