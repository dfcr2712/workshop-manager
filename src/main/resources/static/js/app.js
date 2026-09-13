"use strict";

let nextCustomerId = 2;
let editingCustomerId = null;
let editingRow = null;

const customerTable = document.querySelector(".customer-table");

customerTable.addEventListener("click", (event) => {
    const editButton = event.target.closest(".edit-button");
    const deleteButton = event.target.closest(".delete-button");

    if (editButton) {
        const customerId = editButton.dataset.customerId;

        editingCustomerId = customerId;

        console.log("Edit: ", customerId);

        editingRow = editButton.closest("tr");
        const cells = editingRow.querySelectorAll("td");

        nameInput.value = cells[0].textContent;
        emailInput.value = cells[1].textContent;
        phoneInput.value = cells[2].textContent;

        return;
    }

    if (deleteButton) {
        const customerId = deleteButton.dataset.customerId;
        const row = deleteButton.closest("tr");
        row.remove();

        console.log("Delete: ", customerId);
    }
});


const customerForm = document.querySelector("#customer-form");

const nameInput = document.querySelector("#name");
const emailInput = document.querySelector("#email");
const phoneInput = document.querySelector("#phone");

const customerTableBody = document.querySelector(".customer-table tbody");

customerForm.addEventListener("submit", (event) => {
    event.preventDefault();

    const name = nameInput.value;
    const email = emailInput.value;
    const phone = phoneInput.value;

    if(editingCustomerId !== null) {
        const cells = editingRow.querySelectorAll("td");
        cells[0].textContent = name;
        cells[1].textContent = email;
        cells[2].textContent = phone;

        editingCustomerId = null;
        editingRow = null;

        customerForm.reset();

        return;
    }

    const customer = {
        id: nextCustomerId, name, email, phone
    };
    nextCustomerId++;

    const row = document.createElement("tr");

    const nameCell = document.createElement("td");
    nameCell.textContent = customer.name;
    row.appendChild(nameCell);

    const emailCell = document.createElement("td");
    emailCell.textContent = customer.email;
    row.appendChild(emailCell);

    const phoneCell = document.createElement("td");
    phoneCell.textContent = customer.phone;
    row.appendChild(phoneCell);

    // ---------------

    const actionsCell = document.createElement("td");

    const editButton = document.createElement("button");
    editButton.type = "button";
    editButton.textContent = "Edit";
    editButton.classList.add("action-button", "edit-button");
    editButton.dataset.customerId = String(customer.id);

    const deleteButton = document.createElement("button");
    deleteButton.type = "button";
    deleteButton.textContent = "Delete";
    deleteButton.classList.add("action-button", "delete-button");
    deleteButton.dataset.customerId = String(customer.id);


    actionsCell.appendChild(editButton);
    actionsCell.appendChild(deleteButton);
    row.appendChild(actionsCell);

    customerTableBody.appendChild(row);
    console.log(customer);
})
