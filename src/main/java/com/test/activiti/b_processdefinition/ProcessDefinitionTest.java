package com.test.activiti.b_processdefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class ProcessDefinitionTest {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	
	/**
	 * ���̲����� classpath
	 */
	@Test
	public void prcessDefinition_ClassPathTest() {
		Deployment deploy = processEngine.getRepositoryService()
				.createDeployment().name("���̶���")
				.addClasspathResource("diagrams/helloworld.bpmn").addClasspathResource("diagrams/helloworld.bpmn")
				.name("���̶���_ClASSPATH").deploy();
		System.out.println("���̲���ID : " + deploy.getId());
		System.out.println("���̲�������  : " + deploy.getName());
	}

	/**
	 * ���̲����� zip
	 */
	@Test
	public void processDefinition_ZipTest() {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("diagrams/helloworld.zip");
		Deployment deploy = processEngine.getRepositoryService()
				.createDeployment()
				.addZipInputStream(new ZipInputStream(inputStream)).name("���̶���_ZIP").deploy();
		System.out.println("���̶���ID : " + deploy.getId());
		System.out.println("��������  : " + deploy.getName());
	}

	/**
	 * ��ѯ���̶���
	 */
	@Test
	public void findProcessDefinitionTest() {
		List<ProcessDefinition> processDefinitions = processEngine.getRepositoryService().createProcessDefinitionQuery() // ����һ�����̶���Ĳ�ѯ
				/** ָ����ѯ���� */
				// .deploymentId("1101")//ʹ�ò�������ID��ѯ 1101
				// .processDefinitionId("helloworld:5:1104")//ʹ�����̶���id��ѯ
				// .processDefinitionKey("helloworld")//ʹ�����̶���key��ѯ
				// .processDefinitionNameLike("helloworld%")//ʹ�����̶�������ģ����ѯ
				// .suspended()//���� (��ѯ����)
				/** ���� */
				.orderByProcessDefinitionVersion().asc()// ���ݰ汾��������
				// .orderByProcessDefinitionName().desc() //�������̶������ֽ�������
				/** ���صĽ���� */
				.list();// ����һ�����������װ���̶���Ķ���
		// .singleResult()//����Ψһ�����
		// .count() //���ؽ��������
		// .listPage(firstResult, maxResults)//��ҳ

		if (processDefinitions != null && processDefinitions.size() > 0) {
			for (ProcessDefinition processDefinition : processDefinitions) {
				System.out.println("******************************************");
				System.out.println("���̶���ID : " + processDefinition.getId()); // ���̶����key+�汾
																				// +
																				// �����
				System.out.println("���̶��������  : " + processDefinition.getName());// ��Ӧhelloworld.bpmn�ļ��е�name����ֵ
				System.out.println("���̶����key : " + processDefinition.getKey());// ��Ӧhelloworld.bpmn�ļ��е�id����ֵ
				System.out.println("���̶���İ汾 : " + processDefinition.getVersion());// �����̶����key��ͬʱ�汾����
																					// Ĭ��Ϊ1
				System.out.println("��Դ����bpmn�ļ� : " + processDefinition.getResourceName());
				System.out.println("��Դ����png�ļ� : " + processDefinition.getDiagramResourceName());
				System.out.println("�������ID : " + processDefinition.getDeploymentId());
				System.out.println("******************************************");
			}
		}
	}

	/** ɾ������ */
	@Test
	public void deleteProcessDefinitionTest() {
		String deploymentId = "1";
		/** ���Ǽ���ɾ�� ֻ��ɾ��û������������ ��������Ѿ�����ɾ�����״� */
		// processEngine.getRepositoryService().deleteDeployment(deploymentId);
		/** ����ɾ�� ���������Ƿ���������ɾ�� */
		processEngine.getRepositoryService().deleteDeployment(deploymentId, true);
		System.out.println("ɾ���ɹ�!");
	}

	/** ��ѯ����ͼ */
	@Test
	public void viewProcessDefinitionPngTest() throws IOException {

		String deploymentId = "1101";
		// ��ȡͼƬ��Դ������
		List<String> deploymentResourceNames = processEngine.getRepositoryService()
				.getDeploymentResourceNames(deploymentId);
		// ����ͼƬ������
		String resourceName = "";
		if (deploymentResourceNames != null && deploymentResourceNames.size() > 0) {
			for (String name : deploymentResourceNames) {
				if (name.indexOf(".png") > 0) {
					resourceName = name;
				}
			}
		}
		// ��ȡͼƬ�ļ���������
		InputStream in = processEngine.getRepositoryService().getResourceAsStream(deploymentId, resourceName);
		// �����ļ���d��
		File file = new File("D:/" + resourceName);
		FileUtils.copyInputStreamToFile(in, file);
	}

	/** ��ѯ���°汾�����̶��� */
	@Test
	public void findLastVersionProcessDefinitionTest() {
		List<ProcessDefinition> processDefinitions = processEngine.getRepositoryService().createProcessDefinitionQuery()
				.orderByProcessDefinitionVersion().asc().list();
		Map<String, ProcessDefinition> map = new LinkedHashMap<String, ProcessDefinition>();
		if (processDefinitions != null && processDefinitions.size() > 0) {
			for (ProcessDefinition processDefinition : processDefinitions) {
				map.put(processDefinition.getKey(), processDefinition);
			}
		}
		List<ProcessDefinition> pDefinitions = new ArrayList<ProcessDefinition>(map.values());
		if (pDefinitions != null && pDefinitions.size() > 0) {
			for (ProcessDefinition processDefinition : pDefinitions) {
				System.out.println("******************************************");
				System.out.println("���̶���ID : " + processDefinition.getId()); // ���̶����key+�汾 +  �����
				System.out.println("���̶��������  : " + processDefinition.getName());// ��Ӧhelloworld.bpmn�ļ��е�name����ֵ
				System.out.println("���̶����key : " + processDefinition.getKey());// ��Ӧhelloworld.bpmn�ļ��е�id����ֵ
				System.out.println("���̶���İ汾 : " + processDefinition.getVersion());// �����̶����key��ͬʱ�汾���� // Ĭ��Ϊ1
				System.out.println("��Դ����bpmn�ļ� : " + processDefinition.getResourceName());
				System.out.println("��Դ����png�ļ� : " + processDefinition.getDiagramResourceName());
				System.out.println("�������ID : " + processDefinition.getDeploymentId());
				System.out.println("******************************************");
			}
		}

	}

	/** ����key ɾ�� ���̶��� */
	@Test
	public void deleteProcessDefinitionBykeyTest() {
		String processDefinitionKey = "helloworld";
		List<ProcessDefinition> processDefinitions = processEngine.getRepositoryService()
										.createProcessDefinitionQuery()
										.processDefinitionKey(processDefinitionKey)
										.list();
		if (processDefinitions != null && processDefinitions.size() > 0) {
			for (ProcessDefinition processDefinition : processDefinitions) {
				processEngine.getRepositoryService().deleteDeployment(processDefinition.getDeploymentId(), true);
			}
		}
	}
}
