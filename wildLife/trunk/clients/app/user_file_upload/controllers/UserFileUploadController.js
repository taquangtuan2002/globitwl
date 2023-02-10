/**
 * Created by dungld on 14/12/2020.
 */
(function () {
    'use strict';
    angular.module('Education.UserFileUpload').controller('UserFileUploadController', UserFileUploadController);
    UserFileUploadController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'UserFileUploadService',
        '$uibModal',
        'toastr',
        '$state',
        'blockUI',
        '$stateParams',
        'Utilities',
        '$translate',
        'Upload',
        'FileSaver',
        '$filter',
    ];

    function UserFileUploadController($rootScope, $scope, $http, $timeout, settings, service, modal, toastr, $state, blockUI, $stateParams, utils, $translate, Uploader, FileSaver, $filter) {
        $scope.$on('$viewContentLoaded', function () {
            // initialize core components
            App.initAjax();
        });
        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        /** declare variable */
        var vm = this;
        vm.recordId = null;
        vm.pageIndex = 1;
        vm.pageSize = 25;
        vm.UserFileUploads = []
        vm.modalUserFileUpload = {}
        vm.UserFileUpload = {};
        vm.UserFileUpload.attachments = [];
        vm.UserFileUpload = { isNew: false, typeDto: null, originalDto: null, productTargetDto: null };
        vm.modalConfirmDelete = {};
        vm.selectedUserFileUploads = [];
        vm.baseUrl = settings.api.baseUrl + settings.api.apiPrefix;
        vm.isRole = false;
        vm.isFamer = false;
        vm.isSdah = false;
        vm.isDistrict = false;
        vm.isWard = false;
        vm.isSearchExtend = false;
        vm.isSdahView = false;//cấp tỉnh nhưng chỉ được xem
        //------Start--Phân quyền theo user đăng nhập-----------
        blockUI.start();
        service.getCurrentUser().then(function (data) {
            console.log(data);
            vm.user = data;
            var roles = data.roles;
            if (roles != null && roles.length > 0) {
                for (var i = 0; i < roles.length; i++) {
                    if (roles[i] != null && roles[i].authority && (roles[i].authority.toLowerCase() == 'role_admin' || roles[i].authority.toLowerCase() == 'role_dlp')) {
                        vm.isRole = true;
                    }
                    if (roles[i] != null && roles[i].authority && roles[i].authority.toLowerCase() == 'role_sdah') {
                        vm.isSdah = true;
                    }
                    if (roles[i] != null && roles[i].authority && roles[i].authority.toLowerCase() == 'role_district') {
                        vm.isDistrict = true;
                    }
                    if (roles[i] != null && roles[i].authority && roles[i].authority.toLowerCase() == 'role_ward') {
                        vm.isWard = true;
                    }
                    if (roles[i] != null && roles[i].authority && roles[i].authority.toLowerCase() == 'role_famer') {
                        vm.isFamer = true;
                    }
                    if (roles[i] != null && roles[i].authority && roles[i].authority.toLowerCase() == 'role_sdah_view') {
                        vm.isSdahView = true;
                        //vm.isSdah = true;
                    }
                }
            } else {
                vm.isRole = false;
                vm.isFamer = false;
                vm.isSdah = false;
                vm.isDistrict = false;
                vm.isWard = false;
                vm.isSdahView = false;
            }
            if (vm.isRole) {//trường hợp admin và dlp thì được xem tất cả các cơ sở
                getAllProvince();
                vm.search();
            } else {// trường hợp này thì phân quyền theo user
                if (vm.isRole == false) {
                    getAdministrativeUnitDtoByLoginUser();
                }
            }
            blockUI.stop();
        });

        //load user administrativeunit
        function getAdministrativeUnitDtoByLoginUser() {
            service.getAdministrativeUnitDtoByLoginUser().then(function (data) {
                vm.adminstrativeUnits = data;
                if (vm.adminstrativeUnits != null && vm.adminstrativeUnits.length > 0) {
                    getDataByCombobox(vm.adminstrativeUnits[0]);
                }
            });
        }
        function getDataByCombobox(administrativeUnit) {
            if (administrativeUnit.parentDto == null) {
                vm.province = administrativeUnit.id;
                if (vm.adminstrativeUnits_City == null) vm.adminstrativeUnits_City = [];
                vm.adminstrativeUnits_City.push(administrativeUnit);
                service.getAllByParentId(vm.province).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                    }

                });
            } else if (administrativeUnit.parentDto != null && administrativeUnit.parentDto.parentDto == null) {
                vm.district = administrativeUnit.id;
                vm.province = administrativeUnit.parentDto.id;
                vm.adminstrativeUnits_City = [];
                vm.adminstrativeUnits_City.push(administrativeUnit.parentDto);
                vm.adminstrativeUnits_Dist = [];
                if (vm.adminstrativeUnits_Dist == null) vm.adminstrativeUnits_Dist = [];
                vm.adminstrativeUnits_Dist.push(administrativeUnit);
                service.getAllByParentId(vm.province).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                    }

                });
                service.getAllByParentId(vm.district).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Wards = data;
                    }

                });
            } else if (administrativeUnit.parentDto != null && administrativeUnit.parentDto.parentDto != null) {
                vm.ward = administrativeUnit.id;
                vm.district = administrativeUnit.parentDto.id;
                vm.province = administrativeUnit.parentDto.parentDto.id;

                if (vm.adminstrativeUnits_City == null) vm.adminstrativeUnits_City = [];
                vm.adminstrativeUnits_City.push(administrativeUnit.parentDto.parentDto);
                if (vm.adminstrativeUnits_Dist == null) vm.adminstrativeUnits_Dist = [];
                vm.adminstrativeUnits_Dist.push(administrativeUnit.parentDto);
                if (vm.adminstrativeUnits_Wards == null) vm.adminstrativeUnits_Wards = [];
                vm.adminstrativeUnits_Wards.push(administrativeUnit);
            }
            if (vm.isRole == false && vm.isFamer == true && vm.user !== null && vm.user.username !== null && vm.user.username !== '') {//trường hợp tài khoản nông dân
                vm.textSearch = vm.user.username;
            }
            vm.search();
        }
        //--------End ----Phân quyền-------------

        vm.getPageUserFileUpload = function () {
            vm.searchDto = {};
            vm.searchDto.pageIndex = 1;
            vm.searchDto.pageSize = 25;
            service.getPageUserFileUpload(vm.searchDto).then(function (data) {
                vm.UserFileUploads = data.content;
                vm.bsTableControl.options.data = vm.UserFileUploads;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }
        /** generate */
        vm.getPageUserFileUpload();
        function getAllProvince() {
            service.getAdminstrativeUnitsCity().then(function (data) {
                vm.adminstrativeUnits_City = data;
            });
        }
        vm.onFmsadminstrativeUnitCitySelected = function (city) {
            if (city != null && city.id != null) {
                service.getAllByParentId(city.id).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                        vm.selectedDist = null;
                        vm.selectedWard = null;
                        vm.district = null;
                        vm.ward = null;
                        vm.district = null;
                    } else {
                        vm.district = null;
                        vm.ward = null;
                        vm.selectedDist = null;
                        vm.selectWards = null;
                        vm.district = null;
                        vm.adminstrativeUnits_Dist = [];
                        vm.adminstrativeUnits_Wards = [];
                    }
                });
            } else {
                vm.district = null;
                vm.ward = null;
                vm.selectedDist = null;
                vm.selectWards = null;
                vm.district = null;
                vm.adminstrativeUnits_Dist = [];
                vm.adminstrativeUnits_Wards = [];
            }
            vm.pageIndex = 0;
            vm.search();
        }

        vm.onFmsadminstrativeUnitDistSelected = function (dist) {
            if (dist != null && dist.id != null) {
                service.getAllByParentId(dist.id).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Wards = data;
                        vm.ward = null;
                        vm.selectWards = null;
                        vm.ward = null;

                    }
                    else {
                        vm.selectWards = null;
                        vm.ward = null;
                        vm.adminstrativeUnits_Wards = [];
                    }

                });
            }
            else {
                vm.selectWards = null;
                vm.ward = null;
                vm.adminstrativeUnits_Wards = [];
            }

            vm.pageIndex = 0;
            vm.search();
        }
        vm.onFmsadminstrativeUnitWardsSelected = function (item) {
            vm.pageIndex = 0;
            vm.search();
        }
        vm.bsTableControl = {
            options: {
                data: vm.UserFileUploads,
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
                        vm.selectedUserFileUploads.push(row);
                    });
                },
                onCheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedUserFileUploads = rows;
                    });
                },
                onUncheck: function (row, $element) {
                    var index = utils.indexOf(row, vm.selectedUserFileUploads);
                    if (index >= 0) {
                        $scope.$apply(function () {
                            vm.selectedUserFileUploads.splice(index, 1);
                        });

                    }
                },
                onUncheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedUserFileUploads = [];
                    });
                },
                onPageChange: function (index, pageSize) {
                    vm.pageSize = pageSize;
                    vm.pageIndex = index;
                    vm.findBy();
                }
            }
        };

        vm.uploadedAttachmentFile = null;
        vm.errorAttachmentFile = null;
        vm.uploadAttachmentFiles = function (file, errFiles) {
            if (file != null) {
                vm.uploadedAttachmentFile = file;
                if (vm.uploadedAttachmentFile != null) {
                    Uploader.upload({
                        url: settings.api.baseUrl + settings.api.apiPrefix + 'user_file_upload/uploadattachment',
                        method: 'POST',
                        data: { uploadfile: vm.uploadedAttachmentFile }
                    }).then(function (successResponse) {
                        var attachment = successResponse.data;
                        if (vm.uploadedAttachmentFile && (!vm.errorAttachmentFile || (vm.errorAttachmentFile && vm.errorAttachmentFile.$error == null))) {
                            if (vm.UserFileUpload != null && vm.UserFileUpload.attachments == null || vm.UserFileUpload.attachments == undefined) {
                                vm.UserFileUpload.attachments = [];
                            }
                            vm.UserFileUpload.attachments.push(
                                attachment
                            );
                            if(vm.UserFileUpload.attachments && vm.UserFileUpload.attachments.length >0){
                                vm.UserFileUpload.attachments.forEach(att => {
                                    att.isLock = true;
                                });
                            }
                            vm.bsTableControlAttachmentFiles.options.data = vm.UserFileUpload.attachments;
                        }
                    }, function (errorResponse) {
                        toastr.error('Có lỗi xảy ra khi gửi file, vui lòng thử lại', 'Lỗi');
                    }, function (evt) {
                        console.log('progress: ' + parseInt(100.0 * evt.loaded / evt.total) + '%');
                    });
                }
            }
        };

        $scope.viewFile = function (index) {
            if (vm.UserFileUpload != null && vm.UserFileUpload.attachments != null) {
                for (var i = 0; i < vm.UserFileUpload.attachments.length; i++) {
                    if (i == index) {
                        var attachment = vm.UserFileUpload.attachments[i];
                        service.getFileAttachmentUploadById(attachment.file.id).success(function (data) {
                            var file = new Blob([data], { type: attachment.file.contentType });
                            if (attachment.file.contentType == 'application/pdf') {
                                var fileURL = URL.createObjectURL(file);
                                window.open(fileURL);
                            } else {
                                toastr.warning('Không thể xem tệp tin. Hãy tải xuống', 'Thông báo');
                            }
                        });;
                    }
                }
            }
        }
        $scope.downloadFile = function (index) {
            if (vm.UserFileUpload != null && vm.UserFileUpload.attachments != null) {
                for (var i = 0; i < vm.UserFileUpload.attachments.length; i++) {
                    if (i == index) {
                        var attachment = vm.UserFileUpload.attachments[i];
                        service.getFileAttachmentUploadById(attachment.file.id).success(function (data) {
                            var file = new Blob([data], { type: attachment.file.contentType });
                            FileSaver.saveAs(file, attachment.file.name);
                        });
                    }
                }
            }
        }
        $scope.deleteFileAttachment = function (index) {
            if (vm.UserFileUpload != null && vm.UserFileUpload.attachments != null && vm.UserFileUpload.attachments.length > 0) {
                vm.UserFileUpload.attachments.splice(index / 1, 1);
            }
            vm.bsTableControlAttachmentFiles.options.data = vm.UserFileUpload.attachments;
        }


        vm.enterSearch = function () {
            if (event.keyCode == 13) {//Phím Enter
                vm.search();
            }
        };
        vm.search = function () {
            vm.pageIndex = 1;
            vm.bsTableControl.state.pageNumber = 1;
            vm.findBy();
        };
        vm.findBy = function () {
            var searchDto = {
                pageIndex: vm.pageIndex,
                pageSize: vm.pageSize,
                provinceId: vm.province ? vm.province : null,
                districtId: vm.district ? vm.district : null,
                wardId: vm.ward ? vm.ward : null,
                text: vm.textSearch ? vm.textSearch : ""
            }
            service.getPageUserFileUpload(searchDto).then(function (data) {
                vm.UserFileUploads = data.content;
                vm.bsTableControl.options.data = vm.UserFileUploads;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }


        vm.createUserFileUpload = function () {
            vm.UserFileUpload = {};
            vm.isDisabledField = false;
            vm.bsTableControlAttachmentFiles.options.data = [];
            vm.isNew = true;
            vm.modalUserFileUpload = modal.open({
                animation: true,
                templateUrl: 'UserFileUpload_Info.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'lg'
            });
        };

        $scope.editUserFileUpload = function (id) {
            if (id) {
                vm.isNew = false;
                vm.isDisabledField = false;
                service.getUserFileUploadById(id).then(function (data) {
                    vm.UserFileUpload = data;
                    if(data.attachments && data.attachments.length >0){
                        data.attachments.forEach(att => {
                            att.isLock = true;
                        });
                    }
                    vm.bsTableControlAttachmentFiles.options.data = data.attachments;
                });
                vm.modalUserFileUpload = modal.open({
                    animation: true,
                    templateUrl: 'UserFileUpload_Info.html',
                    scope: $scope,
                    backdrop: 'static',
                    keyboard: false,
                    size: 'lg'
                });
            }
        }
        $scope.viewUserFileUploadRecord = function (id) {
            if (id) {
                vm.isNew = null;
                vm.isDisabledField = true;
                service.getUserFileUploadById(id).then(function (data) {
                    vm.UserFileUpload = data;
                    if(data.attachments && data.attachments.length >0){
                        data.attachments.forEach(att => {
                            att.isLock = false;
                        });
                    }
                    vm.bsTableControlAttachmentFiles.options.data = data.attachments;
                });
                vm.modalUserFileUpload = modal.open({
                    animation: true,
                    templateUrl: 'UserFileUpload_Info.html',
                    scope: $scope,
                    backdrop: 'static',
                    keyboard: false,
                    size: 'lg'
                });
            }
        }
        $scope.deleteUserFileUpload = function (id) {
            vm.modalConfirmDelete = modal.open({
                animation: true,
                templateUrl: 'confirm_delete.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        }

        vm.saveUserFileUpload = function () {
            if (vm.UserFileUpload.id) {
                service.updateUserFileUpload(vm.UserFileUpload.id, vm.UserFileUpload, function success(data) {
                    toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                    vm.findBy();
                    vm.modalUserFileUpload.close();
                }, function failure(error) {
                    toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                    console.log(error);
                });
            } else {
                service.createUserFileUpload(vm.UserFileUpload, function success(data) {
                    toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                    vm.findBy();
                    vm.modalUserFileUpload.close();
                }, function failure(error) {
                    toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                    console.log(error);
                });
            }
        }

        vm.confirmDelete = function () {
            vm.pageIndex = 1;
            var ids = vm.selectedUserFileUploads.map(function (element) {
                return element.id;
            });
            service.deleteListId(ids, function success(data) {
                vm.findBy();
                vm.modalConfirmDelete.close();
                vm.selectedUserFileUploads = [];
            }, function failure(error) {
                console.log(error);
            });
        }
        $scope.deleteUserFileUploadRecord = function (id) {
            if (id) {
                vm.recordId = id;
                service.getUserFileUploadById(id).then(function (data) {
                    if (data && data != null)
                        vm.UserFileUploadDelete = data;
                });
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
                service.deleteUserFileUpload(vm.recordId).then(function (data) {
                    toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
                    vm.findBy();
                    vm.modalConfirmDelete.close();
                    vm.selectedUserFileUploads = [];
                });
            }
        }

        vm.deleteUserFileUploads = function () {
            vm.modalConfirmDelete = modal.open({
                animation: true,
                templateUrl: 'confirm_delete.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        }
        vm.bsTableControlAttachmentFiles = {
            options: {
                data: vm.UserFileUpload.attachments,
                sortable: false,
                striped: true,
                maintainSelected: true,
                clickToSelect: false,
                showColumns: false,
                singleSelect: true,
                showToggle: false,
                pagination: false,
                locale: settings.locale,
                columns: getTableFileAttachment()
            }
        };

        /** upload task comment attachment file */
        function getTableFileAttachment() {
            var _tableOperation = function (value, row, index) {
                var ret = '';
                ret += '<a class="text-danger margin-right-5" href="#"  ng-show="'+row.isLock+'" data-ng-click="$parent.deleteFileAttachment(' + "'" + index + "'" + ')"><i class="icon-trash"></i></a>'
                ret += '<a class="green-dark margin-right-10" uib-tooltip="Tải về" tooltip-trigger="mouseenter" href="#" data-ng-click="$parent.downloadFile(' + "'" + index + "'" + ')"><i class="icon-cloud-download"></i></a>';
                ret += '<a class="green-dark" uib-tooltip="Xem tệp tin" tooltip-trigger="mouseenter" href="#" data-ng-click="$parent.viewFile(' + "'" + index + "'" + ')"><i class="icon-info"></i></a>';
                return ret;
            };
            var _fileSize = function (value, row, index) {
                return $filter('fileSize')(value);
            };

            var _fileType = function (value, row, index) {
                return $filter('contentType')(value);
            };

            var _cellNowrap = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap' }
                };
            };

            var _operationColStyle = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap', 'width': '90px', 'text-align': 'center' }
                };
            };

            return [{
                field: '',
                title: 'Thao tác',
                switchable: true,
                visible: true,
                formatter: _tableOperation,
                cellStyle: _operationColStyle
            }, {
                field: 'file.name',
                title: 'Tên tệp tin',
                sortable: true,
                switchable: false,
                cellStyle: _operationColStyle
            }, {
                field: 'file.contentSize',
                title: 'Kích thước tệp tin',
                sortable: true,
                switchable: false,
                cellStyle: _operationColStyle,
                formatter: _fileSize
            }]
        }

    }
})();
