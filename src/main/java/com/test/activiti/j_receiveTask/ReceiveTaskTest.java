package com.test.activiti.j_receiveTask;

import java.io.InputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class ReceiveTaskTest {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	/**�������̶��壨��inputStream��+ ��������ʵ��+�������̱���+��ȡ���̱���+���ִ��һ��*/
	@Test
	public void StartProcessTest(){
		/**��������*/
		InputStream inputStreamBpmn = this.getClass().getResourceAsStream("receiveTask.bpmn");
		InputStream inputStreamPng = this.getClass().getResourceAsStream("receiveTask.png");
		Deployment deploy = processEngine.getRepositoryService()
		             .createDeployment()
		             .name("receiveTast")
		             .addInputStream("receiveTask.bpmn", inputStreamBpmn)
		             .addInputStream("receiveTask.png", inputStreamPng)
		             .deploy();
		System.out.println("���̲���id : "+ deploy.getId());
		System.out.println("���̲������� : "+ deploy.getName());
		/**��ѯ���̲����key ���ݲ���id��ѯ*/
		ProcessDefinition prod = processEngine.getRepositoryService()
		             .createProcessDefinitionQuery()
		             .deploymentId(deploy.getId())
		             .singleResult();
		System.out.println("���̵�key : " + prod.getKey()); 
		/**��������ʵ��*/
		ProcessInstance startProcessInstance = processEngine.getRuntimeService()
		           .startProcessInstanceByKey(prod.getKey());
		System.out.println("����ʵ��ID:"+startProcessInstance.getId());//����ʵ��ID    
		System.out.println("���̶���ID:"+startProcessInstance.getProcessDefinitionId());//���̶���ID  
		System.out.println("���̶���ID:"+startProcessInstance.getActivityId());//���̶���ID  
		/**��ѯִ�ж���ID*/
		Execution execution1 = processEngine.getRuntimeService()
		             .createExecutionQuery()
		             .processInstanceId(startProcessInstance.getId())
		             .activityId("receivetask1")
		             .singleResult();
		/**ʹ�����̱������õ������۶��������ҵ�����*/
		processEngine.getRuntimeService().setVariable(execution1.getId(), "�������۶�", 210000);
		/**���ִ��һ����������̴��ڵȴ�״̬��ʹ�����̼���ִ��*/
		processEngine.getRuntimeService().signal(execution1.getId());
		/**��ѯִ�ж���ID*/
		Execution execution2 = processEngine.getRuntimeService()
		             .createExecutionQuery()
		             .processInstanceId(startProcessInstance.getId())
		             .activityId("receivetask2")
		             .singleResult();
		/**�����̱����л�ȡ���ܵ������۶��ֵ*/
		Integer value = (Integer)processEngine.getRuntimeService()//
						.getVariable(execution2.getId(), "�������۶�");
		System.out.println("���ϰ巢�Ͷ��ţ�����ǣ�"+value);
		/**���ִ��һ����������̴��ڵȴ�״̬��ʹ�����̼���ִ��*/
		processEngine.getRuntimeService()
						.signal(execution2.getId());
	}
}
