/**
 * Created by bizic on 28/8/2016.
 */
(function() {
    'use strict';

    angular.module('Education.FmsRegion').controller('FmsRegionController', FmsRegionController);

    FmsRegionController.$inject = [
        '$rootScope',
        '$scope',
        '$timeout',
        'settings',
        '$uibModal',
        'toastr',
        'blockUI',
        'bsTableAPI',
        'Utilities',
        'FmsRegionService',
		'$translate'
    ];

    function FmsRegionController($rootScope, $scope, $timeout, settings, modal, toastr, blockUI, bsTableAPI, utils, service,$translate) {
        $scope.$on('$viewContentLoaded', function() {
            // initialize core components
            App.initAjax();
        });

        // set sidebar closed and body solid layout mode
        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        var vm = this;

        vm.user = {}; // Doi tượng
        vm.users = []; // Mảng
        vm.fmsRegion = {}; // mảng Region
        vm.fmsRegions = [];
        vm.id = null;

        vm.selectedFmsRegion = [];

        vm.roles = [];
        vm.groups = [];

        // UI
        vm.modalInstance = null;

        // pagination
        vm.pageIndex = 0;
        vm.pageSize = 10;

        vm.changePass = false;
		vm.viewCheckDuplicateCode = {};
        vm.tempCode = '';

        /**
         * Get a list of FmsRegion
         */
        vm.getFmsRegions = function() {
            service.getFmsRegions(vm.pageIndex, vm.pageSize).then(function(data) {
                vm.fmsRegions = data.content;
                vm.bsTableControl.options.data = vm.fmsRegions;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        };

        vm.getFmsRegions();

        /**
         * Get all roles
         */
        service.getAllRoles().then(function(data) {
            if (data && data.length > 0) {
                vm.roles = data;
            } else {
                vm.roles = [];
            }
        });

        /**
         * Get all user groups
         */
        service.getAllGroups().then(function(data) {
            if (data && data.length > 0) {
                vm.groups = data;
            } else {
                vm.groups = [];
            }
        });

        vm.saveFmsRegion = function() {
           

            /*if (!vm.fmsRegion.description) {
                toastr.error('Vui lòng nhập đầy đủ mô tả vùng!', 'Thông báo');
                return;
            }*/

            // if (!vm.fmsRegion.acreage) {
            //     toastr.error('Vui lòng nhập đầy đủ diện tích vùng!', 'Thông báo');
            //     return;
            // }
			if(vm.fmsRegion.isNew==false){
				vm.fmsRegion.code=vm.tempCode;
			}
            service.saveFmsRegion(vm.fmsRegion).then(function(data) {
               toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                // Reload fmsRegion
                //vm.getFmsRegions();
				vm.findBy();
                vm.modalInstance.close();
            }, function errorCallback(response) {
                toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
            });

            /*service.deleteFmsRegion(id).then(function(data) {
                toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
                // Reload fmsRegion
                vm.getFmsRegions();
                vm.modalInstance.close();
            }, function errorCallback(response) {
                toastr.error($translate.instant("toastr.delFail"), $translate.instant("toastr.error"));
            });*/

        };

        /**
         * Create a new user
         */
        vm.newFmsRegion = function() {
            vm.fmsRegion = { isNew: true };

            vm.modalInstance = modal.open({
                animation: true,
                templateUrl: 'edit_modal.html',
                backdrop: 'static',
                scope: $scope,
                size: 'lg'
            });
        };

        /**
         * Edit an existing user
         *
         * @param userId
         */
        // $scope.editUser = function(userId) {

        //     service.getUser(userId).then(function(data) {
        //         if (data && data.id) {

        //             vm.user = data;
        //             vm.user.isNew = false;

        //             vm.modalInstance = modal.open({
        //                 animation: true,
        //                 templateUrl: 'edit_modal.html',
        //                 scope: $scope,
        //                 size: 'lg'
        //             });
        //         }
        //     });
        //     vm.changePass = false;
        // };

        /**
         * Get Firstname & Lastname from fullname
         */
        $scope.$watch('vm.user.person.displayName', function(newVal, oldVal) {

            if (!newVal) {
                return;
            }

            var fullname = String(newVal).trim();
            if (fullname.length <= 0) {
                return;
            }

            var spaceIndex = fullname.indexOf(' ');

            if (spaceIndex > 0) {
                vm.user.person.firstName = fullname.substr(0, spaceIndex);
                vm.user.person.lastName = fullname.substr(spaceIndex + 1);
            }
        });


        // search user by username
        vm.textSearch = '';

        function findUserByUserName(username, pageIndex, pageSize) {
            service.findUserByUserName(username, pageIndex, pageSize).then(function(data) {
                vm.fmsRegion = data.content;
                vm.bsTableControl.options.data = vm.fmsRegion;
                vm.bsTableControl.options.totalRows = data.totalElements;
                console.log(data);
            });
        }

        vm.searchByCode = function() {
            vm.textSearch = String(vm.textSearch).trim();
            if (vm.textSearch != '') {
                findUserByUserName(vm.textSearch, vm.pageIndex, vm.pageSize);
            }
            if (vm.textSearch == '') {
                vm.getFmsRegions();
            }
        }

        vm.bsTableControl = {
            options: {
                data: vm.fmsRegion,
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
                onCheck: function(row, $element) {
                    $scope.$apply(function() {
                        if (row.username && row.username != 'admin') {
                            vm.selectedFmsRegion.push(row);
                        } else {
                            bsTableAPI('bsTableControl', 'uncheckBy', { field: 'username', values: ['admin'] });
                        }
                        // vm.selectedFmsRegion.push(row);
                    });
                },
                onCheckAll: function(rows) {
                    $scope.$apply(function() {
                        angular.forEach(rows, function(row) {
                            if (row.username && row.username != 'admin') {
                                vm.selectedFmsRegion.push(row);
                            } else {
                                bsTableAPI('bsTableControl', 'uncheckBy', { field: 'username', values: ['admin'] });
                            }
                        });
                        // vm.selectedFmsRegion = rows;
                    });
                },
                onUncheck: function(row, $element) {
                    var index = utils.indexOf(row, vm.selectedFmsRegion);
                    if (index >= 0) {
                        $scope.$apply(function() {
                            vm.selectedFmsRegion.splice(index, 1);
                        });
                    }
                },
                onUncheckAll: function(rows) {
                    $scope.$apply(function() {
                        vm.selectedFmsRegion = [];
                    });
                },
                onPageChange: function(index, pageSize) {
                    vm.pageSize = pageSize;
                    vm.pageIndex = index;

                    //vm.getFmsRegions();
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
            service.getPageSearchFmsRegion(vm.searchDto,vm.pageIndex,vm.pageSize).then(function(data) {
                vm.fmsRegion = data.content;
                vm.bsTableControl.options.data = vm.fmsRegion;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }
		
        $scope.editFmsRegion = function(id) {
            service.getRegionById(id).then(function(data) {
                if (data && data.id) {
                    vm.fmsRegion = data;
                    vm.fmsRegion.isNew = false;
					vm.tempCode = vm.fmsRegion.code;
                    vm.modalInstance = modal.open({
                        animation: true,
                        templateUrl: 'edit_modal.html',
                        backdrop: 'static',
                        scope: $scope,
                        size: 'lg'
                    });
                }
            });
        }

        $scope.deleteFmsRegion = function(id) {
            vm.id = id;
            vm.modalInstance = modal.open({
                animation: true,
                templateUrl: 'delete_modal.html',
                backdrop: 'static',
                scope: $scope,
                size: 'md'
            });


        }
        vm.confirmDelete = function() {			
            // alert(vm.id)
            service.deleteRegionById(vm.id).then(function(data) {
				 vm.fmsRegion = data;
				 if(vm.fmsRegion!=null && vm.fmsRegion.code=="-1"){
                    toastr.warning($translate.instant("toastr.del4"), $translate.instant("toastr.warning"));
                }
				else{
                    toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
                }

                vm.fmsRegion.isNew = false;
                //vm.getFmsRegions();
				vm.findBy();
                vm.modalInstance.close();
            });
        }
		
		function validate() {
		    console.log(vm.fmsRegion);
            if (!vm.fmsRegion.code) {
                toastr.warning($translate.instant("region.emptyCode"), $translate.instant("toastr.warning"));
                return false;
            }
			
            if (!vm.fmsRegion.name) {
                toastr.warning($translate.instant("region.emptyName"), $translate.instant("toastr.warning"));
                return false;
            }
			if(vm.fmsRegion.isNew==false && (vm.tempCode==null || vm.tempCode=="")){
				toastr.warning($translate.instant("region.emptyCode"), $translate.instant("toastr.warning"));
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
                            
							vm.saveFmsRegion();
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
                                    vm.fmsRegion.code = vm.tempCode.trim();
                                    
									vm.saveFmsRegion();
                                }
                            });
                        }else{
                            vm.fmsRegion.code = vm.tempCode.trim();
                            
							vm.saveFmsRegion();
                        }
                    }
                }
                console.log(data);

            });
        }
		vm.checkDuplicateCode = function (type,action) {
            if(validate()){
                checkDuplicateCode(vm.fmsRegion.code,type,action);
            }
        }

    }

})();