package test;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;


public class TestActivity {
  

	



	private ProcessEngine processEngine;

	/**
     * ʹ�ô��봴��23�ű�
     */
	@Test
	public void Test(){
		ProcessEngineConfiguration engineConfiguration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
		//�������ݿ������
		engineConfiguration.setJdbcDriver("com.mysql.jdbc.Driver");
		engineConfiguration.setJdbcUrl("jdbc:mysql://localhost:3306/activity?useUnicode=true&characterEncoding=utf8");
		engineConfiguration.setJdbcUsername("root");
		engineConfiguration.setJdbcPassword("root");
	    engineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		//������
	    processEngine = engineConfiguration.buildProcessEngine();
		System.out.println("processEngine ----------------------��" + processEngine);
	}
	
	@Test
	public void TestCreat(){
		ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml")
				       .buildProcessEngine();
		System.out.println("processEngine ----------------------��" + processEngine);
		
	}
}
