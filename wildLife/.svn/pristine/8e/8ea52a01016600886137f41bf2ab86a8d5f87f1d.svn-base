(function () {
    'use strict';

    angular.module('Education.Report').service('QuantityReportService', QuantityReportService);

    QuantityReportService.$inject = [
        '$http',
        '$q',
        '$filter',
        'settings',
        'Utilities',
        '$translate'
    ];

    function QuantityReportService($http, $q, $filter, settings, utils,$translate) {
        var self = this;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix + 'report/';
		var Url = settings.api.baseUrl + settings.api.apiPrefix ;

        self.getTableDefinition = getTableDefinition;
        self.getQuantityReport = getQuantityReport;
        self.getEggQuantityReport = getEggQuantityReport;
        self.getLivestockCapacityReport = getLivestockCapacityReport;
		self.getStream=getStream;
		self.getAdministrativeUnitDtoByLoginUser=getAdministrativeUnitDtoByLoginUser;
		self.getCurrentUser=getCurrentUser;
		self.farmSummaryReport=farmSummaryReport;
		self.getSeedLevelProducTargetReport=getSeedLevelProducTargetReport;
		self.getLiveStockProductReport=getLiveStockProductReport;
		self.getAllLiveStockProduct=getAllLiveStockProduct;
		self.getPercentOfGrowthQuantityReport=getPercentOfGrowthQuantityReport;
		self.getPercentOfGrowthEggReport=getPercentOfGrowthEggReport;
		self.getExportEggFollowTheHerdReport=getExportEggFollowTheHerdReport;
		self.getPercentOfGrowthMeatReport=getPercentOfGrowthMeatReport;
		self.getMeatProductivityForecastReport=getMeatProductivityForecastReport;
		self.getMeatDetailProductivityForecastReport=getMeatDetailProductivityForecastReport;
		self.getEggProductivityForecastReport=getEggProductivityForecastReport;
        self.getEggDetailProductivityForecastReport=getEggDetailProductivityForecastReport;
        self.getWeeksByYear=getWeeksByYear;
        self.getPredictTheNumberOfLiveMeatSlightly=getPredictTheNumberOfLiveMeatSlightly;
        self.getComparePredictTheNumberOfLiveMeatSlightly=getComparePredictTheNumberOfLiveMeatSlightly;
        self.getListAdministrativeUnitCodeByUserLogin=getListAdministrativeUnitCodeByUserLogin;
        
		function getListAdministrativeUnitCodeByUserLogin() {
            var url = Url + 'user_administrative/getListAdministrativeUnitCodeByUserLogin';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

		function getWeeksByYear(year) {
            var url = settings.api.baseUrl + 'public/publicAPI/getWeeksByYear/' + year;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

		function getCurrentUser() {
            var url = Url + 'users/getCurrentUser';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
		function getAdministrativeUnitDtoByLoginUser() {
            var url = Url + 'user_administrative/get_administrativeUnit_dto_by_loginuser';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function getQuantityReport(dto, successCallback, errorCallback) {
            var url = baseUrl + 'inventory';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
        function getEggQuantityReport(dto, successCallback, errorCallback) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'export_egg/eggQuantityReport';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
        function getLivestockCapacityReport(dto, successCallback, errorCallback) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'farm/farmReport';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
        function getSeedLevelProducTargetReport(dto, successCallback, errorCallback) {
            var url = baseUrl + 'seedLevelQuantityProductTargetReport';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
        function farmSummaryReport(dto, successCallback, errorCallback) {
            var url = baseUrl + 'farm_summary_report';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
		function getStream(code,id,level){
            console.log("RUNNING");
            var deferred = $q.defer();

            $http({
                url:settings.api.baseUrl + settings.api.apiPrefix  +'file/downloadListDistrict/'+code+'/'+id+'/'+level ,
                method:"POST",//you can use also GET or POST
                data:null,
                headers:{'Content-type': 'application/json'},
                responseType : 'arraybuffer',//THIS IS IMPORTANT
            })
                .success(function (data) {
                    console.debug("SUCCESS");
                    deferred.resolve(data);
                }).error(function (data) {
                console.error("ERROR");
                deferred.reject(data);
            });

            return deferred.promise;
        };
		function getLiveStockProductReport(dto, successCallback, errorCallback) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'import_export/liveStockProduct/report';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
		 function getAllLiveStockProduct() {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'liveStockProduct/getalldtos';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
		function getPercentOfGrowthQuantityReport(dto, successCallback, errorCallback) {
            var url = settings.api.baseUrl + settings.api.apiPrefix  + 'import_export/animal/percentOfGrowthQuantity';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
        
		function getExportEggFollowTheHerdReport(dto, successCallback, errorCallback) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'export_egg/export_egg_follow_the_herd';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
		
		function getPercentOfGrowthEggReport(dto, successCallback, errorCallback) {
            var url = settings.api.baseUrl + settings.api.apiPrefix + 'export_egg/percentOfGrowthEgg';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
		function getPercentOfGrowthMeatReport(dto, successCallback, errorCallback) {
            var url = settings.api.baseUrl + settings.api.apiPrefix  + 'import_export/animal/percentOfGrowthMeat';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
		function getMeatProductivityForecastReport(dto, successCallback, errorCallback) {
            var url = settings.api.baseUrl + settings.api.apiPrefix  + 'report/getMeatProductivityForecastReport';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
		function getPredictTheNumberOfLiveMeatSlightly(dto, successCallback, errorCallback) {
            var url = settings.api.baseUrl + settings.api.apiPrefix  + 'report/getPredictTheNumberOfLiveMeatSlightly';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
		function getMeatDetailProductivityForecastReport(dto, successCallback, errorCallback) {
            var url = settings.api.baseUrl + settings.api.apiPrefix  + 'report/getMeatDetailProductivityForecastReport';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
		function getComparePredictTheNumberOfLiveMeatSlightly(dto, successCallback, errorCallback) {
            var url = settings.api.baseUrl + settings.api.apiPrefix  + 'report/getPredictTheNumberOfLiveMeatSlightly/compare';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
		function getEggProductivityForecastReport(dto, successCallback, errorCallback) {
            var url = settings.api.baseUrl + settings.api.apiPrefix  + 'report/getEggProductivityForecastReport';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
		function getEggDetailProductivityForecastReport(dto, successCallback, errorCallback) {
            var url = settings.api.baseUrl + settings.api.apiPrefix  + 'report/getEggDetailProductivityForecastReport';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
 
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
                },{
                    field: 'amount',
                    title: '{{"exportAnimal.amount"| translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                },{
                    field: 'animal',
                    title: '{{"importAnimal.animal.name" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter:_formatterName,
                    switchable: true,
                }, 
                {
                    field: 'parent',
                    title: '{{"farm.animal" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter:_formatterName,
                    switchable: true,
                }, 
                {
                    field: 'animalType',
                    title: '{{"animalType.information" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter:_formatterName,
                    switchable: true,
                },{
                    field: 'farm',
                    title: '{{"importAnimal.farm.name" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter:_formatterName,
                    switchable: true,
                },
                {
                    field: 'ward',
                    title: '{{"farm.ward" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter:_formatterName,
                    switchable: true,
                },
                {
                    field: 'district',
                    title: '{{"farm.district" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter:_formatterName,
                    switchable: true,
                },
                {
                    field: 'province',
                    title: '{{"administrativeUnit.province" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter:_formatterName,
                    switchable: true,
                },
                {
                    field: 'region',
                    title: '{{"region.information" | translate}}',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter:_formatterName,
                    switchable: true,
                }
            ];
        };
    }
})();