package com.test.activiti.l_group;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;

/**������*/
public class TastGroupTest {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	/**��������*/
	@Test
	public void StratProcessTest() {
		InputStream inputStreamBpmn = this.getClass().getResourceAsStream("task.bpmn");
		InputStream inputStreamPng = this.getClass().getResourceAsStream("task.png");
		Deployment deploy = processEngine.getRepositoryService()
		         .createDeployment()
		         .name("������")
		         .addInputStream("task.bpmn", inputStreamBpmn)
		         .addInputStream("task.png", inputStreamPng)
		         .deploy();
		System.out.println("���̲���ID :" +deploy.getId());
		System.out.println("���̲������� :" +deploy.getName());
	}
	
	/**��������*/
	@Test
	public void processDifinitionStratTest(){
		String processDefinitionKey ="task";
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("userIds", "���,СС,����");
		ProcessInstance processInstance = processEngine.getRuntimeService()
		              .startProcessInstanceByKey(processDefinitionKey,variables);
		System.out.println("���̶���ID : " + processInstance.getProcessInstanceId());
		System.out.println("����ʵ��ID : " + processInstance.getId());
	}
	
	/**��ѯ��ǰ�˵ĸ�������*/
	@Test
	public void findMyPersonTask(){
		String assignee = "���";
		List<Task> list = processEngine.getTaskService()
		             .createTaskQuery()
		             .taskAssignee(assignee)
		             .orderByTaskCreateTime()
		             .desc()
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
	
	/**��ѯ��ǰ�˵�������*/
	@Test
	public void findGroupTask(){
		String candidateUser = "���";
		List<Task> list = processEngine.getTaskService()
		             .createTaskQuery()
		             .taskCandidateUser(candidateUser)
		             .orderByTaskCreateTime()
		             .desc()
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
	
	
	/**����ҵ�����*/
	@Test
	public void completeMyPersonalTask(){
		//����ID
		String taskId = "6905";
		processEngine.getTaskService()//������ִ�е����������ص�Service
					.complete(taskId);
		System.out.println("�����������ID��"+taskId);
	}
	
	/**��ѯ����ִ�е���������˱�*/
	@Test
	public void findRunPersonTask() {
		String  taskId = "";
	    List<IdentityLink> identityLinksForTask = processEngine.getTaskService().getIdentityLinksForTask(taskId);
	    if (identityLinksForTask != null && identityLinksForTask.size() >0) {
	    	for (IdentityLink identityLink : identityLinksForTask) {
	    		System.out.println(identityLink.getTaskId()+"   "+identityLink.getType()+"   "+identityLink.getProcessInstanceId()+"   "+identityLink.getUserId());
			}
	    }
	
	}
	/**��ѯ��ʷ����İ����˱�*/
	@Test
	public void findHistoryPersonTask(){
		String processInstanceId ="";
		List<HistoricIdentityLink> historicIdentityLinksForProcessInstance = processEngine.getHistoryService()
				             .getHistoricIdentityLinksForProcessInstance(processInstanceId);
		if (historicIdentityLinksForProcessInstance != null && historicIdentityLinksForProcessInstance.size() > 0 ) {
			for (HistoricIdentityLink historicIdentityLink : historicIdentityLinksForProcessInstance) {
				System.out.println(historicIdentityLink.getTaskId()+"   "+historicIdentityLink.getType()+"   "+historicIdentityLink.getProcessInstanceId()+"   "+historicIdentityLink.getUserId());
			}
		}
	}
	
	/**ʰȡ���񣬽�������ָ���������ָ������İ������ֶ�*/
	@Test
	public void claim (){
		//��������������������
		//����ID
		 String taskId = "";
		 //����ĸ������񣨿������������еĳ�Ա��Ҳ�����Ƿ�������ĳ�Ա��
		 String userId = "";
		 processEngine.getTaskService().claim(taskId, userId);
	}
	
	
	/**������������˵�������ǰ�ᣬ֮ǰһ���Ǹ�������*/
	@Test
	public void setAssignee() {
		String taskId = "";
		processEngine.getTaskService().setAssignee(taskId, "");
	}
	
	/**������������ӳ�Ա*/
	@Test
	public void addGroupUser(){
		//����ID
		String taskId = "";
		//ҵ�������
		String userId = "";
		processEngine.getTaskService().addCandidateUser(taskId, userId);
	}
	/**����������ɾ����Ա*/
	public void deleteGroupUser(){
		//����ID
		String taskId = "";
		//ҵ�������
		String userId = "";
		processEngine.getTaskService().deleteCandidateUser(taskId, userId);
	}
	
}
