/**
 * Created by bizic on 28/8/2016.
 */
(function () {
    'use strict';

    angular.module('Education.Dashboard').controller('DashboardController', DashboardController);

    DashboardController.$inject = [
        '$rootScope',
        '$scope',
        '$http',
        '$timeout',
        'settings',
        'FarmService',
        'FmsRegionService',
        'FmsAdministrativeService',
        'QuantityReportService',
        'blockUI',
        '$translate',
        '$filter',
        'toastr',
        'SumQuantityReportDataService',
        "$location",
        "$sce",
        "$cookies",
        '$state',
        'DashboardService'
    ];

    function DashboardController($rootScope, $scope, $http, $timeout, settings, farmService, regionService, fmsAdministrativeService, quantityReportService, blockUI, $translate, filter, toastr, sumQuantityReportDataService, $location, $sce, $cookies, $state, service) {

        var vm = this;
        vm.dashboardCities = {};
        vm.dashboardFarm = {};
        // vm.dashboardReportForm = {};
        vm.dashboardTotalCites = {};
        vm.dashboardTotalGroverment = {};
        vm.dashboardTotalDashboad = [];
        vm.dashboardForestProduct = {};
        vm.dashboardCitiesVN = {};
        // vm.isView = false;
        vm.dashboardSumInYear = null;
        vm.getCountByYear = {};
        vm.searchDto = {};
        vm.listYear = [];
        vm.viewPosition = false;
        vm.decreasedIndividualVariability = null;

        farmService.getCurrentUser().then(function (data) {
            $scope.currentUser = data;
            var roles = data.roles;
            if (roles != null && roles.length > 0) {
                for (var i = 0; i < roles.length; i++) {
                    if (roles[i] != null && roles[i].authority && (roles[i].authority.toLowerCase() == 'role_admin' || roles[i].authority.toLowerCase() == 'role_dlp')) {
                        vm.isRole = true;
                    }
                    if (roles[i] != null && roles[i].authority && roles[i].authority.toLowerCase() == 'role_sdah') {
                        vm.isSdah = true;
                    }
                    if (roles[i] != null && roles[i].authority && roles[i].authority.toLowerCase() == 'role_district') {
                        vm.isDistrict = true;
                    }
                    if (roles[i] != null && roles[i].authority && roles[i].authority.toLowerCase() == 'role_ward') {
                        vm.isWard = true;
                    }
                    if (roles[i] != null && roles[i].authority && roles[i].authority.toLowerCase() == 'role_famer') {
                        vm.isFamer = true;
                    }
                    if (roles[i] != null && roles[i].authority && roles[i].authority.toLowerCase() == 'role_sdah_view') {
                        vm.isSdahView = true;
                        vm.isSdah = true;
                    }
                }
            } else {
                vm.isRole = false;
                vm.isFamer = false;
                vm.isSdah = false;
                vm.isDistrict = false;
                vm.isWard = false;
                vm.isSdahView = false;

            }
            // if($scope.hasAnyRoles($scope.currentUser, ['ROLE_FAMER'])){
            // 	// $state.go("application.farm_detail", { id: "" });
            // }
        });
        $scope.$on('$viewContentLoaded', function () {
            // initialize core components
            App.initAjax();
        });
        $rootScope.$on('$adminUnitByUser', function (e,data) {
            console.log("aa",data);
            vm.getApiUsersAndRoles(data);
        });


        // set sidebar closed and body solid layout mode
        $rootScope.settings.layout.pageContentWhite = true;
        $rootScope.settings.layout.pageBodySolid = false;
        $rootScope.settings.layout.pageSidebarClosed = false;

        var vm = this;

        vm.data = [];
        /*farmService.getAll().then(function(data){
            vm.data = data.map(element=>element);
            initMap();
        });*/

        // Start: Tree table
        vm.treeColumnDefinitions = [
            { field: "Description" },
            { field: "Area" },
            { field: "Population" },
            { field: "TimeZone", displayName: "Time Zone" }
        ];

        vm.expandingProperty = 'Name';

        vm.onSelectNode = function (node) {

        };

        vm.onClickNode = function (node) {

        };

        function initMap() {
            var map = new google.maps.Map(document.getElementById('map'), {
                zoom: 4,
                center: { lat: 21.017342, lng: 105.818178 }
            });

            var bounds = {
                north: 23.322070,
                south: 8.690170,
                east: 123.926840,
                west: 92.053184
            };

            // C??n ph???m vi b???n ?????
            map.fitBounds(bounds);
            //T???o c??c ??i???m tr??n b???n ????? th??? hi???n v??? tr?? c?? s??? ch??n nu??i
            vm.data.forEach(function (element) {
                if (element.latitude && element.longitude) {
                    var marker = new google.maps.Marker({
                        position: {
                            lat: Number(element.latitude),
                            lng: Number(element.longitude)
                        },
                        map: map
                    });
                    attachSecretMessage(marker, element);//.code +'/' +element.adressDetail
                }
            });

            map.addListener('click', function (e) {
                placeMarkerAndPanTo(e.latLng, map);
            });
            function placeMarkerAndPanTo(latLng, map) {
                //    	  var marker = new google.maps.Marker({
                //    	    position: latLng,
                //    	    map: map
                //    	  });
                console.log(latLng);
                //    	  alert(latLng);
            }
        }

        // T???o s??? ki???n khi click v??o v??? tr?? c?? s??? ch??n nu??i
        function attachSecretMessage(marker, element) {
            var infowindow = new google.maps.InfoWindow({
                content: element.code + '-' + element.name
            });

            marker.addListener('click', function () {
                infowindow.open(marker.get('map'), marker);

            });
        };
        // bi???u ????? m???t ?????
        vm.title = $translate.instant('report.densityMap');
        vm.getAllCity = function () {
            fmsAdministrativeService.getAllCity().then(function (data) {
                vm.adminstrativeUnits_City = data;
                if (vm.adminstrativeUnits_City != null && vm.adminstrativeUnits_City.length > 0) {
                    vm.ids = [];
                    for (var i = 0; i < vm.adminstrativeUnits_City.length; i++) {
                        vm.ids.push(vm.adminstrativeUnits_City[i].id);
                    }
                }
                if (vm.ids != null && vm.ids.length > 0) {
                    farmService.countFarmByListAdministrativeUnit(vm.ids, 1).then(function (data) {

                        //console.log(data);
                        if (data != null) {
                            vm.farmAus = data;
                            if (vm.farmAus != null && vm.farmAus.length > 0) {
                                data = [];
                                for (var i = 0; i < vm.farmAus.length; i++) {
                                    vm.values = [];
                                    vm.values.push(vm.farmAus[i].mapCodeAu);
                                    vm.values.push(vm.farmAus[i].count);
                                    data.push(vm.values);
                                }
                                vm.values = [];
                                vm.values.push("vn-hs");
                                vm.values.push(0);
                                data.push(vm.values);
                                vm.values = [];
                                vm.values.push("vn-ts");
                                vm.values.push(0);
                                data.push(vm.values);
                            }
                        }
                        //console.log(data);
                        // Create the chart
                        Highcharts.mapChart('container', {
                            chart: {
                                map: 'countries/vn/vn-all'
                            },

                            title: {
                                text: vm.title
                            },

                            /*subtitle: {
                                text: 'Source map: <a href="assets/scripts/chart/highcharts/vn-all.js">Vietnam</a>'
                            },*/

                            mapNavigation: {
                                enabled: true,
                                buttonOptions: {
                                    verticalAlign: 'bottom'
                                }
                            },

                            colorAxis: {
                                min: 0,
                                max: 1000,
                                /*labels: {
                                           format: '{value}con'
                                       },*/
                                stops: [
                                    [0, '#ADFF2F'],
                                    [0.3, '#0000ff'],
                                    [0.6, '#ffff00'],
                                    [1, '#ff0000']

                                ]

                            },


                            series: [{
                                data: data,
                                name: $translate.instant('report.density'),
                                borderColor: '#007f3f',
                                states: {
                                    hover: {
                                        color: '#BADA55'
                                    }
                                },
                                dataLabels: {
                                    enabled: false,
                                    format: '{point.name}'

                                },
                                events: {
                                    click: function (e) {

                                        console.log(e);
                                        console.log(e.point["hc-key"]);

                                    }
                                }
                            }]

                        });
                    }, function (respone) {
                        //toastr.error($translate.instant("toastr.createFail"), $translate.instant("toastr.error"));
                        console.log(error);
                    });
                }

            });
        }

        // vm.onSelectedYear = function (item) {
        //     vm.year = item;
        // }

        vm.listYear = [
            
                 "2022", "2021", "2020","2019","2018", "2017","2016", "2015","2014","2013","2012", "2011","2010","2009","2008","2007", "2006", "2005", "2004", "2003","2002", "2001", "2000"
            
        ]

        // vm.enterSearch = function () {
        //     // console.log(event.keyCode);
        //     if (event.keyCode == 13) {//Ph??m Enter

        //         vm.onSelectedYear();
        //     }
        // };

        // service.getCites(vm.searchDto).then(function (data) {
        //     vm.dashboardCities = data;
        //     // console.log(data)

        // });
//
        // service.getFarm(vm.searchDto).then(function (data) {
        //     vm.dashboardFarm = data;
        // });

        // service.getNumberGovermentDecree(vm.searchDto).then(function (data) {
        //     vm.dashboardReportForm = data;
        //     console.log("a", data)

        // });
        // service.getTotalCites(vm.searchDto).then(function (data) {
        //     vm.dashboardTotalCites = data;

        // });

        // service.getTotalGroverment(vm.searchDto).then(function (data) {
        //     vm.dashboardTotalGroverment = data;

        // });

        //
        service.getTotalDashboard(vm.searchDto).then(function (data) {
            vm.dashboardTotalDashboad = data;
        });

        vm.formatter  = function(num){
            return num?.toString()?.replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.')
        }

        service.getVolatility(vm.searchDto).then(function (data) {
            vm.dashboardForestProduct = data;
        //     vm.decreasedIndividualVariability = vm.formatter(Math.abs(vm?.dashboardForestProduct?.decreasedIndividualVariability));
        //     vm.dashboardForestProduct.totalListOfForestProductsByYear=vm.formatter(vm?.dashboardForestProduct?.totalListOfForestProductsByYear)
        //     vm.dashboardForestProduct.exportListOfForestProducts=vm.formatter(vm?.dashboardForestProduct?.exportListOfForestProducts)
        //     vm.dashboardForestProduct.importListOfForestProducts=vm.formatter(vm?.dashboardForestProduct?.importListOfForestProducts)
        //     vm.dashboardForestProduct.increaseVolatilityOfTheBase = vm.formatter(vm?.dashboardForestProduct?.increaseVolatilityOfTheBase)
        //   vm.dashboardForestProduct.increasedVariationOfTheIndividual = vm.formatter(vm?.dashboardForestProduct?.increasedVariationOfTheIndividual)
        //   vm.dashboardForestProduct.decreasedVolatilityOfTheBase=vm.formatter(vm?.dashboardForestProduct?.decreasedVolatilityOfTheBase)
        //   vm.dashboardForestProduct.changesInTotalHouseholds=vm.formatter(vm?.dashboardForestProduct?.changesInTotalHouseholds)
        //   vm.dashboardForestProduct.registrationFacilityInTheYear=vm.formatter(vm?.dashboardForestProduct?.registrationFacilityInTheYear)
        //   vm.dashboardForestProduct.totalEstablishmentStopsFarming=vm.formatter(vm?.dashboardForestProduct?.totalEstablishmentStopsFarming)

        });

        service.getCitesVN(vm.searchDto).then(function (data) {
            vm.dashboardCitiesVN = data;
        });

        vm.onSelectedYear = function () {
            if (vm.year != null) {
                vm.searchDto.year = parseInt(vm.year)
                

                service.getVolatility(vm.searchDto).then(function (data) {
                    vm.dashboardForestProduct = data;
                //     vm.decreasedIndividualVariability = vm.formatter(Math.abs(vm?.dashboardForestProduct?.decreasedIndividualVariability));
                //     vm.dashboardForestProduct.totalListOfForestProductsByYear=vm.formatter(vm?.dashboardForestProduct?.totalListOfForestProductsByYear)
                //     vm.dashboardForestProduct.exportListOfForestProducts=vm.formatter(vm?.dashboardForestProduct?.exportListOfForestProducts)
                //     vm.dashboardForestProduct.importListOfForestProducts=vm.formatter(vm?.dashboardForestProduct?.importListOfForestProducts)
                //     vm.dashboardForestProduct.increaseVolatilityOfTheBase = vm.formatter(vm?.dashboardForestProduct?.increaseVolatilityOfTheBase)
                //   vm.dashboardForestProduct.increasedVariationOfTheIndividual = vm.formatter(vm?.dashboardForestProduct?.increasedVariationOfTheIndividual)
                //   vm.dashboardForestProduct.decreasedVolatilityOfTheBase=vm.formatter(vm?.dashboardForestProduct?.decreasedVolatilityOfTheBase)
                //   vm.dashboardForestProduct.changesInTotalHouseholds=vm.formatter(vm?.dashboardForestProduct?.changesInTotalHouseholds)
                //   vm.dashboardForestProduct.registrationFacilityInTheYear=vm.formatter(vm?.dashboardForestProduct?.registrationFacilityInTheYear)
                //   vm.dashboardForestProduct.totalEstablishmentStopsFarming=vm.formatter(vm?.dashboardForestProduct?.totalEstablishmentStopsFarming)
                });

                // service.getNumberGovermentDecree(vm.searchDto).then(function (data) {
                //     vm.dashboardReportForm = data;
                // });
//
                // service.getCites(vm.searchDto).then(function (data) {
                //     vm.dashboardCities = data;
                //     // console.log(data)

                // });

                // service.getFarm(vm.searchDto).then(function (data) {
                //     vm.dashboardFarm = data;
                //     // console.log(data)

                // });
                // service.getCitesVN(vm.searchDto).then(function (data) {
                //     vm.dashboardCitiesVN = data;
                // });

                // service.getTotalCites(vm.searchDto).then(function (data) {
                //     vm.dashboardTotalCites = data;
                // });

                // service.getTotalGroverment(vm.searchDto).then(function (data) {
                //     vm.dashboardTotalGroverment = data;
                // });

                //
                service.getTotalDashboard(vm.searchDto).then(function (data) {
                    vm.dashboardTotalDashboad = data;
                });
            }
            else{
                vm.searchDto.year = parseInt(vm.year)
                // service.getFarmProvidedCodeInYearNumber(vm.searchDto).then(function (data) {
                //     vm.isView = true;
                //     vm.dashboardSumInYear = data;
                //     // console.log(data);
                // });

                service.getVolatility(vm.searchDto).then(function (data) {
                    vm.dashboardForestProduct = data;
                    // console.log(data)
                //     vm.decreasedIndividualVariability = vm.formatter(Math.abs(vm?.dashboardForestProduct?.decreasedIndividualVariability));
                //     vm.dashboardForestProduct.totalListOfForestProductsByYear=vm.formatter(vm?.dashboardForestProduct?.totalListOfForestProductsByYear)
                //     vm.dashboardForestProduct.exportListOfForestProducts=vm.formatter(vm?.dashboardForestProduct?.exportListOfForestProducts)
                //     vm.dashboardForestProduct.importListOfForestProducts=vm.formatter(vm?.dashboardForestProduct?.importListOfForestProducts)
                //     vm.dashboardForestProduct.increaseVolatilityOfTheBase = vm.formatter(vm?.dashboardForestProduct?.increaseVolatilityOfTheBase)
                //   vm.dashboardForestProduct.increasedVariationOfTheIndividual = vm.formatter(vm?.dashboardForestProduct?.increasedVariationOfTheIndividual)
                //   vm.dashboardForestProduct.decreasedVolatilityOfTheBase=vm.formatter(vm?.dashboardForestProduct?.decreasedVolatilityOfTheBase)
                //   vm.dashboardForestProduct.changesInTotalHouseholds=vm.formatter(vm?.dashboardForestProduct?.changesInTotalHouseholds)
                //   vm.dashboardForestProduct.registrationFacilityInTheYear=vm.formatter(vm?.dashboardForestProduct?.registrationFacilityInTheYear)
                //   vm.dashboardForestProduct.totalEstablishmentStopsFarming=vm.formatter(vm?.dashboardForestProduct?.totalEstablishmentStopsFarming)

                });

                // service.getNumberGovermentDecree(vm.searchDto).then(function (data) {
                //     vm.dashboardReportForm = data;
                // });

                // service.getCites(vm.searchDto).then(function (data) {
                //     vm.dashboardCities = data;
                // });

                // service.getFarm(vm.searchDto).then(function (data) {
                //     vm.dashboardFarm = data;
                // });
                // service.getCitesVN(vm.searchDto).then(function (data) {
                //     vm.dashboardCitiesVN = data;
                // });

                // service.getTotalCites(vm.searchDto).then(function (data) {
                //     vm.dashboardTotalCites = data;
                // });

                // service.getTotalGroverment(vm.searchDto).then(function (data) {
                //     vm.dashboardTotalGroverment = data;
                // });

                //
                service.getTotalDashboard(vm.searchDto).then(function (data) {
                    vm.dashboardTotalDashboad = data;
                });
            }


            
            
        }

        
        $scope.token_id = "";
        vm.getApiUsersAndRoles = function (data) {
            var listUserAndRoles = [];
            var tokenCookies = $cookies.getObject('token');
            var currentUserCookies = $cookies.getObject('currentUser');
            var adminUnitByUserCookies = data?data: $cookies.getObject('adminUnitByUser');
            // var adminUnitByUserCookies = data;
            vm.updateDto = {};
            vm.updateDto.role = [];
            vm.updateDto.is_admin = false;
            vm.updateDto.provinces = [];
            vm.updateDto.districts = [];
            vm.updateDto.commune = [];
            vm.updateDto.token_id = tokenCookies.access_token;
            if(currentUserCookies && currentUserCookies.roles){
                for (var i = 0; i < currentUserCookies.roles.length; i++) {
                    vm.updateDto.role.push(currentUserCookies.roles[i].name);
                    if (currentUserCookies.roles[i].name === 'ROLE_ADMIN') {
                        vm.updateDto.is_admin = true;
                    }
                }
            }
            if(adminUnitByUserCookies){
                for (var i = 0; i < adminUnitByUserCookies.length; i++) {
                    if (adminUnitByUserCookies[i].level == 1) {
                        vm.updateDto.provinces.push(adminUnitByUserCookies[i].code);
                    }
                    else if (adminUnitByUserCookies[i].level == 2) {
                        vm.updateDto.districts.push(adminUnitByUserCookies[i].code);
                    }
                }
            }
            listUserAndRoles.push(vm.updateDto);
            sumQuantityReportDataService.getApiUsersAndRoles(listUserAndRoles).then(function (data) {
                // toastr.info("L???y d??? li???u th??nh c??ng.", 'Th??ng b??o');
                vm.viewPosition = true;
                $scope.token_id = vm.updateDto.token_id;
                let ts = new Date().getTime();
                $rootScope.iframeUrl = $sce.trustAsResourceUrl("https://fao.gisgo.vn/samples/index.html?token_id=" + $scope.token_id + "&ts="+ts);
            }, function (respone) {

            });

        }
        
       
    }

   
})();
