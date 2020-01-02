package com.test.activiti.k_personalTask;

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
/**���̱������ð�����*/
public class TaskTest {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	/**��������*/
	@Test
	public void StratProcessTest() {
		InputStream inputStreamBpmn = this.getClass().getResourceAsStream("task.bpmn");
		InputStream inputStreamPng = this.getClass().getResourceAsStream("task.png");
		Deployment deploy = processEngine.getRepositoryService()
		         .createDeployment()
		         .name("����")
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
		//���̱������ð�����
		Map<String , Object> variables = new HashMap<String, Object>();
		variables.put("userId", "С��");
		ProcessInstance processInstance = processEngine.getRuntimeService()
		             .startProcessInstanceByKey(processDefinitionKey, variables);
		System.out.println("���̶���ID : " + processInstance.getProcessInstanceId());
		System.out.println("����ʵ��ID : " + processInstance.getId());
	}
	/**��ѯ���˵�����*/
	@Test
	public void findMyPersonTaskTest(){
		String assignee = "С��";
		List<Task> list = processEngine.getTaskService().createTaskQuery().taskAssignee(assignee).list();
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
	/**��������*/
	@Test
	public void completeTaskTest(){
		String taskId = "5905";
		processEngine.getTaskService().complete(taskId);
		System.out.println("�������! ����id��:  " + taskId);
	}
}
