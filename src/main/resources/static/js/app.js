"use strict";

let editingCustomerId = null;
let editingRow = null;

const customerTable = document.querySelector(".customer-table");
const customerForm = document.querySelector("#customer-form");
const nameInput = document.querySelector("#name");
const emailInput = document.querySelector("#email");
const phoneInput = document.querySelector("#phone");
const nifInput = document.querySelector("#nif");
const customerTableBody = document.querySelector(".customer-table tbody");

// Função que recebe um cliente e desenha uma linha na tabela

function addCustomerToTable(customer) {
    const row = document.createElement("tr");

    const nameCell = document.createElement("td");
    nameCell.textContent = customer.name;
    row.appendChild(nameCell);

    const nifCell = document.createElement("td");
    nifCell.textContent = customer.nif;
    row.appendChild(nifCell);

    const emailCell = document.createElement("td");
    emailCell.textContent = customer.email;
    row.appendChild(emailCell);

    const phoneCell = document.createElement("td");
    phoneCell.textContent = customer.phoneNumber;
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
}


// Função que vai ao backend buscar os clientes que estão na base de dados
// “Para cada customer que veio da base de dados, adiciona esse customer à tabela.”

async function loadCustomers() {
    const response = await fetch("/customers");
    const customers = await response.json();

    customers.forEach(customer => {
        addCustomerToTable(customer);
    });
}


async function createCustomer(customerData){
    const response = await fetch("/customers", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(customerData)
    });

    if (!response.ok) {
        throw new Error(`Error creating customer: ${response.status}`);
    }
    const createdCustomer = await response.json();

    return createdCustomer;
}

async function updateCustomer(customerId, customerData) {
    const response = await fetch(`/customers/${customerId}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(customerData)
    });

    if (!response.ok) {
        throw new Error(`Error updating customer: ${response.status}`);
    }

    const updatedCustomer = await response.json();

    return updatedCustomer;
}

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
        nifInput.value = cells[1].textContent;
        emailInput.value = cells[2].textContent;
        phoneInput.value = cells[3].textContent;

        return;
    }

    if (deleteButton) {
        const customerId = deleteButton.dataset.customerId;
        const row = deleteButton.closest("tr");
        row.remove();

        console.log("Delete: ", customerId);
    }
});

// SUBMIT
customerForm.addEventListener("submit", async (event) => {
    event.preventDefault();

    const name = nameInput.value;
    const email = emailInput.value;
    const phone = phoneInput.value;
    const nif = nifInput.value;

    const customerData = {
        name,
        nif,
        phoneNumber: phone,
        email
    };

    if (editingCustomerId !== null) {

        const updatedCustomer = await  updateCustomer(editingCustomerId, customerData);
        const cells = editingRow.querySelectorAll("td");

        cells[0].textContent = updatedCustomer.name;
        cells[1].textContent = updatedCustomer.nif;
        cells[2].textContent = updatedCustomer.email;
        cells[3].textContent = updatedCustomer.phoneNumber;

        editingCustomerId = null;
        editingRow = null;
        customerForm.reset();

        return;
    }


    const createdCustomer = await createCustomer(customerData);

    addCustomerToTable(createdCustomer);

    customerForm.reset();
});

loadCustomers();
