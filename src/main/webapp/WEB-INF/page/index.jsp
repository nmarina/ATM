<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ATM</title>
    <meta charset="UTF-8">
    <style type="text/css">
        #tblNotesInformation button {
            width: 100%;
        }

        #tblNotesInformation {
            border-collapse: collapse;
            border: 1px solid black;
        }

        #tblNotesInformation td {
            border: 1px solid black;
        }
    </style>
</head>
<body>
    <table id="tblNotesInformation" align="center">
        <thead>
            <tr>
                <td><button id="btnGetMoney">Получить наличность</button></td>
                <td><input id="cashAmount" type="text"  placeholder="0" /></td>
            </tr>
            <tr>
                <td><button id="btnAddCash">Пополнить банкомат</button></td>
                <td>
                    <select id="slctNote">
                        <option value="50">50</option>
                        <option value="100">100</option>
                        <option value="200">200</option>
                        <option value="500">500</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Баланс</td>
                <td><input id="balance" type="text"  placeholder="0" disabled/></td>
            </tr>
            <tr>
                <td colspan="2" style="background-color: cadetblue"></td>
            </tr>
            <tr>
                <td>Номинал:</td>
                <td>Количество:</td>
            </tr>
        </thead>
        <tbody>
        </tbody>
    </table>

    <!--JavaScript-->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script type="text/javascript">
        var SITE_URL = "${pageContext.request.contextPath}";
        $(document).ready(function() {
            updateAtmBalanceInformation();
            updateAtmNotesInformation();

            $("#btnGetMoney").click(function() {
                var cashAmount = $("#cashAmount").val();
                if ($.isNumeric(cashAmount)) {
                    $.ajax({
                        type: "GET",
                        data: {cashAmount: cashAmount},
                        url: SITE_URL + "/ajax/get-cash-from-atm",
                        success: function (response) {
                            if (response != -1) {
                                updateAtmNotesInformation();
                                updateAtmBalanceInformation();
                                alert("Операция успешно выполнена");
                            } else {
                                alert("-1");
                            }
                            $("#cashAmount").val("");
                        }
                    });
                } else {
                    alert("-1 Вводить целые числа, кратные 50 и больше 0...")
                }
            });

            $("#btnAddCash").click(function() {
                var cashAmount = $("#slctNote").val();
                $.ajax({
                    type: "GET",
                    data: {cashAmount : cashAmount},
                    url: SITE_URL + "/ajax/add-cash-to-atm",
                    success: function (response) {
                        if (response.trim() == "true") {
                            updateAtmNotesInformation();
                            updateAtmBalanceInformation();
                            alert("Операция успешно выполнена");
                        } else {
                            alert("Превышен лимит");
                        }
                    }
                });
            });

            function updateAtmBalanceInformation() {
                $.ajax({
                    type: "GET",
                    url: SITE_URL + "/ajax/get-atm-balance",
                    success: function (response) {
                        $("#balance").val(response);
                    }
                });
            }

            function updateAtmNotesInformation() {
                $.ajax({
                    type: "GET",
                    url: SITE_URL + "/ajax/get-atm-notes-information",
                    success: function(response) {
                        var json = JSON.parse(response);
                        var trAtmNotes = "";
                        $.each(json.atmNotes, function(noteName, noteAmount) {
                            trAtmNotes +=
                            "<tr>" +
                            "<td>" + noteName + "</td>" +
                            "<td>" + noteAmount + "</td>" +
                            "</tr>";
                        });
                        $("#tblNotesInformation tbody").empty();
                        $("#tblNotesInformation tbody").append(trAtmNotes);
                    }
                });
            }
        });
    </script>
</body>
</html>
