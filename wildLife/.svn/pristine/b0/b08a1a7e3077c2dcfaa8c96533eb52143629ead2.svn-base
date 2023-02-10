/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.ImportAnimal').controller('ImportAnimalDetailViewController', ImportAnimalDetailViewController);

    ImportAnimalDetailViewController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'ImportAnimalService',
        '$uibModal',
        'toastr',
        '$state',
        'blockUI',
        '$stateParams',
        'Utilities',
        'Upload',
        'FileSaver',
        'FarmService',
        'AnimalService',
        '$translate',
        'OriginalService',
        'ProductTargetService',
        'InjectionTimeService'
    ];
	
	angular.module('Education.ImportAnimal').directive('numberInput', function($filter) {
    	  return {
    	    require: 'ngModel',
    	    link: function(scope, elem, attrs, ngModelCtrl) {

    	      ngModelCtrl.$formatters.push(function(modelValue) {
    	        return setDisplayNumber(modelValue, true);
    	      });

    	      // it's best to change the displayed text using elem.val() rather than
    	      // ngModelCtrl.$setViewValue because the latter will re-trigger the parser
    	      // and not necessarily in the correct order with the changed value last.
    	      // see http://radify.io/blog/understanding-ngmodelcontroller-by-example-part-1/
    	      // for an explanation of how ngModelCtrl works.
    	      ngModelCtrl.$parsers.push(function(viewValue) {
    	        setDisplayNumber(viewValue);
    	        return setModelNumber(viewValue);
    	      });

    	      // occasionally the parser chain doesn't run (when the user repeatedly 
    	      // types the same non-numeric character)
    	      // for these cases, clean up again half a second later using "keyup"
    	      // (the parser runs much sooner than keyup, so it's better UX to also do it within parser
    	      // to give the feeling that the comma is added as they type)
    	      elem.bind('keyup focus', function() {
    	        setDisplayNumber(elem.val());
    	      });

    	      function setDisplayNumber(val, formatter) {
    	        var valStr, displayValue;

    	        if (typeof val === 'undefined') {
    	          return 0;
    	        }

    	        valStr = val.toString();
    	        displayValue = valStr.replace(/,/g, '').replace(/[A-Za-z]/g, '');
    	        displayValue = parseFloat(displayValue);
    	        displayValue = (!isNaN(displayValue)) ? displayValue.toString() : '';

    	        // handle leading character -/0
    	        if (valStr.length === 1 && valStr[0] === '-') {
    	          displayValue = valStr[0];
    	        } else if (valStr.length === 1 && valStr[0] === '0') {
    	          displayValue = '';
    	        } else {
    	          displayValue = $filter('number')(displayValue);
    	        }

    	        // handle decimal
    	        if (!attrs.integer) {
    	          if (displayValue.indexOf('.') === -1) {
    	            if (valStr.slice(-1) === '.') {
    	              displayValue += '.';
    	            } else if (valStr.slice(-2) === '.0') {
    	              displayValue += '.0';
    	            } else if (valStr.slice(-3) === '.00') {
    	              displayValue += '.00';
    	            }
    	          } // handle last character 0 after decimal and another number
    	          else {
    	            if (valStr.slice(-1) === '0') {
    	              displayValue += '0';
    	            }
    	          }
    	        }

    	        if (attrs.positive && displayValue[0] === '-') {
    	          displayValue = displayValue.substring(1);
    	        }

    	        if (typeof formatter !== 'undefined') {
    	          return (displayValue === '') ? 0 : displayValue;
    	        } else {
    	          elem.val((displayValue === '0') ? '' : displayValue);
    	        }
    	      }

    	      function setModelNumber(val) {
    	        var modelNum = val.toString().replace(/,/g, '').replace(/[A-Za-z]/g, '');
    	        modelNum = parseFloat(modelNum);
    	        modelNum = (!isNaN(modelNum)) ? modelNum : 0;
    	        if (modelNum.toString().indexOf('.') !== -1) {
    	          modelNum = Math.round((modelNum + 0.00001) * 100) / 100;
    	        }
    	        if (attrs.positive) {
    	          modelNum = Math.abs(modelNum);
    	        }
    	        return modelNum;
    	      }
    	    }
    	  };
    	});

    function ImportAnimalDetailViewController($rootScope, $scope, $http, $timeout, settings, service, modal, toastr, $state, blockUI, $stateParams, util, Uploader, FileSaver, farmService, animalService,$translate,originalService,productTargetService,injectionTimeService) {
        $scope.$on('$viewContentLoaded', function () {
            // initialize core components
            App.initAjax();
        });

        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        var vm = this;
        vm.ImportAnimal = {};
        vm.currentId = $state.params.id ? $state.params.id : null;
        vm.farmSelected = null;
        vm.animalSelected = null;
        vm.pageSize = 25;
        vm.pageIndex = 1;
        vm.farms = [];
        vm.animals = [];
        vm.importAnimal = {};
        vm.injectionPlants = [];
        vm.injectionPlant = {};
        vm.modalInjectionPlant = {};
        vm.indexInjectionPlant = -1;
        vm.originals = [];
        vm.original = {};
        vm.productTargets=[];
        vm.injectionTimes=[];
        vm.parentAnimals=[];
        vm.animalByParents=[];
        vm.animal={};
		vm.isRole=false;
		vm.isFamer=false;
		//------Start--Phân quyền theo user đăng nhập-----------
		blockUI.start();
        service.getCurrentUser().then(function (data) {
            console.log(data);
            vm.user=data;
            var roles = data.roles;
           
            if(roles!=null && roles.length>0){
                for(var i=0; i<roles.length;i++){
                    if (roles[i]!=null &&  roles[i].authority && (roles[i].authority.toLowerCase() == 'role_admin' ||roles[i].authority.toLowerCase() == 'role_dlp')) {
                        vm.isRole = true;						
                    }
					if (roles[i]!=null &&  roles[i].authority && roles[i].authority.toLowerCase() == 'role_famer' ) {
                        vm.isFamer = true;						
                    }
                }
            }else{
                vm.isRole=false;
				vm.isFamer=false;
            }
			/*if(vm.isRole){//trường hợp admin và dlp thì được xem tất cả các cơ sở chăn nuôi
				farmService.getAll().then(function (data) {
					vm.farms = data;
				}).catch(function (error) {
					vm.farms = [];
				});
			}else{// trường hợp này thì phân quyền cơ sở chăn nuôi theo user
				if(vm.isRole==false){
					getAdministrativeUnitDtoByLoginUser();
					
				}
			*/
            if(vm.isFamer==true){
				getAdministrativeUnitDtoByLoginUser();				
			}


            blockUI.stop();
        });
		
		//load user administrativeunit
		function getAdministrativeUnitDtoByLoginUser() {
            service.getAdministrativeUnitDtoByLoginUser().then(function(data) {
                vm.adminstrativeUnits = data;
				if(vm.adminstrativeUnits!=null && vm.adminstrativeUnits.length>0){
					getDataByCombobox(vm.adminstrativeUnits[0]);
					
				}
            });
        }
		function getDataByCombobox(administrativeUnit) {
			if( vm.searchDtoFarm==null){
                vm.searchDtoFarm={};
            }
			if(administrativeUnit.parentDto==null){
				vm.searchDtoFarm.province=administrativeUnit.id;
				
			}else if(administrativeUnit.parentDto!=null && administrativeUnit.parentDto.parentDto == null){
				vm.searchDtoFarm.district=administrativeUnit.id;
				vm.searchDtoFarm.province = administrativeUnit.parentDto.id;
				
			}else if(administrativeUnit.parentDto!=null && administrativeUnit.parentDto.parentDto != null){
				vm.searchDtoFarm.ward=administrativeUnit.id;				
                vm.searchDtoFarm.district = administrativeUnit.parentDto.id;
				vm.searchDtoFarm.province = administrativeUnit.parentDto.parentDto.id;
				
			}
			if(vm.isRole == false && vm.isFamer==true &&  vm.user!==null && vm.user.username!==null && vm.user.username!==''){//trường hợp tài khoản nông dân
                //gán tên cơ sở chăn nuôi
				vm.farmCode=vm.user.username;
                vm.searchDtoFarm.nameOrCode=vm.user.username;
               // console.log("username");
				//console.log(vm.user.username);
            }
			
			//vm.findBy();
			vm.findByFarm();
			
            
        }
		/*vm.findBy=function () {
            if( vm.searchDto==null){
                vm.searchDto={};
            }
 
            console.log(vm.searchDto);
            service.getPageSearchFarm(vm.searchDto).then(function(data) {
                 vm.farms = data;
                
            });
        }*/
				
		//--------End ----Phân quyền-------------
		
		/*
		* Tự động tạo mã lô
    */
		
		/*vm.getBatchCode=function(){
			console.log(vm.importAnimal.dateImport);
			service.getBatchCode(new Date()).then(function (data) {
				console.log(data);
				if (data) {
					vm.importAnimal.batchCode = data.batchCode;                       						
				}
			});
						 
		 }*/
        vm.generate = function () {
            if (vm.currentId) {
                service.getImportAnimal(vm.currentId).then(function (data) {
                    console.log(data);
                    if (data) {
                        vm.importAnimal = data;
                        vm.injectionPlants = data.injectionPlants;
                        console.log(vm.injectionPlants);
                        vm.farmSelected = data.farm;
						vm.farmName=vm.farmSelected.name;
                        vm.animalSelected = data.animal.parent;
						animalService.getListByParentId(vm.animalSelected.id).then(function (data) {
							vm.animalByParents = data;
							console.log(vm.animalByParents);
						});
						if(vm.importAnimal.animal!=null){
							animalService.getAnimal(vm.importAnimal.animal.id).then(function (data) {
							vm.animal = data;
							console.log(vm.animal);
							 vm.productTargets= vm.animal.animalProductTargets;
						});
						}
						
                    }
                });
            }else{
                let date = new Date();
				
                vm.importAnimal.dateImport = {year: date.getFullYear(), month: date.getMonth(), day: date.getDate()};
				//vm.getBatchCode();
            }
            
            animalService.getAll().then(function (data) {
                vm.animals = data;
            }).catch(function (error) {
                vm.animals = [];
            });
            
        }
		vm.changedBatchCode=function(date){
			if(date!=null && ($state.params.id==null|| $state.params.id==0)){
				service.getBatchCode(date).then(function (data) {
					console.log(data);
					if (data) {
						vm.importAnimal.batchCode = data.batchCode;                       						
					}
				});
			}

		}
        
        vm.saveImportAnimal = function () {
            /*if(!vm.importAnimal.batchCode){
                toastr.warning($translate.instant("importAnimal.requiredBatchCode"), $translate.instant("toastr.warning"));
                return ;
            }*/
            if(!vm.farmName){
                toastr.warning($translate.instant("importAnimal.farmName"), $translate.instant("toastr.warning"));
                return ;
            }
            if(!vm.importAnimal.dateImport){
                toastr.warning($translate.instant("importAnimal.requiredImportDate"), $translate.instant("toastr.warning"));
                return ;
            }
			if(vm.importAnimal.dateImport!=null && vm.importAnimal.dateImport>new Date()){
                toastr.warning($translate.instant("importAnimal.warningDate"), $translate.instant("toastr.warning"));
                return ;
            }
            if(!vm.farmSelected){
                toastr.warning($translate.instant("importAnimal.requiredFarm"), $translate.instant("toastr.warning"));
                return ;
            }
            if(!vm.importAnimal.animal){
                toastr.warning($translate.instant("importAnimal.requiredAnimal"), $translate.instant("toastr.warning"));
                return ;
            }
            if(!vm.importAnimal.quantity || vm.importAnimal.quantity<1){
                toastr.warning($translate.instant("importAnimal.requiredQuantity"), $translate.instant("toastr.warning"));
                return ;
            }
            if(!vm.importAnimal.dayOld || vm.importAnimal.dayOld<1){
              toastr.warning($translate.instant("importAnimal.requiredDayOld"), $translate.instant("toastr.warning"));
              return ;
            }
			if(vm.importAnimal.dayOld>2147483647){
              toastr.warning($translate.instant("importAnimal.exceedingDayOld"), $translate.instant("toastr.warning"));
              return ;
            }
            if(!vm.importAnimal.lifeCycle || vm.importAnimal.lifeCycle<1){
              toastr.warning($translate.instant("importAnimal.requiredLifeCycle"), $translate.instant("toastr.warning"));
              return ;
             }
             if(!vm.importAnimal.productTarget || vm.importAnimal.productTarget==null){
              toastr.warning($translate.instant("importAnimal.requiredProductTarget"), $translate.instant("toastr.warning"));
              return ;
             }
            vm.importAnimal.type = 1;            
            vm.importAnimal.injectionPlants = vm.injectionPlants;
            if (vm.currentId) {
                if (vm.importAnimal) {
                    service.updateImportAnimal(vm.currentId, vm.importAnimal, function success(data) {

                    }, function failure(error) {
						 toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    }).then(function(data){
                        if(data && data.batchCode == "-7"){
                            toastr.warning($translate.instant("importAnimal.warningImportDate"), $translate.instant("toastr.warning"));
                        }else if(data && data.batchCode == "-5"){
                            toastr.warning($translate.instant("importAnimal.warningImportQuantity"), $translate.instant("toastr.warning"));
                        }else{
                            toastr.info($translate.instant("importAnimal.saveSuccessfully"), $translate.instant("toastr.info"));
                            $state.go('application.import_animal');
                        }
                    });
                }
            } else {
                service.createImportAnimal(vm.importAnimal, function success(data) {
                    $state.go('application.import_animal');
                    toastr.info($translate.instant("importAnimal.saveSuccessfully"), $translate.instant("toastr.info"));
                }, function failure(error) {
					 toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                    console.log(error);
                });
            }
        }

        vm.goBack = function () {
            $state.go('application.import_animal');
        }
        vm.generate();


        vm.editInjectionPlants = function (index) {
            vm.indexInjectionPlant = index;
            vm.injectionPlant = JSON.parse(JSON.stringify(vm.injectionPlants[vm.indexInjectionPlant]));
            vm.injectionPlant.isNew = false;
			injectionTimeService.getAllInjectionTimeDto().then(function(data){
              vm.injectionTimes = data;
            });
            vm.modalInjectionPlant = modal.open({
                animation: true,
                templateUrl: 'injection_plant_detail.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        }
        vm.createInjectionPlants = function (index) {
            vm.indexInjectionPlant = -1;
            vm.injectionPlant = { isNew: true };
            vm.modalInjectionPlant = modal.open({
                animation: true,
                templateUrl: 'injection_plant_detail.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        }

        vm.deleteInjectionPlants = function (index) {
            vm.indexInjectionPlant = index;
            vm.modalInjectionPlant = modal.open({
                animation: true,
                templateUrl: 'confirm_delete_injection_plant.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        }
        

        vm.confirmDeleteImportAnimal = function () {
            vm.injectionPlants.splice(vm.indexInjectionPlant, 1);
            vm.modalInjectionPlant.close();
        }
        vm.injectionTimes = [];
        vm.addInjectionPlant = function(){
            vm.indexInjectionPlant = -1;
            let date = new Date();
            vm.injectionPlant = {isNew : true,injectionDate:{year: date.getFullYear(), month: date.getMonth(), day: date.getDate()}};
            injectionTimeService.getAllInjectionTimeDto().then(function(data){
              vm.injectionTimes = data;
            });
            vm.modalInjectionPlant = modal.open({
                animation: true,
                templateUrl: 'injection_plant_detail.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        }
        //Lưu lần tiêm
        vm.saveInjectionPlant = function () {
        	if(vm.validateAddInjectionPlant()){
                vm.injectionPlant.injectionRound = vm.injectionPlant.injectionTime.name;
                  
                  if(vm.indexInjectionPlant == -1){
                      vm.injectionPlants.push(vm.injectionPlant);
                  }else{
                      vm.injectionPlants[vm.indexInjectionPlant] = vm.injectionPlant;
                  }
                  vm.modalInjectionPlant.close();
                  console.log(vm.injectionPlants);
        	}
        }
      //Kiểm tra điều kiện nhập lần tiêm chủng
        vm.validateAddInjectionPlant = function(){
        	if(vm.injectionPlant!=null && moment(vm.injectionPlant.injectionDate).startOf('day').toDate() < moment(vm.importAnimal.dateImport).startOf('day').toDate()){
        		toastr.warning($translate.instant("importAnimal.injectionPlant.injectionDateEarly"));
        		return false;
        	}
        	if(!vm.injectionPlant.injectionDate){
                toastr.warning($translate.instant("importAnimal.injectionPlant.nullInjectionDate"));
                return false;
            }
        	if(vm.injectionPlant.injectionTime==null){
        		toastr.warning($translate.instant("importAnimal.injectionPlant.nullInjectionTime"));
                return false;
        	}
        	if(vm.injectionPlant.drug==null || vm.injectionPlant.drug==""){
        		toastr.warning($translate.instant("importAnimal.injectionPlant.nullDrug"));
                return false;
        	}
        	return true;
        }
        vm.onFarmSelected = function(item){
            vm.importAnimal.farm = vm.farmSelected;
        };

        vm.onAnimalSelected = function(item){
            /*vm.importAnimal.animal = vm.animalSelected;*/
        };
        /*
         * Nguồn gốc động vật
         */
        vm.getOriginals = function(){
        	originalService.getAllOriginalDto().then(function(data){
        		vm.originals = data;
        	});
        }
        vm.getOriginals();
        /*
         * Loại hình sản xuất
         */
        /*vm.getProductTargets = function(){
        	productTargetService.getAllProductTarget().then(function (data) {
                vm.productTargets = data;
                console.log(vm.productTargets)
            });
        }
        vm.getProductTargets();*/
        /*
         * Loại động vật
         */
        vm.getListParentAnimal = function(){
        	animalService.getListParent().then(function (data) {
        		vm.parentAnimals = data;
                console.log(vm.parentAnimals)
            });
        }
        vm.getListParentAnimal();
        /*
         * Sự kiện Chọn Loại động vật
         */
        vm.onSelectedAnimal=function(animal){			
            if (animal != null && animal.id>0){
				vm.importAnimal.animal=null;
				vm.importAnimal.productTarget=null;
                animalService.getListByParentId(animal.id).then(function (data) {
                vm.animalByParents = data;
                console.log(vm.animalByParents);
				
            });
			}
			
		}
		/*
         * Sự kiện Chọn Loại động vật con để lấy ra list hướng sản phẩm
         */
		vm.onSelectedAnimalByParent=function(item){		
			 vm.importAnimal.productTarget=null;
			if(item!=null && item.animalProductTargets!=null && item.animalProductTargets.length>0){
					 vm.productTargets= item.animalProductTargets;
				}else{
					 vm.productTargets= [];					
				}					
		}
		
		//--------------Popup Farm----------//
        vm.pageIndexFarm=1, vm.pageSizeFarm=10;
        vm.subModalInstance = {};

        vm.selectedFarms=[];
        vm.farmSelected={};
        vm.showPopupFarm = function () {
            vm.subModalInstance = modal.open({
                animation: true,
                templateUrl: 'choose_farm_modal.html',
                scope: $scope,
                size: 'lg'
            });
            vm.code=null;
            vm.selectedFarms=[];
			vm.farmSelected={};
			//lấy danh sách cơ sở chăn nuôi phân theo quyền
			if(vm.isRole){//trường hợp admin và dlp thì được xem tất cả các cơ sở chăn nuôi
				vm.findByFarm();
			}else{// trường hợp này thì phân quyền cơ sở chăn nuôi theo user
				if(vm.isRole==false){
					getAdministrativeUnitDtoByLoginUser();
					
				}
				
			}
			
           
            vm.subModalInstance.result.then(function (confirm) {
                if (confirm == 'yes') {
                   
                }
            }, function () {
				vm.farmCode=null;
                console.log("cancel");
            });
        }

        var closeModal = function () {
			
            vm.subModalInstance.close();

        };
        function checkAgreeFarm(){
            console.log("checkAgree");
            console.log(vm.selectedFarms);
            if(angular.isUndefined(vm.selectedFarms)|| vm.selectedFarms.length==0 ){
                toastr.warning($translate.instant("exportAnimal.notChooseFarm"), $translate.instant("toastr.warning"));
                return false;
            }
            return true;
        }
        vm.agreeFarm=function () {
            if(checkAgreeFarm()){
                vm.farmSelected=vm.selectedFarms[0];
				vm.farm=vm.selectedFarms[0];
                vm.farmName=vm.farm.name;
				vm.importAnimal.farm = vm.farm;
				
				//vm.findBy();
				
				vm.farmCode=null;
                closeModal();
            }
        }
        
        
        vm.bsTableControlFarm = {
            options: {
                data: vm.farms,
                idField: 'id',
                sortable: true,
                striped: true,
                maintainSelected: true,
                clickToSelect: false,
                showColumns: false,
                singleSelect: true,
                showToggle: false,
                pagination: true,
                pageSize: vm.pageSizeFarm,
                pageList: [5, 10, 25, 50, 100],
                locale: settings.locale,
                sidePagination: 'server',
                columns: service.getTableDefinitionFarm(),
                onCheck: function (row, $element) {
                    if( vm.selectedFarms!=null){
                        vm.selectedFarms=[];
                    }
                    $scope.$apply(function () {
                        vm.selectedFarms.push(row);
                    });
                },
              
                onUncheck: function (row, $element) {
                    
                    if( vm.selectedFarms!=null){
                        vm.selectedFarms=[];
                    }
                },
                
                onPageChange: function (index, pageSize) {
                    vm.pageSizeFarm = pageSize;
                    vm.pageIndexFarm = index;
                    //vm.searchTeachers();
					vm.findByFarm();
                }
            }
        };
		
		
		
		vm.enterSearchFarm = function(){
			// console.log(event.keyCode);
			if(event.keyCode == 13){//Phím Enter
				
				vm.searchByFarm();
			}
        };
		 vm.searchByFarm = function () {
            vm.pageIndexFarm = 1;
            vm.bsTableControlFarm.state.pageNumber = 1;
            vm.findByFarm();
        };
		vm.findByFarm=function () {
            if( vm.searchDtoFarm==null){
                vm.searchDtoFarm={};
            }
            if(vm.farmCode!=null&& vm.farmCode!=""){
                vm.searchDtoFarm.nameOrCode=vm.farmCode;
            }
            else{
                vm.searchDtoFarm.nameOrCode=null;
            }
			
			
            console.log("vm.searchDtoFarm");
            console.log(vm.searchDtoFarm);
            service.getPageSearchFarm(vm.searchDtoFarm,vm.pageIndexFarm,vm.pageSizeFarm).then(function(data) {
                vm.farms = data.content;
                vm.bsTableControlFarm.options.data = vm.farms;
                vm.bsTableControlFarm.options.totalRows = data.totalElements;
				console.log("vm.farms");
				console.log(vm.farms);
				if(vm.isFamer==true&& vm.farms!=null && vm.farms.length>0){
					if(vm.importAnimal==null)vm.importAnimal={};
					vm.farmName=vm.farms[0].name;
					vm.farmCode=vm.farms[0].code;
					vm.importAnimal.farm = vm.farms[0];
				}
				//console.log(vm.farms);
            });
        }
		
        
    }

})();
