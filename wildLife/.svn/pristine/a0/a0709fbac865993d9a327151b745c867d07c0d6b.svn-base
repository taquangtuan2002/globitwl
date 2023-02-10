package com.globits.wl.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.wl.dto.ForestProductsListDto;
import com.globits.wl.dto.SystemMessageDto;
import com.globits.wl.service.SystemMessageService;
import com.globits.wl.utils.WLConstant;

@RestController
@RequestMapping("api/systemmessage")
public class RestSystemMessageController {
	@Autowired
	private SystemMessageService systemMessageService;
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(method = RequestMethod.POST)
	public SystemMessageDto saveOrUpdate(@RequestBody SystemMessageDto dto) {
		return systemMessageService.saveOrUpdate(dto);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(method = RequestMethod.POST, value = "/forestproductslist")
	public SystemMessageDto saveSystemMessageForestProductList(@RequestBody ForestProductsListDto forestProductDto) {
		return systemMessageService.saveMessageForestProduct(forestProductDto);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public Object getDetailsOfMessage(@PathVariable("id") long id) {
		return systemMessageService.getSystemMessageOfTableName(id);
	}

}
