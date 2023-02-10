/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.Report').controller('ProvinceImportReportController', ProvinceImportReportController);

    ProvinceImportReportController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'QuantityReportService',
		'FmsRegionService',		
        '$uibModal',
        'toastr',
        '$state',
        'blockUI',
        '$stateParams',
        'Utilities',
		'$translate',
		'FarmService',
		'FmsAdministrativeService'
		
    ];
    
    function ProvinceImportReportController ($rootScope, $scope, $http, $timeout,settings,service,regionService,modal,toastr,$state,blockUI,$stateParams,utils,$translate,farmService,auService) {
        $scope.$on('$viewContentLoaded', function() {
            // initialize core components
            App.initAjax();
        });

        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        /** declare */
        var vm = this;
        var today = new Date();
        vm.currentYear=today.getFullYear();
        
        // blockUI.stop();
        // blockUI.start();
        vm.recordId = null;
        vm.pageIndex = 1;
        vm.pageSize = 25;
        vm.farmSizes = []
        vm.modalFarmSize={};
        vm.farmSize = {isNew : false};
        vm.modalConfirmDelete = {};
        vm.selectedFarmSize = [];
        vm.count = 0;
		vm.searchDto={};
		vm.viewCheckDuplicateCode = {};
        vm.tempCode = '';
        vm.results=[];
        
        vm.selectedCity={};
        $scope.labels = ["Toàn tỉnh"];
        $scope.series = [];
        $scope.data = [];
        vm.viewType="chart";
		$scope.years = [];
		for(var i=2000; i <= vm.currentYear; i++) {
			$scope.years.push(i);
		}
		vm.months=[
        	{
        		name:"Tháng 1",
        		value:1
        	},
        	{
        		name:"Tháng 2",
        		value:2
        	},        	
        	{
        		name:"Tháng 3",
        		value:3
        	},
        	{
        		name:"Tháng 4",
        		value:4
        	},
        	{
        		name:"Tháng 5",
        		value:5
        	},
        	{
        		name:"Tháng 6",
        		value:6
        	},
        	{
        		name:"Tháng 7",
        		value:7
        	},
        	{
        		name:"Tháng 8",
        		value:8
        	},
        	{
        		name:"Tháng 9",
        		value:9
        	},
        	{
        		name:"Tháng 10",
        		value:10
        	},
        	{
        		name:"Tháng 11",
        		value:11
        	},
			{
        		name:"Tháng 12",
        		value:12
        	}
        ];
        
        /** generate */
		
		
        vm.paramDto={};
        vm.paramDto.type=1;
        vm.paramDto.reportType="1";
        vm.paramDto.periodType="0";
        vm.paramDto.currentYear=today.getFullYear().toString();
        vm.paramDto.fromMonth=today.getMonth();
        vm.paramDto.toMonth=today.getMonth();
        vm.paramDto.fromYear=vm.currentYear-1;
        vm.paramDto.toYear=vm.currentYear;
        vm.groupByItems=[
//        	{
//        		name:"Mã lô",
//        		value:"batchCode"
//        	},
        	{
        		name:"động vật",
        		value:"animalId"
        	},        	
        	{
        		name:"Loại động vật",
        		value:"animalParent"
        	},
        	{
        		name:"Loại động vật",
        		value:"animalType"
        	},
        	{
        		name:"Cơ sở nuôi",
        		value:"farm"
        	},
//        	{
//        		name:"Nguồn gốc",
//        		value:"original"
//        	},
        	{
        		name:"Xã",
        		value:"ward"
        	},
        	{
        		name:"Huyện",
        		value:"district"
        	},
        	{
        		name:"Tỉnh",
        		value:"province"
        	},
        	{
        		name:"Vùng sinh thái",
        		value:"region"
        	}//,
//        	{
//        		name:"Hướng sản phẩm",
//        		value:"productTarget"
//        	}
        ];
        /** bussiness */

		vm.getCombo = function () {
            regionService.getAll().then(function (data) {
                console.log()
                if (data && data.length > 0) {
                    vm.regions = data;
                } else {
                    vm.regions = [];
                }
            });
        }
		vm.getAllCity = function() {
			auService.getAllCity().then(function(data) {
                vm.adminstrativeUnits_City = data;                
            });
        }
        vm.getAllCity();
        
		 
		vm.getCombo();
		function validate(){
			if(vm.paramDto.periodType==0 && vm.paramDto.fromYear==vm.paramDto.toYear){
				toastr.warning('{{"report.compareTimeErr" | translate}}','{{"toastr.warning" | translate}}');
				return false;
			}
			if(vm.paramDto.periodType==1 && vm.paramDto.fromYear==vm.paramDto.toYear && vm.paramDto.fromMonth==vm.paramDto.toMonth){
				toastr.warning('{{"report.compareTimeErr" | translate}}','{{"toastr.warning" | translate}}');
				return false;
			}
			return true;
		}
		
        vm.getQuantityReport=function (){
        	if(!validate()){
        		return;
        	}
        	vm.paramDto.groupByItems=[];
        	vm.rows=[];
        	if(vm.paramDto.periodType=='0'){
        		vm.rowsPivot = [{
                    "uniqueName": "yearReport",
                    "sort": "asc",
                    "caption":"Năm"            	                    
                }];
        		vm.rows.push("yearReport");
        	}
        	else if(vm.paramDto.periodType=='1'){
        		vm.rowsPivot = [{
                    "uniqueName": "monthInYear",
                    "sort": "asc",
                    "caption":"Tháng"            	                    
                }];
        		vm.rows.push("monthReport");
        		vm.rows.push("yearReport");
        	}
        	vm.paramDto.groupByItems.push('animalParent');
        	console.log(vm.paramDto.groupByItems.includes('district'));
        	
            service.getQuantityReport(vm.paramDto).then(function(data){
            	vm.results=data;
            	
            	//Pivot master
//            	google.load("visualization", "1", {packages:["corechart", "charteditor"]});
//            	var derivers = $.pivotUtilities.derivers;
//                var renderers = $.extend($.pivotUtilities.renderers,
//                    $.pivotUtilities.gchart_renderers);
//                $("#output").empty();
//                $("#output").pivotUI(vm.results, {
//                    renderers: renderers,
//                    cols: ["parentlName"], 
//                    rows: vm.rows,
//                    vals:["quantity"],
//                    rendererName: "Horizontal Stacked Bar Chart",
//                    rowOrder: "value_z_to_a", colOrder: "value_z_to_a",
//                    aggregatorName: "Sum",
//                    rendererOptions: {
//                        c3: { data: {colors: {
//                            Liberal: '#dc3912', Conservative: '#3366cc', NDP: '#ff9900',
//                            Green:'#109618', 'Bloc Quebecois': '#990099'
//                        }}}
//                    }
//                });
            	
            	//Pivot here     
            	var pivot = new WebDataRocks({
            	    container: "#wdr-component",
            	    toolbar: true,
            	    height: 530,
            	    report: {
            	        dataSource: {
            	            data: vm.results
            	        },
            	        "slice": {
            	            "rows": [{
        	                    "uniqueName": "parentlName",
        	                    "sort": "asc",
        	                    "caption":"Loại động vật"
            	            }],
            	            "columns": vm.rowsPivot,
            	            "measures": [{
            	                "uniqueName": "quantity",
            	                //"aggregation": "sum",
            	                "caption":"Số lượng"
            	            }
//            	            ,{
//            	                "uniqueName": "amount",
//            	                //"aggregation": "sum",
//            	                "caption":"Khối lượng"
//            	            }
            	            ],
            	        },
            	        options: {
            	            grid: {
            	                title: "Báo cáo sao sánh tổng đàn cấp tỉnh",
            	                type:"compact"
            	            }
            	        }
            	    }
            		,
        	        reportcomplete: function() {
        	        	pivot.off("reportcomplete");
        	        	createChart();
        	        }
            	});
            	//createChart
            	function createChart() {
        		    pivot.highcharts.getData({
        		        type: "column"
        		    }, function(data) {
        		        Highcharts.chart('highchartsContainer', data);
        		    }, function(data) {
        		        Highcharts.chart('highchartsContainer', data);
        		    });
        		}
            	//Chart here
                if (vm.results != null && vm.results.length > 0) {
                    $scope.series = [];
                    $scope.data = [];                    
                    for (var i = 0; i < vm.results.length; i++) {
                    	var quantityArray=[];
                        $scope.series.push(vm.results[i].parentlName);
                        quantityArray.push(vm.results[i].quantity);
                        $scope.data.push(quantityArray);
					}                    
            	}
            });
        }
		vm.onFmsRegionSelected=function(region){
			if(region!=null && region.id!=null){
				auService.getAllCityByRegion(region.id).then(function (data) {
					if (data != null && data.length > 0) {
		                vm.adminstrativeUnits_City = data;
						vm.selectedCity=null;
		                vm.selectedDist = null;
		                vm.selectWards=null;
					}
					else {
						vm.selectedCity=null;
			        	vm.selectedDist = null;
			        	vm.selectWards = null;
						 vm.adminstrativeUnits_City=[];
		                vm.adminstrativeUnits_Dist = [];
						vm.adminstrativeUnits_Wards = [];
					}
	            });
			}
		}
		
		vm.onFmsadminstrativeUnitCitySelected = function(city) {
			vm.selectedCity = city;
			if (city != null && city.id != null) {
				farmService.getAllByParentId(city.id).then(function (data) {
					if (data != null && data.length > 0) {
		                vm.adminstrativeUnits_Dist = data;
		                vm.selectedDist = null;
		                vm.selectWards=null;
					}
					else {
			        	vm.selectedDist = null;
			        	vm.selectWards = null;
		                vm.adminstrativeUnits_Dist = [];
						vm.adminstrativeUnits_Wards = [];
					}
	            });
			}
			else {
	        	vm.selectedDist = null;
	        	vm.selectWards = null;
                vm.adminstrativeUnits_Dist = [];
				vm.adminstrativeUnits_Wards = [];
			}
		}
        
        vm.onFmsadminstrativeUnitDistSelected = function(dist) {
			if (dist != null && dist.id != null) {
				farmService.getAllByParentId(dist.id).then(function (data) {
					if (data != null && data.length > 0) {
		                vm.adminstrativeUnits_Wards = data;
					}
					else {
			        	vm.selectWards = null;
		                vm.adminstrativeUnits_Wards = [];
					}
					vm.selectWards=null;
	            });
			}
			else {
	        	vm.selectWards = null;
				vm.adminstrativeUnits_Wards = [];
			}
		}
        console.log(service.getTableDefinition());
        
        
    }

})();
