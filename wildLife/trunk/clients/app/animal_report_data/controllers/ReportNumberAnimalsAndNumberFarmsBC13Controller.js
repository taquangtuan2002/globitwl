/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    "use strict";

    angular
        .module("Education.Report")
        .controller(
            "ReportNumberAnimalsAndNumberFarmsBC13Controller",
            ReportNumberAnimalsAndNumberFarmsBC13Controller
        );

    ReportNumberAnimalsAndNumberFarmsBC13Controller.$inject = [
        "$rootScope",
        "$scope",
        "$http",
        "$timeout",
        "settings",
        "AnimalReportDataService",
        "FmsRegionService",
        "$uibModal",
        "toastr",
        "$state",
        "blockUI",
        "$stateParams",
        "Utilities",
        "$translate",
        "FmsAdministrativeService",
        'FileSaver'

    ];

    function ReportNumberAnimalsAndNumberFarmsBC13Controller(
        $rootScope,
        $scope,
        $http,
        $timeout,
        settings,
        service,
        regionService,
        modal,
        toastr,
        $state,
        blockUI,
        $stateParams,
        utils,
        $translate,
        auService,
        FileSaver

    ) {
        $scope.$on("$viewContentLoaded", function () {
            // initialize core components
            App.initAjax();
        });

        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        /** declare */
        var vm = this;
        var today = new Date();
        vm.currentYear = today.getFullYear();

        // blockUI.stop();
        // blockUI.start();
        vm.recordId = null;
        vm.pageIndex = 1;
        vm.pageSize = 25;
        vm.farmSizes = [];
        vm.modalFarmSize = {};
        vm.farmSize = { isNew: false };
        vm.modalConfirmDelete = {};
        vm.selectedFarmSize = [];
        vm.count = 0;
        vm.searchDto = {};
        vm.viewCheckDuplicateCode = {};
        vm.tempCode = "";
        vm.results = [];

        vm.selectedCity = {};
        $scope.labels = ["Toàn tỉnh"];
        $scope.series = [];
        $scope.data = [];
        vm.viewType = "table";
        $scope.years = [];
        vm.regionName = null;
        vm.provinceName = null;
        vm.districtName = null;
        vm.wardsName = null;
        vm.title = null;
        vm.isShow = true;
        vm.searchDto = {};
        vm.listYear = [];

        for (var i = vm.currentYear; i > 2000; i--) {
            $scope.years.push({ value: i, name: i + "" });
        }
        vm.months = [
            {
                name: "Tháng 1",
                value: 1,
            },
            {
                name: "Tháng 2",
                value: 2,
            },
            {
                name: "Tháng 3",
                value: 3,
            },
            {
                name: "Tháng 4",
                value: 4,
            },
            {
                name: "Tháng 5",
                value: 5,
            },
            {
                name: "Tháng 6",
                value: 6,
            },
            {
                name: "Tháng 7",
                value: 7,
            },
            {
                name: "Tháng 8",
                value: 8,
            },
            {
                name: "Tháng 9",
                value: 9,
            },
            {
                name: "Tháng 10",
                value: 10,
            },
            {
                name: "Tháng 11",
                value: 11,
            },
            {
                name: "Tháng 12",
                value: 12,
            },
        ];

        /** generate */

        vm.paramDto = {};
        vm.paramDto.year = today.getFullYear();
        vm.isRole = false;
        vm.isFamer = false;
        vm.isSdah = false;
        vm.isDistrict = false;
        vm.isWard = false;
        vm.isSearchExtend = false;
        vm.isSdahView = false; //cấp tỉnh nhưng chỉ được xem

        vm.isSelectProvince = false;
        vm.isSelectDistrict = false;
        vm.isSelectWard = false;
        vm.province = null;
        vm.district = null;
        vm.ward = null;

        $scope.years = [];
        var today = new Date();
        vm.currentYear = today.getFullYear();
        for (var i = vm.currentYear; i >= 2000; i--) {
            $scope.years.push({ value: i, name: i + "" });
        }

        //------Start--Phân quyền theo user đăng nhập-----------
        blockUI.start();
        service.getCurrentUser().then(function (data) {
            vm.user = data;
            console.log("a", data)
            var roles = data.roles;
            if (roles != null && roles.length > 0) {
                for (var i = 0; i < roles.length; i++) {
                    if (
                        roles[i] != null &&
                        roles[i].authority &&
                        (roles[i].authority.toLowerCase() == "role_admin" ||
                            roles[i].authority.toLowerCase() == "role_dlp")
                    ) {
                        vm.isRole = true;
                    }
                    if (
                        roles[i] != null &&
                        roles[i].authority &&
                        roles[i].authority.toLowerCase() == "role_sdah"
                    ) {
                        vm.isSdah = true;
                    }
                    if (
                        roles[i] != null &&
                        roles[i].authority &&
                        roles[i].authority.toLowerCase() == "role_district"
                    ) {
                        vm.isDistrict = true;
                    }
                    if (
                        roles[i] != null &&
                        roles[i].authority &&
                        roles[i].authority.toLowerCase() == "role_ward"
                    ) {
                        vm.isWard = true;
                    }
                    if (
                        roles[i] != null &&
                        roles[i].authority &&
                        roles[i].authority.toLowerCase() == "role_famer"
                    ) {
                        vm.isFamer = true;
                    }
                    if (
                        roles[i] != null &&
                        roles[i].authority &&
                        roles[i].authority.toLowerCase() == "role_sdah_view"
                    ) {
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
            if (vm.isRole) {
                //trường hợp admin và dlp thì được xem tất cả các cơ sở
                vm.getAllProvince();
            } else {
                // trường hợp này thì phân quyền theo user
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
                vm.paramDto.provinceId = administrativeUnit.id;
                if (vm.adminstrativeUnits_City == null) vm.adminstrativeUnits_City = [];
                vm.adminstrativeUnits_City.push(administrativeUnit);
                service.getAllByParentId(vm.paramDto.provinceId).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                    }
                });
            } else if (
                administrativeUnit.parentDto != null &&
                administrativeUnit.parentDto.parentDto == null
            ) {
                vm.paramDto.districtId = administrativeUnit.id;
                vm.paramDto.provinceId = administrativeUnit.parentDto.id;
                vm.adminstrativeUnits_City = [];
                vm.adminstrativeUnits_City.push(administrativeUnit.parentDto);
                vm.adminstrativeUnits_Dist = [];
                if (vm.adminstrativeUnits_Dist == null) vm.adminstrativeUnits_Dist = [];
                vm.adminstrativeUnits_Dist.push(administrativeUnit);
                service.getAllByParentId(vm.paramDto.provinceId).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                    }
                });
                service.getAllByParentId(vm.paramDto.districtId).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Wards = data;
                    }
                });
            } else if (
                administrativeUnit.parentDto != null &&
                administrativeUnit.parentDto.parentDto != null
            ) {
                vm.paramDto.wardId = administrativeUnit.id;
                vm.paramDto.districtId = administrativeUnit.parentDto.id;
                vm.paramDto.provinceId = administrativeUnit.parentDto.parentDto.id;

                if (vm.adminstrativeUnits_City == null) vm.adminstrativeUnits_City = [];
                vm.adminstrativeUnits_City.push(administrativeUnit.parentDto.parentDto);
                if (vm.adminstrativeUnits_Dist == null) vm.adminstrativeUnits_Dist = [];
                vm.adminstrativeUnits_Dist.push(administrativeUnit.parentDto);
                if (vm.adminstrativeUnits_Wards == null)
                    vm.adminstrativeUnits_Wards = [];
                vm.adminstrativeUnits_Wards.push(administrativeUnit);
            }
        }

        //--------End ----Phân quyền-------------
        /** get data */
        /** get data */
        vm.searchDto = {};

        vm.searchByCode = function () {
            if (vm.searchDto == null) {
                vm.searchDto = {};
            }
            animalService
                .getPageSearchAnimal(vm.searchDto, 1, 100000)
                .then(function (data) {
                    vm.animals = data.content;
                });
        };
        vm.paramDto.groupByItems = [];
        vm.getAllProvince = function () {
            service.getAdminstrativeUnitsCity().then(function (data) {
                vm.adminstrativeUnits_City = data;
            });
        };


        vm.onFmsadminstrativeUnitCitySelected = function (city) {
            vm.selectedCity = city;
            //clear data combobox
            vm.paramDto.districtId = null;
            vm.paramDto.wardId = null;
            vm.selectedDist = null;
            vm.selectWards = null;
            vm.adminstrativeUnits_Dist = [];
            vm.adminstrativeUnits_Wards = [];
            vm.districtName = null;
            vm.wardsName = null;
            vm.provinceName = null;
            if (city != null && city.id != null) {
                vm.provinceName = city.name;
                auService.getChildrenByParentId(vm.paramDto.provinceId).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                    }
                });
            } else {
                vm.paramDto.groupByItems = [];
            }
        };

        vm.onFmsadminstrativeUnitDistSelected = function (dist) {
            vm.paramDto.wardId = null;
            vm.paramDto.wardId = null;
            vm.selectWards = null;
            vm.adminstrativeUnits_Wards = [];
            vm.wardsName = null;
            vm.districtName = null;
            if (dist != null && dist.id != null) {
                vm.districtName = dist.name;
                auService.getChildrenByParentId(vm.paramDto.districtId).then(function (data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Wards = data;
                    }
                });
            } else {
            }
        };

        vm.onFmsadminstrativeUnitWardsSelected = function (item) {
            if (item != null) {
                vm.wardsName = item.name;
            } else {
                vm.wardsName = null;
            }
        };

        vm.getDataReportAnimal1_3ToExcel = function () {
            vm.myBlobObject = undefined;
            // const searchDto = {};
            if (!vm.paramDto.provinceId) {
                toastr.warning("Chưa chọn Tỉnh.", "Cảnh báo");
                return;
            }
            if (!vm.paramDto.dateReport) {
                toastr.warning("Chưa chọn thời gian báo cáo.", "Cảnh báo");
                return;
            }
            // if (!vm.district) {
            //     toastr.warning("Chưa chọn Huyện.", "Cảnh báo");
            //     return;
            // }
            // if (vm.ward==null) {
            //     toastr.warning("Chưa chọn Xã.", "Cảnh báo");
            //     return;
            // }

            // searchDto.year = vm.selectYear ? vm.selectYear : null;
            // searchDto.provinceId = vm.paramDto.provinceId ? vm.paramDto.provinceId : null;
            // searchDto.districtId = vm.paramDto.districtId ? vm.paramDto.districtId : null;
            // searchDto.communeId = vm.paramDto.wardId ? vm.paramDto.wardId : null;
            const searchDto = {
                year: vm.selectYear ? vm.selectYear : null,
                dateReport: vm.paramDto.dateReport ? vm.paramDto.dateReport : null,
                provinceId: vm.paramDto.provinceId ? vm.paramDto.provinceId : null,
                districtId: vm.paramDto.districtId ? vm.paramDto.districtId : null,
                communeId: vm.paramDto.wardId ? vm.paramDto.wardId : null,
                animalId: vm.animal ? vm.animal.id : null,
                animalIds: vm.searchDto.animalIds ? vm.searchDto.animalIds : null,
                farmId: vm.farmSelected ? vm.farmSelected.id : null,
                animalClass: vm.searchDto.animalClass ? vm.searchDto.animalClass : null,
                listAnimalClass: vm.searchDto.listAnimalClass ? vm.searchDto.listAnimalClass : null,
                animalOrdo: vm.searchDto.ordo ? vm.searchDto.ordo : null,
                listAnimalOrdo: vm.searchDto.listAnimalOrdo ? vm.searchDto.listAnimalOrdo : null,
                animalFamily: vm.searchDto.family ? vm.searchDto.family : null,
                listAnimalFamily: vm.searchDto.listAnimalFamily ? vm.searchDto.listAnimalFamily : null,
            }


            console.log('download started, you can show a wating animation');
            service.exportForm16Service(searchDto).then(function (data) {//is important that the data was returned as Aray Buffer
                console.log(data);
                var file = new Blob([data], {
                    type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
                });
                FileSaver.saveAs(file, "DuLieuBaoCao2_2.xlsx");
            }, function (fail) {
                console.log('Download Error, stop animation and show error message');
                $scope.myBlobObjectFileImportExcel = [];
            });
        };

        vm.paramDto.groupByItems = [];

        vm.getDataAnimalForExcel = function () {
            vm.title =   $translate.instant("report.report_number_animals_and_number_farms_BC1_3")  + " " ;
            //Title: title + province + distric + ward
            if (vm.wardsName != null) {
                vm.title = vm.title + vm.wardsName + ", ";
            }
            if (vm.districtName != null) {
                vm.title = vm.title + vm.districtName + ", ";
            }
            if (vm.provinceName != null) {
                vm.title = vm.title + vm.provinceName + " ";
            }
            vm.paramDto.groupByItems = [];
            vm.rows = [];
            var sortStr = "";

            vm.rowsPivot = [
                {
                    uniqueName: "farmName",
                    sort: "asc",
                    caption: "Chủ trang trại",
                },
                // {
                //     uniqueName: "addressDetails",
                //     caption: "Địa chỉ",
                // },
                {
                    uniqueName: "communeName",
                    caption: "Xã",
                },
                {
                    uniqueName: "districtName",
                    caption: "Huyện",
                },
                {
                    uniqueName: "newRegistrationCode",
                    caption: "Mã cơ sở nuôi",
                },
                {
                    uniqueName: "phoneNumber ",
                    caption: "Số điện thoại",
                },
                {
                    uniqueName: "dateRegistration",
                    caption: "Ngày cấp",
                },
                {
                    uniqueName: "",
                    caption: "",
                },
                {
                    uniqueName: "animalName",
                    caption: "Loài nuôi",
                },
                {
                    uniqueName: "animalScienceName",
                    caption: "Tên khoa học",
                },
                {
                    uniqueName: "total",
                    caption: "Số lượng",
                },
                {
                    uniqueName: "note",
                    caption: "Ghi chú",
                },
                {
                    uniqueName: "total",
                    caption: "Tổng",
                },
                {
                    uniqueName: "certificate",
                    caption: "Giấy chứng nhận cục kiểm lâm đã cấp",
                },
                {
                    uniqueName: "registerDate",
                    caption: "Ngày cấp giấy chứng nhận",
                }
            ];

            // vm.rows.push("year");

            // const searchDto = {};
            // searchDto.year = vm.paramDto.dateReport ? vm.paramDto.dateReport : null;
            // searchDto.provinceId = vm.paramDto.provinceId ? vm.paramDto.provinceId : null;
            // searchDto.districtId = vm.paramDto.districtId ? vm.paramDto.districtId : null;
            // searchDto.communeId = vm.paramDto.wardId ? vm.paramDto.wardId : null;

            service.getDataAnimalForExcel(vm.paramDto).then(function (data) {
                    vm.results = data;
                    if (data == null || data == [] || data.length == 0) {
                        vm.isShow = false;
                    } else {
                        vm.isShow = true;
                    }

                    //Pivot here
                    var pivot = new WebDataRocks({
                        container: "#wdr-component",
                        toolbar: true,
                        height: 530,
                        report: {
                            dataSource: {
                                data: vm.results,
                            },
                            slice: {
                                rows: vm.rowsPivot,
                                columns: [
                                    {
                                        uniqueName: "animalName",
                                        sort: "unsorted",
                                        caption: $translate.instant("report.typeanimal"),
                                    },
                                ],
                                measures: [
                                    {
                                        uniqueName: "total",
                                        caption: "Số con", //$translate.instant('report.quantity')
                                    },
                                    
                                ]
                                // sorting: {
                                //     row: {
                                //         type: "desc",
                                //         tuple: [
                                //             sortStr
                                //         ],
                                //         measure: "total"
                                //     }
                                // }
                            },
                            options: {
                                grid: {
                                    title: vm.title,
                                    type: "flat",
                                    showTotals: "row",
                                    showGrandTotals: "row",
                                },
                            },
                            localization: "assets/scripts/pivot/vi.json",
                        },
                       
                        reportcomplete: function () {
                            pivot.off("reportcomplete");
                            createChart();
                        },
                        global: {
                            // replace this path with the path to your own translated file
                            localization: "assets/scripts/pivot/vi.json",
                        },
                    });

                    
                });

        }
    }
})();
