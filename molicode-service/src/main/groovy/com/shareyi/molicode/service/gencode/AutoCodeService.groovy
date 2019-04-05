package com.shareyi.molicode.service.gencode

import com.shareyi.molicode.common.vo.code.AutoCodeParams
import com.shareyi.molicode.common.vo.code.AutoMakeVo
import com.shareyi.molicode.common.web.CommonResult

interface AutoCodeService {

	/**
	 * 根据tableModel文件自动生成代码
	 * @param filePath tableModel文件路径
	 * @param config
	 * @return
	 */
	CommonResult<String>  generateCode(AutoCodeParams autoMakeParams)

    /**
     * 获取autoMake对象
     * @param autoCodeParams
     * @return
     */
   CommonResult<AutoMakeVo> getAutoMake(AutoCodeParams autoCodeParams)
	
}
