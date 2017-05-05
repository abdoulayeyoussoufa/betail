(function() {
    'use strict';
    angular
        .module('volBetailApp')
        .factory('AvisPerte', AvisPerte);

    AvisPerte.$inject = ['$resource'];

    function AvisPerte ($resource) {
        var resourceUrl =  'api/avis-pertes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
