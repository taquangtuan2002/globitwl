/**
 * Created by bizic on 28/8/2016.
 */
(function() {
    'use strict';

    angular.module('Education.AdministrativeUnitEditable').controller('AdministrativeUnitEditableController', AdministrativeUnitEditableController);

    AdministrativeUnitEditableController.$inject = [
        '$rootScope',
        '$scope',
        '$timeout',
        'settings',
        '$uibModal',
        'toastr',
        'blockUI',
        'bsTableAPI',
        'Utilities',
        'AdministrativeUnitEditableService',
        '$translate',
    ];
    angular.module('Education.AdministrativeUnitEditable').directive('fileDownload', function() {
        return {
            restrict: 'A',
            scope: {
                fileDownload: '=',
                fileName: '=',
            },

            link: function(scope, elem, atrs) {


                scope.$watch('fileDownload', function(newValue, oldValue) {

                    if (newValue != undefined && newValue != null) {
                        console.debug('Downloading a new file');
                        var isFirefox = typeof InstallTrigger !== 'undefined';
                        var isSafari = Object.prototype.toString.call(window.HTMLElement).indexOf('Constructor') > 0;
                        var isIE = /*@cc_on!@*/ false || !!document.documentMode;
                        var isEdge = !isIE && !!window.StyleMedia;
                        var isChrome = !!window.chrome && !!window.chrome.webstore || window.chrome != null;;
                        var isOpera = (!!window.opr && !!opr.addons) || !!window.opera || navigator.userAgent.indexOf(' OPR/') >= 0;
                        var isBlink = (isChrome || isOpera) && !!window.CSS;

                        if (isFirefox || isIE || isChrome) {
                            if (isChrome) {
                                console.log('Manage Google Chrome download');
                                var url = window.URL || window.webkitURL;
                                var fileURL = url.createObjectURL(scope.fileDownload);
                                var downloadLink = angular.element('<a></a>'); //create a new  <a> tag element
                                downloadLink.attr('href', fileURL);
                                downloadLink.attr('download', scope.fileName);
                                downloadLink.attr('target', '_self');
                                downloadLink[0].click(); //call click function
                                url.revokeObjectURL(fileURL); //revoke the object from URL
                            }
                            if (isIE) {
                                console.log('Manage IE download>10');
                                window.navigator.msSaveOrOpenBlob(scope.fileDownload, scope.fileName);
                            }
                            if (isFirefox) {
                                console.log('Manage Mozilla Firefox download');
                                var url = window.URL || window.webkitURL;
                                var fileURL = url.createObjectURL(scope.fileDownload);
                                var a = elem[0]; //recover the <a> tag from directive
                                a.href = fileURL;
                                a.download = scope.fileName;
                                a.target = '_self';
                                a.click(); //we call click function
                            }


                        } else {
                            alert('SORRY YOUR BROWSER IS NOT COMPATIBLE');
                        }
                    }
                });

            }
        }
    });

    function AdministrativeUnitEditableController($rootScope, $scope, $timeout, settings, modal, toastr, blockUI, bsTableAPI, utils, service, $translate) {
        $scope.$on('$viewContentLoaded', function() {
            // initialize core components
            App.initAjax();
        });

        // set sidebar closed and body solid layout mode
        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        var vm = this;

        vm.administrativeUnitEditable = {};
        vm.administrativeUnitEditables = [];
        vm.selectedAdministrativeUnitEditables = [];
        vm.administrativeUnitEditable.roles = "";
        vm.roles = [];
        vm.groups = [];
        vm.tempRoles = [];
        vm.administrativeUnits = [];

        // UI
        vm.modalInstance = {};
        vm.modalChooseFmsAdmin = {};

        // pagination
        vm.pageIndex = 1;
        vm.pageSize = 10;

        vm.changePass = false;

        vm.isRole = false;
        vm.isFamer = false;
        vm.isSdah = false;
        vm.isDistrict = false;
        vm.isWard = false;
        vm.isSearchExtend = false;
        vm.filterRole = null;
        vm.isSdahView = false;
        vm.searchDto = {};

        vm.selectedDist = [];
        vm.selectedCity = [];
        vm.selectedWard = [];

        //------Start--Phân quyền theo user đăng nhập-----------
        blockUI.start();
        service.getCurrentUser().then(function(data) {
            console.log(data);
            // debugger
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

        //load user administrativeunit
        function getAdministrativeUnitDtoByLoginUser() {
            service.getAdministrativeUnitDtoByLoginUser().then(function(data) {
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
                if (item.parentDto == null) {
                    vm.selectedCity = item;
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
                    //vm.searchDto.district=administrativeUnit.id;
                    vm.searchDto.administrativeUnitId = item.parentDto.id;
                    vm.selectedDist.push(item);
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
            vm.findBy();
        }


        //--------End ----Phân quyền-------------

        function getAllCity() {
            service.getAdminstrativeUnitsCity().then(function(data) {
                vm.adminstrativeUnits_City = data;
                // console.log("ádá",vm.adminstrativeUnits_City);

            });
        }
        getAllCity();
        vm.onFmsadminstrativeUnitCitySelected = function(city) {
            let isSelectSdahView = false;
            if (vm.user.roles && vm.user.roles.length > 0) {
                isSelectSdahView = vm.user.roles.filter(r => r.name.toLowerCase() == "ROLE_SDAH_VIEW".toLowerCase()).length > 0 ? true : false;
            }
            if (isSelectSdahView == true) {
                if (city != null && city.id != null) {
                    vm.adminstrativeUnits_Dist = [];
                    if (vm.selectedCity.filter(e => e.id === city.id).length > 1) {
                        vm.selectedCity.pop();
                    }
                } else {
                    toastr.warning("Không chọn được city", "Thông báo");
                }
            }

            if (isSelectSdahView == false) {
                vm.selectedCity = [];
                vm.selectedCity.push(city);
                if (city != null && city.id != null) {
                    service.getAllByParentId(city.id).then(function(data) {
                        if (data != null && data.length > 0) {
                            vm.adminstrativeUnits_Dist = data;
                            vm.selectedDist = null;
                            vm.selectedWard = null;
                            vm.searchDto.district = null;
                            vm.searchDto.ward = null;
                            vm.ward = null;
                            vm.district = null;
                        } else {
                            vm.searchDto.district = null;
                            vm.searchDto.ward = null;
                            vm.selectedDist = null;
                            vm.selectWards = null;
                            vm.district = null;
                            vm.adminstrativeUnits_Dist = [];
                            vm.ward = null;
                            vm.adminstrativeUnits_Wards = [];
                        }
                    });
                } else {
                    vm.searchDto.district = null;
                    vm.searchDto.ward = null;
                    vm.selectedDist = null;
                    vm.selectWards = null;
                    vm.district = null;
                    vm.adminstrativeUnits_Dist = [];
                    vm.ward = null;
                    vm.adminstrativeUnits_Wards = [];
                }
            }

            vm.findBy();
        }

        vm.onFmsadminstrativeUnitDistSelected = function(dist) {
            if (dist != null && dist.id != null) {
                service.getAllByParentId(dist.id).then(function(data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Wards = data;
                        vm.searchDto.ward = null;
                        vm.selectWards = null;
                        vm.ward = null;

                    } else {
                        vm.searchDto.ward = null;
                        vm.selectWards = null;
                        vm.ward = null;
                        vm.adminstrativeUnits_Wards = [];
                    }

                });
            } else {
                vm.searchDto.ward = null;
                vm.selectWards = null;
                vm.ward = null;
                vm.adminstrativeUnits_Wards = [];
            }
            vm.findBy();
        }
        vm.onFmsadminstrativeUnitWardsSelected = function(item) {
            vm.findBy();
        }
        vm.onRoleSelected = function(item) {
            vm.findBy();
        }

        vm.findBy = function() {
            if (vm.searchDto == null) {
                vm.searchDto = {};
            }
            if (vm.textSearch != null && vm.textSearch != "") {
                vm.searchDto.text = vm.textSearch;
            } else {
                vm.searchDto.text = null;
            }

            vm.searchDto.pageIndex = vm.pageIndex ? vm.pageIndex : 1;
            vm.searchDto.pageSize = vm.pageSize ? vm.pageSize : 25;
            service.searchByPage(vm.searchDto).then(function(data) {
                // debugger
                if (vm.isSdah || vm.isSdahView) {
                    service.getAdministrativeUnitDtoByLoginUser().then(function(res) {
                        // debugger
                        var fullList = data.content;
                        var userAdminUnit = res[0];
                        var subList = fullList.filter(x => x.adminUnit.id == (userAdminUnit.parentId != null ? userAdminUnit.parentId : userAdminUnit.id));
                        vm.administrativeUnitEditables = subList;
                        vm.bsTableControl.options.data = vm.administrativeUnitEditables;
                        vm.bsTableControl.options.totalRows = data.totalElements;
                    });
                } else {
                    vm.administrativeUnitEditables = data.content;
                    vm.bsTableControl.options.data = vm.administrativeUnitEditables;
                    vm.bsTableControl.options.totalRows = data.totalElements;
                }

            });
        }
        vm.enterSearch = function() {
            if (event.keyCode == 13) { //Phím Enter
                vm.pageIndex = 1;
                vm.findBy();
            }
        };

        /**
         * Get all roles
         */
        service.getRolesByAccount().then(function(data) {
            if (data && data.length > 0) {
                vm.roles = data;
                vm.roleSdahs = [];

            } else {
                vm.roles = [];
                vm.roleSdahs = [];
            }
        });

        vm.findFmsAdmin = function() {
                service.getPageSearchFarm(vm.searchDtoFarm, vm.pageIndexFarm, vm.pageSizeFarm).then(function(data) {
                    vm.listFmsAdmin = data.content;
                    vm.bsTableControlFmsAdmin.options.data = vm.fmsAdmin;
                    vm.bsTableControlFmsAdmin.options.totalRows = data.totalElements;

                });
            }
            //vm.findFmsAdmin();
            //--------------Popup FmsAdmin----------//
        vm.pageIndexFmsAdmin = 1, vm.pageSizeFmsAdmin = 10;
        vm.subModalInstance = {};

        //vm.selectedFmsAdmins=[];
        vm.fmsAdminSelected = {};
        vm.listSelectedFmsAdmin = [];
        vm.listFmsAdmin = [];
        // vm.openPopupSelectFmsAdmin=function(){
        //     vm.searchFmsAdminDto={};
        //     vm.listFmsAdmin=[];
        //     debugger
        //     service.getFmsAdmin(vm.searchFmsAdminDto,1,100000).then(function (data){
        //         //debugger
        //         if(!data){
        //             toastr.warning("Không có dữ liệu", "Cảnh báo");
        //             return;
        //         }
        //         vm.listFmsAdmin= data;
        //     });
        //     console.log("áđávm",listFmsAdmin);

        //     // vm.modalChooseFmsAdmin = modal.open({
        //     //     animation: true,
        //     //     templateUrl: 'choose_fmsAdmin_modal.html',
        //     //     scope: $scope,
        //     //     backdrop: 'static',
        //     //     //keyboard: false,
        //     //     // windowClass: "customer-modal-lg",
        //     //     size: 'md'
        //     // });

        // };
        // vm.openPopupSelectFmsAdmin();
        vm.bsTableControlFmsAdmin = {
            options: {
                data: vm.listFmsAdmin,
                idField: 'id',
                sortable: true,
                striped: true,
                maintainSelected: true,
                clickToSelect: false,
                showColumns: false,
                singleSelect: true,
                showToggle: false,
                pagination: true,
                pageSize: vm.pageSizeFmsAdmin,
                pageList: [5, 10, 25, 50, 100],
                locale: settings.locale,
                sidePagination: 'server',
                columns: service.getTableDefinitionFmsAdmin(),
                onCheck: function(row, $element) {
                    if (vm.selectedFmsAdmins != null) {
                        vm.selectedFmsAdmins = [];
                    }
                    $scope.$apply(function() {
                        vm.selectedFmsAdmins.push(row);
                    });
                },

                onUncheck: function(row, $element) {

                    if (vm.selectedFmsAdmins != null) {
                        vm.selectedFmsAdmins = [];
                    }
                },

                onPageChange: function(index, pageSize) {
                    vm.pageSizeFmsAdmin = pageSize;
                    vm.pageIndexFmsAdmin = index;

                    vm.findByFmsAdmin();
                }
            }
        };
        // ------------------------------------------Start activity----------------//
        /**
         * Create
         */
        vm.newAdministrativeUnitEditable = function() {
            vm.administrativeUnitEditable = { isNew: true };
            vm.tempRoles = [];
            vm.listSelectedFmsAdmin = [];
            let role = {};
            // role.name = "ROLE_ADMIN";
            // vm.tempRoles.push(role);
            vm.modalInstance = modal.open({
                animation: true,
                templateUrl: 'edit_modal.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'lg'

            });
        };
        /**
         * Edit 
         *
         * @param administrativeUnitEditableId
         */

        $scope.editAdministrativeUnitEditable = function(administrativeUnitEditableId) {

            service.getById(administrativeUnitEditableId).then(function(data) {
                if (data && data.id) {
                    vm.administrativeUnitEditable = data;
                    vm.listSelectedFmsAdmin = [];
                    vm.administrativeUnitEditable.isNew = false;
                    vm.tempRoles = [];
                    var arr = [];
                    arr = vm.administrativeUnitEditable.roles.split(',');;
                    for (var item of arr) {
                        let role = {};
                        role.name = item;
                        vm.tempRoles.push(role);
                    }
                    vm.modalInstance = modal.open({
                        animation: true,
                        templateUrl: 'edit_modal.html',
                        scope: $scope,
                        size: 'lg'
                    });
                }
            });
        };
        vm.editable = {
            questionText: "Có thể chỉnh sửa",
            choices: [{
                id: 1,
                text: "Có",
                isEditable: "true"
            }, {
                id: 2,
                text: "Không",
                isEditable: "false"
            }]
        };

        /*
            Delete
        */
        $scope.deleteAdministrativeUnitEditableRecord = function(id) {
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

        vm.confirmDeleteRecord = function() {
            if (vm.recordId) {
                service.deleteById(vm.recordId, function success(data) {
                    toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
                    //vm.getUsers();	
                    vm.findBy();
                    //vm.getPageFarm();
                    vm.modalConfirmDelete.close();
                    vm.selectedAdministrativeUnitEditables = [];
                }, function failure(error) {
                    toastr.error($translate.instant("toastr.delFail"), $translate.instant("toastr.error"));
                    vm.modalConfirmDelete.close();
                });
            }
        }


        vm.deleteAdministrativeUnitEditables = function() {
            if (vm.selectedUsers.length == 0) {
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
        vm.confirmDelete = function() {
            vm.pageIndex = 1;
            var ids = vm.selectedUsers.map(function(element) {
                return element.id;
            });
            service.deleteAdministrativeUnitEditables(ids, function success(data) {
                //vm.getUsers();
                vm.findBy();
                vm.modalConfirmDelete.close();
                vm.selectedUsers = [];
            }, function failure(error) {
                console.log(error);
            });
        }

        // search 
        vm.textSearch = '';

        vm.searchByCode = function() {
            vm.textSearch = String(vm.textSearch).trim();
            vm.pageIndex = 1;
            vm.findBy();
        }

        vm.bsTableControl = {
            options: {
                data: vm.administrativeUnitEditables,
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
                        vm.selectedAdministrativeUnitEditables.push(row);

                        // bsTableAPI('bsTableControl', 'uncheckBy', { field: 'username', values: ['admin'] });

                        // vm.selectedUsers.push(row);
                    });
                },
                onCheckAll: function(rows) {
                    $scope.$apply(function() {
                        angular.forEach(rows, function(row) {
                            vm.selectedAdministrativeUnitEditables.push(row);
                            // if (row.username && row.username != 'admin') {
                            //     vm.selectedUsers.push(row);
                            // } else {
                            //     bsTableAPI('bsTableControl', 'uncheckBy', { field: 'username', values: ['admin'] });
                            // }
                        });
                        // vm.selectedUsers = rows;
                    });
                },
                onUncheck: function(row, $element) {
                    var index = utils.indexOf(row, vm.selectedAdministrativeUnitEditables);
                    if (index >= 0) {
                        $scope.$apply(function() {
                            vm.selectedAdministrativeUnitEditables.splice(index, 1);
                        });
                    }
                },
                onUncheckAll: function(rows) {
                    $scope.$apply(function() {
                        vm.selectedAdministrativeUnitEditables = [];
                    });
                },
                onPageChange: function(index, pageSize) {
                    vm.pageSize = pageSize;
                    vm.pageIndex = index;
                    vm.findBy();
                    //vm.getUsers();
                }
            }
        };

        //export
        $scope.myBlobObject = undefined;
        $scope.getFile = function() {
            console.log('download started, you can show a wating animation');

            vm.searchAdministrativeUnitEditableDto = {};
            // if (vm.textSearch != null && vm.textSearch != "") {
            //     vm.searchAdministrativeUnitEditableDto.nameCode = vm.textSearch;
            // }
            // else {
            //     vm.searchUserDto.nameCode = null;
            // }
            // if (vm.province != null) {   
            //     vm.searchUserDto.administrativeUnitId = vm.province.id;
            // }
            // if (vm.district != null) {   
            //     vm.searchUserDto.administrativeUnitId = vm.district.id;
            // }
            // if (vm.ward != null) {   
            //     vm.searchUserDto.administrativeUnitId = vm.ward.id;
            // }
            // if (vm.filterRole != null) {   
            //     vm.searchUserDto.roleId = vm.filterRole.id;
            // }

            service.getStream(vm.searchUserDto)
                .then(function(data) { //is important that the data was returned as Aray Buffer
                    console.log('Stream download complete, stop animation!');
                    $scope.myBlobObject = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
                }, function(fail) {
                    console.log('Download Error, stop animation and show error message');
                    $scope.myBlobObject = [];
                });


        };

        vm.selectRoles = () => {
            debugger
            vm.selectedCity = [];
            vm.selectedDist = [];
            vm.administrativeUnitEditable.roles = "";
            vm.arrRoles = vm.tempRoles;
            var str = "";
            for (var x in vm.arrRoles) {
                str += vm.arrRoles[x].name;
                if (x < vm.arrRoles.length - 1) {
                    str += ",";
                }

            }
            vm.administrativeUnitEditable.roles = str;
            console.log("roles after select:" + vm.administrativeUnitEditable.roles);
        }
        vm.selectFmsAdmin = function(item) {
            debugger
            vm.administrativeUnitEditable.adminUnit = item;
        }

        vm.selectListFmsAdmin = function(item) {

        }

        vm.saveAdministrativeUnitEditable = function() {
            var str = "";
            for (var x in vm.tempRoles) {
                str += vm.tempRoles[x].name;
                if (x < vm.tempRoles.length - 1) {
                    str += ",";
                }
            }
            vm.administrativeUnitEditable.roles = str;
            console.log(vm.tempRoles);
            console.log("roles:" + vm.administrativeUnitEditable.roles);
            if (vm.administrativeUnitEditable && vm.administrativeUnitEditable.adminUnit == null && vm.listSelectedFmsAdmin.length == 0) {
                // toastr.warning($translate.instant("user.emptyName"), $translate.instant("toastr.warning"));
                toastr.warning("Đơn vị hành chính không được để trống", "Thông báo");
                return;
            }
            if (vm.administrativeUnitEditable && vm.administrativeUnitEditable.roles == "") {
                toastr.warning("Role không được để trống", "Thông báo");
                return;
            }
            // if(vm.administrativeUnitEditable && vm.administrativeUnitEditable.quater == null){
            //     toastr.warning("Quý không được để trống","Thông báo");
            //     return;
            // }
            if (vm.administrativeUnitEditable && vm.administrativeUnitEditable.year == null) {
                toastr.warning("Năm không được để trống", "Thông báo");
                return;
            }
            if (vm.administrativeUnitEditable && vm.administrativeUnitEditable.quater != null && (vm.administrativeUnitEditable.quater < 1 || vm.administrativeUnitEditable.quater > 4)) {
                toastr.warning("Quý phải có giá trị từ 1 đến 4", "Thông báo");
                return;
            }
            if (vm.administrativeUnitEditable && vm.administrativeUnitEditable.year < 1900 || vm.administrativeUnitEditable.year > new Date().getFullYear()) {
                toastr.warning("Năm không phù hợp", "Thông báo");
                return;
            }

            //Check trung
            //Save
            if (vm.administrativeUnitEditable.isNew == false) {
                service.saveOrUpdate(vm.administrativeUnitEditable).then(
                        function(data) {
                            if (typeof data === 'object') {
                                toastr.success($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                                vm.findBy();
                            } else {
                                toastr.warning("Bạn phải là ngưởi tạo bản ghi này", "Thông báo");
                                vm.findBy();
                            }
                        }
                    ),
                    function errorCallback(response) {
                        toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                    }
            } else {
                var arr = [];
                debugger
                vm.administrativeUnitEditable.listSelectedAdminUnit = [];
                vm.administrativeUnitEditable.listSelectedAdminUnit = arr.concat(vm.listSelectedFmsAdmin);
                service.saveOrUpdate(vm.administrativeUnitEditable).then(
                        function(data) {
                            toastr.success($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                            vm.findBy();
                        }
                    ),
                    function errorCallback(response) {
                        toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                    }
            }

            if (vm.modalInstance) {
                vm.modalInstance.close();
            }
        }
        vm.onSelectIsEditable = function(choice) {
            vm.administrativeUnitEditable.editable = choice;
        }
    }

})();