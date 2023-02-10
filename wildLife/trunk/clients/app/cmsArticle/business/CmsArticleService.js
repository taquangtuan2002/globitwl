/**
 * Created by nguyen the dat on 22/3/2018.
 */
(function () {
    'use strict';

    angular.module('Education.CmsArticle').service('CmsArticleService', CmsArticleService);

    CmsArticleService.$inject = [
        '$http',
        '$q',
        '$filter',
        'settings',
        'Utilities',
		'$translate'
    ];

    function CmsArticleService($http, $q, $filter, settings, utils,$translate) {
        var self = this;
        //var baseUrl=;
        var baseUrl = settings.api.baseUrl + settings.api.apiPrefix;
        	
        self.getArticles = getArticles;
        self.saveArticle = saveArticle;
        self.getArticle = getArticle;
        self.updateArticle=updateArticle;         
        self.deleteArticleById = deleteArticleById;
        self.getSearchArticleByDto = getSearchArticleByDto;
        self.getImage = getImage;
        self.getTableDefinition = getTableDefinition;
		self.getTableDefinitionNotAdmin=getTableDefinitionNotAdmin;
		self.getCurrentUser=getCurrentUser;
		
		
		function getCurrentUser() {
            var url = baseUrl + 'users/getCurrentUser';
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function getArticles(pageIndex, pageSize) {
            var url = baseUrl + 'cms/article/page';
            url += '/' + ((pageIndex >= 0) ? pageIndex : 0);
            url += '/' + ((pageSize > 0) ? pageSize : 25);

            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function saveArticle(priority, successCallback, errorCallback) {
            var url = baseUrl + 'cms/article/create';

            return utils.resolveAlt(url, 'POST', null, priority, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function updateArticle(priority, successCallback, errorCallback) {
            var url = baseUrl + 'cms/article/update/'+priority.id;
            //console.log(url);
            return utils.resolveAlt(url, 'PUT', null, priority, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }
        
        function getArticle(id) {
            if (!id) {
                return $q.when(null);
            }

            var url = baseUrl + 'cms/article/' + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }
        function getImage(id) {
            if (!id) {
                return $q.when(null);
            }

            var url = baseUrl + 'cms/article/image/' + id;
            return utils.resolve(url, 'GET', angular.noop, angular.noop);
        }

        function deleteArticleById(id, successCallback, errorCallback) {
            var url = baseUrl + 'cms/article/delete/'+id;
            return utils.resolveAlt(url, 'DELETE', null, null, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function getSearchArticleByDto(dto, successCallback, errorCallback) {
            var url = baseUrl + 'cms/article/search';
            return utils.resolveAlt(url, 'POST', null, dto, {
                'Content-Type': 'application/json; charset=utf-8'
            }, successCallback, errorCallback);
        }

        function getTableDefinition() {

            var _tableOperation = function (value, row, index) {
                return '<a class="green-dark margin-right-20" href="#" data-ng-click="$parent.editArticle(' + "'" + row.id + "'" + ')"><i class="icon-pencil margin-right-5"></i>{{"cmsArticle.edit" | translate}}</a>'
                    + '<a class="green-dark margin-right-20" href="#" data-ng-click="$parent.deleteArticle(' + "'" + row.id + "'" + ')"><i class="fa fa-trash margin-right-5"></i>{{"cmsArticle.delete" | translate}}</a>';
            };
            var _cellNowrap = function (value, row, index, field) {
                return {
                    classes: '',
                    css: {'white-space': 'nowrap'}
                };
            };
            var _substring = function (value, row, index) {
            	if(value.length>50){            		
            		return '<span style="word-wrap: break-word;">'+value.substring(0,250)+'...'+'</span>';
            	}
                return value;
            };
            var _cellContent = function (value, row, index, field) {
                return {
                    classes: '',
                    css: {'width': '70%'}
                };
            };
            var _cellTitle = function (value, row, index, field) {
                return {
                    classes: '',
                    css: {'width': '20%'}
                };
            };
            return [
                // {
                //     field: 'state',
                //     checkbox: true
                // },
                 {
                    field: '',
                    title: '{{"cmsArticle.action" | translate}}',
                    switchable: true,
                    visible: true,
                    formatter: _tableOperation,
                    cellStyle: _cellNowrap
                }
                , {
                    field: 'title',
                    title: '{{"cmsArticle.title" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellTitle,
                    formatter: _substring
                }
                , {
                    field: 'summary',
                    title: '{{"cmsArticle.synopsis" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellContent,
                    formatter: _substring
                }
            ]
        }
		function getTableDefinitionNotAdmin() {

            var _tableOperation = function (value, row, index) {
                return '<a class="green-dark margin-right-20" href="#" data-ng-click="$parent.viewArticle(' + "'" + row.id + "'" + ')"><i class="fa fa-eye margin-right-5"></i>{{"cmsArticle.view" | translate}}</a>';
            };
            var _cellNowrap = function (value, row, index, field) {
                return {
                    classes: '',
                    css: {'white-space': 'nowrap'}
                };
            };
            var _substring = function (value, row, index) {
            	if(value.length>50){            		
            		return '<span style="word-wrap: break-word; max-width: 200px;">'+value.substring(0,250)+'...'+'</span>';
            	}
                return value;
            };
            return [
				{
                    field: '',
                    title: '{{"cmsArticle.action" | translate}}',
                    switchable: true,
                    visible: true,
                    formatter: _tableOperation,
                    cellStyle: _cellNowrap
                },
                {
                    field: 'title',
                    title: '{{"cmsArticle.title" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    formatter: _substring
                }
                , {
                    field: 'summary',
                    title: '{{"cmsArticle.synopsis" | translate}}',
                    sortable: true,
                    switchable: false,
                    cellStyle: _cellNowrap,
                    formatter: _substring
                }
            ]
        }
    }

})();