/**
 * Created by bizic on 28/8/2016.
 */
(function () {
  "use strict";

  angular
    .module("Education.Report")
    .controller(
      "ReportWildAnimalFarmingReportByAdminstrativeUnitController",
      ReportWildAnimalFarmingReportByAdminstrativeUnitController
    );

  ReportWildAnimalFarmingReportByAdminstrativeUnitController.$inject = [
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
    "AnimalService",
  ];

  function ReportWildAnimalFarmingReportByAdminstrativeUnitController(
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
    animalService
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
    vm.listAnimalClass = [];
    vm.listAnimalFamily = [];
    vm.listAnimalOrdo = [];
    vm.getListAnimalClass = function () {
      animalService.getListAnimalClass().then(function (data) {
        vm.listAnimalClass = [];
        vm.animalClass = null;
        vm.ordo = null;
        vm.family = null;
        data.forEach((item) => {
          var title = "-";
          if (item && item.trim().length > 0) {
            title = item;
          }
          vm.listAnimalClass.push({ name: title });
        });
      });

      vm.getListAnimalOrdo();
    };

    vm.getListAnimalOrdo = function (callSearchFunction) {
      animalService
        .getListAnimalOrdo(vm.searchDto.animalClass)
        .then(function (data) {
          vm.listAnimalOrdo = [];
          vm.ordo = null;
          vm.family = null;
          data.forEach((item) => {
            var title = "-";
            if (item && item.trim().length > 0) {
              title = item;
            }
            vm.listAnimalOrdo.push({ name: title });
          });
          vm.getListAnimalFamily(true);
        });
    };

    vm.getListAnimalFamily = function (callSearchFunction) {
      animalService
        .getListAnimalFamily(vm.searchDto.animalClass, vm.searchDto.ordo)
        .then(function (data) {
          vm.listAnimalFamily = [];
          vm.family = null;
          data.forEach((item) => {
            var title = "-";
            if (item && item.trim().length > 0) {
              title = item;
            }
            vm.listAnimalFamily.push({ name: title });
          });
          if (callSearchFunction) {
            vm.searchByCode();
          }
        });
    };
    vm.getListAnimalClass();
    vm.getListAnimalOrdo();
    vm.getListAnimalFamily();

    vm.animalClassSelected = function () {
      if (vm.animalClass && vm.animalClass == "-") {
        vm.searchDto.animalClass = null;
        vm.searchDto.ordo = null;
        vm.searchDto.family = null;
      } else {
        vm.searchDto.animalClass = vm.animalClass;
        vm.searchDto.ordo = null;
        vm.searchDto.family = null;
      }
      vm.getListAnimalOrdo(true);
    };

    vm.animalOrdoSelected = function () {
      if (vm.ordo && vm.ordo == "-") {
        vm.searchDto.ordo = null;
        vm.searchDto.family = null;
      } else {
        vm.searchDto.ordo = vm.ordo;
        vm.searchDto.family = null;
      }
      vm.getListAnimalFamily(true);
    };

    vm.familySelected = function () {
      if (vm.family && vm.family == "-") {
        vm.searchDto.family = null;
      } else {
        vm.searchDto.family = vm.family;
      }
      vm.searchByCode();
    };
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
    vm.isShowWardData = false;
    vm.getReportNumberAnimalsAndNumberFarmsAccordingToTheRedBook = function () {
      vm.paramDto.groupByItems = ["provinceId", "provinceName"];
      if (vm.paramDto.provinceId != null) {
        vm.paramDto.groupByItems = [];
        vm.paramDto.groupByItems = ["districtId", "districtName"];
      }
      if (vm.paramDto.districtId != null) {
        vm.paramDto.groupByItems = [];
        vm.paramDto.groupByItems = ["wardId", "wardName"];
      }
      if (vm.isShowWardData) {
        vm.paramDto.groupByItems = [];
        vm.paramDto.groupByItems = [
          "provinceId",
          "provinceName",
          "districtId",
          "districtName",
          "wardId",
          "wardName",
        ];
      } 
      if (!vm.paramDto.dateReport) {
        toastr.warning("Bạn chưa chọn năm báo cáo", "Cảnh báo");
        return;
      }
      vm.title = $translate.instant("report.reportAnimalByCites") + " ";
      if (vm.wardsName != null) {
        vm.title = vm.title + vm.wardsName + " ";
      } else if (vm.districtName != null) {
        vm.title = vm.title + vm.districtName + " ";
      } else if (vm.provinceName != null) {
        vm.title = vm.title + vm.provinceName + " ";
      } else if (vm.regionName != null) {
        vm.title = vm.title + vm.regionName + " ";
      }
      vm.rows = [];
      var sortStr = "";

      vm.rowsPivot = [
        {
          uniqueName: "year",
          sort: "asc",
          caption: $translate.instant("report.year"),
        },
      ];
      // vm.rows.push("year");
      vm.paramDto.showWardData=vm.isShowWardData;
      service.wildAnimalFarmingReportByClassification2(vm.paramDto)
        .then(function (data) {
          vm.results = data;
          if (data == null || data == [] || data.length == 0) {
            vm.isShow = false;
          } else {
            vm.isShow = true;
          }
          vm.rows = [
            {
              caption: "Tỉnh",
              uniqueName: "provinceName",
            },
          ];

          vm.columns = [
            {
              uniqueName: "Measures",
            },
          ];

          vm.grid = {
            title:
              "BC 06:Báo cáo tổng hợp nuôi động vật hoang dã theo đơn vị hành chính",
            showTotals: "rows",
            showGrandTotals: "off",
          };

          if (vm.paramDto.provinceId != null) {
            vm.rows = [
              {
                caption: "Huyện",
                uniqueName: "districtName",
              },
              
            ];
          }
          if (vm.paramDto.districtId != null) {
            vm.rows = [
              {
                caption: "Xã",
                uniqueName: "wardName",
              },
            ];
          }
          if (vm.isShowWardData && vm.isShowWardData == true) {
            vm.rows = [
              {
                caption: "Tỉnh",
                uniqueName: "provinceName",
              },
              {
                caption: "Huyện",
                uniqueName: "districtName",
              },
              {
                caption: "Xã",
                uniqueName: "wardName",
              },
              
            ];
            vm.columns = [
              {
                uniqueName: "Measures",
              },
            ];
            vm.grid = {
              type: "classic",
              title:
                "Báo cáo tổng hợp nuôi động vật hoang dã theo đơn vị hành chính",
              showTotals: "off",
              showGrandTotals: "off",
            };
            
          }
          vm.expands = {
            expandAll: true,
          };
          if(vm.paramDto.dateReport==null){
            vm.rows.push({
              caption: "Năm",
              uniqueName :"year",
            });
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
                rows: vm.rows,
                columns: vm.columns,
                measures: [
                  {
                    caption: "Số cá thể",
                    uniqueName: "total",
                    aggregation: "sum",
                  },
                  {
                    caption: "Số cơ sở",
                    uniqueName: "numberOfFam",
                    aggregation: "sum",
                  },
                  {
                    caption: "Số loài",
                    uniqueName: "numberOfAnimal",
                    aggregation: "sum",
                  },
                ],
                expands: vm.expands,
              },
              options: {
                grid: vm.grid,
              },
              localization: "assets/scripts/pivot/vi.json",
            },
            reportcomplete: function () {
              pivot.off("reportcomplete");
              // createChart();
              createChart2();
            },
            global: {
              // replace this path with the path to your own translated file
              localization: "assets/scripts/pivot/vi.json",
            },
          });

          //createChart
          function createChart2() {
            pivot.highcharts.getData({}, function (data) {
							let dataOfTotal = [];
							let dataOfFam = [];
							let dataOfAnimal = [];
							data.series.map((s) => {
								data.xAxis.categories.map((c, i) => {
									let slice = {};
									slice = {
										name: c,
										y: s.data[i] ? s.data[i] : 0,
									}
									if(s.name == "Số cá thể") {
										dataOfTotal.push(slice);
									}
									if(s.name == "Số cơ sở") {
										dataOfFam.push(slice);
									}
									if(s.name == "Số loài") {
										dataOfAnimal.push(slice);
									}
								})
							})

              const getDataChart = (e) => {
                if(e.name == "Số cá thể") {
                  return dataOfTotal;
                }
                if(e.name == "Số cơ sở") {
                  return dataOfFam;
                }
                if(e.name == "Số loài") {
                  return dataOfAnimal;
                }
                return [];
              }
              
							data.series.map((e, index) => {
								Highcharts.chart(`highchartsContainer${index}`, {
									chart: {
										plotBackgroundColor: null,
										plotBorderWidth: null,
										plotShadow: false,
										type: "pie",
									},
									title: {
										text: e.name,
									},
									tooltip: {
										pointFormat: "{series.name}: <b>{point.percentage:.1f}%</b>",
									},
									accessibility: {
										point: {
											valueSuffix: "%",
										},
									},
									plotOptions: {
										pie: {
											allowPointSelect: true,
											cursor: "pointer",
											dataLabels: {
												enabled: true,
												format: "<b>{point.name}</b>: {point.percentage:.1f} %",
											},
										},
									},
									series: [
										{
											name: "Tỉ lệ",
											colorByPoint: true,
											data: getDataChart(e),
										},
									],
								});
							});
            }
						);
          }

          //createChart
          function createChart() {
            pivot.highcharts.getData(
              {
                //Tran huu dat start
                type: "line", //hien thi bieu do theo dang line
                //Tran huu dat start
              },
              function (data) {
                data.chart.options3d = {
                  enabled: true,
                  alpha: 0,
                  beta: 0,
                  depth: 20,
                  viewDistance: 10,
                };
                if (
                  data != null &&
                  data.series != null &&
                  data.series.length <= 3
                ) {
                  data.colors = ["#009800", "#ff9800", "#95CEFF"];
                }

                data.plotOptions = {};
                data.plotOptions.pie = {};
                data.plotOptions.pie.colors = ["blue", "red", "#FF0000"];
                data.plotOptions.series = {};
                data.plotOptions.series.pointWidth = 40; //độ rộng của biểu đồ
                data.title.text =
                  $translate.instant("report.reportAnimalByCites") + " ";

                if (vm.wardsName != null) {
                  data.title.text = data.title.text + vm.wardsName + " ";
                } else if (vm.districtName != null) {
                  data.title.text = data.title.text + vm.districtName + " ";
                } else if (vm.provinceName != null) {
                  data.title.text = data.title.text + vm.provinceName + " ";
                } else if (vm.regionName != null) {
                  data.title.text = data.title.text + vm.regionName + " ";
                }
                data.xAxis.title.text = ""; //bỏ title trục ngang
                data.yAxis[0].title.text = ""; //bỏ title trục dọc
                Highcharts.chart("highchartsContainer", data);
              },
              function (data) {
                data.chart.options3d = {
                  enabled: true,
                  alpha: 0,
                  beta: 0,
                  depth: 20,
                  viewDistance: 10,
                };
                if (
                  data != null &&
                  data.series != null &&
                  data.series.length <= 3
                ) {
                  data.colors = ["#009800", "#ff9800", "#95CEFF"];
                }
                data.plotOptions = {};
                data.plotOptions.pie = {};
                data.plotOptions.pie.colors = ["blue", "red", "#FF0000"];
                data.plotOptions.series = {};
                data.plotOptions.series.pointWidth = 40; //độ rộng của biểu đồ
                data.title.text =
                  $translate.instant(
                    "report.reportNumberAnimalsAndNumberFarmsAccordingToTheRedBook"
                  ) + " ";
                if (vm.wardsName != null) {
                  data.title.text = data.title.text + vm.wardsName + " ";
                } else if (vm.districtName != null) {
                  data.title.text = data.title.text + vm.districtName + " ";
                } else if (vm.provinceName != null) {
                  data.title.text = data.title.text + vm.provinceName + " ";
                } else if (vm.regionName != null) {
                  data.title.text = data.title.text + vm.regionName + " ";
                }
                data.xAxis.title.text = ""; //bỏ title trục ngang
                data.yAxis[0].title.text = ""; //bỏ title trục dọc
                Highcharts.chart("highchartsContainer", data);
              }
            );
          }
        });
    };
    vm.onFmsRegionSelected = function (region) {
      if (region != null && region.id != null) {
        vm.regionName = region.name;
        auService.getAllCityByRegion(region.id).then(function (data) {
          if (data != null && data.length > 0) {
            vm.adminstrativeUnits_City = data;
            vm.selectedCity = null;
            vm.selectedDist = null;
            vm.selectWards = null;
            vm.paramDto.provinceId = null;
            vm.paramDto.districtId = null;
            vm.paramDto.wardId = null;
            vm.provinceName = null;
            vm.districtName = null;
            vm.wardsName = null;
          } else {
            vm.paramDto.provinceId = null;
            vm.paramDto.districtId = null;
            vm.paramDto.wardId = null;
            vm.selectedCity = null;
            vm.selectedDist = null;
            vm.selectWards = null;
            vm.adminstrativeUnits_City = [];
            vm.adminstrativeUnits_Dist = [];
            vm.adminstrativeUnits_Wards = [];
            vm.provinceName = null;
            vm.districtName = null;
            vm.wardsName = null;
          }
        });
      } else {
        vm.paramDto.provinceId = null;
        vm.paramDto.districtId = null;
        vm.paramDto.wardId = null;
        vm.selectedCity = null;
        vm.selectedDist = null;
        vm.selectWards = null;
        vm.adminstrativeUnits_City = [];
        vm.adminstrativeUnits_Dist = [];
        vm.adminstrativeUnits_Wards = [];
        vm.regionName = null;
        vm.provinceName = null;
        vm.districtName = null;
        vm.wardsName = null;
      }
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
        auService
          .getChildrenByParentId(vm.paramDto.provinceId)
          .then(function (data) {
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
        auService
          .getChildrenByParentId(vm.paramDto.districtId)
          .then(function (data) {
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
  }
})();
