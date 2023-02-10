/**
 * Created by bizic on 28/8/2016.
 */
(function() {
    'use strict';

    angular.module('Education.ExportAnimal').controller('ExportAnimalDetailController', ExportAnimalDetailController);

    ExportAnimalDetailController.$inject = [
        '$rootScope',
        '$scope',
        '$timeout',
        'settings',
        '$uibModal',
        'toastr',
        'blockUI',
        'bsTableAPI',
        'Utilities',
        'ExportAnimalService',
        'AnimalService',
        'FarmService',
        '$state',
        'OriginalService',
        'ProductTargetService',
        '$translate',
        'FmsAdministrativeService',
        'Upload'
    ];
	
	angular.module('Education.ExportAnimal').directive('numberInput', function($filter) {
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

    function ExportAnimalDetailController($rootScope, $scope, $timeout, settings, modal, toastr, blockUI, bsTableAPI, utils, service, AnimalService, FarmService, $state, originalService,productTargetService,$translate,FmsAdministrativeService,Upload) {
        $scope.$on('$viewContentLoaded', function() {
            // initialize core components
            App.initAjax();
        });

        // set sidebar closed and body solid layout mode
        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        var vm = this;
        var startDate = new Date();    
        var endDate   = new Date();
        var seconds = (endDate.getTime() - startDate.getTime()) / 1000;
        
        while($rootScope.user==null && seconds<60){
        	endDate = new Date();
    		seconds = (endDate.getTime() - startDate.getTime()) / 1000;
        }
        //alert($rootScope.user.displayName);
        
        vm.user = {}; // Doi tượng
        vm.users = []; // Mảng
        vm.exportAnimal = {}; // mảng Region
        vm.exportAnimals = [];
       
        vm.id = $state.params.id ? $state.params.id : null;
        console.log(vm.id)

        vm.selectedExportAnimal = [];

        vm.roles = [];
        vm.groups = [];

        // UI
        vm.modalInstance = null;
        vm.animals = [];
        vm.farms = []
        vm.farm=null;
        vm.inventory = {};
        vm.inventory.quantity = 0;
        vm.inventory.amount = 0;
        vm.inventory.male = 0;
        vm.inventory.female = 0;
        vm.inventory.unGender = 0;
        vm.exportTypes = [
            {value: 1, name: "Xuất bán"},
            {value: 2, name: "Xuất chết"}
        ];
        // pagination
        vm.pageIndex = 0;
        vm.pageSize = 10;

        vm.changePass = false;
		vm.types = settings.types;
		vm.reasons = settings.reasons;
        vm.firstQuantity = 0;//Số lượng ban đầu, trong trường hợp sửa bản ghi xuất đàn
        vm.firstMale = 0;// Số lượng ban đầu trong trường hợp sửa bản ghi xuất đàn
        vm.firstFemale = 0;// Số lượng ban đầu trong trường hợp sửa bản ghi xuất đàn
        vm.firstUnGender = 0;// Số lượng ban đầu trong trường hợp sửa bản ghi xuất đàn
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
			if(vm.isFamer==true){
				getAdministrativeUnitDtoByLoginUser();				
			}
            


            blockUI.stop();
        });

        service.getPageSearchFarm({showListFarmSelect: true}, 1, 10).then(function(data){
            // debugger
            if(data.content && data.content.length == 1){
                const farmTemp = data.content[0];
                vm.exportAnimal.farm = farmTemp;
                vm.farmName = "";
                if(farmTemp.code){
                    vm.farmName += farmTemp.code+ " - ";
                }
                vm.farmName += farmTemp.name;
            }
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
                vm.searchDtoFarm.nameOrCode=vm.user.username;
				vm.farmCode=vm.user.username;
               // console.log("username");
				//console.log(vm.user.username);
            }
			
			vm.findByFarm();
            
        }
		
				
		//--------End ----Phân quyền-------------
		
		
        //vm.exportAnimal.dateExport = moment();
        /**
         * Get a list of ExportAnimal
         */
        vm.goBack = function () {
            $state.go('application.export_animal');
        }
		
		vm.saveExportAnimal = function() {
			console.log(vm.exportAnimal.quantity);
            if (!vm.farmName) {
				 toastr.warning($translate.instant("exportAnimal.emptyFarm"), $translate.instant("toastr.warning"));
                return;
            }
			// if (vm.exportAnimal.importAnimal==null || vm.exportAnimal.importAnimal.id==null || vm.exportAnimal.importAnimal.id==0) {
			// 	 toastr.warning($translate.instant("exportAnimal.emptyBatchCode"), $translate.instant("toastr.warning"));
            //     return;
            // }
            if (!vm.exportAnimal.animal) {
                toastr.warning($translate.instant("exportAnimal.emptyAnimal"), $translate.instant("toastr.warning"));
                return;
            }
            if (!vm.exportAnimal.dateExport || vm.exportAnimal.dateExport=="") {
                toastr.warning($translate.instant("exportAnimal.emptyDateExport"), $translate.instant("toastr.warning"));
                return;
            }
			if(vm.exportAnimal.dateExport!=null && vm.exportAnimal.dateExport>new Date()){
                toastr.warning($translate.instant("exportAnimal.warningDate"), $translate.instant("toastr.warning"));
                return ;
            }
			if(vm.exportAnimal.dateExport!=null && vm.dateImport!=null && vm.exportAnimal.dateExport<vm.dateImport){
				 toastr.warning($translate.instant("exportAnimal.dateImportExport"), $translate.instant("toastr.warning"));
                return;
			}
           
            if (!vm.exportAnimal.quantity || vm.exportAnimal.quantity <1) {
                toastr.warning($translate.instant("exportAnimal.emptyQuantity"), $translate.instant("toastr.warning"));
                return;
            }
            
             if (vm.exportAnimal.importAnimal!=null && vm.exportAnimal.quantity > vm.exportAnimal.importAnimal.remainQuantity + vm.firstQuantity) {
             	toastr.warning($translate.instant("exportAnimal.errorQuantity"), $translate.instant("toastr.warning"));
                 return;
             }
             if (vm.exportAnimal !=null && vm.exportAnimal.male > vm.inventory.male + vm.firstMale) {
             	toastr.warning("Số lượng con đực khai báo giảm vượt quá số lượng có trong cơ sở", $translate.instant("toastr.warning"));
                 return;
             }
             if (vm.exportAnimal !=null && vm.exportAnimal.female > vm.inventory.female + vm.firstFemale) {
             	toastr.warning("Số lượng con cái khai báo giảm vượt quá số lượng có trong cơ sở", $translate.instant("toastr.warning"));
                 return;
             }
             if (vm.exportAnimal !=null && vm.exportAnimal.unGender > vm.inventory.unGender + vm.firstUnGender) {
             	toastr.warning("Số lượng con không xác định giới tính khai báo giảm vượt quá số lượng có trong cơ sở", $translate.instant("toastr.warning"));
                 return;
             }
            //  if(vm.inventory.male < vm.exportAnimal.male){
            //     toastr.warning("Số con đực không lớn số con đực còn lại", $translate.instant("toastr.warning"));
            //     return;
            //  }
			// if(vm.exportAnimal.exportType>0 && vm.exportAnimal.exportType==3 && vm.exportAnimal.productTargetChange==null) {
            // 	toastr.warning($translate.instant("exportAnimal.emptyProductTargetChange"), $translate.instant("toastr.warning"));
            //     return;
            // }
            
            vm.exportAnimal.type = -1; 
            
            if (vm.id) {
                if (vm.exportAnimal) {
                    service.updateImportAnimal(vm.id, vm.exportAnimal).then(function(data) {
                       
						console.log(data);
						if(data!=null){
							toastr.info($translate.instant("exportAnimal.saveSuccessfully"), $translate.instant("toastr.info"));
							 $state.go('application.export_animal');
						}else{
							toastr.warning($translate.instant("exportAnimal.errorQuantity"), $translate.instant("toastr.warning"));
							return false;
						}
                    });
					/*, function (respone) {
                        toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    });*/
                }
            } else {
                service.save(vm.exportAnimal).then(function(data) {
                   
					console.log(data);
					if(data!=null){						
						$state.go('application.export_animal');
						toastr.info($translate.instant("exportAnimal.saveSuccessfully"), $translate.instant("toastr.info"));
					}else{
						toastr.warning($translate.instant("exportAnimal.errorQuantity"), $translate.instant("toastr.warning"));
						return false;
					}
                });
				/*, function (respone) {
                    toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                    console.log(error);
                });*/
            }
        };
        
        /**
         * Create a new exportAnimal
         */
       
        vm.newExportAnimal = function() {

            vm.exportAnimal.isNew = true;
            vm.exportAnimal.dateExport = moment(new Date(), 'DD/MM/YYYY').toDate();
            AnimalService.getAll().then(function(data){
               vm.animals = data;
            })
           
        };
        vm.newExportAnimal();
        
        vm.updateQuantity = function(){
            vm.exportAnimal.quantity = 0;
            if(vm.exportAnimal.male && vm.exportAnimal.male > 0){
                vm.exportAnimal.quantity += vm.exportAnimal.male;
            }
            if(vm.exportAnimal.female && vm.exportAnimal.female > 0){
                vm.exportAnimal.quantity += vm.exportAnimal.female;
            }
            if(vm.exportAnimal.unGender && vm.exportAnimal.unGender > 0){
                vm.exportAnimal.quantity += vm.exportAnimal.unGender;
            }
        }
        
        $scope.editExportAnimal = function(id) {

            service.getExportAnimalById(id).then(function(data) {
                if (data) {
                    vm.exportAnimal = data;
                    vm.exportAnimal.isNew = false;
                    vm.firstQuantity = vm.exportAnimal.quantity;
                    vm.firstMale = vm.exportAnimal.male;
                    vm.firstFemale = vm.exportAnimal.female;
                    vm.firstUnGender = vm.exportAnimal.unGender;
                    vm.getInventoryReportRemainQuantity();
                    vm.animal = data.animal;
                    if (vm.animal != null) {
                        vm.animalSelected = vm.animal.parent;
						/*AnimalService.getListByParentId(vm.animalSelected.id).then(function (data) {
							vm.animals = data;
							console.log(vm.animals);
						});
						AnimalService.getAnimal(vm.animal.id).then(function (data) {
							vm.animal = data;
							console.log(vm.animal);
							 vm.productTargets= vm.animal.animalProductTargets;
						});*/
						
                    }
                    vm.batchCodeSelected = vm.exportAnimal.importAnimal;
                    vm.farm = data.farm;
                    vm.farmName = "";
                    if(vm.farm.code){
                        vm.farmName += vm.farm.code+ " - ";
                    }
                    vm.farmName += vm.farm.name;
					// vm.farmName=vm.farm.name;
					vm.findBy();
					// vm.dateImport=vm.exportAnimal.importAnimal.dateImport;
                    //AnimalService.getAll().then(function(data){
                    //    vm.animals = data;
                    //})
                    /*FarmService.getAll().then(function(dataFarm){
                        vm.farms = dataFarm;
                    })*/
                }
            });
        }
        if(vm.id){
            $scope.editExportAnimal(vm.id)
        }else {
            let date = new Date(); 
            //vm.exportAnimal.dateImport = {year: date.getFullYear(), month: date.getMonth(), day: date.getDate()};
        }
        $scope.deleteExportAnimal = function(id) {
            vm.id = id;
            vm.modalInstance = modal.open({
                animation: true,
                templateUrl: 'delete_modal.html',
                backdrop: 'static',
                scope: $scope,
                size: 'lg'
            });


        }
        vm.confirmDelete = function() {
            // alert(vm.id)
            service.deleteExportAnimalById(vm.id).then(function(data) {
            	if(data.code="-2"){
            		toastr.warning(data.name, $translate.instant("toastr.warning"));
            	}
            	else{
            		vm.getExportAnimals();
            	}                
                vm.modalInstance.close();
            });
        }
        vm.preMale = 0;
        vm.preFemale = 0;
        vm.preUnGender= 0;
        vm.onAnimalSelected = function (item) {
            vm.getInventoryReportRemainQuantity();
            /*vm.importAnimal.animal = vm.animalSelected;*/
            if(vm.exportAnimal.animalReportDataType == settings.AnimalReportDataTypeENUM.childUnder1YearOld){
                if(vm.exportAnimal.male){
                    vm.preMale = vm.exportAnimal.male;
                }
                if(vm.exportAnimal.female){
                    vm.preFemale = vm.exportAnimal.female;
                }
                vm.exportAnimal.male = 0;
                vm.exportAnimal.female = 0;
            }else{
                if(vm.preMale){
                    vm.exportAnimal.male = vm.preMale;
                }
                if(vm.preFemale){
                    vm.exportAnimal.female = vm.preFemale;
                }
            }

            if(vm.exportAnimal.animalReportDataType == settings.AnimalReportDataTypeENUM.animalParent || settings.AnimalReportDataTypeENUM.gilts == vm.exportAnimal.animalReportDataType){
                if(vm.exportAnimal.unGender){
                    vm.preUnGender = vm.exportAnimal.unGender;
                }
                vm.exportAnimal.unGender = 0;
            }else{
                if(vm.preUnGender){
                    vm.exportAnimal.unGender = vm.preUnGender;
                }
            }

            vm.updateQuantity();
        };
        vm.onFarmSelected = function(item){
            vm.exportAnimal.farm = vm.farm;
			vm.findBy();
			if(!$state.params.id){
				vm.exportAnimal.batchCode="";
				vm.exportAnimal.type=null;
				vm.animalSelected=null;
				vm.exportAnimal.animal=null;
				vm.exportAnimal.productTarget=null;
				vm.exportAnimal.quantity=null;
				vm.exportAnimal.amount=null;
			}
			
        }
        /*
         * Nguồn gốc động vật
         */
        vm.getOriginals = function () {
            originalService.getAllOriginalDto().then(function (data) {
                vm.originals = data;
            });
        }
       // vm.getOriginals();
        
        /* Loại hình sản xuất*/
         
        vm.getProductTargets = function () {
            productTargetService.getAllProductTarget().then(function (data) {
                vm.productTargets = data;
                console.log(vm.productTargets)
            });
        }
        vm.getProductTargets();
        /*
         * Loại động vật
         */
        vm.getListParentAnimal = function () {
            AnimalService.getListParent().then(function (data) {
                vm.parentAnimals = data;
                console.log(vm.parentAnimals)
            });
        }
       // vm.getListParentAnimal();
        /*
         * Sự kiện Chọn Loại động vật
         */
        vm.onSelectedAnimal = function (animal) {            
            if (animal != null && animal.id > 0) {
                AnimalService.getListByParentId(animal.id).then(function (data) {
                    vm.animals = data;
                    console.log(vm.animals)
                });
            }
        }
		/*
         * Sự kiện Chọn Loại động vật con để lấy ra list hướng sản phẩm
         */
		vm.onSelectedAnimalByParent=function(item){		
			 vm.exportAnimal.productTarget=null;
			if(item!=null && item.animalProductTargets!=null && item.animalProductTargets.length>0){
                    vm.productTargets= item.animalProductTargets;
            }else{
                    vm.productTargets= [];					
            }	
            vm.getInventoryReportRemainQuantity(); 				
		}
		
		//--------------Popup Batch Code----------//
        vm.pageIndexBatchCode=1, vm.pageSizeBatchCode=10;
        vm.subModalInstance = {};

        vm.selectedBatchCodes=[];
        vm.batchCodeSelected={};
        vm.showPopupBatchCode = function () {
            if(!vm.exportAnimal.farm){
                toastr.warning($translate.instant("exportAnimal.mustSelectFarm"), $translate.instant("toastr.warning"));
                return;
            }
            vm.subModalInstance = modal.open({
                animation: true,
                templateUrl: 'choose_batch_code_modal.html',
                scope: $scope,
                size: 'lg'
            });
            vm.code=null;
            vm.selectedBatchCodes=[];
			vm.batchCodeSelected={};
			vm.findBy();

            //getListTeacher(vm.pageIndexTeacher,vm.pageSizeTeacher);
            vm.subModalInstance.result.then(function (confirm) {
                if (confirm == 'yes') {
                   
                }
            }, function () {
				vm.batchCode=null;
                console.log("cancel");
            });
        }

        var closeModal = function () {
			
            vm.subModalInstance.close();

        };
        function checkAgree(){
            console.log("checkAgree");
            console.log(vm.selectedBatchCodes);
            if(angular.isUndefined(vm.selectedBatchCodes)|| vm.selectedBatchCodes.length==0 ){
                toastr.warning($translate.instant("exportAnimal.notChooseBatchCode"), $translate.instant("toastr.warning"));
                return false;
            }
            return true;
        }
        vm.agree=function () {
            if(checkAgree()){
                vm.batchCodeSelected=vm.selectedBatchCodes[0];
                vm.batchCode=vm.batchCodeSelected.batchCode;
				if(vm.exportAnimal==null){
					vm.exportAnimal={};
				}
				vm.exportAnimal.batchCode=vm.batchCode;
				vm.animalSelected=vm.batchCodeSelected.animal.parent;
				vm.exportAnimal.animal=vm.batchCodeSelected.animal;
				vm.exportAnimal.productTarget=vm.batchCodeSelected.productTarget;
                vm.farm=vm.batchCodeSelected.farm;
                // vm.exportAnimal.quantity=vm.batchCodeSelected.remainQuantity;
                //vm.exportAnimal.quantity=vm.batchCodeSelected;
				vm.exportAnimal.importAnimal=vm.batchCodeSelected;//phiếu nhập
				vm.dateImport=vm.batchCodeSelected.dateImport;
               // vm.searchTimeTableDetail();
			   vm.batchCode=null;
                closeModal();
            }
        }
        
        vm.enterSearchTeacher=function (e) {
            if(event.keyCode == 13){//Phím Enter
                vm.searchTeachers();
            }
        }
        vm.bsTableControlCode = {
            options: {
                data: vm.importAnimals,
                idField: 'id',
                sortable: true,
                striped: true,
                maintainSelected: true,
                clickToSelect: false,
                showColumns: false,
                singleSelect: true,
                showToggle: false,
                pagination: true,
                pageSize: vm.pageSizeBatchCode,
                pageList: [5, 10, 25, 50, 100],
                locale: settings.locale,
                sidePagination: 'server',
                columns: service.getTableDefinitionCode(),
                onCheck: function (row, $element) {
                    if( vm.selectedBatchCodes!=null){
                        vm.selectedBatchCodes=[];
                    }
                    $scope.$apply(function () {
                        vm.selectedBatchCodes.push(row);
                    });
                },
              
                onUncheck: function (row, $element) {
                    
                    if( vm.selectedBatchCodes!=null){
                        vm.selectedBatchCodes=[];
                    }
                },
                
                onPageChange: function (index, pageSize) {
                    vm.pageSizeBatchCode = pageSize;
                    vm.pageIndexBatchCode = index;
                    //vm.searchTeachers();
					vm.findBy();
                }
            }
        };
		
		
		
		vm.enterSearchCode = function(){
			// console.log(event.keyCode);
			if(event.keyCode == 13){//Phím Enter
				
				vm.searchByCode();
			}
        };
		 vm.searchByCode = function () {
            vm.pageIndexBatchCode = 1;
            vm.bsTableControlCode.state.pageNumber = 1;
            vm.findBy();
        };
		vm.findBy =function () {
            if( vm.searchDto==null){
                vm.searchDto={};
            }
            if(vm.batchCode!=null&& vm.batchCode!=""){
                vm.searchDto.nameOrCode=vm.batchCode;
            }
            else{
                vm.searchDto.nameOrCode=null;
            }
			if(vm.farm!=null && vm.farm.id!=null){
				vm.searchDto.farmId=vm.farm.id;
			}else{
				if(vm.exportAnimal!=null && vm.exportAnimal.farm!=null && vm.exportAnimal.farm.id!=null){
					vm.searchDto.farmId=vm.exportAnimal.farm.id;
				}else
				vm.searchDto.farmId=null;
			}
			vm.searchDto.type=1;
            console.log("vm.searchDto");
            
            console.log(vm.searchDto);
            service.getPageSearchImportAnimal(vm.searchDto,vm.pageIndexBatchCode,vm.pageSizeBatchCode).then(function(data) {
                vm.importAnimals = data.content;
                vm.bsTableControlCode.options.data = vm.importAnimals;
                vm.bsTableControlCode.options.totalRows = data.totalElements;
                console.log(vm.importAnimals);
                //Tự động chọn số lô nếu chỉ có 1 lô nhập
                //  if(vm.importAnimals!=null && vm.importAnimals.length == 1 && vm.importAnimals[0].remainQuantity>0){
                //      //vm.batchCode=vm.importAnimals[0].batchCode;
				// 	if(vm.exportAnimal==null){
				// 		vm.exportAnimal={};
				// 	}
				// 	vm.batchCodeSelected=vm.importAnimals[0];
				// 	vm.exportAnimal.batchCode=vm.importAnimals[0].batchCode;
				// 	vm.animalSelected=vm.importAnimals[0].animal.parent;
				// 	vm.exportAnimal.animal=vm.importAnimals[0].animal;
				// 	vm.exportAnimal.productTarget=vm.importAnimals[0].productTarget;
				// 	//vm.farm=vm.importAnimals[0].farm;
					
				// 	vm.exportAnimal.importAnimal=vm.importAnimals[0];//phiếu nhập
					
                //  }
            });
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
                vm.farmName = "";
                if(vm.farm.code){
                    vm.farmName += vm.farm.code+ " - ";
                }
                vm.farmName += vm.farm.name;
                // vm.farmName=vm.farm.name;
                vm.exportAnimal.farm = vm.farm;
                vm.getInventoryReportRemainQuantity();
				//vm.findBy();
				//if(!$state.params.id){
					vm.exportAnimal.batchCode="";
					vm.exportAnimal.type=null;
					vm.animalSelected=null;
					// vm.exportAnimal.animal=null;
					vm.exportAnimal.productTarget=null;
					//vm.exportAnimal.quantity=null;
					//vm.exportAnimal.amount=null;
				//}
				vm.farmCode=null;
                closeModal();
                vm.findBy();
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
            const searchDtoFarmTemp2 = angular.copy(vm.searchDtoFarm);
            searchDtoFarmTemp2.showListFarmSelect = true;
            service.getPageSearchFarm(searchDtoFarmTemp2,vm.pageIndexFarm,vm.pageSizeFarm).then(function(data) {
                vm.farms = data.content;
                vm.bsTableControlFarm.options.data = vm.farms;
                vm.bsTableControlFarm.options.totalRows = data.totalElements;
				console.log(vm.farms);
				if(vm.isFamer==true&& vm.farms!=null && vm.farms.length>0){
					if(vm.exportAnimal==null)vm.exportAnimal={};
					vm.farmName=vm.farms[0].name;
					vm.farmCode=vm.farms[0].code;
					vm.exportAnimal.farm = vm.farms[0];
					vm.findBy();
//					vm.exportAnimal.batchCode="";
//					vm.exportAnimal.type=null;
//					vm.animalSelected=null;
//					vm.exportAnimal.animal=null;
//					vm.exportAnimal.productTarget=null;
//					vm.exportAnimal.quantity=null;
//					vm.exportAnimal.amount=null;
				}
            });
        }
		
        //vm.findByFarm();
        FmsAdministrativeService.getAllCity().then(function(data){
            vm.provinces = data;
        });
		//chọn hướng sản phẩm cho phiếu xuất chuyển loại để tạo phiếu nhập
		vm.onProductTargetChangeSelected=function(item){
			if(vm.exportAnimal!=null && vm.exportAnimal.productTarget!=null && item.id==vm.exportAnimal.productTarget.id){
				vm.exportAnimal.productTargetChange=null;
				toastr.warning($translate.instant("exportAnimal.checkProductTargetChange"), $translate.instant("toastr.warning"));				
			}
        }
        vm.dropSelectImportAnimal = function(){
            vm.exportAnimal.importAnimal = null;
            vm.exportAnimal.animal = null;
        }

        vm.getInventoryReportRemainQuantity = function(){
            if(vm.exportAnimal != null && vm.exportAnimal.dateExport != null && vm.exportAnimal.animal && vm.exportAnimal.farm != null && typeof vm.exportAnimal.animalReportDataType == typeof 0){
                const reqBody = {};
                reqBody.animalId = vm.exportAnimal.animal.id;
                reqBody.farmId = vm.exportAnimal.farm.id;
                reqBody.toDate = vm.exportAnimal.dateExport;
                reqBody.animalReportDataType = vm.exportAnimal.animalReportDataType;
                service.getInventoryReportRemainQuantity(reqBody).then(function(data){
                    vm.inventory = data;
                }).catch(function(error){
                    vm.inventory = {};
                    vm.inventory.quantity = 0;
                    vm.inventory.amount = 0;
                    vm.inventory.male = 0;
                    vm.inventory.female = 0;
                    vm.inventory.unGender = 0;
                });
            }
        };

        vm.onchangeExportDate = function(){
            vm.getInventoryReportRemainQuantity();
        }
		
    }

})();