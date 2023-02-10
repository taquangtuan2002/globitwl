/**
 * Created by bizic on 28/8/2016.
 */
 (function () {
    'use strict';

    angular.module('Education.FarmDuplicateDoubts').controller('FarmDuplicateDoubtsController', FarmDuplicateDoubtsController);

    FarmDuplicateDoubtsController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'FarmDuplicateDoubtsService',
        '$uibModal',
        'toastr',
        '$state',
        'blockUI',
        '$stateParams',
        'Utilities',
        '$translate'
    ];

    function FarmDuplicateDoubtsController($rootScope, $scope, $http, $timeout, settings, service, modal, toastr, $state, blockUI, $stateParams, utils, $translate) {
        $scope.$on('$viewContentLoaded', function () {
            // initialize core components
            App.initAjax();
        });

        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        /** declare */
        var vm = this;

        /** generate */
        vm.years = [];
        vm.yearA = 2015;
        vm.yearB = 2017;
        vm.sizes = [5, 10, 25, 50, 100];
        vm.searchDto = {};
        vm.pageSize = 25;
        vm.listData = [];
        vm.totalItems = 0;
        vm.pageIndex = 0;
        vm.currentYear = new Date().getFullYear();
        for (let year = vm.currentYear - 20; year <= vm.currentYear; year++) {
            vm.years.push({ value: year, name: year + "" });
        }

        vm.searchByPage = async function () {
            if(!vm.searchDto) {
                vm.searchDto = {};
            }
            vm.searchCountDto = Object.assign({}, vm.searchDto);
            vm.searchCountDto.pageSize = 100000000;
            vm.searchCountDto.pageIndex = 0;
            vm.searchDto.pageSize = vm.pageSize;
            vm.searchDto.pageIndex = vm.pageIndex;

            await service.searchByPage(vm.searchCountDto).then(function (data) {
                if (data) {
                    vm.totalItems = data.content.length;
                } else {
                    vm.listData = [];
                }
            })

            await service.searchByPage(vm.searchDto).then(function (data) {
                if (data) {
                    vm.listData = data.content;
                } else {
                    vm.listData = [];
                }
            });
        }

        vm.searchByPage();

        vm.modalMerge = null;
        vm.showPopupMerge = function (item) {
            vm.farmA = item.farmA;
            vm.farmB = item.farmB;
            vm.checkedFarmA = null;
            vm.checkedFarmB = null;
            vm.modalMerge = modal.open({
                animation: true,
                templateUrl: 'modal_merge_farm.html',
                scope: $scope,
                size: 'lg',
                backdrop: 'static'
            });
        }
        vm.onClickSelectedA = function(){
            if(vm.checkedFarmA && vm.checkedFarmB){
                vm.checkedFarmB= !vm.checkedFarmB;
            }
        }
        vm.onClickSelectedB = function(){
            if(vm.checkedFarmA && vm.checkedFarmB){
                vm.checkedFarmA = !vm.checkedFarmA;
            }
        }
        vm.acceptMergeFarm = function(){
            if(vm.checkedFarmA || vm.checkedFarmB){
                let farmIdMain = null;
                let duplicateFarmId = null;
                if(vm.checkedFarmA == true){
                    farmIdMain = vm.farmA.id;
                    duplicateFarmId = vm.farmB.id;
                }else if(vm.checkedFarmB == true){
                    farmIdMain = vm.farmB.id;
                    duplicateFarmId = vm.farmA.id;
                }
                service.mergeFarm(farmIdMain, duplicateFarmId).then(function(){
                    toastr.info("Hợp nhất dữ liệu thành công", "Thông báo");
                    vm.modalMerge.close();
                    vm.searchByPage();
                });
            }else{
                toastr.warning("Bạn chưa chọn cơ sở nuôi chính", "Cảnh báo");
            }
        }

    }

})();
