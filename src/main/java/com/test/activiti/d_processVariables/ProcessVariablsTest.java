package com.test.activiti.d_processVariables;

import java.io.InputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

public class ProcessVariablsTest {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
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
		 System.out.println("���̶���ID : "+ deployment.getId());
		 System.out.println("���̶������� : "+ deployment.getName());
	}
}
