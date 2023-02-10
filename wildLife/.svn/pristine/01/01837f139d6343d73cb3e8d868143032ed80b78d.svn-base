/**
 * Created by bizic on 28/8/2016.
 */
(function() {
    'use strict';

    angular.module('Education.HusbandryType').controller('HusbandryTypeController', HusbandryTypeController);

    HusbandryTypeController.$inject = [
        '$rootScope',
        '$scope',
        '$timeout',
        'settings',
        '$uibModal',
        'toastr',
        'blockUI',
        'bsTableAPI',
        'Utilities',
        'HusbandryTypeService'
    ];


    function HusbandryTypeController($rootScope, $scope, $timeout, settings, modal, toastr, blockUI, bsTableAPI, utils, service) {
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
        vm.husbandryType = {}; // mảng Region
        vm.husbandryTypes = [];
        vm.id = null;

        vm.selectedHusbandryType = [];

        vm.roles = [];
        vm.groups = [];

        // UI
        vm.modalInstance = null;

        // pagination
        vm.pageIndex = 0;
        vm.pageSize = 10;

        vm.changePass = false;

        /**
         * Get a list of HusbandryType
         */
        vm.getHusbandryTypes = function() {
            service.getHusbandryTypes(vm.pageIndex, vm.pageSize).then(function(data) {
                vm.husbandryTypes = data.content;
                vm.bsTableControl.options.data = vm.husbandryTypes;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        };

        vm.getHusbandryTypes();

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

        vm.saveHusbandryType = function() {


            if (!vm.husbandryType.name) {
                toastr.error('Vui lòng nhập đầy đủ tên !', 'Thông báo');
                return;
            }

            if (!vm.husbandryType.code) {
                toastr.error('Vui lòng nhập đầy đủ mã !', 'Thông báo');
                return;
            }

            // if (!vm.fmsRegion.description) {
            //     toastr.error('Vui lòng nhập đầy đủ mô tả vùng!', 'Thông báo');
            //     return;
            // }

            // if (!vm.fmsRegion.acreage) {
            //     toastr.error('Vui lòng nhập đầy đủ diện tích vùng!', 'Thông báo');
            //     return;
            // }

            service.saveHusbandryType(vm.husbandryType).then(function(data) {
                toastr.info('Đã lưu thông tin người dùng thành công!', 'Thông báo');

                // Reload HusbandryType
                vm.getHusbandryTypes();
                vm.modalInstance.close();
            }, function errorCallback(response) {
                toastr.error('Có lỗi xảy ra khi lưu.', 'Thông báo');
            });

            // service.deleteHusbandryType(id).then(function(data) {
            //     toastr.info('Đã xóa thông tin người dùng thành công!', 'Thông báo');

            //     // Reload HusbandryType
            //     vm.getHusbandryTypes();
            //     vm.modalInstance.close();
            // }, function errorCallback(response) {
            //     toastr.error('Có lỗi xảy ra khi xóa.', 'Thông báo');
            // });

        };

        /**
         * Create a new user
         */
        vm.newHusbandryType = function() {
            vm.husbandryType = { isNew: true };

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
                vm.husbandryType = data.content;
                vm.bsTableControl.options.data = vm.husbandryType;
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
                vm.getWaterSources();
            }
        }

        vm.bsTableControl = {
            options: {
                data: vm.husbandryType,
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
                            vm.selectedHusbandryType.push(row);
                        } else {
                            bsTableAPI('bsTableControl', 'uncheckBy', { field: 'username', values: ['admin'] });
                        }
                        // vm.selectedHusbandryType.push(row);
                    });
                },
                onCheckAll: function(rows) {
                    $scope.$apply(function() {
                        angular.forEach(rows, function(row) {
                            if (row.username && row.username != 'admin') {
                                vm.selectedHusbandryType.push(row);
                            } else {
                                bsTableAPI('bsTableControl', 'uncheckBy', { field: 'username', values: ['admin'] });
                            }
                        });
                        // vm.selectedHusbandryType = rows;
                    });
                },
                onUncheck: function(row, $element) {
                    var index = utils.indexOf(row, vm.selectedHusbandryType);
                    if (index >= 0) {
                        $scope.$apply(function() {
                            vm.selectedHusbandryType.splice(index, 1);
                        });
                    }
                },
                onUncheckAll: function(rows) {
                    $scope.$apply(function() {
                        vm.selectedHusbandryType = [];
                    });
                },
                onPageChange: function(index, pageSize) {
                    vm.pageSize = pageSize;
                    vm.pageIndex = index;

                    vm.getHusbandryTypes();
                }
            }
        };
        $scope.editHusbandryType = function(id) {
            service.getHusbandryTypeById(id).then(function(data) {
                if (data && data.id) {
                    vm.husbandryType = data;
                    vm.husbandryType.isNew = false;

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

        $scope.deleteHusbandryType = function(id) {
            vm.id = id;
            vm.modalInstance = modal.open({
                animation: true,
                templateUrl: 'delete_modal.html',
                backdrop: 'static',
                scope: $scope,
                size: 'lg'
            });


        }
        vm.confirmDelete = function() {
            // alert(vm.id)
            service.deleteHusbandryTypeById(vm.id).then(function(data) {

                vm.husbandryType = data;
                vm.husbandryType.isNew = false;
                vm.getHusbandryTypes();
                vm.modalInstance.close();
            });
        }

    }

})();