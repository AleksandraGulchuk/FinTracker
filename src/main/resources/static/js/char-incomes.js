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
let incomesData = Array.from(map.values());
let labels = Array.from(map.keys()).map((element) => capitalizeFirstLetter(element));

let incomeData = {
    labels: labels,
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
            y: { beginAtZero: true }
        }
    }
};


let incomeChart = new Chart(
    document.getElementById('incomeChart'),
    incomeConfig
);
