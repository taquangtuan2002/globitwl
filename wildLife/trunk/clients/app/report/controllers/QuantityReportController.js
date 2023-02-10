/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.Report').controller('QuantityReportController', QuantityReportController);

    QuantityReportController.$inject = [
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
    
    function QuantityReportController ($rootScope, $scope, $http, $timeout,settings,service,regionService,modal,toastr,$state,blockUI,$stateParams,utils,$translate,farmService,auService) {
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
        vm.paramDto.periodType="0";
        vm.paramDto.currentYear=today.getFullYear().toString();
        vm.paramDto.fromMonth=1;
        vm.paramDto.toMonth=12;
        vm.paramDto.fromYear=vm.currentYear;
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
		/*function getAllCity() {
			farmService.getAdminstrativeUnitsCity().then(function (data) {
                vm.adminstrativeUnits_City = data;
            },function(respone){
            	//if failed do something 
            });
		}*/
        
		 
		vm.getCombo();
		function validate(){			
			 return true;
		}
        vm.getQuantityReport=function (){
        	vm.paramDto.groupByItems=[];
//			if(vm.region!=null)
//				vm.paramDto.regionId=vm.region.id;
//			if(vm.selectedCity!=null)
//				vm.paramDto.provinceId=vm.selectedCity.id;
//			if(vm.selectedDist!=null)
//				vm.paramDto.districtId=vm.selectedDist.id;			
        	if(vm.checkedGroupByItems!=null && vm.checkedGroupByItems.length>0){
        		for (var i = 0; i < vm.checkedGroupByItems.length; i++) {
        			vm.paramDto.groupByItems.push(vm.checkedGroupByItems[i].value);
				}
        	};
        	console.log(vm.paramDto.groupByItems.includes('district'));
            service.getQuantityReport(vm.paramDto).then(function(data){
            	vm.results=data;
            	vm.bsTableControl.options.data=vm.results;
            	vm.bsTableControl.options.columns=getTableDefinition();
            	//Pivot here     
//            	var pivot = new WebDataRocks({
//            	    container: "#wdr-component",
//            	    height: 530,
//            	    report: {
//            	        dataSource: {
//            	            data: vm.results
//            	        },
//            	        "slice": {
//            	            "rows": [{
//            	                    "uniqueName": "yearReport",
//            	                    "sort": "asc",
//            	                    "caption":"Năm"            	                    
//            	                }
//            	            ],
//            	            "columns": [{
//            	                    "uniqueName": "regionName",
//            	                    "sort": "asc",
//            	                    "caption":"Vùng"
//            	            }],
//            	            "measures": [{
//            	                "uniqueName": "quantity",
//            	                //"aggregation": "sum",
//            	                "caption":"Số lượng"
//            	            }
////            	            ,{
////            	                "uniqueName": "amount",
////            	                //"aggregation": "sum",
////            	                "caption":"Khối lượng"
////            	            }
//            	            ],
////	            	        "drills": {
////	            	            "drillAll": false,
////	            	            "rows": [{
////	            	                "tuple": [
////	            	                    "animal"
////	            	                ]
////	            	            }]
////	            	        }
////            	        	,
//            	            "expands": {
//            	                "rows": [{
//            	                    "tuple": [
//            	                        "animal"
//            	                    ]
//            	                }]
//            	            }
//            	        },
//            	        options: {
//            	            grid: {
//            	                title: "Báo cáo số lượng",
//            	                type:"compact"
//            	            }
//            	        }
//            	    }
//            	});
            	
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
						vm.paramDto.provinceId=null;
						vm.paramDto.districtId=null;
						vm.paramDto.wardId=null;
					}
					else {
						vm.paramDto.provinceId=null;
						vm.paramDto.districtId=null;
						vm.paramDto.wardId=null;
						vm.selectedCity=null;
			        	vm.selectedDist = null;
			        	vm.selectWards = null;
						vm.adminstrativeUnits_City=[];
		                vm.adminstrativeUnits_Dist = [];
						vm.adminstrativeUnits_Wards = [];
					}				
					
	            });
			}else {
						vm.paramDto.provinceId=null;
						vm.paramDto.districtId=null;
						vm.paramDto.wardId=null;
						vm.selectedCity=null;
			        	vm.selectedDist = null;
			        	vm.selectWards = null;
						vm.adminstrativeUnits_City=[];
		                vm.adminstrativeUnits_Dist = [];
						vm.adminstrativeUnits_Wards = [];
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
						vm.paramDto.districtId=null;
						vm.paramDto.wardId=null;
					}
					else {
						vm.paramDto.districtId=null;
						vm.paramDto.wardId=null;
			        	vm.selectedDist = null;
			        	vm.selectWards = null;
		                vm.adminstrativeUnits_Dist = [];
						vm.adminstrativeUnits_Wards = [];
					}
	            });
			}
			else {
				vm.paramDto.districtId=null;
				vm.paramDto.wardId=null;
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
						vm.paramDto.wardId=null;
			        	vm.selectWards = null;
		                vm.adminstrativeUnits_Wards = [];
					}
					vm.paramDto.wardId=null;
					vm.selectWards=null;
	            });
			}
			else {
				vm.paramDto.wardId=null;
	        	vm.selectWards = null;
				vm.adminstrativeUnits_Wards = [];
			}
		}
        $scope.labels = ['2006', '2007', '2008', '2009', '2010', '2011', '2012','2013'];
        $scope.series = ['Series A', 'Series B', 'Series C', 'Series D', 'Series E', 'Series F','Series A', 'Series B', 'Series C', 'Series D', 'Series E', 'Series F'
        	,'Series A', 'Series B', 'Series C', 'Series D', 'Series E', 'Series F','Series A', 'Series B', 'Series C', 'Series D', 'Series E', 'Series F'];

        $scope.data = [
          [65, 59, 80, 81, 56, 55, 40,55],
          [28, 48, 40, 19, 86, 27, 90,99],
          [28, 48, 40, 19, 86, 27, 90,99],
          [28, 48, 40, 19, 86, 27, 90,99],
          [28, 48, 40, 19, 86, 27, 90,99],
          [28, 48, 40, 19, 86, 27, 90,99],
          [65, 59, 80, 81, 56, 55, 40,55],
          [28, 48, 40, 19, 86, 27, 90,99],
          [28, 48, 40, 19, 86, 27, 90,99],
          [28, 48, 40, 19, 86, 27, 90,99],
          [28, 48, 40, 19, 86, 27, 90,99],
          [28, 48, 40, 19, 86, 27, 90,99],
          [65, 59, 80, 81, 56, 55, 40,55],
          [28, 48, 40, 19, 86, 27, 90,99],
          [28, 48, 40, 19, 86, 27, 90,99],
          [28, 48, 40, 19, 86, 27, 90,99],
          [28, 48, 40, 19, 86, 27, 90,99],
          [28, 48, 40, 19, 86, 27, 90,99],
          [65, 59, 80, 81, 56, 55, 40,55],
          [28, 48, 40, 19, 86, 27, 90,99],
          [28, 48, 40, 19, 86, 27, 90,99],
          [28, 48, 40, 19, 86, 27, 90,99],
          [28, 48, 40, 19, 86, 27, -90,99],
          [28, 48, 40, 19, 86, 27, 90,99]
        ];        
        
        console.log(service.getTableDefinition());	
        
        vm.bsTableControl = {
                options: {
                    data: vm.results,
                    idField: 'id',
                    sortable: true,
                    striped: true,
                    maintainSelected: true,
                    clickToSelect: false,
                    showColumns: false,
                    showToggle: false,
                    pagination: false,
                    pageSize: vm.pageSize,
                    pageList: [5, 10, 25, 50, 100],
                    locale: settings.locale,
                    sidePagination: 'server',
                    columns: getTableDefinition(),
                    onCheck: function (row, $element) {
                        $scope.$apply(function () {
                            vm.selectedFarmSize.push(row);
                        });
                    },
                    onCheckAll: function (rows) {
                        $scope.$apply(function () {
                            vm.selectedFarmSize = rows;
                        });
                    },
                    onUncheck: function (row, $element) {
                        var index = utils.indexOf(row, vm.selectedFarmSize);
                        if (index >= 0) {
                            $scope.$apply(function () {
                                vm.selectedFarmSize.splice(index, 1);
                            });
                            
                        }
                    },
                    onUncheckAll: function (rows) {
                        $scope.$apply(function () {
                            vm.selectedFarmSize = [];
                        });
                    },
                    onPageChange: function (index, pageSize) {
                        vm.pageSize = pageSize;
                        vm.pageIndex = index;
    					vm.findBy();
                        //vm.getAllAnimalType();
                    }
                }
            };
        function getTableDefinition() {

            var _tableInput = function (value, row, index, field) {
                return '<input  type="text"  value="' + value + '" data-field="row.mark"/>';

            };

            var _cellNowrap = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap' }
                };
            };

            var _cellNowrap2 = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap'}
                };
            };
            var _formatterName = function (value, row, index, field){
                if(value && value.name){
                    return value.name;
                }
                return '';
            }
            var _dateFormatter = function (value, row, index) {
                if (!value) {
                    return '';
                }
                return moment(value).format('DD/MM/YYYY');
            };
            var _operationColStyle = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap', 'text-align': 'center' }
                };
            };
            

            return [
            	{
                    field: 'monthReport',
                    title: '{{"report.month" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
//                    formatter:_formatterName,
                    switchable: true,
                    visible:(vm.paramDto!=null && vm.paramDto && vm.paramDto.periodType=="1")
                },{
                    field: 'yearReport',
                    title: '{{"report.year" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
//                    formatter:_formatterName,
                    switchable: true,
                },{
                    field: 'quantity',
                    title: '{{"importAnimal.quantity"| translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                },
//                {
//                    field: 'amount',
//                    title: '{{"exportAnimal.amount"| translate}}',
//                    sortable: true,
//                    switchable: false,
//                    cellStyle: _cellNowrap
//                },
                {
                    field: 'animal',
                    title: '{{"importAnimal.animal.name" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter:_formatterName,
                    switchable: true,
                    visible:(vm.paramDto!=null && vm.paramDto.groupByItems!=null && vm.paramDto.groupByItems.includes('animalId'))
                }, 
                {
                    field: 'parent',
                    title: '{{"farm.animal" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter:_formatterName,
                    switchable: true,
                    visible:(vm.paramDto!=null && vm.paramDto.groupByItems!=null && vm.paramDto.groupByItems.includes('animalParent'))
                }, 
                {
                    field: 'animalType',
                    title: '{{"animalType.information" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter:_formatterName,
                    switchable: true,
                    visible:(vm.paramDto!=null && vm.paramDto.groupByItems!=null && vm.paramDto.groupByItems.includes('animalType'))
                },{
                    field: 'farm',
                    title: '{{"importAnimal.farm.name" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter:_formatterName,
                    switchable: true,
                    visible:(vm.paramDto!=null && vm.paramDto.groupByItems!=null && vm.paramDto.groupByItems.includes('farm'))
                },
                {
                    field: 'ward',
                    title: '{{"farm.ward" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter:_formatterName,
                    switchable: true,
                    visible:(vm.paramDto!=null && vm.paramDto.groupByItems!=null && vm.paramDto.groupByItems.includes('ward'))
                },
                {
                    field: 'district',
                    title: '{{"farm.district" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter:_formatterName,
                    switchable: true,
                    visible:(vm.paramDto!=null && vm.paramDto.groupByItems!=null && vm.paramDto.groupByItems.includes('district'))
                },
                {
                    field: 'province',
                    title: '{{"administrativeUnit.province" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter:_formatterName,
                    switchable: true,
                    visible:(vm.paramDto!=null && vm.paramDto.groupByItems!=null && vm.paramDto.groupByItems.includes('province'))
                },
                {
                    field: 'region',
                    title: '{{"region.information" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter:_formatterName,
                    switchable: true,
                    visible:(vm.paramDto!=null && vm.paramDto.groupByItems!=null && vm.paramDto.groupByItems.includes('region'))
                }
            ];
        };
    }

})();
