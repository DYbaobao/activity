package com.test.activiti.l_group02;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class TaskListenerImpl implements TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3586441620175906870L;

	@Override
	public void notify(DelegateTask delegateTask) {
		//ָ����������İ����ˣ�Ҳ����ָ��������İ�����
		//��������ͨ����ȥ��ѯ���ݿ⣬����һ������İ����˲�ѯ��ȡ��Ȼ��ͨ��setAssignee()�ķ���ָ������İ�����
		//delegateTask.setAssignee("���ʦ̫");
		//������
		delegateTask.addCandidateUser("����");
		delegateTask.addCandidateUser("����");
	}

}
