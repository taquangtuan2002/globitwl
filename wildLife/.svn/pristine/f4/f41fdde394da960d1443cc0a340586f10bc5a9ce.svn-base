/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.Report').controller('RatioByAnimalReportController', RatioByAnimalReportController);

    RatioByAnimalReportController.$inject = [
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
    
    function RatioByAnimalReportController ($rootScope, $scope, $http, $timeout,settings,service,regionService,modal,toastr,$state,blockUI,$stateParams,utils,$translate,farmService,auService,animalService) {
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
		vm.regionName=null;
		vm.animalName=null;
		var isDup=false;
		vm.datas=[];	
		vm.dataDetail=[];
		vm.paramDtoDetail={};
		vm.paramDtoPreClick={};
		var isDupDetail=false;
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
            
        }			*/
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
			vm.isCallReport = true;
			Highcharts.chart('highchartsContainerDetail', {
				title: {
					text: ""
				},
			});
			vm.title=$translate.instant('report.structureByAnimal1');
			if(vm.ward!=null){
				vm.title=$translate.instant('report.animalByRegion');
				vm.title=vm.title+ " "+vm.ward.name;
			}
			else if(vm.district!=null){
				vm.title=$translate.instant('report.animalByRegion');
				vm.title=vm.title+ " "+vm.district.name;
			}
			else if(vm.city!=null){
				vm.title=$translate.instant('report.animalByRegion');
				vm.title=vm.title+ " "+vm.city.name;
			}
			else if(vm.regionName!=null){
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
				//createChart
            	function createChart() {
      		   
					console.log(vm.results);
					
					vm.data=[];
					vm.total=0;
					if(vm.results!=null && vm.results.length>0){
						for(var i=0;i<vm.results.length;i++){
							vm.value={};
							
							if(vm.data.length==0){
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
					//console.log(vm.total);
					//console.log(vm.data.length);
								
					vm.datas=[];						
					var isDup1=false;
					// biểu đồ tròn với vùng
					/*if(vm.data!=null && vm.data.length>1){
						//console.log("a");
						for(var i=0;i<vm.data.length;i++){
							vm.data[i].y=vm.data[i].quantity/vm.total;
							
						}
						
					}*/
					// biểu đồ tròn cho mỗi vùng
                    if (vm.data != null && vm.data.length > 0) {
                        vm.detailChartName = "";
                        if (vm.ward != null) {                            
                            vm.detailChartName = vm.ward.name;
                        }
                        else if (vm.district != null) {
                            vm.detailChartName = vm.district.name;
                        }
                        else if (vm.city != null) {
                            vm.detailChartName = vm.city.name;
                        }
                        else if (vm.regionName != null) {
                            vm.detailChartName = vm.regionName;
                        }
						 for(var t=0;t< vm.data.length;t++){
							 //console.log("ab");
							//console.log(vm.results.length);
							vm.total=0;
							if(vm.results!=null && vm.results.length>0){
								vm.result=[];
								for(var i=0;i<vm.results.length;i++){
									vm.value={};
                                    var dataChartName = vm.results[i].regionName;
                                    if (vm.detailChartName != "") dataChartName = vm.detailChartName;
									if(vm.result.length==0 && vm.data[t].name==vm.results[i].regionName){
                                        vm.value.regionName = dataChartName;
										vm.value.regionId=vm.results[i].regionId;
										vm.value.id=vm.results[i].parentId;
										vm.value.name=vm.results[i].parentlName;
										vm.value.quantity=vm.results[i].quantity;
										vm.total=vm.total+vm.results[i].quantity;
										vm.value.y=0;
										
										vm.result.push(vm.value);
									}else if(vm.result.length>0&& vm.data[t].name==vm.results[i].regionName){
										isDup1=false;									
										for(var j=0;j<vm.result.length;j++){
											
                                            if (vm.result[j].name == vm.results[i].parentlName && vm.data[t].name == dataChartName){
												vm.result[j].quantity=vm.result[j].quantity+vm.results[i].quantity;
																			
												vm.total=vm.total+vm.results[i].quantity;
												isDup1=true;
												
											}
										}
										if(isDup1==false&& vm.data[t].name==vm.results[i].regionName){
                                            vm.value.regionName = dataChartName;
											vm.value.regionId=vm.results[i].regionId;
											vm.value.id=vm.results[i].parentId;
											vm.value.name=vm.results[i].parentlName;
											vm.value.quantity=vm.results[i].quantity;
											vm.total=vm.total+vm.results[i].quantity;
											vm.value.y=0;
																		
											vm.result.push(vm.value);
											isDup=false;
										}
										
									}
								}
							}
		
							//console.log(vm.result);
							//console.log(vm.data);
							//console.log(vm.total);
							if(vm.result!=null && vm.result.length>0){
								for(var i=0;i<vm.result.length;i++){
									vm.result[i].y=vm.result[i].quantity/vm.total;
									
								}
							}
							console.log(vm.result);
							vm.datas.push(vm.result);
							 
						 }
						
					}
					console.log(vm.datas);
					
					
					

					/*if(vm.datas!=null && vm.datas.length>0){
						for(var i=0;i<vm.datas.length;i++){
							
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
										$translate.instant('report.quantity')+' : <b>{point.quantity}'+$translate.instant('report.ba')+'</b><br/>'
									//pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
								},
								plotOptions: {
									pie: {
										allowPointSelect: true,
										cursor: 'pointer',
										dataLabels: {
											enabled: false
										},
										showInLegend: true
									}
								},
								series: [{
									type: 'pie',
									name: $translate.instant('report.ratio'),
									colorByPoint: true,
									data: vm.datas[i]
									
								}]
								
							});
							
						}
					}*/
					vm.series=[];
					for(var i=0; i<vm.datas.length>0;i++){
						vm.item={};
						vm.item.type='pie';
						vm.item.name=vm.datas[i][0].regionName;
						vm.item.size=200;
						vm.item.data=vm.datas[i];
						vm.item.dataLabels={};
						vm.item.dataLabels.enabled=true;
						vm.item.dataLabels.format='<b>{point.name}</b>: {point.percentage:.1f} %';
						vm.item.dataLabels.distance= 20;
						//vm.item.dataLabels.format='<b>{point.y}</b>';
						//vm.item.showInLegend=true;
						vm.item.title={};
						vm.item.title.align='center';
						vm.item.title.text='<b>'+vm.datas[i][0].regionName+'</b>';
						vm.item.title.verticalAlign='top';
						vm.item.title.y=-60;
						
						vm.item.center=[];
						if(vm.datas.length==1){
							vm.item.center.push(400);
							vm.item.center.push(null);
						}else if(vm.datas.length==2){
							vm.item.center.push(100+(i*600));
							vm.item.center.push(null);
						}
						else if(vm.datas.length==3){
							vm.item.center.push(100+(i*300));
							vm.item.center.push(null);
						}
						else if(vm.datas.length==4){
							vm.item.center.push(100+(i*200));
							vm.item.center.push(null);
						}
						else if(vm.datas.length==5){
							vm.item.size=150;
							vm.item.center.push(100+(i*170));
							vm.item.center.push(null);
						}
						else if(vm.datas.length>=6){
							vm.item.size=150;
							vm.item.center.push(100+(i*155));
							vm.item.center.push(null);
							vm.item.dataLabels.format='{point.percentage:.1f} %';
							vm.item.dataLabels.distance= -10;
						}
						
						vm.series.push(vm.item);
					}
					console.log("vm.series");
					console.log(vm.series);
					console.log(vm.paramDto);
					vm.paramDtoPreClick=vm.paramDto;
					vm.series[0].showInLegend=true;
					for(var i=0;i<vm.series.length;i++){						
						vm.series[i].events={};
						vm.series[i].events.click={};
						vm.series[i].events.click=function (event){
							//alert(event);
							
							console.log("vm.paramDtoPreClick");
							console.log(vm.paramDtoPreClick);
							vm.detail={};
							vm.dataDetail=[];
							vm.totalDetail=0;
							console.log(event);
							vm.detail.animalName=event.point.name;
							vm.detail.regionName=event.point.regionName;
							vm.totalDetail=event.point.quantity;
							vm.paramDtoDetail={};
							
							vm.detail.animalId=event.point.id;
							
							vm.paramDtoDetail=vm.paramDto;							
//							vm.paramDtoDetail.toMonth = vm.paramDto.toMonth;
//							vm.paramDtoDetail.provinceId = vm.paramDto.provinceId;
//							vm.paramDtoDetail.districtId = vm.paramDto.districtId;
//							vm.paramDtoDetail.wardId = vm.paramDto.wardId;
							
							vm.paramDtoDetail.currentYear = vm.paramDto.currentYear;
							vm.paramDtoDetail.regionId=event.point.regionId;
							vm.paramDtoDetail.regionName=event.point.regionName;
							vm.paramDtoDetail.animalParentId=vm.detail.animalId;
							
							//vm.paramDtoDetail.parentlName=vm.detail.animalName;
							vm.paramDtoDetail.groupByItems=["region","animalParent","productTarget"];
							console.log(vm.paramDtoDetail);
							console.log(vm.paramDto);
							
							//gọi hàm vẽ cơ cấu theo hướng sản phẩm
							createChartDetail();
						};
						
					}
					
					 
					(function (Highcharts) {
						Highcharts.wrap(Highcharts.seriesTypes.pie.prototype, 'render', function (proceed) {

							var chart = this.chart,
							center = this.center || (this.yAxis && this.yAxis.center),
							titleOption = this.options.title,
							box;

							proceed.call(this);

							if (center && titleOption) {
								box = {
									x: chart.plotLeft + center[0] - 0.5 * center[2],
									y: chart.plotTop + center[1] - 0.5 * center[2],
									width: center[2],
									height: center[2]
								};
								if (!this.title) {
									this.title = this.chart.renderer.label(titleOption.text)
									.css(titleOption.style)
									.add()
								}
								var labelBBox = this.title.getBBox();
								if (titleOption.align == "center")
									box.x -= labelBBox.width/2;
								else if (titleOption.align == "right")
									box.x -= labelBBox.width;
								this.title.align(titleOption, null, box);
							}
						});

					} (Highcharts));
					
					$('#highchartsContainer').highcharts({
						chart: {
							plotBackgroundColor: null,
							plotBorderWidth: 1,//null,
							plotShadow: false,
							type: 'pie'
						},
						//colors : ['#009800','#ff9800', '#BA21FB'],
						title: {
							text: vm.title
						},
						
						tooltip: {
							
							//pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
							headerFormat: '',
							pointFormat: '<span style="color:{point.color}">\u25CF</span> <b> {point.name}- {series.name}</b><br/>' +
								$translate.instant('report.ratio')+' : <b>{point.percentage:.1f}%</b><br/>' +
								$translate.instant('report.quantity')+' : <b>{point.quantity:,.0f} '+$translate.instant('report.ba')+'</b><br/>'
							
						},
						 /*xAxis: {
							categories: ['Gà', 'Vịt']
						},*/
						plotOptions: {
							
							allowPointSelect: true,
				            cursor: 'pointer',
				            //colors: pieColors,
				            dataLabels: {
				                enabled: true,
				                format: '<b>{point.name}</b><br>{point.percentage:.1f} %',
				                distance: -50
				                
				            }
						},
						series: vm.series
						
					}
					,function(chart) {
							
						$(chart.series[0].data).each(function(i, e) {
							e.legendItem.on('click', function(event) {
								
								/*var legendItem=e.name;
								
								event.stopPropagation();
								
								$(chart.series).each(function(j,f){
									   $(this.data).each(function(k,z){
										   aler(k);
										   aler(z);
											console.log(z);
										   if(z.name==legendItem)
										   {
											   if(z.visible)
											   {
												   z.setVisible(false);
											   }
											   else
											   {
												   z.setVisible(true);
											   }
										   }
									   });
								});*/
								
							});
						});
					});	
        		}
            });
        }
		
		//createChartDetail -biểu cơ cấu theo hướng sản phẩm với 1 động vật
		function createChartDetail() {
			vm.total=0;
			service.getQuantityReport(vm.paramDtoDetail).then(function(data){
				vm.resultDetails=data;
				console.log("details");
				console.log(vm.resultDetails);
				if(vm.resultDetails!=null && vm.resultDetails.length>0){
					for(var i=0;i<vm.resultDetails.length;i++){
						vm.value={};
						
						if(vm.dataDetail.length==0){
							vm.value.id=vm.resultDetails[i].productTargetId;
							vm.value.name=vm.resultDetails[i].productTargetName;
							vm.value.quantity=vm.resultDetails[i].quantity;
							vm.total=vm.total+vm.resultDetails[i].quantity;
							vm.value.y=0;
							
							vm.dataDetail.push(vm.value);
						}else if(vm.dataDetail.length>0){
							isDupDetail=false;									
							for(var j=0;j<vm.dataDetail.length;j++){
								
								if(vm.dataDetail[j].name==vm.resultDetails[i].productTargetName){
									vm.dataDetail[j].quantity=vm.dataDetail[j].quantity+vm.resultDetails[i].quantity;
																
									vm.total=vm.total+vm.resultDetails[i].quantity;
									isDupDetail=true;
									console.log(true);
								}
							}
							if(isDupDetail==false){
								vm.value.id=vm.resultDetails[i].productTargetId;
								vm.value.name=vm.resultDetails[i].productTargetName;
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
				console.log(vm.total);
				if(vm.totalDetail!=vm.total){
					if(vm.totalDetail>vm.total){
						vm.totalRe=vm.totalDetail-vm.total;
						vm.value={};
						//vm.value.id=vm.resultDetails[i].productTargetId;
						vm.value.name="Khác";
						vm.value.quantity=vm.totalRe;
						vm.value.y=0;
						vm.dataDetail.push(vm.value);
						
					}else if(vm.totalDetail<vm.total){
						alert("wrong sum");//sai
					}
				}
				if(vm.dataDetail!=null && vm.dataDetail.length>0){
					//console.log("a");
					for(var i=0;i<vm.dataDetail.length;i++){
						vm.dataDetail[i].y=vm.dataDetail[i].quantity/vm.totalDetail;
						
					}
					
				}
				vm.titleDetail=$translate.instant('report.ratioProduct')+" ";
				if(vm.detail.animalName!=null){					
					vm.titleDetail=vm.titleDetail+ " "+vm.detail.animalName;
				}
				
				if(vm.ward!=null){
					vm.titleDetail=vm.titleDetail+" "+vm.ward.name;
				}
				else if(vm.district!=null){
					vm.titleDetail=vm.titleDetail+" "+vm.district.name;
				}
				else if(vm.city!=null){
					vm.titleDetail=vm.titleDetail+" "+vm.city.name;
				}
				
				if(vm.regionName!=null){
					vm.titleDetail=vm.titleDetail+" "+vm.regionName;
				}
				else if(vm.detail.regionName!=null){					
					vm.titleDetail=vm.titleDetail+" "+vm.detail.regionName;
				}
				vm.titleDetail=vm.titleDetail+" "+$translate.instant('report.animaltime') +" "+ vm.paramDto.toMonth +"/"+vm.paramDto.currentYear+" ("+$translate.instant('report.item')+")";
				if(vm.regionName==null || vm.regionName==""){
					vm.paramDto.regionId=null;							
					
				}
				if(vm.animalName==null || vm.animalName==""){
					vm.paramDto.animalParentId=null;
				}
				
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
		///////////////
		
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
		vm.onFmsAnimalSelected=function(item){
			if(item!=null && item.id!=null){
				vm.animalName=item.name;
			}else{
				vm.animalName=null;
			}
		}
		
		vm.onFmsRegionSelected = function(region){
			
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
						vm.ward = null;
						vm.district=null;
						vm.city = null;
						vm.regionName = null;	
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
	        	
				vm.paramDto.districtId=null;
				vm.paramDto.wardId=null;
	        	vm.selectedDist = null;
	        	vm.selectWards = null;
                vm.adminstrativeUnits_Dist = [];
				vm.adminstrativeUnits_Wards = [];
				vm.districtName=null;
				vm.wardsName=null;
				vm.provinceName=null;
				vm.ward = null;
				vm.district=null;
				vm.city = null;
			}
			vm.city = city;
			console.log(vm.city);
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
				vm.paramDto.wardId=null;
	        	vm.selectWards = null;
				vm.adminstrativeUnits_Wards = [];
				vm.wardsName=null;
				vm.districtName=null;
				vm.ward = null;
				vm.district=null;			
			}
			vm.district = dist;
			console.log(vm.district);
		}
        vm.onFmsadminstrativeUnitWardsSelected = function(item){        	
        	vm.ward=item;
        	console.log(vm.ward);
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
