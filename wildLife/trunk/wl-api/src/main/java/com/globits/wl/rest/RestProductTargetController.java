package com.globits.wl.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.wl.dto.FmsAdministrativeUnitDto;
import com.globits.wl.dto.ProductTargetDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.service.ProductTargetService;
import com.globits.wl.utils.WLConstant;

@RestController
@RequestMapping("/api/producttarget")
public class RestProductTargetController {
	@Autowired
	private ProductTargetService productTargetSevice;
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/page/{pageIndex}/{pageSize}",method = RequestMethod.GET)
	public Page<ProductTargetDto> getPageProductTarget(@PathVariable("pageIndex")int pageIndex,@PathVariable("pageSize")int pageSize){
		 return this.productTargetSevice.getPageProductTarget(pageIndex,pageSize);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/getall",method = RequestMethod.GET)
	public List<ProductTargetDto> getAllProductTarget(){
		 return this.productTargetSevice.getAllProductTarget();
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/getallByParentId/{parentId}",method = RequestMethod.GET)
	public List<ProductTargetDto> getAllProductTarget(@PathVariable("parentId") Long parentId){
		 return this.productTargetSevice.getAllByParentId(parentId);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/{id}",method = RequestMethod.GET)
	public ProductTargetDto getProducTargetById(@PathVariable("id")Long id){
		 return this.productTargetSevice.getProducById(id);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/create", method = RequestMethod.POST)
	public ProductTargetDto createProductTarget(@RequestBody ProductTargetDto dto){
		return this.productTargetSevice.createProductTarget(dto);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/update/{id}", method = RequestMethod.PUT)
	public ProductTargetDto updateProductTarget(@PathVariable("id")Long id,@RequestBody ProductTargetDto dto){
		return this.productTargetSevice.updateProductTarget(id,dto);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/delete/{id}", method = RequestMethod.DELETE)
	public ProductTargetDto updateProductTarget(@PathVariable("id")Long id){
		ProductTargetDto dto=new ProductTargetDto();
		try {
			dto=this.productTargetSevice.deleteProductTarget(id);		 
			return dto;
		} catch (Exception e) {			
			dto.setCode("-1");
			return dto;
		}
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/delete/ids", method = RequestMethod.DELETE)
	public ProductTargetDto updateProductTarget(@RequestBody List<Long> ids){
		ProductTargetDto dto=new ProductTargetDto();
		try {
			this.productTargetSevice.deleteProductTargets(ids);		 
			return dto;
		} catch (Exception e) {			
			dto.setCode("-1");
			return dto;
		}
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(path = "/searchDto/{pageIndex}/{pageSize}", method = RequestMethod.POST)	
	public ResponseEntity<Page<ProductTargetDto>> getListSimpleByNameCode(@RequestBody SearchDto dto,@PathVariable int pageIndex, @PathVariable int pageSize) {
		
		Page<ProductTargetDto> results = this.productTargetSevice.searchDto(dto, pageIndex, pageSize);
		return new ResponseEntity<Page<ProductTargetDto>>(results, HttpStatus.OK);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/checkCode/{code}",method = RequestMethod.GET)
	public ProductTargetDto checkDuplicateCode(@PathVariable("code") String code) {
		return productTargetSevice.checkDuplicateCode(code);
	}
}
