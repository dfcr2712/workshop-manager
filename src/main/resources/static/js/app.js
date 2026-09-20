"use strict";

let editingCustomerId = null;
let editingRow = null;
let customerRequestInProgress = false;

const customerTable = document.querySelector(".customer-table");
const customerForm = document.querySelector("#customer-form");
const nameInput = document.querySelector("#name");
const emailInput = document.querySelector("#email");
const phoneInput = document.querySelector("#phone");
const nifInput = document.querySelector("#nif");
const addressInput = document.querySelector("#address");
const submitButton = customerForm.querySelector('button[type="submit"]');
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

    const addressCell = document.createElement("td");
    addressCell.textContent = customer.address;
    row.appendChild(addressCell);

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
    if (!response.ok) {
        const message = await response.text();
        throw new Error(message || `Error loading customers: ${response.status}`);
    }
    const customers = await response.json();

    customers.forEach(customer => {
        addCustomerToTable(customer);
    });
}


async function createCustomer(customerData) {
    const response = await fetch("/customers", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(customerData)
    });

    if (!response.ok) {
        const message = await response.text();
        throw new Error(message || `Error creating customer: ${response.status}`);
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
        const message = await response.text();
        throw new Error(message || `Error updating customer: ${response.status}`);
    }

    const updatedCustomer = await response.json();

    return updatedCustomer;
}


async function deleteCustomer(customerId) {
    const response = await fetch(`/customers/${customerId}`, {
        method: "DELETE",
    });

    if (!response.ok) {
        const message = await response.text();
        throw new Error(message || `Error deleting customer: ${response.status}`);
    }
}


customerTable.addEventListener("click", async (event) => {
    // Aguarda o pedido atual antes de editar ou eliminar outro cliente.
    if (customerRequestInProgress) {
        return;
    }

    const editButton = event.target.closest(".edit-button");
    const deleteButton = event.target.closest(".delete-button");

    if (editButton) {
        const customerId = editButton.dataset.customerId;

        editingCustomerId = customerId;

        editingRow = editButton.closest("tr");

        const cells = editingRow.querySelectorAll("td");

        nameInput.value = cells[0].textContent;
        nifInput.value = cells[1].textContent;
        emailInput.value = cells[2].textContent;
        phoneInput.value = cells[3].textContent;
        addressInput.value = cells[4].textContent;
        submitButton.textContent = "Update Customer";

        return;
    }

    if (deleteButton) {
        customerRequestInProgress = true;
        submitButton.disabled = true;

        try {
            const customerId = deleteButton.dataset.customerId;
            const row = deleteButton.closest("tr");

            await deleteCustomer(customerId);

            row.remove();

            if (editingCustomerId === customerId) {
                customerForm.reset();
            }
        } catch (error) {
            alert(error.message);
        } finally {
            customerRequestInProgress = false;
            submitButton.disabled = false;
        }
    }
});

customerForm.addEventListener("reset", () => {
    editingCustomerId = null;
    editingRow = null;
    submitButton.textContent = "Create Customer";
});

// SUBMIT
customerForm.addEventListener("submit", async (event) => {
    event.preventDefault();

    if (customerRequestInProgress) {
        return;
    }

    const customerId = editingCustomerId;
    const row = editingRow;

    const name = nameInput.value;
    const email = emailInput.value;
    const phone = phoneInput.value;
    const nif = nifInput.value;
    const address = addressInput.value;

    const customerData = {
        name,
        nif,
        phoneNumber: phone,
        email,
        address
    };

    customerRequestInProgress = true;
    submitButton.disabled = true;

    try {
        if (customerId !== null) {
            const updatedCustomer = await updateCustomer(customerId, customerData);
            const cells = row.querySelectorAll("td");

            cells[0].textContent = updatedCustomer.name;
            cells[1].textContent = updatedCustomer.nif;
            cells[2].textContent = updatedCustomer.email;
            cells[3].textContent = updatedCustomer.phoneNumber;
            cells[4].textContent = updatedCustomer.address;
        } else {
            const createdCustomer = await createCustomer(customerData);
            addCustomerToTable(createdCustomer);
        }

        customerForm.reset();
    } catch (error) {
        alert(error.message);
    } finally {
        customerRequestInProgress = false;
        submitButton.disabled = false;
    }
});

loadCustomers().catch(error => {
    alert(error.message);
});
