(function() {
	'use strict';

	angular
	.module('mutuplexApp')
	.controller('AssureController', AssureController);

	AssureController.$inject = ['$scope', '$state','$http','$compile', 'Assure', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants','DTOptionsBuilder','DTColumnBuilder'];

	function AssureController ($scope, $state,$http,$compile, Assure, ParseLinks, AlertService, pagingParams, paginationConstants,DTOptionsBuilder, DTColumnBuilder) {
		var vm = this;
		
		vm.loadPage = loadPage;
		vm.predicate = pagingParams.predicate;
		vm.reverse = pagingParams.ascending;
		vm.transition = transition;
		vm.itemsPerPage = paginationConstants.itemsPerPage;

		//loadAll();

		function loadAll () {
			Assure.query({
				page: pagingParams.page - 1,
				size: vm.itemsPerPage,
				sort: sort()
			}, onSuccess, onError);
			function sort() {
				var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
				if (vm.predicate !== 'id') {
					result.push('id');
				}
				return result;
			}
			function onSuccess(data, headers) {
				vm.links = ParseLinks.parse(headers('link'));
				vm.totalItems = headers('X-Total-Count');
				vm.queryCount = vm.totalItems;
				vm.assure = data;
				vm.page = pagingParams.page;
				console.log("onsucess");
			}
			function onError(error) {
				AlertService.error(error.data.message);
			}
		}

		function loadPage (page) {
			vm.page = page;
			vm.transition();
		}

		function transition () {
			$state.transitionTo($state.$current, {
				page: vm.page,
				sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
				search: vm.currentSearch
			});
		}
		$scope.dtInstance = {};
		$scope.dtOptions = DTOptionsBuilder.newOptions()
		.withDOM('<"html5buttons"B>lTfgitp')
		.withBootstrap()
		.withDisplayLength(10)
		.withOption('lengthMenu', [
			[10, 25, 50, 100, -1],
			[10, 25, 50, 100, "Tous"]
			])
			.withOption('responsive', true)
			.withTableTools('bower_components/datatables-tabletools/swf/copy_csv_xls_pdf.swf')
            .withTableToolsButtons([
                'copy',
                'csv',
                'xls',
                'pdf'
            ])
				.withDataProp('data').withPaginationType(
				'full_numbers').withOption('processing',
						true)
						.withOption('ajax', {
							url : 'api/assure-datatable',
							type : 'GET',
							data: function(d) {
								
							},
							error: function (request, status, error) {
								console.log(status);
							}
						})
						.withFnServerData(serverData)
						.withOption('serverSide', true)
						.withOption('createdRow', createdRow)		
						.withDOM('<"row"<"col-xs-6"l><"col-xs-2"T><"col-xs-4 text-right"f>>rt<"row"<"col-xs-6"i><"col-xs-6"p>>');
		$scope.dtColumns = [
			DTColumnBuilder.newColumn('nom').withTitle('Nom'),
			DTColumnBuilder.newColumn('prenom').withTitle('Prénom'),
			DTColumnBuilder.newColumn('sexe').withTitle('Sexe'),
			DTColumnBuilder.newColumn('email').withTitle('Email'),
			DTColumnBuilder.newColumn('lieu').withTitle('Lieu de naissance'),
			DTColumnBuilder.newColumn('matricule').withTitle('Matricule'),
			DTColumnBuilder.newColumn('assure_police.raison').withTitle('Police'),
			DTColumnBuilder.newColumn(null).withTitle('Groupe')
			.renderWith(function (data, type, full, meta) {
				if( data.assure_groupe!=null)
					return data.assure_groupe.intitule;
				else
					return "";
			}).notSortable().withOption('searchable', false),
			DTColumnBuilder.newColumn(null).withTitle('Action').withOption('width', '10%')
			.renderWith(function (data, type, full, meta) {
				return '<div class="hidden-sm hidden-xs action-buttons">' +
				'<a title="Fiche" ui-sref="assure-detail({id:' + data.id + '})" class="blue"> <i class="fa fa-search-plus bigger-130"></i></a>'+
				'<a title="Suppimer" ui-sref="assure.delete({id:' + data.id + '})" class="red"> <i class="fa fa-trash-o bigger-130"></i></a>'+
				'<a title="Archiver" ui-sref="assure.edit({id:' + data.id + '})" class="red"> <i class="fa fa-archive bigger-130"></i></a>'+
				'<a title="Creer ayant droit" ui-sref="ayant-droit.new({id:' +data.id+ '})" class="green"> <i class=" fa fa-user bigger-130"></i></a>'+
				'</div>';
			}).notSortable().withOption('searchable', false)];
		function serverData(sSource, aoData, fnCallback, oSettings) {
			var customerParams = {
			};
			var	params = buildParams(aoData, customerParams);
			$scope.employePromise = $http({
				method: 'GET',
				url: 'api/assure-datatable',
				params: params
			})
			.then(function(result) {
				fnCallback(result.data);
			});
			return $scope.policePromise;
		}
		function createdRow(row, data, dataIndex) {
//			Recompiling so we can bind Angular directive to the DT
			$compile(angular.element(row).contents())($scope);
		}
		$scope.dtInstanceCallback = function(_dtInstance) {
			$scope.dtInstance = _dtInstance;
			//$scope.dtInstance.reloadData(); 
		}
		function buildParams(aoData, customerParams) {
			var params = {
					'start': aoData[3].value,
					'length': aoData[4].value,
					'draw': aoData[0].value,
					'order[0].column' : aoData[2]["value"][0].column,
					'order[0].dir' : aoData[2]["value"][0].dir,
					'search.regex' : aoData[5]["value"].regex,
					'search.value' : aoData[5]["value"].value
			};
			var columns = aoData[1]["value"];
			for(var i=0;i<columns.length;i++){
				var column = columns[i];
				params['columns[' + i + '].data'] = column.data;
				params['columns[' + i + '].name'] = column.name;
				params['columns[' + i + '].orderable'] = column.orderable;
				params['columns[' + i + '].searchable'] = column.searchable;
				params['columns[' + i + '].search.regex'] = column["search"].regex;
				params['columns[' + i + '].search.value'] = column["search"].value;
			}
			for ( var key in customerParams) {
				params[key] = customerParams[key];
			}
			return params;
		}
	}
})();
