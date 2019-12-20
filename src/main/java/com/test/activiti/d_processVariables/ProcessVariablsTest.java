package com.test.activiti.d_processVariables;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class ProcessVariablsTest {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	/**���̲��� inputStream*/
	@Test
	public void prcessDefinition_InputStreamTest(){
		 InputStream inputStreamBpmn =  this.getClass().getResourceAsStream("/diagrams/processVariables.bpmn");
		 InputStream inputStreamPng =  this.getClass().getResourceAsStream("/diagrams/processVariables.png");
		 Deployment deployment =   processEngine.getRepositoryService()
		            .createDeployment()
		            .name("���̱���")
		            .addInputStream("processVariables.bpmn", inputStreamBpmn) //ʹ����Դ�ļ����� (Ҫ�� ���ļ�������һ��) ����������ɲ���
		            .addInputStream("processVariables.png", inputStreamPng) //ʹ����Դ�ļ����� (Ҫ�� ���ļ�������һ��)  ����������ɲ���
		            .deploy();
		 System.out.println("���̲���ID : "+ deployment.getId());
		 System.out.println("���̲������� : "+ deployment.getName());
	}
	/**�������� */
	@Test
	public void processDifinitionStratTest() {
		String processDefinitionKey = "processVariables";
		ProcessInstance processInstance = processEngine.getRuntimeService()
				.startProcessInstanceByKey(processDefinitionKey);
		System.out.println("���̶���ID : " + processInstance.getProcessInstanceId());
		System.out.println("����ʵ��ID : " + processInstance.getId());
	}
	
	/**�������̱���*/
	@Test
	public void  setVariables() {
		TaskService taskService = processEngine.getTaskService();
		//����ID
		String  taskId = "2504";
		/**һ �������̱���    ʹ�û�����������*/
	/*	taskService.setVariable(taskId, "�������", 3.5);
		taskService.setVariable(taskId, "�������", new Date());
		taskService.setVariable(taskId, "���ԭ��", "�ؼ�̽��!");*/
		/**�� �������̱���    ʹ��javabean����*/
		Person person  = new Person();
		person.setId(20);
		person.setName("��ʿ��");
		taskService.setVariable(taskId, "��Ա��Ϣ(��ǿ��)",person );
		System.out.println("�������̱����ɹ�!");
	}
	
	/**��ȡ���̱���*/
	@Test
	public void getVariables() {
		TaskService taskService = processEngine.getTaskService();
		String  taskId = "2702";
		/**һ ��ȡ���̱���    ʹ�û�����������*/
		/*Double days = (Double) taskService.getVariable(taskId, "�������");
		Date date  = (Date) taskService.getVariable(taskId, "�������");
		String resean =  (String) taskService.getVariable(taskId, "���ԭ��");
		System.out.println("�������  : " + days);
		System.out.println("�������  : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
		System.out.println("���ԭ��  : " + resean);*/
		/**�� ��ȡ���̱���    ʹ��javabean����*/
		Person person = (Person) taskService.getVariable(taskId, "��Ա��Ϣ(��ǿ��)");
		System.out.println("��ԱID  : " + person.getId());
		System.out.println("��Ա����  : " +  person.getName());
		
	}
	
	/**ģ�����úͻ�ȡ���̱����ĳ���*/
	public void setAndGetVariables(){
		/**����ʵ�� ִ�ж��� (��������)*/
		RuntimeService runtimeService =  processEngine.getRuntimeService();
		/**������ (��������)*/
		TaskService taskService = processEngine.getTaskService();
		/**�������̱���*/
		//runtimeService.setVariable(executionId, variableName, value);//��ʾ��ִ�ж���ID �����̱��������� �������̱�����ֵ һ��ֻ������һ��ֵ
		//runtimeService.setVariables(executionId, variables);//��ʾ��ִ�ж���ID ��map�����������̱�����ֵ map��key ʾ���̱��������� value�����̱�����ֵ
		
		//taskService.setVariable(taskId, variableName, value);//��ʾ������ID �����̱��������� �������̱�����ֵ һ��ֻ������һ��ֵ
		//taskService.setVariables(taskId, variables);//��ʾ������ID ��map�����������̱�����ֵ map��key ʾ���̱��������� value�����̱�����ֵ
		
		//runtimeService.startProcessInstanceByKey(processDefinitionKey, variables)//��������ʵ����ͬʱ �����������̱�����ֵ ��map
		//taskService.complete(taskId, variables);//�������ʱ�������̱�����ֵ ��map
		
		/**��ȡ���̱���*/
		//runtimeService.getVariable(executionId, variableName)//ʹ������ִ�ж���ID�����̱��������ƻ�ȡ���̱�����ֵ
		//runtimeService.getVariables(executionId) // ʹ������ִ�ж���ID ��ȡ���е����̱�����ֵ �����̱����ŵ�map�� map��key �ŵ������̱��������ƣ� value �����̱�����ֵ
		//runtimeService.getVariables(executionId, variableNames)//ʹ������ִ�ж���ID ��ȡ���̱��� �����̱��������Ʒŵ�list�� �ŵ�map
		
		//taskService.getVariable(taskId, variableName)//ʹ������ID�����̱��������ƻ�ȡ���̱�����ֵ
		//taskService.getVariables(taskId) // ʹ������ID ��ȡ���е����̱�����ֵ �����̱����ŵ�map�� map��key �ŵ������̱��������ƣ� value �����̱�����ֵ
		//taskService.getVariables(taskId, variableNames)//ʹ������ID ��ȡ���̱��� �����̱��������Ʒŵ�list�� �ŵ�map
	}
	
	
	/** ������� */
	@Test
	public void completeTaskTest() {
		// 1404
		String taskId = "2702";
		processEngine.getTaskService().complete(taskId);
		System.out.println("�������! ����Id ��" + taskId);
	}
	
	/** ��ѯ��ʷ�����̱��� */
	@Test
	public void findHistoryVariablesTest(){
		 List<HistoricVariableInstance> list = processEngine.getHistoryService()
		              .createHistoricVariableInstanceQuery()
		              .variableName("�������")
		              .list();
		 if (list!= null && list.size() > 0) {
			for (HistoricVariableInstance historicVariableInstance : list) {
				System.out.println("*************************************************");
				System.out.print("ID : " + historicVariableInstance.getId() +"\t" + ",");
				System.out.print("����ʵ��ID : " + historicVariableInstance.getProcessInstanceId() +"\t"+ ",");
				System.out.print("�������� : " + historicVariableInstance.getVariableName()+"\t" + ",");
				System.out.print("��������  : " + historicVariableInstance.getVariableTypeName()+"\t"+ ",");
				System.out.println("����ֵ : " + historicVariableInstance.getValue()+"\t");
				System.out.println("*************************************************");
				
			}
		}
	}
	
}
