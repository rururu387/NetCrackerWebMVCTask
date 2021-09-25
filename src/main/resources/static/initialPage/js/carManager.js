function httpSendPostUrlData(destination)
{
    if (creationDateInputField.value != null && creationDateInputField.value != "" && !validateDate(creationDateInputField.value))
    {
        return;
    }
    
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.onerror = function() 
    {
        alert("Connection error!")
    }

    xmlHttp.onreadystatechange = function() 
    { 
        if (xmlHttp.readyState == 4 && xmlHttp.status >= 200 && xmlHttp.status < 300)
        {
            createCarsTable(xmlHttp.responseText);
        }
        else
        {
            onHttpResponse(xmlHttp);
        }
    };

    xmlHttp.open("POST", destination, true); // true for asynchronous 

    xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

    let data = "id=" + idInputField.value
                + "&name=" + nameInputField.value
                + "&wheelAm=" + wheelAmInputField.value
                + "&weight=" + weightInputField.value
                + "&creationDate=" + creationDateInputField.value;

    xmlHttp.send(data);
}


function createCarsTable(carJson)
{
    let cars = JSON.parse(carJson);
    var table = document.createElement("table");
    table.style.width  = "50%";
    table.style.border = "1px solid black";

    for (car of cars)
    {
        let row = table.insertRow();
        
        for (const [key, value] of Object.entries(car))
        {
            let cell = row.insertCell();
            let textNode = document.createTextNode(value);
            cell.appendChild(textNode);
            cell.style.border = "1px solid black";
        }
    }
    if (table != null)
    {
        body = document.getElementById("body");
        body.appendChild(table);
    }
}

function httpSendPutBodyData(destination)
{
    if (!validateDate(creationDateInputField.value))
    {
        return;
    }

    let xmlHttp = new XMLHttpRequest();

    xmlHttp.onerror = function() 
    {
        alert("Connection error!")
    }

    xmlHttp.onreadystatechange = function() { onHttpResponse(xmlHttp); };
    
    xmlHttp.open("PUT", destination, true);

    let formData = new FormData();

    formData.append("name", nameInputField.value);
    formData.append("wheelAm", wheelAmInputField.value);
    formData.append("weight", weightInputField.value);
    formData.append("creationDate", creationDateInputField.value);

    xmlHttp.send(formData);
}

function onHttpResponse(xmlHttp)
{    
    if (xmlHttp.readyState == 4 && (xmlHttp.status < 200 || xmlHttp.status >= 300))
    {
        if (xmlHttp.responseText != null && xmlHttp.responseText != "")
        {
            alert(xmlHttp.responseText);
        }
        else
        {
            alert("Result code is not 2xx!")
        }
    }
}

function httpSendBodyData(opType, destination)
{
    if (creationDateInputField.value != null && creationDateInputField.value != "" && !validateDate(creationDateInputField.value))
    {
        return;
    }

    let xmlHttp = new XMLHttpRequest();
    xmlHttp.onerror = function() 
    {
        alert("Connection error!")
    }

    xmlHttp.onreadystatechange = function() { onHttpResponse(xmlHttp); };
    
    xmlHttp.open(opType, destination, true);
    /*xmlHttp.setRequestHeader("Content-type", "multipart/form-data");*/

    let formData = new FormData();

    formData.append("id", idInputField.value);
    formData.append("name", nameInputField.value);
    formData.append("wheelAm", wheelAmInputField.value);
    formData.append("weight", weightInputField.value);
    formData.append("creationDate", creationDateInputField.value);

    xmlHttp.send(formData);
}

async function popMessage(message)
{
    let textNode = document.createTextNode(message);
    body = document.getElementById("body");
    body.appendChild(textNode);
    await sleep(2000);
    body.removeChild(textNode);
}

function validateDate(date)
{
    const regex = new RegExp("^[0-9]{2}.[0-9]{2}.[1-9]\\d{3}$");
    if (!regex.test(date))
    {
        popMessage("Date must fit following form: dd.mm.yyyy\n");
        return false;
    }
    return true;
}

function onInsertButtonClicked()
{
    if (idInputField.value == null || nameInputField.value == null || 
        wheelAmInputField == null || weightInputField == null ||
         creationDateInputField == null)
    {
        popMessage("All fields must be filled in!");
        return;
    }
    httpSendPutBodyData("/insert");
}

const sleep = ms => new Promise(res => setTimeout(res, ms));

selectAllButton = document.getElementById("SelectAllButton");
insertButton = document.getElementById("InsertButton");
updateButton = document.getElementById("UpdateButton");
deleteButton = document.getElementById("DeleteButton");

idInputField = document.getElementById("idInput");
nameInputField = document.getElementById("nameInput");
wheelAmInputField = document.getElementById("wheelAmInput");
weightInputField = document.getElementById("weightInput");
creationDateInputField = document.getElementById("dateInput");

selectAllButton.addEventListener("click", function() { httpSendPostUrlData("/list"); });
insertButton.addEventListener("click", onInsertButtonClicked);
updateButton.addEventListener("click", function() { httpSendBodyData("POST", "/update"); });
deleteButton.addEventListener("click", function() { httpSendBodyData("DELETE", "/delete"); });

let body = document.getElementById("boby");