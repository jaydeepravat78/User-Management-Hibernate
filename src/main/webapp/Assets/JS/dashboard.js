$(document).ready(function() {
    var table =  $('#users-table').DataTable( {
        autoWidth: false,
        ajax: 
        {
            url: "DashboardController",
            dataType: "JSON",
            error: function(xhr, error, code) {
                console("Status: " + xhr.status + " Error: "+ xhr.statusText);
            }       
        },
        columns: [
            {
                'data': 'id',
                className: 'dt-body-center'
            },
            {
                'data': 'name',
                className: 'dt-body-center'
            },
            {
                'data': 'email',
                className: 'dt-body-center'
            },
            {
                'data': 'phone',
                className: 'dt-body-center'
            },
            {
                'data': 'gender',
                className: 'dt-body-center'
            },
            {
                'data': 'game',
                className: 'dt-body-center'
            }
        ],
        "columnDefs": [
         	{
			    "targets": 6,
			    className: 'dt-body-center',
			    "render": function ( data, type, row, meta ) {
			      return '<a class="btn btn-sm btn-warning" href="EditController?id=' + row.id+'">Edit</a>';
			    }
		  	}, 
        	{
			    "targets": 7,
			    className: 'dt-body-center',
			    "render": function ( data, type, row, meta ) {
			      return '<button class="btn btn-sm btn-danger" name="delBtn" id="' + row.id+'" onclick="deleteUser(this)">Delete</button>';
			    }
		  	} 
  		]
    });
});


function deleteUser(obj) {
    var id = $(obj).attr('id');
	$.ajax({
        type: "get",
        url: "DeleteController",
        data: {
        	"id": id
        },
        success: function () {
            $('#users-table').DataTable().ajax.reload();
            alert("User deleted successfully");
        },
        error: function(xhr, error) {
            console.log(xhr.status + " " + error);
        }
    });
}

