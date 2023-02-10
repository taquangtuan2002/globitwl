/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.User').controller('UserGroupController', UserGroupController);

    UserGroupController.$inject = [
        '$rootScope',
        '$scope',
        '$timeout',
        'settings',
        'Utilities',
        '$uibModal',
        'bsTableAPI',
        'blockUI',
        'toastr',
        'UserGroupService'
    ];
    
    function UserGroupController ($rootScope, $scope, $timeout,settings, utils, modal, tableAPI, blockUI, toastr, service) {
        $scope.$on('$viewContentLoaded', function() {
            // initialize core components
            App.initAjax();
        });

        // set sidebar closed and body solid layout mode
        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        var vm = this;

        vm.group = {};
        vm.groups = [];
        vm.selectedGroups = [];

        vm.bsTableControl = {
            options: {
                data: vm.groups,
                idField: 'id',
                sortable: true,
                striped: true,
                maintainSelected: true,
                clickToSelect: false,
                showColumns: true,
                showToggle: true,
                pagination: true,
                pageSize: 25,
                pageList: [5, 10, 25, 50, 100, 200],
                locale: settings.locale,
                sidePagination: 'server',
                //totalRows:100,
                columns: service.getTableDefinition(),
                onCheck: function (row, $element) {
                    $scope.$apply(function () {
                        vm.selectedGroups.push(row);
                    });
                },
                onCheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedGroups = rows;
                    });
                },
                onUncheck: function (row, $element) {
                    var index = utils.indexOf(row, vm.selectedGroups);
                    if (index >= 0) {
                        $scope.$apply(function () {
                            vm.selectedGroups.splice(index, 1);
                        });
                    }
                },
                onUncheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedGroups = [];
                    });
                },
                onPageChange: function (index, pageSize) {
                    vm.pageSize = pageSize;
                    vm.pageIndex = index - 1;

                    // Reload user list on page change
                    getGroups(vm.pageSize, vm.pageIndex);
                }
            }
        };

        vm.pageIndex = 0;
        vm.pageSize = vm.bsTableControl.options.pageSize;

        /**
         * Get list of user groups
         */
        function getGroups(pageIndex, pageSize) {
            service.getGroups(pageIndex, pageSize).then(function (data) {
                vm.groups = data.content;
                vm.bsTableControl.options.data = vm.groups;
                vm.bsTableControl.options.totalRows = data.totalElements;

                console.log(vm.groups);
            });
        }

        // Load first page of user groups list
        getGroups(vm.pageIndex, vm.pageSize);

        vm.newGroup = function () {
            vm.group.isNew = true;

            var modalInstance = modal.open({
                animation: true,
                templateUrl: 'edit_group_modal.html',
                scope: $scope,
                size: 'md'
            });

            modalInstance.result.then(function (confirm) {
                if (confirm == 'yes') {

                    if (!vm.group.name || vm.group.name.trim() == '') {
                        toastr.error('Bạn vui lòng nhập tên nhóm người dùng.', 'Lỗi');
                        return;
                    }

                    blockUI.start();

                    service.saveGroup(vm.group, function success() {

                        // Refresh list
                        getGroups(vm.pageIndex, vm.pageSize);

                        blockUI.stop();

                        // Notify
                        toastr.info('Bạn đã thêm mới thành công 1 bản ghi.', 'Thông báo');

                        // clear object
                        vm.group = {};
                    }, function failure() {
                        blockUI.stop();
                        toastr.error('Có lỗi xảy ra khi thêm mới bản khi.', 'Thông báo');
                    });
                }
            }, function () {
                console.log('Modal dismissed at: ' + new Date());
            });
        };

        $scope.editGroup = function (groupId) {
            blockUI.start();
            service.getGroup(groupId).then(function(data) {

                blockUI.stop();

                vm.group = data;
                vm.group.isNew = false;

                var modalInstance = modal.open({
                    animation: true,
                    templateUrl: 'edit_group_modal.html',
                    scope: $scope,
                    size: 'md'
                });

                modalInstance.result.then(function (confirm) {
                    if (confirm == 'yes') {

                        if (!vm.group.name || vm.group.name.trim() == '') {
                            toastr.error('Bạn vui lòng nhập tên nhóm người dùng.', 'Lỗi');
                            return;
                        }

                        blockUI.start();

                        service.saveGroup(vm.group, function success() {

                            // Refresh list
                            getGroups(vm.pageIndex, vm.pageSize);

                            blockUI.stop();

                            // Notify
                            toastr.info('Bạn đã cập nhật thành công 1 bản ghi.', 'Thông báo');

                            // clear object
                            vm.group = {};
                        }, function failure() {
                            blockUI.stop();
                            toastr.error('Có lỗi xảy ra khi cập nhật bản khi.', 'Thông báo');
                        });
                    }
                }, function () {
                    console.log('Modal dismissed at: ' + new Date());
                });
            });
        };

        /**
         * Delete user groups
         */
        vm.deleteGroups = function () {
            var modalInstance = modal.open({
                animation: true,
                templateUrl: 'confirm_delete_modal.html',
                scope: $scope,
                size: 'md'
            });

            modalInstance.result.then(function (confirm) {
                if (confirm == 'yes') {
                    blockUI.start();
                    service.deleteGroups(vm.selectedGroups, function success() {
                        // Refresh list
                        getGroups(vm.pageIndex, vm.pageSize);

                        // Block UI
                        blockUI.stop();

                        // Notify
                        toastr.info('Bạn đã xoá thành công ' + vm.selectedGroups.length + ' bản ghi.', 'Thông báo');

                        // Clear selected tags
                        vm.selectedGroups = [];
                    }, function failure() {
                        // Block UI
                        blockUI.stop();

                        toastr.error('Có lỗi xảy ra khi xóa bản khi.', 'Thông báo');
                    });
                }
            }, function () {
                console.log('Modal dismissed at: ' + new Date());
            });
        };
    }

})();
