/**
 * Copyright (c) 2016-2019  All rights reserved.
 *
 *
 *
 *
 */

package com.demo.admin.controller;

import com.demo.common.exception.RRException;
import com.demo.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 文件上传
 *
 *
 */
@RestController
@RequestMapping("admin")
@Slf4j
public class UploadController {


	@Value("${imgPath}")
	private String imgPath;
	/**
	 * 上传文件
	 */
	@PostMapping("/upload")
	public R upload(@RequestParam("file") MultipartFile file,@RequestParam("id") Long id)  {
		if (file.isEmpty()) {
			throw new RRException("上传文件不能为空");
		}
		//上传文件
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		File dest = new File(imgPath +"bg_"+id+".jpg");
		try {
			file.transferTo(dest);
		} catch (IOException e) {
			throw new RRException("上传失败");
		}
		log.info(dest.getPath());
		return R.ok();
	}
}
