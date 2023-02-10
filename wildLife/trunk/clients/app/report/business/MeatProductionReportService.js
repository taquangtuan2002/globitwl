(function () {
    'use strict';

    angular.module('Education.Report').service('MeatProductionReportService', MeatProductionReportService);

    MeatProductionReportService.$inject = [
        '$http',
        '$q',
        '$filter',
        'settings',
        'Utilities',
        '$translate'
    ];

    function MeatProductionReportService($http, $q, $filter, settings, utils,$translate) {
        var self = this;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix + 'report/';
        var Url = settings.api.baseUrl + settings.api.apiPrefix ;
        
        
        self.getMeatYieldReport = getMeatYieldReport;
        self.getTableDefinition = getTableDefinition;
		self.getEggSummaryReport=getEggSummaryReport;
        function getMeatYieldReport(dto, successCallback, errorCallback) {
            var url = baseUrl + 'getMeatYieldReport';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
		function getEggSummaryReport(dto, successCallback, errorCallback) {
            var url = baseUrl + 'getEggSummaryReport';

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
                    field: '',
                    title: 'Vùng sinh thái',
                    sortable: true,
                    cellStyle: _cellNowrap,
//                    formatter:_formatterName,
                    switchable: true,
                },{
                    field: '',
                    title: 'Tỉnh',
                    sortable: true,
                    cellStyle: _cellNowrap,
//                    formatter:_formatterName,
                    switchable: true,
                },{
                    field: '',
                    title: 'Gà Thịt',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                },{
                    field: '',
                    title: 'Vị Thịt',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                },{
                    field: '',
                    title: 'Động vật Khác Thịt',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter:_formatterName,
                    switchable: true,
                }, 
                {
                    field: '',
                    title: 'Tổng con thịt',
                    sortable: true,
                    cellStyle: _cellNowrap,
                    formatter:_formatterName,
                    switchable: true,
                }
            ];
        };
    }
})();