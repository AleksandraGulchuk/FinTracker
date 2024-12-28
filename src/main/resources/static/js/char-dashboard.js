"use strict"

// let balanceData = [5000, -2000, 300, 10000];
// let balanceLabels = ['Week 1', 'Week 2', 'Week 3', 'Week 4'];


// let categories = ['Car', 'Food', 'Utilities', 'Sports', 'Education'];
// let expenseData = [800, 1000, 300, 300, 1000];


const balancesChar = document.getElementById("balancesChar").value;
console.log(balancesChar);

const categoriesChar = document.getElementById("categoriesChar").value;
console.log(categoriesChar);


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

    let map = new Map(Object.entries(JSON.parse(summary)));
    console.log(map);
    return map;
}

const balancesCharMap = convertStringToMap(balancesChar);
let balanceData = Array.from(balancesCharMap.values());
let balanceLabels = Array.from(balancesCharMap.keys()).map((element) => capitalizeFirstLetter(element));

const categoriesCharMap = convertStringToMap(categoriesChar);
let expenseData = Array.from(categoriesCharMap.values());
let categoryLabels = Array.from(categoriesCharMap.keys()).map((element) => capitalizeFirstLetter(element));





function generatePastelColors(dataLength) {
    const colors = [];
    for (let i = 0; i < dataLength; i++) {
        const hue = (i * (360 / dataLength)) % 360;
        const saturation = 50;
        const lightness = 80;
        colors.push(`hsl(${hue}, ${saturation}%, ${lightness}%)`);
    }
    return colors;
}


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

let expensesData = {
    labels: categoryLabels,
    datasets: [{
        label: 'Expenses by Category',
        data: expenseData,
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
            y: { beginAtZero: true }
        }
    }
};

let expensesConfig = {
    type: 'pie',
    data: expensesData,
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

let expensesChart = new Chart(
    document.getElementById('expensesChart'),
    expensesConfig
);

// function changeTotal() {
//     let mylist = document.getElementById("myList");
//     let option = mylist.options[mylist.selectedIndex].text;
//
//     switch (option) {
//         case "This month":
//             document.getElementById("incomes-total").value = '$50,000.00';
//             document.getElementById("expenses-total").value = '$40,000.00';
//             document.getElementById("balance-total").value = '$10,000.00';
//
//             balanceData = [5000, -2000, 300, 10000];
//             balanceLabels = ['Week 1', 'Week 2', 'Week 3', 'Week 4'];
//             categories = ['Car', 'Food', 'Utilities', 'Sports', 'Education'];
//             expenseData = [800, 1000, 300, 300, 1000];
//
//             break;
//
//         case "This week":
//             document.getElementById("incomes-total").value = '$5,000.00';
//             document.getElementById("expenses-total").value = '$4,000.00';
//             document.getElementById("balance-total").value = '$1,000.00';
//
//             balanceData = [500, 450, 450, 1000];
//             balanceLabels = ['Sunday', 'Monday', 'Tuesday', 'Wenesday'];
//             categories = ['Car', 'Food', 'Sports'];
//             expenseData = [50, 500, 30];
//
//             break;
//
//         case "Six months":
//             document.getElementById("incomes-total").value = '$500,000.00';
//             document.getElementById("expenses-total").value = '$497,000.00';
//             document.getElementById("balance-total").value = '$7,000.00';
//
//             balanceData = [5000, 2000, -1000, -500, 3000, 7000];
//             balanceLabels = ['July', 'August', 'September', 'October', 'November', 'December'];
//             categories = ['Car', 'Food', 'Utilities', 'Sports', 'Education', 'Donations'];
//             expenseData = [1200, 12000, 3000, 600, 15000, 1500];
//
//             break;
//
//         default:
//             document.getElementById("incomes-total").value = '$50,000.00';
//             document.getElementById("expenses-total").value = '$40,000.00';
//             document.getElementById("balance-total").value = '$10,000.00';
//
//             balanceData = [5000, -2000, 300, -1000];
//             balanceLabels = ['Week 1', 'Week 2', 'Week 3', 'Week 4'];
//             categories = ['Car', 'Food', 'Utilities', 'Sports', 'Education'];
//             expenseData = [800, 1000, 300, 300, 1000];
//     }
//
//     // Update the chart data
//     balanceConfig.data.labels = balanceLabels;
//     balanceConfig.data.datasets[0].data = balanceData;
//
//     expensesConfig.data.labels = categories;
//     expensesConfig.data.datasets[0].data = expenseData;
//     expensesConfig.data.datasets[0].backgroundColor = generatePastelColors(categories.length);
//
//     // Destroy the old charts before creating new ones
//     if (balanceChart) balanceChart.destroy();
//     if (expensesChart) expensesChart.destroy();
//
//     // Create new charts
//     balanceChart = new Chart(document.getElementById('balanceChart'), balanceConfig);
//     expensesChart = new Chart(document.getElementById('expensesChart'), expensesConfig);
// }


