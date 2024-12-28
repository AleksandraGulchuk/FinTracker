"use strict"

const incomesChartMap = convertStringToMap(document.getElementById("incomes").value);
let incomesChartData = Array.from(incomesChartMap.values());

let incomesExpensesLabels = Array.from(incomesChartMap.keys()).map((element) => capitalizeFirstLetter(element));

const expensesChartMap = convertStringToMap(document.getElementById("expenses").value);
let expensesChartData = Array.from(expensesChartMap.values());


let incomeExpenseData = {
    labels: incomesExpensesLabels,
    datasets: [
        {
            label: 'Incomes',
            data: incomesChartData,
            backgroundColor: 'hsl(144, 50%, 80%)',
            borderColor: 'hsl(144, 50%, 60%)',
            borderWidth: 1,
            stack: 'Stack 0',
        },
        {
            label: 'Expenses',
            data: expensesChartData,
            backgroundColor: 'hsl(0, 50%, 80%)',
            borderColor: 'hsl(0, 50%, 60%)',
            borderWidth: 1,
            stack: 'Stack 0',
        }
    ]
};

let incomeExpenseConfig = {
    type: 'bar',
    data: incomeExpenseData,
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
                text: 'Incomes-Expenses',
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


let incomeExpenseChart = new Chart(
    document.getElementById('incomeExpenseChart'),
    incomeExpenseConfig
);
