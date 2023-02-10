/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.Drug').controller('DrugController', DrugController);

    DrugController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'DrugService',
        '$uibModal',
        'toastr',
        '$state',
        'blockUI',
        '$stateParams',
        'Utilities',
		'$translate'
    ];
    
    function DrugController ($rootScope, $scope, $http, $timeout,settings,service,modal,toastr,$state,blockUI,$stateParams,utils,$translate) {
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
        vm.drugs = []
        vm.modalDrug={};
        vm.drug = {isNew : false};
        vm.modalConfirmDelete = {};
        vm.selectedDrug = [];
        vm.count = 0;
		vm.searchDto={};
		vm.viewCheckDuplicateCode = {};
        vm.tempCode = '';
        vm.getAllDrug=function (){
            service.getAllDrug(vm.pageIndex,vm.pageSize).then(function(data){
                vm.drugs = data.content;
                vm.bsTableControl.options.data = vm.drugs;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }
        /** generate */
        vm.getAllDrug();


        /** bussiness */

        vm.bsTableControl = {
            options: {
                data: vm.drugs,
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
                        vm.selectedDrug.push(row);
                    });
                },
                onCheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedDrug = rows;
                    });
                },
                onUncheck: function (row, $element) {
                    var index = utils.indexOf(row, vm.selectedDrug);
                    if (index >= 0) {
                        $scope.$apply(function () {
                            vm.selectedDrug.splice(index, 1);
                        });
                        
                    }
                },
                onUncheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedDrug = [];
                    });
                },
                onPageChange: function (index, pageSize) {
                    vm.pageSize = pageSize;
                    vm.pageIndex = index;
					vm.findBy();
                    //vm.getAllDrug();
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
            service.getPageSearchDrug(vm.searchDto,vm.pageIndex,vm.pageSize).then(function(data) {
                vm.drugs = data.content;
                vm.bsTableControl.options.data = vm.drugs;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }
        

        vm.createDrug = function (){
            vm.drug = {isNew : true};

            vm.modalDrug = modal.open({
                animation: true,
                templateUrl: 'drug_info.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });



        };

        $scope.editDrug=function (id){
            if(id){
                service.getDrugById(id).then(function(data){
                    vm.drug = data;
                    vm.drug.isNew =false;
					vm.tempCode = vm.drug.code;
                    console.log(vm.drug);
                });
                vm.modalDrug = modal.open({
                    animation: true,
                    templateUrl: 'drug_info.html',
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

        vm.deleteDrugs =function (){
            if(vm.selectedDrug.length == 0){
                //toastr.warning("Bạn chưa chọn!")
				toastr.warning($translate.instant("drug.emptySelected"), $translate.instant("toastr.warning"));
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

        vm.saveDrug  =function (){
            if(vm.drug){
                
                /*if(!vm.Drug.description){
                    toastr.warning("Bạn chưa nhập mô tả!");
                    return;
                }*/

                if(vm.drug.isNew){
                    service.createDrug(vm.drug,function success(data){
						toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                        //vm.getAllDrug();
						vm.findBy();
                        vm.modalDrug.close();
                        vm.selectedDrug = [];
                    },function failure(error){
						toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    });
                }else {
					vm.drug.code=vm.tempCode;
                    service.updateDrug(vm.drug.id,vm.drug,function success(data){
						toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                        //vm.getAllDrug();
						vm.findBy();
                        vm.modalDrug.close();
                        vm.selectedDrug = [];
                    },function failure(error){
						toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    });
                }
            }
        }

        vm.confirmDelete =function (){
            vm.pageIndex = 1;
            var ids = vm.selectedDrug.map(function(element){
                return element.id;
            });
            service.deleteDrugs(ids,function success(data){
				//toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
                //vm.getAllDrug();
				vm.findBy();
                vm.modalConfirmDelete.close();
                vm.selectedDrug = [];
            },function failure(error){
				//toastr.error($translate.instant("toastr.delFail"), $translate.instant("toastr.error"));
                console.log(error);
            });
       }
       $scope.deleteDrugRecord = function(id){
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
            service.deleteDrug(vm.recordId).then (function (data){
                if(data!=null && data.code=="-1"){
				toastr.warning($translate.instant("toastr.del4"), $translate.instant("toastr.warning"));
                }
				else{
                    toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
                }
                //vm.getAllDrug();
				vm.findBy();
                vm.modalConfirmDelete.close(); 
                vm.selectedDrug = [];
            });
			/*,function failure(error){
                toastr.error($translate.instant("toastr.delFail"), $translate.instant("toastr.error"));
                vm.modalConfirmDelete.close(); 
            });*/
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
                            
							vm.saveDrug();
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

    }

})();
