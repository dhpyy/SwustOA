package cn.swust.oa.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import org.jbpm.api.ProcessDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.swust.oa.base.ModelDrivenBaseAction;
import cn.swust.oa.domain.Template;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class TemplateAction extends ModelDrivenBaseAction<Template> {

	private File upload; // 上传的文件
	private InputStream inputStream; // 下载用的

	/** 列表 */
	public String list() throws Exception {
		List<Template> templateList = templateService.findAll();
		ActionContext.getContext().put("templateList", templateList);
		return "list";
	}

	/** 删除 */
	public String delete() throws Exception {
		templateService.deleteById(model.getId());
		return "toList";
	}

	/** 添加页面 */
	public String addUI() throws Exception {
		// 准备数据：流程定义对象
		List<ProcessDefinition> processDefinitionList = processDefinitionService.findAllLatestVersionList();
		ActionContext.getContext().put("processDefinitionList", processDefinitionList);
		return "saveUI";
	}

	/** 添加 */
	public String add() throws Exception {
		String path = saveUploadFile(upload);		// 保存到服务器中
		
		Template template = new Template();
		template.setName(model.getName());
		template.setProcessKey(model.getProcessKey());
		template.setPath(path);
		templateService.save(template);				// 保存到数据库中

		return "toList";
	}

	/** 修改页面 */
	public String editUI() throws Exception {
		// 准备回显的数据
		Template template = templateService.findById(model.getId());
		ActionContext.getContext().getValueStack().push(template);
		
		// 准备数据：processDefinitionList
		List<ProcessDefinition> processDefinitionList = processDefinitionService.findAllLatestVersionList();
		ActionContext.getContext().put("processDefinitionList", processDefinitionList);
	
		return "saveUI";
	}

	/** 修改 */
	public String edit() throws Exception {
		// 1，从数据库中获取原对象
		Template template = templateService.findById(model.getId());
		
		// 2，设置要修改的属性
		template.setName(model.getName());
		template.setProcessKey(model.getProcessKey());
		if(upload != null){ // 如果上传了文件，才表示要修改文件模板内容
			// 删除老文件
			File file = new File(template.getPath());
			if(file.exists()){
				file.delete();
			}
			// 保存新文件
			String path = saveUploadFile(upload);	// 保存到服务器中
			template.setPath(path);					
		}
		
		// 3，更新到数据库中
		templateService.update(template);			// 保存到数据库中
		
		return "toList";
	}

	/** 下载 */
	public String download() throws Exception {
		// 获取模板对象的在服务器中的路径
		Template template = templateService.findById(model.getId());
		String path = template.getPath();

		// 准备默认的文件名
		String fileName = template.getName();
		fileName = URLEncoder.encode(fileName, "utf-8"); // 解决下载的文件名的乱码问题
		ActionContext.getContext().put("fileName", fileName);

		// 准备输出到前端的输入流
		inputStream = new FileInputStream(path);
		return "download";
	}

	// ---

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

}