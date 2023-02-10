/**
 * Created by bizic on 28/8/2016.
 */
(function () {
  "use strict";

  angular
    .module("Education.ReportPeriod")
    .controller("ImportDataForm16Controller", ImportDataForm16Controller);

  ImportDataForm16Controller.$inject = [
    "$rootScope",
    "$scope",
    "$http",
    "$timeout",
    "settings",
    "ReportPeriodService",
    "$uibModal",
    "toastr",
    "$state",
    "blockUI",
    "$stateParams",
    "Utilities",
    "Upload",
    "FileSaver",
    "FarmService",
    "AnimalService",
    "$translate",
    "OriginalService",
    "ProductTargetService",
    "InjectionTimeService",
  ];
  angular
    .module("Education.ReportPeriod")
    .filter("animalPropsFilter", function () {
      return function (items, props) {
        var out = [];

        if (angular.isArray(items)) {
          items.forEach(function (item) {
            var itemMatches = false;

            var keys = Object.keys(props);
            for (var i = 0; i < keys.length; i++) {
              var prop = keys[i];
              var text = props[prop].toLowerCase();
              if (
                item[prop] &&
                item[prop].toString().toLowerCase().indexOf(text) !== -1
              ) {
                itemMatches = true;
                break;
              }
            }

            if (itemMatches) {
              out.push(item);
            }
          });
        } else {
          // Let the output be the input untouched
          out = items;
        }

        return out;
      };
    });
  angular
    .module("Education.ReportPeriod")
    .directive("numberInput", function ($filter) {
      return {
        require: "ngModel",
        link: function (scope, elem, attrs, ngModelCtrl) {
          ngModelCtrl.$formatters.push(function (modelValue) {
            return setDisplayNumber(modelValue, true);
          });

          // it's best to change the displayed text using elem.val() rather than
          // ngModelCtrl.$setViewValue because the latter will re-trigger the parser
          // and not necessarily in the correct order with the changed value last.
          // see http://radify.io/blog/understanding-ngmodelcontroller-by-example-part-1/
          // for an explanation of how ngModelCtrl works.
          ngModelCtrl.$parsers.push(function (viewValue) {
            setDisplayNumber(viewValue);
            return setModelNumber(viewValue);
          });

          // occasionally the parser chain doesn't run (when the user repeatedly
          // types the same non-numeric character)
          // for these cases, clean up again half a second later using "keyup"
          // (the parser runs much sooner than keyup, so it's better UX to also do it within parser
          // to give the feeling that the comma is added as they type)
          elem.bind("keyup focus", function () {
            setDisplayNumber(elem.val());
          });

          function setDisplayNumber(val, formatter) {
            var valStr, displayValue;

            if (typeof val === "undefined") {
              return 0;
            }

            valStr = val.toString();
            displayValue = valStr.replace(/,/g, "").replace(/[A-Za-z]/g, "");
            displayValue = parseFloat(displayValue);
            displayValue = !isNaN(displayValue) ? displayValue.toString() : "";

            // handle leading character -/0
            if (valStr.length === 1 && valStr[0] === "-") {
              displayValue = valStr[0];
            } else if (valStr.length === 1 && valStr[0] === "0") {
              displayValue = "";
            } else {
              displayValue = $filter("number")(displayValue);
            }

            // handle decimal
            if (!attrs.integer) {
              if (displayValue.indexOf(".") === -1) {
                if (valStr.slice(-1) === ".") {
                  displayValue += ".";
                } else if (valStr.slice(-2) === ".0") {
                  displayValue += ".0";
                } else if (valStr.slice(-3) === ".00") {
                  displayValue += ".00";
                }
              } // handle last character 0 after decimal and another number
              else {
                if (valStr.slice(-1) === "0") {
                  displayValue += "0";
                }
              }
            }

            if (attrs.positive && displayValue[0] === "-") {
              displayValue = displayValue.substring(1);
            }

            if (typeof formatter !== "undefined") {
              return displayValue === "" ? 0 : displayValue;
            } else {
              elem.val(displayValue === "0" ? "" : displayValue);
            }
          }

          function setModelNumber(val) {
            var modelNum = val
              .toString()
              .replace(/,/g, "")
              .replace(/[A-Za-z]/g, "");
            modelNum = parseFloat(modelNum);
            modelNum = !isNaN(modelNum) ? modelNum : 0;
            if (modelNum.toString().indexOf(".") !== -1) {
              modelNum = Math.round((modelNum + 0.00001) * 100) / 100;
            }
            if (attrs.positive) {
              modelNum = Math.abs(modelNum);
            }
            return modelNum;
          }
        },
      };
    });
  angular
    .module("Education.ReportPeriod")
    .directive("fileDownload", function () {
      return {
        restrict: "A",
        scope: {
          fileDownload: "=",
          fileName: "=",
        },

        link: function (scope, elem, atrs) {
          scope.$watch("fileDownload", function (newValue, oldValue) {
            if (newValue != undefined && newValue != null) {
              console.debug("Downloading a new file");
              var isFirefox = typeof InstallTrigger !== "undefined";
              var isSafari =
                Object.prototype.toString
                  .call(window.HTMLElement)
                  .indexOf("Constructor") > 0;
              var isIE = /*@cc_on!@*/ false || !!document.documentMode;
              var isEdge = !isIE && !!window.StyleMedia;
              var isChrome =
                (!!window.chrome && !!window.chrome.webstore) ||
                window.chrome != null;
              var isOpera =
                (!!window.opr && !!opr.addons) ||
                !!window.opera ||
                navigator.userAgent.indexOf(" OPR/") >= 0;
              var isBlink = (isChrome || isOpera) && !!window.CSS;

              if (isFirefox || isIE || isChrome) {
                if (isChrome) {
                  console.log("Manage Google Chrome download");
                  var url = window.URL || window.webkitURL;
                  var fileURL = url.createObjectURL(scope.fileDownload);
                  var downloadLink = angular.element("<a></a>"); //create a new  <a> tag element
                  downloadLink.attr("href", fileURL);
                  downloadLink.attr("download", scope.fileName);
                  downloadLink.attr("target", "_self");
                  downloadLink[0].click(); //call click function
                  url.revokeObjectURL(fileURL); //revoke the object from URL
                }
                if (isIE) {
                  console.log("Manage IE download>10");
                  window.navigator.msSaveOrOpenBlob(
                    scope.fileDownload,
                    scope.fileName
                  );
                }
                if (isFirefox) {
                  console.log("Manage Mozilla Firefox download");
                  var url = window.URL || window.webkitURL;
                  var fileURL = url.createObjectURL(scope.fileDownload);
                  var a = elem[0]; //recover the <a> tag from directive
                  a.href = fileURL;
                  a.download = scope.fileName;
                  a.target = "_self";
                  a.click(); //we call click function
                }
              } else {
                alert("SORRY YOUR BROWSER IS NOT COMPATIBLE");
              }
            }
          });
        },
      };
    });

  function ImportDataForm16Controller(
    $rootScope,
    $scope,
    $http,
    $timeout,
    settings,
    service,
    modal,
    toastr,
    $state,
    blockUI,
    $stateParams,
    util,
    Uploader,
    FileSaver,
    farmService,
    animalService,
    $translate,
    originalService,
    productTargetService,
    injectionTimeService
  ) {
    $scope.$on("$viewContentLoaded", function () {
      // initialize core components
      App.initAjax();
    });

    $rootScope.settings.layout.pageContentWhite = true;
    $rootScope.settings.layout.pageBodySolid = false;
    $rootScope.settings.layout.pageSidebarClosed = false;

    var vm = this;
    vm.type = 1;
    vm.ImportAnimal = {};
    vm.currentId = $state.params.id ? $state.params.id : null;
    vm.farmSelected = null;
    vm.animalSelected = null;
    vm.pageSize = 10;
    vm.pageIndex = 1;
    vm.farms = [];
    vm.animals = [];
    vm.importAnimal = {};
    vm.injectionPlants = [];
    vm.injectionPlant = {};
    vm.modalInjectionPlant = {};
    vm.indexInjectionPlant = -1;
    vm.originals = [];
    vm.original = {};
    vm.productTargets = [];
    vm.injectionTimes = [];
    vm.parentAnimals = [];
    vm.animalByParents = [];
    vm.animal = {};
    vm.isRole = false;
    vm.isFamer = false;
    vm.animalGenders = settings.animalGenders;
    vm.numberOne = 1;
    vm.individual = null;
    vm.animalStatuses = settings.IndividualAnimalStatuses;

    vm.years = [];
    vm.currentYear = new Date().getFullYear();
    for (let year = vm.currentYear; year >= 2000; year--) {
      vm.years.push({ value: year, name: year + "" });
    }
    //------Start--Phân quyền theo user đăng nhập-----------
    blockUI.start();
    service.getCurrentUser().then(function (data) {
      console.log(data);
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
      if (vm.isRole) {
        //trường hợp admin và dlp thì được xem tất cả các cơ sở
        getAllCity();
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
        vm.province = administrativeUnit.id;
        if (vm.adminstrativeUnits_City == null) vm.adminstrativeUnits_City = [];
        vm.adminstrativeUnits_City.push(administrativeUnit);
        service.getAllByParentId(vm.province).then(function (data) {
          if (data != null && data.length > 0) {
            vm.adminstrativeUnits_Dist = data;
          }
        });
      } else if (
        administrativeUnit.parentDto != null &&
        administrativeUnit.parentDto.parentDto == null
      ) {
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
      } else if (
        administrativeUnit.parentDto != null &&
        administrativeUnit.parentDto.parentDto != null
      ) {
        vm.ward = administrativeUnit.id;
        vm.district = administrativeUnit.parentDto.id;
        vm.province = administrativeUnit.parentDto.parentDto.id;

        if (vm.adminstrativeUnits_City == null) vm.adminstrativeUnits_City = [];
        vm.adminstrativeUnits_City.push(administrativeUnit.parentDto.parentDto);
        if (vm.adminstrativeUnits_Dist == null) vm.adminstrativeUnits_Dist = [];
        vm.adminstrativeUnits_Dist.push(administrativeUnit.parentDto);
        if (vm.adminstrativeUnits_Wards == null)
          vm.adminstrativeUnits_Wards = [];
        vm.adminstrativeUnits_Wards.push(administrativeUnit);
      }
      if (
        vm.isRole == false &&
        vm.isFamer == true &&
        vm.user !== null &&
        vm.user.username !== null &&
        vm.user.username !== ""
      ) {
        //trường hợp tài khoản nông dân
        //gán tên cơ sở chăn nuôi
        vm.textSearch = vm.user.username;
        // console.log("username");
        //console.log(vm.user.username);
      }
    }

    //--------End ----Phân quyền-------------
    //// Upload file
    $scope.MAX_FILE_SIZE = "5MB";
    $scope.f = null;
    $scope.errFile = null;

    $scope.uploadFiles = function (file, errFiles) {
      $scope.f = file;
      $scope.errFile = errFiles && errFiles[0];
    };

    vm.startUploadFile = function (file) {
      //console.log(file);
      //   var func ='file/updateListFromFileImport/3/7';
      var func =
        "file/uploadFileAndCallApiUpdateListFromFileImportByListDto/3/7";
      if (file) {
        file.upload = Uploader.upload({
          url: settings.api.baseUrl + settings.api.apiPrefix + func,
          data: { uploadfile: file },
        });
        file.upload.then(
          function (response) {
            //debugger
            file.result = response.data;
            if (
              response.data.listErr != null &&
              response.data.listErr.length > 0
            ) {
              vm.listErrs = response.data.listErr;
              vm.modalInstanceListErr = modal.open({
                animation: true,
                templateUrl: "listDataImportErr.html",
                scope: $scope,
                backdrop: "static",
                keyboard: false,
                windowClass: "customer-modal-lg",
                size: "lg",
              });
            } else {
              toastr.info(
                $translate.instant("upload.importSuccess"),
                $translate.instant("toastr.info")
              );
            }
          },
          function errorCallback(response) {
            toastr.error(
              $translate.instant("upload.importError"),
              $translate.instant("toastr.error")
            );
          }
        );
      }
    };

    //tran huu dat them ham import form moi start
    vm.startUploadFileNew = function (file) {
      //console.log(file);
      // var func ='file/updateListFromFileImport/3/7';
      var func =
        "file/uploadFileAndCallApiUpdateListFromFileImportByListDtoNew/3/7";
      if (file) {
        file.upload = Uploader.upload({
          url: settings.api.baseUrl + settings.api.apiPrefix + func,
          data: { uploadfile: file },
        });
        file.upload.then(
          function (response) {
            file.result = response.data;
            if (
              response.data.listErr != null &&
              response.data.listErr.length > 0
            ) {
              vm.listErrs = response.data.listErr;
              vm.modalInstanceListErr = modal.open({
                animation: true,
                templateUrl: "listDataImportErrNew.html",
                scope: $scope,
                backdrop: "static",
                keyboard: false,
                windowClass: "customer-modal-lg",
                size: "lg",
              });
            } else {
              toastr.info(
                $translate.instant("upload.importSuccess"),
                $translate.instant("toastr.info")
              );
            }
          },
          function errorCallback(response) {
            toastr.error(
              $translate.instant("upload.importError"),
              $translate.instant("toastr.error")
            );
          }
        );
      }
    };

    //tran huu dat them ham import form moi end

    vm.importAnimal = function () {
      var modalInstance = modal.open({
        animation: true,
        templateUrl: "import_modal.html",
        scope: $scope,
        size: "md",
        backdrop: false,
      });
      $scope.f = null;
      $scope.errFile = null;

      modalInstance.result.then(
        function (confirm) {
          if (confirm == "yes") {
            vm.startUploadFile($scope.f);
            console.log($scope.f);
          }
        },
        function () {
          console.log("cancel");
        }
      );
    };

    vm.disabledByTime = () => {
      let hours = new Date().getHours();
      // if((hours >=19 && hours <= 24) || (hours >=0 && hours <= 7)) {
      //   return true;
      // } else {
      //   return false;
      // }
      return true;
    }

    vm.importAnimalNew = function () {
      var modalInstance = modal.open({
        animation: true,
        templateUrl: "import_modal_new.html",
        scope: $scope,
        size: "md",
        backdrop: false,
      });
      $scope.f = null;
      $scope.errFile = null;

      modalInstance.result.then(
        function (confirm) {
          if (confirm == "yes") {
            vm.startUploadFileNew($scope.f);
            console.log($scope.f);
          }
        },
        function () {
          console.log("cancel");
        }
      );
    };

    vm.downloadTemplateImportFile = function () {
      $scope.myBlobObjectFileImportExcel = undefined;

      service.downloadTemplateImportFile().then(
        function (data) {
          //is important that the data was returned as Aray Buffer
          var file = new Blob([data], {
            type:
              "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
          });
          FileSaver.saveAs(file, "TemplateImportDataForm16.xlsx");
        },
        function (fail) {
          console.log("Download Error, stop animation and show error message");
          $scope.myBlobObjectFileImportExcel = [];
        }
      );
    };
    $scope.getFileImportExcelForm16A = function () {
      $scope.myBlobObjectFileImportExcel = undefined;
      const searchDto = {};
      if (!vm.province || vm.province == null) {
        toastr.warning("Bạn chưa chọn Tỉnh/Thành phố", "Cảnh báo");
        return;
      }
      if (vm.province) {
        searchDto.provinceId = vm.province;
      } else {
        searchDto.provinceId = null;
      }
      if (vm.district) {
        searchDto.districtId = vm.district;
      } else {
        searchDto.districtId = null;
      }
      if (vm.ward) {
        searchDto.wardId = vm.ward;
      } else {
        searchDto.wardId = null;
      }
      if (vm.animal) {
        searchDto.animalId = vm.animal.id;
      } else {
        searchDto.animalId = null;
      }
      if (!vm.year || vm.year == null) {
        toastr.warning("Bạn chưa chọn năm", "Cảnh báo");
        return;
      }
      searchDto.year = vm.year ? vm.year : null;
      console.log("download started, you can show a wating animation");
      service.getFileImportExcelForm16AInImportReport(searchDto).then(
        function (data) {
          //is important that the data was returned as Aray Buffer
          var file = new Blob([data], {
            type:
              "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
          });
          FileSaver.saveAs(file, "FileMauImport.xlsx");
        },
        function (fail) {
          console.log("Download Error, stop animation and show error message");
          $scope.myBlobObjectFileImportExcel = [];
        }
      );
    };

    //tran huu dat them file import form 16 new
    $scope.getFileImportExcelForm16ANew = function () {
      $scope.myBlobObjectFileImportExcel = undefined;
      const searchDto = {};
      if (!vm.province || vm.province == null) {
        toastr.warning("Bạn chưa chọn Tỉnh/Thành phố", "Cảnh báo");
        return;
      }
      if (vm.province) {
        searchDto.provinceId = vm.province;
      } else {
        searchDto.provinceId = null;
      }
      if (vm.district) {
        searchDto.districtId = vm.district;
      } else {
        searchDto.districtId = null;
      }
      if (vm.ward) {
        searchDto.wardId = vm.ward;
      } else {
        searchDto.wardId = null;
      }
      if (vm.animal) {
        searchDto.animalId = vm.animal.id;
      } else {
        searchDto.animalId = null;
      }
      if (!vm.year || vm.year == null) {
        toastr.warning("Bạn chưa chọn năm", "Cảnh báo");
        return;
      }
      searchDto.year = vm.year ? vm.year : null;
      console.log("download started, you can show a wating animation");
      service.getFileImportExcelForm16ANewInImportReport(searchDto).then(
        function (data) {
          //is important that the data was returned as Aray Buffer
          var file = new Blob([data], {
            type:
              "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
          });
          FileSaver.saveAs(file, "FileMauImport.xlsx");
        },
        function (fail) {
          console.log("Download Error, stop animation and show error message");
          $scope.myBlobObjectFileImportExcel = [];
        }
      );
    };
    //tran huu dat them file import form 16 new

    vm.downloadListAnimal = function () {
      $scope.myBlobObjectDownloadAnimal = undefined;
      const searchDto = {};
      searchDto.nameOrCode = null;
      service.downloadListAnimal(searchDto).then(
        function (data) {
          //is important that the data was returned as Aray Buffer
          var file = new Blob([data], {
            type:
              "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
          });
          FileSaver.saveAs(file, "DuLieuCacLoaiDongVatHoangDa.xls");
        },
        function (fail) {
          console.log("Download Error, stop animation and show error message");
          $scope.myBlobObjectDownloadAnimal = [];
        }
      );
    };

    vm.getFileIHuongDanImportDuLieuBaoCao = function () {
      //debugger;
      $scope.myBlobObjectFileHuongDanImportDuLieuBaoCao = undefined;
      service.getFileTutorialImportDataForm16().then(
        function (data) {
          //is important that the data was returned as Aray Buffer
          var file = new Blob([data], {
            type: "application/msword",
          });
          FileSaver.saveAs(file, "HuongDanImportDuLieuBaoCao.doc");
        },
        function (fail) {
          console.log("Download Error, stop animation and show error message");
          $scope.getFileIHuongDanImportDuLieuBaoCao = [];
        }
      );
    };

    vm.downloadListAdminstrativeUnit = function () {
      $scope.myBlobObjectDownloadAdminstrativeUnit = undefined;
      const searchDto = {};
      if (!vm.province || vm.province == null) {
        toastr.warning("Bạn chưa chọn Tỉnh/Thành phố", "Cảnh báo");
        return;
      }
      if (vm.province != null) {
        searchDto.id = vm.province;
      }
      if (vm.district != null) {
        searchDto.id = vm.district;
      }
      service.downloadListAdminstrativeUnit(searchDto).then(
        function (data) {
          //is important that the data was returned as Aray Buffer
          var file = new Blob([data], {
            type:
              "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
          });
          FileSaver.saveAs(file, "DuLieuDonViHanhChinh.xls");
        },
        function (fail) {
          console.log("Download Error, stop animation and show error message");
          $scope.myBlobObjectDownloadAdminstrativeUnit = [];
        }
      );
    };

    vm.onFmsadminstrativeUnitDistSelected = function (dist) {
      if (dist != null && dist.id != null) {
        service.getAllByParentId(dist.id).then(function (data) {
          if (data != null && data.length > 0) {
            vm.adminstrativeUnits_Wards = data;
            vm.ward = null;
            vm.selectWards = null;
            vm.ward = null;
          } else {
            vm.selectWards = null;
            vm.ward = null;
            vm.adminstrativeUnits_Wards = [];
          }
        });
      } else {
        vm.selectWards = null;
        vm.ward = null;
        vm.adminstrativeUnits_Wards = [];
      }
    };
    function getAllCity() {
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
            vm.farm.administrativeUnit = null;
          } else {
            vm.selectedDist = null;
            vm.farm.administrativeUnit = null;
            vm.adminstrativeUnits_Dist = [];
            vm.adminstrativeUnits_Wards = [];
          }
        });
      } else {
        vm.selectedDist = null;
        vm.farm.administrativeUnit = null;
        vm.adminstrativeUnits_Dist = [];
        vm.adminstrativeUnits_Wards = [];
      }
    };
  }
})();
