package com.globits.wl.rest;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.globits.wl.domain.Animal;
import com.globits.wl.dto.AnimalDto;
import com.globits.wl.dto.FarmAnimalDto;
import com.globits.wl.dto.functiondto.AnimalSearchDto;
import com.globits.wl.dto.functiondto.FarmSearchDto;
import com.globits.wl.dto.functiondto.MergeAnimal;
import com.globits.wl.service.AnimalService;
import com.globits.wl.service.impl.AnimalServiceImpl;
import com.globits.wl.utils.WLConstant;


@RestController
@RequestMapping("/api/animal")
public class RestAnimalController {
	@Autowired
	private AnimalService animalService;
	
	@Autowired
	private AnimalServiceImpl animalServiceImpl;
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/getall/{pageIndex}/{pageSize}",method=RequestMethod.GET)
	public Page<AnimalDto> getAll(@PathVariable("pageIndex")int pageIndex,@PathVariable("pageSize")int pageSize){
		return this.animalService.getAll(pageIndex,pageSize);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/getall",method=RequestMethod.GET)
	public List<AnimalDto> getAllDto(){
		return this.animalService.getAllDto();
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public AnimalDto getAnimalById(@PathVariable("id")Long id){
		return this.animalService.getAnimalById(id);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/getparent",method=RequestMethod.GET)
	public List<AnimalDto> getListParentDto(){
		return this.animalService.getListParent();
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/getbyparent/{parentId}",method=RequestMethod.GET)
	public List<AnimalDto> getListByParentId(@PathVariable("parentId")Long parentId){
		return this.animalService.getListByParent(parentId);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public AnimalDto createAnimal(@RequestBody AnimalDto dto){
		return this.animalService.createAnimal(dto);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/update/{id}",method=RequestMethod.PUT)
	public AnimalDto updateAnimal(@PathVariable("id") Long id,@RequestBody AnimalDto dto){
		return this.animalService.updateAnimal(id,dto);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/delete/{id}",method=RequestMethod.DELETE)
	public AnimalDto deleteAnimal(@PathVariable("id") Long id,@RequestBody AnimalDto dto){
		AnimalDto dtos=new AnimalDto();
		try {
			 this.animalService.deleteAnimal(id);
			 
			return dtos;
		} catch (Exception e) {			
			dtos.setCode("-1");
			return dtos;
			// TODO: handle exception
		}
		
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/delete/listid",method=RequestMethod.DELETE)
	public AnimalDto deleteAnimals(@RequestBody List<Long> ids){
		AnimalDto dtos=new AnimalDto();
		try {
			this.animalService.deleteAnimals(ids);			 
			return dtos;
		} catch (Exception e) {			
			dtos.setCode("-1");
			return dtos;
			// TODO: handle exception
		}		
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(path = "/searchDto/{pageIndex}/{pageSize}", method = RequestMethod.POST)	
	public ResponseEntity<Page<AnimalDto>> getListSimpleByNameCode(@RequestBody AnimalSearchDto dto,@PathVariable int pageIndex, @PathVariable int pageSize) {
		
		Page<AnimalDto> results = this.animalService.searchAnimalDto(dto, pageIndex, pageSize);
		return new ResponseEntity<Page<AnimalDto>>(results, HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/checkCode/{code}",method = RequestMethod.GET)
	public AnimalDto checkDuplicateCode(@PathVariable("code") String code) {
		return animalService.checkDuplicateCode(code);
	}

	/*get list lớp*/
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/getListAnimalClass",method=RequestMethod.GET)
	public List<String> getListAnimalClass(){
		return this.animalService.getListAnimalClass();
	}

	/*get list họ*/
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/getListAnimalFamily",method=RequestMethod.GET)
	public List<String> getListAnimalFamily(@RequestParam(value = "animalClass", required=false) String animalClass,@RequestParam(value = "ordo", required=false) String ordo){
		return this.animalService.getListAnimalFamily(animalClass, ordo);
	}
	
	/*Get list họ SearcDto*/
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/getListAnimalFamilyParam",method=RequestMethod.POST)
	public List<String> getListAnimalFamilyParam(@RequestBody AnimalSearchDto dto){
		return this.animalService.getListAnimalFamilyParam(dto);
	}

	/*get list bộ*/
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/getListAnimalOrdo",method=RequestMethod.GET)
	public List<String> getListAnimalOrdo(@RequestParam(value = "animalClass", required=false) String animalClass){
		return this.animalService.getListAnimalOrdo(animalClass);
	}
	
	/*Get list bộ AnimalSearchDto*/
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/getListAnimalOrdoParam",method=RequestMethod.POST)
	public List<String> getListAnimalOrdoParam(@RequestBody AnimalSearchDto dto){
		return this.animalService.getListAnimalOrdoParam(dto);
	}
	
	/*get vnList06*/
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/vnList06",method=RequestMethod.GET)
	public List<String> getVnList06(){
		return this.animalService.getVnList06();
	}
	/*get listCites*/
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/listCites",method=RequestMethod.GET)
	public List<String> getListCites(){
		return this.animalService.getListCites();
	}
	/*get vnList*/
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/vnList",method=RequestMethod.GET)
	public List<String> getVnList(){
		return this.animalService.getVnList();
	}
	/*get animal group*/
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/listAnimalGroup",method=RequestMethod.GET)
	public List<String> getListAnimalGroup(){
		return this.animalService.getListAnimalGroup();
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(path = "/pushAnimalToMap", method = RequestMethod.POST)	
	public void pushAnimalToMap() throws ClientProtocolException, JSONException, IOException {
		this.animalServiceImpl.pushAnimalToMap();
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/getListAnimalByFamily",method=RequestMethod.GET)
	public List<Animal> getListAnimalByFamily(@RequestParam(value = "family", required=false) String family){
		return this.animalService.getListAnimalByFamily(family);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT"})
	@RequestMapping(value="/getListAnimalByFarmId",method=RequestMethod.POST)
	public List<FarmAnimalDto> getListAnimalByFarmId(@RequestBody FarmSearchDto dto){
		return this.animalService.getListAnimalByFarmId(dto.getId());
	}
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/updateGroupToAllAnimal",method=RequestMethod.POST)
	public void updateGroupToAllAnimal(){
		this.animalService.updateGroupToAllAnimal();
	}
	
	/*get list lớp Sci*/
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/getListAnimalClassSci",method=RequestMethod.GET)
	public List<String> getListAnimalClassSci(){
		return this.animalService.getListAnimalClassSci();
	}
	
	/*get list bộ Sci*/
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/getListAnimalOrdoSci",method=RequestMethod.GET)
	public List<String> getListAnimalOrdoSci(@RequestParam(value = "animalClassSci", required=false) String animalClassSci){
		return this.animalService.getListAnimalOrdoSci(animalClassSci);
	}
	
	/*get list họ Sci*/
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/getListAnimalFamilySci",method=RequestMethod.GET)
	public List<String> getListAnimalFamilySci(@RequestParam(value = "animalClassSci", required=false) String animalClassSci,@RequestParam(value = "ordoSci", required=false) String ordoSci){
		return this.animalService.getListAnimalFamilySci(animalClassSci, ordoSci);
	}

	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/changeCode/{id}/{newCode}",method = RequestMethod.GET)
	public AnimalDto changeCode(@PathVariable("id") Long id, @PathVariable("newCode") String newCode) {
		return animalService.changeCode(id, newCode);
	}
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/changeReproductionForm/{id}/{reproductionForm}",method = RequestMethod.GET)
	public AnimalDto changeReproductionForm(@PathVariable("id") Long id, @PathVariable("reproductionForm") Integer reproductionForm) {
		return animalService.changeReproductionForm(id, reproductionForm);
	}
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/merge",method = RequestMethod.POST)
	public AnimalDto merge(@RequestBody MergeAnimal dto) {
		return animalService.mergeAnimal(dto);
	}
	
}
