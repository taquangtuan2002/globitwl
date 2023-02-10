/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.ReportFarmerManagerment').controller('ReportEggController', ReportEggController);

    ReportEggController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'ReportEggService',
        '$uibModal',
        'toastr',
        '$state',
        'blockUI',
        '$stateParams',
        'Utilities',
		'$translate',
        'Upload',
        'FarmService',
        'ImportEggService',
        'AnimalService'
    ];
   
    
    function ReportEggController ($rootScope, $scope, $http, $timeout,settings,service,modal,toastr,$state,blockUI,$stateParams,utils,$translate,Upload,FarmService, importEggService, animalService) {
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
        vm.exportEggs = []
        vm.pageIndexImportEgg = 1;
        vm.pageSizeImportEgg = 25;
        //vm.exportEgg={};
        vm.modalFarm={}
        vm.farm = {isNew : false};
        vm.modalConfirmDelete = {};
        vm.selectedExportEggs = [];
        vm.count = 0;
        vm.listDistrictId=null;
        vm.importEggs = [];
        vm.selectedAnimal = null;
        vm.parentAnimals = [];
        /** generate */
        //vm.getPageImportAnimal();
		vm.isRole=false;
		vm.isFamer=false;
		vm.isSdah=false;
		vm.isDistrict=false;
		vm.isWard=false;
		vm.isSdahView = false;
        vm.findBy = findBy;
        vm.findImportEgg= findImportEgg;
        vm.bsTableControlImportEgg = {
            options: {
                data: [],
                idField: 'id',
                sortable: true,
                striped: true,
                maintainSelected: true,
                clickToSelect: false,
                showColumns: false,
                showToggle: false,
                pagination: true,
                pageSize: vm.pageSizeImportEgg,
                pageList: [5, 10, 25, 50, 100],
                locale: settings.locale,
                sidePagination: 'server',
                columns: service.getTableDefinitionImportEgg(),
                
                onPageChange: function (index, pageSize) {
                    vm.pageSizeImportEgg = pageSize;
                    vm.pageIndexImportEgg = index;

                    //vm.getPageImportAnimal();
					vm.findImportEgg();
                }
            }
        };
        vm.bsTableControl = {
            options: {
                data: vm.exportEggs,
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
                        vm.selectedExportEggs.push(row);
                    });
                },
                onCheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedExportEggs = rows;
                    });
                },
                onUncheck: function (row, $element) {
                    var index = utils.indexOf(row, vm.selectedExportEggs);
                    if (index >= 0) {
                        $scope.$apply(function () {
                            vm.selectedExportEggs.splice(index, 1);
                        });
                        
                    }
                },
                onUncheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedExportEggs = [];
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
        vm.bsTableControlAdmin = {
            options: {
                data: vm.exportEggNots,
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
					if(vm.exportEgg==null)
						vm.exportEgg={};
					
                    vm.exportEgg.farm = vm.listFarm[0];
                }
                console.log('vm.listFarm');
                console.log(vm.listFarm);
            });
        }
		
		//--------End ----Phân quyền-------------

        /** bussiness */

        
		
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
            vm.findImportEgg();
           
        };
		function findBy () {
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
            
            if(vm.selectedAnimal != null){
                vm.searchDto.animalId = vm.selectedAnimal.id;
            }else{
                vm.searchDto.animalId = null;
            }
            
            console.log(vm.searchDto);
           if(changeDate()){
                vm.searchDto.type = -1;
			   service.getPageSearchExportEgg(vm.searchDto,vm.pageIndex,vm.pageSize).then(function(data) {
					vm.exportEggs = data.content;
					vm.bsTableControl.options.data = vm.exportEggs;
					vm.bsTableControl.options.totalRows = data.totalElements;
				});
				service.getTotalEggReport(vm.searchDto).then(function(data) {
					vm.report = data;
                });
                vm.findImportEgg();
		   }
            
        }
		function changeDate(){
			 if(vm.fromDate!=null && vm.toDate!=null && vm.fromDate>vm.toDate){
				 toastr.warning($translate.instant("importAnimal.toDateBeforeFrom"), $translate.instant("toastr.warning"));
				return false;
			}
			return true;
		}

        $scope.editExportEgg = function(id){
           if(id){
           	
                service.getExportEgg(id).then(function(data){
                    vm.exportEgg = data;
                    vm.exportEgg.isNew =false;
					
                    console.log(vm.exportEgg);
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

        vm.createExportEgg = function (){
			getFarmByUserLogin();
	
            vm.exportEgg = {isNew : true};
          
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
			if(vm.exportEgg==null){
				 toastr.warning($translate.instant("exportEgg.emptyExport"), $translate.instant("toastr.warning"));
				return;
			}
			if(vm.exportEgg.farm==null){
				 toastr.warning($translate.instant("exportEgg.emptyFarm"), $translate.instant("toastr.warning"));
				return;
			}
			if(vm.exportEgg.dateExport==null){
				 toastr.warning($translate.instant("exportEgg.emptyDate"), $translate.instant("toastr.warning"));
				return;
			}
			
			if(vm.exportEgg.quantity==null){
				 toastr.warning($translate.instant("exportEgg.emptyQuantity"), $translate.instant("toastr.warning"));
				return;
			}
            if(vm.exportEgg){
                
                if(vm.exportEgg.id){
					
                    service.updateExportEgg(vm.exportEgg.id,vm.exportEgg,function success(data){
						 toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                        
						vm.findBy();
                        vm.modalAnimal.close();
                    },function failure(error){
						 toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    });
                }else {
                    service.createExportEgg(vm.exportEgg,function success(data){
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
       $scope.deleteExportEgg = function(id){
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
            service.deleteExportEgg(vm.recordId).then(function (data){
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

            if(vm.selectedAnimal != null){
                vm.searchDto.animalId = vm.selectedAnimal.id;
            }else{
                vm.searchDto.animalId = null;
            }
            
            console.log(vm.searchDto);
			if(changeDate()){
                vm.searchDto.type = -1;
			   service.getPageSearchExportEgg(vm.searchDto,vm.pageIndexNot,vm.pageSizeNot).then(function(data) {
					vm.exportEggNots = data.content;
					vm.bsTableControlAdmin.options.data = vm.exportEggNots;
					vm.bsTableControlAdmin.options.totalRows = data.totalElements;
                });
                vm.findImportEgg();
			}
            
        }

        function findImportEgg () {
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

            if(vm.selectedAnimal != null){
                vm.searchDto.animalId = vm.selectedAnimal.id;
            }else{
                vm.searchDto.animalId = null;
            }
            
            console.log(vm.searchDto);
			if(changeDate()){
                vm.searchDtoCoppy = angular.copy(vm.searchDto);
                vm.searchDtoCoppy.type = 1;
                service.getPageSearchExportEgg(vm.searchDtoCoppy, vm.pageIndexImportEgg, vm.pageSizeImportEgg).then(function(data) {
                    vm.importEggs = data.content;
                    vm.bsTableControlImportEgg.options.data = vm.importEggs;
                    vm.bsTableControlImportEgg.options.totalRows = data.totalElements;
                });
			}
            
        }

        animalService.getListParent().then(function (data) {
            vm.parentAnimals = data;
            // vm.parentAnimals = data.filter(animalParent=>{
            //     if(animalParent.animalProductTargets && animalParent.animalProductTargets.length > 0){
            //         // Nếu hướng sản phẩm có code = "EGG" thì lấy ra
            //         if(animalParent.animalProductTargets.map(el=> el.code).indexOf("EGG") > -1){
            //             return true;
            //         }
            //     }
            //     return false;
            // });
            console.log(JSON.stringify(vm.parentAnimals));
        });

        vm.onSelectAnimal = function(e){
            vm.searchByCode();
        }



        $scope.currentTab = 1;
        $scope.changeTab = function(index){
            $scope.currentTab = index;
        };
        $scope.isActiveTab = function(index){
            return index === $scope.currentTab;
        }
	   
    }

})();
