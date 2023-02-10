/**
 * Created by bizic on 28/8/2016.
 */
(function() {
    'use strict';

    angular.module('Education.User').controller('UserController', UserController);

    UserController.$inject = [
        '$rootScope',
        '$scope',
        '$timeout',
        'settings',
        '$uibModal',
        'toastr',
        'blockUI',
        'bsTableAPI',
        'Utilities',
        'UserService',
        '$translate',
    ];
    angular.module('Education.User').directive('fileDownload', function() {
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

    function UserController($rootScope, $scope, $timeout, settings, modal, toastr, blockUI, bsTableAPI, utils, service, $translate) {
        $scope.$on('$viewContentLoaded', function() {
            // initialize core components
            App.initAjax();
        });

        // set sidebar closed and body solid layout mode
        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        var vm = this;

        vm.user = {};
        vm.users = [];
        vm.selectedUsers = [];

        vm.roles = [];
        vm.groups = [];

        vm.administrativeUnits = [];

        // UI
        vm.modalInstance = {};

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
                console.log("a",item);
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
            });
        }


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


        /**
         * Get a list of users
         */
        vm.getUsers = function() {
            // service.getUsers(vm.pageIndex, vm.pageSize).then(function (data) {
            //     vm.users = data.content;
            //     vm.bsTableControl.options.data = vm.users;
            //     vm.bsTableControl.options.totalRows = data.totalElements;
            // });
            vm.findBy();
        };

        vm.findBy = function() {
            if (vm.searchDto == null) {
                vm.searchDto = {};
            }
            if (vm.textSearch != null && vm.textSearch != "") {
                vm.searchDto.text = vm.textSearch;
            } else {
                vm.searchDto.text = null;
            }
            if (vm.ward != null) {
                vm.searchDto.administrativeUnitId = vm.ward.id;
            } else if (vm.district != null) {
                vm.searchDto.administrativeUnitId = vm.district.id;
            } else if (vm.province != null) {
                vm.searchDto.administrativeUnitId = vm.province.id;
            } else {
                vm.searchDto.administrativeUnitId = null;
            }
            if (vm.filterRole != null) {
                vm.searchDto.roleId = vm.filterRole.id;
            } else {
                vm.searchDto.roleId = null;
            }
            vm.searchDto.pageIndex = vm.pageIndex ? vm.pageIndex : 1;
            vm.searchDto.pageSize = vm.pageSize ? vm.pageSize : 25;
            service.searchByPage(vm.searchDto).then(function(data) {
                vm.users = data.content;
                vm.bsTableControl.options.data = vm.users;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }
        vm.enterSearch = function() {

            if (event.keyCode == 13) {
                vm.pageIndex = 1; //Phím Enter
                vm.findBy();
            }
        };
        vm.getUsers();

        /**
         * Get all roles
         */
        service.getRolesByAccount().then(function(data) {
            if (data && data.length > 0) {
                vm.roles = data;
                vm.roleSdahs = [];
                /*for(var i=0; i<data.length;i++){
					if (data[i]!=null &&  data[i].authority && data[i].authority.toLowerCase() == 'role_district' ) {
                        vm.roleSdahs.push(data[i]);		
                    }
					if (data[i]!=null &&  data[i].authority && data[i].authority.toLowerCase() == 'role_ward' ) {
                        vm.roleSdahs.push(data[i]);		
                    }
				}*/
            } else {
                vm.roles = [];
                vm.roleSdahs = [];
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

        vm.saveUser = function() {
            //thay đổi password
            console.log(vm.changePass);
            if (vm.changePass == true) {
                vm.user.changePass = true;
            }
            if (!vm.user.person) {
                vm.user.person = {};
            }

            if (vm.user && vm.user.person && vm.user.person.displayName == null) {
                toastr.warning($translate.instant("user.emptyName"), $translate.instant("toastr.warning"));
                return;
            }

            if (!vm.user.id) {
                if (!vm.user.username || vm.user.username.trim().length <= 0) {
                    toastr.warning($translate.instant("user.emptyUsername"), $translate.instant("toastr.warning"));
                    //toastr.error('Vui lòng nhập tên đăng nhập!', 'Thông báo');
                    return;
                }
            }

            if (!vm.user.email || vm.user.email.trim().length <= 0) {
                toastr.warning($translate.instant("user.emptyEmail"), $translate.instant("toastr.warning"));
                //toastr.error('Vui lòng nhập địa chỉ email!', 'Thông báo');
                return;
            }

            if (!vm.user.id) {
                if (!vm.user.password || vm.user.password.trim().length <= 0) {
                    toastr.warning($translate.instant("user.emptyPassword"), $translate.instant("toastr.warning"));
                    //toastr.error('Vui lòng nhập mật khẩu!', 'Thông báo');
                    return;
                }

                if (vm.user.password != vm.user.confirmPassword) {
                    toastr.warning($translate.instant("user.confirmPassword"), $translate.instant("toastr.warning"));
                    // toastr.error('Mật khẩu không khớp nhau!', 'Thông báo');
                    return;
                }
            }

            if (!vm.user.roles || vm.user.roles.length <= 0) {
                toastr.warning($translate.instant("user.emptyRole"), $translate.instant("toastr.warning"));
                //toastr.error('Vui lòng chọn ít nhất một vai trò cho người dùng!', 'Thông báo');
                return;
            }
            if (vm.selectedCity == null && vm.isRole == false) {
                toastr.warning($translate.instant("user.emptyCity"), $translate.instant("toastr.warning"));
                return;
            }
            if (vm.selectedDist == null && vm.isSdah && vm.isRole == false) {
                toastr.warning($translate.instant("user.emptyDistrict"), $translate.instant("toastr.warning"));

                return;
            }

            // Check for duplicate username & email
            service.getUserByUsername(vm.user.username).then(function(data) {
                if (data && data.id) {
                    if (!vm.user.id || (vm.user.id && data.id != vm.user.id)) {
                        toastr.warning($translate.instant("user.exitusername"), $translate.instant("toastr.warning"));
                        //toastr.error('Tên đăng nhập đã tồn tại!', 'Thông báo');
                        return;
                    }
                }

                service.getUserByEmail(vm.user.email).then(function(data2) {
                    if (data2 && data2.id) {
                        toastr.warning($translate.instant("user.exitemail"), $translate.instant("toastr.warning"));
                        //toastr.error('Địa chỉ email đã tồn tại!', 'Thông báo');
                        return;
                    }


                    service.saveUser(vm.user).then(function(data) {
                        toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));

                        // Reload users
                        if (vm.selectedWard != null && vm.selectedWard.length > 0) {
                            vm.administrativeUnits = [];
                            vm.administrativeUnits.push(vm.selectedWard);
                        } else if (vm.selectedDist != null && vm.selectedDist.length > 0) {
                            vm.administrativeUnits = [];
                            for (var i = 0; i < vm.selectedDist.length; i++) {
                                var item = vm.selectedDist[i];
                                vm.administrativeUnits.push(item);
                            }

                        } else if (vm.selectedCity != null) {
                            vm.administrativeUnits = [];
                            // vm.administrativeUnits.push(...vm.selectedCity);
                            vm.administrativeUnits = vm.selectedCity;
                        } else {
                            vm.administrativeUnits = [];
                        }
                        service.saveAdministrativeUnit(data.id, vm.administrativeUnits).then(function(data) {
                            //vm.getUsers();	 
                            vm.findBy();
                        });

                    }, function errorCallback(response) {
                        toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                    });

                    // Close the modal
                    if (vm.modalInstance) {
                        vm.modalInstance.close();
                    }
                });
            });
            vm.changePass = false;
        };

        /**
         * Create a new user
         */
        vm.newUser = function() {
            vm.user = { isNew: true };
            vm.selectedCity = null;
            vm.administrativeUnits = [];
            vm.selectedDist = null;
            vm.selectedWard = null;
            vm.adminstrativeUnits_Dist = [];
            vm.adminstrativeUnits_Wards = [];

            vm.modalInstance = modal.open({
                animation: true,
                templateUrl: 'edit_modal.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'lg'

            });
        };

        function getDataByCombobox(au) {
            if (!au || au.length == 0) return;

            let checkCity = au.filter(a => a.parentDto != null).length == 0 ? true : false;
            if (checkCity) {
                vm.selectedCity = au;
                // nếu là tk cấp tỉnh
                if (au.length == 1) {
                    service.getAllByParentId(au[0].id).then(function(data) {
                        if (data != null && data.length > 0) {
                            vm.adminstrativeUnits_Dist = data;
                        }
                    })
                }
                // nếu là tài khoản liên tỉnh thì tạm thời ko cho chọn huyện
                if (au.length > 1) {
                    vm.adminstrativeUnits_Dist = [];
                }

                return;
            }

            let checkDist = au.filter(a => a.parentDto.parentDto != null).length == 0 ? true : false;

            if (checkDist) {
                // nếu là tài khoản cấp huyện
                if (!vm.selectedCity) vm.selectedCity = [];
                vm.selectedCity.push(au[0].parentDto);
                vm.selectedDist = au;
                service.getAllByParentId(au[0].parentDto.id).then(function(data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                    }
                })
            }

        }

        /**
         * Edit an existing user
         *
         * @param userId
         */
        $scope.editUser = function(userId) {
            vm.selectedCity = [];
            vm.selectedDist = [];
            vm.selectedWard = [];
            vm.adminstrativeUnits_Dist = [];
            vm.adminstrativeUnits_Wards = [];
            vm.administrativeUnits = [];
            service.getUser(userId).then(function(data) {
                if (data && data.id) {
                    service.getAdministrativeUnitByUserId(data.id).then(function(data) {
                        vm.aus = data;
                        if (vm.aus != null && vm.aus.length > 0) {
                            getDataByCombobox(vm.aus);
                        }
                    });
                    vm.user = data;
                    vm.user.isNew = false;

                    vm.modalInstance = modal.open({
                        animation: true,
                        templateUrl: 'edit_modal.html',
                        scope: $scope,
                        size: 'lg'
                    });
                }
            });
            vm.changePass = false;
        };


        $scope.deleteUserRecord = function(id) {
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
                service.deleteUserById(vm.recordId, function success(data) {
                    toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
                    //vm.getUsers();	
                    vm.findBy();
                    //vm.getPageFarm();
                    vm.modalConfirmDelete.close();
                    vm.selectedUsers = [];
                }, function failure(error) {
                    toastr.error($translate.instant("toastr.delFail"), $translate.instant("toastr.error"));
                    vm.modalConfirmDelete.close();
                });
            }
        }


        vm.deleteUsers = function() {
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
            service.deleteUsers(ids, function success(data) {
                //vm.getUsers();
                vm.findBy();
                vm.modalConfirmDelete.close();
                vm.selectedUsers = [];
            }, function failure(error) {
                console.log(error);
            });
        }

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
                vm.users = data.content;
                vm.bsTableControl.options.data = vm.users;
                vm.bsTableControl.options.totalRows = data.totalElements;
                console.log(data);
            });
        }

        vm.searchByCode = function() {
            vm.textSearch = String(vm.textSearch).trim();
            vm.pageIndex = 1;
            /*if(vm.textSearch != ''){
                findUserByUserName(vm.textSearch,vm.pageIndex,vm.pageSize);
            }
            if(vm.textSearch == ''){
                vm.getUsers();
            }*/
            vm.findBy();
        }

        vm.bsTableControl = {
            options: {
                data: vm.users,
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
                            vm.selectedUsers.push(row);
                        } else {
                            bsTableAPI('bsTableControl', 'uncheckBy', { field: 'username', values: ['admin'] });
                        }
                        // vm.selectedUsers.push(row);
                    });
                },
                onCheckAll: function(rows) {
                    $scope.$apply(function() {
                        angular.forEach(rows, function(row) {
                            if (row.username && row.username != 'admin') {
                                vm.selectedUsers.push(row);
                            } else {
                                bsTableAPI('bsTableControl', 'uncheckBy', { field: 'username', values: ['admin'] });
                            }
                        });
                        // vm.selectedUsers = rows;
                    });
                },
                onUncheck: function(row, $element) {
                    var index = utils.indexOf(row, vm.selectedUsers);
                    if (index >= 0) {
                        $scope.$apply(function() {
                            vm.selectedUsers.splice(index, 1);
                        });
                    }
                },
                onUncheckAll: function(rows) {
                    $scope.$apply(function() {
                        vm.selectedUsers = [];
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

            vm.searchUserDto = {};
            if (vm.textSearch != null && vm.textSearch != "") {
                vm.searchUserDto.nameCode = vm.textSearch;
            } else {
                vm.searchUserDto.nameCode = null;
            }
            if (vm.province != null) {
                vm.searchUserDto.administrativeUnitId = vm.province.id;
            }
            if (vm.district != null) {
                vm.searchUserDto.administrativeUnitId = vm.district.id;
            }
            if (vm.ward != null) {
                vm.searchUserDto.administrativeUnitId = vm.ward.id;
            }
            if (vm.filterRole != null) {
                vm.searchUserDto.roleId = vm.filterRole.id;
            }

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
            vm.selectedCity = [];
            vm.selectedDist = [];
        }

        /*$scope.$watch('vm.province',function(){
            vm.district = null;
            vm.ward = null;
            vm.findBy();
        });
        $scope.$watch('vm.district',function(){
            vm.ward = null;
            vm.findBy();
        });
        $scope.$watch('vm.ward',function(){
            vm.findBy();
        });
        $scope.$watch('vm.filterRole',function(){
            vm.findBy();
        });*/


    }

})();