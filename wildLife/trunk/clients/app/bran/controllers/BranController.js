/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.Bran').controller('BranController', BranController);

    BranController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'BranService',
        '$uibModal',
        'toastr',
        '$state',
        'blockUI',
        '$stateParams',
        'Utilities',
        '$translate'
    ];

    function BranController($rootScope, $scope, $http, $timeout, settings, service, modal, toastr, $state, blockUI, $stateParams, utils, $translate) {
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
        vm.brans = []
        vm.modalBran = {};
        vm.bran = { isNew: false };
        vm.modalConfirmDelete = {};
        vm.selectedBran = [];
        vm.count = 0;
        vm.searchDto = {};
        vm.viewCheckDuplicateCode = {};
        vm.tempCode = '';
        vm.getAllBran = function () {
            service.getAllBran(vm.pageIndex, vm.pageSize).then(function (data) {
                vm.brans = data.content;
                vm.bsTableControl.options.data = vm.brans;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }
        /** generate */
        vm.getAllBran();


        /** bussiness */

        vm.bsTableControl = {
            options: {
                data: vm.brans,
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
                        vm.selectedBran.push(row);
                    });
                },
                onCheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedBran = rows;
                    });
                },
                onUncheck: function (row, $element) {
                    var index = utils.indexOf(row, vm.selectedBran);
                    if (index >= 0) {
                        $scope.$apply(function () {
                            vm.selectedBran.splice(index, 1);
                        });

                    }
                },
                onUncheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedBran = [];
                    });
                },
                onPageChange: function (index, pageSize) {
                    vm.pageSize = pageSize;
                    vm.pageIndex = index;
                    vm.findBy();
                    //vm.getAllBran();
                }
            }
        };
      

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
            service.getPageSearchBran(vm.searchDto, vm.pageIndex, vm.pageSize).then(function (data) {
                vm.brans = data.content;
                vm.bsTableControl.options.data = vm.brans;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }


        vm.createBran = function () {
            vm.bran = { isNew: true };

            vm.modalBran = modal.open({
                animation: true,
                templateUrl: 'bran_info.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });



        };

        $scope.editBran = function (id) {
            if (id) {
                service.getBranById(id).then(function (data) {
                    vm.bran = data;
                    vm.bran.isNew = false;
                    vm.tempCode = vm.bran.code;
                    console.log(vm.bran);
                });
                vm.modalBran = modal.open({
                    animation: true,
                    templateUrl: 'bran_info.html',
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

        vm.deleteBrans = function () {
            if (vm.selectedBran.length == 0) {
                //toastr.warning("Bạn chưa chọn!")
                toastr.warning($translate.instant("bran.emptySelected"), $translate.instant("toastr.warning"));
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

        vm.saveBran = function () {
            if (vm.bran) {

                /*if(!vm.bran.description){
                    toastr.warning("Bạn chưa nhập mô tả!");
                    return;
                }*/
                console.log(1);
                if (vm.bran.isNew) {
                    console.log(2);
                    service.createBran(vm.bran, function success(data) {
                        toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                        //vm.getAllBran();
                        vm.findBy();
                        vm.modalBran.close();
                        vm.selectedBran = [];
                    }, function failure(error) {
                        toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    });
                } else {
                    vm.bran.code = vm.tempCode;
                    service.updateBran(vm.bran.id, vm.bran, function success(data) {
                        toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                        //vm.getAllBran();
                        vm.findBy();
                        vm.modalBran.close();
                        vm.selectedBran = [];
                    }, function failure(error) {
                        toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    });
                }
            }
        }

        vm.confirmDelete = function () {
            vm.pageIndex = 1;
            var ids = vm.selectedBran.map(function (element) {
                return element.id;
            });
            service.deleteBrans(ids, function success(data) {
                //toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
                //vm.getAllBran();
                vm.findBy();
                vm.modalConfirmDelete.close();
                vm.selectedBran = [];
            }, function failure(error) {
                toastr.error($translate.instant("toastr.delFail"), $translate.instant("toastr.error"));
                console.log(error);
            });
        }

        $scope.deleteBranRecord = function (id) {
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
                service.deleteBran(vm.recordId).then(function (data) {
                    if(data!=null && data.code=="-1"){
						toastr.warning($translate.instant("toastr.del4"), $translate.instant("toastr.warning"));
					}
					else{
						toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
					}
                    //vm.getAllBran();
                    vm.findBy();
                    vm.modalConfirmDelete.close();
                    vm.selectedBran = [];
                });
				/*, function failure(error) {
                    toastr.error($translate.instant("toastr.delFail"), $translate.instant("toastr.error"));
                    vm.modalConfirmDelete.close();
                });*/
            }
        }
        function validate() {
            console.log(vm.bran);
            if (!vm.bran.code) {
                toastr.warning($translate.instant("bran.emptyCode"), $translate.instant("toastr.warning"));
                return false;
            }
            if (!vm.bran.name) {
                toastr.warning($translate.instant("bran.emptyName"), $translate.instant("toastr.warning"));
                return false;
            }
            // if (!vm.bran.description ) {
            //     toastr.warning($translate.instant("bran.emptyDescription"), $translate.instant("toastr.warning"));
            //     return false;
            // }
			if(vm.bran.isNew==false && (vm.tempCode==null || vm.tempCode=="")){
				toastr.warning($translate.instant("bran.emptyCode"), $translate.instant("toastr.warning"));
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

                            vm.saveBran();
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
                                    vm.bran.code = vm.tempCode.trim();

                                    vm.saveBran();
                                    console.log(22);
                                }
                            });
                        } else {
                            vm.bran.code = vm.tempCode.trim();

                            vm.saveBran();
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
                checkDuplicateCode(vm.bran.code, type, action);

            }
        }

    }

})();
