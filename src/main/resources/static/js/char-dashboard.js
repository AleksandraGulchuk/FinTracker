"use strict"

const balancesChar = document.getElementById("balancesChar").value;
console.log(balancesChar);

const categoryChar = document.getElementById("categoryChar").value;
console.log(categoryChar);

const balancesCharMap = convertStringToMap(balancesChar);
let balanceData = Array.from(balancesCharMap.values());
let balanceLabels = Array.from(balancesCharMap.keys()).map((element) => capitalizeFirstLetter(element));

const categoriesCharMap = convertStringToMap(categoryChar);
let categoryData = Array.from(categoriesCharMap.values());
let categoryLabels = Array.from(categoriesCharMap.keys()).map((element) => capitalizeFirstLetter(element));

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


let backgroundColors = generatePastelColors(categoryLabels.length);

let categoriesData = {
    labels: categoryLabels,
    datasets: [{
        label: 'Expenses by Category',
        data: categoryData,
        backgroundColor: backgroundColors,
        borderColor: backgroundColors.map(color => color.replace(/(\d+)%\)/, '100%)')),
        borderWidth: 1
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

let categoriesConfig = {
    type: 'pie',
    data: categoriesData,
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


let balanceChart = new Chart(
    document.getElementById('balanceChart'),
    balancesConfig
);

let categoriesChart = new Chart(
    document.getElementById('categoriesChart'),
    categoriesConfig
);

