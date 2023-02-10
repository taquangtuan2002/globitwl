/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.InjectionTime').controller('InjectionTimeController', InjectionTimeController);

    InjectionTimeController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'InjectionTimeService',
        '$uibModal',
        'toastr',
        '$state',
        'blockUI',
        '$stateParams',
        'Utilities',
        '$translate'
    ];

    function InjectionTimeController($rootScope, $scope, $http, $timeout, settings, service, modal, toastr, $state, blockUI, $stateParams, utils, $translate) {
        $scope.$on('$viewContentLoaded', function () {
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
        vm.injectionTimes = []
        vm.modalInjectionTime = {};
        vm.injectionTime = { isNew: false };
        vm.modalConfirmDelete = {};
        vm.selectedInjectionTime = [];
        vm.count = 0;
        vm.searchDto = {};
        vm.viewCheckDuplicateCode = {};
        vm.tempCode = '';
        vm.getAllInjectionTime = function () {
            service.getAllInjectionTime(vm.pageIndex, vm.pageSize).then(function (data) {
                vm.injectionTimes = data.content;
                vm.bsTableControl.options.data = vm.injectionTimes;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }
        /** generate */
        vm.getAllInjectionTime();


        /** bussiness */

        vm.bsTableControl = {
            options: {
                data: vm.injectionTimes,
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
                        vm.selectedInjectionTime.push(row);
                    });
                },
                onCheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedInjectionTime = rows;
                    });
                },
                onUncheck: function (row, $element) {
                    var index = utils.indexOf(row, vm.selectedInjectionTime);
                    if (index >= 0) {
                        $scope.$apply(function () {
                            vm.selectedInjectionTime.splice(index, 1);
                        });

                    }
                },
                onUncheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedInjectionTime = [];
                    });
                },
                onPageChange: function (index, pageSize) {
                    vm.pageSize = pageSize;
                    vm.pageIndex = index;
                    vm.findBy();
                    //vm.getAllInjectionTime();
                }
            }
        };
        vm.type = [
            {
                value: 0,
                name: 'Tiêm vắc xin phòng bệnh'
            },
            {
                value: 1,
                name: 'Tiêm kháng sinh chữa bệnh'
            },
            {
                value: 2,
                name: 'Khác'
            }
        ];

        vm.enterSearch = function () {
            // console.log(event.keyCode);
            if (event.keyCode == 13) {//Phím Enter

                vm.searchByCode();
            }
        };
        vm.searchByCode = function () {
            vm.pageIndex = 1;
            vm.bsTableControl.state.pageNumber = 1;
            vm.findBy();
        };
        vm.findBy = function () {
            if (vm.searchDto == null) {
                vm.searchDto = {};
            }
            if (vm.textSearch != null && vm.textSearch != "") {
                vm.searchDto.nameOrCode = vm.textSearch;
            }
            else {
                vm.searchDto.nameOrCode = null;
            }


            console.log(vm.searchDto);
            service.getPageSearchInjectionTime(vm.searchDto, vm.pageIndex, vm.pageSize).then(function (data) {
                vm.injectionTimes = data.content;
                vm.bsTableControl.options.data = vm.injectionTimes;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }


        vm.createInjectionTime = function () {
            vm.injectionTime = { isNew: true };

            vm.modalInjectionTime = modal.open({
                animation: true,
                templateUrl: 'injectionTime_info.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });



        };

        $scope.editInjectionTime = function (id) {
            if (id) {
                service.getInjectionTimeById(id).then(function (data) {
                    vm.injectionTime = data;
                    vm.injectionTime.isNew = false;
                    vm.tempCode = vm.injectionTime.code;
                    console.log(vm.injectionTime);
                });
                vm.modalInjectionTime = modal.open({
                    animation: true,
                    templateUrl: 'injectionTime_info.html',
                    scope: $scope,
                    backdrop: 'static',
                    keyboard: false,
                    size: 'md'
                });
            }
        }

        $scope.deleteOriginal = function deleteOriginal(id) {
            vm.modalConfirmDelete = modal.open({
                animation: true,
                templateUrl: 'confirm_delete.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        }

        vm.deleteInjectionTimes = function () {
            if (vm.selectedInjectionTime.length == 0) {
                //toastr.warning("Bạn chưa chọn!")
                toastr.warning($translate.instant("injectionTime.emptySelected"), $translate.instant("toastr.warning"));
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

        vm.saveInjectionTime = function () {
            if (vm.injectionTime) {

                /*if(!vm.injectionTime.description){
                    toastr.warning("Bạn chưa nhập mô tả!");
                    return;
                }*/
                console.log(1);
                if (vm.injectionTime.isNew) {
                    console.log(2);
                    service.createInjectionTime(vm.injectionTime, function success(data) {
                        toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                        //vm.getAllInjectionTime();
                        vm.findBy();
                        vm.modalInjectionTime.close();
                        vm.selectedInjectionTime = [];
                    }, function failure(error) {
                        toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    });
                } else {
                    vm.injectionTime.code = vm.tempCode;
                    service.updateInjectionTime(vm.injectionTime.id, vm.injectionTime, function success(data) {
                        toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                        //vm.getAllInjectionTime();
                        vm.findBy();
                        vm.modalInjectionTime.close();
                        vm.selectedInjectionTime = [];
                    }, function failure(error) {
                        toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    });
                }
            }
        }

        vm.confirmDelete = function () {
            vm.pageIndex = 1;
            var ids = vm.selectedInjectionTime.map(function (element) {
                return element.id;
            });
            service.deleteInjectionTimes(ids, function success(data) {
                //toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
                //vm.getAllInjectionTime();
                vm.findBy();
                vm.modalConfirmDelete.close();
                vm.selectedInjectionTime = [];
            }, function failure(error) {
                toastr.error($translate.instant("toastr.delFail"), $translate.instant("toastr.error"));
                console.log(error);
            });
        }

        $scope.deleteInjectionTimeRecord = function (id) {
            if (id) {
                vm.recordId = id;
            } else {
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
        vm.confirmDeleteRecord = function () {
            if (vm.recordId) {
                service.deleteInjectionTime(vm.recordId).then(function (data) {
                    if(data!=null && data.code=="-1"){
						toastr.warning($translate.instant("toastr.del4"), $translate.instant("toastr.warning"));
					}
					else{
						toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
					}
                    //vm.getAllInjectionTime();
                    vm.findBy();
                    vm.modalConfirmDelete.close();
                    vm.selectedInjectionTime = [];
                });
				/*, function failure(error) {
                    toastr.error($translate.instant("toastr.delFail"), $translate.instant("toastr.error"));
                    vm.modalConfirmDelete.close();
                });*/
            }
        }
        function validate() {
            console.log(vm.injectionTime);
            if (!vm.injectionTime.code) {
                toastr.warning($translate.instant("injectionTime.emptyCode"), $translate.instant("toastr.warning"));
                return false;
            }
            if (!vm.injectionTime.name) {
                toastr.warning($translate.instant("injectionTime.emptyName"), $translate.instant("toastr.warning"));
                return false;
            }
            if (vm.injectionTime.type < 0) {
                toastr.warning($translate.instant("injectionTime.emptyType"), $translate.instant("toastr.warning"));
                return false;
            }
			if(vm.injectionTime.isNew==false && (vm.tempCode==null || vm.tempCode=="")){
				toastr.warning($translate.instant("injectionTime.emptyCode"), $translate.instant("toastr.warning"));
				return false;
			}
            return true;
        }

        function checkDuplicateCode(code, type, action) { //type: 1 -> save; 2 -> edit;   action: 1 -> just check code; 2 -> save or edit
            service.checkDuplicateCode(code).then(function (data) {
                vm.viewCheckDuplicateCode = data;
                console.log(action);
                console.log(type);
                if (action == 1) {
                    if (type == 1) {
                        console.log(11);
                        if (vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == true) {
                            // toastr.warning("Mã bị trùng");
                            toastr.warning($translate.instant("checkCode.codeDuplicate"), $translate.instant("toastr.warning"));
                        }
                        if (vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == false) {
                            //toastr.success("Mã không bị trùng");
                            toastr.success($translate.instant("checkCode.codeDuplicateNot"), $translate.instant("toastr.info"));
                        }
                    }
                    if (type == 2) {
                        console.log(12);
                        if (vm.tempCode.toLowerCase().trim() != code.toLowerCase().trim()) {
                            checkDuplicateCode(vm.tempCode, 1, 1);
                        } else {
                            // toastr.info("Mã chưa thay đổi");
                            toastr.info($translate.instant("checkCode.codeNotChange"), $translate.instant("toastr.info"));
                        }
                    }
                }
                if (action == 2) {
                    if (type == 1) {
                        console.log(21);
                        if (vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == true) {
                            //toastr.warning("Mã bị trùng");
                            toastr.warning($translate.instant("checkCode.codeDuplicate"), $translate.instant("toastr.warning"));
                        }
                        if (vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == false) {

                            vm.saveInjectionTime();
                            console.log(21);
                        }
                    }
                    if (type == 2) {
                        if (vm.tempCode.toLowerCase().trim() != code.toLowerCase().trim()) {
                            service.checkDuplicateCode(vm.tempCode).then(function (data) {
                                vm.viewCheckDuplicateCode = data;
                                if (vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == true) {
                                    //toastr.warning("Mã bị trùng");
                                    toastr.warning($translate.instant("checkCode.codeDuplicate"), $translate.instant("toastr.warning"));
                                }
                                if (vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == false) {
                                    vm.injectionTime.code = vm.tempCode.trim();

                                    vm.saveInjectionTime();
                                    console.log(22);
                                }
                            });
                        } else {
                            vm.injectionTime.code = vm.tempCode.trim();

                            vm.saveInjectionTime();
                            console.log(3);
                        }
                    }
                }
                console.log(data);

            });
        }
        vm.checkDuplicateCode = function (type, action) {
            if (validate()) {
                console.log('123');
                checkDuplicateCode(vm.injectionTime.code, type, action);

            }
        }

    }

})();
