/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.AnimalType').controller('AnimalTypeController', AnimalTypeController);

    AnimalTypeController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'AnimalTypeService',
        '$uibModal',
        'toastr',
        '$state',
        'blockUI',
        '$stateParams',
        'Utilities',
		'$translate'
    ];
    
    function AnimalTypeController ($rootScope, $scope, $http, $timeout,settings,service,modal,toastr,$state,blockUI,$stateParams,utils,$translate) {
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
        vm.animalTypes = []
        vm.modalAnimalType={};
        vm.animalType = {isNew : false};
        vm.modalConfirmDelete = {};
        vm.selectedAnimalType = [];
        vm.count = 0;
		vm.searchDto={};
		vm.viewCheckDuplicateCode = {};
        vm.tempCode = '';
        vm.getAllAnimalType=function (){
            service.getAllAnimalType(vm.pageIndex,vm.pageSize).then(function(data){
                vm.animalTypes = data.content;
                vm.bsTableControl.options.data = vm.animalTypes;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }
        /** generate */
        vm.getAllAnimalType();


        /** bussiness */

        vm.bsTableControl = {
            options: {
                data: vm.animalTypes,
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
                        vm.selectedAnimalType.push(row);
                    });
                },
                onCheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedAnimalType = rows;
                    });
                },
                onUncheck: function (row, $element) {
                    var index = utils.indexOf(row, vm.selectedAnimalType);
                    if (index >= 0) {
                        $scope.$apply(function () {
                            vm.selectedAnimalType.splice(index, 1);
                        });
                        
                    }
                },
                onUncheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedAnimalType = [];
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
            service.getPageSearchAnimalType(vm.searchDto,vm.pageIndex,vm.pageSize).then(function(data) {
                vm.animalTypes = data.content;
                vm.bsTableControl.options.data = vm.animalTypes;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }
        

        vm.createAnimalType = function (){
            vm.animalType = {isNew : true};

            vm.modalAnimalType = modal.open({
                animation: true,
                templateUrl: 'animal_type_info.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });



        };

        $scope.editAnimalType=function (id){
            if(id){
                service.getAnimalTypeById(id).then(function(data){
                    vm.animalType = data;
                    vm.animalType.isNew =false;
					vm.tempCode = vm.animalType.code;
                    console.log(vm.animalType);
                });
                vm.modalAnimalType = modal.open({
                    animation: true,
                    templateUrl: 'animal_type_info.html',
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

        vm.deleteAnimals =function (){
            if(vm.selectedAnimalType.length == 0){
                //toastr.warning("Bạn chưa chọn!")
				toastr.warning($translate.instant("animalType.emptySelected"), $translate.instant("toastr.warning"));
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

        vm.saveAnimalType  =function (){
            if(vm.animalType){
                
                /*if(!vm.animalType.description){
                    toastr.warning("Bạn chưa nhập mô tả!");
                    return;
                }*/

                if(vm.animalType.isNew){
                    service.createAnimal(vm.animalType,function success(data){
						toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                        //vm.getAllAnimalType();
						vm.findBy();
                        vm.modalAnimalType.close();
                        vm.selectedAnimalType = [];
                    },function failure(error){
						toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    });
                }else {
					vm.animalType.code=vm.tempCode;
                    service.updateAnimal(vm.animalType.id,vm.animalType,function success(data){
						toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                        //vm.getAllAnimalType();
						vm.findBy();
                        vm.modalAnimalType.close();
                        vm.selectedAnimalType = [];
                    },function failure(error){
						toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    });
                }
            }
        }

        vm.confirmDelete =function (){
            vm.pageIndex = 1;
            var ids = vm.selectedAnimalType.map(function(element){
                return element.id;
            });
            service.deleteAnimals(ids,function success(data){
				//toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
                //vm.getAllAnimalType();
				vm.findBy();
                vm.modalConfirmDelete.close();
                vm.selectedAnimalType = [];
            },function failure(error){
				//toastr.error($translate.instant("toastr.delFail"), $translate.instant("toastr.error"));
                console.log(error);
            });
       }
       $scope.deleteAnimalTypeRecord = function(id){
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
            service.deleteAnimal(vm.recordId).then (function (data){
                if(data!=null && data.code=="-1"){
				toastr.warning($translate.instant("toastr.del4"), $translate.instant("toastr.warning"));
                }
				else{
                    toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
                }
                //vm.getAllAnimalType();
				vm.findBy();
                vm.modalConfirmDelete.close(); 
                vm.selectedAnimalType = [];
            });
			/*,function failure(error){
                toastr.error($translate.instant("toastr.delFail"), $translate.instant("toastr.error"));
                vm.modalConfirmDelete.close(); 
            });*/
           }
       }
	   function validate() {
		    console.log(vm.animalType);
            if(!vm.animalType.code){
				 toastr.warning($translate.instant("animalType.emptyCode"), $translate.instant("toastr.warning"));
				return false;
			}
			if(!vm.animalType.name){
				toastr.warning($translate.instant("animalType.emptyName"), $translate.instant("toastr.warning"));
				return false;
			}
			if(vm.animalType.isNew==false && (vm.tempCode==null || vm.tempCode=="")){
				toastr.warning($translate.instant("animalType.emptyCode"), $translate.instant("toastr.warning"));
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
                            
							vm.saveAnimalType();
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
                                    vm.animalType.code = vm.tempCode.trim();
                                    
									vm.saveAnimalType();
                                }
                            });
                        }else{
                            vm.animalType.code = vm.tempCode.trim();
                            
							vm.saveAnimalType();
                        }
                    }
                }
                console.log(data);

            });
        }
		vm.checkDuplicateCode = function (type,action) {
            if(validate()){
                checkDuplicateCode(vm.animalType.code,type,action);
            }
        }

    }

})();
