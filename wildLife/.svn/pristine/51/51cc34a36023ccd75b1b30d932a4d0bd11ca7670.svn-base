/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.InformationAnimal').controller('FluctuationHerdByMonthController', FluctuationHerdByMonthController);

    FluctuationHerdByMonthController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'FluctuationHerdByMonthService',
        '$uibModal',
        'toastr',
        '$state',
        'blockUI',
        '$stateParams',
        'Utilities',
        '$translate',
        'AnimalService'
    ];
    
    function FluctuationHerdByMonthController ($rootScope, $scope, $http, $timeout,settings,service,modal,toastr,$state,blockUI,$stateParams,utils,$translate, animalService) {
        $scope.$on('$viewContentLoaded', function() {
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
        vm.pageSize = 25;
        vm.drugs = []
        vm.modalDrug={};
        vm.drug = {isNew : false};
        vm.modalConfirmDelete = {};
        vm.selectedDrug = [];
        vm.count = 0;
		vm.searchDto={};
		vm.viewCheckDuplicateCode = {};
        vm.tempCode = '';
        vm.paramDto = {};
        vm.toDay = new Date();
        vm.paramDto = {};
        vm.paramDto.currentYear = vm.toDay.getFullYear();
        vm.currentYear = vm.toDay.getFullYear();
        vm.paramDto.month = vm.toDay.getMonth()+1;
        $scope.years = [];
		for(var i=2000; i <= vm.currentYear; i++) {
			$scope.years.push(i);
		}
        vm.getListAnimalParent = function() {
			animalService.getListParent().then(function(data){
				vm.animalParents = data;
				vm.animalParents = vm.animalParents.filter(el => el.code != settings.BEAR_CODE);
            });
        };
        vm.months = settings.MONTHS;
        vm.getListAnimalParent();
        vm.clickTest = function(){
            if(vm.paramDto)
			vm.searchDto.animalParentId = vm.paramDto.animalParentId;
			vm.searchDto.year = vm.paramDto.currentYear;
			vm.searchDto.month = vm.paramDto.month - 1;
			service.getTotalQuantityAnimal(vm.searchDto).then(function(data){
				vm.listData = data;
				vm.listData.forEach(el=>{
					el.productTargetName = "";
					if(el.productTargets){
						let count = 0;
						el.productTargets.forEach(target=>{
							if(count > 1){
								el.productTargetName +=', '
							}
							el.productTargetName += '<span class="bold text-secondary" title="'+target.name+'">';
							el.productTargetName += target.symbol;
							el.productTargetName += '</span>';
							count++;
						});
					}
					el.originName = "";
					if(el.originals){
						let count = 0;
						el.originals.forEach(origin=>{
							if(count > 1){
								el.originName +=', '
							}
							el.originName += '<span class="bold text-secondary" title="'+origin.name+'">';
							el.originName += origin.symbol;
							el.originName += '</span>';
							count++;
						});
					}
					
				});
			});
		}
		vm.clickTest();

    }

})();
