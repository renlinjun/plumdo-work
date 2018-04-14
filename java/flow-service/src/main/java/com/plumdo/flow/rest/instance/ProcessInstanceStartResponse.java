package com.plumdo.flow.rest.instance;

import java.util.List;
import java.util.Map;

public class ProcessInstanceStartResponse {
	protected String id;
	protected String businessKey;
	protected String processDefinitionId;
	protected String processDefinitionName;
	protected String processDefinitionKey;
	protected String currentActivityId;
	protected String tenantId;
	protected List<Map<String, String>> taskInfo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getCurrentActivityId() {
		return currentActivityId;
	}

	public void setCurrentActivityId(String currentActivityId) {
		this.currentActivityId = currentActivityId;
	}

	public List<Map<String, String>> getTaskInfo() {
		return taskInfo;
	}

	public void setTaskInfo(List<Map<String, String>> taskInfo) {
		this.taskInfo = taskInfo;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public String getProcessDefinitionName() {
		return processDefinitionName;
	}

	public void setProcessDefinitionName(String processDefinitionName) {
		this.processDefinitionName = processDefinitionName;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}
	
}
