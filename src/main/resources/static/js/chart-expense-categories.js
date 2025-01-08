"use strict"

const expenseCategories = document.getElementById("expenseCategoriesChartId").value;

const expenseCategoriesMap = convertStringToMap(expenseCategories);
let expenseCategoriesValues = Array.from(expenseCategoriesMap.values())
    .map(v => v.toString())
    .map(s => parseFloat(s));
let expenseCategoriesLabels = Array.from(expenseCategoriesMap.keys())
    .map((element) => capitalizeFirstLetter(element));

let expenseBackgroundColors = generatePastelColors(expenseCategoriesLabels.length);

let expenseCategoriesData = {
    labels: expenseCategoriesLabels,
    datasets: [{
        label: 'Expenses by Category',
        data: expenseCategoriesValues,
        backgroundColor: expenseBackgroundColors,
        borderColor: expenseBackgroundColors.map(color => color.replace(/(\d+)%\)/, '100%)')),
        borderWidth: 1
    }]
};

let expenseCategoriesConfig = {
    type: 'pie',
    data: expenseCategoriesData,
    options: {
        responsive: true,
        plugins: {
            legend: {
                display: true,
                position: 'right',
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
                text: 'Expenses by Category',
                font: {
                    size: 16,
                    family: "'Arial', sans-serif",
                    weight: 'bold'
                }
            },
            tooltip: {
                callbacks: {
                    label: function (context) {
                        const total = context.dataset.data.reduce((acc, value) => acc + value, 0);
                        const value = context.raw;
                        const percentage = ((value / total) * 100).toFixed(2);
                        return `${context.label}: ${value} (${percentage}%)`;
                    }
                }
            }
        }
    }
};

let expenseCategoriesChart = new Chart(
    document.getElementById('expenseCategoriesChart'),
    expenseCategoriesConfig
);

