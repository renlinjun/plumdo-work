package com.plumdo.flow.cmd;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Collections;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.io.IOUtils;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.editor.language.json.converter.BpmnJsonConverter;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.common.impl.interceptor.Command;
import org.flowable.engine.common.impl.interceptor.CommandContext;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.image.ProcessDiagramGenerator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.plumdo.flow.exception.FlowableConflictException;

/**
 * 保存模型，同时生成图片
 *
 * @author wengwenhui
 * @date 2018年4月12日
 */
public class SaveModelEditorCmd implements Command<Void>, Serializable  {

	private static final long serialVersionUID = 1L;
	private String modelId;
	private String editorJson;
	
	public SaveModelEditorCmd(String modelId,String editorJson) {
		this.modelId = modelId;
		this.editorJson = editorJson;
	}
	
	public Void execute(CommandContext commandContext) {
		ProcessEngineConfiguration processEngineConfiguration = CommandContextUtil.getProcessEngineConfiguration(commandContext);
		RepositoryService repositoryService = processEngineConfiguration.getRepositoryService();

		try {
			byte[] bytes = editorJson.getBytes("utf-8");
			repositoryService.addModelEditorSource(modelId, bytes);
			ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(bytes);
			BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(modelNode);
			BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
			byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);
			XMLInputFactory xif = XMLInputFactory.newInstance();
			InputStreamReader xmlIn = new InputStreamReader(new ByteArrayInputStream(bpmnBytes), "UTF-8");
			XMLStreamReader xtr = xif.createXMLStreamReader(xmlIn);
			bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);
			
			ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
			InputStream resource = diagramGenerator.generateDiagram(bpmnModel,"png", 
						Collections.<String> emptyList(), Collections.<String> emptyList(), 
						processEngineConfiguration.getActivityFontName(), 
						processEngineConfiguration.getLabelFontName(), 
						processEngineConfiguration.getAnnotationFontName(),
						processEngineConfiguration.getClassLoader(), 1.0);
	
			repositoryService.addModelEditorSourceExtra(modelId, IOUtils.toByteArray(resource));
		} catch (Exception e) {
			throw new FlowableConflictException("create model exception :"+e.getMessage());
		}
	    return null;
	}

}