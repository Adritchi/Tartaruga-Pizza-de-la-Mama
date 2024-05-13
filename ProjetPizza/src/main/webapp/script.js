const ingredientDetails = {
        'fromage': {price: 1.5, nutrition: 310},
        'tomate': {price: 0.5, nutrition: 20},
        'olives': {price: 0.7, nutrition: 50},
        'jambon': {price: 2, nutrition: 350}
    };

    const sizeDetails = {
        'petite': {price: 8, nutrition: 160},
        'moyenne': {price: 10, nutrition: 240},
        'grande': {price: 12, nutrition: 300}
    };

    const doughDetails = {
        'fine': {price: 0, nutrition: 0},
        'normale': {price: 1, nutrition: 40},
        'épaisse': {price: 2, nutrition: 80}
    };

    let totalPrice = 0;
    let totalCalories = 0;

    function initializeIngredients() {
        const ingredientSelect = document.getElementById('ingredient');
        for (const key in ingredientDetails) {
            const option = document.createElement('option');
            option.value = key;
            option.text = `${key.charAt(0).toUpperCase() + key.slice(1)}`;
            ingredientSelect.appendChild(option);
        }
        updateTotal();
    }

    function updateTotal() {
        const sizeSelect = document.getElementById('size').value;
        const doughSelect = document.getElementById('dough').value;

        // Réinitialiser les totaux avant de recalculer
        totalPrice = sizeDetails[sizeSelect].price + doughDetails[doughSelect].price;
        totalCalories = sizeDetails[sizeSelect].nutrition + doughDetails[doughSelect].nutrition;

        // Calculer le total à partir des ingrédients ajoutés
        document.querySelectorAll('.ingredient-item').forEach(item => {
            const ingName = item.getAttribute('data-ingredient');
            totalPrice += parseFloat(ingredientDetails[ingName].price);
            totalCalories += parseInt(ingredientDetails[ingName].nutrition);
        });

        document.getElementById('totalPrice').innerText = totalPrice.toFixed(2);
        document.getElementById('totalCalories').innerText = totalCalories;
    }


    function addIngredient() {
    const ingredientSelect = document.getElementById('ingredient');
    const selectedIngredient = ingredientSelect.value;
    const details = ingredientDetails[selectedIngredient] || {price: '', nutrition: ''};

    // Ajouter visuellement l'ingrédient
    const listDiv = document.getElementById('ingredientsList');
    const itemDiv = document.createElement('div');
    itemDiv.classList.add('ingredient-item');
    itemDiv.setAttribute('data-ingredient', selectedIngredient);
    itemDiv.innerText = `${selectedIngredient.charAt(0).toUpperCase() + selectedIngredient.slice(1)} - Prix: ${details.price} €, Calories: ${details.nutrition} kcal`;
    listDiv.appendChild(itemDiv);

    // Créer des éléments de formulaire cachés pour envoyer les données
    const form = document.querySelector('form');
    createHiddenInput(form, 'ingredientName', selectedIngredient);
    createHiddenInput(form, 'price', details.price);
    createHiddenInput(form, 'nutrition', details.nutrition);

    updateTotal();
}

function createHiddenInput(form, name, value) {
    const input = document.createElement('input');
    input.type = 'hidden';
    input.name = name;
    input.value = value;
    form.appendChild(input);
}
