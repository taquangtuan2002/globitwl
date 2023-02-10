/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.ProductTarget').controller('ProductTargetController', ProductTargetController);

    ProductTargetController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'ProductTargetService',
        '$uibModal',
        'toastr',
        '$state',
        'blockUI',
        '$stateParams',
        'Utilities',
        '$translate'
    ];

    function ProductTargetController($rootScope, $scope, $http, $timeout, settings, service, modal, toastr, $state, blockUI, $stateParams, utils, $translate) {
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
        vm.productTargets = []
        vm.modalProductTarget = {}
        vm.productTarget = { isNew: false };
        vm.modalConfirmDelete = {};
        vm.selectedProductTargets = [];
        vm.viewCheckDuplicateCode = {};
        vm.tempCode = '';
        vm.productTargetsIsParentNull = [];
        vm.productTargetsIsParentNotNull = [];

        vm.getAllProductTargetList = function () {
            service.getAllProductTarget().then(function (data) {
                if (data && data.length > 0) {
                    vm.productTargetsIsParentNull = data.filter(el => el.parent == null);
                    vm.productTargetsIsParentNotNull = data.filter(el => el.parent != null);
                }
            });
        }

        vm.getPageProductTarget = function () {
            service.getPageProductTarget(vm.pageIndex, vm.pageSize).then(function (data) {
                vm.productTargets = data.content;
                vm.bsTableControl.options.data = vm.productTargets;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }
        /** generate */
        vm.getPageProductTarget();


        /** bussiness */

        vm.bsTableControl = {
            options: {
                data: vm.productTargets,
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
                        vm.selectedProductTargets.push(row);
                    });
                },
                onCheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedProductTargets = rows;
                    });
                },
                onUncheck: function (row, $element) {
                    var index = utils.indexOf(row, vm.selectedProductTargets);
                    if (index >= 0) {
                        $scope.$apply(function () {
                            vm.selectedProductTargets.splice(index, 1);
                        });

                    }
                },
                onUncheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedProductTargets = [];
                    });
                },
                onPageChange: function (index, pageSize) {
                    vm.pageSize = pageSize;
                    vm.pageIndex = index;

                    //vm.getPageProductTarget();
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
            service.getPageSearchProductTarget(vm.searchDto, vm.pageIndex, vm.pageSize).then(function (data) {
                vm.productTargets = data.content;
                vm.bsTableControl.options.data = vm.productTargets;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }




        vm.createProductTarget = function () {
            vm.getAllProductTargetList();
            vm.productTarget = { isNew: true };
            vm.modalProductTarget = modal.open({
                animation: true,
                templateUrl: 'product_target_info.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        };

        $scope.editProductTarget = function (id) {
            vm.getAllProductTargetList();
            if (id) {
                service.getProducTargetById(id).then(function (data) {
                    vm.productTarget = data;
                    vm.productTarget.isNew = false;
                    vm.tempCode = vm.productTarget.code;
                    console.log(vm.productTarget);
                });
                vm.modalProductTarget = modal.open({
                    animation: true,
                    templateUrl: 'product_target_info.html',
                    scope: $scope,
                    backdrop: 'static',
                    keyboard: false,
                    size: 'md'
                });
            }
        }

        $scope.deleteproductTarget = function (id) {

            vm.modalConfirmDelete = modal.open({
                animation: true,
                templateUrl: 'confirm_delete.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        }

        vm.saveProductTarget = function () {
            if (vm.productTarget) {

                /*if(!vm.productTarget.description){
                    toastr.warning("Bạn chưa nhập mô tả!");
                    return;
                }*/

                if (vm.productTarget.id) {
                    vm.productTarget.code = vm.tempCode;
                    service.saveProductTarget(vm.productTarget.id, vm.productTarget, function success(data) {
                        toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                        // vm.getPageProductTarget();
                        vm.findBy();
                        vm.modalProductTarget.close();
                    }, function failure(error) {
                        toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    });
                } else {
                    service.createProductTarget(vm.productTarget, function success(data) {
                        toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                        //vm.getPageProductTarget();
                        vm.findBy();
                        vm.modalProductTarget.close();
                    }, function failure(error) {
                        toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    });
                }
            }
        }

        vm.confirmDelete = function () {
            vm.pageIndex = 1;
            var ids = vm.selectedProductTargets.map(function (element) {
                return element.id;
            });
            service.deleteServiceByIds(ids, function success(data) {
                //vm.getPageProductTarget();
                vm.findBy();
                toastr.info("Bạn đã xóa thành công các bản ghi đã chọn", "Thông báo!");
                vm.modalConfirmDelete.close();
                vm.selectedProductTargets = [];
            }, function failure(error) {
                toastr.error($translate.instant("toastr.delFail"), $translate.instant("toastr.error"));
                console.log(error);
            });
        }
        $scope.deleteProductTargetRecord = function (id) {
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
                service.deleteProductTargetById(vm.recordId).then(function (data) {
                    if (data != null && data.code == "-1") {
                        toastr.warning($translate.instant("toastr.del4"), $translate.instant("toastr.warning"));
                    }
                    else {
                        toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
                    }
                    //vm.getPageProductTarget();
                    vm.findBy();
                    vm.modalConfirmDelete.close();
                    vm.selectedProductTargets = [];
                });
                /*,function failure(error){
                    toastr.error($translate.instant("toastr.delFail"), $translate.instant("toastr.error"));
                    vm.modalConfirmDelete.close(); 
                });*/
            }
        }

        vm.deleteProductTargets = function () {
            if (vm.selectedProductTargets.length == 0) {
                //toastr.warning("Bạn chưa chọn!")
                toastr.warning($translate.instant("product.emptySelected"), $translate.instant("toastr.warning"));
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
        function validate() {
            console.log(vm.productTarget);
            if (!vm.productTarget.code) {
                toastr.warning($translate.instant("product.emptyCode"), $translate.instant("toastr.warning"));
                return false;
            }
            if (!vm.productTarget.name) {
                toastr.warning($translate.instant("product.emptyName"), $translate.instant("toastr.warning"));
                return false;
            }
            if (vm.productTarget.isNew == false && (vm.tempCode == null || vm.tempCode == "")) {
                toastr.warning($translate.instant("product.emptyCode"), $translate.instant("toastr.warning"));
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

                            vm.saveProductTarget();
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
                                    vm.productTarget.code = vm.tempCode.trim();

                                    vm.saveProductTarget();
                                }
                            });
                        } else {
                            vm.productTarget.code = vm.tempCode.trim();

                            vm.saveProductTarget();
                        }
                    }
                }
                console.log(data);

            });
        }
        vm.checkDuplicateCode = function (type, action) {
            if (validate()) {
                checkDuplicateCode(vm.productTarget.code, type, action);
            }
        }

    }

})();
