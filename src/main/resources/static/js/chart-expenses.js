"use strict"

const expensesMap = convertStringToMap(document.getElementById("expenses").value);
let expensesData = Array.from(expensesMap.values());
let expensesLabels = Array.from(expensesMap.keys()).map((element) => capitalizeFirstLetter(element));

let expenseData = {
    labels: expensesLabels,
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
            y: {beginAtZero: true}
        }
    }
};


let expenseChart = new Chart(
    document.getElementById('expenseChart'),
    expenseConfig
);
