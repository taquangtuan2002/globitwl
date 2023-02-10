/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.Report').controller('RegionDetailQuantityReportController', RegionDetailQuantityReportController);

    RegionDetailQuantityReportController.$inject = [
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
		'FmsAdministrativeService',
		'AnimalService'
    ];
    
    function RegionDetailQuantityReportController ($rootScope, $scope, $http, $timeout,settings,service,regionService,modal,toastr,$state,blockUI,$stateParams,utils,$translate,farmService,auService,animalService) {
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
        vm.viewType="chart";
		vm.title=null;
		vm.regionName=null;
		vm.isShow=true;
		
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
        		name:"Động vật",
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
        		name:"Cơ sở",
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
		vm.isRole=false;
		
		//------Start--Phân quyền theo user đăng nhập-----------
		/*blockUI.start();
        service.getCurrentUser().then(function (data) {
            console.log(data);
            vm.user=data;
            var roles = data.roles;
           
            if(roles!=null && roles.length>0){
                for(var i=0; i<roles.length;i++){
                    if (roles[i]!=null &&  roles[i].authority && (roles[i].authority.toLowerCase() == 'role_admin' ||roles[i].authority.toLowerCase() == 'role_dlp')) {
                        vm.isRole = true;
						break;
                    }					
                }
            }else{
                vm.isRole=false;
				
            }
			if(vm.isRole){//trường hợp admin và dlp thì được xem tất cả các cơ sở chăn nuôi
				vm.getCombo();
			}else{// trường hợp này thì phân quyền theo user
				if(vm.isRole==false){
					getAdministrativeUnitDtoByLoginUser();
					
				}
				
			}
            blockUI.stop();
        });
		
		//load user administrativeunit
		function getAdministrativeUnitDtoByLoginUser() {
            service.getAdministrativeUnitDtoByLoginUser().then(function(data) {
                vm.adminstrativeUnits = data;
				if(vm.adminstrativeUnits!=null && vm.adminstrativeUnits.length>0){
					getDataByCombobox(vm.adminstrativeUnits[0]);
					
				}
            });
        }
		function getDataByCombobox(administrativeUnit) {
			if( vm.paramDto==null){
                vm.paramDto={};
            }
			if(administrativeUnit.parentDto==null){
				
				vm.paramDto.regionId=administrativeUnit.regionDto.id;
				vm.regionName=administrativeUnit.regionDto.name;
				if(vm.regions==null) vm.regions=[];
				vm.regions.push(administrativeUnit.regionDto);
				
			}else if(administrativeUnit.parentDto!=null && administrativeUnit.parentDto.parentDto == null){
				
				vm.paramDto.regionId=administrativeUnit.parentDto.regionDto.id;
				vm.regionName=administrativeUnit.parentDto.regionDto.name;
				if(vm.regions==null) vm.regions=[];
				vm.regions.push(administrativeUnit.parentDto.regionDto);
				
			}else if(administrativeUnit.parentDto!=null && administrativeUnit.parentDto.parentDto != null){
				
				vm.paramDto.regionId=administrativeUnit.parentDto.parentDto.regionDto.id;
				if(vm.regions==null) vm.regions=[];
				vm.regions.push(administrativeUnit.parentDto.parentDto.regionDto);
				vm.regionName=administrativeUnit.parentDto.parentDto.regionDto.name;
			}			
            
        }		*/	
		//--------End ----Phân quyền-------------
		
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

        };	
		vm.getCombo();
		function validate(){			
			 return true;
		};
		
		vm.getListAnimalParent = function() {
			animalService.getListParent().then(function(data){
                vm.animalParents = data;
            });
        };
        vm.getListAnimalParent();
		
		vm.getQuantityReport=function (){
			vm.title=$translate.instant('report.regionDetailQuantityReport')+ " ";
			if(vm.regionName!=null){
				vm.title=vm.title+ vm.regionName+" ";
			}else{
				vm.title=$translate.instant('report.numberQuantityReport')+" ";
			}
			if(vm.paramDto.fromYear==vm.paramDto.toYear){
				vm.title=vm.title+ vm.paramDto.fromYear+" ("+$translate.instant('report.item')+")";
			}else{
				vm.title=vm.title+ vm.paramDto.fromYear +"-"+vm.paramDto.toYear+" ("+$translate.instant('report.item')+")";
			}
						
        	vm.paramDto.groupByItems=["province"];
        	
        	vm.rowsPivot = [{
                "uniqueName": "provinceName",
                "sort": "asc",
                "caption":$translate.instant('report.city')            	                    
            }];
        	vm.columnsPivot = [{
                "uniqueName": "yearReport",
                "sort": "asc",
                "caption":$translate.instant('report.year')
        	}];
        	
            service.getQuantityReport(vm.paramDto).then(function(data){
				vm.results=data;

				vm.title=vm.title + " - ("+$translate.instant('report.data')+" "+data.length+" "+$translate.instant('report.city')+")";
				console.log(data);
				if(data==null || data==[] ||data.length==0){
					vm.isShow=false;
				}else{
					vm.isShow=true;
				}
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
            	            "rows": vm.rowsPivot,
            	            "columns": vm.columnsPivot,
            	            "measures": [{
            	                "uniqueName": "quantity",
            	                "aggregation": "sum",
            	                "caption":$translate.instant('report.quantity')
            	            }],
            	        },
            	        options: {
            	            grid: {
            	                title: vm.title,
            	                type:"compact",
            	                showHeaders: false,
								"showGrandTotals": "columns",
								subtitle: { text: 'My custom subtitle' }
            	            }
            	        }
            	    }
					,
        	        reportcomplete: function() {
        	        	pivot.off("reportcomplete");
        	        	createChart();
        	        },
        	        global: {
        	    		// replace this path with the path to your own translated file
        	    		localization: "assets/scripts/pivot/vi.json"
        	    	}
            	});
				//createChart
            	function createChart() {
        		    pivot.highcharts.getData({
        		        type: "column"		        		
        		    }, function(data) {
        		    	console.log(data);
        		    	data.chart.options3d={
        		    			enabled: true,
        		                alpha: 0,
        		                beta: 0,
        		                depth: 20,
        		                viewDistance: 10
        		    	}
        		    	data.plotOptions={};
        		    	data.plotOptions.pie={};
        		    	data.plotOptions.pie.colors = ['blue', 'red', '#FF0000'];
						data.plotOptions.series={};
						data.plotOptions.series.pointWidth=40;//độ rộng của biểu đồ
                        data.title.text=$translate.instant('report.regionDetailQuantityReport')+" ";
                                            
						if(vm.regionName!=null){
							data.title.text=data.title.text+ vm.regionName+" ";
						}else{
							data.title.text=$translate.instant('report.numberQuantityReport')+" ";
						}
						if(vm.paramDto.fromYear==vm.paramDto.toYear){
							data.title.text=data.title.text+ vm.paramDto.fromYear+" ("+$translate.instant('report.item')+")"+" <br/> ("+$translate.instant('report.data')+data.xAxis.categories.length+" "+$translate.instant('report.city')+")";
						}else{
							data.title.text=data.title.text+ vm.paramDto.fromYear +"-"+vm.paramDto.toYear+" ("+$translate.instant('report.item')+")"+" <br/> ("+$translate.instant('report.data')+data.xAxis.categories.length+" "+$translate.instant('report.city')+")";
						}
						data.xAxis.title.text="";//bỏ title trục ngang
						data.yAxis[0].title.text="";//bỏ title trục dọc
            		    console.log(data);
        		        Highcharts.chart('highchartsContainer', data);
        		    }, function(data) {
        		    	console.log(data);
        		    	data.chart.options3d={
        		    			enabled: true,
        		                alpha: 0,
        		                beta: 0,
        		                depth: 20,
        		                viewDistance: 10
        		    	}
        		    	data.plotOptions={};
        		    	data.plotOptions.pie={};
        		    	data.plotOptions.pie.colors = ['blue', 'red', '#FF0000'];
						data.plotOptions.series={};
						data.plotOptions.series.pointWidth=40;//độ rộng của biểu đồ
        		    	data.title.text=$translate.instant('report.regionDetailQuantityReport')+" ";
                                            
						if(vm.regionName!=null){
							data.title.text=data.title.text+ vm.regionName+" ";
						}else{
							data.title.text=$translate.instant('report.numberQuantityReport')+" ";
						}
						if(vm.paramDto.fromYear==vm.paramDto.toYear){
							data.title.text=data.title.text+ vm.paramDto.fromYear+" ("+$translate.instant('report.item')+")"+" <br/> ("+$translate.instant('report.data')+data.xAxis.categories.length+" "+$translate.instant('report.city')+")";
						}else{
							data.title.text=data.title.text+ vm.paramDto.fromYear +"-"+vm.paramDto.toYear+" ("+$translate.instant('report.item')+")"+"  <br/> ("+$translate.instant('report.data')+data.xAxis.categories.length+" "+$translate.instant('report.city')+")";
						}
						data.xAxis.title.text="";//bỏ title trục ngang
						data.yAxis[0].title.text="";//bỏ title trục dọc
        		    	console.log(data);
        		        Highcharts.chart('highchartsContainer', data);
        		    });
        		}
            });
        }
		vm.onFmsRegionSelected=function(region){
			
			if(region!=null && region.id!=null){
				vm.regionName=region.name;
				console.log(vm.regionName);
				/*auService.getAllCityByRegion(region.id).then(function (data) {
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
	            });*/
			}else{
				vm.regionName=null;
			}
		}
		
		vm.onFmsadminstrativeUnitCitySelected = function(city) {
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
		vm.showCalcaulation = function (){
          
            vm.modal = modal.open({
                animation: true,
                templateUrl: 'calculation.html',
                scope: $scope,
                backdrop: 'static',
                keyboard: false,
                size: 'md'
            });

        };
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
