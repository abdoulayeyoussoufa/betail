(function() {
    'use strict';
    angular
        .module('volBetailApp')
        .factory('FicheTracabilite', FicheTracabilite);

    FicheTracabilite.$inject = ['$resource', 'DateUtils'];

    function FicheTracabilite ($resource, DateUtils) {
        var resourceUrl =  'api/fiche-tracabilites/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateObt = DateUtils.convertLocalDateFromServer(data.dateObt);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateObt = DateUtils.convertLocalDateToServer(copy.dateObt);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateObt = DateUtils.convertLocalDateToServer(copy.dateObt);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
