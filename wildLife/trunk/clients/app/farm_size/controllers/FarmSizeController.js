/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.FarmSize').controller('FarmSizeController', FarmSizeController);

    FarmSizeController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'FarmSizeService',
        '$uibModal',
        'toastr',
        '$state',
        'blockUI',
        '$stateParams',
        'Utilities',
		'$translate'
    ];
    
    function FarmSizeController ($rootScope, $scope, $http, $timeout,settings,service,modal,toastr,$state,blockUI,$stateParams,utils,$translate) {
        $scope.$on('$viewContentLoaded', function() {
            // initialize core components
            App.initAjax();
        });

        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        /** declare */
        var vm = this;
        // blockUI.stop();
        // blockUI.start();
        vm.recordId = null;
        vm.pageIndex = 1;
        vm.pageSize = 25;
        vm.farmSizes = []
        vm.modalFarmSize={};
        vm.farmSize = {isNew : false};
        vm.modalConfirmDelete = {};
        vm.selectedFarmSize = [];
        vm.count = 0;
		vm.searchDto={};
		vm.viewCheckDuplicateCode = {};
        vm.tempCode = '';
        vm.getAllFarmSize=function (){
            service.getAllFarmSize(vm.pageIndex,vm.pageSize).then(function(data){
                vm.farmSizes = data.content;
                vm.bsTableControl.options.data = vm.farmSizes;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }
        /** generate */
        vm.getAllFarmSize();


        /** bussiness */

        vm.bsTableControl = {
            options: {
                data: vm.farmSizes,
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
                        vm.selectedFarmSize.push(row);
                    });
                },
                onCheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedFarmSize = rows;
                    });
                },
                onUncheck: function (row, $element) {
                    var index = utils.indexOf(row, vm.selectedFarmSize);
                    if (index >= 0) {
                        $scope.$apply(function () {
                            vm.selectedFarmSize.splice(index, 1);
                        });
                        
                    }
                },
                onUncheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedFarmSize = [];
                    });
                },
                onPageChange: function (index, pageSize) {
                    vm.pageSize = pageSize;
                    vm.pageIndex = index;
					vm.findBy();
                    //vm.getAllAnimalType();
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

            
            console.log(vm.searchDto);
            service.getPageSearchFarmSize(vm.searchDto,vm.pageIndex,vm.pageSize).then(function(data) {
                vm.farmSizes = data.content;
                vm.bsTableControl.options.data = vm.farmSizes;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }
        

        vm.createFarmSize = function (){
            vm.farmSize = {isNew : true};

            vm.modalFarmSize = modal.open({
                animation: true,
                templateUrl: 'farm_size_info.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });



        };

        $scope.editFarmSize=function (id){
            if(id){
                service.getFarmSizeById(id).then(function(data){
                    vm.farmSize = data;
                    vm.farmSize.isNew =false;
					vm.tempCode = vm.farmSize.code;
                    console.log(vm.farmSize);
                });
                vm.modalFarmSize = modal.open({
                    animation: true,
                    templateUrl: 'farm_size_info.html',
                    scope: $scope,
                    backdrop: 'static',
                    keyboard: false,
                    size: 'md'
                });
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

        vm.deleteFarmSizes =function (){
            if(vm.selectedFarmSize.length == 0){
                //toastr.warning("Bạn chưa chọn!")
				toastr.warning($translate.instant("farmSize.emptySelected"), $translate.instant("toastr.warning"));
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

        vm.saveFarmSize  =function (){
            if(vm.farmSize){
                /*if(!vm.farmSize.code){
                     toastr.warning($translate.instant("farmSize.emptyCode"), $translate.instant("toastr.warning"));
                    return;
                }
                if(!vm.farmSize.name){
                    toastr.warning($translate.instant("farmSize.emptyName"), $translate.instant("toastr.warning"));
                    return;
                }
                if(!vm.animalType.description){
                    toastr.warning("Bạn chưa nhập mô tả!");
                    return;
                }*/

                if(vm.farmSize.isNew){
                    service.createFarmSize(vm.farmSize,function success(data){
						toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                        //vm.getAllFarmSize();
						vm.findBy();
                        vm.modalFarmSize.close();
                        vm.selectedFarmSize = [];
                    },function failure(error){
						toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    });
                }else {
					vm.farmSize.code=vm.tempCode;
                    service.updateFarmSize(vm.farmSize.id,vm.farmSize,function success(data){
						toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                        //vm.getAllFarmSize();
						vm.findBy();
                        vm.modalFarmSize.close();
                        vm.selectedFarmSize = [];
                    },function failure(error){
						toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    });
                }
            }
        }

        vm.confirmDelete =function (){
            vm.pageIndex = 1;
            var ids = vm.selectedFarmSize.map(function(element){
                return element.id;
            });
            service.deleteFarmSizes(ids,function success(data){
				//toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
                //vm.getAllFarmSize();
				vm.findBy();
                vm.modalConfirmDelete.close();
                vm.selectedFarmSize = [];
            },function failure(error){
				//toastr.error($translate.instant("toastr.delFail"), $translate.instant("toastr.error"));
                console.log(error);
            });
       }
       $scope.deleteFarmSizeRecord = function(id){
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
            service.deleteFarmSize(vm.recordId).then(function (data){
                if(data!=null && data.code=="-1"){
					toastr.warning($translate.instant("toastr.del4"), $translate.instant("toastr.warning"));
				}
				else{
					toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
				}
               // vm.getAllFarmSize();
			   vm.findBy();
                vm.modalConfirmDelete.close(); 
                vm.selectedFarmSize = [];
            });
			/*,function failure(error){
                toastr.error($translate.instant("toastr.delFail"), $translate.instant("toastr.error"));
                vm.modalConfirmDelete.close(); 
            });*/
           }
       }
	   
	   function validate() {
		    console.log(vm.farmSize);
            if(!vm.farmSize.code){
				 toastr.warning($translate.instant("farmSize.emptyCode"), $translate.instant("toastr.warning"));
				return false;
			}
			if(!vm.farmSize.name){
				toastr.warning($translate.instant("farmSize.emptyName"), $translate.instant("toastr.warning"));
				return false;
			}
			if(vm.farmSize.isNew==false && (vm.tempCode==null || vm.tempCode=="")){
				toastr.warning($translate.instant("farmSize.emptyCode"), $translate.instant("toastr.warning"));
				return false;
			}
			return true;
	   }
	   
	   function checkDuplicateCode(code,type,action){ //type: 1 -> save; 2 -> edit;   action: 1 -> just check code; 2 -> save or edit
            service.checkDuplicateCode(code).then(function(data) {
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
                            //newBuilding(vm.building);
							vm.saveFarmSize();
                        }
                    }
                    if(type == 2){
                        if(vm.tempCode.toLowerCase().trim() != code.toLowerCase().trim()){
                            service.checkDuplicateCode(vm.tempCode).then(function(data) {
                                vm.viewCheckDuplicateCode = data;
                                if(vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == true){
                                    //toastr.warning("Mã bị trùng");
									toastr.warning($translate.instant("checkCode.codeDuplicate"), $translate.instant("toastr.warning"));
                                }
                                if(vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == false){
                                    vm.farmSize.code = vm.tempCode.trim();
                                    //editBuilding(vm.building);
									vm.saveFarmSize();
                                }
                            });
                        }else{
                            vm.farmSize.code = vm.tempCode.trim();
                            //editBuilding(vm.building);
							vm.saveFarmSize();
                        }
                    }
                }
                console.log(data);

            });
        }
		vm.checkDuplicateCode = function (type,action) {
            if(validate()){
                checkDuplicateCode(vm.farmSize.code,type,action);
            }
        }

    }

})();
