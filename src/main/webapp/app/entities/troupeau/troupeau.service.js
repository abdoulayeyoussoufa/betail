(function() {
    'use strict';
    angular
        .module('volBetailApp')
        .factory('Troupeau', Troupeau);

    Troupeau.$inject = ['$resource'];

    function Troupeau ($resource) {
        var resourceUrl =  'api/troupeaus/:id';

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
