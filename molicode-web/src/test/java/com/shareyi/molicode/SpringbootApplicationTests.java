package com.shareyi.molicode;

import com.shareyi.molicode.common.utils.FileIoUtil;
import com.shareyi.molicode.common.vo.code.AutoCodeParams;
import com.shareyi.molicode.common.vo.maven.MavenResourceVo;
import com.shareyi.molicode.common.web.CommonResult;
import com.shareyi.molicode.service.gencode.AutoCodeService;
import com.shareyi.molicode.service.maven.impl.MavenServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootApplicationTests {

	@Resource
	MavenServiceImpl service;

	@Resource
	AutoCodeService autoCodeService;


	@Test
	public void contextLoads() {
	}


	@Test
	public void testMavenResFile() {
		MavenResourceVo vo = new MavenResourceVo();
		vo.setLocalRepository("/Users/zhangshibin/.m2/repository/");
		vo.setGroupId("com.jd.b2b");
		vo.setArtifactId("b2b-user-svr-sdk");
		vo.setVersion("0.1.4-SNAPSHOT");
		File file = service.getMavenResourceFile(vo);
		System.out.println(file == null ? file : file.getAbsolutePath());
	}


	@Test
	public void testMakeMavenEvn() {
		MavenResourceVo vo = new MavenResourceVo();
		vo.setLocalRepository("/Users/zhangshibin/.m2/repository/");
		vo.setGroupId("com.jd.b2b");
		vo.setArtifactId("b2b-user-svr-sdk");
		vo.setVersion("0.1.4-SNAPSHOT");
		File parentFile = new File(FileIoUtil.getRunPath());
		vo.setMavenTempDir(new File(parentFile.getParent(), "maven_temp").getAbsolutePath());
		CommonResult resultOne = service.makeMavenExecuteEvn(vo);
		CommonResult result = service.fetchMavenResource(vo);
		System.out.println(result.getReturnMap());
	}


	/**
	 * 测试获取autoMake
	 */
	@Test
	public void testGetAutoMake() {
		AutoCodeParams params = new AutoCodeParams();
		params.setProjectKey("");
		autoCodeService.getAutoMake(params);
	}
}
