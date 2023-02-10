/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.Report').controller('ReportCurrentStatusByAnimalController', ReportCurrentStatusByAnimalController);

    ReportCurrentStatusByAnimalController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'ReportCurrentStatusByAnimalService',
        'FmsRegionService',
        '$uibModal',
        'toastr',
        '$state',
        'blockUI',
        '$stateParams',
        'Utilities',
        '$translate',
        'FarmService',
        'FmsAdministrativeService',
        'AnimalService'
    ];

    function ReportCurrentStatusByAnimalController($rootScope, $scope, $http, $timeout, settings, service, regionService, modal, toastr, $state, blockUI, $stateParams, utils, $translate, farmService, auService, animalService) {
        $scope.$on('$viewContentLoaded', function () {
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
        vm.isCallReport = false;
        // $rootScope.$on('$translateChangeSuccess', function(event, current, previous) {
        // 	if(vm.isCallReport){
        // 		vm.getQuantityReport();
        // 	}
        // });
        // blockUI.stop();
        // blockUI.start();
        vm.recordId = null;
        vm.pageIndex = 1;
        vm.pageSize = 25;
        vm.farmSizes = []
        vm.selectYear = vm.currentYear;
        $scope.years = [];
        for (var i = 2000; i <= vm.currentYear; i++) {
            $scope.years.push({ value: i, name: i + "" });
        }

        /** generate */


        vm.paramDto = {};
        vm.paramDto.periodType = "1";
        vm.paramDto.currentYear = today.getFullYear().toString();
        vm.paramDto.fromMonth = today.getMonth();
        vm.paramDto.toMonth = today.getMonth();

        vm.paramDto.fromMonth = 1;
        vm.paramDto.toMonth = 12;
        vm.paramDto.fromYear = vm.currentYear;
        vm.paramDto.toYear = vm.currentYear;
        vm.animalId = null;
        vm.results = [];

        vm.isRole = false;

        animalService.getAll().then(function (data) {
            vm.animals = data;
        }).catch(function (error) {
            vm.animals = [];
        });

        vm.reportByAnimalsCurrent = function () {
            vm.searchDto = {};
            if (vm.animal != null) {
                vm.searchDto.animalId = vm.animal.id;
            }
            if (vm.searchDto) {
                service.reportByAnimalsCurrent(vm.searchDto).then(function (data) {
                    vm.results = data;
                });
            } else {
                service.reportByAnimalsCurrent(0).then(function (data) {
                    vm.results = data;
                });
                // toastr.warning("Phải chọn loài nuôi");
            }
        }
        vm.selecteAnimal = function () {
            if (vm.animalId) {
                vm.reportByAnimalsCurrent();
            } else {
                vm.reportByAnimalsCurrent();
            }

        }


    }

})();
