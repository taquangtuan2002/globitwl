/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.InjectionPlant').controller('InjectionPlantController', InjectionPlantController);

    InjectionPlantController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'InjectionPlantService',
        '$uibModal',
        'toastr',
        '$state',
        'blockUI',
        '$stateParams',
        'Utilities',
		'$translate'
    ];
    
    function InjectionPlantController ($rootScope, $scope, $http, $timeout,settings,service,modal,toastr,$state,blockUI,$stateParams,utils,$translate) {
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
        vm.injectionPlant = []
        vm.modalInjectionPlant={};
        vm.injectionPlant = {isNew : false};
        vm.modalConfirmDelete = {};
        vm.selectedInjectionPlant = [];
        vm.count = 0;
		vm.searchDto={};
		vm.viewCheckDuplicateCode = {};
        vm.tempCode = '';

        vm.getPage = function (){
            service.getPage(vm.pageIndex,vm.pageSize).then(function(data){
                vm.injectionPlant = data.content;
                vm.bsTableControl.options.data = vm.injectionPlant;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }
        /** generate */
        vm.getPage();


        /** bussiness */

        vm.bsTableControl = {
            options: {
                data: vm.injectionPlant,
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
                        vm.selectedInjectionPlant.push(row);
                    });
                },
                onCheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedInjectionPlant = rows;
                    });
                },
                onUncheck: function (row, $element) {
                    var index = utils.indexOf(row, vm.selectedInjectionPlant);
                    if (index >= 0) {
                        $scope.$apply(function () {
                            vm.selectedInjectionPlant.splice(index, 1);
                        });
                        
                    }
                },
                onUncheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedInjectionPlant = [];
                    });
                },
                onPageChange: function (index, pageSize) {
                    vm.pageSize = pageSize;
                    vm.pageIndex = index;
					// vm.findBy();
                    vm.getPage();
                }
            }
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
            // service.getPageSearchInjectionPlant(vm.searchDto,vm.pageIndex,vm.pageSize).then(function(data) {
            //     vm.injectionPlant = data.content;
            //     vm.bsTableControl.options.data = vm.injectionPlant;
            //     vm.bsTableControl.options.totalRows = data.totalElements;
            // });
        }
        

        vm.createInjectionPlant = function (){
            vm.injectionPlant = {isNew : true};

            vm.modalInjectionPlant = modal.open({
                animation: true,
                templateUrl: 'injectionPlant_info.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });



        };

        $scope.editInjectionPlant=function (id){
            service.getInjectionPlantById(id).then(function(data){
                vm.injectionPlant = data;// load lại data
                vm.injectionPlant.isNew =false;

                vm.modalInjectionPlant = modal.open({
                    animation: true,
                    templateUrl: 'injectionPlant_info.html',
                    scope: $scope,
                    backdrop: 'static',
                    keyboard: false,
                    size: 'md'
                });
                vm.CheckResult= function () {//hàm kiểm tra xem đã nhập các thông tin chưa
                    if(vm.injectionPlant.injectionDate == '' || vm.injectionPlant.injectionDate == null){
                        toastr.warning($translate.instant("injectionPlant.emtyDate"), $translate.instant("toastr.warning"));
                        return;
                    }
                    if(vm.injectionPlant.drug ==null || vm.injectionPlant.drug == '' ){
                        toastr.warning($translate.instant("injectionPlant.emtyDrug"), $translate.instant("toastr.warning"));
                        return;
                    }
                    if(vm.injectionPlant.injectionRound ==null || vm.injectionPlant.injectionRound == ''){
                        toastr.warning($translate.instant("injectionPlant.emtyInjectionRound"), $translate.instant("toastr.warning"));
                        return;
                    }
                    service.saveInjectionPlant(vm.injectionPlant, function success() {
                        // Refresh list
                        
                        // vm.getPage();

                        // Notify
                        toastr.info($translate.instant("toastr.editSuccess"), $translate.instant("toastr.info"));
                        // clear object
                        vm.injectionPlant = {};
                        modalInjectionPlant.close();
                    }, function failure() {
                        toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                    });
                    vm.getPage();
                }
            }, function () {
                vm.injectionPlant = {};
                console.log('Modal dismissed at: ' + new Date());
            });
        
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

        vm.deleteInjectionPlants =function (){ 
            if(vm.selectedInjectionPlant.length == 0){
                toastr.warning("Bạn chưa chọn!")
				c
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
           
            modalConfirmDelete.close();
            vm.getPage();
           
        }

        vm.saveInjectionPlant  =function (){
            if(vm.injectionPlant){
                
                /*if(!vm.injectionPlant.description){
                    toastr.warning("Bạn chưa nhập mô tả!");
                    return;
                }*/

                if(vm.injectionPlant.isNew){
                    service.createInjectionPlant(vm.injectionPlant,function success(data){
						toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                    
						vm.findBy();
                        vm.modalInjectionPlant.close();
                        vm.selectedInjectionPlant = [];
                    },function failure(error){
						toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    });
                }else {
					vm.injectionPlant.injectionDate=vm.tempCode;
                    service.updateInjectionPlant(vm.injectionPlant.id,vm.injectionPlant,function success(data){
						toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                        //vm.getPage();
						vm.findBy();
                        vm.modalInjectionPlant.close();
                        vm.selectedInjectionPlant = [];
                    },function failure(error){
						toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    });
                }
            }
        }

        vm.confirmDelete =function (){
            vm.pageIndex = 1;
            var ids = vm.selectedInjectionPlant.map(function(element){
                return element.id;
            });
            service.deleteInjectionPlants(ids,function success(data){
			
                //notification
                toastr.info($translate.instant("toastr.del1"), $translate.instant("toastr.info"));
                //close modal
                vm.modalConfirmDelete.close();
                //refresh
                vm.getPage();
                vm.selectedInjectionPlant = [];
            },function failure(error){
                toastr.error($translate.instant("toastr.delFail"), $translate.instant("toastr.error"));
                
            });
       }


       $scope.deleteInjectionPlantRecord = function(id){
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
            service.deleteInjectionPlant(vm.recordId,function success(data){
                toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
                vm.getPage();
				vm.findBy();
                vm.modalConfirmDelete.close(); 
                vm.selectedInjectionPlant = [];
            },function failure(error){
                toastr.error($translate.instant("toastr.delFail"), $translate.instant("toastr.error"));
                vm.modalConfirmDelete.close(); 
            });
           }
       }
	

    }

})();
