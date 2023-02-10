/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.Report').controller('AnimalRaisingReportController', AnimalRaisingReportController);

    AnimalRaisingReportController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'AnimalRaisingReportService',
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
    
    function AnimalRaisingReportController ($rootScope, $scope, $http, $timeout,settings,service,regionService,modal,toastr,$state,blockUI,$stateParams,utils,$translate,farmService,auService,animalService) {
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
        vm.viewType="chart";
		vm.title=null;
		vm.isShow=true;
		vm.regionName=null;
		var isDup=false;
		var isDupDetail=false;
		vm.datas=[];	
		vm.dataDetail=[];
		vm.paramDtoDetail={};
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
        vm.paramDto.periodType="1";
        vm.paramDto.currentYear=today.getFullYear().toString();
        vm.paramDto.fromMonth = today.getMonth();
        vm.paramDto.toMonth = today.getMonth();
        
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
		vm.isRole=false;
		

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
				vm.animalParents = vm.animalParents.filter(el => el.code != settings.BEAR_CODE);
            });
        };
        vm.getListAnimalParent();
		
		vm.getQuantityReport=function (){
			vm.isCallReport = true;
			Highcharts.chart('highchartsContainerDetail', {
				title: {
					text: ""
				}
			});
			vm.title=$translate.instant('report.animalByAll');
			if(vm.regionName!=null){
				vm.title=$translate.instant('report.animalByRegion');
				vm.title=vm.title+ " "+vm.regionName;
			}
			vm.title=vm.title+" "+$translate.instant('report.animaltime') +" "+ vm.paramDto.toMonth +"/"+vm.paramDto.currentYear+" ("+$translate.instant('report.item')+")";
        	vm.paramDto.groupByItems=["region","animalParent"];
        	vm.paramDto.fromMonth = vm.paramDto.toMonth;
        	vm.rowsPivot = [{
                "uniqueName": "parentlName",
                "sort": "asc",
                caption:$translate.instant('animalType.information')
            }];
        	vm.columnsPivot = [{
                "uniqueName": "regionName",
                "sort": "asc",
                caption:$translate.instant('report.region')
        	}];
			if(vm.isRole){//trường hợp admin và dlp thì được xem tất cả các cơ sở chăn nuôi
				vm.paramDto.regionId=null;
			}
        	
            service.getQuantityReport(vm.paramDto).then(function(data){
            	vm.results=data;
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
            	                	 "showGrandTotals": "columns"
            	                //showHeaders: false
            	                
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
				//createChart -biểu đồ tròn cho cả nước
            	function createChart() {
      		   
					console.log(vm.results);
					
					vm.data=[];
					vm.total=0;
					if(vm.results!=null && vm.results.length>0){
						for(var i=0;i<vm.results.length;i++){
							vm.value={};
							
							if(vm.data.length==0){
								vm.value.id=vm.results[i].regionId;
								vm.value.name=vm.results[i].regionName;
								vm.value.quantity=vm.results[i].quantity;
								vm.total=vm.total+vm.results[i].quantity;
								vm.value.y=0;
								
								vm.data.push(vm.value);
							}else if(vm.data.length>0){
								isDup=false;									
								for(var j=0;j<vm.data.length;j++){
									
									if(vm.data[j].name==vm.results[i].regionName){
										vm.data[j].quantity=vm.data[j].quantity+vm.results[i].quantity;
																	
										vm.total=vm.total+vm.results[i].quantity;
										isDup=true;
										console.log(true);
									}
								}
								if(isDup==false){
									vm.value.id=vm.results[i].regionId;
									vm.value.name=vm.results[i].regionName;
									vm.value.quantity=vm.results[i].quantity;
									vm.total=vm.total+vm.results[i].quantity;
									vm.value.y=0;
																
									vm.data.push(vm.value);
									isDup=false;
								}
								
							}
						}
					}
					console.log(vm.data);//chia theo  từng vùng
					
					// biểu đồ tròn với vùng
					if(vm.data!=null && vm.data.length>0){
						//console.log("a");
						for(var i=0;i<vm.data.length;i++){
							vm.data[i].y=vm.data[i].quantity/vm.total;
							
						}
						
					}
					Highcharts.chart('highchartsContainer', {
						chart: {
							plotBackgroundColor: null,
							plotBorderWidth: null,
							plotShadow: false,
							type: 'pie'
						},
						title: {
							text: vm.title
						},
						tooltip: {
							headerFormat: '',
							pointFormat: '<span style="color:{point.color}">\u25CF</span> <b> {point.name}</b><br/>' +
								$translate.instant('report.ratio')+' : <b>{point.percentage:.1f}%</b><br/>' +
								$translate.instant('report.quantity')+' : <b>{point.quantity:,.0f} '+$translate.instant('report.ba')+'</b><br/>'
							//pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
						},
						plotOptions: {
							pie: {
								allowPointSelect: true,
								cursor: 'pointer',
								dataLabels: {
									enabled: true,
									format: '<b>{point.name}</b>: {point.percentage:.1f} %'
								}
							},
							series: {
								cursor: 'pointer',
								events: {
									click: function (event) {
										vm.detail={};
										vm.dataDetail=[];
										vm.totalDetail=0;
										console.log(event);
										vm.detail.regionName=event.point.name;
										vm.paramDtoDetail={};
										if(vm.data!=null && vm.data.length>0){
											for(var i=0;i<vm.data.length;i++){
												if(vm.data[i].name==event.point.name){
													vm.detail.regionId=vm.data[i].id;
													vm.paramDtoDetail.currentYear=vm.paramDto.currentYear;
													vm.paramDtoDetail.fromMonth=vm.paramDto.fromMonth;
													vm.paramDtoDetail.fromYear=vm.paramDto.fromYear;													
													vm.paramDtoDetail.periodType=vm.paramDto.periodType;
													vm.paramDtoDetail.toMonth=vm.paramDto.toMonth;
													vm.paramDtoDetail.toYear=vm.paramDto.toYear;
													
													vm.paramDtoDetail.regionId=vm.detail.regionId;
													vm.paramDtoDetail.regionName=vm.data[i].name;
													vm.paramDtoDetail.groupByItems=["region","animalParent","province"];
													console.log(vm.paramDtoDetail);
													vm.totalDetail=event.point.quantity;
													createChartDetail();
													
												}
												
											}
										}

										
										/*alert(
											this.name + ' clicked\n' +
											'Alt: ' + event.altKey + '\n' +
											'Control: ' + event.ctrlKey + '\n' +
											'Meta: ' + event.metaKey + '\n' +
											'Shift: ' + event.shiftKey
										);*/
									}
								}
							}
						},
						series: [{
							name: 'Brands',
							colorByPoint: true,
							data: vm.data
						}]
					});
        		}
            });
        }
		//createChartDetail -biểu đồ tròn cho 1 vùng
		function createChartDetail() {
			service.getQuantityReport(vm.paramDtoDetail).then(function(data){
				vm.resultDetails=data;
				console.log("details");
				console.log(vm.resultDetails);
				if(vm.resultDetails!=null && vm.resultDetails.length>0){
					for(var i=0;i<vm.resultDetails.length;i++){
						vm.value={};
						
						if(vm.dataDetail.length==0){
							vm.value.id=vm.resultDetails[i].provinceId;
							vm.value.name=vm.resultDetails[i].provinceName;
							vm.value.quantity=vm.resultDetails[i].quantity;
							vm.total=vm.total+vm.resultDetails[i].quantity;
							vm.value.y=0;
							
							vm.dataDetail.push(vm.value);
						}else if(vm.dataDetail.length>0){
							isDupDetail=false;									
							for(var j=0;j<vm.dataDetail.length;j++){
								
								if(vm.dataDetail[j].name==vm.resultDetails[i].provinceName){
									vm.dataDetail[j].quantity=vm.dataDetail[j].quantity+vm.resultDetails[i].quantity;
																
									vm.total=vm.total+vm.resultDetails[i].quantity;
									isDupDetail=true;
									console.log(true);
								}
							}
							if(isDupDetail==false){
								vm.value.id=vm.resultDetails[i].provinceId;
								vm.value.name=vm.resultDetails[i].provinceName;
								vm.value.quantity=vm.resultDetails[i].quantity;
								vm.total=vm.total+vm.resultDetails[i].quantity;
								vm.value.y=0;
															
								vm.dataDetail.push(vm.value);
								isDupDetail=false;
							}
							
						}
					}
				}
				console.log("dataDetail");
				console.log(vm.dataDetail);
				console.log(vm.totalDetail);
				if(vm.dataDetail!=null && vm.dataDetail.length>0){
					//console.log("a");
					for(var i=0;i<vm.dataDetail.length;i++){
						vm.dataDetail[i].y=vm.dataDetail[i].quantity/vm.totalDetail;
						
					}
					
				}
				vm.titleDetail=$translate.instant('report.ratioAdministrativeUnit');
				if(vm.detail.regionName!=null){					
					vm.titleDetail=vm.titleDetail+" "+ $translate.instant('report.regi') +" "+vm.detail.regionName;
				}
				vm.titleDetail=vm.titleDetail+" "+$translate.instant('report.animaltime') +" "+ vm.paramDto.toMonth +"/"+vm.paramDto.currentYear+" ("+$translate.instant('report.item')+")";
				Highcharts.chart('highchartsContainerDetail', {
					chart: {
						plotBackgroundColor: null,
						plotBorderWidth: null,
						plotShadow: false,
						type: 'pie'
					},
					title: {
						text: vm.titleDetail
					},
					tooltip: {
							headerFormat: '',
							pointFormat: '<span style="color:{point.color}">\u25CF</span> <b> {point.name}</b><br/>' +
							$translate.instant('report.ratio')+' : <b>{point.percentage:.1f}%</b><br/>' +
							$translate.instant('report.quantity')+' : <b>{point.quantity:,.0f} '+$translate.instant('report.ba')+'</b><br/>'
					},
					plotOptions: {
						pie: {
							allowPointSelect: true,
							cursor: 'pointer',
							dataLabels: {
								enabled: true,
								format: '<b>{point.name}</b>: {point.percentage:.1f} %'
							}
						}
					},
					series: [{
						name: 'Brands',
						colorByPoint: true,
						data: vm.dataDetail
					}]
				});
				
			});
      		   
			
		}
				
		
		vm.onFmsRegionSelected=function(region){
			if(region!=null && region.id!=null){
				vm.regionName=region.name;
				
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
		vm.searchDto = {};
		vm.listData = [];
        
		vm.clickTest = function(){
			if(vm.paramDto)
			vm.searchDto.animalParentId = vm.paramDto.animalParentId;
			service.getReportRaising(vm.searchDto).then(function(data){
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
