/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.ReportFarmerManagerment').controller('ReportDrugController', ReportDrugController);

    ReportDrugController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'ReportDrugService',
        '$uibModal',
        'toastr',
        '$state',
        'blockUI',
        '$stateParams',
        'Utilities',
		'$translate',
        'Upload',
        'FarmService',
        'DrugService'
    ];
   
   
    function ReportDrugController ($rootScope, $scope, $http, $timeout,settings,service,modal,toastr,$state,blockUI,$stateParams,utils,$translate,Upload,FarmService,drugService) {
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
        vm.pageIndexExport = 1;
        vm.pageSizeExport = 25;
        vm.importDrugs = []
        vm.modalFarm={}
        vm.farm = {isNew : false};
        vm.modalConfirmDelete = {};
        vm.selectedimportDrugs = [];
        vm.count = 0;
        vm.listDistrictId=null;
        vm.reportDrug = {};

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
			if(vm.isRole == false && vm.isFamer==true &&  vm.user!==null && vm.user.username!==null && vm.user.username!==''){//trường hợp tài khoản nông dân
               
				vm.textSearch=vm.user.username;
				vm.getCombo();
				vm.findBy();

            }else if(vm.isRole == true){
				vm.findBy();
			}else{
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
			
			vm.findBy();
            
        }
		//get farm by userLogin
        function getFarmByUserLogin(){
            service.getFarmByUserLogin().then(function(data) {
                vm.listFarm = data;
                if(vm.listFarm != null && vm.listFarm.length == 1){
					if(vm.importDrug==null)
						vm.importDrug={};
					
                    vm.importDrug.farm = vm.listFarm[0];
                }
                console.log('vm.listFarm');
                console.log(vm.listFarm);
            });
        }
		
		//--------End ----Phân quyền-------------

        /** bussiness */

        vm.bsTableControl = {
            options: {
                data: vm.importDrugs,
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
        vm.bsTableControlExport = {
            options: {
                data: vm.importDrugs,
                idField: 'id',
                sortable: true,
                striped: true,
                maintainSelected: true,
                clickToSelect: false,
                showColumns: false,
                showToggle: false,
                pagination: true,
                pageSize: vm.pageSizeExport,
                pageList: [5, 10, 25, 50, 100],
                locale: settings.locale,
                sidePagination: 'server',
                columns: service.getTableDefinitionExport(),
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
                    vm.pageSizeExport = pageSize;
                    vm.pageIndexExport = index;

                    //vm.getPageImportAnimal();
					vm.findByExport();
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
           vm.pageIndex = 1;
			vm.bsTableControl.state.pageNumber = 1;
			vm.findBy();
        };
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
               vm.searchDto.type = 1;
			   service.getPageSearchImportDrug(vm.searchDto,vm.pageIndex,vm.pageSize).then(function(data) {
					vm.importDrugs = data.content;
					vm.bsTableControl.options.data = vm.importDrugs;
					vm.bsTableControl.options.totalRows = data.totalElements;
				});
				service.reportInventory(vm.searchDto).then(function(data){
                    vm.reportDrug = data;
                });
				vm.findByExport();
		   }
		   
            
        }
		vm.findByExport=function () {
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
                vm.searchDtoCopy = angular.copy(vm.searchDto);
                vm.searchDtoCopy.type = -1
			   service.getPageSearchImportDrug(vm.searchDtoCopy,vm.pageIndexExport,vm.pageSizeExport).then(function(data) {
					// vm.importDrugs = data.content;
					vm.bsTableControlExport.options.data = data.content;
					vm.bsTableControlExport.options.totalRows = data.totalElements;
				});
				
		   }
		   
            
        }
        vm.findByExport();
		
		function changeDate(){
			 if(vm.fromDate!=null && vm.toDate!=null && vm.fromDate>vm.toDate){
				 toastr.warning($translate.instant("importAnimal.toDateBeforeFrom"), $translate.instant("toastr.warning"));
				return false;
			}
			return true;
		}

        $scope.editImportDrug = function(id){
           if(id){
           	
                service.getImportDrug(id).then(function(data){
                    vm.importDrug = data;
                    vm.importDrug.isNew =false;
					
                    console.log(vm.importDrug);
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
			//let date = new Date();
			//vm.ImportDrug.dateImport = {year: date.getFullYear(), month: date.getMonth(), day: date.getDate()};
			//vm.ImportDrug.dateImport =new Date();
            vm.importDrug = {isNew : true};
          
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
			if(vm.importDrug==null){
				  toastr.warning($translate.instant("importDrug.emptyExport"), $translate.instant("toastr.warning"));
				return;
			}
			if(vm.importDrug.farm==null){
				 toastr.warning($translate.instant("importDrug.emptyFarm"), $translate.instant("toastr.warning"));
				return;
			}
			if(vm.importDrug.dateImport==null){
				 toastr.warning($translate.instant("importDrug.emptyDate"), $translate.instant("toastr.warning"));
				return;
			}
			if(vm.importDrug.drug==null){
				 toastr.warning($translate.instant("importDrug.emptyDrug"), $translate.instant("toastr.warning"));
				return;
			}
			if(vm.importDrug.quantity==null){
				 toastr.warning($translate.instant("importDrug.emptyQuantity"), $translate.instant("toastr.warning"));;
				return;
			}
			
            if(vm.importDrug){
                
                if(vm.importDrug.id){
					
                    service.updateImportDrug(vm.importDrug.id,vm.importDrug,function success(data){
						 toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                        
						vm.findBy();
                        vm.modalAnimal.close();
                    },function failure(error){
						 toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    });
                }else {
                    service.createImportDrug(vm.importDrug,function success(data){
						toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                       
						vm.findBy();
                        vm.modalAnimal.close();
                    },function failure(error){
						 toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
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
				vm.findBy();
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
                vm.searchDto.type = 1;
			   service.getPageSearchImportDrug(vm.searchDto,vm.pageIndexNot,vm.pageSizeNot).then(function(data) {
					vm.importDrugNots = data.content;
					vm.bsTableControlAdmin.options.data = vm.importDrugNots;
					vm.bsTableControlAdmin.options.totalRows = data.totalElements;
				});
			}
            
        }
		vm.bsTableControlAdmin = {
            options: {
                data: vm.importDrugNots,
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

        $scope.currentTab = 1;
        $scope.changeTab = function(index){
            $scope.currentTab = index;
        };
        $scope.isActiveTab = function(index){
            return index === $scope.currentTab;
        }
	   
    }

})();
