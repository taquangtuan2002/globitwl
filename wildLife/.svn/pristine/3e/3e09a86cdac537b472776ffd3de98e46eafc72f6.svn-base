/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.Original').controller('OriginalController', OriginalController);

    OriginalController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'OriginalService',
        '$uibModal',
        'toastr',
        '$state',
        'blockUI',
        '$stateParams',
        'Utilities',
        '$translate'
    ];

    function OriginalController($rootScope, $scope, $http, $timeout, settings, service, modal, toastr, $state, blockUI, $stateParams, utils, $translate) {
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
        vm.originals = []
        vm.modalOriginal = {}
        vm.original = { isNew: false };
        vm.modalConfirmDelete = {};
        vm.selectedOrigins = [];
        vm.count = 0;
        vm.viewCheckDuplicateCode = {};
        vm.tempCode = '';
        vm.getAllOriginal = function () {
            service.getAllOriginal(vm.pageIndex, vm.pageSize).then(function (data) {
                vm.originals = data.content;
                vm.bsTableControl.options.data = vm.originals;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }
        /** generate */
        vm.getAllOriginal();


        /** bussiness */

        vm.bsTableControl = {
            options: {
                data: vm.originals,
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
                        vm.selectedOrigins.push(row);
                    });
                },
                onCheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedOrigins = rows;
                    });
                },
                onUncheck: function (row, $element) {
                    var index = utils.indexOf(row, vm.selectedOrigins);
                    if (index >= 0) {
                        $scope.$apply(function () {
                            vm.selectedOrigins.splice(index, 1);
                        });

                    }
                },
                onUncheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedOrigins = [];
                    });
                },
                onPageChange: function (index, pageSize) {
                    vm.pageSize = pageSize;
                    vm.pageIndex = index;

                    //vm.getAllOriginal();
                    vm.findBy();
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
            service.getPageSearchOriginal(vm.searchDto, vm.pageIndex, vm.pageSize).then(function (data) {
                vm.originals = data.content;
                vm.bsTableControl.options.data = vm.originals;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }


        vm.createOriginal = function () {
            vm.original = { isNew: true };

            vm.modalOriginal = modal.open({
                animation: true,
                templateUrl: 'original_info.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });



        };

        $scope.editOriginal = function (id) {
            if (id) {
                service.getOriginalById(id).then(function (data) {
                    vm.original = data;
                    vm.original.isNew = false;
                    vm.tempCode = vm.original.code;
                    console.log(vm.original);
                });
                vm.modalOriginal = modal.open({
                    animation: true,
                    templateUrl: 'original_info.html',
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

        vm.deleteOriginals = function () {
            if (vm.selectedOrigins.length == 0) {
                //toastr.warning("Bạn chưa chọn!")
                toastr.warning($translate.instant("original.emptySelected"), $translate.instant("toastr.warning"));
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

        vm.saveOriginal = function () {
            if (vm.original) {

                /*if(!vm.original.description){
                    toastr.warning("Bạn chưa nhập mô tả!");
                    return;
                }*/
                if (vm.original.isNew == false) {
                    vm.original.code = vm.tempCode;
                }
                service.saveOriginal(vm.original, function success(data) {
                    toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                    //vm.getAllOriginal();
                    vm.findBy();
                    vm.modalOriginal.close();
                }, function failure(error) {
                    toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                    console.log(error);
                });
            }
        }

        vm.confirmDelete = function () {
            vm.pageIndex = 1;
            var ids = vm.selectedOrigins.map(function (element) {
                return element.id;
            });
            service.deleteServiceByIds(ids, function success(data) {
                toastr.info("Bạn đã xóa thành công các bản ghi đã chọn", "Thông báo!");
                vm.findBy();
                vm.modalConfirmDelete.close();
                vm.selectedOrigins = [];
            }, function failure(error) {
                toastr.error($translate.instant("toastr.delFail"), $translate.instant("toastr.error"));
                console.log(error);
            });
        }
        $scope.deleteOriginalRecord = function (id) {
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
                service.deleteOriginById(vm.recordId).then(function (data) {
                    if (data != null && data.code == "-1") {
                        toastr.warning($translate.instant("toastr.del4"), $translate.instant("toastr.warning"));
                    }
                    else {
                        toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
                    }
                    //vm.getAllOriginal();
                    vm.findBy();
                    vm.modalConfirmDelete.close();
                    vm.selectedOrigins = [];
                });
                /*,function failure(error){
                    toastr.error($translate.instant("toastr.delFail"), $translate.instant("toastr.error"));
                    vm.modalConfirmDelete.close(); 
                });*/
            }
        }
        function validate() {
            console.log(vm.original);
            if (!vm.original.code) {
                toastr.warning($translate.instant("original.emptyCode"), $translate.instant("toastr.warning"));
                return;
            }
            if (!vm.original.name) {
                toastr.warning($translate.instant("original.emptyName"), $translate.instant("toastr.warning"));
                return;
            }
            if (vm.original.isNew == false && (vm.tempCode == null || vm.tempCode == "")) {
                toastr.warning($translate.instant("original.emptyCode"), $translate.instant("toastr.warning"));
                return false;
            }
            return true;
        }

        function checkDuplicateCode(code, type, action) { //type: 1 -> save; 2 -> edit;   action: 1 -> just check code; 2 -> save or edit
            service.checkDuplicateCode(code).then(function (data) {
                vm.viewCheckDuplicateCode = data;
                if (action == 1) {
                    if (type == 1) {
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
                        if (vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == true) {
                            //toastr.warning("Mã bị trùng");
                            toastr.warning($translate.instant("checkCode.codeDuplicate"), $translate.instant("toastr.warning"));
                        }
                        if (vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == false) {

                            vm.saveOriginal();
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
                                    vm.original.code = vm.tempCode.trim();

                                    vm.saveOriginal();
                                }
                            });
                        } else {
                            vm.original.code = vm.tempCode.trim();

                            vm.saveOriginal();
                        }
                    }
                }
                console.log(data);

            });
        }
        vm.checkDuplicateCode = function (type, action) {
            if (validate()) {
                checkDuplicateCode(vm.original.code, type, action);
            }
        }

    }

})();
