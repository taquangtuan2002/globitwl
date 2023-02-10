/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.Report').controller('EggReportController', EggReportController);

    EggReportController.$inject = [
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
		'OwnershipService'
    ];
    
    function EggReportController ($rootScope, $scope, $http, $timeout,settings,service,regionService,modal,toastr,$state,blockUI,$stateParams,utils,$translate,farmService,auService,ownershipService) {
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
        vm.isCallReport = false;
		$rootScope.$on('$translateChangeSuccess', function(event, current, previous) {
			if(vm.isCallReport){
				vm.getQuantityReport();
			}
		});
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
        vm.viewType="table";
		$scope.years = [];
		vm.regionName=null;
		vm.provinceName=null;
		vm.districtName=null;
		vm.wardsName=null;
		vm.title=null;
		vm.isShow=true;
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
        
		vm.reasons = settings.reasons;
		vm.types = settings.types;
        /** generate */
		
		function getOwnership(){
			ownershipService.getAll().then(function(data){
                vm.ownerships = data;
            });
		}
		getOwnership();
		
        vm.paramDto={};
        vm.paramDto.periodType="0";
        vm.paramDto.startTimeType=1;//Lấy mốc đầu tháng - đầu nămS
        vm.paramDto.type=1;//Chỉ Lấy phiếu xuất đàn
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
		vm.isRole=false;
		vm.isFamer=false;
		vm.isSdah=false;
		vm.isDistrict=false;
		vm.isWard=false;
		vm.isSdahView = false;
		//------Start--Phân quyền theo user đăng nhập-----------
		blockUI.start();
        service.getCurrentUser().then(function (data) {
            console.log(data);
            vm.user=data;
            var roles = data.roles;
           
            if(roles!=null && roles.length>0){
                for(var i=0; i<roles.length;i++){
                    if (roles[i]!=null &&  roles[i].authority && (roles[i].authority.toLowerCase() == 'role_admin' ||roles[i].authority.toLowerCase() == 'role_dlp')) {
                        vm.isRole = true;						
                    }
					if (roles[i]!=null &&  roles[i].authority && roles[i].authority.toLowerCase() == 'role_sdah' ) {
                        vm.isSdah = true;						
                    }
					if (roles[i]!=null &&  roles[i].authority && roles[i].authority.toLowerCase() == 'role_district' ) {
                        vm.isDistrict = true;						
                    }
					if (roles[i]!=null &&  roles[i].authority && roles[i].authority.toLowerCase() == 'role_ward' ) {
                        vm.isWard = true;						
                    }
					if (roles[i]!=null &&  roles[i].authority && roles[i].authority.toLowerCase() == 'role_famer' ) {
                        vm.isFamer = true;						
                    }
					if (roles[i]!=null &&  roles[i].authority && roles[i].authority.toLowerCase() == 'role_sdah_view' ){
                        vm.isSdahView = true;
						vm.isSdah = true;							
                    }
                }
            }else{
                vm.isRole=false;
				vm.isRole=false;
				vm.isFamer=false;
				vm.isSdah=false;
				vm.isDistrict=false;
				vm.isWard=false;
				vm.isSdahView = false;
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
				vm.getCombo();
				vm.paramDto.provinceId=administrativeUnit.id;
				vm.paramDto.regionId=administrativeUnit.regionDto.id;				
				if(vm.adminstrativeUnits_City==null) vm.adminstrativeUnits_City=[];
				
				if(vm.paramDto.regionId!=null){
				
					auService.getAllCityByRegion(vm.paramDto.regionId).then(function (data) {
						if (data != null && data.length > 0) {
							vm.adminstrativeUnits_City = data;
							
						}

					});
				}
				farmService.getAllByParentId(vm.paramDto.provinceId).then(function(data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Dist = data;
                    }
                    
                });
				vm.provinceName=administrativeUnit.name;
				vm.regionName=administrativeUnit.regionDto.name;
				
			}else if(administrativeUnit.parentDto!=null && administrativeUnit.parentDto.parentDto == null){
				vm.getCombo();
				vm.paramDto.districtId=administrativeUnit.id;
				vm.paramDto.provinceId = administrativeUnit.parentDto.id;
				vm.paramDto.regionId=administrativeUnit.parentDto.regionDto.id;
				
	
				if(vm.adminstrativeUnits_City==null) vm.adminstrativeUnits_City=[];
				//vm.adminstrativeUnits_City.push(administrativeUnit.parentDto);
				if(vm.adminstrativeUnits_Dist==null) vm.adminstrativeUnits_Dist=[];
				vm.adminstrativeUnits_Dist.push(administrativeUnit);
				farmService.getAllByParentId(vm.paramDto.districtId).then(function(data) {
                    if (data != null && data.length > 0) {
                        vm.adminstrativeUnits_Wards = data;
                    }
                    
                });
				vm.districtName=administrativeUnit.name;
				vm.provinceName=administrativeUnit.parentDto.name;
				vm.regionName=administrativeUnit.parentDto.regionDto.name;
				if(vm.paramDto.regionId!=null){
				
					auService.getAllCityByRegion(vm.paramDto.regionId).then(function (data) {
						if (data != null && data.length > 0) {
							vm.adminstrativeUnits_City = data;
							
						}

					});
				}
				if(vm.paramDto.provinceId!=null){
				
					auService.getChildrenByParentId(vm.paramDto.provinceId).then(function (data) {
						if (data != null && data.length > 0) {
							vm.adminstrativeUnits_Dist = data;
							
						}

					});
				}

				
			}else if(administrativeUnit.parentDto!=null && administrativeUnit.parentDto.parentDto != null){
				vm.getCombo();
				vm.paramDto.wardId=administrativeUnit.id;				
                vm.paramDto.districtId = administrativeUnit.parentDto.id;
				vm.paramDto.provinceId = administrativeUnit.parentDto.parentDto.id;
				vm.paramDto.regionId=administrativeUnit.parentDto.parentDto.regionDto.id;
				if(vm.regions==null) vm.regions=[];
				//vm.regions.push(administrativeUnit.parentDto.parentDto.regionDto);
				
				if(vm.adminstrativeUnits_City==null) vm.adminstrativeUnits_City=[];
				//vm.adminstrativeUnits_City.push(administrativeUnit.parentDto.parentDto);
				if(vm.adminstrativeUnits_Dist==null) vm.adminstrativeUnits_Dist=[];
				//vm.adminstrativeUnits_Dist.push(administrativeUnit.parentDto);
				if(vm.adminstrativeUnits_Wards==null) vm.adminstrativeUnits_Wards=[];
				//vm.adminstrativeUnits_Wards.push(administrativeUnit);
				if(vm.isFamer==false)
				vm.wardsName=administrativeUnit.name;
				vm.districtName=administrativeUnit.parentDto.name;
				vm.provinceName=administrativeUnit.parentDto.parentDto.name;
				vm.regionName=administrativeUnit.parentDto.parentDto.regionDto.name;
				if(vm.paramDto.regionId!=null){
				
					auService.getAllCityByRegion(vm.paramDto.regionId).then(function (data) {
						if (data != null && data.length > 0) {
							vm.adminstrativeUnits_City = data;
							
						}

					});
				}
				if(vm.paramDto.provinceId!=null){
				
					auService.getChildrenByParentId(vm.paramDto.provinceId).then(function (data) {
						if (data != null && data.length > 0) {
							vm.adminstrativeUnits_Dist = data;
							
						}

					});
				}
				if(vm.paramDto.districtId!=null &&  vm.isFamer == false){				
					auService.getChildrenByParentId(vm.paramDto.districtId).then(function (data) {
						if (data != null && data.length > 0) {
							vm.adminstrativeUnits_Wards = data;							
						}
					});
				}else if(vm.isFamer == true){
					vm.adminstrativeUnits_Wards.push(administrativeUnit);
					vm.paramDto.wardId=null;
				}
			}			
            
        }
		
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
        }
		//vm.getCombo();
		vm.getAllCity = function() {
			auService.getAllCity().then(function(data) {
                vm.adminstrativeUnits_City = data;                
            });
        }
