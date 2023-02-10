/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.ExportDrug').controller('ExportDrugController', ExportDrugController);

    ExportDrugController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'ExportDrugService',
        '$uibModal',
        'toastr',
        '$state',
        'blockUI',
        '$stateParams',
        'Utilities',
		'$translate',
        'Upload',
        'FarmService',
        'DrugService',
        'UnitService',
        'ImportAnimalService'
    ];
   
    angular.module('Education.ExportDrug').directive('numberInput', function($filter) {
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
    function ExportDrugController ($rootScope, $scope, $http, $timeout,settings,service,modal,toastr,$state,blockUI,$stateParams,utils,$translate,Upload,FarmService,drugService,unitService, importAnimalService ) {
        $scope.$on('$viewContentLoaded', function() {
            // initialize core components
            App.initAjax();
        });

        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        /** declare */
        var vm = this;
		vm.baseUrl = settings.api.baseUrl + settings.api.apiPrefix;
        vm.recordId = null;
        vm.pageIndex = 1;
        vm.pageSize = 25;
		vm.pageIndexNot = 1;
        vm.pageSizeNot = 25;
        vm.exportDrugs = []
        vm.modalFarm={}
        vm.farm = {isNew : false};
        vm.modalConfirmDelete = {};
        vm.selectedimportDrugs = [];
        vm.count = 0;
        vm.listDistrictId=null;
        vm.units = [];
        vm.selectedBatchCodes = [];
        vm.pageIndexBatchCode = 1;
        vm.pageSizeBatchCode = 25;
        vm.pageIndexAnimal = 1;
        vm.pageSizeAnimal = 25;
        /** generate */
        //vm.getPageImportAnimal();
		vm.isRole=false;
		vm.isFamer=false;
		vm.isSdah=false;
		vm.isDistrict=false;
		vm.isWard=false;
		vm.isSdahView = false;
		vm.getCombo=function(){
			drugService.getAllDrugDto().then(function (data) {
                vm.drugs = data;
            });
		}
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
					if (roles[i]!=null &&  roles[i].authority && roles[i].authority.toLowerCase() == 'role_sdah' ) {
                        vm.isSdah = true;						
                    }
					if (roles[i]!=null &&  roles[i].authority && roles[i].authority.toLowerCase() == 'role_district' ) {
                        vm.isDistrict = true;						
                    }
					if (roles[i]!=null &&  roles[i].authority && roles[i].authority.toLowerCase() == 'role_ward' ) {
                        vm.isWard = true;						
                    }
					if (roles[i]!=null &&  roles[i].authority && roles[i].authority.toLowerCase() == 'role_famer' ) {
                        vm.isFamer = true;						
                    }
					if (roles[i]!=null &&  roles[i].authority && roles[i].authority.toLowerCase() == 'role_sdah_view' ){
                        vm.isSdahView = true;
						vm.isSdah = true;							
                    }
                }
            }else{
                vm.isRole=false;
				vm.isFamer=false;
				vm.isSdah=false;
				vm.isDistrict=false;
				vm.isWard=false;
				vm.isSdahView = false;
            }
			if(vm.isRole == false && (vm.isFamer==true) &&  vm.user!==null && vm.user.username!==null && vm.user.username!==''){//trường hợp tài khoản nông dân
               
				vm.textSearch=vm.user.username;
				vm.findBy();

            }else if(vm.isRole == true){
				vm.findByAdmin();
			}else{
				getAdministrativeUnitDtoByLoginUser();
			}
            vm.getCombo();
			
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
			if( vm.searchDto==null){
                vm.searchDto={};
            }
			if(administrativeUnit.parentDto==null){
				vm.searchDto.province=administrativeUnit.id;
				
			}else if(administrativeUnit.parentDto!=null && administrativeUnit.parentDto.parentDto == null){
				vm.searchDto.district=administrativeUnit.id;
				
			}else if(administrativeUnit.parentDto!=null && administrativeUnit.parentDto.parentDto != null){
				vm.searchDto.ward=administrativeUnit.id;				
               
			}
			
			vm.findByAdmin();
            
        }
		//get farm by userLogin
        function getFarmByUserLogin(){
            if (vm.isFamer) {
                service.getFarmByUserLogin().then(function(data) {
                    vm.listFarm = data;
                    if(vm.listFarm != null && vm.listFarm.length == 1){
                        if(vm.exportDrug==null)
                            vm.exportDrug={};
                        
                        vm.exportDrug.farm = vm.listFarm[0];
                    }
                    console.log('vm.listFarm');
                    console.log(vm.listFarm);
                });
            }
        }
		
		//--------End ----Phân quyền-------------

        /** bussiness */

        vm.bsTableControl = {
            options: {
                data: vm.exportDrugs,
                idField: 'id',
                sortable: true,
                striped: true,
                maintainSelected: true,
                clickToSelect: false,
                showColumns: false,
                showToggle: false,
                pagination: true,
                pageSize: vm.pageSize,
                pageList: [5, 10, 25, 50, 100],
                locale: settings.locale,
                sidePagination: 'server',
                columns: service.getTableDefinition(),
                onCheck: function (row, $element) {
                    $scope.$apply(function () {
                        vm.selectedimportDrugs.push(row);
                    });
                },
                onCheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedimportDrugs = rows;
                    });
                },
                onUncheck: function (row, $element) {
                    var index = utils.indexOf(row, vm.selectedimportDrugs);
                    if (index >= 0) {
                        $scope.$apply(function () {
                            vm.selectedimportDrugs.splice(index, 1);
                        });
                        
                    }
                },
                onUncheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedimportDrugs = [];
                    });
                },
                onPageChange: function (index, pageSize) {
                    vm.pageSize = pageSize;
                    vm.pageIndex = index;

                    //vm.getPageImportAnimal();
					vm.findBy();
                }
            }
        };
		
		vm.enterSearch = function(){
            // console.log(event.keyCode);
            if(event.keyCode == 13){//Phím Enter
                
                vm.searchByCode();
            }
        };
		 vm.searchByCode = function () {
           if(vm.isFamer==true){
                    vm.pageIndex = 1;
                    if (vm.bsTableControl != null && vm.bsTableControl.state != null) {
                        vm.bsTableControl.state.pageNumber = 1;
                    }
					vm.findBy();
			 }else{
					vm.pageIndexNot = 1;
                    if (vm.bsTableControlAdmin != null && vm.bsTableControlAdmin.state != null) {
                        vm.bsTableControlAdmin.state.pageNumber = 1;
                    }
					vm.findByAdmin();
			 }
        };
        vm.getUnits = function(){
            unitService.getAllUnitDto().then(function(data){
                vm.units = data;
            });
        }
		vm.findBy=function () {
            if( vm.searchDto==null){
                vm.searchDto={};
            }
            if(vm.textSearch!=null&& vm.textSearch!=""){
                vm.searchDto.nameOrCode=vm.textSearch;
            }

            else{
                vm.searchDto.nameOrCode=null;
            }          
            
			if(vm.fromDate!=null){
				vm.searchDto.fromDate=vm.fromDate;
			}else{
				vm.searchDto.fromDate=null;
			}
			if(vm.toDate!=null){
				vm.searchDto.toDate=vm.toDate;
			}else{
				vm.searchDto.toDate=null;
			}
            
            console.log(vm.searchDto);
           if(changeDate()){
                vm.searchDto.type = -1;
			   service.getPageSearchImportDrug(vm.searchDto,vm.pageIndex,vm.pageSize).then(function(data) {
					vm.exportDrugs = data.content;
					vm.bsTableControl.options.data = vm.exportDrugs;
					vm.bsTableControl.options.totalRows = data.totalElements;
				});
		   }
            
        }
		function changeDate(){
			 if(vm.fromDate!=null && vm.toDate!=null && vm.fromDate>vm.toDate){
				 toastr.warning($translate.instant("importAnimal.toDateBeforeFrom"), $translate.instant("toastr.warning"));
				return false;
			}
			return true;
		}

        $scope.editImportDrug = function(id){
           if(id){
                vm.getUnits();
                service.getImportDrug(id).then(function(data){
                    vm.exportDrug = data;
                    vm.exportDrug.isNew =false;
					
                    console.log(vm.exportDrug);
                });
                vm.modalAnimal = modal.open({
                    animation: true,
                    templateUrl: 'detail.html',
                    scope: $scope,
                    backdrop: 'static',
                    keyboard: false,
                    size: 'md'
                });
            }
        }

        vm.createImportDrug = function (){
            getFarmByUserLogin();
            vm.getUnits();
			//let date = new Date();
			//vm.exportDrug.dateImport = {year: date.getFullYear(), month: date.getMonth(), day: date.getDate()};
			//vm.exportDrug.dateImport =new Date();
            vm.exportDrug = {isNew : true};
            vm.exportDrug.dateImport = new Date();
          
            vm.modalAnimal = modal.open({
                animation: true,
                templateUrl: 'detail.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        };
		
		vm.save  =function (){
			if(vm.exportDrug==null){
				  toastr.warning($translate.instant("importDrug.emptyExport"), $translate.instant("toastr.warning"));
				return;
			}
			if(vm.exportDrug.farm==null){
				 toastr.warning($translate.instant("importDrug.emptyFarm"), $translate.instant("toastr.warning"));
				return;
			}
			if(vm.exportDrug.dateImport==null){
				 toastr.warning($translate.instant("importDrug.emptyDate"), $translate.instant("toastr.warning"));
				return;
			}
			if(vm.exportDrug.dateImport!=null && vm.exportDrug.dateImport>new Date()){
                toastr.warning($translate.instant("exportDrug.warningDate"), $translate.instant("toastr.warning"));
                return ;
            }
			// if(vm.exportDrug.drug==null){
			// 	 toastr.warning($translate.instant("importDrug.emptyDrug"), $translate.instant("toastr.warning"));
			// 	return;
			// }
			// if(vm.exportDrug.quantity==null){
			// 	 toastr.warning($translate.instant("importDrug.emptyQuantity"), $translate.instant("toastr.warning"));;
			// 	return;
            // }
            if(vm.exportDrug.quantity==null || vm.exportDrug.quantity<=0){
                toastr.warning($translate.instant("importDrug.emptyQuantity"), $translate.instant("toastr.warning"));;
               return;
           }
            if(!vm.exportDrug.importExportAnimal){
                toastr.warning($translate.instant("exportDrug.emptyImportExportAnimal"), $translate.instant("toastr.warning"));;
               return;
           }
           if(vm.exportDrug.importExportAnimal.dateImport && vm.exportDrug.dateImport && moment(vm.exportDrug.importExportAnimal.dateImport).startOf('day').toDate().getTime() > moment(vm.exportDrug.dateImport).endOf('day').toDate().getTime() ){
            toastr.warning($translate.instant("exportDrug.importAnimalDateValid"), $translate.instant("toastr.warning"));;
            return;
           }
			
            if(vm.exportDrug){
                
                vm.exportDrug.type = -1;
                if(vm.exportDrug.id){
                    service.updateImportDrug(vm.exportDrug.id,vm.exportDrug,function success(data){
						 
                    },function failure(error){
						 toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    }).then(function(data){
                        if(data.code == "-6"){// Ngày xuất nhỏ hơn hạn sử dụng
                            toastr.warning($translate.instant("exportDrug.manufactureLtExpiry"), $translate.instant("toastr.warning"));
                        }else if(data.code == "-3"){// Số lượng xuất không được lớn hơn số lượng còn lại
                            toastr.warning($translate.instant("exportDrug.exportQuantityNotLT"), $translate.instant("toastr.warning"));
                        }else if(data.code == "-2"){// Ngày xuất không được nhỏ hơn ngày nhập
                            toastr.warning($translate.instant("exportDrug.dateExportNotLT"), $translate.instant("toastr.warning"));
                        }else{
                            toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                            if (vm.isFamer == true) {
                                vm.pageIndex = 1;
                                if (vm.bsTableControl != null && vm.bsTableControl.state != null) {
                                    vm.bsTableControl.state.pageNumber = 1;
                                }
                                vm.findBy();
                            } else {
                                vm.pageIndexNot = 1;
                                if (vm.bsTableControlAdmin != null && vm.bsTableControlAdmin.state != null) {
                                    vm.bsTableControlAdmin.state.pageNumber = 1;
                                }
                                vm.findByAdmin();
                            }
                            vm.modalAnimal.close();
                        }
                    });
                }else {
                    service.createImportDrug(vm.exportDrug,function success(data){
						
                    },function failure(error){
						 toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    }).then(function(data){
                        if(data.code == "-6"){// Ngày xuất nhỏ hơn hạn sử dụng
                            toastr.warning($translate.instant("exportDrug.manufactureLtExpiry"), $translate.instant("toastr.warning"));
                        }else if(data.code == "-3"){// Số lượng xuất không được lớn hơn số lượng còn lại
                            toastr.warning($translate.instant("exportDrug.exportQuantityNotLT"), $translate.instant("toastr.warning"));
                        }else if(data.code == "-2"){// Ngày xuất không được nhỏ hơn ngày nhập
                            toastr.warning($translate.instant("exportDrug.dateExportNotLT"), $translate.instant("toastr.warning"));
                        }else{
                            toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                            if (vm.isFamer == true) {
                                vm.pageIndex = 1;
                                if (vm.bsTableControl != null && vm.bsTableControl.state != null) {
                                    vm.bsTableControl.state.pageNumber = 1;
                                }
                                vm.findBy();
                            } else {
                                vm.pageIndexNot = 1;
                                if (vm.bsTableControlAdmin != null && vm.bsTableControlAdmin.state != null) {
                                    vm.bsTableControlAdmin.state.pageNumber = 1;
                                }
                                vm.findByAdmin();
                            }
                            vm.modalAnimal.close();
                        }
                    });
                }
            }
        }

        $scope.deleteOriginal =function deleteOriginal(id){
            vm.modalConfirmDelete = modal.open({
                animation: true,
                templateUrl: 'confirm_delete.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        }

        vm.deletefarms =function (){
            if(vm.selectedFarms.length == 0){
                toastr.warning("Bạn chưa chọn!")
                return;
            }

            vm.modalConfirmDelete = modal.open({
                animation: true,
                templateUrl: 'confirm_delete.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });

           
        }

        vm.confirmDelete =function (){
            vm.pageIndex = 1;
            var ids = vm.selectedFarms.map(function(element){
                return element.id;
            });
            service.deleteServiceByIds(ids,function success(data){
               // vm.getPageImportAnimal();
			   vm.findBy();
                vm.modalConfirmDelete.close();
                vm.selectedFarms = [];
            },function failure(error){
                console.log(error);
            });
       }
       $scope.deleteImportDrug = function(id){
        if(id){
            vm.recordId = id;
        }else {
            vm.recordId = null;
        }
        vm.modalConfirmDelete = modal.open({
            animation: true,
            templateUrl: 'confirm_delete_record.html',
            scope: $scope,
            backdrop: 'static',
            keyboard: false,
            size: 'md'
        });
       }
       vm.confirmDeleteRecord = function(){
           if(vm.recordId){
            service.deleteImportDrug(vm.recordId).then(function (data){
                if(data!=null && data.code=="-1"){
					toastr.warning($translate.instant("toastr.del4"), $translate.instant("toastr.warning"));
				}else if(data!=null && data.code=="-2"){
					toastr.warning($translate.instant("importAnimal.deleteImport"), $translate.instant("toastr.warning"));
				}
				else{
					toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
                }
                
                if (vm.isFamer == true) {
                    vm.pageIndex = 1;
                    if (vm.bsTableControl != null && vm.bsTableControl.state != null) {
                        vm.bsTableControl.state.pageNumber = 1;
                    }
                    vm.findBy();
                } else {
                    vm.pageIndexNot = 1;
                    if (vm.bsTableControlAdmin != null && vm.bsTableControlAdmin.state != null) {
                        vm.bsTableControlAdmin.state.pageNumber = 1;
                    }
                    vm.findByAdmin();
                }

                vm.modalConfirmDelete.close(); 
                vm.selectedFarms = [];
            });
			/*,function failure(error){
                toastr.error($translate.instant("toastr.delFail"), $translate.instant("toastr.error"));
                vm.modalConfirmDelete.close(); 
            });*/
           }
       }
	   
	   vm.findByAdmin=function () {
            if( vm.searchDto==null){
                vm.searchDto={};
            }
            if(vm.textSearch!=null&& vm.textSearch!=""){
                vm.searchDto.nameOrCode=vm.textSearch;
            }

            else{
                vm.searchDto.nameOrCode=null;
            }          
            
			if(vm.fromDate!=null){
				vm.searchDto.fromDate=vm.fromDate;
			}else{
				vm.searchDto.fromDate=null;
			}
			if(vm.toDate!=null){
				vm.searchDto.toDate=vm.toDate;
			}else{
				vm.searchDto.toDate=null;
            }
            
            console.log(vm.searchDto);
			if(changeDate()){
                vm.searchDto.type = -1;
                service.getPageSearchImportDrug(vm.searchDto,vm.pageIndexNot,vm.pageSizeNot).then(function(data) {
                    vm.exportDrugNots = data.content;
                    vm.bsTableControlAdmin.options.data = vm.exportDrugNots;
                    vm.bsTableControlAdmin.options.totalRows = data.totalElements;
                });
			}
            
        }
		vm.bsTableControlAdmin = {
            options: {
                data: vm.exportDrugNots,
                idField: 'id',
                sortable: true,
                striped: true,
                maintainSelected: true,
                clickToSelect: false,
                showColumns: false,
                showToggle: false,
                pagination: true,
                pageSize: vm.pageSizeNot,
                pageList: [5, 10, 25, 50, 100],
                locale: settings.locale,
                sidePagination: 'server',
                columns: service.getTableDefinitionAdmin(),
                
                onPageChange: function (index, pageSize) {
                    vm.pageSizeNot = pageSize;
                    vm.pageIndexNot = index;

                    //vm.getPageImportAnimal();
					vm.findByAdmin();
                }
            }
        };
		//thêm thuốc
		vm.createDrug=function(){
			 vm.drug = {isNew : true};
          
            vm.modalDrug = modal.open({
                animation: true,
                templateUrl: 'drug_detail.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
		}
		
		vm.saveDrug  =function (){
            if(vm.drug){
                
                /*if(!vm.Drug.description){
                    toastr.warning("Bạn chưa nhập mô tả!");
                    return;
                }*/

                if(vm.drug.isNew){
                    drugService.createDrug(vm.drug,function success(data){
						toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                        
                        vm.modalDrug.close();
                        vm.getCombo();
                    },function failure(error){
						toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    });
                }else {
					vm.drug.code=vm.tempCode;
                    drugService.updateDrug(vm.drug.id,vm.drug,function success(data){
						toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                        
                        vm.modalDrug.close();
                       vm.getCombo();
                    },function failure(error){
						toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    });
                }
            }
        }
		function validate() {
		    console.log(vm.drug);
            if(!vm.drug.code){
				 toastr.warning($translate.instant("drug.emptyCode"), $translate.instant("toastr.warning"));
				return false;
			}
			if(!vm.drug.name){
				toastr.warning($translate.instant("drug.emptyName"), $translate.instant("toastr.warning"));
				return false;
			}
			if(vm.drug.isNew==false && (vm.tempCode==null || vm.tempCode=="")){
				toastr.warning($translate.instant("drug.emptyCode"), $translate.instant("toastr.warning"));
				return false;
			}
			return true;
	   }
	   
	   function checkDuplicateCode(code,type,action){ //type: 1 -> save; 2 -> edit;   action: 1 -> just check code; 2 -> save or edit
            drugService.checkDuplicateCode(code).then(function(data) {
                vm.viewCheckDuplicateCode = data;
                if(action == 1){
                    if(type == 1){
                        if(vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == true){
                           // toastr.warning("Mã bị trùng");
							toastr.warning($translate.instant("checkCode.codeDuplicate"), $translate.instant("toastr.warning"));
                        }
                        if(vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == false){
                            //toastr.success("Mã không bị trùng");
							toastr.success($translate.instant("checkCode.codeDuplicateNot"), $translate.instant("toastr.info"));
                        }
                    }
                    if(type == 2){
                        if(vm.tempCode.toLowerCase().trim() != code.toLowerCase().trim()){
                            checkDuplicateCode(vm.tempCode,1,1);
                        }else{
                           // toastr.info("Mã chưa thay đổi");
							toastr.info($translate.instant("checkCode.codeNotChange"), $translate.instant("toastr.info"));
                        }
                    }
                }
                if(action == 2){
                    if(type == 1){
                        if(vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == true){
                            //toastr.warning("Mã bị trùng");
							toastr.warning($translate.instant("checkCode.codeDuplicate"), $translate.instant("toastr.warning"));
                        }
                        if(vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == false){
                            
							vm.saveDrug();
                        }
                    }
                    if(type == 2){
                        if(vm.tempCode.toLowerCase().trim() != code.toLowerCase().trim()){
                            drugService.checkDuplicateCode(vm.tempCode).then(function(data) {
                                vm.viewCheckDuplicateCode = data;
                                if(vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == true){
                                    //toastr.warning("Mã bị trùng");
									toastr.warning($translate.instant("checkCode.codeDuplicate"), $translate.instant("toastr.warning"));
                                }
                                if(vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == false){
                                    vm.drug.code = vm.tempCode.trim();
                                    
									vm.saveDrug();
                                }
                            });
                        }else{
                            vm.drug.code = vm.tempCode.trim();
                            
							vm.saveDrug();
                        }
                    }
                }
                console.log(data);

            });
        }
		vm.checkDuplicateCode = function (type,action) {
            if(validate()){
                checkDuplicateCode(vm.drug.code,type,action);
            }
        }

        

        //--------------Popup Farm----------//

        //load user administrativeunit
        function getAdministrativeUnitDtoByLoginUserOnSearchFarm() {
            service.getAdministrativeUnitDtoByLoginUser().then(function (data) {
                vm.adminstrativeUnits = data;
                if (vm.adminstrativeUnits != null && vm.adminstrativeUnits.length > 0) {
                    getDataByComboboxOnSearchFarm(vm.adminstrativeUnits[0]);

                }
            });
        }
        function getDataByComboboxOnSearchFarm(administrativeUnit) {
            if (vm.searchDtoFarm == null) {
                vm.searchDtoFarm = {};
            }
            if (administrativeUnit.parentDto == null) {
                vm.searchDtoFarm.province = administrativeUnit.id;

            } else if (administrativeUnit.parentDto != null && administrativeUnit.parentDto.parentDto == null) {
                vm.searchDtoFarm.district = administrativeUnit.id;
                vm.searchDtoFarm.province = administrativeUnit.parentDto.id;

            } else if (administrativeUnit.parentDto != null && administrativeUnit.parentDto.parentDto != null) {
                vm.searchDtoFarm.ward = administrativeUnit.id;
                vm.searchDtoFarm.district = administrativeUnit.parentDto.id;
                vm.searchDtoFarm.province = administrativeUnit.parentDto.parentDto.id;

            }
            if (vm.isRole == false && vm.isFamer == true && vm.user !== null && vm.user.username !== null && vm.user.username !== '') {//trường hợp tài khoản nông dân
                //gán tên cơ sở chăn nuôi
                vm.farmCode = vm.user.username;
                vm.searchDtoFarm.nameOrCode = vm.user.username;
            }

            //vm.findBy();
            vm.findByFarm();
        }

        vm.pageIndexFarm = 1, vm.pageSizeFarm = 10;
        vm.subModalInstance = {};

        vm.selectedFarms = [];
        vm.farmSelected = {};
        vm.showPopupFarm = function () {
            vm.subModalInstance = modal.open({
                animation: true,
                templateUrl: 'choose_farm_modal.html',
                scope: $scope,
                size: 'lg'
            });
            vm.code = null;
            vm.selectedFarms = [];
            vm.farmSelected = {};
            //lấy danh sách cơ sở chăn nuôi phân theo quyền
            if (vm.isRole) {//trường hợp admin và dlp thì được xem tất cả các cơ sở chăn nuôi
                vm.findByFarm();
            } else {// trường hợp này thì phân quyền cơ sở chăn nuôi theo user
                if (vm.isRole == false) {
                    getAdministrativeUnitDtoByLoginUserOnSearchFarm();
                }
            }

            vm.subModalInstance.result.then(function (confirm) {
                if (confirm == 'yes') {

                }
            }, function () {
                vm.farmCode = null;
                console.log("cancel");
            });
        }

        var closeModal = function () {

            vm.subModalInstance.close();

        };
        function checkAgreeFarm() {
            console.log("checkAgree");
            console.log(vm.selectedFarms);
            if (angular.isUndefined(vm.selectedFarms) || vm.selectedFarms.length == 0) {
                toastr.warning($translate.instant("exportAnimal.notChooseFarm"), $translate.instant("toastr.warning"));
                return false;
            }
            return true;
        }
        vm.agreeFarm = function () {
            if (checkAgreeFarm()) {
                vm.farmSelected = vm.selectedFarms[0];
                vm.farm = vm.selectedFarms[0];
                vm.farmName = vm.farm.name;
                vm.exportDrug.farm = vm.farm;

                //vm.findBy();

                vm.farmCode = null;
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
                    if (vm.selectedFarms != null) {
                        vm.selectedFarms = [];
                    }
                    $scope.$apply(function () {
                        vm.selectedFarms.push(row);
                    });
                },

                onUncheck: function (row, $element) {

                    if (vm.selectedFarms != null) {
                        vm.selectedFarms = [];
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



        vm.enterSearchFarm = function () {
            // console.log(event.keyCode);
            if (event.keyCode == 13) {//Phím Enter

                vm.searchByFarm();
            }
        };
        vm.searchByFarm = function () {
            vm.pageIndexFarm = 1;
            vm.bsTableControlFarm.state.pageNumber = 1;
            vm.findByFarm();
        };
        vm.findByFarm = function () {
            if (vm.searchDtoFarm == null) {
                vm.searchDtoFarm = {};
            }
            if (vm.farmCode != null && vm.farmCode != "") {
                vm.searchDtoFarm.nameOrCode = vm.farmCode;
            }
            else {
                vm.searchDtoFarm.nameOrCode = null;
            }

            console.log("vm.searchDtoFarm");
            console.log(vm.searchDtoFarm);
            service.getPageSearchFarm(vm.searchDtoFarm, vm.pageIndexFarm, vm.pageSizeFarm).then(function (data) {
                vm.farms = data.content;
                vm.bsTableControlFarm.options.data = vm.farms;
                vm.bsTableControlFarm.options.totalRows = data.totalElements;
                console.log("vm.farms");
                console.log(vm.farms);
                if (vm.isFamer == true && vm.farms != null && vm.farms.length > 0) {
                    if (vm.exportDrug == null) vm.exportDrug = {};
                    vm.farmName = vm.farms[0].name;
                    vm.farmCode = vm.farms[0].code;
                    vm.exportDrug.farm = vm.farms[0];
                }
                //console.log(vm.farms);
            });
        }

        //--------------END Popup Farm----------//

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
                    vm.findByBatchCode();
                }
            }
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
                if(vm.exportDrug==null){
                    vm.exportDrug={};
                }
                vm.exportDrug.batchCode=vm.batchCode;
                vm.farm=vm.batchCodeSelected.farm;
                // vm.exportDrug.quantity=vm.batchCodeSelected.remainQuantity;
                //vm.exportDrug.quantity=vm.batchCodeSelected;
                vm.exportDrug.importDrug=vm.batchCodeSelected;//phiếu nhập
               // vm.searchTimeTableDetail();
               vm.batchCode=null;
               console.log(vm.exportDrug.importDrug)
               vm.batchCodeModalInstance.close();
            }
        }
        vm.showPopupBatchCode = function () {
            if(vm.exportDrug && vm.exportDrug.farm && vm.exportDrug.farm.id){
                
            }else{
                toastr.warning($translate.instant("exportEgg.farmNot"), $translate.instant("toastr.warning"));
                return;
            }
            
            vm.batchCodeModalInstance = modal.open({
                animation: true,
                templateUrl: 'choose_batch_code_modal.html',
                scope: $scope,
                size: 'lg'
            });
            vm.code=null;
            vm.selectedBatchCodes=[];
            vm.batchCodeSelected={};
            vm.findByBatchCode();
    
            vm.batchCodeModalInstance.result.then(function (confirm) {
                if (confirm == 'yes') {
                   
                }
            }, function () {
                vm.batchCode=null;
                console.log("cancel");
            });
        }
        vm.findByBatchCode = function(){
            vm.searchBatchCode = {};
            vm.searchBatchCode.nameOrCode = vm.batchCode;
            vm.searchBatchCode.type = 1;
            if(vm.exportDrug && vm.exportDrug.farm && vm.exportDrug.farm.id){
                vm.searchBatchCode.farmId = vm.exportDrug.farm.id;
            }else{
                toastr.warning($translate.instant("exportDrug.farmNot"), $translate.instant("toastr.warning"));
            }
            service.getPageSearchImportDrug(vm.searchBatchCode, vm.pageIndexBatchCode,vm.pageSizeBatchCode).then(function(data) {
                vm.importDrugParents = data.content;
                vm.bsTableControlCode.options.data = vm.importDrugParents;
                vm.bsTableControlCode.options.totalRows = data.totalElements;
                if(vm.exportDrug==null){
                    vm.exportDrug={};
                }
                 if(vm.importDrugParents!=null && vm.importDrugParents.length == 1 && vm.importDrugParents[0].remainQuantity>0){
                    vm.batchCodeSelected=vm.importDrugParents[0];
                    vm.exportDrug.importAnimal=vm.importDrugParents[0];//phiếu nhập
                    
                 }
            });
        }

        vm.enterSearchCode = function(e){
            if(e.keyCode == 13){
                vm.findByBatchCode();
            }
        }

        vm.bsTableControlImportExportAnimal = {
            options: {
                data: [],
                idField: 'id',
                sortable: true,
                striped: true,
                maintainSelected: true,
                clickToSelect: false,
                showColumns: false,
                singleSelect: true,
                showToggle: false,
                pagination: true,
                pageSize: vm.pageSizeAnimal,
                pageList: [5, 10, 25, 50, 100],
                locale: settings.locale,
                sidePagination: 'server',
                columns: service.getTableImportExportAnimal(),
                onCheck: function (row, $element) {
                    if (vm.selectedIEAnimals != null) {
                        vm.selectedIEAnimals = [];
                    }
                    $scope.$apply(function () {
                        vm.selectedIEAnimals.push(row);
                    });
                },

                onUncheck: function (row, $element) {

                    if (vm.selectedIEAnimals != null) {
                        vm.selectedIEAnimals = [];
                    }
                },

                onPageChange: function (index, pageSize) {
                    vm.pageSizeAnimal = pageSize;
                    vm.pageIndexAnimal = index;
                    //vm.searchTeachers();
                    vm.searchByImportExportAnimal();
                }
            }
        };
        vm.searchByImportExportAnimal = function(){
            if (!vm.searchDtoAnimal) {
                vm.searchDtoAnimal = {};
            }
            if (vm.importExportAnimalCode != null && vm.importExportAnimalCode != "") {
                vm.searchDtoAnimal.nameOrCode = vm.importExportAnimalCode;
            }
            else {
                vm.searchDtoAnimal.nameOrCode = null;
            }


            console.log("vm.searchDtoFarm");
            console.log(vm.searchDtoFarm);
            vm.searchDtoAnimal.farmId = vm.exportDrug.farm.id;
            vm.searchDtoAnimal.type = 1;
            vm.searchDtoAnimal.remainQuantityDown = 0;
            importAnimalService.getPageSearchImportAnimalSeed(vm.searchDtoAnimal, vm.pageIndexAnimal, vm.pageSizeAnimal).then(function (data) {
                // vm.farms = data.content;
                vm.bsTableControlImportExportAnimal.options.data = data.content;
                vm.bsTableControlImportExportAnimal.options.totalRows = data.totalElements;
            });
        }

        vm.agreeImportExportAnimal = function(){
            if(vm.selectedIEAnimals.length == 0){
                toastr.warning($translate.instant("exportEgg.emptyImportEcportAnimal"), $translate.instant("toastr.warning"));
                return;
            }else{
                vm.exportDrug.importExportAnimal = vm.selectedIEAnimals[0];
                vm.modalImportExportAnimal.close();
            }
        }
        vm.enterSearchAnimal = function(event){
            if(event.keyCode == 13){
                vm.searchByImportExportAnimal();
            }
        }

        vm.showPopupAnimal = function(){
            if(!vm.exportDrug || !vm.exportDrug.farm || !vm.exportDrug.farm.id ){
                toastr.warning($translate.instant("exportEgg.emptyFarm"), $translate.instant("toastr.warning"));
                return;
            }else{
                vm.selectedIEAnimals = [];
                vm.searchByImportExportAnimal();
                vm.modalImportExportAnimal = modal.open({
                    animation: true,
                    templateUrl: 'choose_importExporAnimal_modal.html',
                    scope: $scope,
                    backdrop: 'static',
                    keyboard: false,
                    size: 'md'
                });
            }
        }
	   
    }

})();
