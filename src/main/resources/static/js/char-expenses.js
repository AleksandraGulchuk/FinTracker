"use strict"

function capitalizeFirstLetter(str){
   str = str.toLowerCase();
   const letter = str.charAt(0).toUpperCase();
   return letter + str.slice(1);
}

function convertStringToMap(summary){
    summary = summary.replaceAll('=', '":"');
    summary = summary.replaceAll('{','{"');
    summary = summary.replaceAll('}','"}');
    summary = summary.replaceAll(', ','","');
    return new Map(Object.entries(JSON.parse(summary)));
}

const map = convertStringToMap(document.getElementById("summary").value);
let expensesData = Array.from(map.values());
let labels = Array.from(map.keys()).map((element) => capitalizeFirstLetter(element));

//let expensesData = [5000, 7000, 10000, 5000, 3000, 7000];
//let labels = ['July', 'August', 'September', 'October', 'November', 'December'];

let expenseData = {
    labels: labels,
    datasets: [{
        label: 'Expenses',
        data: expensesData,
        backgroundColor: 'hsl(0, 50%, 80%)',
        borderColor: 'hsl(0, 50%, 60%)',
        borderWidth: 1
    }]
};

let expenseConfig = {
    type: 'bar',
    data: expenseData,
    options: {
        responsive: true,
        plugins: {
            legend: {
                display: false,
                labels: {
                    font: {
                        size: 14,
                        family: "'Arial', sans-serif",
                        weight: 'bold'
                    }
                }
            },
            title: {
                display: true,
                text: 'Expenses',
                font: {
                    size: 16,
                    family: "'Arial', sans-serif",
                    weight: 'bold'
                }
            }
        },
        scales: {
            y: { beginAtZero: true }
        }
    }
};


let expenseChart = new Chart(
    document.getElementById('expenseChart'),
    expenseConfig
);