//        vm.getAllCity();
        
		 
		//vm.getCombo();
		function validate(){			
			 return true;
		}
		
        vm.getQuantityReport=function (){
			vm.title=$translate.instant('report.exportEgg')+ " ";
			vm.isCallReport = true;
			if(vm.wardsName!=null){
				vm.title=vm.title+ vm.wardsName+ " ";
			}else if(vm.districtName!=null){
				vm.title=vm.title+ vm.districtName+ " ";
			}
			else if(vm.provinceName!=null){
				vm.title=vm.title+ vm.provinceName+ " ";
			}
			else if(vm.regionName!=null){
				vm.title=vm.title+ vm.regionName+ " ";
			}
			if(vm.paramDto.periodType==0){
				vm.title=vm.title+ vm.paramDto.toYear;
			}else if(vm.paramDto.periodType==2){
				if(vm.paramDto.toMonth!=null && vm.paramDto.toMonth==vm.paramDto.fromMonth && vm.paramDto.toYear!=null && vm.paramDto.toYear==vm.paramDto.fromYear){
					vm.title=vm.title+ vm.paramDto.fromMonth+'/'+vm.paramDto.fromYear;
				}else
				vm.title=vm.title+ vm.paramDto.fromMonth+'/'+vm.paramDto.fromYear+ " - "+vm.paramDto.toMonth+'/'+vm.paramDto.toYear;
				//vm.title=vm.title+ vm.paramDto.toMonth+'/'+vm.paramDto.currentYear;
			}
			if(vm.paramDto.exportReason!=null && vm.paramDto.exportReason.length>0){
				vm.title=vm.title+ " - "+vm.paramDto.exportReason;
			}
			vm.title=vm.title+ " ("+$translate.instant('report.item2')+")";
        	vm.paramDto.groupByItems=[];
        	vm.rows=[];
        	var sortStr="";
        	if(vm.paramDto.periodType=='0'){
        		vm.rowsPivot = [{
                    "uniqueName": "yearReport",
                    "sort": "asc",
                    "caption":$translate.instant('report.year')           	                    
                }];
        		vm.rows.push("yearReport");
        		sortStr="yearReport."+vm.paramDto.toYear;
        	}
        	else if(vm.paramDto.periodType=='2'){
        		vm.rowsPivot = [{
        			"uniqueName": "monthInYear",
                    "sort": "asc",
                    "caption":$translate.instant('report.month')            	                    
                }];
        		vm.rows.push("monthReport");
        		vm.rows.push("yearReport");
        		sortStr="monthInYear."+vm.paramDto.toMonth+"/"+vm.paramDto.fromYear + " - "+vm.paramDto.toMonth+"/"+vm.paramDto.toYear;
        	}
        	console.log(sortStr);        	
			if(vm.paramDto.periodType==0){
				vm.paramDto.fromYear = vm.paramDto.toYear;
			}
			vm.paramDto.groupByItems.push('eggType');			
			vm.paramDto.groupByItems.push('region');	
			vm.paramDto.groupByItems.push('province');	
			vm.paramDto.groupByItems.push('district');	
			vm.paramDto.groupByItems.push('ward');
//			vm.paramDto.groupByItems.push('animalParent');
        	//vm.paramDto.fromYear = vm.paramDto.toYear;
        	//vm.paramDto.fromMonth  = vm.paramDto.toMonth;
//        	console.log(vm.paramDto.groupByItems.includes('district'));
        	
            service.getEggQuantityReport(vm.paramDto).then(function(data){
            	vm.results=data;
            	if(data==null || data==[] ||data.length==0){
					vm.isShow=false;
				}else{
					vm.isShow=true;
				}
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
            	            "columns": [{
            	            	"uniqueName": "regionName",
        	                    "sort": "asc",
        	                    "caption":$translate.instant('region.information')
            	            }],
            	            "rows": [{
        	                    "uniqueName": "eggTypeName",
        	                    "sort": "asc",
        	                    "caption":$translate.instant('report.eggType')
	            	            }
//	            	            ,
//	        	            	{
//	    	                    "uniqueName": "regionName",
//	    	                    "sort": "asc",
//	    	                    "caption":$translate.instant('Vùng')
//	            	            },
//            	            	{
//        	                    "uniqueName": "provinceName",
//        	                    "sort": "asc",
//        	                    "caption":$translate.instant('Tỉnh/Thành')
//	            	            }
//	            	            ,
//            	            	{
//        	                    "uniqueName": "districtName",
//        	                    "sort": "asc",
//        	                    "caption":$translate.instant('Quận/huyện')
//	            	            }
//	            	            ,
//            	            	{
//        	                    "uniqueName": "wardName",
//        	                    "sort": "asc",
//        	                    "caption":$translate.instant('Xã/phường')
//	            	            }
            	            ],
            	            "measures": [{
            	                "uniqueName": "quantity",
//            	                "aggregation": "sum",
            	                "caption":$translate.instant('report.amount')
            	            }
//            	            ,{
//            	                "uniqueName": "amount",
//            	                //"aggregation": "sum",
//            	                "caption":"Khối lượng"
//            	            }
            	            ],
//	            	        "drills": {
//	            	            "drillAll": false,
//	            	            "rows": [{
//	            	                "tuple": [
//	            	                    "animal"
//	            	                ]
//	            	            }]
//	            	        }
//            	        	,
            	            //"expands": {
            	            //    "rows": [{
            	            //        "tuple": [
            	            //            "animal"
            	            //        ]
            	            //    }]
            	            //}
            	            "sorting": {
            	                "row": {
            	                    "type": "desc",
            	                    "tuple": [
            	                    	sortStr
            	                    ],
            	                    "measure": "quantity"
            	                }
            	            }
            	        },
            	        options: {
            	            grid: {
            	                title: vm.title,
            	                type:"compact",
            	                showGrandTotals: "rows"
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
        	    		localization: $translate.use()=="vi-VN"?"assets/scripts/pivot/vi.json":"assets/scripts/pivot/en.json"
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
						data.title.text=$translate.instant('report.exportEgg')+" ";
						if(vm.wardsName!=null){
							data.title.text=data.title.text+ vm.wardsName+ " ";
						}else if(vm.districtName!=null){
							data.title.text=data.title.text+ vm.districtName+ " ";
						}
						else if(vm.provinceName!=null){
							data.title.text=data.title.text+ vm.provinceName+ " ";
						}
						else if(vm.regionName!=null){
							data.title.text=data.title.text+ vm.regionName+ " ";
						}
						if(vm.paramDto.periodType==0){
							data.title.text=data.title.text+ vm.paramDto.toYear;
						}else if(vm.paramDto.periodType==2){
							if(vm.paramDto.toMonth!=null && vm.paramDto.toMonth==vm.paramDto.fromMonth && vm.paramDto.toYear!=null && vm.paramDto.toYear==vm.paramDto.fromYear){
								data.title.text=data.title.text+ vm.paramDto.fromMonth+'/'+vm.paramDto.fromYear;
							}else
							data.title.text=data.title.text+ vm.paramDto.fromMonth+'/'+vm.paramDto.fromYear+ " - "+vm.paramDto.toMonth+'/'+vm.paramDto.toYear;
						}
						if(vm.paramDto.exportReason!=null && vm.paramDto.exportReason.length>0){
							data.title.text=data.title.text+ " - "+vm.paramDto.exportReason;
						}
						data.title.text=data.title.text+ " ("+$translate.instant('report.item2')+")";
						data.plotOptions={};
						data.plotOptions.series={};
						data.plotOptions.series.pointWidth=40;//độ rộng của biểu đồ
						data.xAxis.title.text="";//bỏ title trục ngang
						data.yAxis[0].title.text="";//bỏ title trục dọc
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
						data.title.text=$translate.instant('report.exportReport')+" ";
						if(vm.wardsName!=null){
							data.title.text=data.title.text+ vm.wardsName+ " ";
						}else if(vm.districtName!=null){
							data.title.text=data.title.text+ vm.districtName+ " ";
						}
						else if(vm.provinceName!=null){
							data.title.text=data.title.text+ vm.provinceName+ " ";
						}
						else if(vm.regionName!=null){
							data.title.text=data.title.text+ vm.regionName+ " ";
						}
						if(vm.paramDto.periodType==0){
							data.title.text=data.title.text+ vm.paramDto.toYear;
						}else if(vm.paramDto.periodType==2){
							if(vm.paramDto.toMonth!=null && vm.paramDto.toMonth==vm.paramDto.fromMonth && vm.paramDto.toYear!=null && vm.paramDto.toYear==vm.paramDto.fromYear){
								data.title.text=data.title.text+ vm.paramDto.fromMonth+'/'+vm.paramDto.fromYear;
							}else
							data.title.text=data.title.text+ vm.paramDto.fromMonth+'/'+vm.paramDto.fromYear+ " - "+vm.paramDto.toMonth+'/'+vm.paramDto.toYear;
						}
						if(vm.paramDto.exportReason!=null && vm.paramDto.exportReason.length>0){
							data.title.text=data.title.text+ " - "+vm.paramDto.exportReason;
						}
						data.title.text=data.title.text+ " ("+$translate.instant('report.item2')+")";
						data.plotOptions={};
						data.plotOptions.series={};
						data.plotOptions.series.pointWidth=40;//độ rộng của biểu đồ
						data.xAxis.title.text="";//bỏ title trục ngang
						data.yAxis[0].title.text="";//bỏ title trục dọc
        		        Highcharts.chart('highchartsContainer', data);
        		    });
        		}
            	//Chart here
                /*if (vm.results != null && vm.results.length > 0) {
                    $scope.series = [];
                    $scope.data = [];                    
                    for (var i = 0; i < vm.results.length; i++) {
                    	var quantityArray=[];
                        $scope.series.push(vm.results[i].parentlName);
                        quantityArray.push(vm.results[i].quantity);
                        $scope.data.push(quantityArray);
					}                    
            	}*/
            	
            });
        }
        //3d chart
