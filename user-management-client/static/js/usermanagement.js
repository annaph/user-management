function users() {
  $.get("/rest/users", function(data) {

  var tableContent = ""
  var selectUserContent = "<option selected='selected'></option>"

  $.each(data, function (index, user) {
    tableContent +=
      "<tr><td>" + user.id + "</td>" +
      "<td>" + user.name + "</td>" +
      "<td>" + user.email + "</td>" +
      "<td>" + user.address1 + "</td>" +
      "<td>" + user.address2 + "</td>" +
      "<td>" + user.city + "</td>" +
      "<td>" + user.postalCode + "</td>" +
      "<td>" + user.country + "</td>" +
      "<td>" + user.phone + "</td>" +
      "<td>" + user.retrievalTime + "</td></tr>"

    selectUserContent += "<option>" + user.id + "</option>"
  });

  $("#userCollectionTable tbody").html(tableContent)
  $("#deleteUserForm").find("select").html(selectUserContent)
  $("#updateUserSelect").html(selectUserContent)
 });
}

$(document).ready(function() {
  users();
});

$(function() {
  $("#refreshUserCollection").click(function() {
    users();
  });
});

$(function() {
  $("#createUserForm").submit(function(event) {
    var $inputs = $("#createUserForm :input")

    var values = {};
    $inputs.each(function() {
        values[this.name] = $(this).val()
    });

    var name = values["name"]
    var email = values["email"]
    var address1 = values["address1"]
    var address2 = values["address2"]
    var city = values["city"]
    var postalCode = values["postalCode"]
    var country = values["country"]
    var phone = values["phone"]

    var user = {
      "id": "",
      "name": name,
      "email": email,
      "address1": address1,
      "address2": address2,
      "city": city,
      "postalCode": postalCode,
      "country": country,
      "phone": phone,
      "retrievalTime": ""
    };

    $("#createUserForm").trigger("reset")

    $.ajax({
      url: "/rest/createuser/",
      data: JSON.stringify(user),
      type: "POST",
      beforeSend: function(xhr) {
        xhr.setRequestHeader("Accept", "application/json");
        xhr.setRequestHeader("Content-Type", "application/json");
      },
      success: function(data){
        users();
      }
    });

    event.preventDefault();
  });
});

$(function() {
  $("#deleteUserForm").submit(function(event) {
    var id = $("#deleteUserForm").find("select option:selected").val();

    $.ajax({
      url: "/rest/deleteuser/" + id,
      type: "DELETE",
      success: function(data) {
        users()
      }
    });

    event.preventDefault();
  });
});

$(function() {
  $("#updateUserSelect").change(function() {
    var id = $(this).val();

    $.get("/rest/user/" + id, function(user) {
      var name = user.name;
      var email = user.email;
      var address1 = user.address1;
      var address2 = user.address2;
      var city = user.city;
      var postalCode = user.postalCode;
      var country = user.country;
      var phone = user.phone;

      $("#updateUserForm").find("input[name='name']").val(name);
      $("#updateUserForm").find("input[name='email']").val(email);
      $("#updateUserForm").find("input[name='address1']").val(address1);
      $("#updateUserForm").find("input[name='address2']").val(address2);
      $("#updateUserForm").find("input[name='city']").val(city);
      $("#updateUserForm").find("input[name='postalCode']").val(postalCode);
      $("#updateUserForm").find("input[name='country']").val(country);
      $("#updateUserForm").find("input[name='phone']").val(phone);

      $("#updateUserForm").show();
    });
  });
});

$(function() {
  $("#updateUserForm").submit(function(event) {
    var id = $("#updateUserSelect").val();

    var name =  $("#updateUserForm").find("input[name='name']").val();
    var email = $("#updateUserForm").find("input[name='email']").val();
    var address1 = $("#updateUserForm").find("input[name='address1']").val();
    var address2 = $("#updateUserForm").find("input[name='address2']").val();
    var city =  $("#updateUserForm").find("input[name='city']").val();
    var postalCode = $("#updateUserForm").find("input[name='postalCode']").val();
    var country = $("#updateUserForm").find("input[name='country']").val();
    var phone = $("#updateUserForm").find("input[name='phone']").val();

    var user = {
      "id": "",
      "name": name,
      "email": email,
      "address1": address1,
      "address2": address2,
      "city": city,
      "postalCode": postalCode,
      "country": country,
      "phone": phone,
      "retrievalTime": ""
    };

    $.ajax({
      url: "/rest/updateuser/" + id,
      data: JSON.stringify(user),
      type: "PUT",
      beforeSend: function(xhr) {
        xhr.setRequestHeader("Content-Type", "application/json");
      },
      success: function(data) {
        $("#updateUserForm").hide();
        users();
      }
    });

    event.preventDefault();
  });
});
