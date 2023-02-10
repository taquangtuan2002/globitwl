/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.AnimalCertificate').controller('AnimalCertificateController', AnimalCertificateController);

    AnimalCertificateController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'AnimalCertificateService',
        '$uibModal',
        'toastr',
        '$state',
        'blockUI',
        '$stateParams',
        'Utilities',
        '$translate',
        'FileSaver',
        'FarmService',
        'AnimalService',
        'UserService',
        'FmsAdministrativeService'
    ];

    function AnimalCertificateController($rootScope, $scope, $http, $timeout, settings, service, modal, toastr, $state, blockUI, $stateParams, utils, $translate, FileSaver, farmService, animalService, userService, fmsAdministrativeService) {
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
        vm.pageSize = 10;
        vm.animalCertificates = [];
        vm.animals = [];
        vm.animals2 = [];
        vm.modalAnimalCertificate = {};
        vm.animalCertificate = { isNew: false, isCheckFarm: false };
        vm.modalConfirmDelete = {};
        vm.selectedAnimalCertificate = [];
        vm.count = 0;
        vm.isFamer = false;
        vm.searchDto = {};
        vm.searchFarmDto = {};
        vm.searchAnimalDto = {
            vnList06s: ['IIB']
        };
        vm.viewCheckDuplicateCode = {};
        vm.tempCode = '';
        vm.animalCertDetailTemp = {};
        vm.listContentProvided = [];
        vm.listSigner = [];
        vm.isRole = false;
        vm.idACNew = null;
        vm.isViewDetail = false;

        //------Start--Phân quyền theo user đăng nhập-----------
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
                        vm.isSdah = true;
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
            if (vm.isRole) { //trường hợp admin và dlp thì được xem tất cả các cơ sở chăn nuôi
                vm.getAllCity();
                vm.findBy();
            } else { // trường hợp này thì phân quyền theo user
                if (vm.isRole == false) {
                    getAdministrativeUnitDtoByLoginUser();
                }

            }

            animalService.getPageSearchAnimal(vm.searchAnimalDto, vm.pageIndex, 100000).then(function (data) {
                vm.animals = data.content;
            });

            service.getAdminstrativeUnitsCity().then(function (data) {
                vm.adminstrativeUnitsCity = data;
            });
            vm.listContentProvided = [
                'Cấp mới',
                'Cấp lại',
                'Bổ sung loài'
            ];

            vm.listSigner = [
                'Chi cục trưởng',
                'Phó chi cục trưởng'
            ];
            blockUI.stop();

        });

        vm.getAllCity = function () {
            fmsAdministrativeService.getAllCity().then(function (data) {
                vm.adminstrativeUnits_City = data;
            });
        }

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
            if (vm.searchFarmDto == null) {
                vm.searchFarmDto = {};
            }
            if (administrativeUnit.parentDto == null) {
                // Có tỉnh
                vm.searchFarmDto.province = administrativeUnit.id;
                if (vm.adminstrativeUnits_City == null)
                    vm.adminstrativeUnits_City = [];
                vm.adminstrativeUnits_City.push(administrativeUnit);
                service.getAllByParentId(vm.searchFarmDto.province).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                    }
                });
            } else if (administrativeUnit.parentDto != null && administrativeUnit.parentDto.parentDto == null) {
                // Có tỉnh, huyện
                vm.searchFarmDto.district = administrativeUnit.id;
                vm.searchFarmDto.province = administrativeUnit.parentDto.id;
                vm.adminstrativeUnits_City = [];
                vm.adminstrativeUnits_City.push(administrativeUnit.parentDto);
                vm.adminstrativeUnits_Dist = [];
                if (vm.adminstrativeUnits_Dist == null)
                    vm.adminstrativeUnits_Dist = [];
                vm.adminstrativeUnits_Dist.push(administrativeUnit);
                service.getAllByParentId(vm.searchFarmDto.province).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                    }
                });
                service.getAllByParentId(vm.searchFarmDto.district).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Wards = data;
                    }
                });
            } else if (administrativeUnit.parentDto != null && administrativeUnit.parentDto.parentDto != null) {
                // Có tỉnh, huyên, xã
                vm.searchFarmDto.ward = administrativeUnit.id;
                vm.searchFarmDto.dist = administrativeUnit.parentDto.id;
                vm.searchFarmDto.province = administrativeUnit.parentDto.parentDto.id;
                if (vm.adminstrativeUnits_City == null) vm.adminstrativeUnits_City = [];
                vm.adminstrativeUnits_City.push(administrativeUnit.parentDto.parentDto);
                if (vm.adminstrativeUnits_Dist == null) vm.adminstrativeUnits_Dist = [];
                vm.adminstrativeUnits_Dist.push(administrativeUnit.parentDto);
                if (vm.adminstrativeUnits_Wards == null)
                    vm.adminstrativeUnits_Wards = [];
                vm.adminstrativeUnits_Wards.push(administrativeUnit);
            }
            vm.findByFarm();
        }


        vm.searchByPage = function () {
            // service.getAllAnimalCertificate(vm.pageIndex, vm.pageSize).then(function (data) {
            //     vm.animalCertificates = data.content;
            //     vm.bsTableControl.options.data = vm.animalCertificates;
            //     vm.bsTableControl.options.totalRows = data.totalElements;
            // });
            vm.searchDto.pageIndex = vm.pageIndex;
            vm.searchDto.pageSize = vm.pageSize;
            service.searchByPage(vm.searchDto).then(function (data) {
                vm.animalCertificates = data.content;
                vm.bsTableControl.options.data = vm.animalCertificates;
                vm.bsTableControl.options.totalRows = data.totalElements;
            })
        }
        vm.searchByPage();
        /** bussiness */

        vm.bsTableControl = {
            options: {
                data: vm.animalCertificates,
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
                        vm.selectedAnimalCertificate.push(row);
                    });
                },
                onCheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedAnimalCertificate = rows;
                    });
                },
                onUncheck: function (row, $element) {
                    var index = utils.indexOf(row, vm.selectedAnimalCertificate);
                    if (index >= 0) {
                        $scope.$apply(function () {
                            vm.selectedAnimalCertificate.splice(index, 1);
                        });

                    }
                },
                onUncheckAll: function (rows) {
                    $scope.$apply(function () {
                        vm.selectedAnimalCertificate = [];
                    });
                },
                onPageChange: function (index, pageSize) {
                    vm.pageSize = pageSize;
                    vm.pageIndex = index;
                    // vm.findBy();
                    vm.searchByPage();
                }
            }
        };

        vm.getFileWord = function (id) {
            var object = {};
            object.id = id;
            console.log('download started, you can show a wating animation');
            service.getStreamDocument(object)
                .then(function (data) { //is important that the data was returned as Aray Buffer
                    console.log('Stream download complete, stop animation!');
                    //vm.myBlobObjectWord = new Blob([data], { type: 'application/msword' });
                    var file = new Blob([data], { type: 'application/msword' });
                    FileSaver.saveAs(file, "GiayChungNhanTraiNuoi.doc");
                }, function (fail) {
                    console.log('Download Error, stop animation and show error message');
                    vm.myBlobObjectWord = [];
                });

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

            // service.getPageSearchAnimalCertificate(vm.searchDto, vm.pageIndex, vm.pageSize).then(function (data) {
            //     vm.animalCertificates = data.content;
            //     vm.bsTableControl.options.data = vm.animalCertificates;
            //     vm.bsTableControl.options.totalRows = data.totalElements;
            // });
            vm.searchDto.pageIndex = 1;
            vm.searchDto.pageSize = 10;
            service.searchByPage(vm.searchDto).then(function (data) {
                vm.animalCertificates = data.content;
                vm.bsTableControl.options.data = vm.animalCertificates;
                vm.bsTableControl.options.totalRows = data.totalElements;
            });
        }


        // Mở pop up thêm
        vm.createAnimalCertificate = function () {
            vm.animalCertificate.isNew = false;
            if (vm.isViewDetail = true) {
                vm.isViewDetail = false;
            }
            vm.animalCertificate = {
                isNew: true,
                isCheckFarm: true,
                recipientSecond: 'Cơ quan thẩm quyền CITES',
                recipientFourth: 'Lưu BTTN, TTPC, VP',
                // organizationName: 'Chi cục kiểm lâm'
            };
            vm.searchFarmDto = {},
            vm.selectedCity = null;
            vm.selectedDist = null;
            vm.selectedWard = null;
            vm.modalAnimalCertificate = modal.open({
                animation: true,
                templateUrl: 'animal_certificate_info.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'lg'
            });
            if (vm.animalCertDetailTemp.original == null) {
                vm.animalCertDetailTemp.original = "Từ cơ sở nuôi hợp pháp của ông/bà ..., địa chỉ ..., mã số ..."
            }
            // Tên đơn vị cấp giấy chứng nhận.
            if (vm.animalCertificate.organizationName == null) {
                // service.getCurrentUser().then(function (data) {
                //     vm.idUserAttachments = data.id;
                //     service.getUserLogined(vm.idUserAttachments).then(function (data) {
                //         if (data != null && data.length > 0) {
                //             if (data[0].administrativeOrganization.parentDto != null) {
                //                 vm.animalCertificate.organizationName = data[0].administrativeOrganization.parentDto[0].name;
                //             }
                //             else {
                //                 vm.animalCertificate.organizationName = data[0].administrativeOrganization.name;
                //             }
                //         }


                //     })
                // })
                vm.animalCertificate.organizationName = "Chi cục kiểm lâm";
            }
            // vm.animalCertificate.organizationProvinceName = "Sở nông nghiệp và PTNTT Tỉnh/TP";
            if (vm.animalCertificate.organizationProvinceName == null) {
                service.getCurrentUser().then(function (data) {
                    vm.idUserAttachments = data.id;
                    // service.getUserLogined(vm.idUserAttachments).then(function (data) {
                    //     if (data != null && data.length > 0) {
                    //         vm.animalCertificate.organizationProvinceName = "Sở nông nghiệp và PTNTT " + data[0].administrativeOrganization.fmsOrganiration[0].name;
                    //         vm.animalCertificate.provinceName = data[0].administrativeOrganization.fmsOrganiration[0].name;

                    //     }
                    // })
                    userService.getAdministrativeUnitByUserId(vm.idUserAttachments).then(function (data) {
                        if(data != null && data.length > 0) {
                            if(data[0].parentDto){
                                vm.animalCertificate.organizationProvinceName = "Sở nông nghiệp và PTNTT " + data[0].parentDto.name;
                                vm.animalCertificate.provinceName = data[0].parentDto.name;
                                if(vm.animalCertificate.provinceName.substring(0,4) == "Tỉnh"){
                                    vm.animalCertificate.provinceName = vm.animalCertificate.provinceName.substring(5,vm.animalCertificate.provinceName.length);
                                }else{
                                    vm.animalCertificate.provinceName = vm.animalCertificate.provinceName.substring(10,vm.animalCertificate.provinceName.length);
                                }
                            }else{
                                vm.animalCertificate.organizationProvinceName = "Sở nông nghiệp và PTNTT " + data[0].name;
                                vm.animalCertificate.provinceName = data[0].name;
                                if(vm.animalCertificate.provinceName.substring(0,4) == "Tỉnh"){
                                    vm.animalCertificate.provinceName = vm.animalCertificate.provinceName.substring(5,vm.animalCertificate.provinceName.length);
                                }else{
                                    vm.animalCertificate.provinceName = vm.animalCertificate.provinceName.substring(10,vm.animalCertificate.provinceName.length);
                                }
                            }
                        }else{

                        }
                    })
                })
            }
            vm.animalCertificate.recipientThird = "Hạt kiểm lâm";
            // if (vm.animalCertificate.recipientThird == null) {
            //     service.getCurrentUser().then(function (data) {
            //         vm.idUserAttachments = data.id;
            //         service.getUserLogined(vm.idUserAttachments).then(function (data) {
            //             if (data != null && data.length > 0) {
            //                 vm.animalCertificate.recipientThird = data[0].administrativeOrganization.name;
            //             }
            //         })
            //     })
            // }
        };
        //Animal
        // vm.getAllAnimalCertificate();

        // Save
        vm.saveAnimalCertificate = function () {

            if (vm.animalCertificate) {
                if (!vm.animalCertificate.farm) {
                    toastr.warning("Chưa chọn cơ sở nuôi.", "Cảnh báo");
                    return;
                }
                if (!vm.animalCertificate.details) {
                    toastr.warning("Chưa kê khai động vật.", "Cảnh báo");
                    return;
                }
                if (!vm.animalCertificate.typeSigner) {
                    toastr.warning("Chưa chọn chức danh.", "Cảnh báo");
                    return;
                }
                if (vm.animalCertificate.isNew) {
                    service.createAnimalCertificate(vm.animalCertificate).then(function (data) {
                        toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                        vm.findBy();
                        // vm.modalAnimalCertificate.close();
                        vm.selectedAnimalCertificate = [];
                        vm.animalCertDetailTemp = {};
                        vm.searchByPage();
                        vm.idACNew = data.id;
                        vm.animalCertificate.id = data.id;
                        vm.animalCertificate.isNew = false;
                    })
                } else {
                    console.log("update");
                    console.log(vm.animalCertificate);
                    // vm.animalCertificate.code = vm.tempCode;
                    service.updateAnimalCertificate(vm.animalCertificate.id, vm.animalCertificate).then(function (data) {
                        toastr.info($translate.instant("toastr.createSuccess"), $translate.instant("toastr.info"));
                        vm.findBy();
                        // vm.modalAnimalCertificate.close();
                        vm.selectedAnimalCertificate = [];
                        vm.animalCertDetailTemp = {};
                        vm.searchByPage();
                        vm.idACNew = data.id;
                        // vm.animalCertificate.id = data.id;
                    }) 
                }
            }
        }

        // Sửa
        $scope.editAnimalCertificate = function (id) {
            if (id) {
                vm.idACNew = null;
                if (vm.isViewDetail = true) {
                    vm.isViewDetail = false;
                }
                // Goi API tìm theo id
                service.getAniamlCertificateById(id).then(function (data) {
                    vm.animalCertificate = data;
                    vm.animalCertificate.isNew = false;
                    vm.animalCertificate.isCheckFarm = true;
                    vm.selectedCity = vm.animalCertificate.farm.administrativeUnit.parentDto.parentDto;
                    vm.selectedDist = vm.animalCertificate.farm.administrativeUnit.parentDto;
                    vm.selectedWard = vm.animalCertificate.farm.administrativeUnit;
                    vm.tempCode = vm.animalCertificate.code;
                });

                // Mở pop up sửa
                vm.modalAnimalCertificate = modal.open({
                    animation: true,
                    templateUrl: 'animal_certificate_info.html',
                    scope: $scope,
                    backdrop: 'static',
                    keyboard: false,
                    size: 'lg'
                });
            }
        }

        $scope.detailAnimalCertificate = function (id, index) {
            if (id) {
                // Goi API tìm theo id
                service.getAniamlCertificateById(id).then(function (data) {
                    vm.animalCertificate = data;
                    vm.animalCertificate.isNew = false;
                    vm.animalCertificate.isCheckFarm = true;
                    vm.selectedCity = vm.animalCertificate.farm.administrativeUnit.parentDto.parentDto;
                    vm.selectedDist = vm.animalCertificate.farm.administrativeUnit.parentDto;
                    vm.selectedWard = vm.animalCertificate.farm.administrativeUnit;
                    vm.tempCode = vm.animalCertificate.code;
                });
                if (index == 2) {
                    vm.isViewDetail = true;
                } else {
                    vm.isViewDetail = false;
                }
                // Mở pop up sửa
                vm.modalAnimalCertificate = modal.open({
                    animation: true,
                    templateUrl: 'animal_certificate_info.html',
                    scope: $scope,
                    backdrop: 'static',
                    keyboard: false,
                    size: 'lg'
                });
            }
        }

        // Mở pop up xác nhận xóa
        $scope.deleteAnimalCertificateRecord = function (id) {
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

        // Gọi API Xóa
        vm.confirmDeleteRecord = function () {
            if (vm.recordId) {
                service.deleteAnimalCertificate(vm.recordId).then(function (data) {
                    if (data != null && data.code == "-1") {
                        toastr.warning($translate.instant("toastr.del4"), $translate.instant("toastr.warning"));
                    }
                    else {
                        toastr.info($translate.instant("toastr.del3"), $translate.instant("toastr.info"));
                    }
                    vm.findBy();
                    vm.modalConfirmDelete.close();
                    vm.selectedAnimalCertificate = [];
                });
            }
        }

        function checkDuplicateCode(code, type, action) { //type: 1 -> save; 2 -> edit;   action: 1 -> just check code; 2 -> save or edit
            vm.tempCode = vm.animalCertificate.code;
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
                            toastr.warning("Mã bị trùng", "Cảnh báo");
                        }
                        if (vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == false) {
                            vm.saveAnimalCertificate();
                        }
                    }
                    if (type == 2) {
                        if (vm.animalCertificate.details.length <= 0) {
                            toastr.warning("Chưa kê khai động vật", "Cảnh báo");
                            return;
                        }
                        if (vm.tempCode.toLowerCase().trim() != code.toLowerCase().trim()) {
                            service.checkDuplicateCode(vm.tempCode).then(function (data) {
                                vm.viewCheckDuplicateCode = data
                                if (vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == true) {
                                    toastr.warning($translate.instant("checkCode.codeDuplicate"), $translate.instant("toastr.warning"));
                                }
                                if (vm.viewCheckDuplicateCode != null && vm.viewCheckDuplicateCode.duplicate == false) {
                                    vm.animalCertificate.code = vm.tempCode.trim();
                                    vm.saveAnimalCertificate();
                                }
                            });
                        } else {
                            vm.animalCertificate.code = vm.tempCode.trim();
                            vm.saveAnimalCertificate();
                        }
                    }
                }
            });
        }

        function validate() {
            if (!vm.animalCertificate.code) {
                toastr.warning($translate.instant("certificate.emptyCode"), $translate.instant("toastr.warning"));
                return false;
            }
            // if (vm.animalCertificate.isNew == false && (vm.tempCode == null || vm.tempCode == "")) {
            //     toastr.warning($translate.instant("certificate.emptyCode"), $translate.instant("toastr.warning"));
            //     return false;
            // }
            return true;
        }

        vm.checkDuplicateCode = function (type, action) {
            console.log(vm.animalCertificate);
            if (validate()) {
                checkDuplicateCode(vm.animalCertificate.code, type, action);

            }
        }



        //--------------Popup Farm----------//
        vm.pageIndexFarm = 1, vm.pageSizeFarm = 10;
        vm.subModalInstance = {};

        vm.selectedFarms = [];
        vm.farmSelected = {};
        vm.showPopupFarm = function () {
            vm.subModalInstance = modal.open({
                animation: true,
                templateUrl: 'choose_farm_modal.html',
                scope: $scope,
                size: 'lg'
            });
            vm.code = null;
            vm.selectedFarms = [];
            vm.farmSelected = {};
            //lấy danh sách cơ sở chăn nuôi phân theo quyền
            if (vm.isRole) { //trường hợp admin và dlp thì được xem tất cả các cơ sở chăn nuôi
                vm.findByFarm();
            } else { // trường hợp này thì phân quyền cơ sở chăn nuôi theo user
                if (vm.isRole == false) {
                    getAdministrativeUnitDtoByLoginUser();

                }

            }
            vm.subModalInstance.result.then(function (confirm) {
                if (confirm == 'yes') {

                }
            }, function () {
                vm.textSearch = null;
                console.log("cancel");
            });
        }


        vm.bsTableControlFarm = {
            options: {
                data: vm.farms,
                idField: 'id',
                sortable: true,
                striped: true,
                maintainSelected: true,
                clickToSelect: false,
                showColumns: false,
                singleSelect: true,
                showToggle: false,
                pagination: true,
                pageSize: vm.pageSizeFarm,
                pageList: [5, 10, 25, 50, 100],
                locale: settings.locale,
                sidePagination: 'server',
                columns: service.getTableDefinitionFarm(),
                onCheck: function (row, $element) {
                    if (vm.selectedFarms != null) {
                        vm.selectedFarms = [];
                    }
                    $scope.$apply(function () {
                        vm.selectedFarms.push(row);
                    });
                },

                onUncheck: function (row, $element) {

                    if (vm.selectedFarms != null) {
                        vm.selectedFarms = [];
                    }
                },

                onPageChange: function (index, pageSize) {
                    vm.pageSizeFarm = pageSize;
                    vm.pageIndexFarm = index;
                    vm.findByFarm();
                }
            }
        };



        vm.enterSearchFarm = function () {
            // console.log(event.keyCode);
            if (event.keyCode == 13) { //Phím Enter

                vm.searchByFarm();
            }
        };
        vm.searchByFarm = function () {
            vm.pageIndexFarm = 1;
            vm.bsTableControlFarm.state.pageNumber = 1;
            vm.findByFarm();
        };
        vm.findByFarm = function () {
            if (vm.searchFarmDto == null) {
                vm.searchFarmDto = {};
            } else {
                if (vm.searchFarmDto.province != null) {

                }
            }
            if (vm.selectedCity != null && vm.selectedCity != "") {
                vm.searchFarmDto.selectedCity = vm.selectedCity;
            } else {
                vm.searchFarmDto.selectedCity = null;
            }
            if (vm.selectedDist != null && vm.selectedDist != "") {
                vm.searchFarmDto.selectedDist = vm.selectedDist;
            } else {
                vm.searchFarmDto.selectedDist = null;
            }
            if (vm.selectedWard != null && vm.selectedWard != "") {
                vm.searchFarmDto.selectedWard = vm.selectedWard;
            } else {
                vm.searchFarmDto.selectedWard = null;
            }
            if (vm.textSearch != null && vm.textSearch != "") {
                vm.searchFarmDto.nameOrCode = vm.textSearch;
            } else {
                vm.searchFarmDto.nameOrCode = null;
            }

            farmService.getPageSearchFarm(vm.searchFarmDto, vm.pageIndexFarm, vm.pageSizeFarm).then(function (data) {
                vm.farms = data.content;
                vm.bsTableControlFarm.options.data = vm.farms;
                vm.bsTableControlFarm.options.totalRows = data.totalElements;
                // if (vm.isFamer == true && vm.farms != null && vm.farms.length > 0) {
                //     if (vm.importAnimal == null) vm.importAnimal = {};
                //     vm.farmName = vm.farms[0].name;
                //     vm.textSearch = vm.farms[0].code;
                //     vm.importAnimal.farm = vm.farms[0];
                // }
            });
        }

        vm.onFmsadminstrativeUnitCitySelected = function (city) {
            // vm.adminstrativeUnitsCityFrom = [];
            if (city) {
                vm.selectedCityFrom = city;
            } else {
                vm.selectedCityFrom = null;
            }
            vm.selectedDist = null;
            vm.selectedWard = null;
            // vm.bsTableControlFarm.state.pageNumber = 1;

            if (city != null && city.id != null) {
                fmsAdministrativeService.getAllByParentId(city.id).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                    }
                    else {
                        vm.adminstrativeUnits_Dist = [];
                        vm.adminstrativeUnits_Wards = [];
                        vm.searchFarmDto.district = null;
                        vm.selectedDist = null;
                        vm.searchFarmDto.ward = null;
                        vm.selectedWard = null;
                    }
                });
            }
            else {
                vm.searchFarmDto.district = null;
                vm.selectedDist = null;
                vm.searchFarmDto.ward = null;
                vm.selectedWard = null;
                vm.adminstrativeUnits_Dist = [];
                vm.adminstrativeUnits_Wards = [];
            }
            vm.findByFarm();
        }


        vm.onFmsadminstrativeUnitDistSelected = function (district) {
            if (district) {
                // vm.searchDtoFarm.district = district.id;
                vm.selectedDist = null;
            } else {
                vm.selectedDist = null;
            }
            vm.selectedWard = null;
            // vm.bsTableControlFarm.state.pageNumber = 1;
            if (district != null && district.id != null) {
                service.getAllByParentId(district.id).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Wards = data;
                    }
                    else {
                        vm.selectedWard = null;
                        vm.adminstrativeUnits_Wards = [];
                    }
                });
            }
            else {
                vm.adminstrativeUnits_Wards = [];
            }
            vm.findByFarm();
        }
        // vm.onFmsadminstrativeUnitCitySelected = function (city) {
        //     // Clear data combobox
        //     vm.searchFarmDto.district = null;
        //     vm.selectedDist = null;
        //     vm.searchFarmDto.ward = null;
        //     vm.selectedWard = null;
        //     vm.adminstrativeUnits_Dist = [];
        //     vm.adminstrativeUnits_Wards = [];
        //     if (city != null && city.id != null) {
        //         vm.provinceName = city.name; // Hiển thị tên tỉnh lên bảng
        //         fmsAdministrativeService.getChildrenByParentId(city.id).then(function (data) {
        //             if (data != null && data.length > 0) {
        //                 vm.adminstrativeUnits_Dist = data;
        //             }
        //             else {
        //                 vm.searchFarmDto.district = null;
        //                 vm.selectedDist = null;
        //                 vm.searchFarmDto.ward = null;
        //                 vm.selectedWard = null;
        //                 vm.adminstrativeUnits_Dist = [];
        //                 vm.adminstrativeUnits_Wards = [];
        //             }
        //         })
        //     }
        //     else {
        //         vm.searchFarmDto.district = null;
        //         vm.selectedDist = null;
        //         vm.searchFarmDto.ward = null;
        //         vm.selectedWard = null;
        //         vm.adminstrativeUnits_Dist = [];
        //         vm.adminstrativeUnits_Wards = [];
        //     }
        //     vm.findByFarm();
        // }

        // vm.onFmsadminstrativeUnitDistSelected = function (dist) {
        //     // Clear data combobox
        //     vm.searchFarmDto.ward = null;
        //     vm.selectedWard = null;
        //     vm.adminstrativeUnits_Wards = [];
        //     if (dist != null && dist.id != null) {
        //         fmsAdministrativeService.getChildrenByParentId(dist.id).then(function (data) {
        //             if (data != null && data.length > 0) {
        //                 vm.adminstrativeUnits_Wards = data;
        //             }
        //             else {
        //                 vm.searchFarmDto.ward = null;
        //                 vm.selectedWard = null;
        //                 vm.adminstrativeUnits_Wards = [];
        //             }
        //         });
        //     }
        //     else {
        //         vm.searchFarmDto.ward = null;
        //         vm.selectedWard = null;
        //         vm.adminstrativeUnits_Wards = [];
        //     }
        //     vm.findByFarm();
        // }
        vm.onFmsadminstrativeUnitWardsSelected = function (ward) {
            vm.findByFarm();
        }


        var closeModal = function () {
            vm.subModalInstance.close();
        };

        function checkAgreeFarm() {
            if (angular.isUndefined(vm.selectedFarms) || vm.selectedFarms.length == 0) {
                toastr.warning($translate.instant("exportAnimal.notChooseFarm"), $translate.instant("toastr.warning"));
                return false;
            }
            return true;
        }

        vm.agreeFarm = function () {
            
            if (checkAgreeFarm()) {
                vm.farmSelected = vm.selectedFarms[0];
                vm.animalCertificate.farm = vm.farmSelected;
                vm.animalCertificate.recipientFirst = 'Chủ cơ sở ' + vm.animalCertificate.farm.name;
                if (vm.animalCertificate.farm.administrativeUnit != null) {
                    if (vm.animalCertificate.farm.administrativeUnit != null) {
                        vm.selectedWard = vm.animalCertificate.farm.administrativeUnit;
                    }
                    if (vm.animalCertificate.farm.administrativeUnit.parentDto != null) {
                        vm.selectedDist = vm.animalCertificate.farm.administrativeUnit.parentDto;
                    }
                    if (vm.animalCertificate.farm.administrativeUnit.parentDto.parentDto != null) {
                        vm.selectedCity = vm.animalCertificate.farm.administrativeUnit.parentDto.parentDto;
                    }
                    if(vm.selectedCity != null){
                        service.getChildrenByParentId(vm.selectedCity.id).then(function (data) {
                            if (data != null && data.length > 0) {
                                vm.adminstrativeUnits_Dist = data;
                            }
                        });
                    }
                    vm.textSearch = null;
                
                    closeModal();
                }
                
                //
            }
        }

        vm.addAnimalCertDetailTemp = function () {
            if (vm.animalCertDetailTemp.animal) {
                let temp = angular.copy(vm.animalCertDetailTemp);
                if (!vm.animalCertificate.details) {
                    vm.animalCertificate.details = [];
                }
                vm.animalCertificate.details.push(temp);
                vm.animalCertDetailTemp = {
                    original: "Từ cơ sở nuôi hợp pháp của ông/bà ..., địa chỉ ..., mã số ..."
                };
            } else {
                toastr.warning("Chưa chọn loài", "Cảnh báo");
            }

            vm.onSelectedAnimalFormEdit = (animal, index) => {
                if (!vm.animalCertificate.details || vm.animalCertificate.details.length == 0) {
                    return
                }
                if (animal) {
                    let check = vm.animalCertificate.details.filter(e => {
                        if (!e.animal) { return false };
                        return e.animal.id == animal.id;
                    });
                    if (index != null) {
                        if (vm.animalCertDetailTemp.animal && vm.animalCertDetailTemp.animal.id === animal.id) {
                            vm.animalCertificate.details[index].animal = null;
                            toastr.warning("Loài này đã được chọn", "Thông báo");
                            return;
                        }

                        if (check && check.length > 1) {
                            vm.animalCertificate.details[index].animal = null;
                            toastr.warning("Loài này đã được khai thác", "Thông báo");
                            return;
                        }
                    } else {
                        if (check && check.length > 0) {
                            vm.animalCertDetailTemp.animal = null;
                            toastr.warning("Loài này đã được khai thác", "Thông báo");
                            return;
                        }
                    }
                }

            }
        }

        vm.removeAnimalCertificateDetails = function (index) {
            vm.animalCertificate.details.splice(index, 1);
        };
        //CALC
        vm.calcToalAnimal = function (...params) {
            let total = 0;
            if (params && params.length > 0) {
                params.forEach(function (item) {
                    // if (item && typeof item == typeof 0) {
                    //     total += item;
                    // }
                    console.log(item);
                    if (item) {
                        total += parseInt(item);
                    }
                });
            }
            return total;
        };
        vm.updateTotalAnimal = function (obj) {
            obj.total = vm.calcToalAnimal(
                obj.male,
                obj.female,
                obj.unGender
            );

            obj.total = obj.total;
        };

        vm.onselectedContentProvided = function (item) {
            vm.animalCertificate.contentProvided = item;
        }

        vm.onSelectedSigner = function (item) {
            vm.animalCertificate.typeSigner = item;
        }

        // In giấy chứng nhận sau khi thêm mới

        vm.printFileWord = function () {
            if (vm.animalCertificate) {
                if (!vm.animalCertificate.code) {
                    toastr.warning("Chưa nhập mã số cơ sở.", "Cảnh báo");
                    return;
                }
                if (!vm.animalCertificate.farm) {
                    toastr.warning("Chưa chọn cơ sở nuôi.", "Cảnh báo");
                    return;
                }
                if (!vm.animalCertificate.details) {
                    toastr.warning("Chưa kê khai động vật.", "Cảnh báo");
                    return;
                }
                if (!vm.animalCertificate.typeSigner) {
                    toastr.warning("Chưa chọn chức danh.", "Cảnh báo");
                    return;
                }
                if(vm.animalCertificate.isNew){
                    service.createAnimalCertificate(vm.animalCertificate).then(function (data) {
                        if (data && data.id) {
                            vm.getFileWord(data.id);
                            vm.searchByPage();
                            vm.animalCertificate.id = data.id;
                            vm.idACNew = data.id;
                        }
                    });
                }else{
                    service.updateAnimalCertificate(vm.animalCertificate.id, vm.animalCertificate).then(function (data) {
                        if (data && data.id) {
                            vm.getFileWord(data.id);
                            vm.searchByPage();
                            vm.idACNew = data.id;
                        } 
                    });
                }  
            }

        }
    }

})();