/**
 * Created by bizic on 28/8/2016.
 */
(function () {
  "use strict";

  angular
    .module("Education.BiologicalClass")
    .controller("BiologicalClassController", BiologicalClassController);

  BiologicalClassController.$inject = [
    "$rootScope",
    "$scope",
    "$http",
    "$timeout",
    "settings",
    "BiologicalClassService",
    "$uibModal",
    "toastr",
    "$state",
    "blockUI",
    "$stateParams",
    "Utilities",
    "$translate",
    'Upload'
  ];

  function BiologicalClassController(
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
    utils,
    $translate,
    Upload
  ) {
    $scope.$on("$viewContentLoaded", function () {
      App.initAjax();
    });

    $rootScope.settings.layout.pageContentWhite = true;
    $rootScope.settings.layout.pageBodySolid = false;
    $rootScope.settings.layout.pageSidebarClosed = false;

    var vm = this;
    vm.pageIndex = 1;
    vm.pageSize = 25;
    vm.pageIndexSub = 1;
    vm.pageSizeSub = 25;
    vm.count = 0;
    vm.searchDto = {};
    vm.entity = {};
    vm.recordId = null;
    vm.currentType = null;
    vm.searchDtoSub = {};
    vm.selected = [];
    vm.listType = [
      {id: 1, name: "Lớp"},
      {id: 2, name: "Bộ"},
      {id: 3, name: "Họ"}
    ]

    $scope.MAX_FILE_SIZE = '5MB';
    $scope.f = null;
    $scope.errFile = null;

    $scope.uploadFiles = function(file, errFiles) {
        $scope.f = file;
        $scope.errFile = errFiles && errFiles[0];
    };


    vm.bsTableControl = {
      options: {
        data: vm.listData,
        idField: "id",
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
        sidePagination: "server",
        columns: service.getTableDefinition(),
        onPageChange: function (index, pageSize) {
          vm.pageSize = pageSize;
          vm.pageIndex = index;
          vm.findBy();
        },
      },
    };

    vm.enterSearch = function () {
      if (event.keyCode == 13) {
        //Phím Enter

        vm.searchByCode();
      }
    };
    vm.searchByCode = function () {
      vm.pageIndex = 1;
      vm.bsTableControl.state.pageNumber = 1;
      vm.findBy();
    };

    vm.findBy = function () {
      vm.searchDto.pageIndex = vm.pageIndex;
      vm.searchDto.pageSize = vm.pageSize;

      service.searchByPage(vm.searchDto).then(function (data) {
        vm.listData = data.content;
        vm.bsTableControl.options.data = vm.listData;
        vm.bsTableControl.options.totalRows = data.totalElements;
      });
    };

    vm.findBy();

    vm.onSelectType = () => {
      vm.searchByCode();
    }

    vm.openCreateForm = function () {
      vm.entity = { isNew: true };

      vm.openEditForm = modal.open({
        animation: true,
        templateUrl: "form_edit.html",
        scope: $scope,
        backdrop: "static",
        keyboard: false,
        size: "md",
      });
    };

    $scope.openEditForm = function (id) {
      if (id) {
        service.getById(id).then(function (data) {
          vm.entity = data;
          vm.entity.isNew = false;
        });
        vm.openEditForm = modal.open({
          animation: true,
          templateUrl: "form_edit.html",
          scope: $scope,
          backdrop: "static",
          keyboard: false,
          size: "md",
        });
      }
    };

    vm.saveEntity = function () {
      if(!vm.entity) {
        toastr.error("Có lỗi xảy ra", "Thông báo");
        return;
      }
      if(!vm.entity.type) {
        toastr.warning("Vui lòng chọn loại", "Thông báo");
        return;
      }
      if(!vm.entity.code && vm.entity.type == 1) {
        toastr.warning("Vui lòng nhập mã", "Thông báo");
        return;
      }
      if(vm.entity.type != 1 && !vm.entity.parent) {
        toastr.warning("Vui lòng chọn loại cha", "Thông báo");
        return;
      }
      if(vm.entity.id) {
        service.saveOrUpdate(vm.entity).then(data => {
          if(data) {
            vm.searchByCode();
            vm.openEditForm.close();
            toastr.info("Cập nhật thành công", "Thông báo");
          } else {
            toastr.error("Có lỗi xảy ra", "Thông báo");
          }
        })
      } else {
        service.saveOrUpdate(vm.entity).then(data => {
          if(data) {
            vm.searchByCode();
            vm.openEditForm.close();
            toastr.info("Thêm mới thành công", "Thông báo");
          } else {
            toastr.error("Có lỗi xảy ra", "Thông báo");
          }
        })
      }
      
    };

    vm.confirmDelete = function () {
      if (vm.recordId) {
        service.deleteById(vm.recordId).then((data) => {
          if (data) {
            toastr.info("Đã xoá thành công", "Thông báo")
            vm.searchByCode();
            vm.modalConfirmDelete.close();
          } else {
            toastr.warning("Bản ghi đã được sử dụng ở chỗ khác", "Thông báo")
          }
        });
      }
    };
    $scope.deleteById = function (id) {
      if (id) {
        vm.recordId = id;
      } else {
        vm.recordId = null;
      }
      vm.modalConfirmDelete = modal.open({
        animation: true,
        templateUrl: "confirm_delete_record.html",
        scope: $scope,
        backdrop: "static",
        keyboard: false,
        size: "md",
      });
    };

    vm.bsTableSelectControl = {
      options: {
          data: vm.listDataSub,
          idField: 'id',
          sortable: true,
          striped: true,
          maintainSelected: true,
          clickToSelect: false,
          showColumns: false,
          singleSelect: true,
          showToggle: false,
          pagination: true,
          pageSize: vm.pageSizeSub,
          pageList: [5, 10, 25, 50, 100],
          locale: settings.locale,
          sidePagination: 'server',
          columns: service.getTableSelectDefinition(),
          onCheck: function (row, $element) {
              if( vm.selected!=null){
                  vm.selected=[];
              }
              $scope.$apply(function () {
                  vm.selected.push(row);
              });
          },
          onUncheck: function (row, $element) {
              if( vm.selected!=null){
                  vm.selected=[];
              }
          },
          onPageChange: function (index, pageSize) {
              vm.pageSizeSub = pageSize;
              vm.pageIndexSub = index;
              vm.getSelectParent();
          }
      }
    };

    vm.enterSearchSelect = function () {
      if (event.keyCode == 13) {
        //Phím Enter

        vm.getSelectParent();
      }
    };

    vm.getSelectParent = function () {
      // vm.searchDtoSub = {};5
      vm.searchDtoSub.pageIndex = vm.pageIndexSub;
      vm.searchDtoSub.pageSize = vm.pageSizeSub;
      vm.searchDtoSub.currentType = vm.currentType;

      service.searchByPage(vm.searchDtoSub).then(function (data) {
        vm.listDataSub = data.content;
        vm.bsTableSelectControl.options.data = vm.listDataSub;
        vm.bsTableSelectControl.options.totalRows = data.totalElements;
      });
    };

    vm.showPopupSelectParent = function (entity) {
      if(!entity || !entity.type) {
        toastr.warning("Vui lòng chọn loại", "Thông báo");
        return;
      }
      vm.currentType = entity.type;
      vm.getSelectParent();

      vm.selectParentModal = modal.open({
          animation: true,
          templateUrl: 'select_parent_modal.html',
          scope: $scope,
          size: 'md'
      });
    }

    vm.agree=function () {
      if(vm.entity.parent==null){
        vm.entity.parent={};
      }
      vm.entity.parent=vm.selected[0];
      
      vm.selectParentModal.close();
    }

    vm.onChangeType = () => {
      vm.entity.parent = null;
    }

    vm.startUploadFile = function(file) {
      if (file) {
        file.upload = Upload.upload({
            url: settings.api.baseUrl + settings.api.apiPrefix + 'file/importBiologicalClassFile',
            data: {uploadfile: file}
        });

        file.upload.then(function (response) {
          file.result = response.data;
          vm.findBy();
          toastr.info($translate.instant("upload.importSuccess"), $translate.instant("toastr.info"));
        },function errorCallback(response) {
            toastr.error($translate.instant("upload.importError"), $translate.instant("toastr.error"));
        });
      }
    };

    vm.importBiological = () => {
      var modalInstance = modal.open({
        animation: true,
        templateUrl: 'import_modal.html',
        scope: $scope,
        size: 'md'
      });
    
      $scope.f = null;
      $scope.errFile = null;

      modalInstance.result.then(function (confirm) {
        if (confirm == 'yes') {
            vm.startUploadFile($scope.f);
        }
      }, function () {
        console.log("cancel");
      });
    }

  }
})();
