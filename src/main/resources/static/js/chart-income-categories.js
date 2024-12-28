"use strict"

const incomeCategories = document.getElementById("incomeCategoriesChartId").value;
console.log(incomeCategories);

const incomeCategoriesMap = convertStringToMap(incomeCategories);

let incomeCategoriesValues = Array.from(incomeCategoriesMap.values())
    .map(v => v.toString())
    .map(s => parseFloat(s));
let incomeCategoriesLabels = Array.from(incomeCategoriesMap.keys())
    .map((element) => capitalizeFirstLetter(element));

let incomeBackgroundColors = generatePastelColors(incomeCategoriesLabels.length);

let incomeCategoriesData = {
    labels: incomeCategoriesLabels,
    datasets: [{
        label: 'Incomes by Category',
        data: incomeCategoriesValues,
        backgroundColor: incomeBackgroundColors,
        borderColor: incomeBackgroundColors.map(color => color.replace(/(\d+)%\)/, '100%)')),
        borderWidth: 1
    }]
};

let incomeCategoriesConfig = {
    type: 'pie',
    data: incomeCategoriesData,
    options: {
        responsive: true,
        plugins: {
            legend: {
                display: true,
                position: 'left',
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
                text: 'Incomes by Category',
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

let incomeCategoriesChart = new Chart(
    document.getElementById('incomeCategoriesChart'),
    incomeCategoriesConfig
);

