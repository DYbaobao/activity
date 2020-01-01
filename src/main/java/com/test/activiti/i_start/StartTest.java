package com.test.activiti.i_start;

import java.io.InputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class StartTest {

	ProcessEngine processEngine =ProcessEngines.getDefaultProcessEngine();
	/**�������̶��壨��inputStream��+ ��������ʵ��+�ж������Ƿ����+��ѯ��ʷ*/
	@Test
	public void startProcessInstance(){
		InputStream inputStreamBpmn = this.getClass().getResourceAsStream("start.bpmn");
		InputStream inputStreamPng = this.getClass().getResourceAsStream("start.png");
		/**��������*/
		Deployment deployment = processEngine.getRepositoryService()
		             .createDeployment()
		             .name("��ʼ�")
		             .addInputStream("start.bpmn", inputStreamBpmn)
		             .addInputStream("start.png", inputStreamPng)
		             .deploy();
		System.out.println("���̲���id : "+ deployment.getId());
		System.out.println("���̲������� : "+ deployment.getName());
		/**��ѯ���̲����key ���ݲ���id��ѯ*/
		ProcessDefinition prod = processEngine.getRepositoryService()
		             .createProcessDefinitionQuery()
		             .deploymentId(deployment.getId())
		             .singleResult();
		System.out.println("���̵�key : " + prod.getKey());
		/**��������ʵ��*/
		ProcessInstance startProcessInstance = processEngine.getRuntimeService()
		           .startProcessInstanceByKey(prod.getKey());
		System.out.println("����ʵ��ID:"+startProcessInstance.getId());//����ʵ��ID    
		System.out.println("���̶���ID:"+startProcessInstance.getProcessDefinitionId());//���̶���ID  
		System.out.println("���̶���ID:"+startProcessInstance.getActivityId());//���̶���ID  
	    /**��ѯ�����Ƿ����*/
		
		ProcessInstance prInstance = processEngine.getRuntimeService()
		         .createProcessInstanceQuery()
		         .processInstanceId(startProcessInstance.getId())
		         .singleResult();
	
		/**���̽���*/
		if (prInstance == null) {
			/**���̽�������ѯ��ʷ*/
			HistoricProcessInstance historicProcessInstance = processEngine.getHistoryService()
			              .createHistoricProcessInstanceQuery()
			              .processInstanceId(startProcessInstance.getId())
			              .singleResult();
			System.out.println(historicProcessInstance.getId()+"    "+historicProcessInstance.getStartTime()+"   "+historicProcessInstance.getEndTime()+"   "+historicProcessInstance.getDurationInMillis());
		}
	} 
	
}
