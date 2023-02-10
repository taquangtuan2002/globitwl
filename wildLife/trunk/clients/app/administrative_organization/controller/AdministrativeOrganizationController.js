(function() {
    'use strict';
    angular.module('Education.AdministrativeOrganization').controller('AdministrativeOrganizationController', AdministrativeOrganizationController);
    AdministrativeOrganizationController.$inject = [
        '$rootScope',
        '$scope',
        '$timeout',
        'settings',
        '$uibModal',
        'toastr',
        'blockUI',
        'bsTableAPI',
        'Utilities',
        '$translate',
        'AdministrativeOrganizationService',
        'UserService',
        'FmsAdministrativeService'
    ];

    function AdministrativeOrganizationController($rootScope, $scope, $timeout, settings, modal, toastr, blockUI, bsTableAPI, utils, $translate, service, userService, fmsAdministrative) {
        $scope.$on('$viewContentLoaded', function() {
            // initialize core components
            App.initAjax();
        });

        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;
        var vm = this;
        
        vm.treeData = [];

        // FmsAdiministrative
        vm.fmsAdministratives = [];
        vm.fmsAdministrative = {};
        vm.modalInstance = {};
        vm.searchDto = {};
        vm.pageIndex = 1;
        vm.pageSize = 10;
        vm.administrativeOrganization = {};
        vm.organizationLevel = [];
        vm.listGovernmentLevel = [];
        vm.listOrganizationalForm = [];
        vm.searchRootDto = {};
        vm.listFmsOrganization = [];
        vm.modalConfirmDelete = {};
        vm.recordId = null;
        vm.listRoot = [];
        
        // Role
        vm.isRole = false;
        vm.isFamer = false;
        vm.isSdah = false;
        vm.isDistrict = false;
        vm.isWard = false;
        vm.isSearchExtend = false;
        vm.filterRole = null;
        vm.isSdahView = false;
        vm.selectedDist = [];
        vm.selectedWard = [];
        vm.isTrungUong = false;
        //vm.isRoleAdmin = $scope.hasAnyRoles($scope.currentUser, ['ROLE_ADMIN']);

        // Các thẻ select
        vm.organizationLevel = [
            {
                value: 1,
                name: 'Đơn vị',
            },
            {
                value: 2,
                name: 'Phòng',
            }
        ];
        vm.listGovernmentLevel = [
            {
                id: 4,
                name: "Trung ương"
            },
            {
                id: 1,
                name: "Tỉnh"
            },
            {
                id: 2,
                name: "Huyện"
            },
            {
                id: 3,
                name: "Xã"
            }
        ]
        vm.listOrganizationalForm = [
            {
                id: 1,
                name: "Đơn vị thuộc(Tổng cục/Cục kiểm)"
            },
            {
                id: 2,
                name: "Trực thuộc Sở NN và PTT"
            },
            {
                id: 3,
                name: "Các phòng ban thuộc Chi Cục Kiểm Lâm"
            },
            {
                id: 4,
                name: "Thuộc Hạt kiểm lâm"
            }
        ]
        // Phân quyền theo user đăng nhập
        blockUI.start();
        service.getCurrentUser().then(function(data) {
            vm.user = data;
            var roles = data.roles;
            if (roles != null && roles.length > 0) {
                for (var i = 0; i < roles.length; i++) {
                    if (roles[i] != null && roles[i].authority && (roles[i].authority.toLowerCase() == 'role_admin' || roles[i].authority.toLowerCase() == 'role_dlp')) {
                        vm.isRole = true;
                        vm.isRoleAdmin = true;
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
            if (vm.isRole) { 
                getAllCity();
                //vm.getUsers();
                // vm.findBy();
            } else {
                if (vm.isRole == false) {
                    getAdministrativeUnitDtoByLoginUser();
                }
            }
            blockUI.stop();
        });
        // service.getOrganization({currentOrgId : null}).then(function(data){
        //     vm.listAdministrativeOrganization =  data;
        // })
        
        // Load user
        function getAdministrativeUnitDtoByLoginUser() {
            service.getAdministrativeUnitDtoByLoginUser().then(function(data) {
                vm.adminstrativeUnits = data;
                if(vm.adminstrativeUnits != null && vm.adminstrativeUnits.length > 0) {
                    getDataByCombobox(vm.adminstrativeUnits);
                }
            })
        }
        function getDataByCombobox(administrativeUnit) {
            if (vm.searchDto == null) {
                vm.searchDto = {};
            }
            for (var i = 0; i < administrativeUnit.length; i++) {
                var item = administrativeUnit[i];
                if (item.parentDto == null) {
                    // vm.selectedCity = item; Để người dùng chọn
                    vm.searchDto.administrativeUnitId = item.id;
                    vm.province = item;
                    if (vm.adminstrativeUnits_City == null) vm.adminstrativeUnits_City = [];
                    vm.adminstrativeUnits_City.push(item);
                    service.getAllByParentId(vm.searchDto.administrativeUnitId).then(function(data) {
                        if (data != null && data.length > 0) {
                            vm.adminstrativeUnits_Dist = data;
                        }

                    });
                } else if (item.parentDto != null && item.parentDto.parentDto == null) {
                    vm.searchDto.administrativeUnitId = item.parentDto.id;
                    // vm.selectedDist.push(item);
                    vm.selectedCity = item.parentDto;

                    if (vm.adminstrativeUnits_City == null) vm.adminstrativeUnits_City = [];
                    vm.adminstrativeUnits_City.push(item.parentDto);
                    if (vm.adminstrativeUnits_Dist == null) vm.adminstrativeUnits_Dist = [];
                    vm.adminstrativeUnits_Dist.push(item);
                    service.getAllByParentId(item.id).then(function(data) {
                        if (data != null && data.length > 0) {
                            vm.adminstrativeUnits_Wards = data;
                        }

                    });
                }
            }
            // vm.findBy();

        }

        //  End phân quyền
        function getAllCity() {
            fmsAdministrative.getAllCity().then(function(data) {
                vm.adminstrativeUnits_City = data;
            })
        }

        vm.findBy = function() {
            vm.treeData = [];
            if(vm.searchRootDto == null) {
                vm.searchRootDto = {};
            }
            if(vm.textSearch != null && vm.textSearch != "") {
                vm.searchRootDto.text = vm.textSearch;
            } else {
                vm.searchRootDto.text = null;
            }
            vm.searchRootDto.pageIndex = vm.pageIndex;
            vm.searchRootDto.pageSize = vm.pageSize
            service.getListTree(vm.searchRootDto).then(function(data) {
                service.getOrganization({currentOrgId : null}).then(function(data1){
                    vm.listAdministrativeOrganization =  data1;
                })
                vm.treeData = data.content;
                vm.totalItems = data.totalElements;
                if(vm.treeData != null && vm.treeData.length > 0) {
                    service.getChildrenByParentId(vm.treeData[0].id).then(function (dataChildren) {
                        if(dataChildren != null && dataChildren.length > 0) 
                        {
                            vm.listRoot = vm.treeData.concat(dataChildren);
                            // vm.listAdministrativeOrganization = vm.listRoot;
                        }
                        else {
                            // vm.listAdministrativeOrganization = vm.treeData;
                        }
                        console.log(vm.treeData);
                    })
                }
                else {
                    vm.listRoot = [];
                    // vm.listAdministrativeOrganization = [];
                }
            })
            
        }
        vm.findBy();
        vm.expandingProperty =  { field: "name", displayName: "Tên tổ chức hành chính", sortable: true };

        vm.onSelectNode = function(node) {
            getChildrenByParentId(node.id);
            // vm.getById(node.id);
        }

        vm.onClickNode = function(node) {
            console.log( "node",node);
            getChildrenByParentId(node.id);
            vm.getById(node.id);
        }

        function getChildrenByParentId(parentId) {
            service.getChildrenByParentId(parentId).then(function(data) {
                vm.childs = data;
                if(vm.treeData != null && vm.treeData.length > 0) {
                    for(var i=0; i<vm.treeData.length; i++) {
                        if (vm.treeData[i].id == parentId) {
                            vm.treeData[i].children = vm.childs;
                            vm.treeData[i].expanded = true;
                        } else if (vm.treeData[i].parentDto == null && vm.treeData[i].expanded == false) {
                            vm.treeData[i].children = [];
                        }
                        if(vm.treeData[i].children != null){
                            for (var j=0; j< vm.treeData[i].children.length; j++){
                                // if(vm.treeData[i].children[j]){
                                //     vm.treeData[i].children[j].children = vm.treeData[i].children[j].childrenDto;
                                // }
                                if (vm.treeData[i].children[j].id == parentId) {
                                    vm.treeData[i].children[j].children = vm.childs;
                                    vm.treeData[i].children[j].expanded = true;
                                } else if (vm.treeData[i].children[j].parentDto == null && vm.treeData[i].children[j].expanded == false) {
                                    vm.treeData[i].children[j].children = [];
                                }
                                if(vm.treeData[i].children[j].children){
                                    for (var k=0; k< vm.treeData[i].children[j].children.length; k++){
                                        if (vm.treeData[i].children[j].children[k].id == parentId) {
                                            vm.treeData[i].children[j].children[k].children = vm.childs;
                                            vm.treeData[i].children[j].children[k].expanded = true;
                                        } else if (vm.treeData[i].children[j].children[k].parentDto == null 
                                            && vm.treeData[i].children[j].children[k].expanded == false) {
                                            vm.treeData[i].children[j].children[k].children = [];
                                        }
                                        if(vm.treeData[i].children[j].children[k].children){
                                            for (var l=0; l< vm.treeData[i].children[j].children[k].children.length; l++){
                                                if (vm.treeData[i].children[j].children[k].children[l].id == parentId) {
                                                    vm.treeData[i].children[j].children[k].children[l].children = vm.childs;
                                                    vm.treeData[i].children[j].children[k].children[l].expanded = true;
                                                } else if (vm.treeData[i].children[j].children[k].children[l].parentDto == null 
                                                    && vm.treeData[i].children[j].children[k].children[l].expanded == false) {
                                                    vm.treeData[i].children[j].children[k].children[l].children = [];
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        
                    }
                }
            })

            
        }

        vm.getById = function(id) {
            service.getById(id).then(function(data) {
                service.getOrganization({currentOrgId : id}).then(function(data1){
                    vm.listAdministrativeOrganization =  data1;
                })
                if(!data.parentDto){
                    vm.isTrungUong = true;
                }else{
                    vm.isTrungUong = false;
                }
                vm.recordId = data.id;
                vm.administrativeOrganization = data;
                if(data.governmentLevel == null) {
                    vm.governmentLevel = null;
                } else {
                    for (var item in vm.listGovernmentLevel) {
                        if(vm.listGovernmentLevel[item].id == data.governmentLevel) {
                            vm.governmentLevel = vm.listGovernmentLevel[item];
                        }
                    }
                }

                if(data.organizationalForm == null) {
                    vm.organizationalForm = null;
                } else {
                    for (var item in vm.listOrganizationalForm) {
                        if(vm.listOrganizationalForm[item].id == data.organizationalForm) {
                            vm.organizationalForm = vm.listOrganizationalForm[item];
                        }
                    }
                }

                // Hiển thị tỉnh huyện xã
                if(data.fmsOrganiration != null && data.fmsOrganiration.length > 0) {
                    for(var i=0; i <data.fmsOrganiration.length; i++) {
                        if(data.fmsOrganiration[i] != null) { 
                            // Tỉnh
                            vm.selectedCity = data.fmsOrganiration[i];
                        }
                        if(data.fmsOrganiration[i].parentDto != null && data.fmsOrganiration[i].parentDto.parentDto == null) {
                            // Huyện
                            vm.selectedCity = data.fmsOrganiration[i].parentDto;
                            vm.selectedDist.push(data.fmsOrganiration[i]);
                        }
                        if(data.fmsOrganiration[i].parentDto != null && data.fmsOrganiration[i].parentDto.parentDto != null) {
                            // Xã
                            vm.selectedCity = data.fmsOrganiration[i].parentDto.parentDto;
                            vm.selectedDist.push(data.fmsOrganiration[i].parentDto);
                            vm.selectedWard.push(data.fmsOrganiration[i]);
                        }
                    }
                }

            })
            vm.selectedCity = null;
            vm.selectedDist = [];
            vm.selectedWard = [];
        }
        // Thêm mới
        vm.addAdministrativeOrganization = function() {
            vm.recordId = null;
            vm.administrativeOrganization = {};
            vm.governmentLevel = null;
            vm.organizationalForm = null;
            vm.selectedCity = null;
            vm.selectedDist = [];
            vm.selectedWard = [];
            vm.isTrungUong = false;
            service.getOrganization({currentOrgId : null}).then(function(data){
                vm.listAdministrativeOrganization =  data;
            })
            // vm.findBy();
        }

        // Lưu
        vm.saveAdministrativeOrganization = function() {
            
            if (!vm.administrativeOrganization.name){
                toastr.warning("Bạn chưa nhập tên đơn vị", "Thông báo");
                return;
            }
            if(!vm.isRoleAdmin && vm.isTrungUong){
                toastr.warning("Bạn không có quyền sửa đơn vị này", "Thông báo");
                return;
            }
            if(!$scope.hasAnyRoles($scope.currentUser, ['ROLE_ADMIN']) && !vm.administrativeOrganization.parentDto){
                toastr.warning("Vui lòng chọn cơ quan chủ quản", "Thông báo");
                return;
            }

            var vnf_regex = /((09|03|07|08|05)+([0-9]{8})\b)/g;
            if(vm.administrativeOrganization.phoneNumber ){
                if (vnf_regex.test(vm.administrativeOrganization.phoneNumber) == false) {
                    toastr.warning("Số điện thoại không hợp lệ", "Thông báo");
                    return;
                }
            }
            if (vm.administrativeOrganization.phoneNumberAgencyRepresentative) {
                if (vnf_regex.test(vm.administrativeOrganization.phoneNumberAgencyRepresentative) == false) {
                    toastr.warning("Số điện thoại không hợp lệ", "Thông báo");
                    return;
                }
            }
            
            if(vm.governmentLevel) {
                vm.administrativeOrganization.governmentLevel = vm.governmentLevel.id;
            }
            if(vm.organizationalForm) {
                vm.administrativeOrganization.organizationalForm = vm.organizationalForm.id;
            }

            // Save tỉnh, huyện, xã
            if(vm.selectedWard != null && vm.selectedWard.length > 0) {
                vm.administrativeOrganization.fmsOrganiration = [];
                for(var i=0; i<vm.selectedWard.length; i++) {
                    var item = vm.selectedWard[i];
                    vm.administrativeOrganization.fmsOrganiration.push(item);
                }
            } else if(vm.selectedDist != null && vm.selectedDist.length > 0) {
                vm.administrativeOrganization.fmsOrganiration = [];
                for(var i=0; i<vm.selectedDist.length; i++) {
                    var item = vm.selectedDist[i];
                    vm.administrativeOrganization.fmsOrganiration.push(item);
                }
            } else if(vm.selectedCity != null) {
                vm.administrativeOrganization.fmsOrganiration = [];
                vm.administrativeOrganization.fmsOrganiration.push(vm.selectedCity);
            } else {
                vm.administrativeOrganization.fmsOrganiration = [];
            }
            if(vm.recordId == null){
                service.saveAdministrativeOrganization(vm.administrativeOrganization, function success(data) {
                    toastr.info("Lưu thành công", "Thông báo");
                    // Clear data
                    vm.administrativeOrganization = {};
                    vm.governmentLevel = null;
                    vm.organizationalForm = null;
                    vm.selectedCity = null;
                    vm.selectedDist = [];
                    vm.selectedWard = [];
                    vm.searchDto.administrativeUnitId = null;
                    vm.findBy();
                })
            }else{
                service.updateAdministrativeOrganization(vm.recordId,vm.administrativeOrganization, function success(data) {
                    toastr.info("Lưu thành công", "Thông báo");
                    // Clear data
                    vm.administrativeOrganization = {};
                    vm.governmentLevel = null;
                    vm.organizationalForm = null;
                    vm.selectedCity = null;
                    vm.selectedDist = [];
                    vm.selectedWard = [];
                    vm.findBy();
                })
            }
            
           
        }
        
        vm.onFmsadminstrativeUnitCitySelected = function (city){
            // Clear data
            vm.selectedDist = null;
            vm.selectedWard = null;
            vm.searchDto.administrativeUnitId = null;
            vm.adminstrativeUnits_Dist = [];
            vm.adminstrativeUnits_Wards = [];
            if(city != null && city.id != null) {
                service.getAllByParentId(city.id).then(function(data) {
                    if(data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                    } else {
                        vm.selectedDist = null;
                        vm.selectedWard = null;
                        vm.searchDto.administrativeUnitId = null;
                        vm.adminstrativeUnits_Dist = [];
                        vm.adminstrativeUnits_Wards = [];
                    }
                });
            } else {
                vm.selectedDist = null;
                vm.selectedWard = null;
                vm.searchDto.administrativeUnitId = null;
                vm.adminstrativeUnits_Dist = [];
                vm.adminstrativeUnits_Wards = [];
            }
        }

        vm.onFmsadminstrativeUnitDistSelected = function(dist) {
            // Clear data
            vm.selectedWard = null;
            vm.searchDto.administrativeUnitId = null;
            vm.adminstrativeUnits_Wards = [];
            if(dist != null && dist.id != null) {
                service.getAllByParentId(dist.id).then(function(data) {
                    if(data != null) {
                        vm.adminstrativeUnits_Wards = data;
                    } else {
                        vm.selectedWard = null;
                        vm.searchDto.administrativeUnitId = null;
                        vm.adminstrativeUnits_Wards = [];
                    }
                })
            } else {
                vm.selectedWard = null;
                vm.searchDto.administrativeUnitId = null;
                vm.adminstrativeUnits_Wards = [];
            }
        }

        // Popup Xóa
        $scope.deleteAdministrativeOrganization = function() {
            if(vm.recordId) {
                if(!vm.administrativeOrganization.parentDto && (!$scope.hasAnyRoles($scope.currentUser, ['ROLE_ADMIN']))){
                    toastr.warning("Bạn không có quyền xóa đơn vị này", $translate.instant("toastr.warning"));
                    return;
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
        }
        // Nút xác nhận xóa
        vm.confirmDeleteRecord = function () {
            if (vm.recordId) {
                service.deleteAdministrativeOrganization(vm.recordId).then(function (data) {
                    if (data == null) {
                        toastr.warning($translate.instant("toastr.del4"), $translate.instant("toastr.warning"));
                    }
                    else {
                        toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
                    }
                    vm.administrativeOrganization = {};
                    vm.governmentLevel = null;
                    vm.organizationalForm = null;
                    vm.selectedDist = [];
                    vm.selectedWard = [];
                    vm.findBy();
                    vm.modalConfirmDelete.close();
                });
            }
        }
    }
})();