//    	var chart = new Highcharts.chart('container', {
//    	    chart: {
//    	        type: 'column',
//    	        options3d: {
//    	            enabled: true,
//    	            alpha: 10,
//    	            beta: 25,
//    	            depth: 70
//    	        }
//    	    },
//    	    title: {
//    	        text: '3D chart with null values'
//    	    },
//    	    subtitle: {
//    	        text: 'Notice the difference between a 0 value and a null point'
//    	    },
//    	    plotOptions: {
//    	        column: {
//    	            depth: 25,
//    	            colorByPoint: true
//    	        }
//    	    },
//    	    xAxis: {
////    	        categories: Highcharts.getOptions().lang.shortMonths,
//    	        labels: {
//    	            skew3d: true,
//    	            style: {
//    	                fontSize: '16px'
//    	            }
//    	        }
//    	    },
//    	    yAxis: {
//    	        title: {
//    	            text: null
//    	        }
//    	    },
//    	    series: [{
//    	        name: 'Sales',
//    	        data: [2, 3, null, 4, 0, 5, 1, 4, 6, 3]
//    	    }]
//    	});
		vm.onFmsRegionSelected=function(region){
			if(region!=null && region.id!=null){
				vm.regionName=region.name;
				auService.getAllCityByRegion(region.id).then(function (data) {
					if (data != null && data.length > 0) {
		                vm.adminstrativeUnits_City = data;
						vm.selectedCity=null;
		                vm.selectedDist = null;
		                vm.selectWards=null;
						vm.paramDto.provinceId=null;
						vm.paramDto.districtId=null;
						vm.paramDto.wardId=null;
						vm.provinceName=null;
						vm.districtName=null;
						vm.wardsName=null;
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
						vm.provinceName=null;
						vm.districtName=null;
						vm.wardsName=null;
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
						vm.regionName=null;
						vm.provinceName=null;
						vm.districtName=null;
						vm.wardsName=null;
					}		
		}
		
		vm.onFmsadminstrativeUnitCitySelected = function(city) {
			console.log("city");
			console.log(city);
			vm.selectedCity = city;
			if (city != null && city.id != null) {
				
				vm.provinceName=city.name;
				farmService.getAllByParentId(city.id).then(function (data) {
					
					if (data != null && data.length > 0) {						
		                vm.adminstrativeUnits_Dist = data;
						if(data.length==1){
							
							auService.getChildrenByParentId(city.id).then(function (data) {
								if (data != null && data.length > 0) {
									vm.adminstrativeUnits_Dist = data;
									
								}

							});
						
						}
		                vm.selectedDist = null;
		                vm.selectWards=null;
						vm.paramDto.districtId=null;
						vm.paramDto.wardId=null;
						vm.districtName=null;
						vm.wardsName=null;
					}
					else {
						vm.paramDto.districtId=null;
						vm.paramDto.wardId=null;
			        	vm.selectedDist = null;
			        	vm.selectWards = null;
		                vm.adminstrativeUnits_Dist = [];
						vm.adminstrativeUnits_Wards = [];
						vm.districtName=null;
						vm.wardsName=null;
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
				vm.districtName=null;
				vm.wardsName=null;
				vm.provinceName=null;
			}
		}
        
        vm.onFmsadminstrativeUnitDistSelected = function(dist) {
			if (dist != null && dist.id != null) {
				vm.districtName=dist.name;
				farmService.getAllByParentId(dist.id).then(function (data) {
					if (data != null && data.length > 0) {
		                vm.adminstrativeUnits_Wards = data;
						if(data.length== 1&&  vm.isFamer == false){
							
							auService.getChildrenByParentId(dist.id).then(function (data) {
								if (data != null && data.length > 0) {
									vm.adminstrativeUnits_Wards = data;
									
								}

							});
						
						}
					}
					else {
						vm.paramDto.wardId=null;
			        	vm.selectWards = null;
		                vm.adminstrativeUnits_Wards = [];
						vm.wardsName=null;
					}
					vm.paramDto.wardId=null;
					vm.selectWards=null;
					vm.wardsName=null;
	            });
			}
			else {
				vm.paramDto.wardId=null;
	        	vm.selectWards = null;
				vm.adminstrativeUnits_Wards = [];
				vm.wardsName=null;
				vm.districtName=null;
			}
		}
		vm.onFmsadminstrativeUnitWardsSelected=function(item){
			if(item!=null){
				vm.wardsName=item.name;
			}else{
				vm.wardsName=null;
			}
		}
		////////cong thức
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
        
        
        console.log(service.getTableDefinition());
        
        
    }

})();
