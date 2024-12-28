"use strict"


const dateInput = document.getElementById("date");
const today = new Date().toISOString().split('T')[0];
dateInput.max = today;
dateInput.value = today;

