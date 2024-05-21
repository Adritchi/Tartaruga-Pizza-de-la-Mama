document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("ingredients-form");
    const pizzaImage = document.getElementById("pizza-image");
    const selectedIngredientsList = document.getElementById("selected-ingredients");

    form.addEventListener("change", function() {
        const formData = new FormData(form);
        const ingredients = formData.getAll("ingredients");

        // Clear the current pizza image and selected ingredients list
        pizzaImage.innerHTML = "";
        selectedIngredientsList.innerHTML = "";

        ingredients.forEach(ingredient => {
            const img = document.createElement("img");
            img.src = `images/${ingredient.toLowerCase()}.png`;
            img.alt = ingredient;
            img.style.position = "absolute";
            pizzaImage.appendChild(img);

            const li = document.createElement("li");
            li.textContent = ingredient;
            selectedIngredientsList.appendChild(li);
        });
    });
});
