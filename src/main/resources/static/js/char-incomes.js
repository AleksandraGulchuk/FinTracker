"use strict"

const incomesMap = convertStringToMap(document.getElementById("incomes").value);
let incomesData = Array.from(incomesMap.values());
let incomesLabels = Array.from(incomesMap.keys()).map((element) => capitalizeFirstLetter(element));

let incomeData = {
    labels: incomesLabels,
    datasets: [{
        label: 'Incomes',
        data: incomesData,
        backgroundColor: 'hsl(144, 50%, 80%)',
        borderColor: 'hsl(144, 50%, 60%)',
        borderWidth: 1
    }]
};

let incomeConfig = {
    type: 'bar',
    data: incomeData,
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
                text: 'Incomes',
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


let incomeChart = new Chart(
    document.getElementById('incomeChart'),
    incomeConfig
);
