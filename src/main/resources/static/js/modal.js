document.addEventListener("DOMContentLoaded", function () {

    const modalButtons = document.querySelectorAll('[data-toggle="modal"]');

    modalButtons.forEach(button => {
        button.addEventListener("click", function () {
            const modalId = button.getAttribute("data-target").substring(1);
            const modal = document.getElementById(modalId);

            if (modal) {
                const id = button.getAttribute("data-id");
                const categoryId = button.getAttribute("data-category-id");
                const date = button.getAttribute("data-date");
                const amount = button.getAttribute("data-amount");
                const description = button.getAttribute("data-description");

                modal.querySelector("#id").value = id;
                modal.querySelector("#category").value = categoryId;
                modal.querySelector("#date").value = date;
                modal.querySelector("#amount").value = amount;
                modal.querySelector("#description").value = description;

                modal.classList.add("show");
                modal.style.display = "block";

                const closeButtons = modal.querySelectorAll("[data-dismiss='modal']");
                closeButtons.forEach(closeButton => {
                    closeButton.addEventListener("click", function () {
                        modal.classList.remove("show");
                        modal.style.display = "none";
                    });
                });
            }
        });
    });
});
