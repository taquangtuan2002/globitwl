/**
 * Created by bizic on 28/8/2016.
 */
(function () {
  "use strict";

  angular
    .module("Education.Report").controller("AtlasGrowPrimanController", AtlasGrowPrimanController);

  AtlasGrowPrimanController.$inject = [
    "$rootScope",
    "$scope",
    "$http",
    "$timeout",
    "settings",
    "$uibModal",
    "toastr",
    "$state",
    "blockUI",
    "$stateParams",
    "Utilities",
    "$translate",
  ];

  function AtlasGrowPrimanController(
    $rootScope,
    $scope,
    $http,
    $timeout,
    settings,
    modal,
    toastr,
    $state,
    blockUI,
    $stateParams,
    utils,
    $translate,
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
    console.log($rootScope);
    
    // Education.API_SERVER_URL;
  }
})();
