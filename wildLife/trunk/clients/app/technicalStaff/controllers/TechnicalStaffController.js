
(function () {
    'use strict';

    angular.module('Education.TechnicalStaff').controller('TechnicalStaffController', TechnicalStaffController);

    TechnicalStaffController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'TechnicalStaffService',
        '$uibModal',
        'toastr',
        '$state',
        'blockUI',
        '$stateParams',
        'Utilities',
        '$translate',
        'FmsAdministrativeService'
        
    ];

    function TechnicalStaffController($rootScope, $scope, $http, $timeout, settings, service, modal, toastr, $state, blockUI, $stateParams, utils, $translate, fmsAdministrativeService) {
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
        vm.technicalStaffs = []
        vm.modalTechnicalStaff = {}
        vm.technicalStaff = { isNew: false };
        // vm.isViewDetail = false;
        vm.modalConfirmDelete = {};
        vm.selectedTechnicalStaffs = [];
        vm.viewCheckDuplicateCode = {};
        vm.tempCode = '';
        vm.technicalStaffIsProvinceNull = [];
        vm.technicalStaffIsProvinceNotNull = [];
        vm.searchDtotTechnicalStaff = {};
        vm.isRole = false;
        vm.isSdahView = false;
        vm.isSdah = false;
        vm.selectedDist = [];
        vm.selectedCity = [];
        vm.selectedWard = [];
        vm.searchDto = {};


        // blockUI.start();
        blockUI.start();
        service.getCurrentUser().then(function (data) {
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
                    if (roles[i] != null && roles[i].authority && roles[i].authority.toLowerCase() == 'role_sdah_view') {
                        vm.isSdahView = true;
                        vm.isSdah = true;
                    }

                }
            } else {
                vm.isRole = false;
                vm.isSdah = false;
                vm.isSdahView = false;

            }
            if (vm.isRole) { //trường hợp admin và dlp thì được xem tất cả các cơ sở chăn nuôi
                getAllCity();
                //vm.getUsers();
                vm.findBy();
            } else { // trường hợp này thì phân quyền theo user
                if (vm.isRole == false) {
                    getAdministrativeUnitDtoByLoginUser();
                    
                }
            }
            blockUI.stop();
        });


        function getAdministrativeUnitDtoByLoginUser() {
            service.getAdministrativeUnitDtoByLoginUser().then(function (data) {
                vm.adminstrativeUnits = data;
                if (vm.adminstrativeUnits != null && vm.adminstrativeUnits.length > 0) {
                    getCombobox(vm.adminstrativeUnits);

                }
            });
        }

        function getCombobox(administrativeUnit) {
            if (vm.searchDto == null) {
                vm.searchDto = {};
            }
            for (var i = 0; i < administrativeUnit.length; i++) {
                var item = administrativeUnit[i];
                // tk tinh
                if (item.parentDto == null) {
                    // vm.selectedCity = item; Để người dùng chọn
                    vm.searchDto.administrativeUnitId = item.id;
                    vm.province = item;
                    
                    if (vm.adminstrativeUnitsCity != null)  vm.adminstrativeUnitsCity = [];
                    vm.adminstrativeUnitsCity.push(item);
                    service.getAllByParentId(vm.searchDto.administrativeUnitId).then(function (data) {
                        if (data != null && data.length > 0) {
                            vm.adminstrativeUnits_Dist = data;
                        }

                    });
                    //tk huyen 
                } else if (item.parentDto != null && item.parentDto.parentDto == null) {
                    vm.searchDto.administrativeUnitId = item.parentDto.id;
                    // vm.selectedDist.push(item);
                    vm.technicalStaff.province = item.parentDto;

                    if (vm.adminstrativeUnitsCity != null) vm.adminstrativeUnitsCity = [];
                    vm.adminstrativeUnitsCity.push(item.parentDto);
                    if (vm.adminstrativeUnits_Dist == null) vm.adminstrativeUnits_Dist = [];
                    vm.adminstrativeUnits_Dist.push(item);
                    service.getAllByParentId(item.id).then(function (data) {
                        if (data != null && data.length > 0) {
                            vm.adminstrativeUnits_Wards = data;
                        }

                    });
                }
            }
            // vm.findBy();

        }

        vm.getAllTechnicalStaff = function () {
            service.getAllTechnicalStaff().then(function (data) {
                if (data && data.length > 0) {
                    vm.technicalStaffIsProvinceNull = data.filter(el => el.Province == null);
                    vm.technicalStaffIsProvinceNotNull = data.filter(el => el.Province != null);
                }
            });
        }

        vm.getPageSearchTechnicalStaff = function () {
            service.getPageSearchTechnicalStaff(vm.searchDtotTechnicalStaff, vm.pageIndex, vm.pageSize).then(function (data) {
                vm.technicalStaffs = data.content;
                vm.bsTableControl.options.data = vm.technicalStaffs;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }
        /** generate */
        vm.getPageSearchTechnicalStaff();


        /** bussiness */

        vm.bsTableControl = {
            options: {
                data: vm.technicalStaffs,
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
                        vm.selectedTechnicalStaffs.push(row);
                    });
                },
                onCheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedTechnicalStaffs = rows;
                    });
                },
                onUncheck: function (row, $element) {
                    var index = utils.indexOf(row, vm.selectedTechnicalStaffs);
                    if (index >= 0) {
                        $scope.$apply(function () {
                            vm.selectedTechnicalStaffs.splice(index, 1);
                        });

                    }
                },
                onUncheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedTechnicalStaffs = [];
                    });
                },
                onPageChange: function (index, pageSize) {
                    vm.pageSize = pageSize;
                    vm.pageIndex = index;
                    vm.getPageSearchTechnicalStaff();
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
                vm.searchDto.text = vm.textSearch;
            }
            else {
                vm.searchDto.text = null;
            }

            // console.log(vm.searchDto);
            service.getPageSearchTechnicalStaff(vm.searchDto, vm.pageIndex, vm.pageSize).then(function (data) {
                vm.technicalStaffs = data.content;
                vm.bsTableControl.options.data = vm.technicalStaffs;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }


        vm.getAllCity = function () {
            fmsAdministrativeService.getAllCity().then(function (data) {
                vm.adminstrativeUnitsCity = data;
            });
        }
        service.getAdminstrativeUnitsCity().then(function (data) {
            vm.adminstrativeUnitsCity = data;
        });
        vm.onFmsadminstrativeUnitCitySelected = function (city) {
            debugger
            // vm.adminstrativeUnitsCityFrom = [];
            if (city) {
                vm.technicalStaff.province = city;
            } else {
                vm.technicalStaff.province = null;
            }
            vm.technicalStaff.district = null;
            vm.technicalStaff.commune = null;
            // vm.bsTableControlFarm.state.pageNumber = 1;

            if (city != null && city.id != null) {
                fmsAdministrativeService.getAllByParentId(city.id).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                    }
                    else {
                        vm.adminstrativeUnits_Dist = [];
                        vm.adminstrativeUnits_Wards = [];
                        // vm.searchFarmDto.district = null;
                        vm.technicalStaff.district = null;
                        // vm.searchFarmDto.ward = null;
                        vm.technicalStaff.commune = null;
                    }
                });
            }
            else {
                // vm.searchFarmDto.district = null;
                vm.technicalStaff.district = null;
                // vm.searchFarmDto.ward = null;
                vm.technicalStaff.commune = null;
                vm.adminstrativeUnits_Dist = [];
                vm.adminstrativeUnits_Wards = [];
            }
            vm.findBy();
        }


        vm.onFmsadminstrativeUnitDistSelected = function (district) {
            if (district) {
                // vm.searchDtoFarm.district = district.id;
                vm.technicalStaff.district = district;
            } else {
                vm.technicalStaff.district = null;
            }
            vm.technicalStaff.commune = null;
            // vm.bsTableControlFarm.state.pageNumber = 1;
            if (district != null && district.id != null) {
                service.getAllByParentId(district.id).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Wards = data;
                    }
                    else {
                        vm.technicalStaff.commune = null;
                        vm.adminstrativeUnits_Wards = [];
                    }
                });
            }
            else {
                vm.adminstrativeUnits_Wards = [];
            }
            vm.findBy();
        }
        
        // vm.onFmsadminstrativeUnitCitySelected = function (city) {

        //     if (city != null && city.id != null) {
        //         // vm.technicalStaff.Province.name = city.name; // Hiển thị tên tỉnh lên bảng
        //         fmsAdministrativeService.getChildrenByParentId(city.id).then(function (data) {
        //             if (data != null && data.length > 0) {
        //                 vm.adminstrativeUnits_Dist = data;
        //             }
        //             else {
        //                 vm.adminstrativeUnits_Dist = [];
        //                 vm.technicalStaff.district = null;
        //                 vm.technicalStaff.commune = null;
        //             }
        //         })
        //     }
        //     else {
        //         vm.adminstrativeUnits_Dist = [];
        //         vm.technicalStaff.district = null;
        //         vm.technicalStaff.commune = null;
        //     }
        //     vm.findBy();
        // }
        // vm.onFmsadminstrativeUnitDistSelected = function (dist) {

        //     if (dist != null && dist.id != null) {
        //         fmsAdministrativeService.getChildrenByParentId(dist.id).then(function (data) {
        //             if (data != null && data.length > 0) {
        //                 vm.adminstrativeUnits_Wards = data;
        //                 vm.technicalStaff.commune = null;
        //             }
        //             else {
        //                 vm.adminstrativeUnits_Wards = [];
        //                 vm.technicalStaff.commune = null;
        //             }
        //         });
        //     }
        //     else {
        //         vm.adminstrativeUnits_Wards = [];
        //         vm.technicalStaff.commune = null;
        //     }
        //     vm.findBy();
        // }
        vm.onFmsadminstrativeUnitWardsSelected = function (ward) {
            vm.findBy();
        }
        vm.onSelectedGender = function (item) {
            vm.technicalStaff.gender = item;
        }

        vm.gender = [
            'Nam',
            'Nữ',
            'Không rõ'
        ];


        vm.createTechnicalStaff = function () {
            vm.getAllTechnicalStaff();
            vm.technicalStaff = { isNew: true };
            vm.modalTechnicalStaff = modal.open({
                animation: true,
                templateUrl: 'technicalStaff_info.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        };

        $scope.editTechnicalStaff = function (id) {
            vm.getAllTechnicalStaff();
            if (id) {
                service.getTechnicalStaffById(id).then(function (data) {
                    vm.technicalStaff = data;
                    vm.technicalStaff.isNew = false;
                    vm.tempCode = vm.technicalStaff.code;
                    console.log(vm.technicalStaff);
                });
                vm.modalTechnicalStaff = modal.open({
                    animation: true,
                    templateUrl: 'technicalStaff_info.html',
                    scope: $scope,
                    backdrop: 'static',
                    keyboard: false,
                    size: 'md'
                });
            }
        }

        $scope.deleteTechnicalStaff = function (id) {

            vm.modalConfirmDelete = modal.open({
                animation: true,
                templateUrl: 'confirm_delete.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });
        }



        vm.saveTechnicalStaff = function () {
            if (vm.technicalStaff) {

                /*if(!vm.technicalStaff.description){
                    toastr.warning("Bạn chưa nhập mô tả!");
                    return;
                }*/

                if (vm.technicalStaff.id) {
                    vm.technicalStaff.code = vm.tempCode;
                    service.saveTechnicalStaff(vm.technicalStaff.id, vm.technicalStaff, function success(data) {
                        toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                        // vm.getPageTechnicalStaff();
                        vm.findBy();
                        vm.modalTechnicalStaff.close();
                    }, function failure(error) {
                        toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    });
                } else {
                    service.createTechnicalStaff(vm.technicalStaff, function success(data) {
                        toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                        //vm.getPageTechnicalStaff();
                        vm.findBy();
                        vm.modalTechnicalStaff.close();
                    }, function failure(error) {
                        toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    });
                }
            }
        }

        vm.confirmDelete = function () {
            vm.pageIndex = 1;
            var ids = vm.selectedTechnicalStaffs.map(function (element) {
                return element.id;
            });
            service.deleteServiceByIds(ids, function success(data) {
                //vm.getPageTechnicalStaff();
                vm.findBy();
                toastr.info("Bạn đã xóa thành công các bản ghi đã chọn", "Thông báo!");
                vm.modalConfirmDelete.close();
                vm.selectedTechnicalStaffs = [];
            }, function failure(error) {
                toastr.error($translate.instant("toastr.delFail"), $translate.instant("toastr.error"));
                console.log(error);
            });
        }

        $scope.deleteTechnicalStaffRecord = function (id) {
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
                service.deleteTechnicalStaffById(vm.recordId).then(function (data) {
                    if (data != null && data.code == "-1") {
                        toastr.warning($translate.instant("toastr.del4"), $translate.instant("toastr.warning"));
                    }
                    else {
                        toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
                    }
                    //vm.getPageTechnicalStaff();
                    vm.findBy();
                    vm.modalConfirmDelete.close();
                    vm.selectedTechnicalStaffs = [];
                });
                /*,function failure(error){
                    toastr.error($translate.instant("toastr.delFail"), $translate.instant("toastr.error"));
                    vm.modalConfirmDelete.close(); 
                });*/
            }
        }

        vm.deleteTechnicalStaffs = function () {
            if (vm.selectedTechnicalStaffs.length == 0) {
                //toastr.warning("Bạn chưa chọn!")
                toastr.warning($translate.instant("Bạn chưa chọn!"), $translate.instant("toastr.warning"));
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
            console.log(vm.technicalStaff);
            if (!vm.technicalStaff.code) {
                toastr.warning($translate.instant("Vui lòng nhập mã đầy đủ "), $translate.instant("toastr.warning"));
                return false;
            }
            if (!vm.technicalStaff.name) {
                toastr.warning($translate.instant("Vui lòng nhập tên đầy đủ "), $translate.instant("toastr.warning"));
                return false;
            }
            if (!vm.technicalStaff.email) {
                toastr.warning($translate.instant("Vui lòng nhập email đầy đủ "), $translate.instant("toastr.warning"));
                return false;
            }
            if (vm.technicalStaff.isNew == false && (vm.tempCode == null || vm.tempCode == "")) {
                toastr.warning($translate.instant("technicalStaff.emptyCode"), $translate.instant("toastr.warning"));
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

                            vm.saveTechnicalStaff();
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
                                    vm.technicalStaff.code = vm.tempCode.trim();

                                    vm.saveTechnicalStaff();
                                }
                            });
                        } else {
                            vm.technicalStaff.code = vm.tempCode.trim();

                            vm.saveTechnicalStaff();
                        }
                    }
                }
                console.log(data);

            });
        }
        vm.checkDuplicateCode = function (type, action) {
            if (validate()) {
                checkDuplicateCode(vm.technicalStaff.code, type, action);
            }
        }

    }

})();