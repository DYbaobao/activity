package com.test.activiti.c_processInstance;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class ProcessInstanceTest {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/** ���̲��� */
	@Test
	public void processDefinition_ZipTest() {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("diagrams/helloworld.zip");
		Deployment deployment = processEngine.getRepositoryService().createDeployment().name("���̶���")
				.addZipInputStream(new ZipInputStream(inputStream)).deploy();
		System.out.println("���̲���ID : " + deployment.getId());
		System.out.println("���̲������� : " + deployment.getName());
	}

	/** �������� */
	@Test
	public void processDifinitionStratTest() {
		String processDefinitionKey = "helloworld";
		ProcessInstance processInstance = processEngine.getRuntimeService()
				.startProcessInstanceByKey(processDefinitionKey);
		System.out.println("���̶���ID : " + processInstance.getProcessInstanceId());
		System.out.println("����ʵ��ID : " + processInstance.getId());
	}

	/** ��ѯ���˵����� */
	@Test
	public void findMyPersonProcessTest() {
		String assignee = "����";
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

	/** ������� */
	@Test
	public void completeTaskTest() {
		// 1404
		String taskId = "1602";
		processEngine.getTaskService().complete(taskId);
		System.out.println("�������! ����Id ��" + taskId);
	}

	/** ��ѯ����״̬���ж���������ִ�У����ǽ����� */
	@Test
	public void findProcessStatus() {
		String processInstanceId = "1401";
		ProcessInstance processInstance = processEngine.getRuntimeService().createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		if (processInstance == null) {
			System.out.println("�����Ѿ�����!");
		} else {
			System.out.println("����δ����!");
		}
	}

	/** ��ѯ��ʷ���񣨼� */
	@Test
	public void findHistoryTaskTest() {
		String taskAssignee = "����";
		List<HistoricTaskInstance> list = processEngine.getHistoryService().createHistoricTaskInstanceQuery()
				.taskAssignee(taskAssignee).list();
		if (list != null && list.size() > 0) {
			for (HistoricTaskInstance historicTaskInstance : list) {
				System.out.println("**************************************");
				System.out.println("TaskDefinitionKey : " + historicTaskInstance.getTaskDefinitionKey());
				System.out.println("ID  : " + historicTaskInstance.getId());
				System.out.println("taskAssignee  : " + historicTaskInstance.getAssignee());
				System.out.println("StartTime : " + historicTaskInstance.getStartTime());
				System.out.println("EndTime : " + historicTaskInstance.getEndTime());
				System.out.println("Name : " + historicTaskInstance.getName());
				System.out.println("**************************************");
			}
		}
	}

	/** ��ѯ��ʷ����ʵ������ */
	@Test
	public void findHistoryProcessInstanceTest() {
		String processInstanceId = "1401";
		HistoricProcessInstance historicProcessInstance = processEngine.getHistoryService()
				.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		if (historicProcessInstance != null) {
			System.out.println("Id :" + historicProcessInstance.getId());
			System.out.println("StartUserId :" + historicProcessInstance.getStartUserId());
			System.out.println("ProcessDefinitionId :" + historicProcessInstance.getProcessDefinitionId());
		}
	}
}
