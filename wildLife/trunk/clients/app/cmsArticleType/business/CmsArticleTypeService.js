/**
 * Created by nguyen the dat on 22/3/2018.
 */
(function () {
    'use strict';

    angular.module('Education.CmsArticleType').service('CmsArticleTypeService', CmsArticleTypeService);

    CmsArticleTypeService.$inject = [
        '$http',
        '$q',
        '$filter',
        'settings',
        'Utilities',
		'$translate'
    ];

    function CmsArticleTypeService($http, $q, $filter, settings, utils,$translate) {
        var self = this;
        //var baseUrl=;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix;
        	
        self.getArticleTypes = getArticleTypes;
        self.createArticleType = createArticleType;
        self.getArticleType = getArticleType;        
        self.deleteArticleById = deleteArticleById;
        self.getTableDefinition = getTableDefinition;
		self.getTableDefinitionNotAdmin=getTableDefinitionNotAdmin;
        self.getCurrentUser=getCurrentUser;
        self.checkDuplicateCode=checkDuplicateCode;
        
        function checkDuplicateCode(code) {
            var url = baseUrl + 'cms/articletype/checkCode/' + code;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
		
		
		function getCurrentUser() {
            var url = baseUrl + 'users/getCurrentUser';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getArticleTypes(pageIndex, pageSize) {
            var url = baseUrl + 'cms/articletype/page';
            url += '/' + ((pageIndex >= 0) ? pageIndex : 0);
            url += '/' + ((pageSize > 0) ? pageSize : 25);

            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function createArticleType(dto, successCallback, errorCallback) {
            var url = baseUrl + 'cms/articletype/add';

            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
        
        function getArticleType(id) {
            if (!id) {
                return $q.when(null);
            }

            var url = baseUrl + 'cms/articletype/' + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function deleteArticleById(articleId, successCallback, errorCallback) {

            var url = baseUrl + 'cms/articletype/removeList';
            return utils.resolveAlt(url, 'DELETE', null, [{id: articleId}], {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function getTableDefinition() {

            var _tableOperation = function (value, row, index) {
                return '<a class="green-dark margin-right-20" href="#" data-ng-click="$parent.editArticleType(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5"></i>{{"cmsArticleType.edit" | translate}}</a>'
                    + '<a class="green-dark margin-right-20" href="#" data-ng-click="$parent.deleteArticleType(' + "'" + row.id + "'" + ')"><i class="fa fa-trash margin-right-5"></i>{{"cmsArticleType.delete" | translate}}</a>';
            };
        	var _formaterType = function (value, row, index, field) {
                if(value==1){
                	return 'Phòng- Ban hành chính';
                }
                else if(value==2){
                	return 'Khoa- Trung tâm đào tạo';
                }
                else{
                	return '';
                }
            };
            var _cellNowrap = function (value, row, index, field) {
                return {
                    classes: '',
                    css: {'white-space': 'nowrap'}
                };
            };

            return [
                // {
                //     field: 'state',
                //     checkbox: true
                // }, 
                {
                    field: '',
                    title: '{{"cmsArticleType.action" | translate}}',
                    switchable: true,
                    visible: true,
                    formatter: _tableOperation,
                    cellStyle: _cellNowrap
                }
                , {
                    field: 'code',
                    title: '{{"cmsArticleType.code" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }
                , {
                    field: 'name',
                    title: '{{"cmsArticleType.name" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }
                , {
                    field: 'description',
                    title: '{{"cmsArticleType.description" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }
                , {
                    field: 'priority',
                    title: '{{"cmsArticleType.priority" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }
            ]
        }
		
		function getTableDefinitionNotAdmin() {

           
        	var _formaterType = function (value, row, index, field) {
                if(value==1){
                	return 'Phòng- Ban hành chính';
                }
                else if(value==2){
                	return 'Khoa- Trung tâm đào tạo';
                }
                else{
                	return '';
                }
            };
            var _cellNowrap = function (value, row, index, field) {
                return {
                    classes: '',
                    css: {'white-space': 'nowrap'}
                };
            };

            return [
                 {
                    field: 'code',
                    title: '{{"cmsArticleType.code" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }
                , {
                    field: 'name',
                    title: '{{"cmsArticleType.name" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }
                , {
                    field: 'description',
                    title: '{{"cmsArticleType.description" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }
                , {
                    field: 'priority',
                    title: '{{"cmsArticleType.priority" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }
            ]
        }
    }

})();