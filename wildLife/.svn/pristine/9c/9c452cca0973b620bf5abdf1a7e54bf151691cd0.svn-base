/**
 * Created by bizic on 28/8/2016.
 */
(function () {
  "use strict";

  angular
    .module("Education.Report")
    .controller(
      "ReportNumberAnimalsAndNumberFarmsAccordingToTheRedBookController",
      ReportNumberAnimalsAndNumberFarmsAccordingToTheRedBookController
    );

  ReportNumberAnimalsAndNumberFarmsAccordingToTheRedBookController.$inject = [
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

  function ReportNumberAnimalsAndNumberFarmsAccordingToTheRedBookController(
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
    vm.checkHaveData = true;

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
    vm.year = "";
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

    vm.vnlist06 = null;

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
        vm.getAllCity();
        // vm.search();
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
        if (vm.adminstrativeUnits_City == null) 
        vm.adminstrativeUnits_City = [];
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

      // vm.getListAnimalOrdo();
      vm.getListAnimalOrdoParam();
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

    // Search list ordo by class AnimalSearchDto
    vm.getListAnimalOrdoParam = function(callSearchFunction) {
      animalService
        .getListAnimalOrdoParam(vm.searchDto)
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
          vm.getListAnimalFamilyParam(true);
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

    // Search list family by class, ordo AnimalSearchDto
    vm.getListAnimalFamilyParam = function (callSearchFunction) {
      animalService
        .getListAnimalFamilyParam(vm.searchDto)
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
          if(callSearchFunction) {
            vm.searchByCode();
          }
        })
    }

    vm.getListAnimalClass();
    // vm.getListAnimalOrdo();
    // vm.getListAnimalFamily();
    // vm.getListAnimalOrdoParam();
    // vm.getListAnimalFamilyParam();

    vm.getVnList06s = function () {
      service.getVnList06s().then(function (data) {
        vm.vnList06s = [];
        vm.vnList06s.push({ name: "Tổng" });
        data.forEach((item) => {
          let vnList06;
          if (item && item.trim().length > 0) {
            vnList06 = item;
            vm.vnList06s.push({ name: vnList06 });
          }
        });
        vm.vnList06s.push({ name: "Không xác định" });
      });
    };

    vm.getVnList06s();

    vm.animalClassSelected = function () {
      if (vm.animalClass && vm.animalClass == "-") {
        vm.searchDto.animalClass = null;
        vm.searchDto.listAnimalClass = null;
        vm.searchDto.ordo = null;
        vm.searchDto.family = null;
      } else {
        // vm.searchDto.animalClass = vm.animalClass;
        vm.searchDto.listAnimalClass = vm.listAnimalClassParam;        
        vm.searchDto.ordo = null;
        vm.searchDto.family = null;
      }
      // vm.getListAnimalOrdo(true);
      vm.getListAnimalOrdoParam(true);

    };

    vm.animalOrdoSelected = function () {
      if (vm.ordo && vm.ordo == "-") {
        vm.searchDto.ordo = null;
        vm.searchDto.listAnimalOrdo = null;
        vm.searchDto.family = null;
      } else {
        vm.searchDto.listAnimalOrdo = vm.listOrdoClassParam;
        vm.searchDto.ordo = vm.ordo;
        vm.searchDto.family = null;
      }
      // vm.getListAnimalFamily(true);
      vm.getListAnimalFamilyParam(true);
    };

    vm.familySelected = function () {
      if (vm.family && vm.family == "-") {
        vm.searchDto.family = null;
        vm.searchDto.listAnimalFamily = null;
      } else {
        vm.searchDto.family = vm.family;
        vm.searchDto.listAnimalFamily = vm.listFamilyClassParam;
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
    vm.onVmList06Selected = function(item){
      if(item!=null){
        vm.vnlist06 = item.name;
        vm.listVn06ClassParam.forEach(function(vnList06){
          if(vnList06 === 'Tổng'){
            vm.listVn06ClassParam.splice(0);
            vm.listVn06ClassParam.push('Tổng');
          }
          if(vnList06 === 'Không xác định'){
            vm.listVn06ClassParam.splice(0);
            vm.listVn06ClassParam.push('Không xác định');
          }
        })
        vm.searchDto.vnList06s = vm.listVn06ClassParam;
      }
    }
    vm.getAllCity = function () {
      auService.getAllCity().then(function (data) {
        vm.adminstrativeUnits_City = data;
      });
    };
    function validate() {
      return true;
    }
    vm.getReportNumberAnimalsAndNumberFarmsAccordingToTheRedBook = function () {
      vm.title = $translate.instant("report.reportAnimalByCites") + " ";
      //Fix title: Show province + distric + ward + year in title
      if (vm.wardsName != null) {
        vm.title = vm.title + vm.wardsName + ", ";
      }
      if (vm.districtName != null) {
        vm.title = vm.title + vm.districtName + ", ";
      }
      if (vm.provinceName != null) {
        vm.title = vm.title + vm.provinceName + " ";
      }
      if (vm.regionName != null) {
        vm.title = vm.title + vm.regionName + " ";
      } 
      if (vm.paramDto.currentYear != null){
        vm.title = vm.title +" - Năm " + vm.paramDto.currentYear;
      }
      //console.log(vm.paramDto.currentYear);
      vm.rows = [];
      var sortStr = "";

      vm.rowsPivot = [
        {
          uniqueName: "year",
          sort: "asc",
          caption: $translate.instant("report.year"),
        },
      ];
      vm.rows.push("year");
      vm.paramDto.animalClass = vm.animalClass;
      vm.paramDto.listAnimalClass = vm.listAnimalClassParam;
      vm.paramDto.listAnimalOrdo = vm.listOrdoClassParam;
      vm.paramDto.listAnimalFamily = vm.listFamilyClassParam;
      vm.paramDto.vnList06s = vm.listVn06ClassParam;
      vm.paramDto.ordo = vm.ordo;
      vm.paramDto.family = vm.family;
      vm.paramDto.vnlist06 = vm.vnlist06;
      if (vm.listAnimals && vm.listAnimals.length > 0) {
        vm.paramDto.listAnimalIds = [];
        vm.listAnimals.forEach((animal) => {
          vm.paramDto.listAnimalIds.push(animal.id);
        });
      } else {
        vm.paramDto.listAnimalIds = [];
      }
      //debugger
      service.getReportNumberAnimalsAndNumberFarmsAccordingToTheRedBook(vm.paramDto)
        .then(function (data) {
          //debugger
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
                    uniqueName: "vnList06",
                    sort: "unsorted",
                    caption: $translate.instant("report.typeanimal"),
                  },
                ],
                measures: [
                  {
                    uniqueName: "totalNumberOfChildren",
                    caption: "Tổng số cá thể", //$translate.instant('report.quantity')
                  },
                  {
                    uniqueName: "totalNumberOfCamps",
                    caption: "Số cơ sở", //$translate.instant('report.quantity')
                  },
                ],
              },
              options: {
                grid: {
                  title: vm.title,
                  type: "compact",
                  showTotals: "off",
                  showGrandTotals: "off",
                },
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

          function createChart2() {
            pivot.highcharts.getData(
              {},
              function (data) {
								let vnlist06 = "";
								
								if(vm.paramDto.vnlist06) {
									vnlist06 = vm.paramDto.vnlist06;
								} else {
									vnlist06 = "Tổng";
								}

								let totalNumberOfCamps = data.series.filter((e) => e.name == `Số cơ sở - ${vnlist06}`);
								let totalNumberOfChildren = data.series.filter((e) => e.name == `Tổng số cá thể - ${vnlist06}`);
                //Title = title + province + distric + ward + year
                let text = '';
                if (vm.wardsName != null) {
                  text = text + vm.wardsName + ", ";
                }
                if (vm.districtName != null) {
                  text = text + vm.districtName + ", ";
                }
                if (vm.provinceName != null) {
                  text = text + vm.provinceName + " ";
                } 
                if (vm.regionName != null) {
                  text = text + vm.regionName + " ";
                }
                if (vm.paramDto.currentYear != null){
                  vm.title = vm.title +" - Năm " + vm.paramDto.currentYear;
                }
                Highcharts.chart("highchartsContainer", {
                  chart: {
                    zoomType: "xy",
                  },
                  title: {
                    text:
                      $translate.instant(
                        "report.reportNumberAnimalsAndNumberFarmsAccordingToTheRedBook"
                      ) + " "+text+"",
                  },
                  subtitle: {
                    text: "",
                  },
                  xAxis: [
                    {
                      categories: data.xAxis.categories,
                      crosshair: true,
                    },
                  ],
                  yAxis: [
                    {
                      // Primary yAxis
                      labels: {
                        format: "{value}",
                        style: {
                          color: Highcharts.getOptions().colors[1],
                        },
                      },
                      title: {
                        text: "Tổng số cá thể",
                        style: {
                          color: Highcharts.getOptions().colors[1],
                        },
                      },
                    },
                    {
                      // Secondary yAxis
                      title: {
                        text: "Số cơ sở",
                        style: {
                          color: Highcharts.getOptions().colors[0],
                        },
                      },
                      labels: {
                        format: "{value}",
                        style: {
                          color: Highcharts.getOptions().colors[0],
                        },
                      },
                      opposite: true,
                    },
                  ],
                  tooltip: {
                    shared: true,
                  },
                  legend: {
                    layout: "vertical",
                    align: "left",
                    x: 120,
                    verticalAlign: "top",
                    y: 100,
                    floating: true,
                    backgroundColor:
                      Highcharts.defaultOptions.legend.backgroundColor || // theme
                      "rgba(255,255,255,0.25)",
                  },
                  series: [
                    {
                      name: `Số cơ sở - ${vnlist06}`,
                      type: "column",
                      yAxis: 1,
                      data: totalNumberOfCamps[0].data,
                      tooltip: {
                        valueSuffix: " ",
                      },
                    },
                    {
                      name: `Tổng số cá thể - ${vnlist06}`,
                      type: "spline",
                      data: totalNumberOfChildren[0].data,
                      tooltip: {
                        valueSuffix: " ",
                      },
                    },
                  ],
                });
              }
            );
          }

          //createChart
          function createChart() {
            pivot.highcharts.getData(
              {},
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
      // ./end clear data combobox

      if (city != null && city.id != null) {
        vm.provinceName = city.name;

        auService
          .getChildrenByParentId(vm.paramDto.provinceId)
          .then(function (data) {
            if (data != null && data.length > 0) {
              vm.adminstrativeUnits_Dist = data;
            }
          });
      }
    };

    vm.onFmsadminstrativeUnitDistSelected = function (dist) {
      vm.paramDto.wardId = null;
      //clear data combobox
      vm.paramDto.wardId = null;
      vm.selectWards = null;
      vm.adminstrativeUnits_Wards = [];
      vm.wardsName = null;
      vm.districtName = null;
      // ./end clear data combobox

      if (dist != null && dist.id != null) {
        vm.districtName = dist.name;

        auService
          .getChildrenByParentId(vm.paramDto.districtId)
          .then(function (data) {
            if (data != null && data.length > 0) {
              vm.adminstrativeUnits_Wards = data;
            }
          });
      }
    };

    vm.onFmsadminstrativeUnitWardsSelected = function (item) {
      if (item != null) {
        vm.wardsName = item.name;
      } else {
        vm.wardsName = null;
      }
    };

    vm.showCalcaulation = function () {
      vm.modal = modal.open({
        animation: true,
        templateUrl: "calculation.html",
        scope: $scope,
        backdrop: "static",
        keyboard: false,
        size: "md",
      });
    };
  }
})();
