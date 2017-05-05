(function() {
    'use strict';
    angular
        .module('volBetailApp')
        .factory('FicheAnimal', FicheAnimal);

    FicheAnimal.$inject = ['$resource', 'DateUtils'];

    function FicheAnimal ($resource, DateUtils) {
        var resourceUrl =  'api/fiche-animals/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateDec = DateUtils.convertDateTimeFromServer(data.dateDec);
                        data.dateNais = DateUtils.convertDateTimeFromServer(data.dateNais);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
