"use strict"

const balancesChar = document.getElementById("balancesChart").value;
console.log(balancesChar);

const balancesCharMap = convertStringToMap(balancesChar);
let balanceData = Array.from(balancesCharMap.values());
let balanceLabels = Array.from(balancesCharMap.keys()).map((element) => capitalizeFirstLetter(element));

let balancesData = {
    labels: balanceLabels,
    datasets: [{
        label: 'Balance Changes',
        data: balanceData,
        backgroundColor: 'rgba(75, 192, 192, 0.2)',
        borderColor: 'rgba(75, 192, 192, 1)',
        borderWidth: 2,
        tension: 0.4,
        pointBackgroundColor: 'rgba(75, 192, 192, 1)',
        pointBorderColor: '#fff',
        pointRadius: 4,
    }]
};

let balancesConfig = {
    type: 'line',
    data: balancesData,
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
                text: 'Balance Changes',
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

let balanceChart = new Chart(
    document.getElementById('balanceChart'),
    balancesConfig
);