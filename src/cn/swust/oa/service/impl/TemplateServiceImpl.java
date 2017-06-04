package cn.swust.oa.service.impl;

import java.io.File;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.swust.oa.base.DaoSupportImpl;
import cn.swust.oa.domain.Template;
import cn.swust.oa.service.TemplateService;


@Service
@Transactional
public class TemplateServiceImpl extends DaoSupportImpl<Template> implements TemplateService {

	@Override
	public void deleteById(Long id) {
		// 删除数据库记录
		Template template = findById(id);
		getSession().delete(template);

		// 删除服务器中的文件
		File file = new File(template.getPath());
		if (file.exists()) {
			file.delete();
		}
	}
	
}
