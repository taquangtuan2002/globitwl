/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.User').controller('UserRoleController', UserRoleController);

    UserRoleController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'UserRoleService',
        '$uibModal',
        'toastr'
    ];

    function UserRoleController ($rootScope, $scope, $http, $timeout,settings,service,modal,toastr) {
        $scope.$on('$viewContentLoaded', function() {
            // initialize core components
            App.initAjax();
        });

        // set sidebar closed and body solid layout mode
        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        var vm = this;
        vm.role = null;
        vm.roles = [];
        vm.selectedRoles = [];

        // pagination
        vm.pageIndex = 1;
        vm.pageSize = 10;

        function getListRole(pageIndex, pageSize){
            service.getListRole(pageIndex,pageSize).then(function(data) {
                vm.roles = data.content;
                if(vm.roles.length <= 0 && data.totalElements != 0){
                    $state.reload();
                }
                vm.bsTableControl.options.data = vm.roles;
                vm.bsTableControl.options.totalRows = data.totalElements;
                console.log(data);
            });
        }

        function newRole(role) {
            service.newRole(role).then(function(data) {
                getListRole(vm.pageIndex,vm.pageSize);
                toastr.info('Thêm mới thành công','Thông báo');
            });
        }

        function getRoleById(roleById){
            service.getRoleById(roleById).then(function(data) {
                vm.role = data;
                console.log(vm.role);
            });
        }

        function editRole(role) {
            service.editRole(role).then(function (data) {
                getListRole(vm.pageIndex,vm.pageSize);
                toastr.info('Lưu thành công','Thông báo');
            });
        }

        function deleteRole(roleId) {
            service.deleteRole(roleId).then(function (data) {
                getListRole(vm.pageIndex,vm.pageSize);
                toastr.info('Xóa thành công.', 'Thông báo');
            });
        }

        function validateRoleData(role) {
            if(role.name == null || role.name.length <= 0 || role.name == ''){
                toastr.warning('Hành động không thành công! Tên quyền không được để trống','Thông báo!');
                return false;
            }
            return true;
        }

        vm.newRole = function () {
            var modalInstance = modal.open({
                animation: true,
                templateUrl: 'new_modal.html',
                scope: $scope,
                size: 'md'
            });

            vm.role = {};

            modalInstance.result.then(function (confirm) {
                if (confirm == 'yes') {
                    if(validateRoleData(vm.role)){
                        newRole(vm.role);
                    }
                }
            }, function () {
                console.log("cancel");
            });
        }

        $scope.editRole = function (roleId) {
            var modalInstance = modal.open({
                animation: true,
                templateUrl: 'edit_modal.html',
                scope: $scope,
                size: 'md'
            });

            getRoleById(roleId);

            modalInstance.result.then(function (confirm) {
                if (confirm == 'yes') {
                    if(validateRoleData(vm.role)) {
                        editRole(vm.role);
                    }
                }
            }, function () {
                console.log("cancel");
            });
        }

        $scope.viewRole = function (roleId) {
            var modalInstance = modal.open({
                animation: true,
                templateUrl: 'view_modal.html',
                scope: $scope,
                size: 'md'
            });

            getRoleById(roleId);

            modalInstance.result.then(function (confirm) {
                if (confirm == 'yes') {
                }
            }, function () {
                console.log("cancel");
            });
        }

        $scope.deleteRole = function (roleId) {
            var modalInstance = modal.open({
                animation: true,
                templateUrl: 'delete_modal.html',
                scope: $scope,
                size: 'md'
            });

            getRoleById(roleId);

            modalInstance.result.then(function (confirm) {
                if (confirm == 'yes') {
                    if(vm.role != null){
                        if(!angular.isUndefined(vm.role.id)){
                            deleteRole(vm.role.id);
                        }
                    }
                }
            }, function () {
                console.log("cancel");
            });
        }

        getListRole(vm.pageIndex,vm.pageSize);

        vm.bsTableControl = {
            options: {
                data: vm.roles,
                idField: 'id',
                sortable: true,
                striped: true,
                maintainSelected: true,
                clickToSelect: false,
                showColumns: false,
                singleSelect: false,
                showToggle: false,
                pagination: true,
                pageSize: vm.pageSize,
                pageList: [5, 10, 25, 50, 100],
                locale: settings.locale,
                sidePagination: 'server',
                columns: service.getTableDefinition(),
                onCheck: function (row, $element) {
                    $scope.$apply(function () {
                        vm.selectedRoles.push(row);
                    });
                },
                onCheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedRoles = rows;
                    });
                },
                onUncheck: function (row, $element) {
                    var index = utils.indexOf(row, vm.selectedRoles);
                    if (index >= 0) {
                        $scope.$apply(function () {
                            vm.selectedRoles.splice(index, 1);
                        });
                    }
                },
                onUncheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedRoles = [];
                    });
                },
                onPageChange: function (index, pageSize) {
                    vm.pageSize = pageSize;
                    vm.pageIndex = index;
                    getListRole(vm.pageIndex,vm.pageSize);
                },
                onChange: function (index, pageSize) {
                    vm.pageSize = pageSize;
                    vm.pageIndex = index;
                    getListRole(vm.pageIndex,vm.pageSize);
                }
            }
        };
    }
})();
