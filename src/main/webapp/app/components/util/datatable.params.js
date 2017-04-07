(function() {
    'use strict';
angular.module('mutuplexApp')
.service('DatatableUtils',DatatableUtils);
function DatatableUtils () {
	return this.buildParams = function(aoData, customerParams) {
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
	};      
}

})();