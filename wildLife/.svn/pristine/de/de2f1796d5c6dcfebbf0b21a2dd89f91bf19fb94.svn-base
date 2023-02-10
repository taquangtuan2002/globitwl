/**
 * Created by nguyen the dat on 22/3/2018.
 */
(function () {
    'use strict';

    angular.module('Education.CmsCategory').service('CmsCategoryService', CmsCategoryService);

    CmsCategoryService.$inject = [
        '$http',
        '$q',
        '$filter',
        'settings',
        'Utilities',
		'$translate'
    ];

    function CmsCategoryService($http, $q, $filter, settings, utils,$translate) {
        var self = this;
        //var baseUrl=;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix;

        // self.getdepartments = getdepartments;
        // self.savedepartment = savedepartment;
        self.getPageCategory = getPageCategory;
        self.getCategoryById = getCategoryById;
        self.createCategory = createCategory;
        self.updateCategory = updateCategory;
        self.deleteCategoryById = deleteCategoryById;
        self.getTableDefinition = getTableDefinition;		
		self.getCurrentUser=getCurrentUser;
		self.getTableDefinitionNotAdmin=getTableDefinitionNotAdmin;
		
		
		function getCurrentUser() {
            var url = baseUrl + 'users/getCurrentUser';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
		
        function getPageCategory(pageIndex, pageSize) {
            var url = baseUrl + 'cms/category/page';
            url += '/' + ((pageIndex >= 0) ? pageIndex : 0);
            url += '/' + ((pageSize > 0) ? pageSize : 25);

            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function createCategory(dto, successCallback, errorCallback) {
            var url = baseUrl + 'cms/category/create';
            //console.log(url);
            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function updateCategory(dto, successCallback, errorCallback) {
            var url = baseUrl + 'cms/category/update/' + dto.id;
            //console.log(url);
            return utils.resolveAlt(url, 'PUT', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function getCategoryById(id) {
            if (!id) {
                return $q.when(null);
            }

            var url = baseUrl + 'cms/category/' + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function deleteCategoryById(id, successCallback, errorCallback) {

            var url = baseUrl + 'cms/category/delete/' + id;
            return utils.resolveAlt(url, 'DELETE', null, null, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function getTableDefinition() {

            var _tableOperation = function (value, row, index) {
                return '<a class="green-dark margin-right-20" href="#" data-ng-click="$parent.editCategory(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5"></i>{{"cmscategory.edit" | translate}}</a>'
                    + '<a class="green-dark margin-right-20" href="#" data-ng-click="$parent.deleteCategory(' + "'" + row.id + "'" + ')"><i class="fa fa-trash margin-right-5"></i>{{"cmscategory.delete" | translate}}</a>';
            };

            var _cellNowrap = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap' }
                };
            };

            return [
                // {
                //     field: 'state',
                //     checkbox: true
                // }, 
                {
                    field: '',
                    title: '{{"cmscategory.action" | translate}}',
                    switchable: true,
                    visible: true,
                    formatter: _tableOperation,
                    cellStyle: _cellNowrap
                }
                , {
                    field: 'title',
                    title: '{{"cmscategory.title" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }
                , {
                    field: 'description',
                    title: '{{"cmscategory.description" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }
            ]
        }
		function getTableDefinitionNotAdmin() {

            var _tableOperation = function (value, row, index) {
                return '<a class="green-dark margin-right-20" href="#" data-ng-click="$parent.editCategory(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5"></i>{{"cmscategory.edit" | translate}}</a>'
                    + '<a class="green-dark margin-right-20" href="#" data-ng-click="$parent.deleteCategory(' + "'" + row.id + "'" + ')"><i class="fa fa-trash margin-right-5"></i>{{"cmscategory.delete" | translate}}</a>';
            };

            var _cellNowrap = function (value, row, index, field) {
                return {
                    classes: '',
                    css: { 'white-space': 'nowrap' }
                };
            };

            return [
                 {
                    field: 'title',
                    title: '{{"cmscategory.title" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }
                , {
                    field: 'description',
                    title: '{{"cmscategory.description" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap
                }
            ]
        }
    }

})();