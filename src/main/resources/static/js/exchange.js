let themes = ['black', 'white'];
let eventsCount;
let chart;
let interval;
let button = 'BUY';
let eventLimit = 30;
let price;
let balance;

const successTimout = 5000;
const eventInterval = 5000;

function addLastExchangeEvent() {
    $.get("/internal/events/last", function(data) {
        console.log('Last event: ' + data.dateTime + ' ' + data.absoluteAmount)
        if (eventsCount >= eventLimit && data) {
            chart.data.labels.splice(0, 1);
            chart.data.datasets[0].data.splice(0, 1);
        }
        chart.data.labels.push(data.dateTime);
        chart.data.datasets[0].data.push(data.absoluteAmount);
        price.value = data.absoluteAmount;
        eventsCount++;
        chart.update();
    })
}

function getLiveEvents() {
    initChart(format(new Date()), true);
}

function initChart(date, dynamic) {
    console.log('Date: ' + date + '; Dynamic: ' + dynamic);
    $.get('/internal/events/all?date=' + date + '&limit=' + eventLimit, function (data) {
        eventsCount = data.length;
        let labels = [];
        let dataset = [];
        for (const event of data) {
            labels.push(event.dateTime);
            dataset.push(event.absoluteAmount);
        }
        if (data.length > 0) {
            price.value = data[data.length - 1].absoluteAmount;
        } else {
            price.value = '';
        }
        console.log('Labels count: ' + labels.length);
        console.log('Data count: ' + data.length);
        drawChart(labels, dataset, date);

        if (dynamic) {
            interval = setInterval(addLastExchangeEvent, eventInterval);
        } else {
            clearInterval(interval);
        }
    })
}

function drawChart(labels, data, date) {
    destroyCurrentChart();
    let context = document.getElementById('chart').getContext('2d');
    let chartData = {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: 'Exchange events. Date: ' + date,
                data: data,
                cubicInterpolationMode: 'monotone',
                backgroundColor: 'crimson',
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)',
                    'rgba(75, 111, 111, 1)',
                    'rgba(200, 10, 290, 1)'
                ],
                borderWidth: 3
            }]
        }
    };
    chart = new Chart(context, chartData);
}

function destroyCurrentChart() {
    if (chart != null) {
        chart.destroy();
        clearInterval(interval);
    }
}

function createTransaction() {
    let quantityAreaElement = document.getElementById('quantityArea');
    console.log('Quantity: ' + quantityAreaElement.value);
    let isValid = validateQuantity(quantityAreaElement.value);
    if (isValid) {
        let form = $('#transactionForm').serialize();
        let error = $('#error');
        hideElement(error);

        $.ajax({
            url: 'internal/transactions/create?type=' + button,
            method: 'POST',
            contentType: 'application/x-www-form-urlencoded',
            data: form
        }).done(function() {
            showSuccess();
            updateBalance();
            hideElement($('#captchaError'));
        }).fail(function(response) {
            grecaptcha.reset();
            if (response.responseStatus === 400) {
                console.error('Recaptcha check failed... ' + response.responseJSON.message);
                $('#captchaError').show().html(response.responseJSON.message);
            } else {
                error.show().html(response.responseJSON.message);
            }
        }).then(() => {
            grecaptcha.reset();
            quantityAreaElement.value = '';
        })
    }
}

function validateQuantity(quantity) {
    let result = true;
    let error = $('#error');
    if (quantity <= 0) {
        result = false;
        error.show().html('Quantity can\'t be less than zero');
    } else if (quantity > 1000) {
        result = false;
        error.show().html('Quantity can\'t be greater than 1 thousand');
    }

    return result;
}

function hideElement(element) {
    element.css('display', 'none');
    element.html('');
}

function format(date) {
    return date.toISOString().substr(0, 10);
}

function changeTheme(index) {
    console.log('Change theme... Index: ' + index);
    let canvas = $('#canvas');
    if (canvas.css('background') !== themes[index]) {
        canvas.css('background', themes[index]);
    }
}

function onEventLimitChange() {
    let dateAreaElement = document.getElementById('dateArea');
    if (dateAreaElement.value) {
        initChart(dateAreaElement.value, false);
    } else {
        getLiveEvents();
    }
}

function showSuccess() {
    let success = $('#success');
    success.show();
    setTimeout(() => success.hide(), successTimout);
}

function getContactEmails() {
    $.get('/internal/profile/contactEmails', function (data) {
        let contactEmails = document.getElementById('contactEmails');
        contactEmails.onchange = function (e) {
            sendInvitation(e.target.value);
        }
        for (let email of data) {
            let option = document.createElement('option');
            option.innerText = email;
            contactEmails.appendChild(option);
        }
    })
}

function sendInvitation(email) {
    $.ajax({
        url: '/internal/email/send?email=' + email,
        method: 'POST',
        contentType: 'application/json'
    }).done(function () {
        showSuccess();
    })
}

function updateBalance() {
    $.get('/internal/profile/balance', function (data) {
        balance.value = data;
    })
}

$(document).ready(() => {
    $(document).ajaxError(function (e, xhr) {
        console.log('Unhandled ajax error. Status ' + xhr.status);
    })

    getLiveEvents();
    getContactEmails();

    price = document.getElementById('price');

    balance = document.getElementById('balance');
    updateBalance();

    let transactionForm = document.getElementById('transactionForm');
    transactionForm.onsubmit = function (event) {
        event.preventDefault();
        createTransaction();
    }

    let buy = document.getElementById('buy');
    buy.onclick = function() {
        button = buy.value;
    }

    let sell = document.getElementById('sell');
    sell.onclick = function() {
        button = sell.value;
    }

    let dateAreaElement = document.getElementById('dateArea');
    dateAreaElement.onchange = () => initChart(dateAreaElement.value, false);

    let liveButton = document.getElementById('live');
    liveButton.onclick = function() {
        getLiveEvents();
    }

    let theme = document.getElementById('theme');
    theme.oninput = function(event) {
        changeTheme(event.target.selectedIndex)
    }

    let limit = document.getElementById('limit');
    limit.oninput = function(event) {
        eventLimit = event.target.value;
        onEventLimitChange();
    }
})