package com.ek.project.system.form.service.impl;


import com.ek.project.system.form.domain.FileInfo;
import com.ek.project.system.form.mapper.FormFileMapper;
import com.ek.project.system.form.service.FormFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FormFileServiceImpl implements FormFileService {

	@Autowired
	private FormFileMapper formFileMapper;

	@Override
	public void save(FileInfo fileInfo) {
		formFileMapper.save(fileInfo);
		
	}
	

	

}
