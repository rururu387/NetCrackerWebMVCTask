function httpGetAllCarsAsync()
{
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.onerror = function() 
    {
        alert("Connection error!")
    }

    xmlHttp.onreadystatechange = function()
    { 
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
            createCarsTable(xmlHttp.responseText);
    }
    xmlHttp.open("GET", "localhost", true); // true for asynchronous 
    xmlHttp.send(null);
}

function createCarsTable(tableContents)
{

}

let selectAllButton = document.getElementById("SelectAllButton");
selectAllButton.onclick = httpGetAllCarsAsync;

let insertAllButton = document.getElementById("InsertButton");

let updateButton = document.getElementById("UpdateButton");

let DeleteButton = document.getElementById("DeleteButton");