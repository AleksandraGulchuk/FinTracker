"use strict"


const income = 'income';
const expense = 'expense';
const tr = document.querySelectorAll('tr');

for (let i = 1; i < tr.length; i++) {
    let row = tr[i];
    let type = row.firstElementChild.textContent;
    if (type === income) {
        row.classList.add('green-row');
    } else if (type === expense) {
        row.classList.add('pink-row');
    }
}
